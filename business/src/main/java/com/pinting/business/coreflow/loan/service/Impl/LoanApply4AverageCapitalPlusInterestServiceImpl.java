package com.pinting.business.coreflow.loan.service.Impl;

import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 等额本息借款申请服务
 * Created by zousheng on 2018/6/19.
 */
@Service("loanApply4AverageCapitalPlusInterestServiceImpl")
public class LoanApply4AverageCapitalPlusInterestServiceImpl extends AbstractLoanApplyServiceImpl {

    private static Map<String, Double> settleRate4YunMap = new HashMap<>();

    /**
     * 获取币港湾借款结算利率（系统结算_年化利率）
     *
     * @return
     */
    protected Double getSettleRate() {
        //云贷借款---币港湾服务费利率
        String settleRate4YunMapKey = DateUtil.format(new Date(), DateUtil.SIMPLE_DATE);
        if (settleRate4YunMap.get(settleRate4YunMapKey) == null) {
            //map取不到当日的值，则先清空map,然后查询配置表，保存该值到map中
            settleRate4YunMap.clear();
            Double initRate = getSysSettleRate();
            if (initRate != null) {
                settleRate4YunMap.put(settleRate4YunMapKey, initRate);
            }
        }
        return settleRate4YunMap.get(settleRate4YunMapKey);
    }

    @Override
    protected Double getSysSettleRate() {
        return sysConfigService.findRatePercentByKey(Constants.YUN_FIXED_INSTALLMENT_SYS_SETTLE_RATE);
    }

    @Override
    protected Double getSysLoanServerRate() {
        return sysConfigService.findRatePercentByKey(Constants.LOAN_SERVICE_RATE_YUN_DAI_FIXED_INSTALLMENT);
    }

    @Override
    protected Integer getPeriodUnit() {
        return Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_MONTH);
    }

    @Override
    protected String getFinancInterestRate(LnDepositionTarget target, LnLoan lnLoan) {
        //此处是否存在精度问题
        List<AverageCapitalPlusInterestVO> agreementVoList = algorithmService.calAverageCapitalPlusInterestPlan(lnLoan.getApproveAmount(), lnLoan.getPeriod(), lnLoan.getAgreementRate());
        Double financInterest = 0d;
        for (AverageCapitalPlusInterestVO agreementVo : agreementVoList) {
            financInterest = MoneyUtil.add(financInterest, agreementVo.getPlanInterest()).doubleValue();
        }
        Double financInterestRate = MoneyUtil.divide(financInterest, target.getTotalLimit()).doubleValue();
        return financInterestRate.toString();
    }
}
