package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.pay19.enums.DFOrderStatus;

public class B2GResMsg_Pay4Another_MerchantDfQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2393903946445683531L;
	private String mxOrderId;
	private String sysOrderId;
	private DFOrderStatus orderStatus;
	private Date finishTime;
	private Double amount;

	public String getMxOrderId() {
		return mxOrderId;
	}

	public void setMxOrderId(String mxOrderId) {
		this.mxOrderId = mxOrderId;
	}

	public String getSysOrderId() {
		return sysOrderId;
	}

	public void setSysOrderId(String sysOrderId) {
		this.sysOrderId = sysOrderId;
	}

	public DFOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(DFOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
