package com.manage.system.modules.sys.service;

import com.manage.system.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysOfficeDao;
import com.manage.system.modules.sys.entity.SysOffice;
import com.manage.system.modules.sys.service.SystemBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 机构表服务
*/
@Service
public class SysOfficeService extends SystemBaseService<SysOfficeDao, SysOffice> {

    private SysOfficeDao sysOfficeDao;

    @Autowired
    protected void setDao(SysOfficeDao sysOfficeDao) {
        super.setDao(sysOfficeDao);
        this.sysOfficeDao=sysOfficeDao;
    }

    @Override
    public void save(SysOffice entity) throws Exception {
        String parentId=entity.getParentId();
        if(StringUtils.isNotBlank(parentId)){
            SysOffice parent=get(parentId);
            if (parent!=null){
                String parentIds = parent.getParentIds()+","+parent.getId();
                entity.setParentIds(parentIds);
            }else{
                entity.setParentId("o_0");
                entity.setParentIds("o_0");
            }

        }else{
            entity.setParentId("o_0");
            entity.setParentIds("o_0");
        }

        if(StringUtils.isNotBlank(entity.getId())){ //更新  修改关联上级parentIds
            SysOffice old = get(entity.getId());
            String updateOldParentIds = old.getParentIds()+","+entity.getId();
            String updateNewParentIds= entity.getParentIds()+","+entity.getId();
            sysOfficeDao.updateParentIds(updateOldParentIds,updateNewParentIds); //更新关联的信息
        }
        entity.setDelFlag("0");
        super.save(entity);
    }

    public List<SysOffice> findOfficeUser(SysOffice entity) {
        return dao.findOfficeUser(entity);
    }
}
