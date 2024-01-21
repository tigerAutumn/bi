package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProductSerialMapper;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsProductSerialExample;
import com.pinting.business.service.manage.MProductSerialService;

@Service
public class MProductSerialServiceImpl implements MProductSerialService{
	@Autowired
	private BsProductSerialMapper productSerailMapper;

	@Override
	public List<BsProductSerial> findBsProductSerials(BsProductSerialExample example) {
		return productSerailMapper.selectByExample(example);
	}

	@Override
	public BsProductSerial findBsProductSerialsById(Integer id) {
		return productSerailMapper.selectByPrimaryKey(id);
	}
}
