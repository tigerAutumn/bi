package com.pinting.business.coreflow.loan.service.Impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ApplyLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 借款申请默认服务
 * Created by zousheng on 2018/6/19.
 */
@Service("loanApply4DefaultServiceImpl")
public class LoanApply4DefaultServiceImpl implements DepFixedService {

    @Autowired
    private DepFixedLoanPaymentService depFixedLoanPaymentService;

    @Override
    public ResMsg execute(FlowContext flowContext) {
        G2BReqMsg_DafyLoan_ApplyLoan req = (G2BReqMsg_DafyLoan_ApplyLoan) flowContext.getReq();
        PartnerEnum partnerEnum = flowContext.getPartnerEnum();
        depFixedLoanPaymentService.loanApply(req, partnerEnum);
        return flowContext.getRes();
    }
}
