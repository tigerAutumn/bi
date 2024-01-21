package com.pinting.gateway.hfbank.out.model;
/**
 * 
 * @project gateway
 * @title PlatformTransferReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，平台转个人请求
 */
public class PlatformTransferReqModel extends BaseReqModel {

	
	/**平台账户(01 为平台账户，从平台自有资金转账)*/
	private 	String 		plat_account;
	/**金额*/
	private 	String 		amount;
	/**电子账户*/
	private 	String 		platcust;
	/**原因*/
	private 	String 		remark;
	/**原因类型*/
	private 	String 		cause_type;
	public String getPlat_account() {
		return plat_account;
	}
	public void setPlat_account(String plat_account) {
		this.plat_account = plat_account;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCause_type() {
		return cause_type;
	}
	public void setCause_type(String cause_type) {
		this.cause_type = cause_type;
	}
}
