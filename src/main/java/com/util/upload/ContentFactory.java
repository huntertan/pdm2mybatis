package com.util.upload;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

public class ContentFactory
{
  private Hashtable values;
  private Hashtable files;

  public ContentFactory()
  {
  }

  private ContentFactory(Hashtable values, Hashtable files)
  {
    this.values = values;
    this.files = files;
  }

  public String getParameter(String name)
  {
    Vector v = (Vector)this.values.get(name);
    if (v != null)
      return (String)v.elementAt(0);
    return null;
  }

  public Enumeration getParameterNames()
  {
    return this.values.keys();
  }

  public String[] getParameterValues(String name)
  {
    Vector v = (Vector)this.values.get(name);
    if (v != null)
    {
      String[] result = new String[v.size()];
      for (int i = 0; i < v.size(); i++) {
        result[i] = ((String)v.elementAt(i));
      }
      return result;
    }

    return new String[0];
  }

  public FileHolder getFileParameter(String name)
  {
    Vector v = (Vector)this.files.get(name);
    if (v != null)
      return (FileHolder)v.elementAt(0);
    return null;
  }

  public Enumeration getFileParameterNames()
  {
    return this.files.keys();
  }

  public FileHolder[] getFileParameterValues(String name)
  {
    Vector v = (Vector)this.files.get(name);
    if (v != null)
    {
      FileHolder[] result = new FileHolder[v.size()];
      for (int i = 0; i < v.size(); i++) {
        result[i] = ((FileHolder)v.elementAt(i));
      }
      return result;
    }
    return new FileHolder[0];
  }

  public static ContentFactory getContentFactory(HttpServletRequest request)
    throws ContentFactoryException, IOException
  {
    return getContentFactory(request, 1048576);
  }

  public static ContentFactory getContentFactory(HttpServletRequest request, int maxLength)
    throws ContentFactoryException, IOException
  {
    Hashtable values = new Hashtable();
    Hashtable files = new Hashtable();
    String contentType = request.getContentType();
    int contentLength = request.getContentLength();

    if (contentLength > maxLength)
    {
      ContentFactoryException e = new ContentFactoryException("上传数据太多，请不要选择太大的文件");
      throw e;
    }
    if ((contentType == null) || (!contentType.startsWith("multipart/form-data"))) {
      return null;
    }

    int start = contentType.indexOf("boundary=");

    int boundaryLen = new String("boundary=").length();
    String boundary = contentType.substring(start + boundaryLen);
    boundary = "--" + boundary;

    boundaryLen = bytesLen(boundary);

    byte[] buffer = new byte[contentLength];
    int once = 0;
    int total = 0;
    DataInputStream in = new DataInputStream(request.getInputStream());
    while ((total < contentLength) && (once >= 0)) {
      once = in.read(buffer, total, contentLength);
      total += once;
    }

    int pos1 = 0;

    int pos0 = byteIndexOf(buffer, boundary, 0);
    while (true)
    {
      pos0 += boundaryLen;
      pos1 = byteIndexOf(buffer, boundary, pos0);
      if (pos1 == -1) {
        break;
      }
      pos0 += 2;

      parse(subBytes(buffer, pos0, pos1 - 2), values, files);
      pos0 = pos1;
    }
    return new ContentFactory(values, files);
  }

  private static void parse(byte[] buffer, Hashtable values, Hashtable files)
  {
    String[] tokens = { "name=\"", "\"; filename=\"", "\"\r\n", "Content-Type: ", "\r\n\r\n" };

    int[] position = new int[tokens.length];

    for (int i = 0; i < tokens.length; i++)
    {
      position[i] = byteIndexOf(buffer, tokens[i], 0);
    }
    if ((position[1] > 0) && (position[1] < position[2]))
    {
      String name = subBytesString(buffer, position[0] + bytesLen(tokens[0]), position[1]);

      String file = subBytesString(buffer, position[1] + bytesLen(tokens[1]), position[2]);
      if (file.equals("")) return;
      file = new File(file).getName();

      String contentType = subBytesString(buffer, position[3] + bytesLen(tokens[3]), position[4]);

      byte[] b = subBytes(buffer, position[4] + bytesLen(tokens[4]), buffer.length);
      FileHolder f = new FileHolder(b, contentType, file, name);
      Vector v = (Vector)files.get(name);
      if (v == null)
      {
        v = new Vector();
      }
      if (!v.contains(f))
      {
        v.addElement(f);
      }
      files.put(name, v);

      v = (Vector)values.get(name);
      if (v == null)
      {
        v = new Vector();
      }
      if (!v.contains(file))
      {
        v.addElement(file);
      }
      values.put(name, v);
    }
    else
    {
      String name = subBytesString(buffer, position[0] + bytesLen(tokens[0]), position[2]);
      String value = subBytesString(buffer, position[4] + bytesLen(tokens[4]), buffer.length);
      Vector v = (Vector)values.get(name);
      if (v == null)
      {
        v = new Vector();
      }
      if (!v.contains(value))
      {
        v.addElement(value);
      }
      values.put(name, v);
    }
  }

  private static int byteIndexOf(byte[] source, String search, int start)
  {
    return byteIndexOf(source, search.getBytes(), start);
  }

  private static int byteIndexOf(byte[] source, byte[] search, int start)
  {
    if (search.length == 0)
    {
      return 0;
    }
    int max = source.length - search.length;
    if (max < 0)
      return -1;
    if (start > max)
      return -1;
    if (start < 0) {
      start = 0;
    }

    for (int i = start; i <= max; i++)
    {
      if (source[i] != search[0]) {
        continue;
      }
      int k = 1;

      while (source[(k + i)] == search[k])
      {
        k++;

        if (k >= search.length)
        {
          return i;
        }
      }
    }
    return -1;
  }

  private static byte[] subBytes(byte[] source, int from, int end)
  {
    byte[] result = new byte[end - from];
    System.arraycopy(source, from, result, 0, end - from);
    return result;
  }

  private static String subBytesString(byte[] source, int from, int end)
  {
    return new String(subBytes(source, from, end));
  }

  private static int bytesLen(String s)
  {
    return s.getBytes().length;
  }
}