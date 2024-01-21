package com.pinting.business.model.vo;

/**
 * 管理台借款人提现
 * @author SHENGUOPING
 * @date  2017年9月28日 下午1:07:14
 */
public class BsHfBankSysLoanerWithdrawVo {

	
	//恒丰用户ID
	private String hfUserId;
	
	//金额
	private Double amount; 
	
	//操作人
	private String opUserName;
	
	//备注
	private String note;
	
	// 银行卡号
	private String bankCard;
	
	//
	private Integer loanUserId;
	
	public String getHfUserId() {
		return hfUserId;
	}

	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOpUserName() {
		return opUserName;
	}

	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public Integer getLoanUserId() {
		return loanUserId;
	}

	public void setLoanUserId(Integer loanUserId) {
		this.loanUserId = loanUserId;
	}
	
}
