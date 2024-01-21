package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_Regist extends ResMsg {
    /**
     * 
     */
    private static final long serialVersionUID = 8901587499067500449L;
    /**
     * 
     */

    private Integer           userId;
    private String            nick;
    private String            mobile;
    private String            mobileCode;
    private String            password;
    private String            payPassword;
    private String            userType;
    private Integer           flag;
    private Integer           qianbaoFlag;  // 钱报返回所需
    private Integer           agentId;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getQianbaoFlag() {
		return qianbaoFlag;
	}

	public void setQianbaoFlag(Integer qianbaoFlag) {
		this.qianbaoFlag = qianbaoFlag;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
