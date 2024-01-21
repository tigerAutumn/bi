package com.pinting.gateway.baofoo.out.enums;

public enum BaofooAgreementPayTxnType {
    /*协议支付预绑卡类交易*/
	READY_SIGN("01", "协议支付预绑卡类交易"),
    /*协议支付确认绑卡类交易*/
	CONFIRM_SIGN("02", "协议支付确认绑卡类交易"),
    /*查询绑定关系类交易*/
	QUERY_BIND("03", "查询绑定关系类交易"),
    /*协议支付解除绑卡类交易*/
	ABOLISH_BIND("04", "协议支付解除绑卡类交易"),
    /*协议支付预支付类交易*/
	READY_PAY("05", "协议支付预支付类交易"),
    /*协议支付确认支付类交易*/
	CONFIRM_PAY("06", "协议支付确认支付类交易"),
    /*协议支付订单查询类交易*/
	QUERY_ORDER("07", "协议支付订单查询类交易"),
    /*协议支付直接支付类*/
	SINGLE_PAY("08", "协议支付直接支付类"),
	;
	
    /** code */
    private String code;

    /** description */
    private String description;
    
    
    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
	private BaofooAgreementPayTxnType(String code, String description) {
		this.code = code;
		this.description = description;
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
