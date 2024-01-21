package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;
@Deprecated
public class ReqMsg_Invest_PrincipalListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3862574293307606464L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	private String nick;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
}
