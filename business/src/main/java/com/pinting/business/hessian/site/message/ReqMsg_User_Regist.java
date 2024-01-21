package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_Regist extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3264289376022327934L;

	private String recommendId;
	@NotNull(message="密码不能为空")
    @Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="密码格式错误")
	private String password;
	private String nick;
	private String mobile;
	private String mobileCode;
	private String payPassword;
	private Integer flag ;
	private Integer agentId;
	private String qianbao;
	private String inviteCode;
	private Integer regTerminal;
	private String managerInviteCode;
	private String viewAgentId; //"钱报或柯桥"
	
	public Integer getRegTerminal() {
        return regTerminal;
    }
    public void setRegTerminal(Integer regTerminal) {
        this.regTerminal = regTerminal;
    }
	
	public String getQianbao() {
        return qianbao;
    }
    public void setQianbao(String qianbao) {
        this.qianbao = qianbao;
    }

    
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getManagerInviteCode() {
		return managerInviteCode;
	}
	public void setManagerInviteCode(String managerInviteCode) {
		this.managerInviteCode = managerInviteCode;
	}
	public String getViewAgentId() {
		return viewAgentId;
	}
	public void setViewAgentId(String viewAgentId) {
		this.viewAgentId = viewAgentId;
	}
	
	
}
