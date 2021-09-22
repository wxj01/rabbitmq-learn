package com.wxj.rabbit.rabbitmqlearn.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author wxj
 * @version 1.0
 * @description: TODO  死信队列
 * @date 2021/9/22 0022 10:29
 */
@Configuration
@EnableScheduling
@Slf4j
public class DeadController {

    @Resource
    RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String topicExchange;

//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void sendEmailMessage(){
//        String msg = RandomUtil.randomString(8);
//        JSONObject email=new JSONObject();
//        email.put("content",msg);
//        email.put("to","wxj@qq.com");
//        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
//        rabbitTemplate.convertAndSend(topicExchange,"demo.email.x",email.toJSONString(),correlationData);
//        log.info("---发送 email 消息---{}---messageId---{}",email,correlationData.getId());
//    }



}