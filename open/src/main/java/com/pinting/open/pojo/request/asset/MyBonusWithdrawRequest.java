package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 
 * @project open-base
 * @title MyBonusWithdrawRequest.java
 * @author Dragon & cat
 * @date 2017-4-14
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 我的奖励金收益_奖励金转出
 */
public class MyBonusWithdrawRequest extends Request {
	/*用户ID*/
	private		Integer		userId;
	/*提现金额*/
	private		Double		bonusAmount;
	/*支付密码*/
	private		String		payPassword;
	/*银行卡ID*/
	private		Integer		cardId;
	/*订单来源  来源(3:android,4:ios)*/
	private 	Integer 	terminalType;   
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/my_bonus_withdraw";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/my_bonus_withdraw";
	}

}
