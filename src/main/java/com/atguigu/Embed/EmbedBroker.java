package com.atguigu.Embed;

import org.apache.activemq.broker.BrokerService;

/**
 * @author hxld
 * @create 2022-08-19 18:11
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {

        //ActiveMQ也支持在vm中通信基于嵌入式的broker
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        //"tcp://192.168.119.100:61616"  这个是放在linux服务器上的，我们现在broker是嵌入式mq实例，是在本机上运行的
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
