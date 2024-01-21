package com.pinting.business.service.site.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsSalesMapper;
import com.pinting.business.model.BsSales;
import com.pinting.business.service.site.SalesService;

@Service
public class SalesServiceImpl implements SalesService{
	
	@Autowired
	private BsSalesMapper bsSalesMapper;

	@Override
	public int updateBsSales(BsSales record) {
		return bsSalesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public BsSales selectBsSales(BsSales record) {
		return bsSalesMapper.selectBsSales(record);
	}

	@Override
	public BsSales selectByPrimaryKey(Integer id) {
		return bsSalesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addBsbank(BsSales record) {
		return bsSalesMapper.insert(record);
	}

}
