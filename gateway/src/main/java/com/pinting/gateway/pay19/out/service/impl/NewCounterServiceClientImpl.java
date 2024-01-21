package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.pay19.out.enums.NewCounterUrl;
import com.pinting.gateway.pay19.out.model.req.EBankReq;
import com.pinting.gateway.pay19.out.model.req.RefundReq;
import com.pinting.gateway.pay19.out.model.resp.RefundResp;
import com.pinting.gateway.pay19.out.service.NewCounterServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

@Service("newCounterServiceClient")
public class NewCounterServiceClientImpl implements NewCounterServiceClient {

	@Override
	public String eBank(EBankReq req) {
		return Pay19HttpUtil.newCounterEBankSend(NewCounterUrl.EBANK, req);
		
	}

	@Override
	public RefundResp refund(RefundReq req) {
		return (RefundResp)Pay19HttpUtil.newCounterRefund(NewCounterUrl.REFUND, req);
	}

}
