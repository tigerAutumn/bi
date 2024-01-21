package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.loan.model.BaseAccount;

/**
 * 
 * @project business
 * @title UserDepFixedQuitAccountService.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 存管固定期限产品退出记账服务
 */
public interface UserDepFixedQuitAccountService {

	/**
	 * 理财人退出记账服务(云贷+赞时贷+7贷)
	 * @param subAccountId
	 * @param userId
	 * @param fillAmount
	 * @param totalPrincipal
	 * @param totalInterest
     * @param overflowInterest
     * @param baseAccount
     */
	public void depQuitAccount(Integer subAccountId, Integer userId, Double fillAmount,
							   Double totalPrincipal, Double totalInterest, Double overflowInterest,
							   BaseAccount baseAccount);
	
}
