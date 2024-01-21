package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分收入交易记录表-交易类型枚举
 * @project business
 * @author bianyatian
 * @2018-5-10 下午5:06:49
 */
public enum MallRuleEnum {
	
	MALL_REGISTER("REGISTER","注册"),
	MALL_OPEN_DEPOSIT("OPEN_DEPOSIT", "开通存管"),
	MALL_FINISH_RISK_ASSESSMENT("FINISH_RISK_ASSESSMENT", "风险测评"),
	MALL_FIRST_INVEST("FIRST_INVEST", "首次投资"),
	MALL_INVEST("INVEST","投资"),
	MALL_TOTAL_INVEST("TOTAL_INVEST", "累计投资"),
	MALL_SIGN("SIGN","签到"),
	;
	
	private MallRuleEnum(String code, String message){
		this.code = code; //income-交易类型
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
	public static MallRuleEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (MallRuleEnum type : values()) {
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
		for (MallRuleEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
	
	
}
