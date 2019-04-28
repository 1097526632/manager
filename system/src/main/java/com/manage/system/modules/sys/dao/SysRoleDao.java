package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysOffice;
import com.manage.system.modules.sys.entity.SysRole;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import com.manage.system.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* 角色表Dao
*/
@Mapper
public interface SysRoleDao extends SystemBaseDao<SysRole> {

    void updateRoleMenuOffice(SysRole entity) throws Exception;

    List<SysRole> loadRoleMenu(SysRole entity);

    List<SysOffice> loadRoleOffice(SysRole entity);

    List<SysUser> loadExistUser(SysRole entity);

    void saveRoleUser(SysRole entity);
}
