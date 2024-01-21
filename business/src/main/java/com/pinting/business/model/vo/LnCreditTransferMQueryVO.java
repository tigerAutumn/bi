package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台债权转让统计-查询条件
 * @author bianyatian
 * @2016-12-2 下午2:41:42
 */
public class LnCreditTransferMQueryVO extends PageInfoObject {
	
	private String inUserName; //转入（承接）理财用户姓名
	
	private String inUserMobile; //转入（承接）理财用户手机号
	
	private Date beginTime; //债权转让发生开始时间
	
	private Date overTime;//债权转让发生结束时间
	
	private String outUserName; //受让人用户姓名
	
	private String partnerCode; //YUN_DAI_SELF /ZSD

	private String partnerBusinessFlag; //借款产品标识

	private String queryPartnerBusinessFlag;

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

	public String getInUserName() {
		return inUserName;
	}

	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}

	public String getInUserMobile() {
		return inUserMobile;
	}

	public void setInUserMobile(String inUserMobile) {
		this.inUserMobile = inUserMobile;
	}

	public String getOutUserName() {
		return outUserName;
	}

	public void setOutUserName(String outUserName) {
		this.outUserName = outUserName;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}

	public String getQueryPartnerBusinessFlag() {
		return queryPartnerBusinessFlag;
	}

	public void setQueryPartnerBusinessFlag(String queryPartnerBusinessFlag) {
		this.queryPartnerBusinessFlag = queryPartnerBusinessFlag;
	}
}
