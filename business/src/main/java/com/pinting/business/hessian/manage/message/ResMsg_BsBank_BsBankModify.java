package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsBank_BsBankModify extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -409809685714121822L;

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
	/**
	 * 该银行渠道必须有且只有一个优先级为1的
	 */
	public static final Integer CHANNEL_PRIORITY_ONE = 4;	
	
	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
}
