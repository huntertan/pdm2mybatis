package com.adam;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adam.model.ColumnM;
import com.adam.model.DomainM;
import com.adam.model.KeyM;
import com.adam.model.PackageM;
import com.adam.model.ReferenceM;
import com.adam.model.TableM;

public class ParseHandler
{
  private static ParseHandler modelHandler = null;

  List packages = new ArrayList();

  List keys = new ArrayList();

  List columns = new ArrayList();

  List domains = new ArrayList();

  public static ParseHandler getInstance()
  {
    if (modelHandler == null)
      modelHandler = new ParseHandler();
    return modelHandler;
  }

  public void addDomain(DomainM domain)
  {
    this.domains.add(domain);
  }

  public DomainM getDomain(String id) {
    Iterator it = this.domains.iterator();
    while (it.hasNext()) {
      DomainM domain = (DomainM)it.next();
      if (Proxy.isProxyClass(domain.getClass())) {
        continue;
      }
      if (id.equals(domain.getId())) {
        return domain;
      }
    }
    return null;
  }

  public void addColumn(ColumnM column) {
    this.columns.add(column);
  }

  public ColumnM getColumn(String id) {
    Iterator it = this.columns.iterator();
    while (it.hasNext()) {
      ColumnM column = (ColumnM)it.next();
      if (Proxy.isProxyClass(column.getClass())) {
        continue;
      }
      if (id.equals(column.getId())) {
        return column;
      }
    }
    return null;
  }

  public List getPackages() {
    return this.packages;
  }

  public void addPackage(PackageM pack) {
    this.packages.add(pack);
  }

  public void addKey(KeyM key) {
    this.keys.add(key);
  }

  public PackageM getPackage(String id) {
    Iterator it = this.packages.iterator();
    while (it.hasNext()) {
      PackageM pack = (PackageM)it.next();
      if (pack.getId().equals(id))
        return pack;
    }
    return null;
  }

  public PackageM getPackageByObjId(String objId) {
    Iterator it = this.packages.iterator();
    while (it.hasNext()) {
      PackageM pack = (PackageM)it.next();
      if (pack.getObjectId().equals(objId))
        return pack;
    }
    return null;
  }

  public TableM getTable(String id) {
    Iterator it = this.packages.iterator();
    while (it.hasNext()) {
      PackageM pack = (PackageM)it.next();
      TableM t = pack.getTable(id);
      if ((t != null) && (!Proxy.isProxyClass(t.getClass())))
      {
        return t;
      }
    }
    return null;
  }

  public TableM getTableByObjId(String objId) {
    Iterator it = this.packages.iterator();
    while (it.hasNext()) {
      PackageM pack = (PackageM)it.next();
      TableM t = pack.getTableByObjId(objId);
      if (t != null)
        return t;
    }
    return null;
  }

  public ReferenceM getReference(String id) {
    Iterator it = this.packages.iterator();
    while (it.hasNext()) {
      PackageM pack = (PackageM)it.next();
      ReferenceM ref = pack.getReference(id);
      if (ref != null)
        return ref;
    }
    return null;
  }

  public ReferenceM getReferenceByObjId(String objId) {
    Iterator it = this.packages.iterator();
    while (it.hasNext()) {
      PackageM pack = (PackageM)it.next();
      ReferenceM ref = pack.getReferenceByObjId(objId);
      if (ref != null)
        return ref;
    }
    return null;
  }

  public KeyM getKey(String id) {
    Iterator ks = this.keys.iterator();
    while (ks.hasNext()) {
      KeyM k = (KeyM)ks.next();
      if (Proxy.isProxyClass(k.getClass())) {
        continue;
      }
      if (id.equals(k.getId())) {
        return k;
      }
    }
    return null;
  }
}