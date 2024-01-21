package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 4.8.6.清算状态查询
 * @author SHENGP
 * @date  2017年5月11日 下午4:38:09
 */
public class B2GReqMsg_HFBank_QueryLiquidationInfo extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8833400626460584957L;

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
