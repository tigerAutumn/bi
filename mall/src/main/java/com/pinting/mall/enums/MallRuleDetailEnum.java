package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分规则详情key
 * @author SHENGUOPING
 * @date  2018年5月11日 下午2:25:01
 */
public enum MallRuleDetailEnum {
	
	MALL_INVEST_AMOUNT_BEGIN("INVEST_AMOUNT_BEGIN", "投资满额起始额"),
	MALL_INVEST_AMOUNT_END("INVEST_AMOUNT_END", "投资满额截至额"),
	MALL_SIGN_INIT_POINT("SIGN_INIT_POINT", "签到初始积分"),
	MALL_SIGN_INCREMENT_POINT("SIGN_INCREMENT_POINT", "签到递增积分"),
	MALL_SIGN_AWARD_POINT("SIGN_AWARD_POINT", "签到额外奖励积分"),
	MALL_SIGN_INIT_POINT_APP("SIGN_INIT_POINT_APP", "签到初始积分（app）"),
	MALL_SIGN_INCREMENT_POINT_APP("SIGN_INCREMENT_POINT_APP", "签到递增积分(app)"),
	MALL_SIGN_AWARD_POINT_APP("SIGN_AWARD_POINT_APP", "签到额外奖励积分(app)"),
	MALL_EXCHANGE_RATE("EXCHANGE_RATE", "投资兑换比例"),
	;
	
	private MallRuleDetailEnum(String code, String message){
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
	public static MallRuleDetailEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (MallRuleDetailEnum type : values()) {
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
		for (MallRuleDetailEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
}
