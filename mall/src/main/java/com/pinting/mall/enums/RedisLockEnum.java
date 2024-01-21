package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * redis锁枚举
 * @author Gemma
 *
 */
public enum RedisLockEnum {
	MALL_LOCK_GRANT_POINTS("mallPointsGrantSync", 	"积分发放业务锁"),
	MALL_LOCK_SIGN("mallSignSync", 				"签到锁"),
	MALL_LOCK_REGISTER("mallRegisterSync", 		"注册锁"),
	MALL_LOCK_INVEST("mallInvestSync", 			"投资锁"),
	MALL_LOCK_OPEN_DEPOSITE("mallOpenDepositeSync", 	"开通存管锁"),
	MALL_LOCK_RISK_ASSESSMENT("mallRishSync", 	"风险测评锁"),
	MALL_LOCK_EXCHANGE_COMMODITY("mallExchangeCommoditySync", 	"商品兑换业务锁"),
	LOCK_ACCESSTOKEN_SYNC("accessTokenSync", "微信accessToken锁"),
	LOCK_TICKET_SYNC("ticketSync", "微信ticket锁"),
	;
	private RedisLockEnum(String key,String keyName){
		this.key = key;
		this.keyName = keyName;
	}
	private String key;
	private String keyName;
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static RedisLockEnum getEnumByKey(String key){
		if (null == key) {
			return null;
		}
		for (RedisLockEnum type : values()) {
			if (type.getKey().equals(key.trim()))
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
		for (RedisLockEnum type : values()) {
			enumDataMap.put(type.getKey(), type.getKeyName());
		}
		return enumDataMap;
	}
	
}
