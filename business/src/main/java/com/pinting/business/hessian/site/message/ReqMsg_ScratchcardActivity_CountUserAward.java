package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 刮刮乐活动用户中奖次数
 * @author SHENGUOPING
 * @date  2017年8月21日 上午11:14:09
 */
public class ReqMsg_ScratchcardActivity_CountUserAward extends ReqMsg {

	private static final long serialVersionUID = 3173453292910925623L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
