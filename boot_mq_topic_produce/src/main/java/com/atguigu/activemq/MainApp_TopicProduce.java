package com.atguigu.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hxld
 * @create 2022-08-20 11:15
 */
@SpringBootApplication
@EnableScheduling
public class MainApp_TopicProduce {
    public static void main(String[] args) {
        SpringApplication.run(MainApp_TopicProduce.class,args);
    }
}
