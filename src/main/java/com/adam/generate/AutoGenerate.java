package com.adam.generate;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.adam.PdmParser;
import com.adam.model.ModelM;
import com.adam.model.PackageM;
import com.adam.model.TableM;
import com.template.PdmModelHandler;
import com.template.TableModel;
import com.util.Config;

/**
 *
 * 自动生成器
 * @author hanqing.tan
 */
public class AutoGenerate {

    /**
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        //powerDesigner_pdm_path为pdm文件所在路径
        generate(Config.get("powerDesigner_pdm_path"));

    }

    public AutoGenerate(Pdm2JavaConfig pdm2JavaConfig) {
        super();
        init(pdm2JavaConfig);
    }

    private static void init(Pdm2JavaConfig pdm2JavaConfig) {
        AutoGenerate.pdm2JavaConfig = pdm2JavaConfig;
        BASE_PATH = pdm2JavaConfig.getProjectOutputPath() + "/" + pdm2JavaConfig.getProjectNameEn();
        BASE_PACKAGE = pdm2JavaConfig.getBasePackage() + "." + pdm2JavaConfig.getProjectNameEn();

        if (pdm2JavaConfig.getFramework() == 0)
            FRAMEWORK = "sm";
        else if (pdm2JavaConfig.getFramework() == 1)
            FRAMEWORK = "si";
        else if (pdm2JavaConfig.getFramework() == 2)
            FRAMEWORK = "ssh";
        if (StringUtils.isNotBlank(pdm2JavaConfig.getAdminPathName()))
            ADMIN_PATH_NAME = pdm2JavaConfig.getAdminPathName();
        encoding = pdm2JavaConfig.getEncoding();
        pojoPackage = pdm2JavaConfig.getPojoPackage();
        JAVA_BASE_PATH = BASE_PATH + "/src/main/java/" + BASE_PACKAGE.replace('.', '/') + "/";
        ADMIN_VM_VIEW_BASE_PATH = BASE_PATH + "/src/main/webapp/WEB-INF/views/admin/";
        RESOURCES_BASE_PATH = BASE_PATH + "/src/main/resources/" + BASE_PACKAGE.replace('.', '/') + "/";

    }

    private static String ADMIN_PATH_NAME = Config.get("admin_path_name");
    private static String ADMIN_PATH = Config.get("admin_path");
    public static void createIbatisDaoPackage() {
        File daoPath = new File(JAVA_BASE_PATH + daoPackage.replace('.', '/'));
        if (!daoPath.exists()) {
            daoPath.mkdirs();
        }
    }
    private static String daoPackage = Config.get("dao_package");
    private static String worldcase = Config.get("worldcase");
    private static String projectNameZh = Config.get("projct_name_zh");
    private static String projectNameEn = Config.get("projct_name_en");
    private static String servicePackage = Config.get("service_package");
    private static Pdm2JavaConfig pdm2JavaConfig;
    private static String BASE_PATH = Config.get("project_path");
    private static String BASE_PACKAGE = Config.get("base_package") + "";
    private static String ADMIN_VM_VIEW_BASE_PATH = BASE_PATH + ADMIN_PATH_NAME
            + "/";
    private static String FRAMEWORK = Config.get("framework");
    private static String JAVA_BASE_PATH = BASE_PATH + BASE_PACKAGE.replace('.', '/') + "/";
    private static String RESOURCES_BASE_PATH = BASE_PATH + "/src/main/resources/" + BASE_PACKAGE.replace('.', '/')
            + "/";
    private static String CLASS_BASE_PATH = null;
    private static String DAOIMPL_PACKAGE = null;
    private static String encoding = Config.get("encoding");

    private static String pojoPackage = Config.get("pojo_package");

    public static boolean generate() {
        try {
            generate(pdm2JavaConfig.getPdmPath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void generate(String pdm) {
        File file = new File(pdm);
        //解析pdm文件
        ModelM model = new PdmParser().parse(file);
        try {
            initClassPath();
            VelocityContext context = init();
            List packagesList = model.getPackages();
            List<TableModel> tableModels = new ArrayList<TableModel>();
            if ("ssi".equals(FRAMEWORK)) {
                DAOIMPL_PACKAGE = "ibatis";
            } else if ("sm".equals(FRAMEWORK)) {
                DAOIMPL_PACKAGE = "mybatis";
            }
            for (int i = 0; i < packagesList.size(); i++) {
                PackageM packageM = (PackageM) packagesList.get(i);
                List<TableM> tableList = packageM.getTables();
                for (int j = 0; j < tableList.size(); j++) {
                    TableM tableM = tableList.get(j);
                    TableModel tableModel = PdmModelHandler.tableM2TableModel(tableM);
                    context.put("tableModel", tableModel);
                    tableModels.add(tableModel);
                    if ("ssi".equals(FRAMEWORK)) {
                        generateIbatisDao(context, tableModel);
                        generateStrutsAction(context, tableModel);
                        generatePo(context, tableModel);
                        generateViewVm(context, tableModel);
                        generateSqlMap(context, tableModel);
                    } else if ("sm".equals(FRAMEWORK)) {
                        generateMybatisDao(context, tableModel);
                        generateMybatisSpringAction(context, tableModel);
                        generateMybatisPo(context, tableModel);
                        generateMybatisSqlMap(context, tableModel);
                        generateMybatisService(context, tableModel);
                        generateMybatisViewVm(context, tableModel);
                    }
                }
            }
            context.put("tableModels", tableModels);
            context.put("packagesList", packagesList);
            createProject(CLASS_BASE_PATH + "template/" + FRAMEWORK, context);
            generateBaseJava(CLASS_BASE_PATH + "template/javaUtil", context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initClassPath() throws Exception {
        URL url = AutoGenerate.class.getResource("/");
        CLASS_BASE_PATH = URLDecoder.decode(url.getPath().substring(0, url.getPath().length()));
        System.out.println("CLASS_BASE_PATH=" + CLASS_BASE_PATH);
        System.out.println(url);
        System.out.println(AutoGenerate.class.getResource(""));
        System.out.println(AutoGenerate.class.getResource("."));

    }

    public static VelocityContext init() throws Exception {
        Map map = new HashMap();
        map.put("projectnamezh", projectNameZh);
        map.put("projectnameen", projectNameEn);
        map.put("basepackage", BASE_PACKAGE);
        map.put("encoding", encoding);
        map.put("framework", FRAMEWORK);
        map.put("pojopackage", pojoPackage);
        map.put("servicepackage", servicePackage);
        map.put("daopackage", daoPackage);
        map.put("worldcase", worldcase);

        map.put("projct_name_zh", projectNameZh);
        map.put("projct_name_en", projectNameEn);
        map.put("base_package", BASE_PACKAGE);
        map.put("pojo_package", pojoPackage);
        map.put("service_package", servicePackage);
        map.put("dao_package", daoPackage);
        map.put("admin_path", ADMIN_PATH);

        VelocityContext context = new VelocityContext(map);// Config.getConfig());
        Properties p = new Properties();
        String resourcePath = CLASS_BASE_PATH + "template";
        p.put("file.resource.loader.path", resourcePath);
        p.put("input.encoding", "UTF-8");
        p.put("output.encoding", "UTF-8");
        Velocity.init(p);

        context.put("timeMillisecond", System.currentTimeMillis());
        context.put("stringUtil", new StringUtils());
        context.put("dt", new org.apache.velocity.tools.generic.DateTool());

        return context;
    }

    /**
     *
     * @param path
     * @param context
     * @throws Exception
     */
    public static void createJava(String path, VelocityContext context) throws Exception {
        String javaPath = "src\\main\\java";
        File prf = new File(path);
        File[] files = prf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jar")) {
                    return false;
                }
                return true;
            }
        });
        for (int i = 0; i < files.length; i++) {
            String ap = files[i].getAbsolutePath();
            String subAp = ap.substring(ap.indexOf(javaPath) + javaPath.length() + 1, ap.length());
            if (files[i].isDirectory()) {
                new File(BASE_PATH + File.separator + javaPath + File.separator + BASE_PACKAGE.replace('.', '/')
                        + File.separator + subAp).mkdirs();

                createJava(files[i].getAbsolutePath(), context);
            } else {
                // String ap = files[i].getAbsolutePath();
                String relativePath = ap.substring(ap.indexOf("template" + File.separator + FRAMEWORK + File.separator)
                        + ("template" + File.separator + FRAMEWORK + File.separator).length(), ap.length());
                new File(BASE_PATH + File.separator + javaPath + File.separator + subAp).getParentFile().mkdirs();

                PrintWriter fwWriter = new PrintWriter(BASE_PATH + File.separator + javaPath + File.separator
                        + BASE_PACKAGE.replace('.', '/') + File.separator
                        + (subAp.endsWith(".vm") ? subAp.subSequence(0, subAp.indexOf(".vm")) + ".java" : subAp));
                Velocity.mergeTemplate(FRAMEWORK + "/" + relativePath, encoding, context, fwWriter);
                fwWriter.flush();
                fwWriter.close();

            }
        }
    }

    public static void createProject(String path, VelocityContext context) throws Exception {
        File prf = new File(path);
        File[] files = prf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jar")) {
                    return false;
                }
                return true;
            }
        });
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                String ap = files[i].getAbsolutePath();
                String relativePath = ap.substring(ap.indexOf("template" + File.separator + FRAMEWORK + File.separator)
                        + ("template" + File.separator + FRAMEWORK + File.separator).length(), ap.length());
                if ("src\\main\\java".equals(relativePath)) {
                    createJava(ap, context);
                    continue;
                }
                new File(BASE_PATH + File.separator + relativePath).mkdirs();
                createProject(files[i].getAbsolutePath(), context);
            } else {
                String ap = files[i].getAbsolutePath();
                String relativePath = ap.substring(ap.indexOf("template" + File.separator + FRAMEWORK + File.separator)
                        + ("template" + File.separator + FRAMEWORK + File.separator).length(), ap.length());
                new File(BASE_PATH + File.separator + relativePath).getParentFile().mkdirs();
                PrintWriter fwWriter = new PrintWriter(BASE_PATH + File.separator + relativePath);
                Velocity.mergeTemplate(FRAMEWORK + "/" + relativePath, encoding, context, fwWriter);
                fwWriter.flush();
                fwWriter.close();

            }
        }
    }

    /**
     *
     *
     * @param path
     * @param context
     * @throws Exception
     */
    public static void generateBaseJava(String path, VelocityContext context) throws Exception {
        File prf = new File(path);
        File[] files = prf.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jar")) {
                    return false;
                }
                return true;
            }
        });
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                String ap = files[i].getAbsolutePath();
                String relativePath = ap.substring(
                        ap.indexOf("template" + File.separator + "javaUtil" + File.separator)
                                + ("template" + File.separator + "javaUtil" + File.separator).length(), ap.length());
                new File(JAVA_BASE_PATH + File.separator + relativePath).mkdirs();
                generateBaseJava(files[i].getAbsolutePath(), context);
            } else {
                String ap = files[i].getAbsolutePath();
                String relativePath = ap.substring(
                        ap.indexOf("template" + File.separator + "javaUtil" + File.separator)
                                + ("template" + File.separator + "javaUtil" + File.separator).length(), ap.length());
                new File(JAVA_BASE_PATH + File.separator + relativePath).getParentFile().mkdirs();
                String javaRelativePath = relativePath.substring(0, relativePath.lastIndexOf("."));
                PrintWriter fwWriter = new PrintWriter(JAVA_BASE_PATH + javaRelativePath + ".java");
                Velocity.mergeTemplate("javaUtil/" + relativePath, encoding, context, fwWriter);
                fwWriter.flush();
                fwWriter.close();

            }
        }
    }

    public static void createIbatisServicePackage() {
        File daoPath = new File(JAVA_BASE_PATH + servicePackage.replace('.', '/'));
        if (!daoPath.exists()) {
            daoPath.mkdirs();
        }
        File ibatisDaoPath = new File(JAVA_BASE_PATH + "service/impl");
        if (!ibatisDaoPath.exists()) {
            ibatisDaoPath.mkdirs();
        }
    }

    public static void createIbatisActionPackage() {
        File dir = new File(JAVA_BASE_PATH + "controller");
        if (!dir.exists()) {
            dir.mkdirs();
        }

    }

    public static void createIbatisPoPackage() {
        File dir = new File(JAVA_BASE_PATH + pojoPackage.replace('.', '/'));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void createIbatisViewPackage(TableModel tableModel) {
        File dir = new File(ADMIN_VM_VIEW_BASE_PATH + StringUtils.uncapitalize(tableModel.getTableName()));
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void generateIbatisDao(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisDaoPackage();
        PrintWriter daoWriter = new PrintWriter(new File(JAVA_BASE_PATH + daoPackage.replace('.', '/')) + "/" + tableModel.getTableName()
                + "DAO.java");
        Velocity.mergeTemplate("java/ibatis_dao.vm", encoding, context, daoWriter);
        daoWriter.flush();
        daoWriter.close();
        // ------------
        PrintWriter ibatisDaoWriter = new PrintWriter(new File(JAVA_BASE_PATH + daoPackage.replace('.', '/')) + "/" + DAOIMPL_PACKAGE
                + "/" + tableModel.getTableName() + "DAOImpl.java");
        Velocity.mergeTemplate("java/ibatis_daoImpl.vm", encoding, context, ibatisDaoWriter);
        ibatisDaoWriter.flush();
        ibatisDaoWriter.close();
    }

    public static void generateMybatisDao(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisDaoPackage();
        PrintWriter daoWriter = new PrintWriter(new File(JAVA_BASE_PATH + daoPackage.replace('.', '/')) + "/"
                + tableModel.getTableName() + "Mapper.java");
        Velocity.mergeTemplate("javaMybatis/ibatis_dao.vm", encoding, context, daoWriter);
        daoWriter.flush();
        daoWriter.close();
    }

    public static void generateIbatisService(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisServicePackage();
        PrintWriter daoWriter = new PrintWriter(new File(JAVA_BASE_PATH + servicePackage.replace('.', '/')) + "/I"
                + tableModel.getTableName() + "Service.java");
        Velocity.mergeTemplate("java/ibatis_service.vm", encoding, context, daoWriter);
        daoWriter.flush();
        daoWriter.close();
        // ------------
        PrintWriter ibatisDaoWriter = new PrintWriter(new File(JAVA_BASE_PATH + servicePackage.replace('.', '/')) + "/impl/"
                + tableModel.getTableName() + "ServiceImpl.java");
        Velocity.mergeTemplate("java/ibatis_serviceImpl.vm", encoding, context, ibatisDaoWriter);
        daoWriter.flush();
        daoWriter.close();
    }

    public static void generateMybatisService(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisServicePackage();
        PrintWriter daoWriter = new PrintWriter(new File(JAVA_BASE_PATH + servicePackage.replace('.', '/')) + "/"
                + tableModel.getTableName() + "Service.java");
        Velocity.mergeTemplate("javaMybatis/ibatis_service.vm", encoding, context, daoWriter);
        daoWriter.flush();
        daoWriter.close();
        // ------------
        PrintWriter ibatisDaoWriter = new PrintWriter(new File(JAVA_BASE_PATH + servicePackage.replace('.', '/')) + "/impl/"
                + tableModel.getTableName() + "ServiceImpl.java");
        Velocity.mergeTemplate("javaMybatis/ibatis_serviceImpl.vm", encoding, context, ibatisDaoWriter);
        ibatisDaoWriter.flush();
        ibatisDaoWriter.close();
    }

    public static void generatePo(VelocityContext context, TableModel tableModel) throws ResourceNotFoundException,
            ParseErrorException, MethodInvocationException, Exception {
        createIbatisPoPackage();

        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + pojoPackage.replace('.', '/')) + "/" + tableModel.getTableName()
                + ".java");
        Velocity.mergeTemplate("java/ibatis_po.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateMybatisPo(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisPoPackage();

        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + pojoPackage.replace('.', '/')) + "/"
                + tableModel.getTableName() + ".java");
        Velocity.mergeTemplate("javaMybatis/ibatis_po.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateSpringAction(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisActionPackage();
        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + "web/action") + "/" + tableModel.getTableName()
                + ".java");
        Velocity.mergeTemplate("java/ibatis_spring_action.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateStrutsAction(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisActionPackage();
        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + "web/action") + "/" + tableModel.getTableName()
                + "Action.java");
        Velocity.mergeTemplate("java/ibatis_struts_action.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateMybatisStrutsAction(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisActionPackage();
        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + "web/action") + "/" + tableModel.getTableName()
                + "Action.java");
        Velocity.mergeTemplate("javaMybatis/ibatis_struts_action.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateMybatisSpringAction(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisActionPackage();
        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + "controller") + "/" + tableModel.getTableName()
                + "Controller.java");
        Velocity.mergeTemplate("javaMybatis/ibatis_spring_action.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateSqlMap(VelocityContext context, TableModel tableModel) throws ResourceNotFoundException,
            ParseErrorException, MethodInvocationException, Exception {
        new File(BASE_PATH + "/src/main/resources/map").mkdirs();
        PrintWriter writer = new PrintWriter(BASE_PATH + "/src/main/resources/map/"
                + StringUtils.uncapitalize(tableModel.getTableName()) + ".xml");
        Velocity.mergeTemplate("javaMybatis/ibatis_sqlMap.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateMybatisSqlMap(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        String path = BASE_PATH + "/src/main/resources/mapper";
        // new File(RESOURCES_BASE_PATH + daoPackage.replace('.', '/')).mkdirs();
        new File(path).mkdirs();
        // PrintWriter writer = new PrintWriter(new File(RESOURCES_BASE_PATH + daoPackage.replace('.', '/')) + "/"
        // + StringUtils.capitalize(tableModel.getTableName()) + "Mapper.xml");

        PrintWriter writer = new PrintWriter(new File(path) + "/" + StringUtils.capitalize(tableModel.getTableName())
                + "Mapper.xml");
        Velocity.mergeTemplate("javaMybatis/ibatis_sqlMap.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateSpringConfig(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisActionPackage();
        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + "web/action") + "/" + tableModel.getTableName()
                + ".java");
        Velocity.mergeTemplate("java/ibatis_struts_action.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateStrutsConfig(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisActionPackage();
        PrintWriter writer = new PrintWriter(new File(JAVA_BASE_PATH + "web/action") + "/" + tableModel.getTableName()
                + ".java");
        Velocity.mergeTemplate("java/ibatis_struts_action.vm", encoding, context, writer);
        writer.flush();
        writer.close();
    }

    public static void generateViewVm(VelocityContext context, TableModel tableModel) throws ResourceNotFoundException,
            ParseErrorException, MethodInvocationException, Exception {
        createIbatisViewPackage(tableModel);
        PrintWriter listWriter = new PrintWriter(ADMIN_VM_VIEW_BASE_PATH
                + StringUtils.uncapitalize(tableModel.getTableName()) + "/"
                + StringUtils.uncapitalize(tableModel.getTableName()) + "List.vm");
        Velocity.mergeTemplate("java/ibatis_list_vm.vm", encoding, context, listWriter);
        listWriter.flush();
        listWriter.close();
        // ------------
        PrintWriter editWriter = new PrintWriter(ADMIN_VM_VIEW_BASE_PATH
                + StringUtils.uncapitalize(tableModel.getTableName()) + "/"
                + StringUtils.uncapitalize(tableModel.getTableName()) + "Edit.vm");
        Velocity.mergeTemplate("java/ibatis_edit_vm.vm", encoding, context, editWriter);
        editWriter.flush();
        editWriter.close();
    }

    public static void generateMybatisViewVm(VelocityContext context, TableModel tableModel)
            throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        createIbatisViewPackage(tableModel);

        PrintWriter listWriter = new PrintWriter(ADMIN_VM_VIEW_BASE_PATH
                + StringUtils.uncapitalize(tableModel.getTableName()) + "/"
                + StringUtils.uncapitalize(tableModel.getTableName()) + "-list.jsp");
        Velocity.mergeTemplate("javaMybatis/ibatis_list_vm.vm", encoding, context, listWriter);
        listWriter.flush();
        listWriter.close();
        // ------------

        PrintWriter editWriter = new PrintWriter(ADMIN_VM_VIEW_BASE_PATH
                + StringUtils.uncapitalize(tableModel.getTableName()) + "/"
                + StringUtils.uncapitalize(tableModel.getTableName()) + "-edit.jsp");
        Velocity.mergeTemplate("javaMybatis/ibatis_edit_vm.vm", encoding, context, editWriter);
        editWriter.flush();
        editWriter.close();
    }
}
