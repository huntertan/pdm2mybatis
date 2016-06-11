package com.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Description :
 * 
 * @author hanqing.tan
 */
public class Config {
    private static final Log log = LogFactory.getLog(Config.class);
    private static Configuration config;
    private static PropertiesConfiguration typeConvert;

    static {
        try {
            String path = Config.class.getResource("/").getPath();
            path = path.replaceFirst("/","");
            path = "";
            config = new PropertiesConfiguration(path+"conf.properties");

            typeConvert = new PropertiesConfiguration();
            typeConvert.setIOFactory(new WhitespaceIOFactory());
            typeConvert.setFile(new File(path+"typeConvert.properties"));
            typeConvert.load();

            // typeConvert = new PropertiesConfiguration("typeConvert.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static String getTypeConvert(String key) {
        return typeConvert.getString(key);
    }

    public static String get(String key) {
        try {
            return config.getString(key);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> getConfig() {
        Map<String, String> map = new HashMap<String, String>();
        for (Iterator<String> iterator = config.getKeys(); iterator.hasNext();) {
            String key = (String) iterator.next();
            map.put(key.replaceAll("\\.", "_"), config.getString(key));
        }
        return map;
    }

    public static void main(String[] args) {
        String path = Config.class.getClass().getResource("").getPath();
        System.out.println(path);
    }
}
