package com.pinting.schedule.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * 消息返回枚举类
 * @author dingpengfeng
 * @2016-6-24 下午10:15:42
 */
public enum PTMessageEnum {
	
	TRANS_SUCCESS("000000", "交易成功"),
	TRANS_ERROR("999999", "交易失败"),
	
	//一些公共类型异常，错误码以90开头6位数字，例：
	TRANSCODE_NOT_FOUND("900001", "交易码不符合规范"),
	DATA_VALIDATE_ERROR("900002", "数据校验失败："),
	
	
	
	//其他需要的类型错误码，请自行添加
	//...
	
	//系统方面异常，错误码以99开头6位数字，例：
	SYSTEM_ERROR("990000", "系统异常")
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
