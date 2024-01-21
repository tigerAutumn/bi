package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ProductSerial_ProductSerialModify extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6314022560832137772L;
	
	/**
	 * 添加成功
	 */
	public static final Integer INSERT_SUCCESS = 1;
	
	/**
	 * 修改成功
	 */
	public static final Integer MODIFY_SUCCESS = 2;
	
	/**
	 * 该系列名称已存在
	 */
	public static final Integer REPEAT_NAME = 3;
	
	/**
	 * 该系列已经被引用，不可修改
	 */
	public static final Integer QUOTE_PRODUCT = 4;
	
	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

}
