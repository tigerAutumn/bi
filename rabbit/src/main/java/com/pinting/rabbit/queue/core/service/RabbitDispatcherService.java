package com.pinting.rabbit.queue.core.service;


import com.pinting.rabbit.queue.core.model.RabbitFlowContext;

public interface RabbitDispatcherService {
    Object dispatcher(RabbitFlowContext flowContext); // 对象上下文
}
