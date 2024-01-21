package com.pinting.business.enums;

public enum SmsPlatformsCodeEnum {

	EMay("EMay","亿美"),
	CL253("CL253","创蓝253"),
	DREAM56("DREAM56","56梦网");
	
	private SmsPlatformsCodeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	private String code;

	private String description;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
