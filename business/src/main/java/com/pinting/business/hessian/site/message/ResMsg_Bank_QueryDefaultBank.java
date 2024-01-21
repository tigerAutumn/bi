package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 判断用户是否绑定，已绑定的老用户显示最近一次使用的银行卡 出参
 * @author shencheng
 * @2015-11-18 下午8:51:44
 */
public class ResMsg_Bank_QueryDefaultBank extends ResMsg {

	private static final long serialVersionUID = 8716601751420784872L;
	/*银行id*/
	private Integer bankId;
	 /*银行卡号*/
	private String cardNo;
	/*单笔限额*/
	private Double oneTop;
	/*单日限额*/
	private Double dayTop;
	/*子账户ID*/
	private Integer subAccountId;
	/*子账户可用余额*/
	private Double availableBalance;
	/*是否常用卡*/
	private Integer isFirst;
	/*开户行*/
	private String bankName;
	/*是否是绑定卡*/
	private boolean isBindBank;
	/*用户手机号*/
	private String mobile;
	/*用户名称*/
	private String userName;
	/*用户身份证号*/
	private String idCard;
	/*最小充值金额*/
	private String rechangeLimit;
	/*银行卡当日已使用的额度*/
	private Double amount;
	/*每日提示信息*/
	private String dailyNotice;
    
    public String getDailyNotice() {
        return dailyNotice;
    }

    public void setDailyNotice(String dailyNotice) {
        this.dailyNotice = dailyNotice;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isBindBank() {
		return isBindBank;
	}

	public void setBindBank(boolean isBindBank) {
		this.isBindBank = isBindBank;
	}

	public Integer getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(Integer isFirst) {
		this.isFirst = isFirst;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public Double getDayTop() {
		return dayTop;
	}

	public void setDayTop(Double dayTop) {
		this.dayTop = dayTop;
	}

	public Double getOneTop() {
		return oneTop;
	}

	public void setOneTop(Double oneTop) {
		this.oneTop = oneTop;
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

	public String getRechangeLimit() {
		return rechangeLimit;
	}

	public void setRechangeLimit(String rechangeLimit) {
		this.rechangeLimit = rechangeLimit;
	}

}
