package com.pinting.business.accounting.service;

import com.pinting.business.model.BsProduct;

public interface ProductPlanStatutsChangeService {
	
	/**
	 * 理财计划审核通过时，定时任务注册（调用schedule接口）
	 * @param product
	 */
	public void scheduleRegist4AuthPass(BsProduct product);
	
	/**
	 * 理财计划审核退回时，删除定时任务注册（调用schedule接口）
	 * @param product
	 */
	public void scheduleRegistDelete4AuthReturn(BsProduct product);
	
	/**
	 * 理财计划手工发布时，重置定时任务注册（调用schedule接口）
	 * @param product
	 */
	public void scheduleRegistReset4Publish(BsProduct product);
	
	/**
	 * 理财计划手工结束时，删除定时任务注册（调用schedule接口）
	 * @param product
	 */
	public void scheduleRegistDelete4Finish(BsProduct product);
	
	/**
	 * 理财计划金额满额时，删除定时任务注册（调用schedule接口）
	 * @param product
	 */
	public void scheduleRegistDelete4AmountFull(BsProduct product);
	
}
