package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysOffice;
import com.manage.system.modules.sys.service.SysOfficeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* 机构表Controller
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysOffice")
public class SysOfficeController extends SystemBaseController {

    @Autowired
    private SysOfficeService sysOfficeService;
    /**
    * 加载机构表列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载机构表列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysOffice entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysOfficeService.findList(entity));
    }

    @ApiOperation(value = "加载机构用户信息")
    @RequestMapping(value="officeUser",method = RequestMethod.POST)
    public BaseResponse officeUser(@RequestBody SysOffice entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysOfficeService.findOfficeUser(entity));
    }

    @ApiOperation(value = "保存机构表信息")
    @RequirePermissions("edit:sys:sysOffice")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysOffice entity) throws Exception {
        entity.setIdPre("o_");
        sysOfficeService.save(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",entity);
    }

    @ApiOperation(value = "删除机构表信息")
    @RequirePermissions("del:sys:sysOffice")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysOffice entity) throws Exception {
        sysOfficeService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }
}

