package com.manage.system.modules.sys.web;

import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.entity.SysFileCategory;
import com.manage.system.modules.sys.utils.FileUploadUtils;
import com.manage.system.modules.sys.utils.UserUtils;
import com.manage.system.modules.sys.web.SystemBaseController;
import com.manage.system.core.security.RequirePermissions;
import com.manage.system.modules.sys.entity.SysFileInfo;
import com.manage.system.modules.sys.service.SysFileInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
* SysFileInfoController
*/
@RestController
@RequestMapping("${app.auth.adminurl}/sys/sysFileInfo")
public class SysFileInfoController extends SystemBaseController {

    @Autowired
    private SysFileInfoService sysFileInfoService;
    /**
    * 加载SysFileInfo列表
    * @param entity
    * @return
    */
    @ApiOperation(value = "加载SysFileInfo列表")
    @RequirePermissions("view:sys:sysFileInfo")
    @RequestMapping(value="loadList",method = RequestMethod.POST)
    public BaseResponse list(@RequestBody SysFileInfo entity){
        entity.setCreateBy(UserUtils.getInstance().getUser().getId());
        return new BaseResponse(BaseResponse.SUCCESS,"加载信息成功",sysFileInfoService.findList(entity));
    }

    @ApiOperation(value = "保存SysFileInfo信息")
    @RequirePermissions("edit:sys:sysFileInfo")
    @RequestMapping(value="save")
    public BaseResponse save(HttpServletRequest request) throws Exception {
        SysFileInfo sysFileInfo= FileUploadUtils.uploadFile(request);
        String categoryId =request.getParameter("categoryId");
        String fileId=request.getParameter("id");
        if(StringUtils.isNotBlank(fileId)){
            SysFileInfo fileInfo=sysFileInfoService.get(fileId);
            fileInfo.setRealName(sysFileInfo.getRealName());
            sysFileInfoService.save(sysFileInfo);
        } else{
            if(StringUtils.isNotBlank(categoryId)){
                sysFileInfo.setCategoryId(categoryId);
                sysFileInfoService.save(sysFileInfo);
            }
        }

        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",sysFileInfo);
    }

    @ApiOperation(value = "变更文件目录")
    @RequirePermissions("edit:sys:sysFileInfo")
    @RequestMapping(value="changeCategory",method = RequestMethod.POST)
    public BaseResponse changeCategory(@RequestBody SysFileInfo sysFileInfo) throws Exception {
        if(StringUtils.isNotBlank(sysFileInfo.getCategoryId())&&StringUtils.isNotBlank(sysFileInfo.getId())){
            SysFileInfo oldFile= sysFileInfoService.get(sysFileInfo);
            oldFile.setCategoryId(sysFileInfo.getCategoryId());
            sysFileInfoService.save(oldFile);
        }
        return new BaseResponse(BaseResponse.SUCCESS,"保存信息成功",sysFileInfo);
    }

    @ApiOperation(value = "删除SysFileInfo信息")
    @RequirePermissions("del:sys:sysFileInfo")
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public BaseResponse delete(@RequestBody SysFileInfo entity) throws Exception {
        sysFileInfoService.delete(entity);
        return new BaseResponse(BaseResponse.SUCCESS,"删除信息成功",entity);
    }
}

