package com.manage.system.modules.sys.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.manage.system.core.entity.Page;
import com.manage.system.core.security.Principal;

import java.util.Date;
import java.util.List;

/**
 * 系统用户
 */
public class SysUser extends Principal {

    private String officeId;
    private String officeName;
    private String no;
    private String email;
    private String mobile;
    private String photo;
    private String personality;
    private String loginIp;
    private Date loginDate;
    private String loginFlag;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    private String delFlag;
    private String remarks;
    private Page page;

    private String keyName;

    private List<SysMenu> menuList; //菜单列表
    private List<String> btnPermission;// 按钮权限
    private List<String> pathPermission;// 路径权限


    public SysUser(){

    }
    public SysUser(String id){
        this.id=id;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @JSONField(serialize = false)
    public List<SysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
    }

    @JSONField(serialize = false)
    public List<String> getBtnPermission() {
        return btnPermission;
    }

    public void setBtnPermission(List<String> btnPermission) {
        this.btnPermission = btnPermission;
    }

    @JSONField(serialize = false)
    public List<String> getPathPermission() {
        return pathPermission;
    }

    public void setPathPermission(List<String> pathPermission) {
        this.pathPermission = pathPermission;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
}
