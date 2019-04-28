package com.manage.system.websocket.web;

import com.manage.system.websocket.annotation.SocketUrlMapper;
import com.manage.system.websocket.config.MessageHandlerInterface;
import com.manage.system.websocket.entity.SocketData;
import com.manage.system.websocket.entity.WebSocketHolder;
import com.manage.system.websocket.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@SocketUrlMapper("user")
public class UserMessaageHandler implements MessageHandlerInterface {

    @Autowired
    private SocketService socketService;

    /**
     * 注册用户
     * @param socketData
     * @param webSocketSession
     */
    @SocketUrlMapper("register")
    public void register(SocketData socketData, WebSocketHolder webSocketSession){
        socketService.registerUser(socketData,webSocketSession);
    }
}
