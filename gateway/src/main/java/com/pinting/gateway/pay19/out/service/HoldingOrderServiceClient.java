package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.HoldingOrderQueryReq;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderReq;
import com.pinting.gateway.pay19.out.model.resp.QueryWithHoldingNewResp;
import com.pinting.gateway.pay19.out.model.resp.WithholdingOrderResp;

public interface HoldingOrderServiceClient {

	/**
	 * 
	 * @Title: holdingOrder
	 * @Description: 代扣下单
	 * @param @param req
	 * @param @return
	 * @return WithholdingOrderResp
	 * @throws
	 */
	public WithholdingOrderResp holdingOrder(HoldingOrderReq req);
	
	/**
	 * 
	 * @Title: holdingOrderQuery
	 * @Description: 代扣订单查询
	 * @param @param req
	 * @param @return
	 * @return QueryWithHoldingNewResp
	 * @throws
	 */
	public QueryWithHoldingNewResp holdingOrderQuery(HoldingOrderQueryReq req);
}
