package com.pinting.open.pojo.response.index;

import com.pinting.open.base.response.Response;

public class RegisterResponse extends Response {

	private String mobile;
	
	private String nick;
	
	private Integer userId;
	
	private String userType;
	
	private Integer qianbaoFlag;  //钱报渠道ID

	private Integer agentId;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getQianbaoFlag() {
		return qianbaoFlag;
	}

	public void setQianbaoFlag(Integer qianbaoFlag) {
		this.qianbaoFlag = qianbaoFlag;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
}
