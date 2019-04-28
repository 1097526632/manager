package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysUser;
import com.manage.system.modules.sys.service.UserService;
import com.manage.system.modules.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户相关接口")
@RestController
@RequestMapping(value="${app.auth.adminurl}/sys/user")
public class UserController extends SystemBaseController {

    @Autowired
    private UserService sysUserService;

    @ApiOperation(value="用户菜单接口")
    @RequestMapping("userMenu")
    public BaseResponse userMenu(){
        SysUser user= UserUtils.getInstance().getUser();
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",user.getMenuList());
    }
    /**
     * 根据用户名加载查找信息
     * @param entity
     * @return
     */
    @ApiOperation(value = "根据用户名加载查找信息")
    @RequestMapping(value="findByUsername",method = RequestMethod.POST)
    public BaseResponse findByUsername(@RequestBody SysUser entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysUserService.getByUsername(entity.getUsername()));
    }



    /**
     * 加载用户表列表
     * @param entity
     * @return
     */
    @ApiOperation(value = "加载用户表列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysUser entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysUserService.findPage(entity));
    }

    @ApiOperation(value = "保存用户表信息")
    @RequirePermissions("edit:sys:sysUser")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysUser entity) throws Exception {
        sysUserService.save(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",entity);
    }

    @ApiOperation(value = "删除用户表信息")
    @RequirePermissions("del:sys:sysUser")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysUser entity) throws Exception {
        sysUserService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }


}
