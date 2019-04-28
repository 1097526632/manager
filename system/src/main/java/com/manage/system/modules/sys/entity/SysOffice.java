package com.manage.system.modules.sys.entity;

import com.manage.system.modules.sys.entity.SystemBaseEntity;

/**
* 机构表实体
*/
public class SysOffice extends SystemBaseEntity {

    private String parentId;		//  父级编号
    private String parentIds;		//  所有父级编号
    private String name;		//  名称
    private String sort;		//  排序
    private String type;		//  机构类型
    private String address;		//  联系地址
    private String zipCode;		//  邮政编码
    private String useable;		//  是否启用
    private String isParent; // 是否为父节点

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }
}
