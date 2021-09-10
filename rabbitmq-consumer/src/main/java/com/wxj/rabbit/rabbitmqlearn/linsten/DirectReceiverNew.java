package com.wxj.rabbit.rabbitmqlearn.linsten;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wxj
 * @version 1.0.0
 * @ClassName DirectReceiver.java
 * @Description TODO
 * @createTime 2021年09月08日 22:29:00
 */
@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
public class DirectReceiverNew {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("DirectReceiverNew消费者收到消息 : " + testMessage.toString());
    }

}
