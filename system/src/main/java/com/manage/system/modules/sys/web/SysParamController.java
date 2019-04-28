package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysParam;
import com.manage.system.modules.sys.service.SysParamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* 系统参数表Controller
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysParam")
public class SysParamController extends SystemBaseController {

    @Autowired
    private SysParamService sysParamService;
    /**
    * 加载系统参数表列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载系统参数表列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysParam entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysParamService.findPage(entity));
    }

    @ApiOperation(value = "保存系统参数表信息")
    @RequirePermissions("edit:sys:sysParam")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysParam entity) throws Exception {
        sysParamService.save(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",entity);
    }

    @ApiOperation(value = "删除系统参数表信息")
    @RequirePermissions("del:sys:sysParam")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysParam entity) throws Exception {
        sysParamService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }
}

