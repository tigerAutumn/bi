package com.pinting.gateway.qb178.out.service;

import com.pinting.gateway.qb178.out.model.OrderNoticeReqModel;
import com.pinting.gateway.qb178.out.model.OrderNoticeResModel;
import com.pinting.gateway.qb178.out.model.UpdateProductInfoReqModel;
import com.pinting.gateway.qb178.out.model.UpdateProductInfoResModel;
import com.pinting.gateway.qb178.out.model.UpdateRepayPlanReqModel;
import com.pinting.gateway.qb178.out.model.UpdateRepayPlanResModel;
import com.pinting.gateway.qb178.out.model.UpdateUserInfoReqModel;
import com.pinting.gateway.qb178.out.model.UpdateUserInfoResModel;

/**
 * Created by babyshark on 2017/7/28.
 */
public interface SendQb178Service {
	
	/**
	 * 更新还款计划状态通知
	 * @param req
	 * @return
	 */
	public UpdateRepayPlanResModel updateRepayPlan(UpdateRepayPlanReqModel req);
	
	/**
	 * 订单通知
	 * @param req
	 * @return
	 */
	public OrderNoticeResModel orderNotice(OrderNoticeReqModel req);
	
	/**
	 * 更新产品状态
	 * @param req
	 * @return
	 */
	public UpdateProductInfoResModel updateProductInfo(UpdateProductInfoReqModel req);
	
	/**
	 * 更新会员信息
	 * @return
	 */
	public UpdateUserInfoResModel updateUserInfo(UpdateUserInfoReqModel req);
}
