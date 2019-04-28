package com.manage.system.modules.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SysMenu extends SystemBaseEntity {
    private String parentId;
    private String parentIds;
    private String name;
    private String sort;
    private String href;
    private String icon;
    private String isShow;
    private String permission;
    private int deep;

    private List<SysMenu> children;

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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }

    public static void treeMenu(List<SysMenu> list, List<SysMenu> sourceList,String rootId) {
        for (int i = 0; i < sourceList.size(); i++) {
            SysMenu e = sourceList.get(i);
            if (e.getChildren() != null && e.getChildren().size() > 0) {
                e.setChildren(new ArrayList<SysMenu>());
            }
        }
        for (int i = 0; i < sourceList.size(); i++) {
            SysMenu e = sourceList.get(i);
            for (int j = 0; j < sourceList.size(); j++) {
                SysMenu e1 = sourceList.get(j);
                if (e.getId().equalsIgnoreCase(e1.getParentId())) {
                    if("1".equals(e1.getIsShow())){
                        if(e.getChildren()==null){
                            e.setChildren(new ArrayList<SysMenu>());
                        }
                        e.getChildren().add(e1);
                    }

                }
            }
            if(rootId.equalsIgnoreCase(e.getParentId())){
                list.add(e);
            }
        }
    }
}
