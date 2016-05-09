package com.adam.model;

import java.util.List;

import com.adam.RefAble;

public abstract interface KeyM extends BaseM, RefAble
{
  public abstract void setColumns(List paramList);

  public abstract List getColumns();

  public abstract void addColumn(ColumnM paramColumnM);

  public abstract void setType(KeyType paramKeyType);

  public abstract KeyType getType();

  public abstract boolean isPrimaryKey();

  public abstract boolean existColumn(String paramString);
}