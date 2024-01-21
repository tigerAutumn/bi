package com.pinting.business.service.site.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.dao.BsUserSalesMapper;
import com.pinting.business.model.BsUserSales;
import com.pinting.business.service.site.UserSalesService;

public class UserSalesServiceImpl implements UserSalesService{
	
	@Autowired
	private BsUserSalesMapper bsUserSalesMapper;

	@Override
	public int updateBsSales(BsUserSales record) {
		return bsUserSalesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public BsUserSales selectByPrimaryKey(Integer id) {
		return bsUserSalesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addUserSales(BsUserSales record) {
		return bsUserSalesMapper.insert(record);
	}

}
