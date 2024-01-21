package com.pinting.business.coreflow.core.service.impl;

import com.pinting.business.coreflow.core.async.DispatcherAsyncProcess;
import com.pinting.business.coreflow.core.enums.FlowServiceEnum;
import com.pinting.business.coreflow.core.enums.TransCodeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.coreflow.core.util.ConstantsForCore;
import com.pinting.business.coreflow.core.util.FindServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DispatcherServiceImpl implements DispatcherService {

    private final Logger logger = LoggerFactory.getLogger(DispatcherServiceImpl.class);

    @Override
    public Object dispatcherService(final FlowContext flowContext) {
        if (flowContext.getAsync()) {
            new Thread(new DispatcherAsyncProcess(this, flowContext)).start();
            return null;
        } else {
            logger.info("业务流程开始-------start");
            logger.info("交易类型{}，借款合作方{}，业务产品类型{}", TransCodeEnum.getEnumByCode(flowContext.getTransCode()).getName(), flowContext.getPartnerEnum().getName(), flowContext.getBusinessType());
            try {
                String serviceCode = flowContext.getTransCode() + flowContext.getPartnerEnum().getCode() + flowContext.getBusinessType();
                String defaultServiceCode = flowContext.getTransCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL;
                DepFixedService depFixedService = FindServiceFactory.findService(getServiceName(serviceCode), getServiceName(defaultServiceCode));
                Object resObj = depFixedService.execute(flowContext);
                return resObj != null ? resObj : flowContext.getRes();
            } finally {
                logger.info("业务流程结束-------end");
                // 销毁上下文件
                flowContext.destroy();
            }
        }
    }

    /**
     * 获取对应服务的serviceName
     *
     * @param serviceCode
     * @return
     */
    private String getServiceName(String serviceCode) {
        return FlowServiceEnum.getEnumByServiceName(serviceCode);
    }
}
