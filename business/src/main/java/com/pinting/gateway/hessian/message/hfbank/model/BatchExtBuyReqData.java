package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.List;

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
	private static final long serialVersionUID = 347120743436756730L;
	/**明细编号*/
	private 	String 		detail_no;
	/**投资人编号*/
	private 	String 		platcust;
	/**交易金额（体验金+抵用券+自费金额+手续费）*/
	private 	Double 		trans_amt;
	/**体验金金额*/
	private 	Double 		experience_amt;
	/**抵用券金额*/
	private 	Double 		coupon_amt;
	/**自费金额*/
	private 	Double 		self_amt;
	/**加息*/
	private 	Double 		in_interest;
	/**科目优先级0-可提优先1可投优先*/
	private 	String 		subject_priority;
	/**手续费说明*/
	private 	List<BatchExtBuyReqCommission> 		commission;
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
	public Double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(Double trans_amt) {
		this.trans_amt = trans_amt;
	}
	public Double getExperience_amt() {
		return experience_amt;
	}
	public void setExperience_amt(Double experience_amt) {
		this.experience_amt = experience_amt;
	}
	public Double getCoupon_amt() {
		return coupon_amt;
	}
	public void setCoupon_amt(Double coupon_amt) {
		this.coupon_amt = coupon_amt;
	}
	public Double getSelf_amt() {
		return self_amt;
	}
	public void setSelf_amt(Double self_amt) {
		this.self_amt = self_amt;
	}
	public Double getIn_interest() {
		return in_interest;
	}
	public void setIn_interest(Double in_interest) {
		this.in_interest = in_interest;
	}
	public String getSubject_priority() {
		return subject_priority;
	}
	public void setSubject_priority(String subject_priority) {
		this.subject_priority = subject_priority;
	}
	public List<BatchExtBuyReqCommission> getCommission() {
		return commission;
	}
	public void setCommission(List<BatchExtBuyReqCommission> commission) {
		this.commission = commission;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
