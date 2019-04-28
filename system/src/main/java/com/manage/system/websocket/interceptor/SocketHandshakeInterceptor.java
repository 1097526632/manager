package com.manage.system.websocket.interceptor;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.manage.system.core.security.Principal;
import com.manage.system.core.security.SecurityUtils;
import com.manage.system.core.security.Subject;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


public class SocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor  {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
			SecurityUtils.set(Subject.getSubject(session.getId()));
        }
        return SecurityUtils.getSubject().isAuth();
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}

}
