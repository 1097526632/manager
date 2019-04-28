package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysParam;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 系统参数表Dao
*/
@Mapper
public interface SysParamDao extends SystemBaseDao<SysParam> {

}
