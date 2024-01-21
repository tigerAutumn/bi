package com.pinting.business.model.vo;

/**
 * 恒丰系统账户转账
 * @author SHENGP
 * @date  2017年4月19日 下午5:11:31
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class BsHfBankSysAccountTransferVo {

	//转出账户
	private String sourceAccount;
	
	//转入账户
	private String destAccount;
	
	//金额
	private Double amount; 
	
	//操作人
	private String userName;
	
	//备注
	private String note;

	
	public String getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public String getDestAccount() {
		return destAccount;
	}

	public void setDestAccount(String destAccount) {
		this.destAccount = destAccount;
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
