package com.atguigu.ack;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author hxld
 * @create 2022-08-18 21:01
 */
public class JmsProduce_ACK {
    //    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
    public static final String ACTIVEMQ_URL = "tcp://192.168.76.100:61616";
    public static final String QUEUE_NAME = "queue01";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话session 第一个参数是事务，第二个参数是签收
        //非事务状态下
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //事务状态下，签收与生产者关系不大，第二个签收参数都可以不写
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5. 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6.通过使用  MessageProducer  生产3条消息发送到mq队列里面
        for (int i = 1; i <= 3 ; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage(" tx  msg ---" + i);
            //8.通过  messageProducer 发送给mq
            messageProducer.send(textMessage);
        }
        //9. 关闭资源   正着生产，倒着关闭
        messageProducer.close();
        //事务状态下
        session.commit();
        session.close();
        connection.close();
        System.out.println("消息发送成功");


    }
}
