package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysLog;
import com.manage.system.modules.sys.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* 日志表Controller
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysLog")
public class SysLogController extends SystemBaseController {

    @Autowired
    private SysLogService sysLogService;
    /**
    * 加载日志表列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载日志表列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysLog entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysLogService.findPage(entity));
    }
}

