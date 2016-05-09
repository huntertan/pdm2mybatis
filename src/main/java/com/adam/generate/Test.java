package com.adam.generate;

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * 描述 :
 * 
 * <pre>
 * +--------------------------------------------------------------------
 * 更改历史
 * 更改时间		 更改人		目标版本		更改内容
 * +--------------------------------------------------------------------
 * 2012-8-15       hanqing.tan 		1.00	 	创建
 * </pre>
 * 
 * @author hanqing.tan
 */
public class Test {
    private static final Log log = LogFactory.getLog(Test.class);
    private static Properties props = new Properties();

    static {
        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");

        props.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        props.setProperty(Velocity.RESOURCE_LOADER, "class");
        props.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    }

    /**
     * 测试字符串模板替换
     */
    private static void testStringVelocity() {
        // 初始化并取得Velocity引擎
        VelocityEngine engine = new VelocityEngine(props);
        // 字符串模版
        String template = "${owner}：您的${type} : ${bill} 在  ${date} 日已支付成功";
        // 取得velocity的上下文context
        VelocityContext context = new VelocityContext();
        // 把数据填入上下文
        context.put("owner", "nassir");
        context.put("bill", "201203221000029763");
        context.put("type", "订单");
        context.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", template);
        System.out.println(writer.toString());

    }

    /**
     * 测试模板静态方法使用
     */
    private static void testCommonsStringUtils() {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        VelocityContext ctx = new VelocityContext();
        ctx.put("stringUtils", new StringUtils());
        ctx.put("comments", "this is a \n newline test");
        ctx.put("newline", "\n");
        ctx.put("break", "<br/>");
        String template = "#set($comments = $stringUtils.replace($comments,$newline,$break))";
        template += "$comments";
        StringWriter writer = new StringWriter();
        engine.evaluate(ctx, writer, "", template);
        System.out.println(writer.toString());
        // assertEquals("this is a <br /> newline test", writer.toString());
    }

    private static void testLoop() {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        VelocityContext ctx = new VelocityContext();
        // ctx.put("stringUtils", new StringUtils());
        // ctx.put("comments", "this is a \n newline test");
        // ctx.put("newline", "\n");
        // ctx.put("break", "<br/>");
        String template = "#set($pageStart=0)#set($pageEnd=750)#set($step=10)"
                + "#foreach($page in [$pageStart..$pageEnd])" + "#if($foreach.index%$step==0)"
                + "http://baike.baidu.com/wiki_1-0/list-php/dispose/taglist.php?tag=%C2%C3%D3%CE&offset=$page"
                + "\n#set($page=$step+$page)#end" + "#end";
        StringWriter writer = new StringWriter();
        engine.evaluate(ctx, writer, "", template);
        System.out.println(writer.toString());
        // assertEquals("this is a <br /> newline test", writer.toString());
    }

    /**
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {
        // testStringVelocity();
        // testCommonsStringUtils();
        // testLoop();
//        System.out.println("http://meishi.qq.com/beijing/s/d110104-p50".replaceFirst("-p(\\d+)$", "\\${page}"));
//        URL u = new URL("http://www.baidu.com//");
//        System.out.println(u.toString());
//        Pattern p = Pattern.compile("-p(\\d+)$");
//        String s="http://meishi.qq.com/beijing/s/d110104-p50";
//        Matcher m = p.matcher(s);
//        while (m.find()) {
//            try {
//                int start=m.start(1);
//                int end=m.end(1);
//                System.out.println(m.group(1));
//                System.out.println(s.substring(0, start)+"${page}"+s.substring(end, s.length()));
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }
//        }
        String s="jar:file:/C:/Users/zhiqi.zhou.SERVERS/Desktop/tt.jar!/com/adam/generate/";
        System.out.println(s.substring(0,s.indexOf("!"))+"!/");
    }
}
