package com.manage.system.core.security;

import com.manage.system.core.cache.CacheManage;

import java.util.List;

/**
 * 安全缓存管理
 */
public class SecurityCacheManage extends CacheManage {

    private static SecurityCacheManage instance;
    private static final String SESSION_CACHE="sessionCache";

    public static SecurityCacheManage getInstance() {
        synchronized (SecurityCacheManage.class){
            if(instance==null){
                instance=new SecurityCacheManage();
            }
        }
        return instance;
    }

    /**
     * 获取subject
     * @param subjectId
     * @return
     */
    public Subject getSubject(String subjectId){
        return (Subject) getObject(SESSION_CACHE,subjectId);
    }

    /**
     * 设置subject
     * @param subjectId
     * @param subject
     */
    public void putSubject(String subjectId,Subject subject){
        putObject(SESSION_CACHE,subjectId,subject);
    }

    /**
     * 删除subject
     * @param subjectId
     */
    public void removeSubject(String subjectId){
        removeObject(SESSION_CACHE,subjectId);
    }

    /**
     * 清除超时的subject
     */
    public void clearTimeoutSubject(){
        List<String> keys=getCacheKeyList(SESSION_CACHE);
        for(String key:keys){
            Subject subject=getSubject(key);
            if(subject!=null&&subject.isTimeout()){
                removeSubject(key);
            }
        }
    }

}
