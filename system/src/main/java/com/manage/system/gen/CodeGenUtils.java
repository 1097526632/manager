package com.manage.system.gen;

import com.manage.system.common.utils.StringUtils;
import com.manage.system.common.utils.TemplateUtils;
import com.manage.system.gen.entity.ColumnVo;
import com.manage.system.gen.entity.TableVo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成
 */
public class CodeGenUtils {

    private static List<String> excludeColumnList=new ArrayList<String>();
    private static List<String> excludeTableList=new ArrayList<String>();
    private static String templates="/gen/tpl";
    private static String mapperPath="/config/mappers";
    private static String pagePath="/manager-system-ui/src/components/page";
    private static String modulePath="cms";
    // 不需要生成的列
    static{
        excludeColumnList.add("id");
        excludeColumnList.add("create_by");
        excludeColumnList.add("create_date");
        excludeColumnList.add("update_by");
        excludeColumnList.add("update_date");
        excludeColumnList.add("del_flag");
        excludeColumnList.add("remarks");
    }

    static String url="jdbc:mysql://localhost:3307/manage_cms?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8",
    username="root",
    password="";

    public static void main(String[] args) {
//        System.out.println("com.manage.system.modules".replaceAll("\\.","/"));
        converModel("com.manage.system.modules",getTable(getConnection(url,username,password),"cms%"));
    }

    private static void converModel(String basePackage,Map<String,TableVo> params){
        for(Map.Entry<String,TableVo> entry:params.entrySet()){
            String tableName=entry.getKey();
            TableVo tableVo=entry.getValue();
            Map<String,Object> model=new HashMap<String,Object>();
            model.put("tableName",tableVo.getTableName());
            model.put("tableComment",StringUtils.isNotBlank(tableVo.getTableComment())?tableVo.getTableComment():StringUtils.toCapitalizeCamelCase(tableName));
            model.put("columns",tableVo.getPropertyColumn());
            model.put("allColumns",tableVo.getAllColumn());
            model.put("module",modulePath);
            genFile(basePackage+"."+modulePath,model);
        }
    }

    private static void genFile(String basePackage,Map<String,Object> model){
        Map<String,String> tplFiles=new HashMap<String,String>();
        tplFiles.put("controller","controller.html");
        tplFiles.put("entity","entity.html");
        tplFiles.put("service","service.html");
        tplFiles.put("dao","dao.html");
        tplFiles.put("mapper","mapper.html");
        tplFiles.put("list","list.html");
        tplFiles.put("form","form.html");

        String classPath=CodeGenUtils.class.getResource("/").getPath();
        String packagePath=basePackage.replaceAll("\\.","/");
        for(Map.Entry<String,String> entry:tplFiles.entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            String tplPath=classPath+"/templates/"+templates+"/"+value;
            File file=new File(tplPath);
            if(file.exists()){
                String projectPath=new File(classPath).getParentFile().getParentFile().getPath()+"/src/main/";
                try {
                    String filePath=packagePath;
                    if("controller".equalsIgnoreCase(key)){
                        filePath=filePath+"/web/"+ StringUtils.toCapitalizeCamelCase(model.get("tableName").toString())+"Controller.java";
                    }else if("service".equalsIgnoreCase(key)){
                        filePath=filePath+"/service/"+ StringUtils.toCapitalizeCamelCase(model.get("tableName").toString())+"Service.java";
                    }else if("entity".equalsIgnoreCase(key)){
                        filePath=filePath+"/entity/"+ StringUtils.toCapitalizeCamelCase(model.get("tableName").toString())+".java";
                    }else if("dao".equalsIgnoreCase(key)){
                        filePath=filePath+"/dao/"+ StringUtils.toCapitalizeCamelCase(model.get("tableName").toString())+"Dao.java";
                    }else if("mapper".equalsIgnoreCase(key)){
                        filePath=mapperPath+"/"+modulePath+"/"+ StringUtils.toCapitalizeCamelCase(model.get("tableName").toString())+"Dao.xml";
                    }else if("list".equalsIgnoreCase(key)){
                        filePath=pagePath+"/"+modulePath+"/"+ StringUtils.toCamelCase(model.get("tableName").toString())+"/list.vue";
                    }else if("form".equalsIgnoreCase(key)){
                        filePath=pagePath+"/"+modulePath+"/"+ StringUtils.toCamelCase(model.get("tableName").toString())+"/form.vue";
                    }
                    model.put("package",basePackage);

                    String genFilePath="";

                    if("list".equalsIgnoreCase(key)||"form".equalsIgnoreCase(key)){
                        genFilePath=new File(classPath).getParentFile().getParentFile().getParentFile().getPath()+"/"+filePath;
                    } else if("mapper".equalsIgnoreCase(key)){
                        genFilePath=projectPath+"/resources/"+filePath;
                    }else {
                        genFilePath=projectPath+"/java/"+filePath;
                    }

                    File genFile=new File(genFilePath);
                    if(!genFile.getParentFile().exists()){
                        genFile.getParentFile().mkdirs();
                    }
                    FileOutputStream outputStream=new FileOutputStream(genFilePath);
                    TemplateUtils.getInstance().outToOutStream(templates+"/"+value,model,outputStream);
                    outputStream.close();
                    System.out.println("=========生成文件"+genFilePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    /**
     * 获取连接
     * @param url
     * @param username
     * @param password
     * @return
     */
    private static Connection getConnection(String url,String username,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void closeConnection(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, TableVo> getTable(Connection connection, String tableNames) {

        Map<String,TableVo> result=new HashMap<String,TableVo>();
        try {
            String[] tableNameArr=tableNames.split(",");
            for(String tableName:tableNameArr) {
                String sql="select a.*,b.TABLE_COMMENT from information_schema.`COLUMNS` a " +
                        "INNER JOIN information_schema.`TABLES` b on a.TABLE_SCHEMA=b.TABLE_SCHEMA " +
                        "AND a.TABLE_NAME=b.TABLE_NAME where a.TABLE_SCHEMA= database()" +
                        "              AND a.TABLE_NAME like '"+tableName+"' ORDER BY a.TABLE_NAME, a.ORDINAL_POSITION";
                Statement statement=connection.createStatement();
                ResultSet resultSet= statement.executeQuery(sql);
                if(resultSet!=null){
                    while(resultSet.next()){
                        String table= resultSet.getString("TABLE_NAME");//表名
                        String tableComment=resultSet.getString("TABLE_COMMENT");//
                        String columnName= resultSet.getString("COLUMN_NAME");//列名
                        String comment=resultSet.getString("COLUMN_COMMENT");
                        if(!excludeTableList.contains(table)){
                            TableVo tableVo= result.get(table);
                            if(tableVo==null){
                                tableVo=new TableVo();
                                tableVo.setTableName(table);
                                tableVo.setTableComment(tableComment);
                                result.put(table,tableVo);
                            }
                            if(!excludeColumnList.contains(columnName.toLowerCase())){
                                List<ColumnVo> list=tableVo.getPropertyColumn();
                                if(list==null){
                                    list=new ArrayList<ColumnVo>();
                                    tableVo.setPropertyColumn(list);
                                    ;                           }
                                list.add(new ColumnVo(columnName,comment));
                            }
                            List<ColumnVo> allColumn=tableVo.getAllColumn();
                            if(allColumn==null){
                                allColumn=new ArrayList<ColumnVo>();
                                tableVo.setAllColumn(allColumn);
                            }
                            allColumn.add(new ColumnVo(columnName,comment));
                        }
                    }
                    resultSet.close();
                }
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(connection);
        }
        return result;
    }

}
