package com.adam.model;

import java.util.List;

import com.adam.RefAble;

public abstract interface TableM extends BaseM, RefAble
{
  public abstract void setPackage(PackageM paramPackageM);

  public abstract PackageM getPackage();

  public abstract void setReferences(List paramList);

  public abstract List getReferences();

  public abstract void addReference(ReferenceM paramReferenceM);

  public abstract void setColumns(List paramList);

  public abstract List<ColumnM> getColumns();

  public abstract void setKeys(List paramList);

  public abstract List getKeys();

  public abstract void addColumn(ColumnM paramColumnM);

  public abstract void addKey(KeyM paramKeyM);

  public abstract KeyM getPrimaryKey();

  public abstract List getAKeys();

  public abstract List getIdColumns();

  public abstract boolean isNullAble(ColumnM paramColumnM);

  public abstract boolean isNullAble(String paramString);

  public abstract boolean isPrimaryKey(ColumnM paramColumnM);

  public abstract boolean isPrimaryKey(String paramString);
}