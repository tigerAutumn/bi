package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqFundData;
import com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqRepayPlan;

public class B2GReqMsg_HFBank_EstablishOrAbandon extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7892050196512784396L;
	/*产品编号*/
	private		String 		prod_id;
	/*成标、废标标记    2 成标 3废标*/
	private		String 		flag;
	/*成标时候需要进行的分佣说明*/
	private		List<EstablishOrAbandonReqFundData> 	funddata;
	/*还款计划表，标的成立时必填，该计划为借款人还款计划*/
	private		List<EstablishOrAbandonReqRepayPlan> 		repay_plan_list;
	/*备注*/
	private		String 		remark;
	
    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

	public String getProd_id() {
		return prod_id;
	}

	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	public List<EstablishOrAbandonReqFundData> getFunddata() {
		return funddata;
	}

	public void setFunddata(List<EstablishOrAbandonReqFundData> funddata) {
		this.funddata = funddata;
	}

	public List<EstablishOrAbandonReqRepayPlan> getRepay_plan_list() {
		return repay_plan_list;
	}

	public void setRepay_plan_list(
			List<EstablishOrAbandonReqRepayPlan> repay_plan_list) {
		this.repay_plan_list = repay_plan_list;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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
}
