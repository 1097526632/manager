package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysMenu;
import com.manage.system.modules.sys.entity.SysUser;
import com.manage.system.modules.sys.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "菜单信息接口")
@RequestMapping(value="${app.auth.adminurl}/menu")
public class MenuController extends SystemBaseController {

    @Autowired
    private MenuService menuService;

    /**
     * 加载菜单列表
     * @param menu
     * @return
     */
    @ApiOperation(value = "加载菜单列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse loadList(@RequestBody SysMenu menu){
        return new BaseResponse(BaseResponse.SUCCESS,"加载菜单信息成功",menuService.findList(menu));
    }

    @ApiOperation(value = "保存菜单信息")
    @RequirePermissions("edit:sys:menu")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysMenu menu) throws Exception {
        menuService.save(menu);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",menu);
    }

    @ApiOperation(value = "删除菜单信息")
    @RequirePermissions("del:sys:menu")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysMenu menu) throws Exception {
        menuService.delete(menu);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",menu);
    }
}
