package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 刮刮乐活动我的中奖信息
 * @author SHENGUOPING
 * @date  2017年8月22日 下午3:20:11
 */
public class ReqMsg_ScratchcardActivity_ScratchcardPrize extends ReqMsg {

	private static final long serialVersionUID = 3876726490417661372L;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
