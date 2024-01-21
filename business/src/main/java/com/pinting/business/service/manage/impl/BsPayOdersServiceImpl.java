package com.pinting.business.service.manage.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.model.vo.DailyCheckGachaVO;
import com.pinting.business.service.manage.BsPayOdersService;

@Service
public class BsPayOdersServiceImpl implements BsPayOdersService{
	
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;

	@Override
	public List<BsPayOrdersVO> payOrdersPage(BsPayOrdersVO record) {
		return bsPayOrdersMapper.payOrdersPage(record);
	}

	@Override
	public int countPayOrders(BsPayOrdersVO record) {
		return bsPayOrdersMapper.countPayOrders(record);
	}

	@Override
	public Map<String,Object> findFirstInvestDevice(Map<String, Object> map) {
		return bsPayOrdersMapper.selectFirstInvestDevice(map);
	}

	@Override
	public List<BsPayOrders> selectBuySuccessPayOrders(Integer userId) {
		return bsPayOrdersMapper.selectBuySuccessPayOrders(userId);
	}

	@Override
	public List<BsPayOrdersVO> queryPayOrdersByUserId(Integer userId) {
		return bsPayOrdersMapper.selectPayOrdersByUserId(userId);
	}


	@Override
	public Integer queryGachaCheckDailyCount(DailyCheckGachaVO record) {
		return bsPayOrdersMapper.queryGachaCheckDailyCount(record);
	}

	@Override
	public List<DailyCheckGachaVO> queryGachaCheckDailyInfo(DailyCheckGachaVO record) {
		List<DailyCheckGachaVO> list = bsPayOrdersMapper.queryGachaCheckDailyInfo(record);
		return CollectionUtils.isEmpty(list)? null : list; 
	}

	@Override
	public Double queryGachaCheckDailySum(DailyCheckGachaVO record) {
		return bsPayOrdersMapper.selectGachaCheckDailySum(record);
	}
	
}
