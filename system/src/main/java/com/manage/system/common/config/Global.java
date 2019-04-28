package com.manage.system.common.config;

import com.manage.system.common.utils.PropUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全局配置
 */
public class Global {
    public static String contextPath;
    private static Map<String,String> globalConfig=new HashMap<String,String>();

    public static String getExportPath() {
        return globalConfig.getOrDefault("exporttemplate","exporttemplate");
    }

    public static String getUserfilesBaseDir() {
        return globalConfig.getOrDefault("userfiles","userfiles");
    }

    public static String getConfig(String key) {
        if(globalConfig.size()==0){
           Properties properties= PropUtils.readProp("/application.properties");
            globalConfig.putAll( PropUtils.getProperties(properties));
        }
        return globalConfig.get(key);
    }

    /**
     * 获取日期类路径
     * @return
     */
    public static String getDatePath(){
        return getConfig("datePath");
    }

    public static String getAdminUrl() {
        return getConfig("app.auth.adminurl");
    }

    public static void putConfig(String key,String value){
        globalConfig.put(key,value);
    }
    public static void putConfig(Map<String,String> values){
        globalConfig.putAll(values);
    }
}
