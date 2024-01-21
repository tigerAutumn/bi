package com.pinting.gateway.dafy.out.model;

/**
 * @Project: gateway
 * @Title: BindBankCardReqModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午3:00:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BindBankcardReqModel extends BaseReqModel{
	private String customerId;
	private String bankName;
	private String openAccountProvince;
	private String subbranchName;
	private String bankcardNo;
	private String openAccountCity;
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
	public String getSubbranchName() {
		return subbranchName;
	}
	public void setSubbranchName(String subbranchName) {
		this.subbranchName = subbranchName;
	}
	public String getBankcardNo() {
		return bankcardNo;
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public String getOpenAccountCity() {
		return openAccountCity;
	}
	public void setOpenAccountCity(String openAccountCity) {
		this.openAccountCity = openAccountCity;
	}
	
}
