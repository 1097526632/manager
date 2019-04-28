package com.manage.system.modules.sys.web;

import com.manage.system.core.web.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页信息访问
 */
@Controller
@RequestMapping("/")
@Api(value = "/", description = "首页访问")
public class SysIndexController extends BaseController {

    @RequestMapping("sysIndex")
    public String index(){
        return "sys/index";
    }

}
