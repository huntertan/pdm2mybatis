package com.util.upload;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileHolder
{
  String contentType;
  byte[] buffer;
  String fileName;
  String parameterName;

  FileHolder()
  {
  }

  FileHolder(byte[] buffer, String contentType, String fileName, String parameterName)
  {
    this.buffer = buffer;
    this.contentType = contentType;
    this.fileName = fileName;
    this.parameterName = parameterName;
  }

  public void saveTo(File file)
    throws IOException
  {
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
    out.write(this.buffer);
    out.close();
  }

  public void saveTo(String name)
    throws IOException
  {
    saveTo(new File(name));
  }

  public byte[] getBytes()
  {
    return this.buffer;
  }

  public InputStream getInputStream()
  {
    InputStream is = new ByteArrayInputStream(this.buffer);
    return is;
  }

  public int getSize()
  {
    return this.buffer.length;
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public String getContentType()
  {
    return this.contentType;
  }

  public String getParameterName()
  {
    return this.parameterName;
  }

  public String getFileExt()
  {
    String value = new String();
    int start = 0;
    int end = 0;
    if (this.fileName == null)
      return null;
    start = this.fileName.lastIndexOf('.') + 1;
    end = this.fileName.length();
    value = this.fileName.substring(start, end);
    if (this.fileName.lastIndexOf('.') > 0) {
      return value;
    }
    return "";
  }
}