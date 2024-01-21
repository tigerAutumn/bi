package com.pinting.gateway.qidian.in.model;

import java.util.Date;

/**
 * @Project: gateway
 * @Title: BaseResModel.java
 * @author Zhou Changzai
 * @date 2015-2-10 下午8:05:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BaseResModel {
	private String respCode;
	private String respMsg;
	private Date responseTime;
	private String hash;
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	
}
