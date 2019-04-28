package com.manage.system.modules.sys.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.manage.system.common.utils.HttpUtils;
import com.manage.system.common.utils.JsonUtils;
import com.manage.system.core.exception.Exceptions;
import com.manage.system.modules.sys.entity.SysLog;
import com.manage.system.modules.sys.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

@Aspect
@Component
public class LogInterceptor {

    @Autowired
    SysLogService logService;

    private static final ThreadLocal<SysLog> threadLocal =
            new ThreadLocal<SysLog>();

    /**
     * 切点
     */
    @Pointcut("execution(* com.manage.system.modules..web.*.*(..))")
    public void executePointcut(){
    }

    /**
     * token controller切点
     */
    @Pointcut("execution(* com.manage.system.core.web.TokenController.*(..))")
    public void executeTokenPointCut(){

    }

    /**
     * 执行之前
     * @param joinPoint
     */
    @Before("executePointcut()||executeTokenPointCut()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        SysLog sysLog=new SysLog();
        sysLog.setType("SYSTEM_LOG");
        sysLog.setBeginDate(new Date());
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
//        joinPoint.getThis();
        //代理的目标对象
//        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        if(targetMethod.isAnnotationPresent(ApiOperation.class)){
            ApiOperation apiOperation=targetMethod.getAnnotation(ApiOperation.class);
            sysLog.setTitle(apiOperation.value());
        }else{
            sysLog.setTitle(signature.getDeclaringTypeName()+"."+signature.getName());
        }
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        sysLog.setRequestUri(request.getRequestURI());
        sysLog.setUserAgent(JsonUtils.toJson(HttpUtils.getHeaders(request)));
        sysLog.setRemoteAddr(HttpUtils.getIpAddr(request));
        sysLog.setMethod(signature.getDeclaringTypeName()+"."+signature.getName());
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> parameterMap = Maps.newHashMap();
        while (enumeration.hasMoreElements()){
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter,request.getParameter(parameter));
        }
        StringBuilder str=new StringBuilder("request:"+JSON.toJSONString(parameterMap)+";   ");
        if(obj!=null&&obj.length>0){
            for(Object o:obj){
                if(o!=null && !(o instanceof HttpServletRequest) && !(o instanceof HttpServletResponse)){
                    try{
                        str.append(JsonUtils.toJson(o, SerializerFeature.IgnoreErrorGetter)).append("   ");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        sysLog.setParams(str.toString());
        threadLocal.set(sysLog);
    }

    /**
     * 执行之后有返回
     * @param joinPoint
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "executePointcut()||executeTokenPointCut()")
    public void doAfterReturnAdvice(JoinPoint joinPoint,Object ret) {
        SysLog log= threadLocal.get();
        if(log!=null) {
//            log.setReturnResult(JsonUtils.toJson(ret));
        }
    }

    /**
     * 返回异常
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "executePointcut()||executeTokenPointCut()",throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception) {
        SysLog log= threadLocal.get();
        if(log!=null) {
            log.setException(Exceptions.getStackTraceAsString(exception));
        }
    }

    /**
     * 退出之前执行
     * @param joinPoint
     */
    @After("executePointcut()||executeTokenPointCut()")
    public void doAfterAdvice(JoinPoint joinPoint) {
        SysLog log=threadLocal.get();
        try {
            log.setEndDate(new Date());
         logService.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
