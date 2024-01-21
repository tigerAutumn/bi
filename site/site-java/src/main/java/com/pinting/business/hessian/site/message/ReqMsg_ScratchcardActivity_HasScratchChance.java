package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 刮刮乐活动用户是否有刮奖机会
 * @author SHENGUOPING
 * @date  2017年8月21日 下午5:30:22
 */
public class ReqMsg_ScratchcardActivity_HasScratchChance extends ReqMsg {

	private static final long serialVersionUID = 3741588769526592897L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
