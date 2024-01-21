package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 理财计划列表分级--首页（引导页面）
 * @author SHENGP
 * @date  2017年7月5日 上午10:07:10
 */
public class ReqMsg_Product_ListIndexInfoQuery4User extends ReqMsg {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3987454997544966687L;
	/** 用户ID */
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	} 
	
}
