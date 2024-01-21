package com.pinting.rabbit.queue.core.service.impl;

import com.pinting.core.util.SpringBeanUtil;
import com.pinting.rabbit.enums.PTMessageEnum;
import com.pinting.rabbit.exception.PTMessageException;
import com.pinting.rabbit.queue.core.model.RabbitFlowContext;
import com.pinting.rabbit.queue.core.service.RabbitDispatcherService;
import com.pinting.rabbit.queue.core.service.RabbitServiceExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRabbitDispatcherServiceImpl implements RabbitDispatcherService {

    private final Logger logger = LoggerFactory.getLogger(AbstractRabbitDispatcherServiceImpl.class);

    @Override
    public Object dispatcher(RabbitFlowContext flowContext) {
        logger.info("队列执行开始-------start");
        try {
            String serviceName = getServiceName(flowContext);
            RabbitServiceExecutor serviceExecutor = (RabbitServiceExecutor) SpringBeanUtil.getBean(serviceName);
            if (serviceExecutor != null) {
                return serviceExecutor.execute(flowContext);
            } else {
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "找不到服务");
            }
        } finally {
            logger.info("队列执行结束-------end");
            // 销毁上下文件
            flowContext.destroy();
        }
    }

    /**
     * 获取对应服务的serviceName
     *
     * @param flowContext
     * @return
     */
    protected abstract String getServiceName(RabbitFlowContext flowContext);
}
