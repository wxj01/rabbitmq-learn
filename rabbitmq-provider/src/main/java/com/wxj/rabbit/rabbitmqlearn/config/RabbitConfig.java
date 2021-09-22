package com.wxj.rabbit.rabbitmqlearn.config;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxj
 * @version 1.0.0
 * @ClassName RabbitConfig.java
 * @Description TODO
 * @createTime 2021年09月08日 23:18:00
 */

@Configuration
public class RabbitConfig {

    // 2021年9月22日09:54:20  死信队列 配置
    @Value("${email.queue.name}")
    private String emailQueue;
    @Value("${exchange.name}")
    private String topicExchange;
    @Value("${dead.letter.queue.name}")
    private String deadLetterQueue;
    @Value("${dead.letter.exchange.name}")
    private String deadLetterExchange;

    @Value("${delay.queue.name}")
    private String delayQueue;
    @Value("${delay.exchange.name}")
    private String delayExchange;

    @Bean
    public Queue emailQueue() {

        Map<String, Object> arguments = new HashMap<>(2);
        // 绑定死信交换机
        arguments.put("x-dead-letter-exchange", deadLetterExchange);
        // 绑定死信的路由key
        arguments.put("x-dead-letter-routing-key", deadLetterQueue+".#");

        return new Queue(emailQueue,true,false,false,arguments);
    }

    @Bean
    TopicExchange emailExchange(){
        return new TopicExchange(topicExchange);
    }

    @Bean
    Binding bindingEmailQueue() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange()).with(emailQueue+".#");
    }

    //私信队列和交换器
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(deadLetterQueue);
    }

    @Bean
    TopicExchange deadLetterExchange() {
        return new TopicExchange(deadLetterExchange);
    }

    @Bean
    Binding bindingDeadLetterQueue() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(deadLetterQueue+".#");
    }

    //延时队列
    @Bean
    public Queue delayQueue() {
        return new Queue(delayQueue);
    }

    @Bean
    CustomExchange delayExchange() {
        // 延迟交换机需要rabbitMq 配置 x-delayed-message 要不就报错
        // channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND
        // - no queue 'demo.delay' in vhost '/'
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "topic");
//        args.put("x-delayed-type", "direct");
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange(delayExchange, "x-delayed-message", true, false, args);

    }

    @Bean
    Binding bindingDelayQueue() {
//        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(delayQueue+".#").noargs();
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(delayQueue+".#").noargs();
    }




    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
                System.out.println("ConfirmCallback:     "+"确认情况："+ack);
                System.out.println("ConfirmCallback:     "+"原因："+cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("ReturnCallback:     "+"消息："+message);
                System.out.println("ReturnCallback:     "+"回应码："+replyCode);
                System.out.println("ReturnCallback:     "+"回应信息："+replyText);
                System.out.println("ReturnCallback:     "+"交换机："+exchange);
                System.out.println("ReturnCallback:     "+"路由键："+routingKey);
            }
        });

        return rabbitTemplate;
    }

}
