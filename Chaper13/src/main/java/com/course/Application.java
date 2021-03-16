package com.course;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

@EnableScheduling //开启定时任务,开启功能，自动扫描
@SpringBootApplication //SpringBootApplication注解标记当前类为SpringBoot的引导启动类
//@EnableSwagger2
public class Application {
    private static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        Application.context =SpringApplication.run(Application.class,args);

    }
    @PreDestroy
    public void close(){
        Application.context.close();
    }
}
