package com.manage.system.common.utils;

import com.manage.system.common.beetl.SysFunction;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TemplateUtils {

    private static TemplateUtils instance;

    private ResourceLoader resourceLoader;

    private TemplateUtils(){
        ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
        try {
            // WebAppResourceLoader 配置root路径是关键
//            resourceLoader = new WebResourceLoader(patternResolver.getResource("classpath*:templates/").getFile().getPath());

            resourceLoader = new ClasspathResourceLoader("templates/");
//            MapResourceLoader mapResourceLoader
//            CompositeResourceLoader compositeResourceLoader
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public static TemplateUtils getInstance() {
        if(instance==null){
            instance=new TemplateUtils();
        }
        return instance;
    }

    /**
     * 解析模板
     * @param templatePath
     * @param context
     * @param writer
     */
    public  void renderTemplate(String templatePath, Map<String, Object> context, PrintWriter writer){
        getContent(templatePath,context,writer);
    }


    /**
     * 获取解析的内容
     * @param templatePath
     * @param params
     * @param writer
     * @return
     * @throws IOException
     */
    public String getContent(String templatePath, Map<String,Object> params, PrintWriter writer) {
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate(templatePath);
            t.binding(params);
            if(writer!=null){
                t.renderTo(writer);
                return "";
            }
            return  t.render();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 输出到输出流
     * @param templatePath
     * @param params
     * @param out
     * @return
     */
    public String outToOutStream(String templatePath, Map params, OutputStream out) {
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            Map<String,String> fnMap=new HashMap<String,String>();
            fnMap.put("sys", SysFunction.class.getName());
            cfg.setFnMap(fnMap);
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate(templatePath);
            t.binding(params);
            if(out!=null){
                t.renderTo(out);
                return "";
            }
            return  t.render();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 解析字符信息
     * @param content
     * @param params
     * @return
     */
    public String renderString(String content, Map<String, Object> params) {
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
            Map<String,String> fnMap=new HashMap<String,String>();
            fnMap.put("sys",SysFunction.class.getName());
            cfg.setFnMap(fnMap);
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate(content);
            t.binding(params);
            return t.render();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
