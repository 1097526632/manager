package com.manage.system.modules.sys.service;

import com.manage.system.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysLogDao;
import com.manage.system.modules.sys.entity.SysLog;
import com.manage.system.modules.sys.service.SystemBaseService;
import org.springframework.stereotype.Service;

/**
* 日志表服务
*/
@Service
public class SysLogService extends SystemBaseService<SysLogDao, SysLog> {

    private SysLogDao sysLogDao;

    @Autowired
    protected void setDao(SysLogDao sysLogDao) {
        super.setDao(sysLogDao);
        this.sysLogDao=sysLogDao;
    }

    @Override
    public void save(SysLog entity) throws Exception {
        if(entity!=null){
            if(StringUtils.isNotBlank(entity.getId())){
                entity.setId(null);
            }
            entity.preInsert();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        sysLogDao.insert(entity);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }

    }
}
