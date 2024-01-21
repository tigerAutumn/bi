package com.pinting.business.hessian.site.message;


import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 奖励金转余额 入参
 * @author bianyatian
 * @2015-11-18 下午8:41:21
 */
public class ReqMsg_Bonus_BonusToJSH extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5726741239981460137L;
	/*用户id*/
	private Integer userId;
	/*金额*/
	private Double amount;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
