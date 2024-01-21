package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.QueryOrderReq;
import com.pinting.gateway.baofoo.out.model.req.SinglePayReq;
import com.pinting.gateway.baofoo.out.model.resp.QueryOrderResp;
import com.pinting.gateway.baofoo.out.model.resp.SinglePayResp;

/**
 * 直接支付（协议支付）
 * @project gateway
 * @title SinglePayService.java
 * @author Dragon & cat
 * @date 2018-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public interface SinglePayService {

	/**
	 * 直接支付（协议支付）
	 * @param req SinglePayReq
	 * @return SinglePayResp
	 * @throws Exception 
	 */
	SinglePayResp singlePay(SinglePayReq req) throws Exception;
	/**
	 * 支付结果查询类交易 ---直接支付（协议支付）
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	QueryOrderResp queryOrder(QueryOrderReq req) throws Exception;
	
}
