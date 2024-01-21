package com.pinting.manage.service;

public interface DepRepayScheduleService {

	/**
	 * 标的还款至投资人账户成功处理
	 * @param depRepayScheduleId
	 */
	String doRepay2Investor(Integer depRepayScheduleId,String fileName);
}
