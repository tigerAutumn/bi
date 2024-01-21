package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 用户竞猜结果查询 入参
 * @author bianyatian
 * @2016-6-22 下午1:35:37
 */
public class ReqMsg_Ecup2016Activity_GetUserActInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2914478241240730003L;
	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
