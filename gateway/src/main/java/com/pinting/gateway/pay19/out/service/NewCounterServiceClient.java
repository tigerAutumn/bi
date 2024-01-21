package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.EBankReq;
import com.pinting.gateway.pay19.out.model.req.RefundReq;
import com.pinting.gateway.pay19.out.model.resp.RefundResp;

public interface NewCounterServiceClient {

	/**
	 * 
	 * @Title: eBank
	 * @Description: 新网银支付请求接口
	 * @param @param req
	 * @return void
	 * @throws
	 */
	public String eBank(EBankReq req);
	
	/**
	 * 
	 * @Title: refund
	 * @Description: 新网银支付退款接口
	 * @param @param req
	 * @param @return
	 * @return String
	 * @throws
	 */
	public RefundResp refund(RefundReq req);
	
}
