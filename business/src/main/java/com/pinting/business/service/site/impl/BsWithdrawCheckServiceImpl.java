package com.pinting.business.service.site.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsWithdrawCheckMapper;
import com.pinting.business.model.BsWithdrawCheck;
import com.pinting.business.service.site.BsWithdrawCheckService;

@Service
public class BsWithdrawCheckServiceImpl implements BsWithdrawCheckService {

	@Autowired
	private BsWithdrawCheckMapper bsWithdrawCheckMapper;
	
	@Override
	public void addWithdrawCheck(BsWithdrawCheck withdrawCheck) {
		if(null == withdrawCheck.getCreateTime()) {
			withdrawCheck.setCreateTime(new Date());
		}
		bsWithdrawCheckMapper.insertSelective(withdrawCheck);
	}

	@Override
	public void updateWithdrawCheck(BsWithdrawCheck withdrawCheck) {
		withdrawCheck.setCheckTime(new Date());
		bsWithdrawCheckMapper.updateByPrimaryKeySelective(withdrawCheck);
	}

	@Override
	public BsWithdrawCheck selectWithdrawCheck(Integer id) {
		return bsWithdrawCheckMapper.selectByPrimaryKey(id);
	}

}
