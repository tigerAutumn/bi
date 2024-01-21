package com.pinting.business.coreflow.core.service;

import com.pinting.business.coreflow.core.model.FlowContext;

/**
 * Created by zousheng on 2018/6/19.
 * 业务服务公共类
 */
public interface DepFixedService {

    /**
     * 业务服务执行方法
     *
     * @param flowContext
     * @return
     */
    Object execute(FlowContext flowContext);
}
