package com.wxj.rabbit.rabbitmqlearn.linsten;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * @author wxj
 * @version 1.0.0
 * @ClassName FanoutReceiverB.java
 * @Description TODO
 * @createTime 2021年09月08日 23:11:00
 */

@Component
@RabbitListener(queues = "fanout.B")
public class FanoutReceiverB {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("FanoutReceiverB消费者收到消息  : " +testMessage.toString());
    }

}
