package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class Log
{
  private static Log instance = null;
  private static Log instanceFile = null;
  private Properties prop = new Properties();
  private String home = "";
  private PrintWriter outLog;
  private String logFile = "collection.log";
  private static HashMap instanceFiles = new HashMap();

  private int location = 1;

  public static synchronized Log getInstance()
  {
    if (instance == null) {
      instance = new Log();
    }

    return instance;
  }

  public static synchronized Log getInstance(String filename)
  {
    if (instanceFiles.get(filename) == null) {
      instanceFile = new Log(filename);
      instanceFiles.put(filename, instanceFile);
    } else {
      instanceFile = (Log)instanceFiles.get(filename);
    }
    return instanceFile;
  }

  private Log()
  {
    init();
  }

  private Log(String filename)
  {
    init(filename);
  }

  private void init()
  {
    try {
      InputStream in = getClass().getResourceAsStream("/home.properties");
      if (in != null) {
        this.prop.load(in);
      }
      this.logFile = (System.getProperty("SystemPath") + this.prop.getProperty("logfile", this.logFile));
      clearLog(true);
      this.location = Integer.parseInt(this.prop.getProperty("isout", "0"));
      this.outLog = new PrintWriter(new FileWriter(this.logFile, true), true);
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  private void init(String filename) {
    try {
      this.logFile = (System.getProperty("SystemPath") + filename);

      this.location = Integer.parseInt(this.prop.getProperty("isout", "1"));
      this.outLog = new PrintWriter(new FileWriter(this.logFile, true), true);
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void info(String info)
  {
    if (this.location != 0)
    {
      if (this.location == 1) {
        outLogln(info);
      }
      else if (this.location == 2)
        System.out.println(info);
    }
  }

  public void infoSpec(String info)
  {
    if (this.location != 0)
    {
      if (this.location == 1) {
        outLoglnSpec(info);
      }
      else if (this.location == 2)
        System.out.println(info);
    }
  }

  public void info(Object Class, String methodName, String info)
  {
    if (this.location != 0)
    {
      if (this.location == 1) {
        outLogln("类名：" + Class.getClass().getName() + "； 方法名：" + methodName + "； 日志信息：" + info);
      }
      else if (this.location == 2)
        System.out.println(getDatetime() + "  类名：" + Class.getClass().getName() + "； 方法名：" + methodName + "； 日志信息：" + info);
    }
  }

  public void error(Object Class, String methodName, String errorInfo)
  {
    if (this.location != 0)
    {
      if (this.location == 1) {
        outLogln(" 类名：" + Class.getClass().getName() + "； 方法名：" + methodName + "； 错误信息：" + errorInfo);
      }
      else if (this.location == 2)
        System.out.println(getDatetime() + "  类名：" + Class.getClass().getName() + "； 方法名：" + methodName + "； 错误信息：" + errorInfo);
    }
  }

  public void error(Object Class, String methodName, String errorInfo, Throwable t)
  {
    if (this.location != 0)
    {
      if (this.location == 1) {
        outLogln(t, "  类名：" + Class.getClass().getName() + "； 方法名：" + methodName + "； 错误信息：" + errorInfo);
      }
      else if (this.location == 2) {
        System.out.println(getDatetime() + " 类名：" + Class.getClass().getName() + "； 方法名：" + methodName + "； 错误信息：" + errorInfo);

        t.printStackTrace();
      }
    }
  }

  public void outLog(String msg)
  {
    clearLog(false);
    if (this.outLog != null)
      this.outLog.print(msg);
  }

  private String getDatetime()
  {
    Date rightNow = new Date();
    String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S").format(rightNow);

    return dateString;
  }

  public void outLogln()
  {
    clearLog(false);

    if (this.outLog != null)
      this.outLog.println("\n");
  }

  public void outLogln(String msg)
  {
    clearLog(false);
    if (this.outLog != null)
      this.outLog.println("时间：" + getDatetime() + "  " + msg);
  }

  public void outLoglnSpec(String msg)
  {
    clearLog(false);
    if (this.outLog != null)
      this.outLog.println(msg);
  }

  public void outLogln(Throwable e, String msg)
  {
    clearLog(false);

    if (this.outLog != null) {
      this.outLog.println("时间：" + getDatetime() + "  " + msg);
      e.printStackTrace(this.outLog);
      e.printStackTrace();
    }
  }

  public synchronized void clearLog(boolean clear)
  {
    if (this.logFile != null)
      try {
        File f = new File(this.logFile);
        long fSize = f.length();
        if ((fSize > 2097152L) || (clear)) {
          FileOutputStream file = new FileOutputStream(this.logFile);
          file.close();
        }
      }
      catch (FileNotFoundException ex) {
        System.out.println("can not clear logFile");
      }
      catch (IOException x) {
        System.out.println("can not clear logFile");
      }
  }

  public void setLocation(int location) {
    this.location = location;
  }
}