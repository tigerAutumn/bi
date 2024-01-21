package com.pinting.business.coreflow.repay.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.NormalRepaySysSplitInfo;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;

/**
 * Created by Gemma on 2018/6/25.
 * 等额本息代扣结果处理
 */
@Service("repayShareProfit4AverageCapitalPlusInterestServiceImpl")
public class RepayShareProfit4AverageCapitalPlusInterestServiceImpl extends AbstractRepayShareProfitResultServiceImpl {

	private final Logger logger = LoggerFactory.getLogger(RepayShareProfit4AverageCapitalPlusInterestServiceImpl.class);
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;
	
	
	@Override
	protected void repaySysSplit(NormalRepaySysSplitInfo result) {
		try {
			depFixedRepayPaymentService.repaySysSplit4YunFixedInstallment(result);
		} catch (Exception e) {
			logger.error("云贷等额本息-代扣还款-分账异常", e);
		}
		
	}
	
	
	
	
}
