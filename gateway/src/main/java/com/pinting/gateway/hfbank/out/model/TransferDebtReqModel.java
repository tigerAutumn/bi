package com.pinting.gateway.hfbank.out.model;

import net.sf.json.JSONObject;

/**
 * 
 * @project gateway
 * @title TransferDebtReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管 ，标的转让请求
 */
public class TransferDebtReqModel extends BaseReqModel {
	/**转让人平台客户号*/
	private  	String 		platcust;
	/**转让份额*/
	private  	String 		trans_share;
	/**产品编号*/
	private  	String 		prod_id;
	/**交易金额（成交价格+出让人手续费+受让人手续费+转让收益）*/
	private  	String 		trans_amt;
	/**自费价格*/
	private  	String 		deal_amount;
	/**抵用劵金额*/
	private  	String 		coupon_amt;
	/**成交账号（受让人平台客户编号）*/
	private  	String 		deal_platcust;
	/**出让人手续费说明*/
	private  	JSONObject 		commission;
	/**受让人手续费说明*/
	private  	JSONObject 		commission_ext;
	/**发布时间(YYYY-MM-DD HH:mm:ss)*/
	private 	String 		publish_date;
	/**成交时间(YYYY-MM-DD HH:mm:ss)*/
	private 	String 		trans_date;
	/**转让收益*/
	private 	String 		transfer_income;
	/**收益出资方账户    平台：01  ；个人：对应platcust*/
	private 	String 		income_acct;
	/**涉及的标的编号，不同标的之间用逗号分隔(eg:P0001,P0002)*/
	private 	String 		related_prod_ids;
	/**科目优先级0-可提优先1可投优先 新增*/
	private 	String		subject_priority;
	/**备注*/
	private 	String 		remark;
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getDeal_platcust() {
		return deal_platcust;
	}
	public void setDeal_platcust(String deal_platcust) {
		this.deal_platcust = deal_platcust;
	}
	public JSONObject getCommission() {
		return commission;
	}
	public void setCommission(JSONObject commission) {
		this.commission = commission;
	}
	public JSONObject getCommission_ext() {
		return commission_ext;
	}
	public void setCommission_ext(JSONObject commission_ext) {
		this.commission_ext = commission_ext;
	}
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	public String getIncome_acct() {
		return income_acct;
	}
	public void setIncome_acct(String income_acct) {
		this.income_acct = income_acct;
	}
	public String getRelated_prod_ids() {
		return related_prod_ids;
	}
	public void setRelated_prod_ids(String related_prod_ids) {
		this.related_prod_ids = related_prod_ids;
	}
	
	public String getSubject_priority() {
		return subject_priority;
	}
	public void setSubject_priority(String subject_priority) {
		this.subject_priority = subject_priority;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTrans_share() {
		return trans_share;
	}
	public void setTrans_share(String trans_share) {
		this.trans_share = trans_share;
	}
	public String getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	public String getDeal_amount() {
		return deal_amount;
	}
	public void setDeal_amount(String deal_amount) {
		this.deal_amount = deal_amount;
	}
	public String getCoupon_amt() {
		return coupon_amt;
	}
	public void setCoupon_amt(String coupon_amt) {
		this.coupon_amt = coupon_amt;
	}
	public String getTransfer_income() {
		return transfer_income;
	}
	public void setTransfer_income(String transfer_income) {
		this.transfer_income = transfer_income;
	}
}
