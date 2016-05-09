package com.adam.model;

public abstract interface JoinM
{
  public abstract void setObjectId(String paramString);

  public abstract String getObjectId();

  public abstract ColumnM getLeft();

  public abstract void setLeft(ColumnM paramColumnM);

  public abstract ColumnM getRight();

  public abstract void setRight(ColumnM paramColumnM);

  public abstract void setLeftTable(TableM paramTableM);

  public abstract TableM getLeftTable();

  public abstract void setRightTable(TableM paramTableM);

  public abstract TableM getRightTable();
}