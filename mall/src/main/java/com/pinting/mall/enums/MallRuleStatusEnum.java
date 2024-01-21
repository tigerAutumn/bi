package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分规则状态
 * @author SHENGUOPING
 * @date  2018年5月11日 下午2:13:19
 */
public enum MallRuleStatusEnum {

	MALL_RULE_STATUS_OPEN("OPEN", "开启"),
	MALL_RULE_STATUS_CLOSE("CLOSE", "关闭"),
	MALL_RULE_STATUS_DELETED("DELETED", "已删除"),
	;
	
	private MallRuleStatusEnum(String code, String message){
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
	public static MallRuleStatusEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (MallRuleStatusEnum type : values()) {
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
		for (MallRuleStatusEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
}
