package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_MerchantDfQuery;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_NewMerchantDf;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_QueryMOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_RSendSms;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_MerchantDfQuery;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_NewMerchantDf;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_QueryMOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_RSendSms;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_RealName_RealNameAuth;
import com.pinting.gateway.out.service.Pay19TransportService;

@Service
public class Pay19TransportServiceImpl implements Pay19TransportService {
	@Autowired
	@Qualifier("pay19GatewayService")
	private HessianService pay19GatewayService;

	@Override
	public B2GResMsg_QuickPay_PreOrder preOrder(B2GReqMsg_QuickPay_PreOrder req) {
		B2GResMsg_QuickPay_PreOrder res = (B2GResMsg_QuickPay_PreOrder) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_QuickPay_ConfirmOrder confirmOrder(
			B2GReqMsg_QuickPay_ConfirmOrder req) {
		B2GResMsg_QuickPay_ConfirmOrder res = (B2GResMsg_QuickPay_ConfirmOrder) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_QuickPay_RSendSms rSendSms(B2GReqMsg_QuickPay_RSendSms req) {
		B2GResMsg_QuickPay_RSendSms res = (B2GResMsg_QuickPay_RSendSms) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_QuickPay_QueryMOrder queryMOrder(
			B2GReqMsg_QuickPay_QueryMOrder req) {
		B2GResMsg_QuickPay_QueryMOrder res = (B2GResMsg_QuickPay_QueryMOrder) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Pay4Another_NewMerchantDf newMerchantDf(
			B2GReqMsg_Pay4Another_NewMerchantDf req) {
		B2GResMsg_Pay4Another_NewMerchantDf res = (B2GResMsg_Pay4Another_NewMerchantDf) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Pay4Another_MerchantDfQuery merchantDfQuery(
			B2GReqMsg_Pay4Another_MerchantDfQuery req) {
		B2GResMsg_Pay4Another_MerchantDfQuery res = (B2GResMsg_Pay4Another_MerchantDfQuery) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_Pay4Another_QueryCheckAccountFile queryCheckAccountFile(
			B2GReqMsg_Pay4Another_QueryCheckAccountFile req) {
		B2GResMsg_Pay4Another_QueryCheckAccountFile res = (B2GResMsg_Pay4Another_QueryCheckAccountFile) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_RealName_RealNameAuth realNameAuth(
			B2GReqMsg_RealName_RealNameAuth req) {
		B2GResMsg_RealName_RealNameAuth res = (B2GResMsg_RealName_RealNameAuth) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_AcctTrans_AcctTrans acctTrans(
			B2GReqMsg_AcctTrans_AcctTrans req) {
		B2GResMsg_AcctTrans_AcctTrans res = (B2GResMsg_AcctTrans_AcctTrans) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_NetBank_EBank eBank(B2GReqMsg_NetBank_EBank req) {
		B2GResMsg_NetBank_EBank res = (B2GResMsg_NetBank_EBank) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_AcctTrans_QueryRecvAcctTrans queryRecvAcctTrans(
			B2GReqMsg_AcctTrans_QueryRecvAcctTrans req) {
		B2GResMsg_AcctTrans_QueryRecvAcctTrans res = (B2GResMsg_AcctTrans_QueryRecvAcctTrans) pay19GatewayService.handleMsg(req);
		return res;
	}

}
