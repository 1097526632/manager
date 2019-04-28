package com.manage.system.core.cache;

import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.core.cache.IAppCache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

import java.util.List;

public class CacheManage{

   private static IAppCache appCache = SpringContextHolder.getInterfaceBean(IAppCache.class);

    public <T> T get(String cacheName,String key,Class<T> clazz){
        return appCache.get(cacheName,key,clazz);
    }

    public void put(String cacheName,String key,Object value){
        appCache.put(cacheName,key,value);
    }
    public void remove(String cacheName,String key){
        appCache.remove(cacheName,key);
    }

    public List getCacheKeyList(String cacheName){
        return appCache.cacheKeyList(cacheName);
    }

    public Object getObject(String cacheName, String key) {
        return appCache.getStream(cacheName,key);
    }

    public void putObject(String cacheName, String key, Object value) {
        appCache.putStream(cacheName,key,value);
    }

    public void removeObject(String cacheName, String key) {
        appCache.removeStream(cacheName,key);
    }

}
