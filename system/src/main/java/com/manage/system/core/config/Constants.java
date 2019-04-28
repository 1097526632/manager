package com.manage.system.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    @Value("app.auth.key")
    public static final String LOGIN_SESSION_KEY ="LOGIN_SESSION_KEY" ;
    @Value("app.auth.adminurl")
    public static final String ADMIN_AUTH_URL="/admin";
    @Value("app.timeout")
    public static final int timeout=30;
}
