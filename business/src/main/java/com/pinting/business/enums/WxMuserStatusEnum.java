package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 运营数据微信用户管理状态
 * @author SHENGUOPING
 * @date  2018年6月13日 下午2:21:22
 */
public enum WxMuserStatusEnum {

	WX_MUSER_STATUS_OPEN("OPEN", "开启"),
	WX_MUSER_STATUS_DELETED("DELETED", "已删除"),
	;
	
	private WxMuserStatusEnum(String code, String message){
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
	public static WxMuserStatusEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (WxMuserStatusEnum type : values()) {
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
		for (WxMuserStatusEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
}
