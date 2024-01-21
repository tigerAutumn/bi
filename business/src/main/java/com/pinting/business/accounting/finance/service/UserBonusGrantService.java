package com.pinting.business.accounting.finance.service;

import com.pinting.business.model.BsBonusGrantPlan;

public interface UserBonusGrantService {
	
	/**
	 * 推荐人拿所有奖励金
	 * 奖励金第一期发放，以及后续发放计划记录生成
	 * @param selfUserId 理财人编号
	 * @param referrerUserId 推荐人编号
	 * @param subAccountId 理财产品户编号
	 * @param amount 理财金额
	 */
	void referrerTakeAll(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount);
	
	/**
	 * 购买人、推荐人各拿部分奖励金
	 * 奖励金第一期发放，以及后续发放计划记录生成
	 * @param selfUserId 理财人编号
	 * @param referrerUserId 推荐人编号
	 * @param subAccountId 理财产品户编号
	 * @param amount 理财金额
	 */
	void eachTakePart(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount);
	
	/**
	 * 购买人拿部分奖励金
	 * 奖励金第一期发放，以及后续发放计划记录生成
	 * @param selfUserId 理财人编号
	 * @param subAccountId 理财产品户编号
	 * @param amount 理财金额
	 */
	void selfTakePart(Integer selfUserId, Integer subAccountId, Double amount);
	
	/**
	 * 奖励金发放
	 * @param plan 发放计划记录
	 * @return 成功返回true，否则false
	 */
	boolean grantBonus(BsBonusGrantPlan plan);
	
	/**
	 * 根据用户编号，获得该用户产生奖励金的发放类型
	 * @param userId 用户编号
	 * @return
	 */
	String getBonusGrantTypeByUserId(Integer userId);

	/**
	 * 委托计划
	 * 推荐人拿所有奖励金
	 * 奖励金第一期发放，以及后续发放计划记录生成
	 * @param selfUserId 理财人编号
	 * @param referrerUserId 推荐人编号
	 * @param subAccountId 理财产品户编号
	 * @param amount 理财金额
	 */
	void entrustReferrerTakeAll(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount);

	/**
	 * 委托计划
	 * 购买人、推荐人各拿部分奖励金
	 * 奖励金第一期发放，以及后续发放计划记录生成
	 * @param selfUserId 理财人编号
	 * @param referrerUserId 推荐人编号
	 * @param subAccountId 理财产品户编号
	 * @param amount 理财金额
	 */
	void entrustEachTakePart(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount);

	/**
	 * 委托计划
	 * 购买人拿部分奖励金
	 * 奖励金第一期发放，以及后续发放计划记录生成
	 * @param selfUserId 理财人编号
	 * @param subAccountId 理财产品户编号
	 * @param amount 理财金额
	 */
	void entrustSelfTakePart(Integer selfUserId, Integer subAccountId, Double amount);

}
