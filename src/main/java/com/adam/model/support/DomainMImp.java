package com.adam.model.support;

import com.adam.model.DomainM;

public class DomainMImp extends BaseMImp
  implements DomainM
{
  String comment = "";

  String dataType = "";

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getDataType() {
    return this.dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getRef() {
    throw new RuntimeException(" can not invoke ! ");
  }

  public void setRef(String ref) {
    throw new RuntimeException(" can not invoke ! ");
  }
}