package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_User_InfoQuery.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:46:22
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_User_InfoQuery extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4698417562914840050L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;//用户编号	必填
	private String nick;//用户名	可选
	private String mobile;//手机	可选
	
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
