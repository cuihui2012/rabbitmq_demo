package com.cuihui.rabbit.springbootproducer;

import com.cuihui.rabbit.springbootproducer.producer.RabbitOrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RabbitOrderSender orderSender;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendOrderTest() throws Exception{
    }
}
