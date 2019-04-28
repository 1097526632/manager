package com.manage.system.core.web;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.DateUtils;
import com.manage.system.common.utils.HttpUtils;
import com.manage.system.common.utils.JsonUtils;
import com.manage.system.core.entity.BaseResponse;
import com.manage.system.core.security.UserAuth;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Controller
@Api(value = "", description = "错误信息处理")
public class ExceptionController extends BaseController  implements ErrorController {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private UserAuth userAuth;

    @RequestMapping("/error")
    public String handleError( HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> errorMap = getErrorAttributes(request,true);

        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(HttpUtils.isAjax(request)){
            BaseResponse baseResponse=BaseResponse.createDefault(false);
            baseResponse.setCode(BaseResponse.ERROR);
            baseResponse.setDesc("服务异常,服务状态码："+statusCode);
            try {
                out(response,baseResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        if(userAuth.checkPermission(errorMap.get("path").toString())){
            return "forward:/sysIndex";
        }
        return "forward:/";
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }

    private void out(HttpServletResponse response,BaseResponse result) throws IOException {
        PrintWriter writer=response.getWriter();
        writer.write(JsonUtils.toJson(result));
        writer.close();
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(servletWebRequest,
                includeStackTrace);
    }
}
