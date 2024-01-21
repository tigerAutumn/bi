package com.pinting.business.service.manage.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsUserMainOperationMapper;
import com.pinting.business.model.BsUserMainOperation;
import com.pinting.business.service.manage.MUserMainOperationService;

@Service
public class MUserMainOperationServiceImpl implements MUserMainOperationService {

	@Autowired
	private BsUserMainOperationMapper userMainOperationMapper;

	@Override
	public List<BsUserMainOperation> findUserMainOperationList(
			BsUserMainOperation userMainOperation) {
		return userMainOperationMapper.selectUserMainOperationList(userMainOperation);
	}

	@Override
	public int findUserMainOperationAllCount(
			BsUserMainOperation userMainOperation) {
		return userMainOperationMapper.selectUserMainOperationAllCount(userMainOperation);
	}
	
	
}
