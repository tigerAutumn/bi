package com.pinting.gateway.hfbank.out.model;

import java.util.Date;

/**
 * @Project: gateway
 * @Title: BaseModel.java
 * @author Zhou Changzai
 * @date 2015-2-10 下午8:03:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BaseReqModel {
//	private String token;
//	private String transCode;
//	private Date requestTime;
//	private String hash;
//	private String clientKey;

	/* 商户平台在资金账户管理平台注册的平台编号 */
	private String plat_no;

	/* 订单号 */
	private String order_no;

	/* 商户交易日期 YYYYMMdd */
	private String partner_trans_date;

	/* 商户交易时间 HHmmss */
	private String partner_trans_time;

	/* 签名数据 */
	private String signdata;


	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getPartner_trans_date() {
		return partner_trans_date;
	}

	public void setPartner_trans_date(String partner_trans_date) {
		this.partner_trans_date = partner_trans_date;
	}

	public String getPartner_trans_time() {
		return partner_trans_time;
	}

	public void setPartner_trans_time(String partner_trans_time) {
		this.partner_trans_time = partner_trans_time;
	}

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}
}
