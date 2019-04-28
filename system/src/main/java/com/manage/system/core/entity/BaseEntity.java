package com.manage.system.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.manage.system.common.utils.DateUtils;
import com.manage.system.common.utils.IdGen;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.security.Principal;
import com.manage.system.core.security.SecurityUtils;

import java.util.Date;

public abstract class BaseEntity {
    protected String id;
    protected String idPre;// id前缀

    public BaseEntity(){

    }
    public BaseEntity(String id){
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String createBy;
    private String createUserName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private String updateBy;
    private String updateUserName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    private String delFlag;
    private String remarks;
    private String queryBeginDate;
    private String queryEndDate;
    private Page page;
    private boolean isNewRecord=false;

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

    public void preInsert() {
        if(!isNewRecord){
            id=(StringUtils.isNotBlank(idPre)?idPre: "")+ IdGen.uuid();
        }
        Principal principal= SecurityUtils.getSubject().getPrincipal();
        if(principal!=null){
            createBy= SecurityUtils.getSubject().getPrincipal().getId();
            updateBy=createBy;
        }
        if(StringUtils.isBlank(delFlag)){
            delFlag="0";
        }
        createDate=new Date();
        updateDate=new Date();
    }

    public void preUpdate() {
        Principal principal= SecurityUtils.getSubject().getPrincipal();
        if(principal!=null){
            updateBy=SecurityUtils.getSubject().getPrincipal().getId();
        }
        if(StringUtils.isBlank(delFlag)){
            delFlag="0";
        }
        updateDate=new Date();
    }

    public boolean getIsNewRecord() {
        if(isNewRecord|| StringUtils.isBlank(id)){
            return true;
        }
        return false;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public String getQueryBeginDate() {
        return DateUtils.formateQueryDate(queryBeginDate);
    }

    public void setQueryBeginDate(String queryBeginDate) {
        this.queryBeginDate = queryBeginDate;
    }

    public String getQueryEndDate() {
        return DateUtils.formateQueryDate(queryEndDate);
    }

    public void setQueryEndDate(String queryEndDate) {
        this.queryEndDate = queryEndDate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getIdPre() {
        return idPre;
    }

    public void setIdPre(String idPre) {
        this.idPre = idPre;
    }
}
