package com.pinting.mall.service;

import java.util.List;
import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallUserSign;
import com.pinting.mall.model.dto.MallPointsIncomeQueueDTO;


public interface MallPointsService {
	/**
	 * 签到积分发放
	 * @param MallPointsIncomeQueueDTO
	 * @return MallUserSign
	 * */
	public MallUserSign signPointsIn(MallPointsIncomeQueueDTO mallPointsDTO);
	
	/**
	 * 注册积分发放
	 * @param MallPointsIncomeQueueDTO
	 * */
	public void registerPointsIn(MallPointsIncomeQueueDTO mallPointsDTO);
	
	/**
	 * 风险测评积分
	 * @param MallPointsIncomeQueueDTO
	 * */
	public void riskAssessmentPointsIn(MallPointsIncomeQueueDTO mallPointsDTO);
	
	/**
	 * 投资积分发放
	 * @param MallPointsIncomeQueueDTO
	 * */
	public void investPointsIn(MallPointsIncomeQueueDTO mallPointsDTO);
	
	/**
	 * 开通存管积分发放
	 * @param MallPointsIncomeQueueDTO
	 * */
	public void openDepositePointsIn(MallPointsIncomeQueueDTO mallPointsDTO);
	/**
	 * 积分规则业务公共校验
	 * @param 
	 * @return List<MallPointsRule>
	 * */
	public List<MallPointsRule> mallPointsRuleFactory(MallPointsIncomeQueueDTO mallPointsDTO);
}
