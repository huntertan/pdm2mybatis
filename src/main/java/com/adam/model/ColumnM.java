package com.adam.model;

import com.adam.RefAble;

public abstract interface ColumnM extends BaseM, RefAble
{
  public abstract void setDataType(String paramString);

  public abstract String getDataType();

  public abstract int getLength();

  public abstract void setLength(int paramInt);

  public abstract void setMandatory(int paramInt);

  public abstract int getMandatory();

  public abstract void setPrimaryKey(boolean paramBoolean);

  public abstract boolean isPrimaryKey();

  public abstract boolean isNullAble();

  public abstract void setNullAble(boolean paramBoolean);

  public abstract void setTable(TableM paramTableM);

  public abstract TableM getTable();

  public abstract DomainM getDomain();

  public abstract void setDomain(DomainM paramDomainM);

  public abstract boolean hasDomain();
}