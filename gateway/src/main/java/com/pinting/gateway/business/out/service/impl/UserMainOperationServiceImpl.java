package com.pinting.gateway.business.out.service.impl;


import com.pinting.business.hessian.site.message.ReqMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.UserMainOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserMainOperationServiceImpl implements UserMainOperationService {

	@Autowired
	@Qualifier("gatewayMobileService")
	private HessianService hessianService;

	@Override
	public ResMsg_UserMainOperation_UserMainOperationAdd saveUserMainOperation(ReqMsg_UserMainOperation_UserMainOperationAdd req) {
		ResMsg_UserMainOperation_UserMainOperationAdd res = (ResMsg_UserMainOperation_UserMainOperationAdd) hessianService.handleMsg(req);
		return res;
	}
}
