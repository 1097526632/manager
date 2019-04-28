package com.manage.system.websocket.config;

import com.manage.system.websocket.interceptor.SocketHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(messageHandler(),"/ws").setAllowedOrigins("*").addInterceptors(new SocketHandshakeInterceptor());
    }

    @Bean
    public TextWebSocketHandler messageHandler(){
        return new SocketMessageHandler();
    }
}

