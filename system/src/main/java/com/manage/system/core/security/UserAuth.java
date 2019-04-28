package com.manage.system.core.security;

import javax.servlet.http.HttpServletRequest;

public interface UserAuth {
    /**
     * 用户授权
     * @param principal
     * @return
     */
    Principal authUser(Principal principal, HttpServletRequest request) throws Exception;

    /**
     * 访问权限检查
     * @param permissKey
     * @return
     */
    boolean checkPermission(String permissKey);

    /**
     * 功能按钮权限检查
     * @return
     */
    boolean checkFunPermission(String permissKey);

    void userLogout(HttpServletRequest request) throws Exception;

}
