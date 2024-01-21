package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.ChargeOffReqDetail;

public class B2GReqMsg_HFBank_ChargeOff extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6494504320426803788L;
	/**产品编号*/
	private  	String 		prod_id;
	/**产品编号*/
	private  	List<ChargeOffReqDetail> 		charge_off_list;
	/**异步通知地址；接口：成标出账通知*/
	private  	String 		notify_url;
	/**支付通道*/
	private  	String 		pay_code;
	/**备注*/
	private  	String 		remark;
	
    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;
    
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public List<ChargeOffReqDetail> getCharge_off_list() {
		return charge_off_list;
	}
	public void setCharge_off_list(List<ChargeOffReqDetail> charge_off_list) {
		this.charge_off_list = charge_off_list;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getPay_code() {
		return pay_code;
	}
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
