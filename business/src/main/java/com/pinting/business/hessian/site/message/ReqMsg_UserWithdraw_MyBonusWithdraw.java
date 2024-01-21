package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_UserWithdraw_MyBonusWithdraw extends ReqMsg {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -688838352840896815L;
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
	
}
