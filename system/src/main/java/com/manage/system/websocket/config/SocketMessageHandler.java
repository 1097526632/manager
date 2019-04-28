package com.manage.system.websocket.config;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import com.manage.system.common.utils.CollectionUtils;
import com.manage.system.common.utils.JsonUtils;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.websocket.entity.SocketData;
import com.manage.system.websocket.entity.WebSocketHolder;
import com.manage.system.websocket.service.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


public class SocketMessageHandler extends TextWebSocketHandler {
	private static Logger logger = LoggerFactory.getLogger(SocketMessageHandler.class);
	private static final Map<String,WebSocketHolder> connections=new HashMap<String,WebSocketHolder>();

	private SocketService socketService= SpringContextHolder.getBean(SocketService.class);

	public SocketMessageHandler() {
	}

	/**
	 * 连接成功时候，会触发UI上onopen方法
	 */
	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		connections.put(session.getId(),WebSocketHolder.create(session));
    }

	/**
	 * 在UI在用js调用websocket.send()时候，会调用该方法
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		if(StringUtils.isNotBlank(message.getPayload())){
			logger.info("接收信息为："+message);
			try {
				SocketData sd= JsonUtils.parseJson(message.getPayload(),SocketData.class);
				if(StringUtils.isNotBlank(sd.getUserId())&&(StringUtils.isNotBlank(sd.getSessionId()))
						&&(StringUtils.isNotBlank(sd.getUrl()))){
					WebSocketHolder holder= connections.get(session.getId());
					holder.setLastVisitDate(new Date());
					socketService.handlerMsg(sd,holder);
				}else{
					logger.info("信息格式不符合要求，放弃请求");
				}

			} catch (Exception e) {
				logger.error("数据异常:"+e.getMessage());
			}
		}
	}

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        connections.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        connections.remove(session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

	public static Map<String,WebSocketHolder> connections() {
		return connections;
	}
}
