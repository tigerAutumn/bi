package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 刮刮乐活动用户刮奖
 * @author SHENGUOPING
 * @date  2017年8月21日 下午1:49:17
 */
public class ReqMsg_ScratchcardActivity_UserScratch  extends ReqMsg {

	private static final long serialVersionUID = -3829689129491988346L;
	
	/** 年化投资金额 */
	private Double investAmount;
	
	private Integer userId;

	public Double getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
