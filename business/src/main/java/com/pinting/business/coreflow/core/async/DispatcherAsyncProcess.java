package com.pinting.business.coreflow.core.async;

import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;

public class DispatcherAsyncProcess implements Runnable {
    private FlowContext flowContext;

    private DispatcherService dispatcherService;

    public DispatcherAsyncProcess(DispatcherService dispatcherService, FlowContext flowContext) {
        this.flowContext = flowContext;
        this.dispatcherService = dispatcherService;
    }

    @Override
    public void run() {
        flowContext.setAsync(false);
        dispatcherService.dispatcherService(flowContext);
    }

    public FlowContext getFlowContext() {
        return flowContext;
    }

    public void setFlowContext(FlowContext flowContext) {
        this.flowContext = flowContext;
    }

    public DispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public void setDispatcherService(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
    }
}
