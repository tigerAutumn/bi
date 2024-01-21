package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_AssetInfoQuery extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2768340300064627015L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	private String nick;
	private String mobile;
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
