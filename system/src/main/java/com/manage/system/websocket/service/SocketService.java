package com.manage.system.websocket.service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.manage.system.common.utils.JsonUtils;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.modules.sys.utils.UserUtils;
import com.manage.system.websocket.config.HanderMsgService;
import com.manage.system.websocket.config.SocketMessageHandler;
import com.manage.system.websocket.entity.SocketData;
import com.manage.system.websocket.entity.WebSocketHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


@Component
public class SocketService {
	@SuppressWarnings("unused")
	private static Logger log=LoggerFactory.getLogger(SocketService.class);
	//userId,sessionId
	private static Map<String,ConcurrentHashMap<String,List<WebSocketHolder>>> userSocket=new ConcurrentHashMap<String, ConcurrentHashMap<String,List<WebSocketHolder>>>();

	@Autowired
	private HanderMsgService handerMsgService;

	/**
	 * 发送信息
	 * @param text
	 */
	public void sendMsg( String text,WebSocketSession websocket ) {
		try {
			SocketData socketData=new SocketData();
			socketData.setSessionId(websocket.getId());
			socketData.setData(text);
			websocket.sendMessage(new TextMessage(JsonUtils.toJson(socketData)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给用户发送信息
	 * @param userId
	 * @param msg
	 */
	public void sendUserMsg(String userId,Object msg){
		ConcurrentHashMap<String,List<WebSocketHolder>> map=userSocket.get(userId);
		if(map!=null){
			SocketData socketData=new SocketData();
			socketData.setSessionId(SecurityUtils.getSubject().getSubjectId());
			socketData.setUserId(userId);
			socketData.setData(JsonUtils.toJson(msg));
			Set<String> set=map.keySet();
			Iterator<String> iter=set.iterator();
			while(iter.hasNext()){
				String key=iter.next(); //  userId
				if(key.equalsIgnoreCase(userId)){
					List<WebSocketHolder> wsList=map.get(key);
					Iterator<WebSocketHolder> wsIter=wsList.iterator();
					while(wsIter.hasNext()){
						WebSocketHolder ws=wsIter.next();
						if(!ws.getWebSocketSession().isOpen()||ws.isTimeout()){//判断是否关闭，如果已关闭则从userSokcet移除
							wsIter.remove();
						}else{
							sendMsg(JsonUtils.toJson(msg), ws.getWebSocketSession());
						}
					}
				}
			}
		}
	}

	public void clearSocket(){
		Set<String> set=userSocket.keySet();
		Iterator<String> iter=set.iterator();
		while(iter.hasNext()){
			String userId =iter.next();
			clearSocket(userId,"");
		}
	}

	private void clearSocket(String userId,String sessionId){
		ConcurrentHashMap<String,List<WebSocketHolder>> map=userSocket.get(userId);
		if(map!=null){
			Set<String> set=map.keySet();
			Iterator<String> iter=set.iterator();
			while(iter.hasNext()){
				String key=iter.next(); //  sessionId
				if(StringUtils.isNotBlank(sessionId)&&key.equalsIgnoreCase(sessionId)){
					iter.remove();
				} else {
					List<WebSocketHolder> wsList=map.get(key);
					Iterator<WebSocketHolder> wsIter=wsList.iterator();
					while(wsIter.hasNext()){
						WebSocketHolder ws=wsIter.next();
						if(!ws.getWebSocketSession().isOpen()||ws.isTimeout()){//判断是否关闭，如果已关闭则从userSokcet移除
							wsIter.remove();
						}
					}
					if(wsList.size()==0){
						iter.remove();
					}
				}
			}
			if(map.size()==0){
				userSocket.remove(userId);
			}

		}

	}

	/**
	 * 注册用户信息
	 */
	public void registerUser(SocketData sd,WebSocketHolder socket){
		String userId=sd.getUserId();
		String sessionId=sd.getSessionId();
		clearSocket(userId,"");//清理无用的
		ConcurrentHashMap<String, List<WebSocketHolder>> map=userSocket.get(userId);
		if(map==null){
			map=new ConcurrentHashMap<String,List<WebSocketHolder>>();
			userSocket.put(userId,map);
		}
		List<WebSocketHolder> list=map.get(sessionId);
		if(list==null){
			list=new ArrayList<WebSocketHolder>();
			map.put(sessionId,list);
		}

		list.add(socket);
		sendAll(SocketData.create(SocketData.NOTICE_USER_NUM,countUsers()+"")); //通知用户上级人数
	}

	/**
	 * 移除session
	 * @param userId
	 * @param sessionId
	 */
	public void removeSession(String userId,String sessionId){
		clearSocket(userId,sessionId);
		SocketData socketData=SocketData.create(SocketData.NOTICE_USER_NUM,countUsers()+"");
		sendAll(socketData); //通知用户上级人数
	}

	/**
	 * 获取用户数
	 * @return
	 */
	public int countUsers(){
		return userSocket.size();
	}

	/**
	 * 向所有人没发送信息
	 * @param socketData
	 */
	public  void sendAll(SocketData socketData){
		Map<String,WebSocketHolder> webConnlections= SocketMessageHandler.connections();
		for(Map.Entry<String,WebSocketHolder> ws:webConnlections.entrySet()){
			sendMsg(JsonUtils.toJson(socketData), ws.getValue().getWebSocketSession());
		}
	}

	public void handlerMsg(SocketData sd, WebSocketHolder webSocketHolder){
		if(sd!=null){
			try {
				handerMsgService.handlerMsg(sd,webSocketHolder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void close(WebSocketSession conn) {
		try {
			conn.close();
			clearSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
