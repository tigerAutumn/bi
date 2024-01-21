package com.pinting.gateway.hfbank.in.model;

import java.util.Date;

/**
 * @Project: gateway
 * @Title: BaseModel.java
 * @author Zhou Changzai
 * @date 2015-2-10 下午8:03:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BaseReqModel {
	private String token;
	private String transCode;
	private Date requestTime;
	private String hash;
	private String jsonList;//用作保存 json数组字符串

	/* 平台编号 */
	private String plat_no;
	/* 订单号 */
	private String order_no;
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

	public String getSigndata() {
		return signdata;
	}

	public void setSigndata(String signdata) {
		this.signdata = signdata;
	}

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public String getJsonList() {
		return jsonList;
	}
	public void setJsonList(String jsonList) {
		this.jsonList = jsonList;
	}
	@Override
	public String toString() {
		return "BaseReqModel [token=" + token + ", transCode=" + transCode
				+ ", requestTime=" + requestTime + ", hash=" + hash
				+ ", jsonList=" + jsonList + "]";
	}
	
}
