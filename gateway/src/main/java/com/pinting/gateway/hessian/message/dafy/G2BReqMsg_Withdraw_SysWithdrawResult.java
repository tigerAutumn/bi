package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Withdraw_SysWithdrawResult extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6485861137089511886L;
	public static final int WITHDRAW_RESULT_SUCCESS = 1;//交易成功
	public static final int WITHDRAW_RESULT_FAIL = 2;//交易失败
	
	private Double amount;
	private String applyNo;
	private String result;
	private Date successTime;
	private String moneyType;
	private String transType;
	private String failReason;
	
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
}
