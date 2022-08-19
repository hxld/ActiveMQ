package com.atguigu.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author hxld
 * @create 2022-08-19 22:22
 */
//说穿了是业务逻辑层,本来还应该写controller层的，此案例中为了简便
    @Service
public class SpringMQ_Produce {
        @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
       ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
       SpringMQ_Produce produce = (SpringMQ_Produce) ctx.getBean("springMQ_Produce");

       //匿名内部类方式
    /*   produce.jmsTemplate.send(new MessageCreator() {
           @Override
           public Message createMessage(Session session) throws JMSException {
               TextMessage textMessage = session.createTextMessage("****spring和ActiveMQ的整合case......");
               return textMessage;
           }
       });
*/

       //lambda表达式 ---要求是接口，而且接口中只有一个方法
        //口诀：拷贝，小括号，写死，右箭头，落地，大括号｛｝，而且由于只有一个参数，可以将类型省略
        produce.jmsTemplate.send((session) ->{
//            TextMessage textMessage = session.createTextMessage("****spring和ActiveMQ的整合case for queue......");
            TextMessage textMessage = session.createTextMessage("****spring和ActiveMQ的整合case for topic......");
            return textMessage;
        } );
        System.out.println("********send task over");
    }

}
