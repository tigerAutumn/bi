package com.pinting.business.enums;

public enum TransTypeEnum {
	USER_TOP_UP_QUICK_PAY("USER_TOP_UP_QUICK_PAY","理财用户充值（快捷）"),
	USER_TOP_UP_E_BANK("USER_TOP_UP_E_BANK","理财用户充值（网银）"),
	USER_WITHDRAW("USER_WITHDRAW","理财用户提现"),
	LOAN_USER_LOAN("LOAN_USER_LOAN","借款用户借款"),
	LOAN_USER_REPAY_QUICK_PAY("LOAN_USER_REPAY_QUICK_PAY","借款用户还款（快捷）"),
	LOAN_USER_REPAY_E_BANK("LOAN_USER_REPAY_E_BANK","借款用户还款（网银）"),
	PARTNER_MARKET_FEE("PARTNER_MARKET_FEE","合作方营销费用提现手续费"),
	ZAN_REPAY_DK("ZAN_REPAY_DK","赞分期代扣"),

	
	YUN_DAI_SELF_LOAN("YUN_DAI_SELF_LOAN","云贷自主_用户借款"),
	YUN_DAI_SELF_REPAY_QUICK_PAY("YUN_DAI_USER_REPAY_QUICK_PAY","云贷自主_借款用户还款（快捷）"),
	YUN_DAI_SELF_REPAY_E_BANK("YUN_DAI_USER_REPAY_E_BANK","云贷自主_借款用户还款（网银）"),
	YUN_DAI_SELF_DK("YUN_DAI_SELF_DK","云贷自主_借款用户（代扣）"),

	ZSD_LOAN("ZSD_LOAN", "赞时贷用户借款"),
	ZSD_REPAY_DK("ZSD_REPAY_DK","赞时贷_借款用户(代扣)"),

	SEVEN_DAI_SELF_DK("SEVEN_DAI_SELF_DK","7贷自主_借款用户（代扣）"),
	SEVEN_DAI_SELF_LOAN("SEVEN_DAI_SELF_LOAN","7贷自主_用户借款"),
	SEVEN_DAI_SELF_REPAY_QUICK_PAY("SEVEN_DAI_USER_REPAY_QUICK_PAY","7贷自主_借款用户还款（快捷）"),
	SEVEN_DAI_SELF_REPAY_E_BANK("SEVEN_DAI_USER_REPAY_E_BANK","7贷自主_借款用户还款（网银）"),
	;
	
	private String code;

    private String description;
	

    TransTypeEnum(String code, String description){
    	 this.setCode(code);
         this.setDescription(description);
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
