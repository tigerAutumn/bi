package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 等本等息代偿还款系统分账
 * Created by zousheng on 2018/6/19.
 */
@Service("compensateRepaySplit4EqualPrincipalInterestServiceImpl")
public class CompensateRepaySplit4EqualPrincipalInterestServiceImpl extends AbstractCompensateRepaySplitServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(CompensateRepaySplit4EqualPrincipalInterestServiceImpl.class);

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
        AverageCapitalPlusInterestVO agreementVo = new AverageCapitalPlusInterestVO();
        /**
         * 出借人借款协议本息=(出借人剩余本金*借款协议利率)/(剩余总本金*结算利率)*结算利息;
         */
        Double repayAgreement = CalculatorUtil.calculate("(a*a*a)/(a*a)", record.getLeftAmount(), record.getAgreementRate(), settleInterestAmount,
                leftTotalPrincipal, lnLoan.getBgwSettleRate());
        agreementVo.setPlanInterest(repayAgreement);
        logger.info("出借人借款协议利息{}=(出借人剩余本金{}*借款协议利率{}*结算利息{})/(剩余应还总本金{}*结算利率{})", repayAgreement, record.getLeftAmount(), record.getAgreementRate(), settleInterestAmount,
                leftTotalPrincipal, lnLoan.getBgwSettleRate());
        /**
         * 出借人应回产品本金=(出借人初始本金/借款期数;(最后一期应回本金=初始总本金-前几期本金之和)
         * FLOOR求精度
         */
        Double repay2UserPrincipal = 0d;
        if (!lnLoan.getPeriod().equals(repayScheduleSerialId)) {
            repay2UserPrincipal = CalculatorUtil.formatCash(record.getInitAmount(),
                    lnLoan.getPeriod() - record.getFirstTerm() + 1,
                    2);
        } else {
            repay2UserPrincipal = record.getLeftAmount();
        }
        agreementVo.setPlanPrincipal(repay2UserPrincipal);
        agreementVo.setPlanTotal(MoneyUtil.add(repayAgreement, repay2UserPrincipal).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        agreementVo.setRepaySerial(repayScheduleSerialId);
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
        /**
         * 出借人应回产品利息=(出借人剩余本金*产品利率)/(剩余总本金*结算利率)*结算利息;
         */
         Double productInterest = CalculatorUtil.calculate("(a*a*a)/(a*a)", record.getLeftAmount(), record.getBaseRate(), settleInterestAmount,
                leftTotalPrincipal, lnLoan.getBgwSettleRate());
        logger.info("计算当期产品利息{}=(出借人剩余本金{}*产品利率{}*结算利息{})/(剩余应还总本金{}*结算利率{})", productInterest, record.getLeftAmount(), record.getBaseRate(), settleInterestAmount,
                leftTotalPrincipal, lnLoan.getBgwSettleRate());
        return productInterest;
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
        AverageCapitalPlusInterestVO settleVO = algorithmService.calEqualPrincipalInterestPlan4Serial(lnLoan.getApproveAmount(), lnLoan.getPeriod(),
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
        return MoneyUtil.divide(MoneyUtil.multiply(settleInterestAmount, lnLoan.getLoanServiceRate()).doubleValue(), lnLoan.getBgwSettleRate()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    protected Double getSysSettleRate() {
        return sysConfigService.findRatePercentByKey(Constants.YUN_FIXED_PRINCIPAL_INTEREST_SYS_SETTLE_RATE);
    }

    @Override
    protected Double getSysLoanServerRate() {
        return sysConfigService.findRatePercentByKey(Constants.LOAN_SERVICE_RATE_FIXED_PRINCIPAL_INTEREST);
    }
}
