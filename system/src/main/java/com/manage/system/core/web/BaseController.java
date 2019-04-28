package com.manage.system.core.web;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

/**
 * controller基类
 */
public class BaseController {
    protected static final String EXPORT_TEMPLATE= Global.getExportPath();

    /**
     * 客户端返回JSON字符串
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, JsonUtils.toJson(object), "application/json");
    }

    /**
     * 客户端返回字符串
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
        });
    }
    public void bindRequestParam(HttpServletRequest request, Model model){
        Enumeration paramNames = request.getParameterNames();
        if(paramNames!=null){
            while(paramNames.hasMoreElements()){
                String paramName = (String) paramNames.nextElement();
                String paramValue=request.getParameter(paramName);
                if(paramValue!=null){
                    model.addAttribute(paramName,paramValue);
                }
            }
        }
    }

    protected void exportFile(String fileName, String filePath, HttpServletRequest request, HttpServletResponse response, Map<String,Object> value) throws Exception {

        if (HttpUtils.isIE(request)) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename", Encodes.urlEncode(fileName));
        ServletOutputStream out = response.getOutputStream();
        FileInputStream fis=new FileInputStream(filePath);
        IOUtils.copy(fis,out);
        out.flush();
        out.close();
    }

    protected void exportFile(String fileName, InputStream inputStream, HttpServletRequest request, HttpServletResponse response, Map<String,Object> value) throws Exception {

        if (HttpUtils.isIE(request)) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename", Encodes.urlEncode(fileName));
        ServletOutputStream out = response.getOutputStream();
        IOUtils.copy(inputStream,out);
        out.flush();
        out.close();
    }

    protected void exportExcel(String fileName,String templateFileName,HttpServletRequest request,HttpServletResponse response,Map<String,Object> value) throws Exception {
        if(!fileName.endsWith(".xls")&&!fileName.endsWith(".xlsx")){
            fileName += templateFileName.substring(templateFileName.lastIndexOf("."));
        }

        if (HttpUtils.isIE(request)) {
            fileName = java.net.URLEncoder.encode(fileName, "UTF8");
        } else {
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }

        if(templateFileName.indexOf("/")<0&&templateFileName.indexOf("\\")<0){
            templateFileName=EXPORT_TEMPLATE+templateFileName;
        }
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        response.setHeader("filename", Encodes.urlEncode(fileName));
        ServletOutputStream out = response.getOutputStream();
        ExcelExportUtils.exportExcel(templateFileName,out,value);
        out.flush();
        out.close();
    }

}
