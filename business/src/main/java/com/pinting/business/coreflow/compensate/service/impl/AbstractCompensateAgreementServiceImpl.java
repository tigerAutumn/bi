package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.coreflow.compensate.helper.CompensateAgreementHelper;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.core.hessian.msg.ResMsg;

import javax.annotation.Resource;

/**
 * 单笔借款代偿签章协议抽象实现类
 * Created by zousheng on 2018/7/30.
 */
public abstract class AbstractCompensateAgreementServiceImpl implements DepFixedService, ConstantsForFields {

    @Resource(name = "compensateStagesAgreementHelperImpl")
    private CompensateAgreementHelper compensateAgreementHelper;

    @Override
    public ResMsg execute(FlowContext flowContext) {
        compensateAgreementHelper.agreementSignGenerate(flowContext);
        return flowContext.getRes();
    }
}
