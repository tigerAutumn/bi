package com.pinting.gateway.out.service.impl;

import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.out.service.App178TransportService;
@Service
public class App178TransportServiceImpl implements App178TransportService {

	@Autowired
	@Qualifier("app178GatewayService")
	private HessianService app178GatewayService;
	
	@Override
	public B2GResMsg_APP178_OrderNotice orderNotice(
			B2GReqMsg_APP178_OrderNotice req) {
		B2GResMsg_APP178_OrderNotice res = (B2GResMsg_APP178_OrderNotice) app178GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_APP178_UpdateProductInfo updateProductInfo(
			B2GReqMsg_APP178_UpdateProductInfo req) {
		B2GResMsg_APP178_UpdateProductInfo res = (B2GResMsg_APP178_UpdateProductInfo) app178GatewayService.handleMsg(req);
		return res;
	}
	@Override
	public B2GResMsg_APP178_UpdateUserInfo UpdateUserInfo(B2GReqMsg_APP178_UpdateUserInfo req) {
		B2GResMsg_APP178_UpdateUserInfo res = (B2GResMsg_APP178_UpdateUserInfo) app178GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_APP178_UpdateRepayPlan updateRepayPlan(
			B2GReqMsg_APP178_UpdateRepayPlan req) {
		B2GResMsg_APP178_UpdateRepayPlan res = (B2GResMsg_APP178_UpdateRepayPlan) app178GatewayService.handleMsg(req);
		return res;
	}

}
