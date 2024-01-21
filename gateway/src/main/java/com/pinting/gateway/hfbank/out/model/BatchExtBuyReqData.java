package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONObject;

/**
 * 
 * @project gateway
 * @title BatchExtBuyReqData.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管-批量投标业务数据
 */
public class BatchExtBuyReqData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9111791408860811860L;
	/**明细编号*/
	private 	String 		detail_no;
	/**投资人编号*/
	private 	String 		platcust;
	/**交易金额（体验金+抵用券+自费金额+手续费）*/
	private 	String 		trans_amt;
	/**体验金金额*/
	private 	String 		experience_amt;
	/**抵用券金额*/
	private 	String 		coupon_amt;
	/**自费金额*/
	private 	String 		self_amt;
	/**加息*/
	private 	String 		in_interest;
	/**科目优先级0-可提优先1可投优先*/
	private 	String 		subject_priority;
	/**手续费说明*/
	private 	JSONObject 		commission;
	/**备注*/
	private 	String 		remark;
	
	
	public String getDetail_no() {
		return detail_no;
	}
	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	public String getExperience_amt() {
		return experience_amt;
	}
	public void setExperience_amt(String experience_amt) {
		this.experience_amt = experience_amt;
	}
	public String getCoupon_amt() {
		return coupon_amt;
	}
	public void setCoupon_amt(String coupon_amt) {
		this.coupon_amt = coupon_amt;
	}
	public String getSelf_amt() {
		return self_amt;
	}
	public void setSelf_amt(String self_amt) {
		this.self_amt = self_amt;
	}
	public String getIn_interest() {
		return in_interest;
	}
	public void setIn_interest(String in_interest) {
		this.in_interest = in_interest;
	}
	public String getSubject_priority() {
		return subject_priority;
	}
	public void setSubject_priority(String subject_priority) {
		this.subject_priority = subject_priority;
	}
	
	public JSONObject getCommission() {
		return commission;
	}
	public void setCommission(JSONObject commission) {
		this.commission = commission;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
