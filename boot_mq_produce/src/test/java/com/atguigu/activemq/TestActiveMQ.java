package com.atguigu.activemq;

import com.atguigu.activemq.produce.Queue_Produce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author hxld
 * @create 2022-08-20 10:33
 */
@SpringBootTest(classes = MainApp_Produce.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {

    @Autowired
    private Queue_Produce queue_produce;

    @Test
    public void testSend()  throws  Exception{
        queue_produce.produceMsg();
    }
}
