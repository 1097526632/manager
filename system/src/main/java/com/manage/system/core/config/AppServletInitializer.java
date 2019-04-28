package com.manage.system.core.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.manage.system.common.config.Global;
import com.manage.system.modules.sys.servlet.DruidServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.nio.charset.StandardCharsets;

public class AppServletInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");


        FilterRegistration.Dynamic webStatFilter = servletContext.addFilter("webStatFilter", WebStatFilter.class);
        webStatFilter.addMappingForUrlPatterns(null, false, "/*");
        webStatFilter.setInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.txt,*.ico");
        webStatFilter.setInitParameter("profileEnable", "true");
        webStatFilter.setInitParameter("principalCookieName", "USER_COOKIE");
        webStatFilter.setInitParameter("principalSessionName", "USER_SESSION");

//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(WebMvcConfig.class);
//        ctx.setServletContext(servletContext);
//        ctx.scan("com.manage.system");

        ServletRegistration.Dynamic servlet = servletContext.addServlet("stat", new DruidServlet());
        servlet.addMapping("/druid/*");
        servlet.setLoadOnStartup(2);

    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
