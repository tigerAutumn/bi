package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

public enum MallAccountEnum {
	
	MALL_POINTS_JSH("POINTS_JSH", "积分余额户"),
	;
	
	private MallAccountEnum(String code, String message){
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
	public static MallAccountEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (MallAccountEnum type : values()) {
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
		for (MallAccountEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
	
}
