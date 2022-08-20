package com.atguigu.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author hxld
 * @create 2022-08-20 11:27
 */
@Component
public class Topic_Consumer {
    @JmsListener(destination = "${mytopic}")

    public void receive(TextMessage text) throws Exception {
        System.out.println("消费者收到订阅的主题：" + text.getText());

    }
}
