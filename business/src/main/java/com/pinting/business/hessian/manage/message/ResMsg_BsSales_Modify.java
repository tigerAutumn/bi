package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSales_Modify extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8106239184314837545L;
	
	/**
	 * 新增成功
	 */
	public static final Integer INSERT_SUCCESS = 1;
	
	/**
	 * 修改成功
	 */
	public static final Integer MODIFY_SUCCESS = 2;
	/**
	 * 该银行存在这种渠道
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
