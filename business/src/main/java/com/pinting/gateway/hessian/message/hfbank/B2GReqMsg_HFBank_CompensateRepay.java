package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_HFBank_CompensateRepay extends ReqMsg {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8150208578291081382L;
	/**订单号 */
    private String order_no;
    /**商户交易日期 YYYYMMdd */
    private Date partner_trans_date;
    /**商户交易时间 HHmmss */
    private Date partner_trans_time;
	/**产品编号*/
	private  	String 		prod_id;
	/**还款期数，如果一次性还款，repay_num为1*/
	private  	Integer 		repay_num;
	/**计划还款日期(yyyyMMdd)*/
	private  	Date 		repay_date;
	/**计划还款金额*/
	private  	Double 		repay_amt;
	/**实际还款日期(yyyyMMdd)*/
	private  	Date 		real_repay_date;
	/**实际还款金额*/
	private  	Double 		real_repay_amt;
	/**平台客户编号（借款人）*/
	private  	String 		platcust;
	/**代偿人平台客户编号（或者代偿账户编号）*/
	private  	String 		compensation_platcust;
	/**交易金额（实际还款金额+手续费金额）*/
	private  	Double 		trans_amt;
	/**手续费金额*/
	private  	Double 		fee_amt;
	/**还款类型 0-代偿还款 1-委托还款*/
	private  	String 		repay_type;
	/**备注*/
	private  	String 		remark;
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public Date getPartner_trans_date() {
		return partner_trans_date;
	}
	public void setPartner_trans_date(Date partner_trans_date) {
		this.partner_trans_date = partner_trans_date;
	}
	public Date getPartner_trans_time() {
		return partner_trans_time;
	}
	public void setPartner_trans_time(Date partner_trans_time) {
		this.partner_trans_time = partner_trans_time;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public Integer getRepay_num() {
		return repay_num;
	}
	public void setRepay_num(Integer repay_num) {
		this.repay_num = repay_num;
	}
	public Date getRepay_date() {
		return repay_date;
	}
	public void setRepay_date(Date repay_date) {
		this.repay_date = repay_date;
	}
	public Double getRepay_amt() {
		return repay_amt;
	}
	public void setRepay_amt(Double repay_amt) {
		this.repay_amt = repay_amt;
	}
	public Date getReal_repay_date() {
		return real_repay_date;
	}
	public void setReal_repay_date(Date real_repay_date) {
		this.real_repay_date = real_repay_date;
	}
	public Double getReal_repay_amt() {
		return real_repay_amt;
	}
	public void setReal_repay_amt(Double real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getCompensation_platcust() {
		return compensation_platcust;
	}
	public void setCompensation_platcust(String compensation_platcust) {
		this.compensation_platcust = compensation_platcust;
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
	public String getRepay_type() {
		return repay_type;
	}
	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
