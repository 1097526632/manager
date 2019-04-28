package com.manage.system.modules.sys.entity;

import com.manage.system.modules.sys.entity.SystemBaseEntity;

/**
* 系统参数表实体
*/
public class SysParam extends SystemBaseEntity {

    private String code;		//  编码
    private String value;		//  值
    private String description;		//  描述
    private String type;		//  参数类型

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
