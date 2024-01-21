package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Bank_AddInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6811210781948103167L;
	/*用户id*/
	private Integer userId;
	/*当出现修改绑定银行卡号时会有id传入*/
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
