package com.manage.system.websocket.web;

import com.manage.system.websocket.annotation.SocketUrlMapper;
import com.manage.system.websocket.config.MessageHandlerInterface;
import com.manage.system.websocket.entity.SocketData;
import com.manage.system.websocket.entity.WebSocketHolder;
import com.manage.system.websocket.utils.SocketMessageUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

@Component
@SocketUrlMapper(value = "app")
public class AppMessageHandler implements MessageHandlerInterface {

    @SocketUrlMapper(value = "heartJump")
    public void heartJump(SocketData socketData, WebSocketHolder webSocketSession){
        webSocketSession.setLastVisitDate(new Date());
        SocketMessageUtils.sendMsg(socketData,webSocketSession.getWebSocketSession());
    }

}
