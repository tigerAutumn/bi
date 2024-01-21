package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class UserLastBankResponse extends Response {

	private boolean isBindBank;
	
	private String mobile;
	
	private Integer subAccountId;
	
	private Double availableBalance;
	
	private Integer bankId;
	
	private String cardNo;
	
	private Double oneTop;
	
	private Double dayTop;
	
	private String bankName;
	
	private Integer isFirst;
	
	private String userName;
	
	private String idCard;
	
	private Double amount;
	
	private String dailyNotice;

	public String getDailyNotice() {
        return dailyNotice;
    }

    public void setDailyNotice(String dailyNotice) {
        this.dailyNotice = dailyNotice;
    }

    public boolean isBindBank() {
		return isBindBank;
	}

	public void setBindBank(boolean isBindBank) {
		this.isBindBank = isBindBank;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
