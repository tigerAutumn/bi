package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsDeptMapper;
import com.pinting.business.model.BsDept;
import com.pinting.business.service.manage.BsDeptService;

@Service
public class BsDeptServiceImpl implements BsDeptService {
	
	@Autowired
	private BsDeptMapper bsDeptMapper;

	@Override
	public List<BsDept> findDeptName(BsDept record) {
		return bsDeptMapper.selectDeptNameOfSales(record);
	}
	
}
