package com.manage.system.core.config;

import com.manage.system.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.manage.system")
@MapperScan(basePackages = "com.manage.system",annotationClass = Mapper.class)
@EnableTransactionManagement
@Configuration
public class RootConfiguration   extends SpringBootServletInitializer {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    @PostConstruct
    public void postConstruct () throws IOException {
        new MapperRefresh(sqlSessionFactory.getConfiguration()).run();
    }
}
