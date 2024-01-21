package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Withdraw_CustomerWithdrawResult extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2435878027577319332L;
	public static final int WITHDRAW_RESULT_SUCCESS = 1;//交易成功
	public static final int WITHDRAW_RESULT_FAIL = 2;//交易失败
	
	private String applyNo; 	//提现交易唯一标识码
	private String result;		//提现结果
	private Date successTime;	//实际到账时间
	private String customerId;	//达飞客户id
	private String bankcard;	//转入卡号
	private Double amount;		//交易金额
	private String moneyType;	//币种
	private String transType;	//交易类型
	private String failReason;  //提现失败原因
	
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
