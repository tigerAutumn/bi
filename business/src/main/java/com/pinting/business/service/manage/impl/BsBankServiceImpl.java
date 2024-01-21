package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsBankMapper;
import com.pinting.business.model.BsBank;
import com.pinting.business.service.manage.BsBankService;

@Service
public class BsBankServiceImpl implements BsBankService{

	@Autowired
	private BsBankMapper bsBankMapper;
	
	@Override
	public List<BsBank> bsBankPage(BsBank record) {
		return bsBankMapper.bsBankPage(record);
	}

	@Override
	public int baBankCount(BsBank record) {
		return bsBankMapper.countBsBank(record);
	}

	@Override
	public int updateBsBank(BsBank record) {
		return bsBankMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addBsbank(BsBank record) {
		return bsBankMapper.insert(record);
	}

	@Override
	public BsBank bsBankPrimaryKey(Integer id) {
		return bsBankMapper.selectByPrimaryKey(id);
	}

	@Override
	public BsBank selectBank(BsBank record) {
		return bsBankMapper.selectBank(record);
	}

	@Override
	public List<BsBank> groupByBankName() {
		return bsBankMapper.groupByName();
	}

}
