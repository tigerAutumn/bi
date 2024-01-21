package com.dafy.core.enums;

public enum FinancingDetailStatus {
	RECEIVED("RECEIVED","已收到"),
	CONFIRMED("CONFIRMED","已回复确认"),
	CONFIRM_FAIL("CONFIRM_FAIL","回复确认失败"),
	RETURNED("RETURNED","已回款"),
	RETURN_FAIL("RETURN_FAIL","回款失败");
	
	//编码
	private String code;
	//描述
	private String description;
	
	private FinancingDetailStatus(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	//根据编码查询该对象
	public static FinancingDetailStatus find(String code) {
		for(FinancingDetailStatus fs : FinancingDetailStatus.values()) {
			if(fs.getCode().equals(code)) {
				return fs;
			}
		}
		return null;
	}

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
