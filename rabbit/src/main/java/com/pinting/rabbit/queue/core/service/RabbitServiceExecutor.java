package com.pinting.rabbit.queue.core.service;

import com.pinting.rabbit.queue.core.model.RabbitFlowContext;

/**
 * Created by zousheng on 2018/7/19.
 * 服务公共类
 */
public interface RabbitServiceExecutor {

    /**
     * 业务服务执行方法
     *
     * @param flowContext
     * @return
     */
    Object execute(RabbitFlowContext flowContext);
}
