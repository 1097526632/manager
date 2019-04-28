package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.utils.UserUtils;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysFileCategory;
import com.manage.system.modules.sys.service.SysFileCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* SysFileCategoryController
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysFileCategory")
public class SysFileCategoryController extends SystemBaseController {

    @Autowired
    private SysFileCategoryService sysFileCategoryService;
    /**
    * 加载SysFileCategory列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载SysFileCategory列表")
    @RequirePermissions("view:sys:sysFileCategory")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysFileCategory entity){
        entity.setCreateBy(UserUtils.getInstance().getUser().getId());
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysFileCategoryService.findList(entity));
    }

    @ApiOperation(value = "保存SysFileCategory信息")
    @RequirePermissions("edit:sys:sysFileCategory")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysFileCategory entity) throws Exception {
        sysFileCategoryService.save(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",entity);
    }

    @ApiOperation(value = "删除SysFileCategory信息")
    @RequirePermissions("del:sys:sysFileCategory")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysFileCategory entity) throws Exception {
        sysFileCategoryService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }
}

