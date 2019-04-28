package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysOffice;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 机构表Dao
*/
@Mapper
public interface SysOfficeDao extends SystemBaseDao<SysOffice> {

    void updateParentIds(@Param("oldParentIds") String oldParentIds, @Param("newParentIds") String newParentIds);

    List<SysOffice> findOfficeUser(SysOffice entity);
}
