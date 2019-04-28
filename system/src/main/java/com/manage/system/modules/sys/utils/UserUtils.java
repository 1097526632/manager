package com.manage.system.modules.sys.utils;

import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.security.Principal;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.modules.sys.dao.MenuDao;
import com.manage.system.modules.sys.dao.UserDao;
import com.manage.system.modules.sys.entity.SysMenu;
import com.manage.system.modules.sys.entity.SysUser;
import com.manage.system.modules.sys.service.MenuService;
import com.manage.system.modules.sys.service.UserService;
import com.manage.system.websocket.service.SocketService;

import java.util.*;

public class UserUtils {

    private static UserUtils instance;

    private Map<String,SysUser> userMap=new HashMap<String,SysUser>();
    private UserDao userDao= null;
    private MenuDao menuDao=null;
    private SocketService socketService;

    public UserUtils(){
        userDao= SpringContextHolder.getBean(UserDao.class);
        menuDao=SpringContextHolder.getBean(MenuDao.class);
        socketService=SpringContextHolder.getBean(SocketService.class);
    }

    public static UserUtils getInstance() {
        if(instance==null){
            instance=new UserUtils();
        }
        return instance;
    }

    /**
     * 获取用户
     * @return
     */
    public SysUser getUser(){
        if(SecurityUtils.getSubject().getPrincipal()!=null){
           return getUser(SecurityUtils.getSubject().getPrincipal().getId());
        }

      return null;
    }

    /**
     * 获取用户
     * @param id
     * @return
     */
    public SysUser getUser(String id) {

        SysUser user=userMap.get(id);
        if (user==null){
            user=userDao.get(new SysUser(id));
            if(user!=null){
                loadUserMenu(user);
                userMap.put(user.getId(),user);
            }
        }
        return user;
    }

    /**
     * 加载用户菜单
     * @param sysUser
     */
    public void loadUserMenu(SysUser sysUser){
        List<SysMenu> sysMenus= menuDao.loadUserMenu(sysUser);
        sysUser.setMenuList(sysMenus);
        List<String> btnPermission=new ArrayList<String>();
        List<String> pathPermission=new ArrayList<String>();
        for(SysMenu menu:sysMenus){
            if(StringUtils.isNotBlank(menu.getPermission())){
                btnPermission.addAll(Arrays.asList(menu.getPermission().split(",")));
            }
            if(StringUtils.isNotBlank(menu.getHref())){
                pathPermission.add(StringUtils.formatUrl("/"+menu.getHref()));
            }
        }
        sysUser.setBtnPermission(btnPermission);
        sysUser.setPathPermission(pathPermission);
    }


    /**
     * 删除用户
     */
    public void removeUser() {
        if(SecurityUtils.getSubject().getPrincipal()!=null){
            userMap.remove(SecurityUtils.getSubject().getPrincipal().getId());
        }
        // 移除session
        socketService.removeSession(SecurityUtils.getSubject().getPrincipal().getId(),SecurityUtils.getSubject().getSubjectId());

    }
}
