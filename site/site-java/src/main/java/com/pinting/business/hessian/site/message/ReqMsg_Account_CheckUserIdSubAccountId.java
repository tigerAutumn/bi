package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 审核SubAccountId是否属于该用户的入参
 * @author bianyatian
 * @2016-2-24 下午3:33:10
 */
public class ReqMsg_Account_CheckUserIdSubAccountId extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8608667621417304680L;

	/*用户id*/
	private Integer userId;

	/*用户子账户id*/
    private Integer subAccountId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
}
