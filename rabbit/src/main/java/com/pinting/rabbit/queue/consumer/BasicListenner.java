package com.pinting.rabbit.queue.consumer;

import com.alibaba.fastjson.JSONObject;
import com.pinting.rabbit.exception.PTMessageException;
import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.enums.RabbitBusinessEnum;
import com.pinting.rabbit.queue.core.enums.RabbitEventEnum;
import com.pinting.rabbit.queue.core.model.RabbitFlowContext;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import com.pinting.rabbit.queue.core.service.RabbitDispatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.MessageConverter;

import javax.annotation.Resource;

/**
 * 消息队列监听者基础类
 */
public abstract class BasicListenner implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(BasicListenner.class);

    @Resource(name = "jackson2JsonMessageConverter")
    private MessageConverter messageConverter;

    public RabbitFlowContext onMessage(RabbitDispatcherService dispatcherService, Message message) {
        try {
            RabbitBindingEnum rabbitBindingEnum = RabbitBindingEnum.getRabbitBindingEnum(message.getMessageProperties().getReceivedExchange(), message.getMessageProperties().getReceivedRoutingKey());
            if (rabbitBindingEnum == null) {
                logger.info("队列消息异常，ReceivedExchange:{}，ReceivedRoutingKey:{}", message.getMessageProperties().getReceivedExchange(), message.getMessageProperties().getReceivedRoutingKey());
                return null;
            }
            String messageBody = (String) messageConverter.fromMessage(message);
            logger.info("消息{}", messageBody);
            RabbitQueuesVO queuesVO = JSONObject.parseObject(messageBody, RabbitQueuesVO.class);
            logger.info("队列queues:{}，消息编号queuesNo:{}", rabbitBindingEnum.getQueues(), queuesVO.getQueuesNO());
            RabbitFlowContext flowContext = new RabbitFlowContext();
            flowContext.setQueuesVO(queuesVO);
            flowContext.setRabbitBindingEnum(rabbitBindingEnum);
            flowContext.setRabbitBusinessEnum(RabbitBusinessEnum.getRabbitEventEnum(queuesVO.getRabbitBusinessCode()));
            flowContext.setRabbitEventEnum(RabbitEventEnum.getRabbitEventEnum(queuesVO.getRabbitEventCode()));
            flowContext.setExecuteCount(queuesVO.getExecuteCount());
            dispatcherService.dispatcher(flowContext);
            return flowContext;
        } catch (PTMessageException e) {
            logger.info("消息队列执行失败：{}", e.getErrMessage());
        } catch (Exception e) {
            logger.info("消息队列执行异常", e);
        }
        return null;
    }
}



