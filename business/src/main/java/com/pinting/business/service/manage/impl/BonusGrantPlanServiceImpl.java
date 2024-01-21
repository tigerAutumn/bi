package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsBonusGrantPlanMapper;
import com.pinting.business.model.BsBonusGrantPlan;
import com.pinting.business.model.BsBonusGrantPlanExample;
import com.pinting.business.service.manage.BonusGrantPlanService;
import com.pinting.business.util.Constants;

@Service
public class BonusGrantPlanServiceImpl implements BonusGrantPlanService {
	@Autowired
	private BsBonusGrantPlanMapper bonusGrantPlanMapper;

	@Override
	public void saveBonusGrantPlan(List<BsBonusGrantPlan> list, Integer mUserId) {
		if(CollectionUtils.isNotEmpty(list)){
			for (BsBonusGrantPlan bsBonusGrantPlan : list) {
				//校验是否存在相同的计划，发放时间，金额，用户，类型，不存在-false，存库
				//if(!checkSameGrant(bsBonusGrantPlan)){
					bsBonusGrantPlan.setBeRecommendUserId(bsBonusGrantPlan.getUserId());
					bsBonusGrantPlan.setOpUserId(mUserId);
					bsBonusGrantPlan.setCreateTime(new Date());
					bsBonusGrantPlan.setGrantType(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);//活动奖励
					bsBonusGrantPlan.setSerialNo(1);
					bsBonusGrantPlan.setUpdateTime(new Date());
					bsBonusGrantPlan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
					bonusGrantPlanMapper.insertSelective(bsBonusGrantPlan);
				//}
			}
		}
		
	}

	/**
	 * 校验是否存在相同的计划，发放时间，金额，用户，类型
	 * @param plan
	 * @return 存在-true,不存在-false
	 */
	@Override
	public boolean checkSameGrant(BsBonusGrantPlan plan) {
		BsBonusGrantPlanExample example = new BsBonusGrantPlanExample();
		example.createCriteria().andAmountEqualTo(plan.getAmount())
			.andUserIdEqualTo(plan.getUserId())
			.andGrantDateEqualTo(plan.getGrantDate())
			.andGrantTypeEqualTo(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY);
		List<BsBonusGrantPlan> exampleList = bonusGrantPlanMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(exampleList)){
			//不存在-false
			return false;
		}else{
			return true;
		}
	}

	@Override
	public Double sumBonusAmount(Integer userId) {
		return bonusGrantPlanMapper.sumBonusAmount(userId);
	}

}
