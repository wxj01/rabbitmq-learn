package com.wxj.rabbit.rabbitmqlearn.config;

import com.wxj.rabbit.rabbitmqlearn.linsten.MyAckReceiver;
import com.wxj.rabbit.rabbitmqlearn.linsten.MyAckReceiverOld;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxj
 * @version 1.0.0
 * @ClassName MessageListenerConfig.java
 * @Description TODO
 * @createTime 2021年09月08日 23:26:00
 */



@Configuration
public class MessageListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private MyAckReceiver myAckReceiver;//消息接收处理类

    @Autowired
    private MyAckReceiverOld myAckReceiverOld; //消息接收处理类

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // RabbitMQ默认是自动确认，这里改为手动确认消息
        //设置一个队列
//        container.setQueueNames("TestDirectQueue");
        //如果同时设置多个如下： 前提是队列都是必须已经创建存在的
        //  container.setQueueNames("TestDirectQueue","TestDirectQueue2","TestDirectQueue3");
        container.setQueueNames("TestDirectQueue","fanout.A"); // 新设置的队列

        //另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
        //container.setQueues(new Queue("TestDirectQueue",true));
        //container.addQueues(new Queue("TestDirectQueue2",true));
        //container.addQueues(new Queue("TestDirectQueue3",true));

        container.setMessageListener(myAckReceiver);
//        container.setMessageListener(myAckReceiverOld);

        return container;
    }


}