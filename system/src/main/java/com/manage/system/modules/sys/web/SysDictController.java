package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysDict;
import com.manage.system.modules.sys.service.SysDictService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* 字典表Controller
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysDict")
public class SysDictController extends SystemBaseController {

    @Autowired
    private SysDictService sysDictService;
    /**
    * 加载字典表列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载字典表列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysDict entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysDictService.findPage(entity));
    }

    @ApiOperation(value = "保存字典表信息")
    @RequirePermissions("edit:sys:sysDict")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysDict entity) throws Exception {
        sysDictService.save(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",entity);
    }

    @ApiOperation(value = "删除字典表信息")
    @RequirePermissions("del:sys:sysDict")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysDict entity) throws Exception {
        sysDictService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }
}

