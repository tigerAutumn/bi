package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台保证金代收代付 查询 
 * @author bianyatian
 * @2016-12-7 上午11:21:02
 */
public class MDepositDsDfQueryVO extends PageInfoObject {

	private String userName; //用户姓名
	
	private String userMobile; //用户手机号
	
	private String dsdfType; //代收代付类型
	
	private Date beginTime; //开始时间
	
	private Date overTime;//结束时间
	
	private String partnerCode; //资产方ZAN/ZSD

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

	public String getDsdfType() {
		return dsdfType;
	}

	public void setDsdfType(String dsdfType) {
		this.dsdfType = dsdfType;
	}

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

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	
}
