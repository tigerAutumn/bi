package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsRedPacketApply_BsRedPacketApplyModify extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6142444229177552345L;
	
	/**
	 * 添加成功
	 */
	public static final Integer INSERT_SUCCESS = 1;
	
	/**
	 * 红包名称已存在
	 */
	public static final Integer REPEAT_NAME = 2;
	
	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
