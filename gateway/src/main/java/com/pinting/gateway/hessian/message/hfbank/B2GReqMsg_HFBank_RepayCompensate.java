package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_HFBank_RepayCompensate extends ReqMsg {	

    /**
	 * 
	 */
	private static final long serialVersionUID = 7907909934364323099L;
	/**订单号 */
    private 	String 		order_no;
    /**商户交易日期 YYYYMMdd */
    private 	Date 		partner_trans_date;
    /**商户交易时间 HHmmss */
    private 	Date 		partner_trans_time;
	/**产品编号*/
	private 	String 		prod_id;
	/**还款金额*/
	private 	Double 		repay_amt;
	/**平台客户编号（借款人）*/
	private 	String 		platcust;
	/**代偿人平台客户编号（或者代偿账户编号）*/
	private 	String 		compensation_platcust;
	/**备注*/
	private 	String 		remark;
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
	public Double getRepay_amt() {
		return repay_amt;
	}
	public void setRepay_amt(Double repay_amt) {
		this.repay_amt = repay_amt;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
