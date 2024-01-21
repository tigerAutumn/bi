package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_Certify;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_BindCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_Certify;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_MemoryCardSign;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_ResendCode;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_SubmitPay;
import com.pinting.gateway.out.service.ReapalTransportService;

@Service
public class ReapalTransportServiceImpl implements ReapalTransportService {
	@Autowired
	@Qualifier("reapalGatewayService")
	private HessianService reapalGatewayService;

	@Override
	public B2GResMsg_ReapalAgentPay_AgentPaySubmit agentPaySubmit(
			B2GReqMsg_ReapalAgentPay_AgentPaySubmit req) {
		B2GResMsg_ReapalAgentPay_AgentPaySubmit res = (B2GResMsg_ReapalAgentPay_AgentPaySubmit) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalAgentPay_AgentPayQuery agentPayQuery(
			B2GReqMsg_ReapalAgentPay_AgentPayQuery req) {
		B2GResMsg_ReapalAgentPay_AgentPayQuery res = (B2GResMsg_ReapalAgentPay_AgentPayQuery) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalQuickPay_MemoryCardSign memoryCardSign(B2GReqMsg_ReapalQuickPay_MemoryCardSign req) {
		B2GResMsg_ReapalQuickPay_MemoryCardSign res = (B2GResMsg_ReapalQuickPay_MemoryCardSign) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalQuickPay_BindCardSign bindCardSign(B2GReqMsg_ReapalQuickPay_BindCardSign req) {
		B2GResMsg_ReapalQuickPay_BindCardSign res = (B2GResMsg_ReapalQuickPay_BindCardSign) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalQuickPay_QueryOrderResult queryOrderResult(
			B2GReqMsg_ReapalQuickPay_QueryOrderResult req) {
		B2GResMsg_ReapalQuickPay_QueryOrderResult res = (B2GResMsg_ReapalQuickPay_QueryOrderResult) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalQuickPay_SubmitPay submitPay(B2GReqMsg_ReapalQuickPay_SubmitPay req) {
		B2GResMsg_ReapalQuickPay_SubmitPay res = (B2GResMsg_ReapalQuickPay_SubmitPay) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalQuickPay_Certify certify(B2GReqMsg_ReapalQuickPay_Certify req) {
		B2GResMsg_ReapalQuickPay_Certify res = (B2GResMsg_ReapalQuickPay_Certify) reapalGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_ReapalQuickPay_ResendCode resendCode(B2GReqMsg_ReapalQuickPay_ResendCode req) {
		B2GResMsg_ReapalQuickPay_ResendCode res = (B2GResMsg_ReapalQuickPay_ResendCode) reapalGatewayService.handleMsg(req);
		return res;
	}

}
