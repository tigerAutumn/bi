package com.pinting.gateway.pay19.out.enums;

public enum NewCounterUrl {
	
	/** 新网银支付请求接口 */
	EBANK("ebank.do", "新网银支付请求接口"),
	/** 新网银支付订单查询接口 */
	ORDER_QUERY("orderquery.do", "网银支付订单查询接口"),
	/** 新网银支付退款接口 */
	REFUND("refund.do", "网银支付退款接口");
	
	private String code;
	
	private String description;

	private NewCounterUrl(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
    public static NewCounterUrl find(String code) {
        for (NewCounterUrl key : NewCounterUrl.values()) {
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
