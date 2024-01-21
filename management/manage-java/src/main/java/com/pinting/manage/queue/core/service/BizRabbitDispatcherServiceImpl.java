package com.pinting.manage.queue.core.service;

import com.pinting.manage.queue.core.enums.BizRabbitFlowServiceEnum;
import com.pinting.rabbit.queue.core.model.RabbitFlowContext;
import com.pinting.rabbit.queue.core.service.impl.AbstractRabbitDispatcherServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 公共服务，不需改动
 * 根据对应serviceCode跳转对应Service实现类进行业务实现
 */
@Service
public class BizRabbitDispatcherServiceImpl extends AbstractRabbitDispatcherServiceImpl {

    /**
     * 获取对应服务的serviceName
     *
     * @param flowContext
     * @return
     */
    @Override
    protected String getServiceName(RabbitFlowContext flowContext) {
        String serviceCode = flowContext.getRabbitBindingEnum().getQueues() + flowContext.getRabbitEventEnum().getCode() + flowContext.getRabbitBusinessEnum().getCode();
        return BizRabbitFlowServiceEnum.getEnumByServiceName(serviceCode);
    }
}
