package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static void main(String[] args) {
        DateUtil dateUtil1 = new DateUtil();
    }

    public static String getDate() {
        Date rightNow = new Date();
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rightNow);
        return dateString;
    }

    public static String getDate2() {
        Date rightNow = new Date();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(rightNow);
        return dateString;
    }
}