package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 查询处理中和成功的购买订单，有则获取第一条的状态 入参
 * @author bianyatian
 * @2016-5-19 下午2:26:38
 */
public class ReqMsg_PayOrders_GetSuccessPayOrders extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1163018413655511261L;
	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
