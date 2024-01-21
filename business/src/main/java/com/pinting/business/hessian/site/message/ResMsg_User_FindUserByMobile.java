package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_FindUserByMobile extends ResMsg {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9064046000287444804L;

	private Integer id;

    private String nick;

    private String userName;

    private String mobile;

    private String urgentName;

    private String urgentMobile;

    private Integer relation;

    private String email;

    private String password;

    private String payPassword;

    private Date loginTime;

    private Date logoutTime;

    private Integer loginFailTimes;

    private Date loginFailTime;

    private Integer payFailTimes;

    private Date payFailTime;

    private String idCard;

    private Integer status;

    private Integer isBindName;

    private Integer isBindBank;

    private Double currentInterest;

    private Double currentBonus;

    private Double totalInterest;

    private Double totalBonus;

    private Double canWithdraw;

    private Integer totalTrans;

    private Integer recommendId;

    private Integer createChannel;

    private String userType;

    private Integer agentId;

    private Date registerTime;

    private Date firstBuyTime;

    private Integer returnPath;

    private Integer recentBankCardId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUrgentName() {
		return urgentName;
	}

	public void setUrgentName(String urgentName) {
		this.urgentName = urgentName;
	}

	public String getUrgentMobile() {
		return urgentMobile;
	}

	public void setUrgentMobile(String urgentMobile) {
		this.urgentMobile = urgentMobile;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
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

	public Integer getPayFailTimes() {
		return payFailTimes;
	}

	public void setPayFailTimes(Integer payFailTimes) {
		this.payFailTimes = payFailTimes;
	}

	public Date getPayFailTime() {
		return payFailTime;
	}

	public void setPayFailTime(Date payFailTime) {
		this.payFailTime = payFailTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsBindName() {
		return isBindName;
	}

	public void setIsBindName(Integer isBindName) {
		this.isBindName = isBindName;
	}

	public Integer getIsBindBank() {
		return isBindBank;
	}

	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
	}

	public Double getCurrentInterest() {
		return currentInterest;
	}

	public void setCurrentInterest(Double currentInterest) {
		this.currentInterest = currentInterest;
	}

	public Double getCurrentBonus() {
		return currentBonus;
	}

	public void setCurrentBonus(Double currentBonus) {
		this.currentBonus = currentBonus;
	}

	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Double getTotalBonus() {
		return totalBonus;
	}

	public void setTotalBonus(Double totalBonus) {
		this.totalBonus = totalBonus;
	}

	public Double getCanWithdraw() {
		return canWithdraw;
	}

	public void setCanWithdraw(Double canWithdraw) {
		this.canWithdraw = canWithdraw;
	}

	public Integer getTotalTrans() {
		return totalTrans;
	}

	public void setTotalTrans(Integer totalTrans) {
		this.totalTrans = totalTrans;
	}

	public Integer getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}

	public Integer getCreateChannel() {
		return createChannel;
	}

	public void setCreateChannel(Integer createChannel) {
		this.createChannel = createChannel;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getFirstBuyTime() {
		return firstBuyTime;
	}

	public void setFirstBuyTime(Date firstBuyTime) {
		this.firstBuyTime = firstBuyTime;
	}

	public Integer getReturnPath() {
		return returnPath;
	}

	public void setReturnPath(Integer returnPath) {
		this.returnPath = returnPath;
	}

	public Integer getRecentBankCardId() {
		return recentBankCardId;
	}

	public void setRecentBankCardId(Integer recentBankCardId) {
		this.recentBankCardId = recentBankCardId;
	}
    
    
}
