package com.pinting.gateway.hfbank.out.model;
/**
 * 
 * @project gateway
 * @title ModifyPayOutAcctReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的出账信息修改请求
 */
public class ModifyPayOutAcctReqModel extends BaseReqModel {
	/**标的编号*/
	private 	String 		prod_id;
	/**产品收款人开户行（八位编号）*/
	private 	String 		open_branch;
	/**产品收款人银行卡号*/
	private 	String 		withdraw_account;
	/**卡类型(1-个人 2-企业)*/
	private 	String 		account_type;
	/**产品收款人姓名*/
	private 	String 		payee_name;
	/**备注*/
	private 	String 		remark;
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getOpen_branch() {
		return open_branch;
	}
	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}
	public String getWithdraw_account() {
		return withdraw_account;
	}
	public void setWithdraw_account(String withdraw_account) {
		this.withdraw_account = withdraw_account;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	public String getPayee_name() {
		return payee_name;
	}
	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
