package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.pay19.enums.DFStatus;

public class B2GResMsg_Pay4Another_NewMerchantDf extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160611487241740597L;
	private String retCode;
	private DFStatus status;
	private String mxOrderId;
	private Date preOrderTime;
	private String sysOrderId;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public DFStatus getStatus() {
		return status;
	}

	public void setStatus(DFStatus status) {
		this.status = status;
	}

	public String getMxOrderId() {
		return mxOrderId;
	}

	public void setMxOrderId(String mxOrderId) {
		this.mxOrderId = mxOrderId;
	}

	public Date getPreOrderTime() {
		return preOrderTime;
	}

	public void setPreOrderTime(Date preOrderTime) {
		this.preOrderTime = preOrderTime;
	}

	public String getSysOrderId() {
		return sysOrderId;
	}

	public void setSysOrderId(String sysOrderId) {
		this.sysOrderId = sysOrderId;
	}

}
