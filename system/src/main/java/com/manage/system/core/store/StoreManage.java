package com.manage.system.core.store;

import com.manage.system.common.utils.SpringContextHolder;

public class StoreManage {

    private static StoreManage instance;
    private IStore storeInterface = SpringContextHolder.getBean(IStore.class);

    public static StoreManage getInstance() {
        if(instance==null){
            instance=new StoreManage();
        }
        return instance;
    }

    public IStore getStoreEngine(){
        return storeInterface;
    }
}
