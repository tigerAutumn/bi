package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 */
public class ReqMsg_Activity_WeChatLuckyTurningInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5202004623707770871L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
