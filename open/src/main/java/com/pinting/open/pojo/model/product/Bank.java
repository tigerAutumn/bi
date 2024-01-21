package com.pinting.open.pojo.model.product;

public class Bank {

	private Integer bankId;
	
	private String bankName;
	
	private String pay19BankCode;
	
	private Double oneTop;
	
	private Double dayTop;
	
	private Integer isAvailable;
	
	private String cardNo;
	
	private Integer isFirst;
	
    private String notice;

    private String dailyNotice;
	

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPay19BankCode() {
		return pay19BankCode;
	}

	public void setPay19BankCode(String pay19BankCode) {
		this.pay19BankCode = pay19BankCode;
	}

	public Double getOneTop() {
		return oneTop;
	}

	public void setOneTop(Double oneTop) {
		this.oneTop = oneTop;
	}

	public Double getDayTop() {
		return dayTop;
	}

	public void setDayTop(Double dayTop) {
		this.dayTop = dayTop;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getDailyNotice() {
		return dailyNotice;
	}

	public void setDailyNotice(String dailyNotice) {
		this.dailyNotice = dailyNotice;
	}
	
	
}
