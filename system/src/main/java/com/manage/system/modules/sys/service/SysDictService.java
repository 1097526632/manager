package com.manage.system.modules.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysDictDao;
import com.manage.system.modules.sys.entity.SysDict;
import com.manage.system.modules.sys.service.SystemBaseService;
import org.springframework.stereotype.Service;

/**
* 字典表服务
*/
@Service
public class SysDictService extends SystemBaseService<SysDictDao, SysDict> {

    private SysDictDao sysDictDao;

    @Autowired
    protected void setDao(SysDictDao sysDictDao) {
        super.setDao(sysDictDao);
        this.sysDictDao=sysDictDao;
    }


}
