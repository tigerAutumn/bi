package com.pinting.gateway.hessian.message.baofoo;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 协议支付-直接支付类交易
 * @project gateway
 * @title B2GResMsg_BaoFooAgreementPay_DirectAgreementPay.java
 * @author Dragon & cat
 * @date 2018-3-31
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GResMsg_BaoFooAgreementPay_DirectAgreementPay extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4037373332893314745L;
	
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
    
    /**
     * 钱包转账订单号
     */
    private String pay4OnlineOrderNo;

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public Long getSucc_amt() {
		return succ_amt;
	}

	public void setSucc_amt(Long succ_amt) {
		this.succ_amt = succ_amt;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPay4OnlineOrderNo() {
		return pay4OnlineOrderNo;
	}

	public void setPay4OnlineOrderNo(String pay4OnlineOrderNo) {
		this.pay4OnlineOrderNo = pay4OnlineOrderNo;
	}
    
    
	
}
