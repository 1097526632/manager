package com.manage.system.gen.entity;

import com.manage.system.common.utils.StringUtils;

public class ColumnVo {
    private String columnName;
    private String comment;

    public ColumnVo(){

    }

    public ColumnVo(String columnName, String comment) {
        this.columnName = columnName;
        this.comment = comment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPropertyName() {
        return StringUtils.toCamelCase(columnName);
    }

}
