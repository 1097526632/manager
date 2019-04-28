package com.manage.system.websocket.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited  //可以继承
public @interface SocketUrlMapper {
    String value();
}
