package com.wxj.rabbit.rabbitmqlearn.linsten;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * @author wxj
 * @version 1.0.0
 * @ClassName TopicTotalReceiver.java
 * @Description TODO
 * @createTime 2021年09月08日 22:40:00
 */
@Component
@RabbitListener(queues = "topic.woman")
public class TopicTotalReceiver {

    @RabbitHandler
    public void process(Map testMessage) {
        System.out.println("TopicTotalReceiver消费者收到消息  : " + testMessage.toString());
    }
}
