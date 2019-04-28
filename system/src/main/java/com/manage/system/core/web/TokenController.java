package com.manage.system.core.web;

import com.manage.system.common.utils.IdGen;
import com.manage.system.common.utils.JsonUtils;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.entity.BaseResponse;
import com.manage.system.core.security.Principal;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.core.security.Subject;
import com.manage.system.core.security.UserAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${app.auth.adminurl}/token")
@Api(value = "${app.auth.adminurl}/token", description = "token相关接口信息")
public class TokenController extends BaseController {

    @Autowired
    private UserAuth userAuth;

    /**
     * 获取token信息
     * @return
     */
    @ApiOperation(value="获取token信息", notes="首次加载时需要获取token检验用户是否登录")
    @RequestMapping(value = "getToken",method = RequestMethod.POST)
    public BaseResponse getToken(){
        Subject subject=SecurityUtils.getSubject();
        BaseResponse baseResponse=BaseResponse.createDefault(false);
        System.out.println("token: "+JsonUtils.toJson(subject));
        if(subject.isAuth()){
            baseResponse.setCode(BaseResponse.SUCCESS);
            baseResponse.setDesc("获取信息成功");
            baseResponse.setData(subject.getPrincipal());
        }
        return baseResponse;
    }

    /**
     * 用户授权登录
     * @param principal
     * @return
     */
    @RequestMapping(value="authUser",method = RequestMethod.POST)
    @ApiOperation(value="用户授权登录")
    public BaseResponse authUser(@RequestBody Principal principal, HttpServletRequest request) throws Exception {
        BaseResponse baseResponse=BaseResponse.createDefault(false);
        Principal authPrincipal=userAuth.authUser(principal,request);
        if(authPrincipal!=null){//登录成功
            Subject subject= SecurityUtils.getSubject();
            authPrincipal.setPassword("");//清空密码
            subject.setPrincipal(authPrincipal);

            if(StringUtils.isBlank(principal.getId())){
                authPrincipal.setId(IdGen.uuid());
            }
            subject.saveSubject();
            System.out.println(JsonUtils.toJson(subject));
            baseResponse.setCode(BaseResponse.SUCCESS);
            baseResponse.setDesc("用户登录成功");
            baseResponse.setData(authPrincipal);
        }else{
            baseResponse.setCode(BaseResponse.FAIL);
            baseResponse.setDesc("用户名或密码错误");
        }
        return baseResponse;
    }

    @RequestMapping(value = "logout",method = RequestMethod.POST)
    @ApiOperation(value = "用户退出")
    public BaseResponse logout(HttpServletRequest request) throws Exception {
        userAuth.userLogout(request);
        SecurityUtils.getSubject().removePrincipal();
        return BaseResponse.createDefault(true);
    }

}
