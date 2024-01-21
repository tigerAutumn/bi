package com.pinting.business.accounting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.CustomerReceiveMoneyService;
import com.pinting.business.dao.BsCustomerReceiveMoneyMapper;
import com.pinting.business.model.BsCustomerReceiveMoney;

@Service
public class CustomerReceiveMoneyServiceImpl implements
		CustomerReceiveMoneyService {

	@Autowired
	private BsCustomerReceiveMoneyMapper bsCustomerReceiveMoneyMapper;
	
	@Override
	public void addCustomerReceiveMoney(
			BsCustomerReceiveMoney bsCustomerReceiveMoney) {
		bsCustomerReceiveMoneyMapper.insertSelective(bsCustomerReceiveMoney);
	}

}
