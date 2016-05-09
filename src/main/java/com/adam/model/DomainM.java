package com.adam.model;

import com.adam.RefAble;

public abstract interface DomainM extends BaseM, RefAble
{
  public abstract void setComment(String paramString);

  public abstract String getComment();

  public abstract String getDataType();

  public abstract void setDataType(String paramString);
}