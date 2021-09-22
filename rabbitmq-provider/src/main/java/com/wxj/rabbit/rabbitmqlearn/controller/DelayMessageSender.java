package com.wxj.rabbit.rabbitmqlearn.controller;

import com.wxj.rabbit.rabbitmqlearn.commons.DelayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.wxj.rabbit.rabbitmqlearn.config.DeadAndDelayConfig.*;
import static com.wxj.rabbit.rabbitmqlearn.config.DelayedRabbitMQConfig.DELAYED_EXCHANGE_NAME;
import static com.wxj.rabbit.rabbitmqlearn.config.DelayedRabbitMQConfig.DELAYED_ROUTING_KEY;


/**
 * @author wxj
 * @version 1.0.0
 * @ClassName DelayMessageSender.java
 * @Description TODO
 * @createTime 2021年09月22日 20:32:00
 */
@Component
@Slf4j
public class DelayMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, DelayTypeEnum type){
        switch (type){
            case DELAY_10s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, msg);
                break;
            case DELAY_60s:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEB_ROUTING_KEY, msg);
                break;
        }
    }

    public void sendMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEC_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setExpiration(String.valueOf(delayTime));
            return a;
        });
    }

    public void sendDelayMsg(String msg, Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, msg, a ->{
            a.getMessageProperties().setDelay(delayTime);
            return a;
        });
    }

}
