package com.manage.system.core.entity;

import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.security.SecurityUtils;

public class BaseResponse<T> {

    public static final String SUCCESS="success";//成功
    public static final String FAIL="fail";//失败
    public static final String NO_LOGIN="no_login";//未登录
    public static final String NO_PER="no_per";//无权限
    public static final String ERROR="error";//错误

    private String sessionId;

    /**
     * 返回编码
     */
    private String code;
    /**
     * 描述信息
     */
    private String desc;

    public static BaseResponse createDefault(boolean flag){
        if(flag){
            return new BaseResponse(SUCCESS,"处理信息成功");
        }else{
            return new BaseResponse(FAIL,"处理信息失败");
        }

    }

    public BaseResponse(){}

    public BaseResponse(String code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public BaseResponse(String code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public String getSessionId() {
        return StringUtils.isNotBlank(sessionId)?sessionId: SecurityUtils.getSubject().getSubjectId();
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
