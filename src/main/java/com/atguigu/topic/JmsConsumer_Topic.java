package com.atguigu.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author hxld
 * @create 2022-08-18 16:30
 */
public class JmsConsumer_Topic {
//    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
public static final String ACTIVEMQ_URL = "tcp://192.168.76.100:61616";

    public static final String TOPIC_NAME = "topic-atguigu";

    public static void main(String[] args) throws JMSException, IOException {
//        System.out.println("我是1号消费者");
//        System.out.println("我是2号消费者");
        System.out.println("我是3号消费者");


        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码
        //为什么要创建一个全局常量，是因为如果我们在创建对象的过程中直接写地址，那就是写死了，修改不方便。而创建一个全局常量，修改方便
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一个叫做事务/第二个叫做签收（boolean b,int i）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);

        //5. 创建消息的消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        /**
         * 通过监听的方式
         */
//        传统方式
       /* messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者接收到topic消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/

        //使用lambda表达式
        messageConsumer.setMessageListener((message -> {
            if(message != null && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费者接收到topic消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }));



        //防止执行太快，保证控制台不灭
        //因为消费者进行连接到消息中间件，会有一系列验证，如果不写 System.in.read();这个，程序会马上执行完，但是消费者不会接收到任何消息
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

    }
}
