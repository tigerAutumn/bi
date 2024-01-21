package com.pinting.business.enums;

/**
 * 短信签名枚举类
 * @project business
 * @author bianyatian
 * @2018-4-18 下午5:37:23
 */
public enum SmsSignEnum {
 
	BGW("1", "【币港湾】","BGW", "币港湾"),
	YUN_DAI("2", "【达飞云贷】", "YUN_DAI", "云贷"),
	ZAN("3", "【赞分期】", "ZAN", "赞分期"),
	ZSD("4", "【赞时贷】", "ZSD", "赞时贷"),
	;
	
	/**
	 * 
	 * @param serial 序列号
	 * @param sign 签名
	 * @param description 描述- 存本地库
	 */
	private SmsSignEnum(String serial, String sign, String partnerCode, String description) {
		this.serial = serial;
		this.sign = sign;
		this.partnerCode = partnerCode;
		this.description = description;
	}

	private String serial;
	
	private String sign;
	
	private String partnerCode;

	private String description;
	

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static SmsSignEnum getSmsSignEnumBySerial(String serial){
		if(null == serial){
			return BGW;
		}
		for (SmsSignEnum smsSign : values()) {
			if(smsSign.getSerial().equals(serial.trim())){
				return smsSign;
			}
		}
		return BGW;
	}

	
	
}
