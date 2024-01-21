package com.pinting.gateway.hfbank.in.model;
/**
 * 
 * @project gateway
 * @title LiquidationNoticeReqModel.java
 * @author Dragon & cat
 * @date 2017-5-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class LiquidationNoticeReqModel extends BaseReqModel {
	/*清算标识：1:完成*/
	private		String		liquidation_flag;
	private 	String  	notify_code;
	private		String		notify_name;
	private		String		notify_url;
	public String getLiquidation_flag() {
		return liquidation_flag;
	}

	public void setLiquidation_flag(String liquidation_flag) {
		this.liquidation_flag = liquidation_flag;
	}

	public String getNotify_code() {
		return notify_code;
	}

	public void setNotify_code(String notify_code) {
		this.notify_code = notify_code;
	}

	public String getNotify_name() {
		return notify_name;
	}

	public void setNotify_name(String notify_name) {
		this.notify_name = notify_name;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
}