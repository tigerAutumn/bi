package com.pinting.business.model.vo;

/**
 * 赞分期代偿人充值和提现
 * @author SHENGP
 * @date  2017年4月22日 上午10:34:07
 */
public class BsHfBankZanFinanceVo {
	
	//子账户主键ID
	private Integer subAccountId;
	//银行id
	private Integer bankId;
	//银行卡号
	private String cardNo;
	//终端类型@1手机端,2PC端
	private Integer terminalType; 
	//赞分期代偿人用户编号 
	private Integer userId;
	//账户编号
	private Integer accountId;	
	//产品类型 
	private String productType;
	//金额
	private Double amount; 
	//操作人
	private String userName;
	//备注
	private String note;
	
	public Integer getSubAccountId() {
		return subAccountId;
	}
	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
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
