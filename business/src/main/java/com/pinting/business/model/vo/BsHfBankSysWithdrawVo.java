package com.pinting.business.model.vo;

/**
 * 恒丰系统提现
 * @author SHENGP
 * @date  2017年4月19日 下午4:23:13
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class BsHfBankSysWithdrawVo {

	//账户编号
	private String account;
	
	//金额
	private Double amount; 
	
	//操作人
	private String userName;
	
	//备注
	private String note;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
