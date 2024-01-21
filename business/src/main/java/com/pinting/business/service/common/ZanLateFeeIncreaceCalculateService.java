package com.pinting.business.service.common;


public interface ZanLateFeeIncreaceCalculateService {
	/**
	 * 计算赞分期滞纳金
	 * @param amount
	 * @param lateDay
	 * @param reserveRule
	 * @return
	 */
	public Double calLateFee(Double amount ,Integer lateDay,String reserveRule);
}
