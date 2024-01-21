package com.pinting.business.service.mall.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BizMallPointsIncomeMapper;
import com.pinting.business.enums.MallRuleEnum;
import com.pinting.business.model.BizMallPointsIncome;
import com.pinting.business.model.BizMallPointsIncomeExample;
import com.pinting.business.service.mall.MallPointsIncomeService;
import com.pinting.business.util.Constants;

@Service
public class MallPointsIncomeServiceImpl implements MallPointsIncomeService {

	@Autowired
	private BizMallPointsIncomeMapper bizMallPointsIncomeMapper;
	
	@Override
	public BizMallPointsIncome addPointsIncome(
			BizMallPointsIncome pointsIncome) {
		
		/**
		 * 判断类型为注册、开通存管、完成风险测评、首次投资，判断pointsIncome是否存在同类型，状态为已完成，数据，存在，不入库表
		 */
		if(MallRuleEnum.MALL_REGISTER.getCode().equals(pointsIncome.getTransType()) || 
				MallRuleEnum.MALL_OPEN_DEPOSIT.getCode().equals(pointsIncome.getTransType()) || 
				MallRuleEnum.MALL_FINISH_RISK_ASSESSMENT.getCode().equals(pointsIncome.getTransType()) || 
				MallRuleEnum.MALL_FIRST_INVEST.getCode().equals(pointsIncome.getTransType()) ){
			BizMallPointsIncomeExample example = new BizMallPointsIncomeExample();
			example.createCriteria().andUserIdEqualTo(pointsIncome.getUserId())
				.andTransTypeEqualTo(pointsIncome.getTransType())
				.andStatusEqualTo(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
			
			List<BizMallPointsIncome> list = bizMallPointsIncomeMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				pointsIncome.setPoints(0l);
				pointsIncome.setStatus(Constants.MALL_POINTS_INCOME_STATUS_INIT);
				pointsIncome.setCreateTime(new Date());
				pointsIncome.setUpdateTime(new Date());
				bizMallPointsIncomeMapper.insertSelective(pointsIncome);
				return pointsIncome;
			}else{
				return null;
			}
		}else{
			pointsIncome.setPoints(0l);
			pointsIncome.setStatus(Constants.MALL_POINTS_INCOME_STATUS_INIT);
			pointsIncome.setCreateTime(new Date());
			pointsIncome.setUpdateTime(new Date());
			bizMallPointsIncomeMapper.insertSelective(pointsIncome);
			return pointsIncome;
		}
		
	}

}
