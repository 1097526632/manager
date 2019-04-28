package com.manage.system.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysFileInfoDao;
import com.manage.system.modules.sys.entity.SysFileInfo;
import com.manage.system.modules.sys.service.SystemBaseService;
import org.springframework.stereotype.Service;

/**
* SysFileInfo服务
*/
@Service
public class SysFileInfoService extends SystemBaseService<SysFileInfoDao, SysFileInfo> {

    private SysFileInfoDao sysFileInfoDao;

    @Autowired
    protected void setDao(SysFileInfoDao sysFileInfoDao) {
        super.setDao(sysFileInfoDao);
        this.sysFileInfoDao=sysFileInfoDao;
    }


}
