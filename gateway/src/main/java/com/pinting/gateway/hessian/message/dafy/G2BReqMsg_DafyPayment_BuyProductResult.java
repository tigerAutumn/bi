package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * @Project: business
 * @Title: G2BReqMsg_Payment_BuyProductResult.java
 * @author Zhou Changzai
 * @date 2015-2-27 下午5:41:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class G2BReqMsg_DafyPayment_BuyProductResult extends ReqMsg{
	private static final long serialVersionUID = 3078999863231113845L;
	
	private int result;//支付结果：1-成功，2-失败
	private String orderNo; //网新生成的订单号
	private String bankName; //支付银行名称
	private double amount; // 实际支付的金额
	private int moneyType; //币种
	private Date successTime; //成功时间
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}

}
