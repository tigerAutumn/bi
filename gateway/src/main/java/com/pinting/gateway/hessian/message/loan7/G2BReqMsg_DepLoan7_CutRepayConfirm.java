package com.pinting.gateway.hessian.message.loan7;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 代扣还款
 * @project gateway
 * @title G2BReqMsg_DepLoan7_CutRepayConfirm.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_CutRepayConfirm extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -852851815535459083L;
	@NotEmpty(message="orderNo为空")
	private String orderNo; //还款单号
	@NotEmpty(message="overdueFlag为空")
	private String overdueFlag; //逾期标识,正常代扣NORMAL
	@NotEmpty(message="userId为空")
	private String userId; //用户编号
	@NotEmpty(message="name为空")
	private String name; //姓名
	@NotEmpty(message="idCard为空")
	private String idCard; //身份证
	@NotEmpty(message="mobile为空")
	private String mobile; //预留手机号
	@NotEmpty(message="bankCard为空")
	private String bankCard; //卡号
	@NotEmpty(message="bankCode为空")
	private String bankCode; //银行编号
	@NotNull(message="totalAmount为空")
	private Double totalAmount; //还款总金额
	
	private String userSignNo; //网联协议号
	
	private String payIP; //持卡人支付IP
	

	private String isOffline; //是否线下还款

	private String offPayOrderNo; //线下还款支付单号
	
	private Date applyTime;
	
	private ArrayList<HashMap<String, Object>> loans; // 借款列表

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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public ArrayList<HashMap<String, Object>> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<HashMap<String, Object>> loans) {
		this.loans = loans;
	}


	public String getIsOffline() {
		return isOffline;
	}

	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}

	public String getOffPayOrderNo() {
		return offPayOrderNo;
	}

	public void setOffPayOrderNo(String offPayOrderNo) {
		this.offPayOrderNo = offPayOrderNo;
	}
}
