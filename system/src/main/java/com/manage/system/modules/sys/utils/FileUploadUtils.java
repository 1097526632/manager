package com.manage.system.modules.sys.utils;

import com.manage.system.common.config.Global;
import com.manage.system.common.utils.DateUtils;
import com.manage.system.common.utils.FileUtils;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.entity.BaseResponse;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.core.store.StoreManage;
import com.manage.system.modules.sys.entity.SysFileCategory;
import com.manage.system.modules.sys.entity.SysFileInfo;
import com.manage.system.modules.sys.service.SysFileCategoryService;
import com.manage.system.modules.sys.service.SysFileInfoService;
import com.manage.system.modules.sys.utils.vo.FileTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 文件上传工具类
 * @author wangmin
 *
 */
public class FileUploadUtils {
	private static Logger log= LoggerFactory.getLogger(FileUploadUtils.class);
	private static SysFileInfoService fileInfoService= SpringContextHolder.getBean(SysFileInfoService.class);
	private static SysFileCategoryService fileCategoryService=SpringContextHolder.getBean(SysFileCategoryService.class);

	public static Map<String,String> upload(HttpServletRequest request) throws Exception {
	     String noSave=request.getParameter("noSave");
	     String categoryId=request.getParameter("categoryId");
	     String userOtherFile="other_file_upload_"+ UserUtils.getInstance().getUser().getId();
	     String fileCategoryId=StringUtils.isBlank(categoryId)? userOtherFile:categoryId+"_"+UserUtils.getInstance().getUser().getId();
		SysFileCategory category=fileCategoryService.get(fileCategoryId);
		if(category==null){
			category=new SysFileCategory();
			String categoryName=userOtherFile.equalsIgnoreCase(fileCategoryId)?"其他文件上传":
					StringUtils.isNotBlank(request.getParameter("categoryName"))?request.getParameter("categoryName"):"无分类";
			category.setName(categoryName);
			category.setParentId("0");
			category.setParentIds("0");
			category.setParentNames("");
			category.setIsNewRecord(true);
			category.setId(fileCategoryId);
			fileCategoryService.save(category);//保存分类
		}
		Map<String,String> result=new HashMap<String,String>();

		SysFileInfo sysFileInfo= uploadFile(request);
		sysFileInfo.setCategoryId(category.getId());
		if(StringUtils.isBlank(noSave)||"yes".equalsIgnoreCase(noSave)){
			fileInfoService.save(sysFileInfo);
		}
		result.put("fileName", sysFileInfo.getName());
		result.put("realName", sysFileInfo.getRealName());
	     return result;
	}

	public static SysFileInfo uploadFile(HttpServletRequest request) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap =multipartRequest.getFileMap();
		String saveDirectory = Global.getUserfilesBaseDir();
		String datePath=Global.getDatePath();
		String datePathName=(UserUtils.getInstance().getUser()!=null?UserUtils.getInstance().getUser().getId():"" )+"/";
		SimpleDateFormat sdf=null;
		String realName=request.getParameter("realName");
		datePathName=datePathName+ (StringUtils.isNotBlank(datePath) ? DateUtils.formatDate(new Date(),datePath):"");
		saveDirectory=saveDirectory+"/"+datePathName;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();
			String oriFileName = file.getOriginalFilename(); // 源文件名称
			String fileName= UUID.randomUUID().toString().replaceAll("-", "")+ FileUtils.getFileExt(oriFileName);
			String fileKey=saveDirectory+"/"+fileName;
			if(StringUtils.isNotBlank(realName)){
				File destFile=new File(Global.getUserfilesBaseDir()+"/"+realName);
				if(destFile.exists()){
					FileUtils.copyFile(destFile,new File(Global.getUserfilesBaseDir()+"/"+realName+".del"));
					destFile.deleteOnExit();
				}
				fileKey=Global.getUserfilesBaseDir()+"/"+realName;
			}else{
				realName=datePathName+"/"+fileName;
			}
			StoreManage.getInstance().getStoreEngine().save(fileKey,file.getInputStream());

			SysFileInfo fileInfo=new SysFileInfo();
			fileInfo.setName(oriFileName);
			fileInfo.setRealName(realName);
			fileInfo.setExtType(FileTypeUtils.getFileExt(oriFileName));
			String type=FileTypeUtils.getFileType(oriFileName);
			fileInfo.setFileType(type);
			fileInfo.setFileSize((file.getSize()/1024.0)+"");
			fileInfo.setCssClass(FileTypeUtils.getCssClass(type));
			fileInfo.setCreateBy(UserUtils.getInstance().getUser().getId());
			fileInfo.setUpdateBy(UserUtils.getInstance().getUser().getId());
			return fileInfo;
		}
		return null;
	}

}
