package com.atguigu.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @author hxld
 * @create 2022-08-20 9:26
 */

//类似spring框架的applicationContext.xml文件
@Component
@EnableJms
public class ConfigBean {
    @Value("${myqueue}")
    private String myQueue;

    @Bean   //<bean id= class= >
    public Queue queue(){

        return new ActiveMQQueue(myQueue);
    }
}
