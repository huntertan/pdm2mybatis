package com.adam.model;

import java.util.List;

public abstract interface PackageM extends BaseM
{
  public abstract void setTables(List paramList);

  public abstract List  getTables();

  public abstract void setReferences(List paramList);

  public abstract List getReferences();

  public abstract void addReference(ReferenceM paramReferenceM);

  public abstract void addTable(TableM paramTableM);

  public abstract TableM getTable(String paramString);

  public abstract TableM getTableByObjId(String paramString);

  public abstract ReferenceM getReference(String paramString);

  public abstract ReferenceM getReferenceByObjId(String paramString);

  public abstract boolean isDefaultPackage();

  public abstract void setDefaultPackage(boolean paramBoolean);
}