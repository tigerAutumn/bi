package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RegularBuy_InfoQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5233301917859622693L;
	

	private int id;
	
	private int trem;
	
	private int investNum;
	
	private double rate;

	private int maxInvestTimes; //购买能次数
	
	private int buyNum;			//剩余购买次数
	
	private int userId;

	private String nick;
	
	private double priceCeiling;  //金额上限
	
	private double priceLimit;		//金额下限
	
	private Double surplusAmount; //剩余购买金额
	
	public Double getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(Double surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public int getMaxInvestTimes() {
		return maxInvestTimes;
	}

	public void setMaxInvestTimes(int maxInvestTimes) {
		this.maxInvestTimes = maxInvestTimes;
	}

	public double getPriceCeiling() {
		return priceCeiling;
	}

	public void setPriceCeiling(double priceCeiling) {
		this.priceCeiling = priceCeiling;
	}

	public double getPriceLimit() {
		return priceLimit;
	}

	public void setPriceLimit(double priceLimit) {
		this.priceLimit = priceLimit;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTrem() {
		return trem;
	}

	public void setTrem(int trem) {
		this.trem = trem;
	}

	public int getInvestNum() {
		return investNum;
	}

	public void setInvestNum(int investNum) {
		this.investNum = investNum;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
