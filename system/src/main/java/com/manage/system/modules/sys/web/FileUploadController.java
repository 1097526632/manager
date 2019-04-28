package com.manage.system.modules.sys.web;

import com.manage.system.common.utils.JsonUtils;
import com.manage.system.core.entity.BaseResponse;
import com.manage.system.modules.sys.utils.FileUploadUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 上传文件
 * @author wm
 *
 */
@RestController
@RequestMapping(value = "${app.auth.adminurl}/uploadFile")
public class FileUploadController extends SystemBaseController {

	@RequestMapping(value="upload",method = RequestMethod.POST)
	public BaseResponse uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
        Map<String,String> result=new HashMap<String,String>();
		BaseResponse baseResponse=null;
        try {
        	result= FileUploadUtils.upload(request);
			baseResponse=BaseResponse.createDefault(true);
			baseResponse.setData(result);
        } catch (Exception exception) {
			baseResponse=BaseResponse.createDefault(false);
        }
        return baseResponse;
	}
}
