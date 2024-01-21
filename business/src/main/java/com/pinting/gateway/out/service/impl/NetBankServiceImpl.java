package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_EBank;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_NetBank_EBank;
import com.pinting.gateway.out.service.NetBankService;
@Service
public class NetBankServiceImpl implements NetBankService {
	@Autowired
	@Qualifier("pay19GatewayService")
	private HessianService pay19GatewayService;
    @Autowired
    @Qualifier("baoFooGatewayService")
    private HessianService baoFooGatewayService;
	
	@Override
	public B2GResMsg_NetBank_EBank netBankBuyProduct(B2GReqMsg_NetBank_EBank req) {
		B2GResMsg_NetBank_EBank res = (B2GResMsg_NetBank_EBank) pay19GatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_BaoFooQuickPay_EBank netBankBuyProductBaofoo(
			B2GReqMsg_BaoFooQuickPay_EBank req) {
		B2GResMsg_BaoFooQuickPay_EBank res = (B2GResMsg_BaoFooQuickPay_EBank) baoFooGatewayService.handleMsg(req);
		return res;
	}
}
