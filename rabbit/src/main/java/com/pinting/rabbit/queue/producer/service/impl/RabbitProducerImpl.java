package com.pinting.rabbit.queue.producer.service.impl;

import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import com.pinting.rabbit.queue.producer.BasicProducer;
import com.pinting.rabbit.queue.producer.service.RabbitProducer;
import org.springframework.stereotype.Service;

/**
 * 消息队列生产者公共服务
 */
@Service
public class RabbitProducerImpl extends BasicProducer implements RabbitProducer {

    @Override
    public void enRabbitQueue(RabbitQueuesVO message, RabbitBindingEnum... rabbitBindingEnums) {
        super.enRabbitQueue(message, rabbitBindingEnums);
    }
}
