package com.pinting.gateway.pay19.out.enums;

public enum HoldingOrderUrl {

	/** 代扣下单接口 */
	HOILDINGORDER("withholdingOrder.do", "代扣下单接口"),
	/** 代扣查询接口 */
	HOILDINGORDERQUERY("queryWithHoldingNew.do", "代扣查询接口");
	
	private String code;
	
	private String description;

	private HoldingOrderUrl(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
    public static HoldingOrderUrl find(String code) {
        for (HoldingOrderUrl key : HoldingOrderUrl.values()) {
            if (key.getCode().equals(code)) {
                return key;
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
