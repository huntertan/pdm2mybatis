package com.template;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.adam.model.ColumnM;
import com.adam.model.TableM;

/**
 * Description :
 * 
 * @author hanqing.tan
 */
public class TableModel {
    private String tableName;
    private String tableComment;
    private String tableNameOriginal;
    private String tableNameCN;
    private String pk;
    private String formatPK;
    private String pkType;
    private String pkJavaType;
    private String[] columnName;
    private String[] columnNameCN;
    private String[] columnType;
    private String[] javaType;
    private String[] columnSize;
    private String[] columnComment;
    private String[] columnNameOriginal;
    private String[] isNullable;
    private String className;
    private String packageName;
    private String formName;

    private Set<String> javaImport;
    private TableM tableM;
    private List<ColumnM> columnList;

    public TableM getTableM() {
        return tableM;
    }

    public void setTableM(TableM tableM) {
        this.tableM = tableM;
    }

    public List getColumnList() {
        return columnList;
    }

    public void setColumnList(List columnList) {
        this.columnList = columnList;
    }

    public String getPkJavaType() {
        return pkJavaType;
    }

    public void setPkJavaType(String pkJavaType) {
        this.pkJavaType = pkJavaType;
    }

    public Set<String> getJavaImport() {
        return javaImport;
    }

    public void setJavaImport(Set<String> javaImport) {
        this.javaImport = javaImport;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String[] getJavaType() {
        return javaType;
    }

    public void setJavaType(String[] javaType) {
        this.javaType = javaType;
    }

    public String[] getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String[] columnComment) {
        this.columnComment = columnComment;
    }

    public String[] getColumnNameOriginal() {
        return columnNameOriginal;
    }

    public void setColumnNameOriginal(String[] columnNameOriginal) {
        this.columnNameOriginal = columnNameOriginal;
    }

    public static void main(String[] args) {
        TableModel bean1 = new TableModel();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableNameOriginal() {
        return tableNameOriginal;
    }

    public void setTableNameOriginal(String tableNameOriginal) {
        this.tableNameOriginal = tableNameOriginal;
    }

    public String getTableNameCN() {
        return tableNameCN;
    }

    public void setTableNameCN(String tableNameCN) {
        this.tableNameCN = tableNameCN;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String[] getColumnName() {
        return columnName;
    }

    public void setColumnName(String[] columnName) {
        this.columnName = columnName;
    }

    public String[] getColumnNameCN() {
        return columnNameCN;
    }

    public void setColumnNameCN(String[] columnNameCN) {
        this.columnNameCN = columnNameCN;
    }

    public String[] getColumnType() {
        return columnType;
    }

    public void setColumnType(String[] columnType) {
        this.columnType = columnType;
    }

    public String[] getColumnSize() {
        return this.columnSize;
    }

    public void setColumnSize(String[] columnSize) {
        this.columnSize = columnSize;
    }

    public String[] getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String[] isNullable) {
        this.isNullable = isNullable;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormatPK() {
        return formatPK;
    }

    public void setFormatPK(String formatPK) {
        this.formatPK = formatPK;
    }

}
