package com.pinting.business.model.vo;

/**
 * 针对一笔借款的出借人（理财人）信息
 * @author bianyatian
 * @2016-9-23 下午6:54:53
 */
public class BsUser4LoanVO {
	
	private String userAccount; //客户账户
	
	private String userName; //客户姓名
	
	private String userIdCardNo; //客户身份证
	
	private Double outAmount; //出借金额	
	
	private Integer term; //借款期限

	private Integer userId;//客户id
	

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIdCardNo() {
		return userIdCardNo;
	}

	public void setUserIdCardNo(String userIdCardNo) {
		this.userIdCardNo = userIdCardNo;
	}

	public Double getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Double outAmount) {
		this.outAmount = outAmount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
