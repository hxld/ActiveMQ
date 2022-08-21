package com.atguigu.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.*;

/**
 * @author hxld
 * @create 2022-08-18 10:29
 */
public class JmsProduce_DelayAndSchedule {

    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        long delay = 3 *1000; //延迟3秒钟
        long period = 4 * 1000; // 时间间隔4秒钟
        int repeat = 5 ; //重复次数 5次
        for (int i = 1; i <= 3 ; i++) {
            TextMessage textMessage = session.createTextMessage("jdbcmsg ---" + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);

            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送成功");
    }
}
