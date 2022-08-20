package com.atguigu.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.util.UUID;

/**
 * @author hxld
 * @create 2022-08-20 9:27
 */
@Component
public class Queue_Produce {

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;
    //触发投递----工作中常用

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"****:"+UUID.randomUUID().toString().substring(0,6));
    }


    //间隔定投----工作中常用
    //间隔时间3秒钟定投
    @Scheduled(fixedDelay =  3000)
    public void produceMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(queue,"****Scheduled:"+UUID.randomUUID().toString().substring(0,6));
        System.out.println("produceMsgScheduled send ok");

    }
}
