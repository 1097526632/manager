package com.manage.system.modules.sys.entity;

public class SysTreeEntity extends SystemBaseEntity {
    private String updateParentIds;
    private String updateParentNames;
    private String newParentIds;
    private String newParentNames;

    public String getUpdateParentIds() {
        return updateParentIds;
    }

    public void setUpdateParentIds(String updateParentIds) {
        this.updateParentIds = updateParentIds;
    }

    public String getUpdateParentNames() {
        return updateParentNames;
    }

    public void setUpdateParentNames(String updateParentNames) {
        this.updateParentNames = updateParentNames;
    }

    public String getNewParentIds() {
        return newParentIds;
    }

    public void setNewParentIds(String newParentIds) {
        this.newParentIds = newParentIds;
    }

    public String getNewParentNames() {
        return newParentNames;
    }

    public void setNewParentNames(String newParentNames) {
        this.newParentNames = newParentNames;
    }
}
