package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 还款结果查询
 * @author bianyatian
 * @2016-9-9 下午2:19:36
 */
public class G2BReqMsg_Repay_QueryRepayResult extends ReqMsg {

	private String orderNo; // 还款订单编号

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
