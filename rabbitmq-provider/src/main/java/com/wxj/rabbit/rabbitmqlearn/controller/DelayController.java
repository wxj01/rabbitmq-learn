package com.wxj.rabbit.rabbitmqlearn.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author wxj
 * @version 1.0
 * @description: TODO
 * @date 2021/9/22 0022 11:12
 */
@Configuration
@EnableScheduling
@Slf4j
public class DelayController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${exchange.name}")
    private String topicExchange;

    @Value("${delay.exchange.name}")
    private String delayTopicExchange;

//    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendDelayOrderMessage() throws Exception{

        //订单号 id实际是保存订单后返回的，这里用uuid代替
        String orderId = RandomUtil.randomString(32);
        // 模拟订单信息
        JSONObject order=new JSONObject();
        order.put("orderId",orderId);
        order.put("goodsName","vip充值");
        order.put("orderAmount","99.00");
        CorrelationData correlationData=new CorrelationData(orderId);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(orderId);
        //30分钟时间太长，这里延时120s消费
        messageProperties.setHeader("x-delay", 120000);
        Message message = new Message(order.toJSONString().getBytes(StandardCharsets.UTF_8), messageProperties);

        rabbitTemplate.convertAndSend(delayTopicExchange,"demo.delay.x",message,correlationData);

        log.info("---发送 order 消息---{}---orderId---{}",order,correlationData.getId());
        //睡一会，为了看延迟效果
        TimeUnit.MINUTES.sleep(10);
    }
}