package com.adam.model;

import java.util.List;

public abstract interface ReferenceM extends BaseM {
    public abstract TableM getLeft();

    public abstract void setLeft(TableM paramTableM);

    public abstract TableM getRight();

    public abstract void setRight(TableM paramTableM);

    public abstract TableM getRelationTable();

    public abstract void setParentKey(KeyM paramKeyM);

    public abstract KeyM getParentKey();

    public abstract String getConstraintName();

    public abstract void setConstraintName(String paramString);

    public abstract String getReferenceType();

    public abstract void setReferenceType(String paramString);

    public abstract List getJoins();

    public abstract void setJoins(List paramList);

    public abstract void addJoin(JoinM paramJoinM);
}