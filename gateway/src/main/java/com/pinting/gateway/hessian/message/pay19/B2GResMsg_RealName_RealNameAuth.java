package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.pay19.enums.RealNameTradeStatus;

public class B2GResMsg_RealName_RealNameAuth extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5064710883024345980L;
	private String reqStatus;
	private String retCode;
	private RealNameTradeStatus tradeStatus;
	private String mxReqSno;
	private Date mxReqDate;
	private String mpServSno;

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public RealNameTradeStatus getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(RealNameTradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getMxReqSno() {
		return mxReqSno;
	}

	public void setMxReqSno(String mxReqSno) {
		this.mxReqSno = mxReqSno;
	}

	public Date getMxReqDate() {
		return mxReqDate;
	}

	public void setMxReqDate(Date mxReqDate) {
		this.mxReqDate = mxReqDate;
	}

	public String getMpServSno() {
		return mpServSno;
	}

	public void setMpServSno(String mpServSno) {
		this.mpServSno = mpServSno;
	}

}
