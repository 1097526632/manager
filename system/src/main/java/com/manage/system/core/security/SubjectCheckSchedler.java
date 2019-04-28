package com.manage.system.core.security;

public class SubjectCheckSchedler {

    public static void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000*60*5);
                        SecurityCacheManage.getInstance().clearTimeoutSubject();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
