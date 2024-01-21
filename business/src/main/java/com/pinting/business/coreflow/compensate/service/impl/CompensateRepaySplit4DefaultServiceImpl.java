package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.CompensateRepaySysSplitInfo;
import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 代偿还款系统分账默认服务
 * Created by zousheng on 2018/6/19.
 */
@Service("compensateRepaySplit4DefaultServiceImpl")
public class CompensateRepaySplit4DefaultServiceImpl extends AbstractCompensateRepaySplitServiceImpl {

    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;

    @Override
    protected void compensateRepaySysSplit(LnLoan lnLoan, LnCompensateDetail lnCompensateDetail, PartnerEnum partner, LnRepaySchedule lnRepaySchedule) {

        // 循环调用代偿系统分账处理
        CompensateRepaySysSplitInfo info = new CompensateRepaySysSplitInfo();
        info.setLoanId(lnLoan.getId());
        info.setPartnerRepayId(lnCompensateDetail.getPartnerRepayId());
        info.setPartnerLoanId(lnLoan.getPartnerLoanId());
        info.setRepayAmount(lnCompensateDetail.getTotal());
        info.setLnCompensateDetailId(lnCompensateDetail.getId());

        // 云贷代偿分账处理
        depFixedRepayPaymentService.compensateRepaySysSplit(info);
    }

    @Override
    protected void verifyBeforeLnRepayScheduleRepayment(Integer loanId, LnCompensateDetail lnCompensateDetail, PartnerEnum partner) {

    }

    @Override
    protected AverageCapitalPlusInterestVO calAgreementPrincipalInterestAmount(LoanRelation4TransferVO record, LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount, Double leftTotalPrincipal) {
        return null;
    }

    @Override
    protected Double calProductInterestAmount(LoanRelation4TransferVO record, LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount, Double leftTotalPrincipal) {
        return null;
    }

    @Override
    protected AverageCapitalPlusInterestVO calSettleInterestAmount(LnLoan lnLoan, Integer repayScheduleSerialId) {
        return null;
    }

    @Override
    protected Double calLoanServiceAmount(LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount) {
        return null;
    }

    @Override
    protected Double getSysSettleRate() {
        return null;
    }

    @Override
    protected Double getSysLoanServerRate() {
        return null;
    }
}
