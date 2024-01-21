package com.pinting.business.accounting.loan.enums;

import java.util.HashMap;
import java.util.Map;


public enum PartnerEnum {
	ZAN("ZAN", "赞分期"),
	YUN_DAI("YUN_DAI", "云贷"),
	SEVEN_DAI("7_DAI", "7贷"),
	YUN_DAI_SELF("YUN_DAI_SELF", "云贷自主放款"),
	ZSD("ZSD", "赞时贷"),
	SEVEN_DAI_SELF("7_DAI_SELF", "7贷自主放款"),
	BGW("BGW","币港湾"),
	FREE("FREE","自由资产端"),
	;
	private PartnerEnum(String code,String name){
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
	public static PartnerEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (PartnerEnum type : values()) {
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
		for (PartnerEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getName());
		}
		return enumDataMap;
	}
}
