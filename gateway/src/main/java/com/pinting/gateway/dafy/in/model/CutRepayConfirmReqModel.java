package com.pinting.gateway.dafy.in.model;

import java.util.List;

/**
 * 自主放款-代扣还款
 * @author bianyatian
 * @2016-11-29 上午10:13:57
 */
public class CutRepayConfirmReqModel extends BaseReqModel {
	/*客户端标识*/
	private			String 		clientKey;

	private String orderNo; //还款单号
	
	private String overdueFlag; //逾期标识,正常代扣NORMAL
	
	private String userId; //用户编号
	
	private String name; //姓名
	
	private String idCard; //身份证
	
	private String mobile; //预留手机号
	
	private String bankCard; //卡号
	
	private String bankCode; //银行编号
	
	private Long totalAmount; //还款总金额
	
	private String userSignNo; //用户签约协议号
	
	private String payIP; //持卡人支付IP
	
	private List<CutRepayConfirmLoans> loans; //一级循环,借款列表

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOverdueFlag() {
		return overdueFlag;
	}

	public void setOverdueFlag(String overdueFlag) {
		this.overdueFlag = overdueFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<CutRepayConfirmLoans> getLoans() {
		return loans;
	}

	public void setLoans(List<CutRepayConfirmLoans> loans) {
		this.loans = loans;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getUserSignNo() {
		return userSignNo;
	}

	public void setUserSignNo(String userSignNo) {
		this.userSignNo = userSignNo;
	}

	public String getPayIP() {
		return payIP;
	}

	public void setPayIP(String payIP) {
		this.payIP = payIP;
	}
	
}
