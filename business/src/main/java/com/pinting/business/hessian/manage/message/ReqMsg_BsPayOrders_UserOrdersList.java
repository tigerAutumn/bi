package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 财务确认处理查看详情中查询用户订单信息   入参
 * Created by shh on 2016/11/3 21:26.
 */
public class ReqMsg_BsPayOrders_UserOrdersList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5651753043563460386L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
