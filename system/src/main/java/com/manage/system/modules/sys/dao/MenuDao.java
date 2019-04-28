package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysMenu;
import com.manage.system.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao extends SystemBaseDao<SysMenu> {
    void updateParentIds(@Param("updateOldParentIds") String updateOldParentIds, @Param("updateNewParentIds") String updateNewParentIds) throws Exception;

    void deleteSubMenu(@Param("updateParentIds") String updateParentIds) throws Exception;

    List<SysMenu> loadUserMenu(SysUser user);
}
