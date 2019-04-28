package com.manage.system.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {

    /**
     * 转成json
     * @param value
     * @return
     */
    public static String toJson(Object value){

        return toJson(value,SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 转化json
     * @param value
     * @param serializerFeature
     * @return
     */
    public static String toJson(Object value,SerializerFeature... serializerFeature){
        return JSONObject.toJSONString(value, serializerFeature);
    }
    /**
     * 解析json
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseJson(String json,Class<T> clazz){
        return JSONObject.parseObject(json,clazz);
    }


}
