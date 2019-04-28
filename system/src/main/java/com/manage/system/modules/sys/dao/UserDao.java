package com.manage.system.modules.sys.dao;

import com.manage.system.core.dao.BaseDao;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import com.manage.system.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends SystemBaseDao<SysUser> {
    SysUser getByUsername(String username);
    void updateLoginInfo(SysUser user);
}
