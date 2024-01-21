package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 审核SubAccountId是否属于该用户的出参
 * @author bianyatian
 * @2016-2-24 下午3:33:10
 */
public class ResMsg_Account_CheckUserIdSubAccountId extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6986403686617055863L;
	/*符合这返回subAccountId，不符合，返回-1*/
	private Integer subAccountId; 
	public Integer getSubAccountId() {
		return subAccountId;
	}
	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

}
