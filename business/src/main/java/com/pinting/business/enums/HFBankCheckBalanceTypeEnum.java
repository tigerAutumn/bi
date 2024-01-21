package com.pinting.business.enums;

public enum HFBankCheckBalanceTypeEnum {
	
	HFBANK_FINANCIAL_WITHDRAW("HFBANK_FINANCIAL_WITHDRAW", "出金(投资人提现)"),
	HFBANK_FINANCIAL_BALANCE_WITHDRAW("HFBANK_FINANCIAL_BALANCE_WITHDRAW", "出金(投资人余额提现)"),
	HFBANK_FINANCIAL_BONUS_WITHDRAW("HFBANK_FINANCIAL_BONUS_WITHDRAW", "出金(投资人奖励金提现)"),
	HFBANK_PLATFORM_WITHDRAW("HFBANK_PLATFORM_WITHDRAW", "出金(平台提现)"),
	HFBANK_YUN_LOAN("HFBANK_YUN_LOAN", "出金(云贷放款)"),
	HFBANK_SEVEN_LOAN("HFBANK_SEVEN_LOAN", "出金(7贷放款)"),
	
	HFBANK_FINANCIAL_TOP_UP("HFBANK_FINANCIAL_TOP_UP", "入金(投资人充值)"),
	HFBANK_PLATFORM_TOP_UP("HFBANK_PLATFORM_TOP_UP", "入金(平台充值)"),
	HFBANK_REPAY_CARD_2_DEPOSITOR("HFBANK_REPAY_CARD_2_DEPOSITOR", "入金(归集户到存管户)"),
	;
	
	private String code;

    private String description;
    
	public static HFBankCheckBalanceTypeEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (HFBankCheckBalanceTypeEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	HFBankCheckBalanceTypeEnum(String code, String description){
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
