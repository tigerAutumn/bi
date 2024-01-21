package com.pinting.business.accounting.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.ReconciliationService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.out.service.DafyTransportService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;

@Service
public class ReconciliationServiceImpl implements ReconciliationService {

	@Autowired
	private DafyTransportService dafyTransportService;
	@Override
	public List<InvestmentAmounts> checkAccount2Dafy(Date queryDate) {
		B2GReqMsg_Account_CheckAccount req = new B2GReqMsg_Account_CheckAccount();
		req.setQueryDate(DateUtil.parseDate(DateUtil.format(queryDate)));
		B2GResMsg_Account_CheckAccount res = dafyTransportService.checkAccount(req);
		List<InvestmentAmounts> resList = res.getInvestmentAmounts();
		return resList;
	}

}
