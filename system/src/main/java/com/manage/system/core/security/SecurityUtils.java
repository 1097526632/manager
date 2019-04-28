package com.manage.system.core.security;

public class SecurityUtils {

    private static ThreadLocal<Subject> subjectThreadLocal=new ThreadLocal<Subject>();

    public static Subject getSubject() {
        return subjectThreadLocal.get();
    }

    public static void set(Subject subject){
        subjectThreadLocal.set(subject);
    }
}
