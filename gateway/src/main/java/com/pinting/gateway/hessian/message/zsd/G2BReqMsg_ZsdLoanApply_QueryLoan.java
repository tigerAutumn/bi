package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 借款结果查询服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午4:32:10
 */
public class G2BReqMsg_ZsdLoanApply_QueryLoan extends ReqMsg {

	private static final long serialVersionUID = 4391631689725523328L;
	
	/**
	 * 借款订单号
	 */
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	 
}
