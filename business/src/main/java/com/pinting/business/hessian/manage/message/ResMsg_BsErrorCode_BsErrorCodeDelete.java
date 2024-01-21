package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsErrorCode_BsErrorCodeDelete extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4149803649847755214L;
	
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
