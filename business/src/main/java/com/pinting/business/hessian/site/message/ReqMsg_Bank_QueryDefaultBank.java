package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 判断用户是否绑定，已绑定的老用户显示最近一次使用的银行卡 入参
 * @author shencheng
 * @2015-11-18 下午6:51:44
 */
public class ReqMsg_Bank_QueryDefaultBank extends ReqMsg {

	private static final long serialVersionUID = 2555937128992615168L;
	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
