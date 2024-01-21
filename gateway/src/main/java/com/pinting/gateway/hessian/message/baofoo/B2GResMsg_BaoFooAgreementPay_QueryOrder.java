package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_BaoFooAgreementPay_QueryOrder extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2701699942771014697L;
	/**
     * 商户订单号
     */
	private String trans_id;
	/**
	 * 成功金额
	 */
	private Long succ_amt;

	/**
	 * 宝付订单号
	 */
	private 	String 		order_id;

	public String getTrans_id() {
		return trans_id;
	}

	public Long getSucc_amt() {
		return succ_amt;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public void setSucc_amt(Long succ_amt) {
		this.succ_amt = succ_amt;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
}
