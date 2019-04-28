package com.manage.system.common.utils;

import com.manage.system.common.utils.http.HttpRequestUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpUtils {

	private static final String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
			+"|windows (phone|ce)|blackberry"
			+"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
			+"|laystation portable)|nokia|fennec|htc[-_]"
			+"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	private static final String tabletReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

	//移动设备正则匹配：手机端、平板
	private static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
	private static Pattern tabletPat = Pattern.compile(tabletReg, Pattern.CASE_INSENSITIVE);

	public static boolean isAjax(HttpServletRequest request){
		String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1)
		{
			return true;
		}

		String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
		{
			return true;
		}

		return false;
	}

	/**
	 * 获取绝对地址
	 * @param request
	 * @return
	 */
	public static String getAbsoluteUrl(HttpServletRequest request){
		return request.getScheme()+"://" + request.getServerName() //服务器地址
				+("80".equals(request.getServerPort())?"":":"+request.getServerPort() )
				+ request.getContextPath()      //项目名称
				+ request.getServletPath()      //请求页面或其他地址
				+ "?" + (request.getQueryString()); //参数
	}

	/**
	 * 获取主机地址
	 * @param request
	 * @return
	 */
	public static String getServerName(HttpServletRequest request){
		return request.getServerName();
	}

	/**
	 * 获取应用名称
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request){
		return request.getContextPath();
	}

	/***
	 *  true:already in using  false:not using
	 * @param port
	 */
	public static boolean isLoclePortUsing(int port){
		boolean flag = true;
		try {
			flag = isPortUsing("127.0.0.1", port);
		} catch (Exception e) {
		}
		return flag;
	}
	/***
	 *  true:already in using  false:not using
	 * @param host
	 * @param port
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unused")
	public static boolean isPortUsing(String host, int port) throws UnknownHostException {
		boolean flag = false;
		InetAddress theAddress = InetAddress.getByName(host);
		try {
			Socket socket = new Socket(theAddress,port);
			flag = true;
		} catch (IOException e) {

		}
		return flag;
	}

	/**
	 * 获取请求的参数
	 * @param request
	 * @return
	 */
	public static Map<String,String> getRequestParams(HttpServletRequest request) {
		Map<String,String> params=new HashMap<String,String>();
		Enumeration paramNames = request.getParameterNames();
		if(paramNames!=null){
			while(paramNames.hasMoreElements()){
				String paramName = (String) paramNames.nextElement();
				String paramValue=Encodes.urlDecode(Encodes.urlDecode(request.getParameter(paramName)));
				params.put(paramName,paramValue);
			}
		}
		return params;
	}

	public static boolean isIE(HttpServletRequest request) {
		return (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0
				|| request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0") > 0
				|| request.getHeader("USER-AGENT").toLowerCase().indexOf("edge") > 0) ? true
				: false;
	}

	/**
	 * 判断是否移动端
	 * @param request
	 * @return
	 */
    public static boolean isMobile(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		if(null == userAgent){
			userAgent = "";
		}
		return phonePat.matcher(userAgent).find() || tabletPat.matcher(userAgent).find();
    }

    public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	public static String getHttpRequestStream(HttpServletRequest request){
		String data="";

		try {
			InputStream inputStream= request.getInputStream();
			data= IOUtils.toString(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * get信息
	 * @param url
	 * @param params
	 * @param header
	 * @return
	 */
	public static String get(String url, Map<String,Object> params, Map<String,String> header){
		Map<String,String> paramStr=new HashMap<String,String>();
		if(params!=null){
			for(Map.Entry<String, Object> entry:params.entrySet()){
				paramStr.put(entry.getKey(),entry.getValue()+"");
			}
		}
		return HttpRequestUtils.get(url,paramStr,header);
	}

	/**
	 * post提交
	 * @param url
	 * @param params
	 * @param header
	 * @param json
	 * @return
	 */
	public static String post(String url, Map<String,Object> params, Map<String,String> header, boolean json){
		if(json){
			return HttpRequestUtils.postJson(url,params,header);
		}else{
			Map<String,String> paramStr=new HashMap<String,String>();
			if(params!=null){
				for(Map.Entry<String, Object> entry:params.entrySet()){
					paramStr.put(entry.getKey(),entry.getValue()+"");
				}
			}
			return HttpRequestUtils.post(url,paramStr,header);
		}

	}


	/**
	 * 获取user-agent
	 * @param request
	 * @return
	 */
    public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("user-agent");
    }

	/**
	 * 获取所有头信息
	 * @param request
	 * @return
	 */
	public static Map<String,String> getHeaders(HttpServletRequest request) {
		Enumeration<String> headerNames=request.getHeaderNames();
		Map<String,String> result=new HashMap<String,String>();
		while(headerNames.hasMoreElements()){
			String name=headerNames.nextElement();
			result.put(name,request.getHeader(name));
		}
		return result;
	}
}
