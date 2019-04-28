package com.manage.system.core.config;

import com.manage.system.common.utils.SpringContextHolder;
import com.manage.system.core.security.SubjectCheckSchedler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(value=1)
public class AppStartupRunner implements CommandLineRunner {

    /**
     * 执行的方法,
     * args为执行时传入的参数,
     * 可用: SpringApplication.run(App.class, new String[]{"hello,","林峰"});
     * 或者: eclipse中给java应用传args参数的方法如下：
            1、先写好Java代码，比如文件名为IntArrqy.java；
            2、在工具栏或菜单上点run as下边有个Run Configuration；
            3、在弹出窗口点选第二个标签arguments；
            4、把你想输入的参数写在program argumenst就可以了，多个参数使用空格隔开。
            完成后点run即可通过运行结果看到参数使用情况了。
     */
    @Override
    public void run(String... arg0) throws Exception {
        SubjectCheckSchedler.start();//启动检测超时信息
        List<AfterRun> afterRunList= SpringContextHolder.getInterfaceBeans(AfterRun.class);
        for(AfterRun afterRun:afterRunList){
            afterRun.afterRun();
        }
    }
}
