package com.atguigu.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author hxld
 * @create 2022-08-18 10:29
 */
public class JmsProduce {

//    public static final String ACTIVEMQ_URL = "tcp://192.168.119.100:61616";
        //broker实例
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
//    public static final String ACTIVEMQ_URL = "tcp://192.168.76.100:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工程，按照给定的url地址，采用默认用户名和密码
        //为什么要创建一个全局常量，是因为如果我们在创建对象的过程中直接写地址，那就是写死了，修改不方便。而创建一个全局常量，修改方便
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一个叫做事务/第二个叫做签收（boolean b,int i） AUTO_ACKNOWLEDGE 自动默认签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //4.创建目的地（具体是队列还是主题topic）
        //Queue queue 直接alt+enter是前面这个queue类型，但是我们可以改成destination类型(queue是destination的子接口，功能更加强大)
        //destination的子接口有queue topic 父接口一般是定义规范。子接口一般是实现更加强大的功能
//        Destination destination = session.createQueue(QUEUE_NAME);     //和集合接口我们常用arraylist接口一样  collection collection = new arrayList;

        Queue queue = session.createQueue(QUEUE_NAME);

        //5. 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //没有持久化
//        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);  //默认持久化
        //6.通过使用  MessageProducer  生产3条消息发送到mq队列里面
        for (int i = 1; i <= 3 ; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg ---" + i);  //理解为一个字符串
            //设置消息属性
            textMessage.setStringProperty("c01","vip");

//            TextMessage textMessage = session.createTextMessage("messagelistener ---" + i); //测试监听器

            //mapmessage
         /*   MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1","mapmessage -- v1");
            messageProducer.send(mapMessage);*/

            //8.通过  messageProducer 发送给mq
            messageProducer.send(textMessage);
        }
        //9. 关闭资源   正着生产，倒着关闭
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送成功");
    }
}
