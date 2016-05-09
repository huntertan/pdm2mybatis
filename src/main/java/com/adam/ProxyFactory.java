package com.adam;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyFactory
{
  private static ProxyFactory factory = null;

  public static ProxyFactory getInstance()
  {
    if (factory == null) {
      factory = new ProxyFactory();
    }
    return factory;
  }

  public Object getProxy(Class clas)
  {
    Class[] interfaces = clas.getInterfaces();
    InvocationHandler handler = new ModelInvHandler();
    return Proxy.newProxyInstance(clas.getClassLoader(), interfaces, handler);
  }
}