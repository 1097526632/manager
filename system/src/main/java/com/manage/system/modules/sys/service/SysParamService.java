package com.manage.system.modules.sys.service;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.CollectionUtils;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.config.AfterRun;
import org.springframework.beans.factory.annotation.Autowired;
import com.manage.system.modules.sys.dao.SysParamDao;
import com.manage.system.modules.sys.entity.SysParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* 系统参数表服务
*/
@Service
public class SysParamService extends SystemBaseService<SysParamDao, SysParam> implements AfterRun {

    private SysParamDao sysParamDao;

    @Autowired
    protected void setDao(SysParamDao sysParamDao) {
        super.setDao(sysParamDao);
        this.sysParamDao=sysParamDao;
    }

    @Override
    public void save(SysParam entity) throws Exception {
        if(StringUtils.isBlank(entity.getType())){
            entity.setType("1"); //设置自定义参数
        }
        super.save(entity);
        Global.putConfig(entity.getCode(),entity.getValue()); // 重新加载参数信息
    }

    @Override
    public void afterRun() {
        List<SysParam> sysParamList= findList(new SysParam());
        Map<String,String> maps= CollectionUtils.extractToMap(sysParamList,"code","value");
        Global.putConfig(maps);
    }
}
