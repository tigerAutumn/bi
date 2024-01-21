package com.pinting.business.model.vo;

import java.util.HashMap;

/**
 * 使用rabbitMQ发送微信消息时的入参
 * @author bianyatian
 * @2018-8-10 下午3:19:21
 */
public class WeChat4RabbitModel {
	private Integer userId; //用户id
	
	private String weChatTempEnumCode; //对应微信模板枚举类的noticeCode
	
	private HashMap<String, String> map; //模板消息的其他数据

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWeChatTempEnumCode() {
		return weChatTempEnumCode;
	}

	public void setWeChatTempEnumCode(String weChatTempEnumCode) {
		this.weChatTempEnumCode = weChatTempEnumCode;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
	
	

}
