package com.manage.system.core.cache;

import com.manage.system.common.utils.JsonUtils;
import com.manage.system.common.utils.StringUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EhcacheImpl implements IAppCache {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public <T> T get(String cacheInfo,String key, Class<T> clazz) {

       Element element=  getCache(cacheInfo).get(key);
       if(element!=null){
           String cacheJson = (String) element.getObjectValue();
           if(StringUtils.isNotBlank(cacheJson)){
               return JsonUtils.parseJson(cacheJson,clazz);
           }
       }

       return null;
    }

    @Override
    public void put(String cacheInfo,String key, Object value) {
        Element element=new Element(key,JsonUtils.toJson(value));
        getCache(cacheInfo).put(element);
    }

    @Override
    public void remove(String cacheInfo,String key) {
        cacheManager.getCache(cacheInfo).remove(key);
    }

    @Override
    public Object getStream(String cacheName, String key) {
        Element element=  getCache(cacheName).get(key);
        return element==null?null:element.getObjectValue();
    }

    @Override
    public void putStream(String cacheName, String key, Object value) {
        Element element=new Element(key,value);
        getCache(cacheName).put(element);
    }

    @Override
    public void removeStream(String cacheName, String key) {
        cacheManager.getCache(cacheName).remove(key);
    }

    @Override
    public List cacheKeyList(String cacheName) {
       return getCache(cacheName).getKeys();
    }

    private Cache getCache(String cacheName){
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null){
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }
}
