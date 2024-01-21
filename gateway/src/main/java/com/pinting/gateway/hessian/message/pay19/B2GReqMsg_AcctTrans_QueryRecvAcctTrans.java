package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_AcctTrans_QueryRecvAcctTrans extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7860140153302986923L;
	private String            oriOutMxId;
    private String            oriOutOrderId;
    private Date            oriOutOrderDate;
    private String            tradeType;
    private Date            ts;
	public String getOriOutMxId() {
		return oriOutMxId;
	}
	public void setOriOutMxId(String oriOutMxId) {
		this.oriOutMxId = oriOutMxId;
	}
	public String getOriOutOrderId() {
		return oriOutOrderId;
	}
	public void setOriOutOrderId(String oriOutOrderId) {
		this.oriOutOrderId = oriOutOrderId;
	}
	public Date getOriOutOrderDate() {
		return oriOutOrderDate;
	}
	public void setOriOutOrderDate(Date oriOutOrderDate) {
		this.oriOutOrderDate = oriOutOrderDate;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}

}
