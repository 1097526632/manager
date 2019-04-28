package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysDict;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 字典表Dao
*/
@Mapper
public interface SysDictDao extends SystemBaseDao<SysDict> {

}
