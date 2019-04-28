package com.manage.system.common.web;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.RandomImageUtils;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.store.IStore;
import com.manage.system.core.store.StoreManage;
import com.manage.system.core.web.BaseController;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class CommonController extends BaseController {
    @RequestMapping("userfiles/**")
    public void userfiles(HttpServletRequest req, HttpServletResponse resp){
        String filepath = req.getRequestURI();
        int index = filepath.indexOf("userfiles");
        filepath = filepath.substring(index + "userfiles".length());
        filepath = UriUtils.decode(filepath, "UTF-8");
        String fileKey= StringUtils.formatUrl(Global.getUserfilesBaseDir() + filepath);

        long pos = 0;
        try {
            IStore storeInterface= StoreManage.getInstance().getStoreEngine();
            storeInterface.downloadFile(fileKey,req,resp);
            return;
        } catch (Exception e) {
            req.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
            try {
                req.getRequestDispatcher("/error/404.jsp").forward(req, resp);
            } catch (ServletException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RequestMapping("validatecode")
    public void validatecode(HttpServletRequest request,HttpServletResponse response){
        try {
            if("get".equalsIgnoreCase(request.getMethod())){
                RandomImageUtils.outImage(request,response);
            }else{
                String validateCode = request.getParameter(RandomImageUtils.VALIDATE_CODE); // AJAX验证，成功返回true
                if (StringUtils.isNotBlank(validateCode)){
                    response.getOutputStream().print(RandomImageUtils.validate(request, validateCode)?"true":"false");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  @RequestMapping("${app.staticPath}/**")
    public void resource(HttpServletRequest req,HttpServletResponse resp){
        String filepath = req.getRequestURI();
        if(filepath.indexOf("?")>=0){
            filepath=StringUtils.formatUrl(filepath.substring(0,filepath.indexOf("?")));
        }
        filepath = filepath.substring(StringUtils.formatUrl(req.getContextPath()+"/"+Global.getConfig("staticPath")).length());
        filepath = UriUtils.decode(filepath, "UTF-8");

        if(filepath.endsWith(".js")){
            resp.setContentType("text/javascript");
        }else if(filepath.endsWith(".css")){
            resp.setContentType("text/css");
        }else if(filepath.endsWith(".png")){
            resp.setContentType("image/png");
        }else if(filepath.endsWith(".jpg")||filepath.endsWith(".jpeg")){
            resp.setContentType("image/jpeg");
        }else if(filepath.endsWith(".svg")){
            resp.setContentType("image/svg+xml");
        }else{
            resp.setContentType("application/octet-stream");
        }

        String staticRealPath= Global.getConfig("staticRealPath").trim();
        try{
            InputStream is=null;
            if(staticRealPath.toLowerCase().startsWith("classpath")){
//                staticRealPath=staticRealPath.substring("classpath:".length());
                is= SpringContextHolder.getApplicationContext().getResource(StringUtils.formatUrl(staticRealPath+"/"+filepath)).getInputStream();
            }else{
                is=new FileInputStream(StringUtils.formatUrl(req.getServletContext().getRealPath("/")+"/"+staticRealPath+"/"+filepath));
            }
            OutputStream output=resp.getOutputStream();
            try{
                IOUtils.copy(is,output);
            }finally{
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(output);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
