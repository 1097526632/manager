package com.manage.system.core.web;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response){
        String indexUrl = Global.getConfig("indexUrl");
        if(StringUtils.isBlank(indexUrl)){
            indexUrl="/sysIndex";
        }
        return "forward:"+indexUrl;
    }
}
