package com.manage.system.websocket.utils;

import com.manage.system.common.utils.JsonUtils;
import com.manage.system.websocket.entity.SocketData;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class SocketMessageUtils {

    /**
     * 发送消息
     * @param socketData
     * @param websocket
     */
    public static void sendMsg(SocketData socketData, WebSocketSession websocket ) {
        try {
            websocket.sendMessage(new TextMessage(JsonUtils.toJson(socketData)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
