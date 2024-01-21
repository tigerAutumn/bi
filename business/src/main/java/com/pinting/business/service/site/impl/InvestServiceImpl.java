package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsDailyInterestMapper;
import com.pinting.business.model.BsDailyInterest;
import com.pinting.business.model.BsDailyInterestExample;
import com.pinting.business.service.site.InvestService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.MethodRole;

/**
 * @Project: business
 * @Title: InvestServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:47:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class InvestServiceImpl implements InvestService {

	@Autowired
	private BsDailyInterestMapper bsDailyInterestMapper;

	@Override
	@MethodRole("APP")
	public List<BsDailyInterest> findDailyInvestByUserId(Integer userId,
			Integer pageIndex, Integer pageSize) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("userId", userId);
		data.put("start", pageIndex * pageSize);
		data.put("pageSize", pageSize);
		List<BsDailyInterest> dataList = bsDailyInterestMapper
				.selectByExamplePage(data);
		return dataList.size() > 0 ? dataList : null;
	}

	@Override
	@MethodRole("APP")
	public Integer findDailyInvestCountByUserId(Integer userId) {
		BsDailyInterestExample example = new BsDailyInterestExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return bsDailyInterestMapper.selectByExample(example).size();
	}

	@Override
	public boolean addInvestService(BsDailyInterest bsDailyInterest) {
		return bsDailyInterestMapper.insertSelective(bsDailyInterest) > 0 ? true
				: false;
	}

	@Override
	@MethodRole("APP")
	public List<BsDailyInterest> findDailyInterestByUserAndDay(Integer userId,
			Date day) {
		BsDailyInterestExample example = new BsDailyInterestExample();
		example.createCriteria().andUserIdEqualTo(userId)
				.andTimeLessThan(DateUtil.addDays(day, 1))
				.andTimeGreaterThan(day);
		List<BsDailyInterest> list = bsDailyInterestMapper.selectByExample(example);
		return list.size() >= 1 ? list : null;
	}
	
	
	@Override
	public Double findInvestTotalByUserId(Integer userId) {
		BsDailyInterestExample example = new BsDailyInterestExample();
		example.createCriteria().andUserIdEqualTo(userId).andTimeLessThan(DateUtil.addDays(new Date(), 1));
		List<BsDailyInterest> bsDailyInterests =  bsDailyInterestMapper.selectByExample(example);
		Double totalAmount = 0d;
		for (BsDailyInterest bsDailyInterest : bsDailyInterests) {
			totalAmount = MoneyUtil.add(totalAmount, bsDailyInterest.getInterest()).doubleValue();
		}
		return MoneyUtil.defaultRound(totalAmount).doubleValue();
	}

	@Override
	public Double findZanInvestTotalByUserId(Integer userId) {
		return bsDailyInterestMapper.findZanInvestTotalByUserId(userId);
	}
	

}
