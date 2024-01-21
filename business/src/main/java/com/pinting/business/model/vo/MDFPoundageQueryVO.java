package com.pinting.business.model.vo;

import java.util.Date;

public class MDFPoundageQueryVO extends PageInfoObject {

	private String userName; //用户姓名
	
	private String userMobile; //用户手机号
	
	private String userType; //客户类型
	
	private String payType; //手续费支付类型
	
	private Date beginTime; //开始时间
	
	private Date overTime;//结束时间

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
