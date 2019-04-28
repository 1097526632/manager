package com.manage.system.modules.sys.entity;

import com.manage.system.modules.sys.entity.SystemBaseEntity;

import java.util.List;

/**
* 角色表实体
*/
public class SysRole extends SystemBaseEntity {

    private String name;		//  角色名称
    private String dataScope;		//  数据范围
    private String isSys;		//  是否系统数据
    private String useable;		//  是否可用
    private List<SysMenu> menuDatas;
    private List<SysOffice> officeDatas;
    private List<SysUser> roleUsers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }
    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }
    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public List<SysMenu> getMenuDatas() {
        return menuDatas;
    }

    public void setMenuDatas(List<SysMenu> menuDatas) {
        this.menuDatas = menuDatas;
    }

    public List<SysOffice> getOfficeDatas() {
        return officeDatas;
    }

    public void setOfficeDatas(List<SysOffice> officeDatas) {
        this.officeDatas = officeDatas;
    }

    public List<SysUser> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(List<SysUser> roleUsers) {
        this.roleUsers = roleUsers;
    }
}
