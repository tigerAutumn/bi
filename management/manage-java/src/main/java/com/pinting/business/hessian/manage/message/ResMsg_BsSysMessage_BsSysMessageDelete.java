package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSysMessage_BsSysMessageDelete extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8141439678587716280L;

	/**
	 * 删除成功
	 */
	public static final Integer DELETE_SUCCESS = 1;
	
	/**
	 * 删除失败
	 */
	public static final Integer DELETE_FAIL = 2;
	
	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
}
