package com.adam.model.support;

import com.adam.model.ColumnM;
import com.adam.model.JoinM;
import com.adam.model.TableM;

public class JoinMImp
  implements JoinM
{
  String objectId = "";

  ColumnM left = null;

  ColumnM right = null;

  TableM leftTable = null;

  TableM rightTable = null;

  public String getObjectId() {
    return this.objectId;
  }

  public TableM getLeftTable() {
    return this.leftTable;
  }

  public void setLeftTable(TableM leftTable) {
    this.leftTable = leftTable;
  }

  public TableM getRightTable() {
    return this.rightTable;
  }

  public void setRightTable(TableM rightTable) {
    this.rightTable = rightTable;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public ColumnM getLeft() {
    return this.left;
  }

  public void setLeft(ColumnM left) {
    this.left = left;
  }

  public ColumnM getRight() {
    return this.right;
  }

  public void setRight(ColumnM right) {
    this.right = right;
  }
}