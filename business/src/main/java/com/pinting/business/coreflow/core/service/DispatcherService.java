package com.pinting.business.coreflow.core.service;

import com.pinting.business.coreflow.core.model.FlowContext;

public interface DispatcherService {
    Object dispatcherService(FlowContext flowContext); // 对象上下文
}
