package com.manage.system.modules.sys.utils.vo;

import com.manage.system.common.utils.StringUtils;

import java.util.*;

public class FileTypeUtils {
	private static Map<String,List<String>> map=new HashMap<String,List<String>>();
	private static Map<String,String> cssMap=new HashMap<String,String>();

	   static{
		   List<String> zipList=new ArrayList<String>();
		   zipList.add("rar");
		   zipList.add("zip");
		   zipList.add("7z");
		   zipList.add("gz");
		   zipList.add("tar");
		   map.put("zip", zipList);
		   List<String> imageList=new ArrayList<String>();
		   imageList.add("gif");
		   imageList.add("png");
		   imageList.add("jpg");
		   imageList.add("jpeg");
		   imageList.add("bmp");
		   imageList.add("psd");
		   map.put("image", imageList);
		   List<String> pdfList=new ArrayList<String>();
		   pdfList.add("pdf");
		   map.put("pdf", pdfList);
		   List<String> txtList=new ArrayList<String>();
		   txtList.add("txt");
		   txtList.add("md");
		   txtList.add("json");
		   txtList.add("htm");
		   txtList.add("xml");
		   txtList.add("html");
		   txtList.add("js");
		   txtList.add("css");
		   txtList.add("php");
		   txtList.add("jsp");
		   txtList.add("asp");
		   map.put("txt", txtList);
		   List<String> docList=new ArrayList<String>();
		   docList.add("doc");
		   docList.add("docx");
		   map.put("doc", docList);
		   List<String> xlsList=new ArrayList<String>();
		   xlsList.add("xlsx");
		   xlsList.add("xls");
		   map.put("xls", xlsList);
		   List<String> pptList=new ArrayList<String>();
		   pptList.add("ppt");
		   pptList.add("pptx");
		   map.put("ppt", pptList);
		   List<String> vsdtList=new ArrayList<String>();
		   vsdtList.add("vsd");
		   map.put("vsd",vsdtList );
		   List<String> apkList=new ArrayList<String>();
		   apkList.add("apk");
		   map.put("apk",apkList );
		   List<String> exeList=new ArrayList<String>();
		   exeList.add("exe");
		   map.put("exe",exeList );
		   List<String> ipaList=new ArrayList<String>();
		   ipaList.add("ipa");
		   map.put("ipa",ipaList );
		   List<String> mp4List=new ArrayList<String>();
		   mp4List.add("mp4");
		   mp4List.add("mov");
		   mp4List.add("mpeg");
		   mp4List.add("rm");
		   mp4List.add("rmvb");
		   mp4List.add("flv");
		   map.put("video",mp4List );

		   List<String> mp3List=new ArrayList<String>();
		   mp3List.add("wav");
		   mp3List.add("wmv");
		   mp3List.add("mid");
		   mp3List.add("rm");
		   mp3List.add("mp3");
		   map.put("music",mp3List );





		   cssMap.put("zip", "ufui-file-icon-rar");
		   cssMap.put("image", "ufui-file-icon-jpg");
		   cssMap.put("txt", "ufui-file-icon-txt");
		   cssMap.put("doc", "ufui-file-icon-doc");
		   cssMap.put("xls", "ufui-file-icon-xls");
		   cssMap.put("ppt", "ufui-file-icon-ppt");
		   cssMap.put("pdf", "ufui-file-icon-pdf");
		   cssMap.put("unknow", "ufui-file-icon-file");
		   cssMap.put("apk", "ufui-file-icon-apk");
		   cssMap.put("vsd", "ufui-file-icon-vsd");
		   cssMap.put("exe", "ufui-file-icon-exe");
		   cssMap.put("ipa", "ufui-file-icon-ipa");
		   cssMap.put("video", "ufui-file-icon-mp4");
		   cssMap.put("music", "ufui-file-icon-wav");
	   }

	   public static String getFileExt(String fileName) {
		   String extType=fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
		   if(StringUtils.isNotBlank(extType)){
			   extType=extType.substring(1);
		   }
			return extType;
		}

	   /**
	    * 去掉后缀文件名称
	    * @param fileName
	    * @return
	    */
	   public static String getFileName(String fileName) {
		   if(fileName.indexOf(".")>0){
			   return fileName.substring(0,fileName.lastIndexOf(".")).toLowerCase();
		   }
			return fileName;
		}



	   public  static String getFileType(String fileName){
		   String fileType=getFileExt(fileName);
		   if(fileType.startsWith(".")){
			   fileType=fileType.substring(1);
		   }
		   Set<String> keySet=map.keySet();
		   Iterator<String> iter=keySet.iterator();
		   while(iter.hasNext()){
			   String key=iter.next();
			   List<String> list=map.get(key);
			   if(list.contains(fileType)){
				   return key;
			   }
		   }

		   return "unknow";
	   }

	   public static String getCssClass(String fileType){
		   return cssMap.get(fileType);
	   }


}
