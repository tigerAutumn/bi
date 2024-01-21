package com.pinting.business.service.site.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsDailyBonusMapper;
import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.BsDailyBonusExample;
import com.pinting.business.model.BsDailyBonusExample.Criteria;
import com.pinting.business.model.vo.BsDailyBonusVO;
import com.pinting.business.service.site.BonusService;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.in.util.MethodRole;
/**
 * @Project: business
 * @Title: BonusServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:47:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class BonusServiceImpl implements BonusService{

	@Autowired
	private BsDailyBonusMapper bsDailyBonusMapper;
	
	@Override
	@MethodRole("APP")
	public List<BsDailyBonus> findDailyBonusByUserId(Integer userId, Integer pageIndex, Integer pageSize, boolean withdrawFlag) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("start", pageIndex * pageSize);
		param.put("pageSize", pageSize);
		if(withdrawFlag){
			Date oneMonthsAgo = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
			Date beginDate = DateUtil.addDays(oneMonthsAgo, -30);
			param.put("beginDate", beginDate);
		}
		List<BsDailyBonus> dataList =  bsDailyBonusMapper.selectByExamplePage(param);
		return dataList.size()>0? dataList : null;
	}

	@Override
	@MethodRole("APP")
	public Integer findDailyBonusCountByUserId(Integer userId, boolean withdrawFlag) {
		BsDailyBonusExample example=new BsDailyBonusExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		if(withdrawFlag){
			Date oneMonthsAgo = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
			Date beginDate = DateUtil.addDays(oneMonthsAgo, -30);
			criteria.andTimeLessThan(beginDate);
		}
		return bsDailyBonusMapper.selectByExample(example).size();
	}

	@Override
	public boolean addDailyBonus(BsDailyBonus bsDailyBonus) {
		
		return bsDailyBonusMapper.insertSelective(bsDailyBonus) > 0 ? true : false;
	}

	@Override
	public BsDailyBonusVO findSumDailyBonusByUserIdAndTime(Integer userId,
			Date time) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("time", DateUtil.formatYYYYMMDD(time));
		return bsDailyBonusMapper.selectSumDailyBonusByUserIdAndTime(paramMap);
	}

	@Override
	public Double sumShouldBonus() {
		Double shouldBonus = bsDailyBonusMapper.sumShouldBonus(); 
		return shouldBonus == null?0:shouldBonus;
	}

	@Override
	public BsDailyBonus findDailyBonusByUserIdAndDate(Integer userId, Date date) {
		BsDailyBonusExample example=new BsDailyBonusExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId).andTimeBetween(DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(date, -1))), DateUtil.parseDate(DateUtil.formatYYYYMMDD(date)));
		List<BsDailyBonus> list = bsDailyBonusMapper.selectByExample(example);
		return list!=null && list.size()==1 ? list.get(0) : null;
	}
	
	@Override
	public double sumIncarnateBonus() {
		
		Double incarnateBonus = bsDailyBonusMapper.sumIncarnateBonus();
		return incarnateBonus == null?0.0:incarnateBonus; 
	}

}
