package com.atguigu.activemq.topic;


import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Topic;
import java.util.UUID;

/**
 * @author hxld
 * @create 2022-08-20 11:12
 */
@Component
public class Topic_Produce {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource
    private Topic topic;

    @Scheduled(fixedDelay = 3000)
    public void produceTopic(){
        jmsMessagingTemplate.convertAndSend(topic,"主题消息："+ UUID.randomUUID().toString().substring(0,6));
//        System.out.println("主题消息产生："+UUID.randomUUID().toString().substring(0,6));

    }

}
