package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUserChannel_BsUserChannelModify extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339226511592999596L;
	
	/**
	 * 添加成功
	 */
	public static final Integer INSERT_SUCCESS = 1;
	
	/**
	 * 修改成功
	 */
	public static final Integer MODIFY_SUCCESS = 2;
	
	/**
	 * 该用户该类型优先支付渠道已存在
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
