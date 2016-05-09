package com.template;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.adam.model.ColumnM;
import com.adam.model.KeyM;
import com.adam.model.TableM;
import com.util.Config;

public class PdmModelHandler {
    private static int WORLD_CASE = Integer.valueOf(Config.get("worldcase"));

    public static TableModel tableM2TableModel(TableM tableM) {
        TableModel tableModel = new TableModel();
        String tableName = "";
        String tableNameCN = "";
        String PK = "";
        String PKType = "";
        String PKJavaType = "";
        try {
            tableName = tableM.getCode();
            tableName = formatCode(tableName);// StringUtils.capitalize(tableName);
            tableNameCN = tableM.getName();

            PK = "";
            KeyM primaryKey = tableM.getPrimaryKey();
            if (primaryKey != null) {
                List primaryKeyColumnList = primaryKey.getColumns();
                for (int k = 0; k < primaryKeyColumnList.size(); k++) {
                    ColumnM columnP = (ColumnM) primaryKeyColumnList.get(k);

                    PK = columnP.getCode();
                    if (PK.length() < 3)
                        PK = PK.toLowerCase();
                    PKType = columnP.getDataType().toLowerCase();
                    PKJavaType = Config.getTypeConvert(PKType);
                }
            } else if (PK.equals("")) {
                PK = "noPK";
            }
            tableModel.setTableNameOriginal(tableM.getCode());
            tableModel.setTableName(tableName);
            tableModel.setTableNameCN(tableNameCN);
            tableModel.setPk(PK);
            tableModel.setFormatPK(StringUtils.uncapitalize(formatCode(PK)));
            tableModel.setPkType(PKType);
            tableModel.setPkJavaType(PKJavaType);
            tableModel.setPackageName(System.getProperty("packageName"));
            tableModel.setClassName(StringUtils.capitalize(tableName));
            tableModel.setFormName(tableName.toLowerCase());
            tableModel.setTableComment(tableM.getComment());
            tableModel.setTableM(tableM);
            tableModel.setColumnList(tableM.getColumns());
            col(tableModel, tableM);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return tableModel;
    }

    private static String formatCode(String code) {
        StringBuilder tnsb = new StringBuilder();
        String[] tns = code.split("_");
        for (int i = 0; i < tns.length; i++) {
            tnsb.append(StringUtils.capitalize(tns[i]));
        }
        return tnsb.toString();
    }

    private static void col(TableModel tableModel, TableM tableM) {
        try {
            List columnList = tableM.getColumns();
            String[] columnNames = new String[columnList.size()];
            String[] columnNameCN = new String[columnList.size()];
            String[] columnType = new String[columnList.size()];
            String[] columnSize = new String[columnList.size()];
            String[] isNullable = new String[columnList.size()];
            String[] columnNameOriginal = new String[columnList.size()];
            String[] columnComment = new String[columnList.size()];
            Set<String> javaImports = new HashSet<String>();
            String[] javaType = new String[columnList.size()];

            for (int k = 0; k < columnList.size(); k++) {
                ColumnM columnM = (ColumnM) columnList.get(k);
                String columnName = formatCode(columnM.getCode());
                if (columnName.length() < 3)
                    columnName = columnName.toLowerCase();
                else {
                    columnName = StringUtils.uncapitalize(columnName);
                }

                String type_name = columnM.getDataType().toLowerCase();
                String column_size = String.valueOf(columnM.getLength());
                String is_nullable;
                if (columnM.getMandatory() == 1)
                    is_nullable = "no";
                else {
                    is_nullable = "yes";
                }
                columnNameOriginal[k] = columnM.getCode();
                columnNames[k] = columnName;
                columnNameCN[k] = columnM.getName();
                columnType[k] = StringUtils.trimToEmpty(type_name);
                columnSize[k] = column_size;
                isNullable[k] = is_nullable;
                columnComment[k] = columnM.getComment();
                String javaImport = Config.getTypeConvert(columnType[k]);
                try {
                    if (!javaImport.contains(".lang.") && javaImport.contains(".")) {
                        javaImports.add(javaImport);
                    }
                    if (javaImport.contains(".")) {
                        javaType[k] = javaImport.substring(javaImport.lastIndexOf(".") + 1, javaImport.length());
                    } else {
                        javaType[k] = javaImport;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(tableModel.getTableName() + " " + columnName + " " + type_name + " "
                            + columnType[k]);
                }
            }
            tableModel.setColumnNameOriginal(columnNameOriginal);
            tableModel.setColumnName(columnNames);
            tableModel.setColumnNameCN(columnNameCN);
            tableModel.setColumnType(columnType);
            tableModel.setColumnSize(columnSize);
            tableModel.setIsNullable(isNullable);
            tableModel.setColumnComment(columnComment);
            tableModel.setJavaImport(javaImports);
            tableModel.setJavaType(javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
