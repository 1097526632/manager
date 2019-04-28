package com.manage.system.common.utils;

import com.manage.system.common.jxls.CustomerJxlsFunction;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-01.
 */
public class ExcelExportUtils {
    public static void exportExcel(String templateName, OutputStream out, Map<String, Object> value) {
        InputStream in = ExcelExportUtils.class.getResourceAsStream(templateName);
        exportExcel(in,out,value);
    }

    public static void exportExcel(InputStream in, OutputStream out, Map<String, Object> value){

        Context context = new Context();
        if (value != null) {
            for (String key : value.keySet()) {
                context.putVar(key, value.get(key));
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer  = jxlsHelper.createTransformer(in, out);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("jx", new CustomerJxlsFunction());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);
        try {
            jxlsHelper.processTemplate(context, transformer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
