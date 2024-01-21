package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.coreflow.compensate.helper.CompensateAgreementHelper;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.core.hessian.msg.ResMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 单笔借款代偿签章协议默认服务
 * Created by zousheng on 2018/6/19.
 */
@Service("compensateAgreement4DefaultServiceImpl")
public class CompensateAgreement4DefaultServiceImpl extends AbstractCompensateAgreementServiceImpl {

    @Resource(name = "compensateAgreementHelperImpl")
    private CompensateAgreementHelper compensateAgreementHelper;

    @Override
    public ResMsg execute(FlowContext flowContext) {
        compensateAgreementHelper.agreementSignGenerate(flowContext);
        return flowContext.getRes();
    }

}
