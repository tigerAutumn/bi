package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_Login extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7677448441612651278L;
	
	private Integer userId;
	private String nick;
	private String mobile;
	private String userType;
	private String agentId;
	private Integer realAgentId;// 此处是真的agentId，上面的agentId专门标识钱报。
	
	private Integer cookieMaxAge;  //cookie失效时间
	
	public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
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
	public Integer getRealAgentId() {
		return realAgentId;
	}
	public void setRealAgentId(Integer realAgentId) {
		this.realAgentId = realAgentId;
	}
	public Integer getCookieMaxAge() {
		return cookieMaxAge;
	}
	public void setCookieMaxAge(Integer cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}
	
}
