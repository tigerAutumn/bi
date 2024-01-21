package com.pinting.business.coreflow.compensate.helper;

import com.pinting.business.coreflow.core.model.FlowContext;

/**
 * 代偿协议帮助类
 */
public interface CompensateAgreementHelper {

    /**
     * 生成签章协议
     * @param flowContext
     */
    void agreementSignGenerate(FlowContext flowContext);
}