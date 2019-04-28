package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysLog;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 日志表Dao
*/
@Mapper
public interface SysLogDao extends SystemBaseDao<SysLog> {

}
