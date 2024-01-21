package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 用户登录时查询
 * 积分商城首页用户相关信息
 * @project site-java
 * @author bianyatian
 * @2018-5-15 上午11:05:12
 */
public class ReqMsg_MallIndex_UserIndex extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7104334209087696552L;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
