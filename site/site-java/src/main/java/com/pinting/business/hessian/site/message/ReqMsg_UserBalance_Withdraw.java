package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @Project: business
 * @Title: ReqMsg_Balance_Withdraw.java
 * @author Zhou Changzai
 * @date 2015-11-25上午9:39:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_UserBalance_Withdraw extends ReqMsg{
	private int userId;//用户编号
	private Double amount;//提现金额
	private String password;//用户支付密码
	private Integer terminalType; //终端类型@1手机端,2PC端
	private Integer manageId;//管理人员id
	private String checkId;//有值，管理台审核；空：PC或者手机端
	private String accountType;//账户类型：DEP-存管账户；SIMPLE-普通账户

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getManageId() {
		return manageId;
	}

	public void setManageId(Integer manageId) {
		this.manageId = manageId;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}
}


