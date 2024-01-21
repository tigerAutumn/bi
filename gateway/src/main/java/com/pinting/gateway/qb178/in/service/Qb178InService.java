package com.pinting.gateway.qb178.in.service;

import com.pinting.gateway.qb178.in.model.*;

public interface Qb178InService {

	
	/**
	 * 查询产品列表
	 * @param req
	 * @return
     */
	ProductListResModel queryProductList(ProductListReqModel req);
	/**
	 * 查询订单列表
	 * @param reqModel
	 * @return
	 */
	OrderListResModel queryOrderList(OrderListReqModel reqModel);

	/**
	 * 查询会员详情
	 * @param reqModel
	 * @return
     */
	QueryUserDetailsResModel queryUserDetails(QueryUserDetailsReqModel reqModel);

	/**
	 * 查询会员持仓余额
	 * @param reqModel
	 * @return
	 */
	PositionBalanceResModel queryPositionBalance(PositionBalanceReqModel reqModel);
	
	/**
	 * 查询会员资金余额
	 * @param reqModel
	 * @return
	 */
	QueryBalanceResModel queryBalance(QueryBalanceReqModel reqModel);
	
	/**
	 * 查询会员资金流水
	 * @param reqModel
	 * @return
	 */
	QueryBalanceJnlResModel queryBalanceJnl(QueryBalanceJnlReqModel reqModel);
	
	/**
	 * 查询还款计划
	 * @param reqModel
	 * @return
	 */
	QueryRepayPlanResModel queryRepayPlan(QueryRepayPlanReqModel reqModel);
	
}
