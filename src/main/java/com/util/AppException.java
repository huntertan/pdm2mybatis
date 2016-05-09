package com.util;

public class AppException extends Exception
{
  private String msg = null;

  public AppException() {  }

  public AppException(String msg) { this.msg = msg; }

  public String getMsg()
  {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}