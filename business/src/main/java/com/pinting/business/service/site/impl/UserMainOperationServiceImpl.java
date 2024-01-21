package com.pinting.business.service.site.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsUserMainOperationMapper;
import com.pinting.business.model.BsUserMainOperation;
import com.pinting.business.service.site.UserMainOperationService;

@Service
public class UserMainOperationServiceImpl implements UserMainOperationService {

	@Autowired
	private BsUserMainOperationMapper userMainOperationMapper;
	
	@Override
	public int saveUserMainOperation(BsUserMainOperation userMainOperation) {
		return userMainOperationMapper.insertSelective(userMainOperation);
	}
}
