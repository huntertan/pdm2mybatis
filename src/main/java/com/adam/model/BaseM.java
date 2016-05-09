package com.adam.model;

public abstract interface BaseM
{
  public abstract String getId();

  public abstract void setId(String paramString);

  public abstract String getName();

  public abstract void setName(String paramString);

  public abstract String getCode();

  public abstract void setCode(String paramString);

  public abstract void setObjectId(String paramString);

  public abstract String getObjectId();
  
  public abstract String getComment();
  
  public abstract void setComment(String comment);
}