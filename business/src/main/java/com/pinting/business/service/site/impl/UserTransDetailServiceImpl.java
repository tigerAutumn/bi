package com.pinting.business.service.site.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsUserTransDetailMapper;
import com.pinting.business.model.BsUserTransDetail;
import com.pinting.business.model.BsUserTransDetailExample;
import com.pinting.business.service.site.UserTransDetailService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class UserTransDetailServiceImpl implements UserTransDetailService {

	@Autowired
	private BsUserTransDetailMapper bsUserTransDetailMapper;

	@Override
	@MethodRole("APP")
	public int countByUserIdWithdrawSuc(Integer userId,Date time) {
		BsUserTransDetail detail = new BsUserTransDetail();
		detail.setUserId(userId);
		detail.setUpdateTime(time);
		
		return bsUserTransDetailMapper.countByUserIdWithdrawSuc(detail);
	}

	@Override
	@MethodRole("APP")
	public List<BsUserTransDetail> findByUserId(Integer userId,
			Integer pageIndex, Integer pageSize) {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("userId", userId);
		data.put("start", pageIndex * pageSize);
		data.put("pageSize", pageSize);
		List<BsUserTransDetail> detailList = bsUserTransDetailMapper.selectByExamplePage(data);
		return detailList.size() > 0? detailList : null;
	}

	@Override
	@MethodRole("APP")
	public List<BsUserTransDetail> findByUserIdNew(Integer userId,
												Integer pageIndex, Integer pageSize) {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("userId", userId);
		data.put("start", pageIndex * pageSize);
		data.put("pageSize", pageSize);
		List<BsUserTransDetail> detailList = bsUserTransDetailMapper.selectByExamplePageNew(data);
		return detailList.size() > 0? detailList : null;
	}

	@Override
	@MethodRole("APP")
	public Integer findByUserIdCount(Integer userId) {
		BsUserTransDetailExample example = new BsUserTransDetailExample();
		example.createCriteria().andUserIdEqualTo(userId);

		return bsUserTransDetailMapper.selectByExample(example).size();
	}

	@Override
	@MethodRole("APP")
	public Integer findByUserIdCountNew(Integer userId) {
		Map<String, Object> data = new HashMap<>();
		data.put("userId", userId);
		return bsUserTransDetailMapper.countByExamplePage(data);
	}

	@Override
	@MethodRole("APP")
	public Integer processingNum(Integer userId) {
		
		BsUserTransDetailExample bsUserTransDetailExample = new BsUserTransDetailExample();
		bsUserTransDetailExample.createCriteria().andUserIdEqualTo(userId).andTransTypeEqualTo(Constants.Trans_TYPE_BUY).andTransStatusEqualTo(Constants.Trans_STATUS_DEAL);
		return bsUserTransDetailMapper.countByExample(bsUserTransDetailExample);
		
	}

	@Override
	@MethodRole("APP")
	public Integer processingNumAll(Integer userId) {
		BsUserTransDetail record = new BsUserTransDetail(); 
		record.setUserId(userId);
		Integer num1 = bsUserTransDetailMapper.countProcessingNoWithdraw(record);
		Integer num2 = bsUserTransDetailMapper.countProcessingWithdraw(record);
		return num1+num2;
	}

	@Override
	@MethodRole("APP")
	public Double sumUnFallWithdraw(Integer userId, String startDay, String endDay) {
		Double sum = bsUserTransDetailMapper.sumUnFallWithdraw(userId, startDay, endDay);
		return sum == null ? 0 : sum;
	}

	@Override
	public List<BsUserTransDetail> queryReturnZanDetail(Integer userId, String time) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("time", time);
		List<BsUserTransDetail> detailList = bsUserTransDetailMapper.selectReturnZanDetail(map);
		if(null == detailList) {
			detailList = new ArrayList<>();
		}
		return detailList;
	}
}
