package com.pinting.business.service.site.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.DafyWapBankExtMapper;
import com.pinting.business.model.DafyWapBankExt;
import com.pinting.business.model.DafyWapBankExtExample;
import com.pinting.business.service.site.DafyWapBankService;

@Service
public class DafyWapBankServiceImpl implements DafyWapBankService {

	@Autowired
	private DafyWapBankExtMapper dafyWapBankExtMapper;
	
	@Override
	public DafyWapBankExt findDafyBankCodeByBankId(String bankId) {
		
		return dafyWapBankExtMapper.selectByPrimaryKey(Integer.parseInt(bankId));
	}

}
