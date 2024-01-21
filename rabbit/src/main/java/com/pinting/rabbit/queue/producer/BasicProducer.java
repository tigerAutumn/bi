package com.pinting.rabbit.queue.producer;

import com.pinting.core.util.JSONUtil;
import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 消息队列生产者基础类
 */
public class BasicProducer {

    private Logger logger = LoggerFactory.getLogger(BasicProducer.class);

    @Resource(name = "jackson2JsonMessageConverter")
    private MessageConverter messageConverter;

    @Autowired
    protected AmqpTemplate rabbitTemplate;

    public void enRabbitQueue(RabbitQueuesVO message, RabbitBindingEnum... rabbitBindingEnums) {
        logger.info("消息{}入队列{}", JSONUtil.toJSONString(message), rabbitBindingEnums.toString());

        for (RabbitBindingEnum bindingEnum : rabbitBindingEnums) {
            rabbitTemplate.convertAndSend(bindingEnum.getExchange(), bindingEnum.getRoutingKey(), messageConverter.toMessage(JSONUtil.toJSONString(message), new MessageProperties()));
        }
    }
}
