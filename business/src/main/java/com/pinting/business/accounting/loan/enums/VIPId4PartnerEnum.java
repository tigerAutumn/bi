package com.pinting.business.accounting.loan.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * VIP用户id在sysconfig中的key
 * @author bianyatian
 * @2017-12-12 下午1:56:35
 */
public enum VIPId4PartnerEnum {

	YUN_DAI_SELF("YUN_DAI_SELF", "YUN_DAI_SELF_SUPER_FINANCE_USER_ID", "YUN_DAI_SELF_SUPER_PRODUCT_ID","YUN_DAI_COMPENSATE_USER_ID"),
	ZSD("ZSD", "ZSD_SUPER_FINANCE_USER_ID", "ZSD_SUPER_PRODUCT_ID", "ZSD_COMPENSATE_USER_ID"),
	SEVEN_DAI_SELF("7_DAI_SELF", "7_DAI_SELF_SUPER_FINANCE_USER_ID", "7_DAI_SELF_SUPER_PRODUCT_ID", "7_DAI_COMPENSATE_USER_ID"),
	
	FREE("FREE","FREE_SUPER_FINANCE_USER_ID","FREE_SUPER_PRODUCT_ID",""),
	;

	private VIPId4PartnerEnum(String code, String vipIdKey, String vipProductIdKey, String compensateIdKey){
		this.code = code;//合作方编码
		this.vipIdKey = vipIdKey;//VIP用户id对应key
		this.vipProductIdKey = vipProductIdKey;//VIP专享产品对应key
		this.compensateIdKey = compensateIdKey;//代偿人用户id对应key
	}

	private String code;
	private String vipIdKey;
	private String vipProductIdKey;
	private String compensateIdKey;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getVipIdKey() {
		return vipIdKey;
	}
	public void setVipIdKey(String vipIdKey) {
		this.vipIdKey = vipIdKey;
	}
	public String getVipProductIdKey() {
		return vipProductIdKey;
	}
	public void setVipProductIdKey(String vipProductIdKey) {
		this.vipProductIdKey = vipProductIdKey;
	}
	@Deprecated
	public String getCompensateIdKey() {
		return compensateIdKey;
	}

	public void setCompensateIdKey(String compensateIdKey) {
		this.compensateIdKey = compensateIdKey;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static VIPId4PartnerEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (VIPId4PartnerEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	/**
	 * 转出Map
	 * @return
	 */
	public static Map<String, String> toVipIdMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (VIPId4PartnerEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getVipIdKey());
		}
		return enumDataMap;
	}
}
