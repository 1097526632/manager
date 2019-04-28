package com.manage.system.modules.sys.entity;

import com.manage.system.modules.sys.entity.SystemBaseEntity;

/**
* 字典表实体
*/
public class SysDict extends SystemBaseEntity {

    private String value;		//  数据值
    private String label;		//  标签名
    private String type;		//  类型
    private String description;		//  描述
    private String sort;		//  排序（升序）
    private String parentId;		//  父级编号

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
