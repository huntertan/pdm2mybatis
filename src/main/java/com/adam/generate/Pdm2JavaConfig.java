package com.adam.generate;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 描述 :
 * 
 * <pre>
 * +--------------------------------------------------------------------
 * 更改历史
 * 更改时间		 更改人		目标版本		更改内容
 * +--------------------------------------------------------------------
 * 2012-10-24       hanqing.tan 		1.00	 	创建
 * </pre>
 * 
 * @author hanqing.tan
 */
public class Pdm2JavaConfig implements Serializable {
    private static final Log log = LogFactory.getLog(Pdm2JavaConfig.class);
    private String projectNameZh;
    private String projectNameEn;
    private String basePackage = "com.wireless.platform";
    private String encoding = "UTF-8";
    private Integer framework;
    private String projectOutputPath;
    private String pdmPath;
    private String adminPathName;
    private String pojoPackage = "po";
    private String servicePackage = "service";
    private String daoPackage = "dao";
    private Integer worldcase = 2;

    public String getProjectNameZh() {
        return projectNameZh;
    }

    public String getAdminPathName() {
        return adminPathName;
    }

    public void setAdminPathName(String adminPathName) {
        this.adminPathName = adminPathName;
    }

    public void setProjectNameZh(String projectNameZh) {
        this.projectNameZh = projectNameZh;
    }

    public String getProjectNameEn() {
        return projectNameEn;
    }

    public void setProjectNameEn(String projectNameEn) {
        this.projectNameEn = projectNameEn;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getFramework() {
        return framework;
    }

    public void setFramework(Integer framework) {
        this.framework = framework;
    }

    public String getProjectOutputPath() {
        return projectOutputPath;
    }

    public void setProjectOutputPath(String projectOutputPath) {
        this.projectOutputPath = projectOutputPath;
    }

    public String getPdmPath() {
        return pdmPath;
    }

    public void setPdmPath(String pdmPath) {
        this.pdmPath = pdmPath;
    }

    public String getPojoPackage() {
        return pojoPackage;
    }

    public void setPojoPackage(String pojoPackage) {
        this.pojoPackage = pojoPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public Integer getWorldcase() {
        return worldcase;
    }

    public void setWorldcase(Integer worldcase) {
        this.worldcase = worldcase;
    }

    @Override
    public String toString() {
        return "Pdm2JavaConfig [projectNameZh=" + projectNameZh + ", projectNameEn=" + projectNameEn + ", basePackage="
                + basePackage + ", encoding=" + encoding + ", framework=" + framework + ", projectOutputPath="
                + projectOutputPath + ", pdmPath=" + pdmPath + ", pojoPackage=" + pojoPackage + ", servicePackage="
                + servicePackage + ", daoPackage=" + daoPackage + ", worldcase=" + worldcase + "]";
    }

}
