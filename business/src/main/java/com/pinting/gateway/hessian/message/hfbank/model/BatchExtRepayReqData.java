package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @project gateway
 * @title BatchExtRepayReqData.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，借款人批量还款明细数据
 */
public class BatchExtRepayReqData  implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1284395394989256716L;
	/**明细编号*/
	private  	String 		detail_no;
	/**标的编号*/
	private  	String 		prod_id;
	/**还款期数，如果一次性还款，repay_num为1*/
	private  	String 		repay_num;
	/**计划还款日期*/
	private  	Date 		repay_date;
	/**计划还款金额*/
	private  	Double 		repay_amt;
	/**实际还款日期*/
	private 	Date 		real_repay_date;
	/**实际还款金额*/
	private  	Double 		real_repay_amt;
	/**平台客户编号（借款人）*/
	private  	String 		platcust;
	/**交易金额（实际还款金额+手续费金额）*/
	private  	Double 		trans_amt;
	/**手续费金额*/
	private  	Double 		fee_amt;
	/**备注*/
	private  	String 		remarkprivate;
	public String getDetail_no() {
		return detail_no;
	}
	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getRepay_num() {
		return repay_num;
	}
	public void setRepay_num(String repay_num) {
		this.repay_num = repay_num;
	}

	public Date getRepay_date() {
		return repay_date;
	}

	public void setRepay_date(Date repay_date) {
		this.repay_date = repay_date;
	}

	public Date getReal_repay_date() {
		return real_repay_date;
	}

	public void setReal_repay_date(Date real_repay_date) {
		this.real_repay_date = real_repay_date;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getRemarkprivate() {
		return remarkprivate;
	}
	public void setRemarkprivate(String remarkprivate) {
		this.remarkprivate = remarkprivate;
	}
	public Double getRepay_amt() {
		return repay_amt;
	}
	public void setRepay_amt(Double repay_amt) {
		this.repay_amt = repay_amt;
	}
	public Double getReal_repay_amt() {
		return real_repay_amt;
	}
	public void setReal_repay_amt(Double real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}
	public Double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(Double trans_amt) {
		this.trans_amt = trans_amt;
	}
	public Double getFee_amt() {
		return fee_amt;
	}
	public void setFee_amt(Double fee_amt) {
		this.fee_amt = fee_amt;
	}
}
