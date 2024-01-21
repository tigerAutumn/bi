package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.ProdRepayReqFundData;

import java.util.Date;

public class B2GReqMsg_HFBank_ProdRepay extends ReqMsg {
	
	private static final long serialVersionUID = 7910241868340142797L;
	/**标的编号*/
	private 	String 		prod_id;
	/**还款期数，如果一次性还款，repay_num为1*/
	private 	Integer 		repay_num;
	/**是否整个标的还清：0-是，1-否； */
	private 	String 		is_payoff;
	/**交易金额（所有实际还款金额之和）*/
	private 	Double 		trans_amt;
	/**资金数据，json格式记录还款金额*/
	private 	ProdRepayReqFundData 		funddata;
	/**本期已还清 0-是，1-否*/
	private		String 		repay_flag;
	/**备注 */
	private 	String 		remark;
    /** 订单号 */
    private String order_no;
    /** 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;
    /** 商户交易时间 HHmmss */
    private Date partner_trans_time;

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
	public String getIs_payoff() {
		return is_payoff;
	}
	public void setIs_payoff(String is_payoff) {
		this.is_payoff = is_payoff;
	}
	public String getRepay_flag() {
		return repay_flag;
	}
	public void setRepay_flag(String repay_flag) {
		this.repay_flag = repay_flag;
	}
	public Double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(Double trans_amt) {
		this.trans_amt = trans_amt;
	}
	public ProdRepayReqFundData getFunddata() {
		return funddata;
	}
	public void setFunddata(ProdRepayReqFundData funddata) {
		this.funddata = funddata;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
