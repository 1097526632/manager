package com.manage.system.modules.sys.entity;

import com.manage.system.common.utils.IdGen;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.entity.BaseEntity;
import com.manage.system.core.entity.Page;
import com.manage.system.core.security.Principal;
import com.manage.system.core.security.SecurityUtils;

import java.util.Date;

public abstract class SystemBaseEntity extends BaseEntity {

    private String keyName;//关键字查询

    public SystemBaseEntity(){

    }

    public SystemBaseEntity(String id) {
        super(id);
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
