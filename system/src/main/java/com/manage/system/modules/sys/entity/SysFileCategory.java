package com.manage.system.modules.sys.entity;

import com.manage.system.modules.sys.entity.SystemBaseEntity;

/**
* SysFileCategory实体
*/
public class SysFileCategory extends SysTreeEntity {

    private String parentId;		//  父ID
    private String parentIds;		//  所有父ID
    private String name;		//  名称
    private String parentNames;		//  所有父名称

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
    public String getParentNames() {
        return parentNames;
    }

    public void setParentNames(String parentNames) {
        this.parentNames = parentNames;
    }
}
