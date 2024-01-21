package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_InvestCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_PushP2P;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_InvestCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_PushP2P;
import com.pinting.gateway.out.service.XicaiTransportService;

@Service
public class XicaiTransportServiceImpl implements XicaiTransportService {
	@Autowired
	@Qualifier("xicaiGatewayService")
	private HessianService xicaiGatewayService;

	@Override
	public B2GResMsg_Xicai_PushP2P pushP2P(B2GReqMsg_Xicai_PushP2P req) {
		B2GResMsg_Xicai_PushP2P res = (B2GResMsg_Xicai_PushP2P) xicaiGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Xicai_InvestCallBack investCallBack(B2GReqMsg_Xicai_InvestCallBack req) {
		B2GResMsg_Xicai_InvestCallBack res = (B2GResMsg_Xicai_InvestCallBack) xicaiGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Xicai_BindUserCallBack bindUserCallBack(
			B2GReqMsg_Xicai_BindUserCallBack req) {
		B2GResMsg_Xicai_BindUserCallBack res = (B2GResMsg_Xicai_BindUserCallBack) xicaiGatewayService.handleMsg(req);
		return res;
	}

}
