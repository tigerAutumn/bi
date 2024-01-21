package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 财务确认处理查看详情中查询用户基本信息   入参
 * Created by shh on 2016/11/3 21:26.
 */
public class ReqMsg_BsUser_BsUserFinancialConfirmation extends ReqMsg {

	private static final long serialVersionUID = 6118991065828366145L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
