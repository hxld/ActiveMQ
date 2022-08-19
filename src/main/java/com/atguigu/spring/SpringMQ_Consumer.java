package com.atguigu.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hxld
 * @create 2022-08-19 22:22
 */
@Service
public class SpringMQ_Consumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQ_Consumer consumer = (SpringMQ_Consumer) ctx.getBean("springMQ_Consumer");

        //  Object  --- SPring    因为生产者发送的是textMessage，是string
      String retValue = (String) consumer.jmsTemplate.receiveAndConvert();

        System.out.printf("*******消费者收到的消息："+retValue);

    }
}
