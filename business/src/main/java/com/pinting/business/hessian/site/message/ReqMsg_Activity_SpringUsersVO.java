package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 踏春活动 用户登录后可查数据
 * @author bianyatian
 * @2017-3-27 上午10:27:45
 */
public class ReqMsg_Activity_SpringUsersVO extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1037137209002151163L;

	private Integer userId;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
