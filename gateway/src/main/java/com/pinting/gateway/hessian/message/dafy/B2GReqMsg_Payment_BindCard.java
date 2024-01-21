package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * @Project: business
 * @Title: B2GReqMsg_Payment_BindCard.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午7:53:06
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GReqMsg_Payment_BindCard extends ReqMsg {
	private static final long serialVersionUID = -6384896337112039302L;

	private String customerId; //达飞系统的客户ID
	private String bankName;//开户银行名称
	private String openAccountProvince;//开户省份
	private String subBranchName;//支行名称
	private String openAccountCity;//所在城市
	private String bankCardNo;//银行卡号
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getOpenAccountProvince() {
		return openAccountProvince;
	}
	public void setOpenAccountProvince(String openAccountProvince) {
		this.openAccountProvince = openAccountProvince;
	}
	public String getSubBranchName() {
		return subBranchName;
	}
	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
	}
	public String getOpenAccountCity() {
		return openAccountCity;
	}
	public void setOpenAccountCity(String openAccountCity) {
		this.openAccountCity = openAccountCity;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

}
