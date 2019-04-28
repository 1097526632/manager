package com.manage.system.core.interceptor;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.config.Constants;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.core.security.Subject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SystemInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(StringUtils.isBlank(Global.contextPath)){
            Global.contextPath=request.getContextPath();
        }
        SecurityUtils.set(Subject.getSubject(request));
        request.getSession().setMaxInactiveInterval((Constants.timeout)*60); // 设置超时时间
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
