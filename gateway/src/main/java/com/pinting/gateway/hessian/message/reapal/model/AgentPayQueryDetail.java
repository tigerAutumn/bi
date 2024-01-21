package com.pinting.gateway.hessian.message.reapal.model;

import java.io.Serializable;

public class AgentPayQueryDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8450648152097631885L;
	private String tradeNum;	//记录序号
	private String tradeCardnum;	//银行卡或存折号码
	private String tradeCardname;	//银行卡或存折上的所有人姓名
	private String tradeBranchbank;	//开户行分行
	private String tradeSubbranchbank;	//开户行支行
	private String tradeAccountname;	//开户行名称
	private String tradeAccounttype;	//账户类型：0私，1公
	private String tradeAmount;	//整数，单位元 例如：1000.00
	private String tradeAmounttype;	//人民币：CNY
	private String tradeRemark;	//用途
	private String contractUsercode;	//用户协议号 (代付可空)
	private String tradeFeedbackcode;	//反馈状态  空白 (处理中),成功,失败
	private String tradeReason;	//反馈原因(可空)
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	public String getTradeCardnum() {
		return tradeCardnum;
	}
	public void setTradeCardnum(String tradeCardnum) {
		this.tradeCardnum = tradeCardnum;
	}
	public String getTradeCardname() {
		return tradeCardname;
	}
	public void setTradeCardname(String tradeCardname) {
		this.tradeCardname = tradeCardname;
	}
	public String getTradeBranchbank() {
		return tradeBranchbank;
	}
	public void setTradeBranchbank(String tradeBranchbank) {
		this.tradeBranchbank = tradeBranchbank;
	}
	public String getTradeSubbranchbank() {
		return tradeSubbranchbank;
	}
	public void setTradeSubbranchbank(String tradeSubbranchbank) {
		this.tradeSubbranchbank = tradeSubbranchbank;
	}
	public String getTradeAccountname() {
		return tradeAccountname;
	}
	public void setTradeAccountname(String tradeAccountname) {
		this.tradeAccountname = tradeAccountname;
	}
	public String getTradeAccounttype() {
		return tradeAccounttype;
	}
	public void setTradeAccounttype(String tradeAccounttype) {
		this.tradeAccounttype = tradeAccounttype;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getTradeAmounttype() {
		return tradeAmounttype;
	}
	public void setTradeAmounttype(String tradeAmounttype) {
		this.tradeAmounttype = tradeAmounttype;
	}
	public String getTradeRemark() {
		return tradeRemark;
	}
	public void setTradeRemark(String tradeRemark) {
		this.tradeRemark = tradeRemark;
	}
	public String getContractUsercode() {
		return contractUsercode;
	}
	public void setContractUsercode(String contractUsercode) {
		this.contractUsercode = contractUsercode;
	}
	public String getTradeFeedbackcode() {
		return tradeFeedbackcode;
	}
	public void setTradeFeedbackcode(String tradeFeedbackcode) {
		this.tradeFeedbackcode = tradeFeedbackcode;
	}
	public String getTradeReason() {
		return tradeReason;
	}
	public void setTradeReason(String tradeReason) {
		this.tradeReason = tradeReason;
	}

}
