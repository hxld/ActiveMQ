package com.atguigu.topic.topicpersist;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author hxld
 * @create 2022-08-18 16:30
 */
public class JmsConsumer_Topic_Persist {
//    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
    public static final String ACTIVEMQ_URL = "tcp://192.168.76.100:61616";
    public static final String TOPIC_NAME = "topic-Persist";

    public static void main(String[] args) throws JMSException, IOException {
//        System.out.println("*****z3");
        System.out.println("*****z4");

        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        //z3订阅
//        connection.setClientID("z3");
        connection.setClientID("z4");

        //3.创建会话session
        //两个参数，第一个叫做事务/第二个叫做签收（boolean b,int i）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //设置持久化订阅者
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark....");
       //启动连接
        connection.start();

        //订阅者收取消息
        Message message = topicSubscriber.receive();
        while(message != null){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("*******收到的持久化topic:"+textMessage.getText());
            //设置等待时间为1秒，收到消息之后，message = null ,跳出循环，关闭资源。
//            message = topicSubscriber.receive(1000L);
            //z3不等待
//            message = topicSubscriber.receive();
            //z4等待3秒
            message = topicSubscriber.receive(3000L);


        }
        session.close();
        connection.close();

    }
}
