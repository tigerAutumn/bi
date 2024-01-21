package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class MUser extends PageInfoObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = -529408985521136921L;

	private Integer id;

    private String name;

    private String nick;

    private String password;

    private String email;

    private String mobile;

    private Integer status;

    private Integer roleId;

    private String userType;

    private Date createTime;

    private Date loginTime;

    private Date logoutTime;
    
    private Integer loginFailTimes;
    
    private Date loginFailTime;

    private String note;
    private String roleName;
    
    private String agentName;

    public String getRoleName() {
		return roleName;
	}

    public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    private Integer agentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }
    
    public Integer getLoginFailTimes() {
		return loginFailTimes;
	}

	public void setLoginFailTimes(Integer loginFailTimes) {
		this.loginFailTimes = loginFailTimes;
	}

	public Date getLoginFailTime() {
		return loginFailTime;
	}

	public void setLoginFailTime(Date loginFailTime) {
		this.loginFailTime = loginFailTime;
	}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
}