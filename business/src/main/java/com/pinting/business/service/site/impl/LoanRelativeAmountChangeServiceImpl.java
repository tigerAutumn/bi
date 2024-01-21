package com.pinting.business.service.site.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsLoanRelativeAmountChangeMapper;
import com.pinting.business.model.BsLoanRelativeAmountChange;
import com.pinting.business.service.site.LoanRelativeAmountChangeService;

@Service
public class LoanRelativeAmountChangeServiceImpl implements
		LoanRelativeAmountChangeService {
	
	@Autowired
	private BsLoanRelativeAmountChangeMapper bsLoanRelativeAmountChangeMapper;

	@Override
	public void addLoanRelativeAmountChange(
			BsLoanRelativeAmountChange changeRecord) {
		changeRecord.setCreateTime(new Date());
		bsLoanRelativeAmountChangeMapper.insertSelective(changeRecord);
	}
	
	

}
