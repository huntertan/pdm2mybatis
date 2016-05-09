package com.adam.model.support;

import com.adam.model.ColumnM;
import com.adam.model.DomainM;
import com.adam.model.TableM;

public class ColumnMImp extends BaseMImp implements ColumnM {
    String dataType = "";

    int length = 0;

    int mandatory = 0;

    boolean isPrimaryKey = false;

    boolean nullAble = true;

    TableM table = null;

    DomainM domain = null;

    public DomainM getDomain() {
        return this.domain;
    }

    public void setDomain(DomainM domain) {
        this.domain = domain;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(int mandatory) {
        this.mandatory = mandatory;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRef() {
        throw new RuntimeException(" can not invoke ! ");
    }

    public void setRef(String ref) {
        throw new RuntimeException(" can not invoke ! ");
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey();
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public boolean isNullAble() {
        return this.nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public TableM getTable() {
        return this.table;
    }

    public void setTable(TableM table) {
        this.table = table;
    }

    public boolean hasDomain() {
        return this.domain != null;
    }
}
