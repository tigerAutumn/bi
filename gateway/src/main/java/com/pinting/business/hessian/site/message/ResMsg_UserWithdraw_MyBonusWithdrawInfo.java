package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_UserWithdraw_MyBonusWithdrawInfo extends ResMsg {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = -5998955357380917384L;

	private Integer cardId;
    
    private String cardNo;
    
    private String bankName;
    
    private Integer withdrawTimes;  // 用户当月可提现次数
    
	private String  smallLogo;   //小图标

    private String  largeLogo;   //大图标
    
    private Double canWithdraw; // 最多可提现金额
    
    private Double limitWithdraw; // 最少起提金额
    
    private Double totalAmount; // 奖励金总金额
	/* 单笔提现上限 */
	private Double singleWithdrawUpperLimit;
	/* 单日提现上限 */
	private Double dayWithdrawUpperLimit;
	/* 用户已提现总金额*/
	private Double userWithdrawAmount;
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getWithdrawTimes() {
		return withdrawTimes;
	}
	public void setWithdrawTimes(Integer withdrawTimes) {
		this.withdrawTimes = withdrawTimes;
	}
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
	public Double getCanWithdraw() {
		return canWithdraw;
	}
	public void setCanWithdraw(Double canWithdraw) {
		this.canWithdraw = canWithdraw;
	}
	public Double getLimitWithdraw() {
		return limitWithdraw;
	}
	public void setLimitWithdraw(Double limitWithdraw) {
		this.limitWithdraw = limitWithdraw;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getSingleWithdrawUpperLimit() {
		return singleWithdrawUpperLimit;
	}
	public void setSingleWithdrawUpperLimit(Double singleWithdrawUpperLimit) {
		this.singleWithdrawUpperLimit = singleWithdrawUpperLimit;
	}
	public Double getDayWithdrawUpperLimit() {
		return dayWithdrawUpperLimit;
	}
	public void setDayWithdrawUpperLimit(Double dayWithdrawUpperLimit) {
		this.dayWithdrawUpperLimit = dayWithdrawUpperLimit;
	}
	public Double getUserWithdrawAmount() {
		return userWithdrawAmount;
	}
	public void setUserWithdrawAmount(Double userWithdrawAmount) {
		this.userWithdrawAmount = userWithdrawAmount;
	}
	
	
}
