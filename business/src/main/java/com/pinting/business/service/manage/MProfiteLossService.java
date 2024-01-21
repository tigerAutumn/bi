package com.pinting.business.service.manage;

import java.util.Date;

import com.pinting.business.model.BsProfitLoss;

/**
 * 
 * @Project: business
 * @Title: MProfiteLossService.java
 * @author Huang MengJian
 * @date 2015-3-6 上午10:59:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MProfiteLossService {

	
	/**
	 * 插入一条损益表数据
	 * @param bsProfitLoss
	 * @return 插入成功返回true
	 */
	public boolean insertProfitLoss(BsProfitLoss bsProfitLoss);
	
	/**
	 * 根据结算日期进行更新损益表数据
	 * @return
	 */
	public boolean updateProfitLossById(BsProfitLoss bsProfitLoss);

	/**
	 * 根据
	 * @param parseDate
	 * @return
	 */
	public BsProfitLoss findProfitByClearDateMonth();

	/**
	 * 
	 * @return
	 */
	public double sumProfite();
	
}
