package com.adam.model.support;

import java.util.ArrayList;
import java.util.List;

import com.adam.model.JoinM;
import com.adam.model.KeyM;
import com.adam.model.ReferenceM;
import com.adam.model.TableM;

public class ReferenceMImp extends BaseMImp
  implements ReferenceM
{
  TableM left = null;

  TableM right = null;

  KeyM parentKey = null;

  List joins = new ArrayList();

  String constraintName = "";

  String referenceType = "";

  public String getReferenceType() {
    return this.referenceType;
  }

  public void setReferenceType(String referenceType) {
    this.referenceType = referenceType;
  }

  public String getConstraintName() {
    return this.constraintName;
  }

  public void setConstraintName(String constraintName) {
    this.constraintName = constraintName;
  }

  public TableM getLeft() {
    return this.left;
  }

  public void setLeft(TableM left) {
    this.left = left;
  }

  public TableM getRight() {
    return this.right;
  }

  public void setRight(TableM right) {
    this.right = right;
  }

  public KeyM getParentKey() {
    return this.parentKey;
  }

  public void setParentKey(KeyM parentKey) {
    this.parentKey = parentKey;
  }

  public List getJoins() {
    return this.joins;
  }

  public void setJoins(List joins) {
    this.joins = joins;
  }

  public void addJoin(JoinM join) {
    this.joins.add(join);
  }

  public TableM getRelationTable() {
    return this.right;
  }
}