package com.manage.system.common.jxls;

import com.alibaba.fastjson.JSONObject;
import com.manage.system.common.utils.StringUtils;
import org.jxls.builder.xls.XlsCommentAreaBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by wm on 2017-07-18.
 */
public class CustomerJxlsFunction {


    public CustomerJxlsFunction(){
        XlsCommentAreaBuilder.addCommandMapping("comment", CommentCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("link", LinkCommand.class);
        XlsCommentAreaBuilder.addCommandMapping("merge", MergeCommand.class);
    }


    // 日期格式化
    public String dateFmt(Date date, String fmt) {
        if (date == null) {
            return null;
        }
        try {
            SimpleDateFormat dateFmt = new SimpleDateFormat(fmt);
            return dateFmt.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 状态转换
     * @param index 下标
     * @param status 字符串对象，如："0:未提交,1:审核中,2:审核通过,3:审核失败"
     * @return
     */
    public String statusFmt(String index, String status){
        if(StringUtils.isBlank(index)|| StringUtils.isBlank(status)){return "";}

        status="{"+status+"}";  //因为excel中大括号{}会被解析，所以，在后台加大括号{}
        JSONObject jb= JSONObject.parseObject(status);
        Map<String,String> map = (Map)jb;

        return map.get(index);
    }

}
