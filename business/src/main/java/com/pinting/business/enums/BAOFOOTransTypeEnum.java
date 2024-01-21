package com.pinting.business.enums;

/**
 * 宝付对账业务类型
 * @author SHENGUOPING
 * @date  2018年1月2日 上午6:35:29
 */
public enum BAOFOOTransTypeEnum {

	BALANCE_WITHDRAW("BALANCE_WITHDRAW", "用户余额提现"),
	BONUS_WITHDRAW("BONUS_WITHDRAW", "用户奖励金提现"),
	SYS_PARTNER_REVENUE("SYS_PARTNER_REVENUE", "赞分期营收"),
	SYS_YUN_REPAY_REVENUE("SYS_YUN_REPAY_REVENUE", "云贷营收"),
	SYS_SEVEN_REPAY_REVENUE("SYS_SEVEN_REPAY_REVENUE", "7贷营收"),
	SYS_ZSD_REPAY_REVENUE("SYS_ZSD_REPAY_REVENUE", "赞时贷营收"),
	SYS_YUN_REPEAT("SYS_YUN_REPEAT", "云贷重复还款"),
	SYS_ZSD_REPEAT("SYS_ZSD_REPEAT", "赞时贷重复还款"),
	SYS_SEVEN_REPEAT("SYS_SEVEN_REPEAT", "7贷重复还款"),
	
	WITHDRAW_2_DEP_REPAY_CARD("WITHDRAW_2_DEP_REPAY_CARD", "宝付代付到归集户"),
	CUT_REPAY_2_BORROW("CUT_REPAY_2_BORROW", "归集户代扣到存管户"),
	DEPREPAY_MAIN_WITHHOLD_REPAY("DEPREPAY_MAIN_WITHHOLD_REPAY", "存管产品还款-主商户代扣还款"),
	DEPREPAY_ASSIST_WITHHOLD_REPAY("DEPREPAY_ASSIST_WITHHOLD_REPAY", "存管产品还款-辅商户代扣还款"),
	
	TRANSFER_2_MAIN("REPAY_TRANSFER_2_MAIN", "辅助通道转账到主通道"),
	SYS_RECEIVE_MONEY("SYS_RECEIVE_MONEY", "老产品回款"),
	LN_COMPENSATE("LN_COMPENSATE", "代偿"),
	PARTNER_OFFLINE_REPAY("PARTNER_OFFLINE_REPAY", "合作方线下还款"),
	
	HFBANK_BALANCE_WITHDRAW("BALANCE_WITHDRAW", "投资人提现"),
	HFBANK_SYS_WITHDRAW("HFBANK_SYS_WITHDRAW", "平台提现"),
	HFBANK_YUN_LOAN("LOAN", "云贷放款"),
	HFBANK_SEVEN_LOAN("LOAN", "7贷放款"),
	HFBANK_TOP_UP("TOP_UP", "投资人充值"),
	HFBANK_SYS_TOP_UP("HFBANK_SYS_TOP_UP", "平台充值"),
	;
	
	private String code;

    private String description;
    
	public static BAOFOOTransTypeEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (BAOFOOTransTypeEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
    BAOFOOTransTypeEnum(String code, String description){
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
