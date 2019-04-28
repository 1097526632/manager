package com.manage.system.modules.sys.entity;

import com.manage.system.common.utils.DateUtils;
import com.manage.system.modules.sys.entity.SystemBaseEntity;

import java.util.Date;

/**
* 日志表实体
*/
public class SysLog extends SystemBaseEntity {

    private String type;		//  日志类型
    private String title;		//  日志标题
    private String remoteAddr;		//  操作IP地址
    private String userAgent;		//  用户代理
    private String requestUri;		//  请求URI
    private String method;		//  操作方式
    private String params;		//  操作提交的数据
    private String exception;		//  异常信息
    private String returnResult;//返回信息
    private Date beginDate;//开始时间
    private Date endDate;//结束时间

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public double getTimes() {
        if(beginDate==null){
            beginDate=new Date();
        }
        if(endDate==null){
            endDate=new Date();
        }
        return DateUtils.diffSecondDouble(endDate,beginDate);
    }
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
