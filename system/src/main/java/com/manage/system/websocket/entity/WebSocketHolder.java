package com.manage.system.websocket.entity;

import com.manage.system.common.utils.DateUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

public class WebSocketHolder {
    private Date lastVisitDate;
    private WebSocketSession webSocketSession;

    public WebSocketHolder(){

    }

    public WebSocketHolder( WebSocketSession webSocketSession,Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
        this.webSocketSession = webSocketSession;
    }

    public static WebSocketHolder create(WebSocketSession webSocketSession){
        return new WebSocketHolder(webSocketSession,new Date());
    }

    public boolean isTimeout(){
        if(lastVisitDate!=null&& DateUtils.diffSecond(new Date(),lastVisitDate)>60*2){
            return true;
        }
        return false;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
