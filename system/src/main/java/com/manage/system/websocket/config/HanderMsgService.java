package com.manage.system.websocket.config;

import java.lang.reflect.Method;
import java.util.*;

import com.manage.system.common.utils.Reflections;
import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.common.utils.StringUtils;
import com.manage.system.core.config.AfterRun;
import com.manage.system.websocket.annotation.SocketUrlMapper;
import com.manage.system.websocket.entity.ObjectMethod;
import com.manage.system.websocket.entity.SocketData;
import com.manage.system.websocket.entity.WebSocketHolder;
import com.manage.system.websocket.service.SocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * 处理消息服务类
 * @author wangmin
 *
 */
@Component
public class HanderMsgService implements AfterRun {
	private static Map<String, ObjectMethod> handlerUrl=new HashMap<String,ObjectMethod>();

	@Autowired
	private SocketService socketService;

	public void handlerMsg(SocketData sd, WebSocketHolder ws) throws Exception {

		ObjectMethod objectMethod =handlerUrl.get(StringUtils.formatUrl("/"+sd.getUrl()));
		if(objectMethod!=null){
			Reflections.invokeMethod(objectMethod.getObject(),objectMethod.getMethod(),new Object[]{sd,ws});
		}else {
			throw new Exception("不存在的地址:"+sd.getUrl());
		}
	}

	@Override
	public void afterRun() {
		List<MessageHandlerInterface> messageHandlerInterfaceList= SpringContextHolder.getInterfaceBeans(MessageHandlerInterface.class);
		for(MessageHandlerInterface messageHandler:messageHandlerInterfaceList){
			String baseUrl="";
			if(messageHandler.getClass().getAnnotation(SocketUrlMapper.class)!=null){
				SocketUrlMapper socketUrlMapper=messageHandler.getClass().getAnnotation(SocketUrlMapper.class);
				baseUrl=socketUrlMapper.value();
			}
			Method[] methods= messageHandler.getClass().getMethods();
			if(methods!=null&&methods.length>0){
				for(Method method:methods){
					if(method.getAnnotation(SocketUrlMapper.class)!=null){
						SocketUrlMapper socketUrlMapper=method.getAnnotation(SocketUrlMapper.class);
						handlerUrl.put(StringUtils.formatUrl("/"+baseUrl+"/"+socketUrlMapper.value()),new ObjectMethod(messageHandler,method));
					}
				}
			}
		}
		checkSocket();
	}

	private void checkSocket(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						socketService.clearSocket(); //清理socket
						Thread.sleep(30*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
