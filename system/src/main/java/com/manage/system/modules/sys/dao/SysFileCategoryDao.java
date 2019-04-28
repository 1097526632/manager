package com.manage.system.modules.sys.dao;

import com.manage.system.modules.sys.entity.SysFileCategory;
import com.manage.system.modules.sys.dao.SystemBaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
* SysFileCategoryDao
*/
@Mapper
public interface SysFileCategoryDao extends SystemBaseDao<SysFileCategory> {

    void updateSubCategory(SysFileCategory entity);
}
