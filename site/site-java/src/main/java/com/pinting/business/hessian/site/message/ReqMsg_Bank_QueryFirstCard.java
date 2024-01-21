package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询回款卡信息 入参
 * @author bianyatian
 * @2015-11-19 下午7:20:01
 */
public class ReqMsg_Bank_QueryFirstCard extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1600461856013667196L;
	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
