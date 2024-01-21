package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      yed
 * Date:        2017/5/25
 * Description: 资金余额查询响应信息业务数据
 */
public class GetFundListInfoData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 412151173069994969L;
	/* 平台编号 */
    private String plat_no;
    /* 平台客户编号 */
    private String platcust;
    /* 交易时间 */
    private String trans_time;
    /* 交易日期 */
    private String trans_date;
    /* 交易金额 */
    private String amt;
    /* 资金变动类型 */
    private String amt_type;
    /* 交易名称 */
    private String trans_name;
	public String getPlat_no() {
		return plat_no;
	}
	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getTrans_time() {
		return trans_time;
	}
	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	public String getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getAmt_type() {
		return amt_type;
	}
	public void setAmt_type(String amt_type) {
		this.amt_type = amt_type;
	}
	public String getTrans_name() {
		return trans_name;
	}
	public void setTrans_name(String trans_name) {
		this.trans_name = trans_name;
	}
	
}