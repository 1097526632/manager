package com.manage.system.core.cache;

import java.util.List;

public interface IAppCache {

    <T> T get(String cacheName,String key,Class<T> clazz);
    void put(String cacheName,String key,Object value);
    void remove(String cacheName,String key);
    Object getStream(String cacheName,String key);
    void putStream(String cacheName,String key,Object value);
    void removeStream(String cacheName,String key);
    List cacheKeyList(String cacheName);
}
