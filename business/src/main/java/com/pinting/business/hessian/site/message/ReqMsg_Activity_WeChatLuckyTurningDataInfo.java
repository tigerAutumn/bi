package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Activity_WeChatLuckyTurningDataInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7944992129683974228L;

	private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
