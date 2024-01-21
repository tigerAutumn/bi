package com.pinting.business.service.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.enums.LoanSubjectRulesEnum;
import com.pinting.business.service.common.ZanLateFeeIncreaceCalculateService;
import com.pinting.core.util.MoneyUtil;

@Service
public class ZanLateFeeIncreaceCalculateServiceImpl implements
		ZanLateFeeIncreaceCalculateService {
	
	private static Logger log = LoggerFactory.getLogger(ZanLateFeeIncreaceCalculateServiceImpl.class);
	
	@Override
	public Double calLateFee(Double amount, Integer lateDay, String reserveRule) {  
    	log.info("计算滞纳金>>>>>>>>剩余未还本金："+amount+";逾期天数："+lateDay+";取舍规则："+reserveRule); 
        Double lateFee = 0d;
        Double rate = 0d;
    	if (lateDay<=3) {
    		rate = 0.001;
		}else if (lateDay>=4 && lateDay <= 15) {
			rate = 0.002;
		}else if (lateDay>=16 && lateDay <= 30) {
			rate = 0.0025;
		}else if (lateDay>=31 ) {
			rate = 0.004;
		}
    	amount=MoneyUtil.multiply(amount, rate).doubleValue();
		//元-分
		amount = MoneyUtil.multiply(amount, 100).doubleValue();
		if(reserveRule.equals(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode())){
			//计数保留规则-四舍五入
			amount = (double) Math.round(amount);
		}else if(reserveRule.equals(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode())){
			//计数保留规则-小数点后直接舍弃
			amount = (double) Math.floor(amount);
		}else if(reserveRule.equals(LoanSubjectRulesEnum.ReserveRuleEnum.CEIL.getCode())){
			//计数保留规则-存在小数直接进位
			amount = (double) Math.ceil(amount);
		}
		lateFee = MoneyUtil.multiply(amount, lateDay).doubleValue();
    	return lateFee;  
    }

}
