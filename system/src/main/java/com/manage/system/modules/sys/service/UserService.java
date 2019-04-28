package com.manage.system.modules.sys.service;

import com.manage.system.common.utils.Encodes;
import com.manage.system.common.utils.HttpUtils;
import com.manage.system.common.utils.IdGen;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.entity.Page;
import com.manage.system.core.security.Principal;
import com.manage.system.core.security.SecurityCacheManage;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.core.security.UserAuth;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import com.manage.system.modules.sys.dao.UserDao;
import com.manage.system.modules.sys.entity.SysUser;
import com.manage.system.modules.sys.entity.SystemBaseEntity;
import com.manage.system.modules.sys.utils.UserUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 用户service
 */
@Service
public class UserService extends SystemBaseService<UserDao, SysUser> implements UserAuth {

    private UserDao userDao;

    @Autowired
    protected void setUserDao(UserDao userDao) {
        this.userDao=userDao;
        this.dao=userDao;
    }


    public SysUser getByUsername(String username){
        return userDao.getByUsername(username);
    }

    @Override
    @Transactional(readOnly = false)
    public Principal authUser(Principal principal, HttpServletRequest request) throws Exception {
        SysUser user=getByUsername(principal.getUsername());
        if(user!=null){
            if(Encodes.validatePassword(principal.getPassword(),user.getPassword())){
                principal.setId(user.getId());
                principal.setName(user.getName());
                user.setLoginDate(new Date());
                user.setLoginIp(HttpUtils.getIpAddr(request));
                userDao.updateLoginInfo(user);//更新用户登录信息
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean checkPermission(String permissKey) {
        SysUser user=UserUtils.getInstance().getUser();
        if(user==null){
            return false;
        }
        if("u_01".equalsIgnoreCase(user.getId())){
            return true;
        }
        return user.getPathPermission().contains(StringUtils.formatUrl("/"+permissKey));
    }

    @Override
    public boolean checkFunPermission(String permissKey) {
        SysUser sysUser= UserUtils.getInstance().getUser();
        if(sysUser==null){
            return false;
        }
        if("u_01".equalsIgnoreCase(sysUser.getId())){
            return true;
        }
        return sysUser.getBtnPermission().contains(permissKey);
    }

    @Override
    public void userLogout(HttpServletRequest request) throws Exception {
        UserUtils.getInstance().removeUser();
    }

    public SysUser getUser(String id) {
        return userDao.get(new SysUser(id));
    }

    public SysUser get(SysUser entity) {
        return userDao.get(entity);
    }

    public List<SysUser> findList(SysUser entity) {
        return userDao.findList(entity);
    }

    public Page<SysUser> findPage(SysUser entity) {
        Page page=entity.getPage();
        if(page==null){
            page=new Page();
            entity.setPage(page);
        }
        List<SysUser> userList= userDao.findList(entity);
        page.setRecords(userList);
        return page;
    }

    public void save(SysUser entity) throws Exception {
        entity.setUpdateBy(UserUtils.getInstance().getUser().getId());
        entity.setUpdateDate(new Date());
        if(StringUtils.isBlank(entity.getDelFlag())){
            entity.setDelFlag("0");
        }
        if(StringUtils.isNotBlank(entity.getId())){
            if(StringUtils.isNotBlank(entity.getPassword())){
                entity.setPassword(Encodes.entryptPassword(entity.getPassword()));
            }
            userDao.update(entity);
        }else{
            entity.setId("u_"+IdGen.uuid());
            entity.setCreateBy(SecurityUtils.getSubject().getPrincipal().getId());
            entity.setCreateDate(new Date());
            if(StringUtils.isBlank(entity.getPassword())){
                entity.setPassword(entity.getUsername());
            }
            entity.setPassword(Encodes.entryptPassword(entity.getPassword()));
            userDao.insert(entity);
        }
        updateSubject(entity);
    }

    public void updateSubject(SysUser user){
       SysUser principal= (SysUser) SecurityUtils.getSubject().getPrincipal();
       if(principal.getId().equalsIgnoreCase(user.getId())){
           principal.setName(user.getName());
           principal.setMobile(user.getMobile());
           principal.setPhoto(user.getPhoto());
           principal.setPersonality(user.getPersonality());
           SecurityUtils.getSubject().saveSubject();
       }
    }

    public void delete(SysUser entity) throws Exception {
        userDao.delete(entity);
    }
}
