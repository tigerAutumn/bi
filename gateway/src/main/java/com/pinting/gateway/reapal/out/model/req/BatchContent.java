package com.pinting.gateway.reapal.out.model.req;

public class BatchContent {
	private String serialNo;			//序号
	private String accountNo;			//银行账户
	private String accountName;			//开户名
	private String bankName;			//开户行
	private String branchBankName;		//分行
	private String subBranchBankName;	//支行
	private String accountType;			//公/私
	private String amount;				//金额
	private String currency="CNY";		//币种：CNY
	private String province;			//省
	private String city;				//市
	private String mobile;				//手机号		不必输
	private String idType;				//证件类型		不必输
	private String idNo;				//证件号		不必输
	private String userAgreementNo;		//用户协议号	不必输
	private String merchantOrderNo;		//商户订单号	不必输
	private String note;				//备注		不必输
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBranchBankName() {
		return branchBankName;
	}
	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}
	public String getSubBranchBankName() {
		return subBranchBankName;
	}
	public void setSubBranchBankName(String subBranchBankName) {
		this.subBranchBankName = subBranchBankName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getUserAgreementNo() {
		return userAgreementNo;
	}
	public void setUserAgreementNo(String userAgreementNo) {
		this.userAgreementNo = userAgreementNo;
	}
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
