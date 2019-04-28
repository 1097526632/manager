package com.manage.system.modules.sys.service;

import com.manage.system.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysFileCategoryDao;
import com.manage.system.modules.sys.entity.SysFileCategory;
import com.manage.system.modules.sys.service.SystemBaseService;
import org.springframework.stereotype.Service;

/**
* SysFileCategory服务
*/
@Service
public class SysFileCategoryService extends SystemBaseService<SysFileCategoryDao, SysFileCategory> {

    private SysFileCategoryDao sysFileCategoryDao;

    @Autowired
    protected void setDao(SysFileCategoryDao sysFileCategoryDao) {
        super.setDao(sysFileCategoryDao);
        this.sysFileCategoryDao=sysFileCategoryDao;
    }

    @Override
    public void save(SysFileCategory entity) throws Exception {
        if(StringUtils.isNotBlank(entity.getParentId())&&!"0".equalsIgnoreCase(entity.getParentId())){
            SysFileCategory parent=get(entity.getParentId());
            if(parent!=null){
                entity.setParentIds(parent.getParentIds()+","+parent.getId());
                entity.setParentNames(parent.getParentNames()+","+parent.getName());
            }
        }

        if (StringUtils.isNotBlank(entity.getId())){ // 更新
            SysFileCategory fileCategory=get(entity.getId());
            if(fileCategory!=null){
                entity.setUpdateParentIds(fileCategory.getParentId()+","+entity.getId());
                entity.setNewParentIds(entity.getParentIds()+","+entity.getId());
                entity.setNewParentNames(entity.getParentNames()+","+entity.getName());
                dao.updateSubCategory(entity);
            }


        }
        super.save(entity);
    }
}
