package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 奖励金转余额 入参
 * @author shiyulong
 * @2015-12-18 下午5:35:21
 */
public class ReqMsg_Bonus_BonusToJSH extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7638598607691847752L;
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
