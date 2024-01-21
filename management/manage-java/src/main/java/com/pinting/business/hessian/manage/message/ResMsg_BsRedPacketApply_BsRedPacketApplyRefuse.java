package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3741096093324841794L;
	
	/**
	 * 操作成功
	 */
	public static final Integer UPDATE_SUCCESS = 1;
	
	/**
	 * 操作失败
	 */
	public static final Integer UPDATE_FAIL = 2;
	
	public Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
