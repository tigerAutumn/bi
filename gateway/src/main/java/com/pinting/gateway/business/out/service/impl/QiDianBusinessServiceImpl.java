package com.pinting.gateway.business.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.QiDianBusinessService;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_DailyAvailableAmountLimit;
import com.pinting.gateway.hessian.message.qidian.G2BReqMsg_QiDian_FranchiseeRegist;
import com.pinting.gateway.hessian.message.qidian.G2BResMsg_QiDian_FranchiseeRegist;
@Service
public class QiDianBusinessServiceImpl implements QiDianBusinessService {

	@Autowired
	@Qualifier("gatewayQiDianService")
	private HessianService gatewayQiDianService;
	
	
	@Override
	public G2BResMsg_QiDian_FranchiseeRegist sendBsFranchiseeRegist(
			G2BReqMsg_QiDian_FranchiseeRegist req) {
		G2BResMsg_QiDian_FranchiseeRegist res = (G2BResMsg_QiDian_FranchiseeRegist) gatewayQiDianService
				.handleMsg(req);
		return res;
	}

}
