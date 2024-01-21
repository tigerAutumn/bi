package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSysNews_BsSysNewsModify extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4611683612682043012L;
	
	/**
	 * 添加成功
	 */
	public static final Integer INSERT_SUCCESS = 1;
	
	/**
	 * 修改成功
	 */
	public static final Integer MODIFY_SUCCESS = 2;
	
	/**
	 * 该错误码已存在
	 */
	public static final Integer REPEAT_NAME = 3;
	
	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
