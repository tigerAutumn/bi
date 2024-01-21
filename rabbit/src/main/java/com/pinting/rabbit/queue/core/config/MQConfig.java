package com.pinting.rabbit.queue.core.config;

import com.pinting.core.util.SpringBeanUtil;
import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.util.QueueConstant;
import com.pinting.rabbit.queue.core.util.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 消息队列公共配置类
 */
@Configuration
public class MQConfig {

    @Resource(name = "rabbitConnectionFactory")
    private ConnectionFactory rabbitConnectionFactory;

    /*===================声明DirectExchange交换模式start=========================*/
    @Bean
    public DirectExchange wechatExchange() {
        return new DirectExchange(QueueConstant.WECHAT_EXCHANGE);
    }

    @Bean
    public DirectExchange activityExchange() {
        return new DirectExchange(QueueConstant.ACTIVITY_EXCHANGE);
    }
    /*===================声明DirectExchange交换模式end=========================*/

    /*===================队列绑定start==============================*/
    //微信消息模板队列绑定
    @Bean
    public Queue bizWechatMsgTemplateQueue() {
        return new Queue(RabbitBindingEnum.BIZ_WECHAT_MSG_TEMPLATE.getQueues() + RabbitConstant.RABBIT_MODE);
    }

    @Bean
    @Autowired
    public Binding bindingBizWechatMsgTemplateQueue(Queue bizWechatMsgTemplateQueue, DirectExchange wechatExchange) {
        return BindingBuilder.bind(bizWechatMsgTemplateQueue).to(wechatExchange).with(RabbitBindingEnum.BIZ_WECHAT_MSG_TEMPLATE.getRoutingKey());
    }

    @Bean
    public SimpleMessageListenerContainer bizWechatMsgTemplateListener() {
        //设置监听者“容器”
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitConnectionFactory);
        //设置队列名
        container.setQueues(bizWechatMsgTemplateQueue());
        //设置监听者数量，即消费线程数
        container.setConcurrentConsumers(RabbitConstant.RABBIT_LISTENER_CONCURRENTCONSUMERS);
        try {
            MessageListener messageListener = (MessageListener) SpringBeanUtil.getBean(RabbitBindingEnum.BIZ_WECHAT_MSG_TEMPLATE.getQueues());
            if (messageListener != null) {
                container.setMessageListener(messageListener);
            }
            return container;
        } catch (Exception e) {
        }
        return null;
    }

    //活动数据采集队列绑定
    @Bean
    public Queue bizActivityCollectQueue() {
        return new Queue(RabbitBindingEnum.BIZ_ACTIVITY_COLLECT.getQueues() + RabbitConstant.RABBIT_MODE);
    }

    @Bean
    @Autowired
    public Binding bindingBizActivityCollectQueue(Queue bizActivityCollectQueue, DirectExchange activityExchange) {
        return BindingBuilder.bind(bizActivityCollectQueue).to(activityExchange).with(RabbitBindingEnum.BIZ_ACTIVITY_COLLECT.getRoutingKey());
    }

    @Bean
    public SimpleMessageListenerContainer bizActivityCollectListener() {
        //设置监听者“容器”
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitConnectionFactory);
        //设置队列名
        container.setQueues(bizActivityCollectQueue());
        //设置监听者数量，即消费线程数
        container.setConcurrentConsumers(RabbitConstant.RABBIT_LISTENER_CONCURRENTCONSUMERS);
        try {
            MessageListener messageListener = (MessageListener) SpringBeanUtil.getBean(RabbitBindingEnum.BIZ_ACTIVITY_COLLECT.getQueues());
            if (messageListener != null) {
                container.setMessageListener(messageListener);
            }
            return container;
        } catch (Exception e) {
        }
        return null;
    }

    /*===================队列绑定end==============================*/
}
