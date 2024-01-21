package com.pinting.mall.service;

import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.model.dto.MallIncomeResultInfo;


public interface MallPointsAccountService {
	/**
	 * 更新账户积分信息
	 * @param incomeResultInfo
	 * */
	public MallAccountJnl grantsPointsAccount(MallIncomeResultInfo incomeResultInfo);
	/**
	 * 创建积分账户
	 * @param pIncomeId - 积分收入Id
	 * @return 如果新增成功,则返回true,否则返回false
	 */
	public MallAccount openAccount(int pIncomeId);
}
