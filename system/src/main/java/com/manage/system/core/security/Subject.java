package com.manage.system.core.security;

import com.manage.system.common.utils.DateUtils;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.config.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class Subject {

    private Principal principal;
    private String subjectId;
    private Date updateDate;

    public static Subject getSubject(HttpServletRequest request){
        HttpSession session=request.getSession();

        SecurityCacheManage securityCacheManage=SecurityCacheManage.getInstance();
        Subject subject=securityCacheManage.getSubject(session.getId());
        if(subject==null){
            subject=new Subject();
            subject.setSubjectId(session.getId());
            securityCacheManage.putSubject(session.getId(),subject);
        }
        subject.setUpdateDate(new Date());
        return subject;
    }

    public static Subject getSubject(String subjectId){
        return SecurityCacheManage.getInstance().getSubject(subjectId);
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isAuth(){
        return principal!=null&&StringUtils.isNotBlank(principal.getId());
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 判断subject是否超时
     * @return
     */
    public boolean isTimeout() {
        if(updateDate!=null){
            return DateUtils.diffSecond(new Date(),this.updateDate)>=(Constants.timeout)*60*1000;
        }
        return false;
    }

    public void saveSubject(){
        SecurityCacheManage securityCacheManage=SecurityCacheManage.getInstance();
        securityCacheManage.putSubject(subjectId,this);
    }

    public void removePrincipal() {
        principal=null;
        saveSubject();
    }
}
