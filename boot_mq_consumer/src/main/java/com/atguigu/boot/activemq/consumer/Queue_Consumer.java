package com.atguigu.boot.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author hxld
 * @create 2022-08-20 11:01
 */
@Component
public class Queue_Consumer {
    @JmsListener(destination = "${myqueue}")  //以前spring中还要专门写一个监听类，springboot直接注解即可实现
    public void receive(TextMessage textMessage) throws JMSException{
        System.out.println("消费者收到消息："+textMessage.getText());
    }
}
