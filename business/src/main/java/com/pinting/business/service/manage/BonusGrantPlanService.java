package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsBonusGrantPlan;

/**
 * 奖励金发放计划
 * @author bianyatian
 * @2016-11-3 下午7:59:11
 */
public interface BonusGrantPlanService {
	
	/**
	 * 奖励金发放计划存储
	 * @param list
	 * @param mUserId
	 */
	void saveBonusGrantPlan(List<BsBonusGrantPlan> list, Integer mUserId);
	
	/**
	 * 校验是否存在相同的计划，发放时间，金额，用户，类型
	 * @param plan
	 * @return 存在-true,不存在-false
	 */
	boolean checkSameGrant(BsBonusGrantPlan plan);
	
	/**
	 * 已累计获得推荐奖励
	 * @param userId
	 * @return
	 */
	Double sumBonusAmount(Integer userId);
	
}
