package com.util;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class StringUtil {
    public static String splitBefore(String str) {
        int bracketIndex = str.indexOf("(");
        if (bracketIndex == -1)
            System.out.println("字符分割错误，不能找到()");
        return str.substring(0, bracketIndex);
    }

    public static String splitAfter(String str) {
        int bracketIndex = str.indexOf("(");
        if (bracketIndex == -1)
            System.out.println("字符分割错误，不能找到()");
        return str.substring(bracketIndex + 1, str.length() - 1);
    }

    public static String trimStr(String str) throws IOException {
        if ((str == null) || (str.equalsIgnoreCase("null")))
            return "";
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        int start = 0;
        int end = c.length;

        for (int i = 0; i < c.length; i++) {
            if ((c[i] > ' ') && (c[i] != '　')) {
                start = i;
                break;
            }
        }
        for (int i = c.length - 1; i >= 0; i--) {
            if ((c[i] > ' ') && (c[i] != '　')) {
                end = i;
                break;
            }
        }
        for (int i = start; (i <= end) && (i < c.length); i++) {
            if (c[i] >= ' ') {
                sb.append(c[i]);
            }
        }

        return sb.toString();
    }

    public static String trimALLStr(String str) throws IOException {
        if ((str == null) || (str.equalsIgnoreCase("null")))
            return "";
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (c[i] != ' ') {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    public static String change(String columnValue) {
        columnValue = columnValue.replaceAll("'", "");
        columnValue = columnValue.replaceAll("\"", "\\\\\"");
        columnValue = columnValue.replaceAll("%", "％");
        return columnValue;
    }

    public static boolean isNaN(String str) {
        if (str == null)
            return false;
        if (str.length() == 0)
            return false;
        boolean isnan = true;
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if ((c[i] < '0') || (c[i] > '9')) {
                if (c[i] != '.') {
                    isnan = false;
                    break;
                }
                if (c.length == 1) {
                    isnan = false;
                    break;
                }
            }
        }
        return isnan;
    }

    public static boolean isNaDate(String str) {
        if (str == null)
            return false;
        if (str.length() == 0)
            return false;
        if ((str == null) || (str.equalsIgnoreCase("null")))
            return false;
        boolean isnan = true;
        DateFormat df = DateFormat.getDateInstance();
        try {
            Date d = df.parse(str);
        } catch (ParseException e) {
            System.out.println("Unable to Date parse " + str);
            isnan = false;
        }
        return isnan;
    }

    public static String firstUpper(String str) throws Exception {
        if ((str == null) || (str.length() < 2))
            throw new Exception();
        String first = str.substring(0, 1);
        str = str.substring(1, str.length());
        str = first.toUpperCase() + str;

        return str;
    }

    public static String firstLower(String str) throws Exception {
        if ((str == null) || (str.length() < 2))
            throw new Exception();
        String first = str.substring(0, 1);
        str = str.substring(1, str.length());
        str = first.toLowerCase() + str;

        return str;
    }

    public static void test() {
        HashMap sh = new HashMap();
        sh.put("aa", "1");
        sh.put("aa", "2");
        System.out.println(sh.size());
        System.out.println(sh.get("aa"));
    }

    public static void main(String[] args) throws IOException {
        StringUtil stringUtil1 = new StringUtil();
        System.out.println(trimStr(" "));
    }
}
