package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Pay4Another_MerchantDfQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -99857748243983317L;
	private Date reqTime;
	private Date tradeDate;
	private String mxOrderId;

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getMxOrderId() {
		return mxOrderId;
	}

	public void setMxOrderId(String mxOrderId) {
		this.mxOrderId = mxOrderId;
	}

}
