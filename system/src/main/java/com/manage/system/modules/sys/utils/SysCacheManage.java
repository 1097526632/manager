package com.manage.system.modules.sys.utils;

import com.manage.system.core.cache.CacheManage;
import com.manage.system.modules.sys.entity.SysDict;

import java.util.ArrayList;
import java.util.List;

public class SysCacheManage extends CacheManage {

    private static String CACHE_NAME="sysCache";

    private static SysCacheManage instance;

    public static SysCacheManage getInstance() {
        synchronized (SysCacheManage.class){
            if(instance == null){
                instance=new SysCacheManage();
            }
        }
        return instance;
    }
}
