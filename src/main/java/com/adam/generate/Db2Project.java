package com.adam.generate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.template.TableModel;

public class Db2Project {
    private static final Logger logger = LoggerFactory.getLogger(Db2Project.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/app-db.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
        String schema = "public";
        String table = "team";
        String tableCN = "团购信息";
        String primaryKey = "id";
        SqlRowSet sqlRowSet = jdbcTemplate
                .queryForRowSet("SELECT col.table_schema ,col.table_name ,col.ordinal_position,col.column_name ,col.data_type ,col.character_maximum_length,col.numeric_precision,col.numeric_scale,col.is_nullable,col.column_default ,      des.description  FROM      information_schema.columns col LEFT JOIN pg_description des ON col.table_name::regclass = des.objoid AND col.ordinal_position = des.objsubid  WHERE table_schema = '"
                        + schema + "' AND table_name = '" + table + "'  ORDER BY ordinal_position");
        TableModel tableModel = new TableModel();
        List<String> columnNames = new ArrayList<String>();
        List<String> columnNamesCN = new ArrayList<String>();
        List<String> columnTypes = new ArrayList<String>();
        List<String> javaTypes = new ArrayList<String>();

        List<String> columnSize = new ArrayList<String>();
        List<String> columnComment = new ArrayList<String>();
        List<String> columnNameOriginal = new ArrayList<String>();
        List<String> isNullable = new ArrayList<String>();
        tableModel.setTableNameOriginal(table);
        tableModel.setTableName(formatCode(table));
        tableModel.setTableNameCN(tableCN);
        tableModel.setPk(primaryKey);
        tableModel.setFormatPK(StringUtils.uncapitalize(formatCode(primaryKey)));
        // tableModel.setPkType(PKType);
        // tableModel.setPkJavaType(PKJavaType);
        tableModel.setPackageName(System.getProperty("packageName"));
        tableModel.setClassName(StringUtils.capitalize(table));
        tableModel.setFormName(table.toLowerCase());
        // tableModel.setTableComment(tableM.getComment());
        // tableModel.setTableM(tableM);
        // tableModel.setColumnList(tableM.getColumns());
        while (sqlRowSet.next()) {

            String columnName = sqlRowSet.getString("column_name");
            String dataType = sqlRowSet.getString("data_type");
            System.out.println(columnName + "" + dataType);
        }
    }

    private static String formatCode(String code) {
        StringBuilder tnsb = new StringBuilder();
        String[] tns = code.split("_");
        for (int i = 0; i < tns.length; i++) {
            tnsb.append(StringUtils.capitalize(tns[i]));
        }
        return tnsb.toString();
    }
}
