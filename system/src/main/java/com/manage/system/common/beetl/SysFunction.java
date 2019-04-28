package com.manage.system.common.beetl;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.DateUtils;
import com.manage.system.common.utils.Reflections;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysFunction implements Function {
    Map<String,Object> classInstanceConfig=new HashMap<String,Object>();

    public SysFunction(){
        classInstanceConfig.put("global", Global.class);
        classInstanceConfig.put("strutils", StringUtils.class);
        classInstanceConfig.put("date", DateUtils.class);
        List<BeetlFunction> beetlFunctionList= SpringContextHolder.getInterfaceBeans(BeetlFunction.class);
        if(beetlFunctionList!=null&&beetlFunctionList.size()>0){
            for(BeetlFunction beetlFunction:beetlFunctionList){
                classInstanceConfig.put(beetlFunction.funName(),beetlFunction);
            }
        }
    }

    @Override
    public Object call(Object[] paras, Context ctx) {
        Object o = paras[0];//键值
        if (o != null){
            String[] firstParam=o.toString().split("\\.");

            String firstKey=firstParam[0];
            String firstMethod=firstParam[1];

         Object instance=  classInstanceConfig.get(firstKey);
         Object[] args=new Object[paras.length-1];
         for(int i=0;i<paras.length-1;i++){
            args[i]=paras[i+1];
         }
          Object result=  Reflections.invokeMethod(instance,firstMethod,args);
           /* try{
                ctx.byteWriter.write(o.toString().getBytes());
            }catch (IOException e){
                throw new RuntimeException(e);
            }*/
            return result;
        }
        return null;

    }

}
