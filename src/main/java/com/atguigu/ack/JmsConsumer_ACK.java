package com.atguigu.ack;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author hxld
 * @create 2022-08-18 21:01
 */

/**
 * 事务如果是true，要写commit
 * 签收如果是手动签收，要写acknowledge
 */
public class JmsConsumer_ACK {
    public static final String ACTIVEMQ_URL = "tcp://192.168.76.100:61616";
    public static final String QUEUE_NAME = "queue01";
    public static void main(String[] args) throws JMSException, IOException {
        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话session
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //非事务状态下设置手动签收
//        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        //事务状态下，手动签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5. 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者接收到消息："+textMessage.getText());
                        //手动签收后要设置对消息的反馈
//                        textMessage.acknowledge();

                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.commit();
        session.close();
        connection.close();

    }
}
