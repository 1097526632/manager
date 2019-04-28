package com.manage.system.modules.sys.service;

import com.manage.system.modules.sys.entity.SysOffice;
import com.manage.system.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysRoleDao;
import com.manage.system.modules.sys.entity.SysRole;
import com.manage.system.modules.sys.service.SystemBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 角色表服务
*/
@Service
public class SysRoleService extends SystemBaseService<SysRoleDao, SysRole> {

    private SysRoleDao sysRoleDao;

    @Autowired
    protected void setDao(SysRoleDao sysRoleDao) {
        super.setDao(sysRoleDao);
        this.sysRoleDao=sysRoleDao;
    }

    @Override
    public void save(SysRole entity) throws Exception {
        entity.setIdPre("r_");
        super.save(entity);
       dao.updateRoleMenuOffice(entity);
    }

    public List<SysRole> loadRoleMenu(SysRole entity) {
        return dao.loadRoleMenu(entity);
    }

    public List<SysOffice> loadRoleOffice(SysRole entity) {
        return dao.loadRoleOffice(entity);
    }

    public List<SysUser> loadExistUser(SysRole entity) {
        return dao.loadExistUser(entity);
    }

    public void saveRoleUser(SysRole entity) throws Exception {
        dao.saveRoleUser(entity);
    }
}
