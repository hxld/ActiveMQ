package com.atguigu.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * @author hxld
 * @create 2022-08-18 10:29
 */

public class JmsProduce_Async {
    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
    public static final String QUEUE_NAME = "queue01";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        //设置开启异步投递
        activeMQConnectionFactory.setUseAsyncSend(true);

        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一个叫做事务/第二个叫做签收（boolean b,int i） AUTO_ACKNOWLEDGE 自动默认签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);

        //5. 创建消息的生产者----使用更加细粒度化的消息生产者
       ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);
       TextMessage message = null;

        //6.通过使用  MessageProducer  生产3条消息发送到mq队列里面
        for (int i = 1; i <= 3 ; i++) {
            //7.创建消息
            message = session.createTextMessage("msg ---" + i);
            //给每条消息设置一个id，在回调函数中通过有无id来判断否发送成功
           message.setJMSMessageID(UUID.randomUUID().toString()+"----测试");

            String msgId = message.getJMSMessageID();
            activeMQMessageProducer.send(message, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(msgId + "has been ok send");
                }

                @Override
                public void onException(JMSException e) {
                    System.out.println(msgId + "fail to send to mq");

                }
            });

        }
        //9. 关闭资源   正着生产，倒着关闭
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送成功");
    }
}
