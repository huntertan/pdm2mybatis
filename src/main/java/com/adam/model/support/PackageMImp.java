package com.adam.model.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adam.model.PackageM;
import com.adam.model.ReferenceM;
import com.adam.model.TableM;

public class PackageMImp extends BaseMImp
  implements PackageM
{
  List tables = new ArrayList();

  List references = new ArrayList();

  boolean isDefaultPackage = false;

  public PackageMImp() {
  }
  public PackageMImp(boolean defaultPackage) {
    setDefaultPackage(defaultPackage);
  }

  public List getTables() {
    return this.tables;
  }

  public void setTables(List tables) {
    this.tables = tables;
  }

  public List getReferences() {
    return this.references;
  }

  public void setReferences(List references) {
    this.references = references;
  }

  public void addReference(ReferenceM reference) {
    this.references.add(reference);
  }

  public void addTable(TableM table) {
    this.tables.add(table);
  }

  public TableM getTable(String id) {
    Iterator it = this.tables.iterator();
    while (it.hasNext()) {
      TableM t = (TableM)it.next();
      if (t.getId().equals(id))
        return t;
    }
    return null;
  }

  public TableM getTableByObjId(String objId) {
    Iterator it = this.tables.iterator();
    while (it.hasNext()) {
      TableM t = (TableM)it.next();
      if (t.getObjectId().equals(objId))
        return t;
    }
    return null;
  }

  public ReferenceM getReference(String id) {
    Iterator it = this.references.iterator();
    while (it.hasNext()) {
      ReferenceM refer = (ReferenceM)it.next();
      if (refer.getId().equals(id))
        return refer;
    }
    return null;
  }

  public ReferenceM getReferenceByObjId(String objId) {
    Iterator it = this.references.iterator();
    while (it.hasNext()) {
      ReferenceM refer = (ReferenceM)it.next();
      if (refer.getObjectId().equals(objId))
        return refer;
    }
    return null;
  }

  public boolean isDefaultPackage() {
    return this.isDefaultPackage;
  }

  public void setDefaultPackage(boolean defaultPackage) {
    this.isDefaultPackage = defaultPackage;
  }
}