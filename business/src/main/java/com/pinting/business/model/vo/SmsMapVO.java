package com.pinting.business.model.vo;

import org.apache.commons.lang.StringUtils;

public class SmsMapVO {
	
	private String message; //短信内容
	
	private String addserial; //扩展号，不同扩展号用于不同的短信签名，默认为1，币港湾。

	private Integer messageType; // messageType短信类型：1通知类短信，2营销短信
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAddserial() {
		if(StringUtils.isBlank(addserial)){
			return "1";
		}
		return addserial;
	}

	public void setAddserial(String addserial) {
		this.addserial = addserial;
	}

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

}
