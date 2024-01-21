package com.pinting.rabbit.queue.producer.service;

import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;

/**
 * 入队列消息--生产者
 */
public interface RabbitProducer {

    void enRabbitQueue(RabbitQueuesVO message, RabbitBindingEnum... rabbitBindingEnums);
}
