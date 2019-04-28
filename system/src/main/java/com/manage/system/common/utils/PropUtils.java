package com.manage.system.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropUtils {

    /**
     * 读取properties
     * @param file
     * @return
     */
    public static Properties readProp(String file){
        Properties properties=new Properties();
        InputStream is= PropUtils.class.getResourceAsStream(file);
        try {
            properties.load(new InputStreamReader(is,"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**
     * 获取properties内的信息
     * @param properties
     * @return
     */
    public static Map<String,String> getProperties(Properties properties) {
        Map<String,String> result=new HashMap<String,String>();
         for(Object key:properties.keySet()){
             String keyStr=key.toString();
             result.put(keyStr,properties.getProperty(keyStr,""));
         }
         return result;
    }

}
