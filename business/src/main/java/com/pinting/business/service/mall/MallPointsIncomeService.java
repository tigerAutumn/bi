package com.pinting.business.service.mall;

import com.pinting.business.model.BizMallPointsIncome;


public interface MallPointsIncomeService {
	
	/**
	 * 新增积分收入交易记录表
	 * @param pointsIncome
	 * @return
	 */
	public BizMallPointsIncome addPointsIncome(BizMallPointsIncome pointsIncome);
	
}
