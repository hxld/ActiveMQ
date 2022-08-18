package com.atguigu.topic.topicpersist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author hxld
 * @create 2022-08-18 16:30
 */

/**
 * 一定要先运行一次消费者，等于向MQ注册，类似我订阅了这个主题。
 * 然后再运行生产者发送信息，此时，无论消费者是否在线，都会接收到，
 * 不在线的话，下次连接的时候，会把没有接收过的消息都接收下来。
 */
public class JmsProduce_Topic_Persist {
//    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
        public static final String ACTIVEMQ_URL = "tcp://192.168.76.100:61616";

    public static final String TOPIC_NAME = "topic-Persist";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();

        //3.创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5. 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        //设置持久topic
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 将启动从创建连接改到设置持久之后
        connection.start();

        //6.通过使用  MessageProducer  生产3条消息发送到mq队列里面
        for (int i = 1; i <= 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME---" + i);  //理解为一个字符串
            //8.通过  messageProducer 发送给mq
            messageProducer.send(textMessage);
        }
        //9. 关闭资源   正着生产，倒着关闭
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("TOPIC-NAME消息发送到MQ完成");
    }
}