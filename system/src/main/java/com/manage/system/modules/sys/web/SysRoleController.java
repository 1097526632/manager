package com.manage.system.modules.sys.web;

import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.entity.SysOffice;
import com.manage.system.modules.sys.entity.SysUser;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysRole;
import com.manage.system.modules.sys.service.SysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 角色表Controller
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysRole")
public class SysRoleController extends SystemBaseController {

    @Autowired
    private SysRoleService sysRoleService;
    /**
    * 加载角色表列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载角色表列表")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysRole entity){
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysRoleService.findPage(entity));
    }

    @ApiOperation(value = "保存角色表信息")
    @RequirePermissions("edit:sys:sysRole")
    @RequestMapping(value="save",method = RequestMethod.POST)
    public BaseResponse save(@RequestBody SysRole entity) throws Exception {
        sysRoleService.save(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",entity);
    }

    @ApiOperation(value = "删除角色表信息")
    @RequirePermissions("del:sys:sysRole")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysRole entity) throws Exception {
        sysRoleService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }

    @ApiOperation(value = "加载角色菜单信息")
    @RequestMapping(value="loadRoleMenu",method = RequestMethod.POST)
    public BaseResponse loadRoleMenu(@RequestBody SysRole entity){
        List<SysRole> menuList = sysRoleService.loadRoleMenu(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",menuList);
    }

    @ApiOperation(value = "加载角色机构信息")
    @RequestMapping(value="loadRoleOffice",method = RequestMethod.POST)
    public BaseResponse loadRoleOffice(@RequestBody SysRole entity) {
        List<SysOffice> officeList = sysRoleService.loadRoleOffice(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",officeList);
    }

    @ApiOperation(value = "加载角色用户信息")
    @RequestMapping(value="loadExistUser",method = RequestMethod.POST)
    public BaseResponse loadExistUser(@RequestBody SysRole entity){
        List<SysUser> userList = sysRoleService.loadExistUser(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",userList);
    }

    @ApiOperation(value = "加载角色机构信息")
    @RequestMapping(value="saveRoleUser",method = RequestMethod.POST)
    public BaseResponse saveRoleUser(@RequestBody SysRole entity) throws Exception {
        sysRoleService.saveRoleUser(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功");
    }

}

