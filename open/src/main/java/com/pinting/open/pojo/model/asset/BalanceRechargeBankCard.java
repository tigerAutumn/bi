package com.pinting.open.pojo.model.asset;

public class BalanceRechargeBankCard {

    private Integer id;  //卡ID

    private Integer userId;   //用户ID

    private String cardNo;  //卡号
    
    private Integer bankId;  //银行ID

    private String bankName;  //银行名字
    
    private Integer status;  //状态

    private Integer isFirst;   //是否回款卡
    
    private Double oneTop;  //单笔限额

    private Double dayTop;  //单日限额

    private Integer isAvailable;   //是否可用
    
    private String dailyNotice; // 日备注信息

	private String largeLogo; // 大logo

	private String smallLogo; // 小logo

	public String getSmallLogo() {
		return smallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}

	public String getLargeLogo() {
		return largeLogo;
	}

	public void setLargeLogo(String largeLogo) {
		this.largeLogo = largeLogo;
	}

	public String getDailyNotice() {
        return dailyNotice;
    }

    public void setDailyNotice(String dailyNotice) {
        this.dailyNotice = dailyNotice;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
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
    

}
