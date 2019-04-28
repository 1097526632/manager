package com.manage.system.websocket.entity;

import com.manage.system.core.security.SecurityUtils;

import java.io.Serializable;

/**
 * socket传输实体
 * @author wangmin
 *
 */
public class SocketData  implements Serializable{

	public static String NOTICE_USER_NUM="/app/noticeUserNum";


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private String data;

	//客户端传输对象
	private String userId;
	private String sessionId;

	private String requestId;

	public SocketData(){
		this.sessionId=SecurityUtils.getSubject().getSubjectId();
	}

	public static SocketData create(){
		SocketData socketData= new SocketData();
		return socketData;
	}

	public static SocketData create(String url,String data){
		SocketData socketData= new SocketData(url,data);
		return socketData;
	}

	public SocketData(String url, String data) {
		this.sessionId=SecurityUtils.getSubject().getSubjectId();
		this.url = url;
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
