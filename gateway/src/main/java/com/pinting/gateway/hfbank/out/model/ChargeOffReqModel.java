package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 
 * @project gateway
 * @title ChargeOffReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的出账请求
 */
public class ChargeOffReqModel extends BaseReqModel {

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
	
	
}
