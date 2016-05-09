package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TypeConvert {
    private static TypeConvert instance = null;
    private Properties prop = new Properties();

    private TypeConvert() {
        init();
    }

    public static TypeConvert getInstance() {
        if (instance == null)
            instance = new TypeConvert();
        return instance;
    }

    public String convert(String str) {
        if (str != null) {
            int i = str.indexOf("(");
            if (i != -1)
                str = str.substring(0, i);
        }
        return this.prop.getProperty(str.toLowerCase(), "null");
    }

    public static void main(String[] args) {
        TypeConvert typeConvert1 = new TypeConvert();
    }

    private void init() {
        try {
            InputStream in = getClass().getResourceAsStream("/TypeConvert.properties");
            if (in != null)
                this.prop.load(in);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
