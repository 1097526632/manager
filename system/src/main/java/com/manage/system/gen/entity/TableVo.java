package com.manage.system.gen.entity;

import java.util.List;

public class TableVo {
    private String tableName;
    private String tableComment;
    private List<ColumnVo> allColumn;
    private List<ColumnVo> propertyColumn;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnVo> getAllColumn() {
        return allColumn;
    }

    public void setAllColumn(List<ColumnVo> allColumn) {
        this.allColumn = allColumn;
    }

    public List<ColumnVo> getPropertyColumn() {
        return propertyColumn;
    }

    public void setPropertyColumn(List<ColumnVo> propertyColumn) {
        this.propertyColumn = propertyColumn;
    }
}
