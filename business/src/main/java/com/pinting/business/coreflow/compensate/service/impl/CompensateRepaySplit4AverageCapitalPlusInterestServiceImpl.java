package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 等额本息代偿还款系统分账服务
 * Created by zousheng on 2018/6/19.
 */
@Service("compensateRepaySplit4AverageCapitalPlusInterestServiceImpl")
public class CompensateRepaySplit4AverageCapitalPlusInterestServiceImpl extends AbstractCompensateRepaySplitServiceImpl {

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private SysConfigService sysConfigService;


    /**
     * 计算当期协议本息
     *
     * @param record                record.initAmount：债权匹配/债权转让的初始借款金额;record.firstTerm：首次还款期数
     * @param lnLoan                lnLoan.loanPeriod：借款期数;lnLoan.agreementRate：借款协议费率
     * @param repayScheduleSerialId 还款计划当期序号
     * @param settleInterestAmount  结算利息
     * @return AverageCapitalPlusInterestVO
     */
    @Override
    protected AverageCapitalPlusInterestVO calAgreementPrincipalInterestAmount(LoanRelation4TransferVO record, LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount, Double leftTotalPrincipal) {
        AverageCapitalPlusInterestVO agreementVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(record.getInitAmount(), (lnLoan.getPeriod() - record.getFirstTerm() + 1),
                MoneyUtil.divide(lnLoan.getAgreementRate(), 100, 4).doubleValue(), (repayScheduleSerialId - record.getFirstTerm() + 1));
        return agreementVo;
    }

    /**
     * 计算当期产品利息
     *
     * @param record                record.initAmount：债权匹配/债权转让的初始借款金额;record.firstTerm：首次还款期数;record.baseRate：产品利率
     * @param lnLoan                lnLoan.loanPeriod：借款期数;lnLoan.agreeRate：借款协议费率
     * @param repayScheduleSerialId 还款计划当期序号
     * @param settleInterestAmount  结算利息
     * @return
     */
    @Override
    protected Double calProductInterestAmount(LoanRelation4TransferVO record, LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount, Double leftTotalPrincipal) {
        AverageCapitalPlusInterestVO interestVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(record.getInitAmount(), (lnLoan.getPeriod() - record.getFirstTerm() + 1),
                MoneyUtil.divide(record.getBaseRate(), 100, 4).doubleValue(), (repayScheduleSerialId - record.getFirstTerm() + 1));
        return interestVo.getPlanInterest();
    }

    /**
     * 计算当期结算利息
     *
     * @param lnLoan                lnLoan.approveAmount借款金额;lnLoan.loanPeriod：借款期数;lnLoan.settleRate：结算费率
     * @param repayScheduleSerialId 还款计划当期序号
     * @return
     */
    @Override
    protected AverageCapitalPlusInterestVO calSettleInterestAmount(LnLoan lnLoan, Integer repayScheduleSerialId) {
        AverageCapitalPlusInterestVO settleVO = algorithmService.calAverageCapitalPlusInterestPlan4Serial(lnLoan.getApproveAmount(), lnLoan.getPeriod(),
                MoneyUtil.divide(lnLoan.getBgwSettleRate(), 100, 4).doubleValue(), repayScheduleSerialId);
        return settleVO;
    }

    /**
     * 计算当期借款服务费
     *
     * @param lnLoan                lnLoan.approveAmount借款金额;lnLoan.loanPeriod：借款期数;lnLoan.settleRate：结算费率
     * @param settleInterestAmount  结算利息
     * @param repayScheduleSerialId 还款计划当期序号
     * @return
     */
    @Override
    protected Double calLoanServiceAmount(LnLoan lnLoan, Integer repayScheduleSerialId, Double settleInterestAmount) {
        AverageCapitalPlusInterestVO loanServiceVO = algorithmService.calAverageCapitalPlusInterestPlan4Serial(lnLoan.getApproveAmount(), lnLoan.getPeriod(),
                MoneyUtil.divide(lnLoan.getLoanServiceRate(), 100, 4).doubleValue(), repayScheduleSerialId);
        return loanServiceVO.getPlanInterest();
    }

    @Override
    protected Double getSysSettleRate() {
        return sysConfigService.findRatePercentByKey(Constants.YUN_FIXED_INSTALLMENT_SYS_SETTLE_RATE);
    }

    @Override
    protected Double getSysLoanServerRate() {
        return sysConfigService.findRatePercentByKey(Constants.LOAN_SERVICE_RATE_YUN_DAI_FIXED_INSTALLMENT);
    }
}
