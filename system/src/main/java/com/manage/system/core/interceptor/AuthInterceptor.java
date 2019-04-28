package com.manage.system.core.interceptor;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.HttpUtils;
import com.manage.system.common.utils.JsonUtils;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.core.config.Constants;
import com.manage.system.core.entity.BaseResponse;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.core.security.Subject;
import com.manage.system.core.security.UserAuth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static UserAuth userAuth = SpringContextHolder.getBean(UserAuth.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject subject=SecurityUtils.getSubject();
        if(!subject.isAuth()){//是否登录
            if(HttpUtils.isAjax(request)){
                BaseResponse result=new BaseResponse(BaseResponse.NO_LOGIN,"用户未登录");
                out(response,result);
            }else{
                response.sendRedirect(Global.contextPath);
            }
            return false;
        }

//        if(!checkPermission(request)){
//            BaseResponse result=new BaseResponse(BaseResponse.NO_PER,"菜单权限访问不足");
//            out(response,result);
//            return false;
//        }

        if(!(checkFunction(handler))){
            BaseResponse result=new BaseResponse(BaseResponse.NO_PER,"功能权限访问不足");
            out(response,result);
            return false;
        }

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

    /**
     * 不需要过滤的路径
     * @return
     */
    public List<String> getExcludePath() {
        List<String> excludeList=new ArrayList<String>();
        excludeList.add(Constants.ADMIN_AUTH_URL+"/token/getToken");
        excludeList.add(Constants.ADMIN_AUTH_URL+"/token/authUser");
        excludeList.add(Constants.ADMIN_AUTH_URL+"/token/logout");
        excludeList.add(Constants.ADMIN_AUTH_URL+"/sys/sysParam/loadList");
        return excludeList;
    }

    /**
     * @param request
     * @return
     */
    private boolean checkPermission(HttpServletRequest request){
        String url=request.getServletPath();
        return userAuth.checkPermission(url);
    }

    private boolean checkFunction(Object handler){
        if(handler instanceof HandlerMethod){
            HandlerMethod hm=(HandlerMethod)handler;
            Class<?> clazz=hm.getBeanType();
            Method m=hm.getMethod();
            try {
                if (clazz!=null && m != null ) {
                    boolean isMethondAnnotation=m.isAnnotationPresent(RequirePermissions.class);
                    RequirePermissions rc=null;
                    //如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
                    if(isMethondAnnotation){
                        rc=m.getAnnotation(RequirePermissions.class);
                        String value=rc.value();
                        if(StringUtils.isNotBlank(value)){
                            String[] values = value.split(",");
                            boolean hasPer = true;
                            for(String per: values){
                                if(!userAuth.checkFunPermission(value)){
                                    hasPer = false;
                                    break;
                                }
                            }
                            return hasPer;
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    private void out(HttpServletResponse response,BaseResponse result) throws IOException {
        PrintWriter writer=response.getWriter();
        writer.write(JsonUtils.toJson(result));
        writer.close();
    }

}
