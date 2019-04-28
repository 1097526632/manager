package com.manage.system.modules.sys.servlet;

import com.alibaba.druid.support.http.StatViewServlet;
import com.manage.system.core.security.SecurityUtils;

import javax.servlet.http.HttpServletRequest;

public class DruidServlet extends StatViewServlet {

    @Override
    public boolean isRequireAuth() {
       return true;
    }

    @Override
    public boolean checkLoginParam(HttpServletRequest request) {
        return SecurityUtils.getSubject().isAuth();
    }
}
