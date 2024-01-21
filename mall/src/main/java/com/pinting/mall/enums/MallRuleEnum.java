package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

public enum MallRuleEnum {
	
	MALL_REGISTER("REGISTER", "注册", "成功注册"),
	MALL_OPEN_DEPOSIT("OPEN_DEPOSIT", "开通存管", "成功开通存管账户"),
	MALL_FINISH_RISK_ASSESSMENT("FINISH_RISK_ASSESSMENT", "完成风险测评", "成功完成风险测评"),
	MALL_FIRST_INVEST("FIRST_INVEST", "首次加入产品", "成功完成首次加入产品"),
	MALL_INVEST("INVEST", "投资", "成功加入产品"),
	MALL_TOTAL_INVEST("TOTAL_INVEST", "累计加入产品", "累计加入产品积分奖励"),
	MALL_SIGN("SIGN", "签到", "签到"),
	MALL_EXCHANGE("EXCHANGE", "积分商城兑换", "积分商城兑换"),
	MALL_BIRTHDAY("BIRTHDAY", "生日奖励", "用户生日奖励"),
	;
	
	private MallRuleEnum(String code, String wxSceneTypeDesc, String message){
		this.code = code; //rule-场景--交易类型对应
		this.wxSceneTypeDesc = wxSceneTypeDesc;
		this.message = message;
	}
	
	private String code;
	private String wxSceneTypeDesc;
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

	public String getWxSceneTypeDesc() {
		return wxSceneTypeDesc;
	}

	public void setWxSceneTypeDesc(String wxSceneTypeDesc) {
		this.wxSceneTypeDesc = wxSceneTypeDesc;
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
