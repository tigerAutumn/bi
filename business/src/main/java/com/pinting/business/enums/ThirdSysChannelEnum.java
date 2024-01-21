package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

public enum ThirdSysChannelEnum {
	
	YUN_DAI("YUN_DAI", "云贷"),
	SEVEN_DAI("7_DAI", "7贷"),
	;
	private ThirdSysChannelEnum(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static ThirdSysChannelEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (ThirdSysChannelEnum type : values()) {
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
		for (ThirdSysChannelEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getName());
		}
		return enumDataMap;
	}
	
}
