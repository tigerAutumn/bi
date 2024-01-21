package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

public enum PTMessageEnum {
	
	TRANS_SUCCESS("000000", 		"交易成功"),
	TRANS_ERROR("999999", 			"交易失败"),
	
	//一些公共类型异常，错误码以90开头6位数字，例：
	NO_DATA_FOUND("900001", 		"数据不存在"),
	TRANSCODE_NOT_FOUND("900002", 	"交易码不符合规范"),
	DATA_VALIDATE_ERROR("900003", 	"数据校验失败："),
	ILLEGAL_REQUEST("900004",	 	"非法请求"),
	CONNECTION_ERROR("900006",		"通讯异常"),
	CONNECTION_TIME_OUT("900010",	"通讯超时"),
	
	//用户相关类型异常,错误码以91开头6位数字,例：
	
	//订单相关类型异常,错误码以92开头6位数字,例
	ORDER_COMMODITY_SOLD_OUT("920001", 	"兑换商品已售罄"),
	ORDER_USER_INTEGRAL_LESS("920002", 	"您的积分不足"),

	//账务相关类型异常,错误码以93开头6位数字,例：
	
	//数据库操作失败类型异常,错误码以94开头6位数字,例：
	
	//业务相关类型异常,错误码以95开头6位数字,例:
	NOT_CONDITION_FOUND("950001",	"业务规则不匹配"),
	POINTS_GAINS_FAIL("950002",		"积分发放失败"),
	//其他需要的类型错误码,请自行累加2位首数字,例:
	
	//系统方面异常,错误码以99开头6位数字,例：
	SYSTEM_ERROR("990000", "系统异常"),

	;
	
	private PTMessageEnum(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static PTMessageEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (PTMessageEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	/**
	 * 转出Map
	 * @return
	 */
	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (PTMessageEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
	
}
