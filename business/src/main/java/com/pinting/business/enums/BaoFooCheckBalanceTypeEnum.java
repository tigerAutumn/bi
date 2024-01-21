package com.pinting.business.enums;

/**
 *
 * @author SHENGUOPING
 * @date  2018年4月19日 下午2:23:42
 */
public enum BaoFooCheckBalanceTypeEnum {

	WITHHOLDING_REPAY("WITHHOLDING_REPAY", "代扣(还款)"),
	
	WALLET_TRANSFER_OFFLINE_REPAY("WALLET_TRANSFER_OFFLINE_REPAY", "钱包转账(线下还款)"),
	WALLET_TRANSFER_LN_COMPENSATE("WALLET_TRANSFER_LN_COMPENSATE", "钱包转账(代偿)"),
	WALLET_TRANSFER_ASSIST_2_MAIN("WALLET_TRANSFER_ASSIST_2_MAIN", "钱包转账(辅转主)"),
	WALLET_TRANSFER_PARTNER_SETTLE("WALLET_TRANSFER_PARTNER_SETTLE", "钱包转账(结算合作方)"),
	WALLET_TRANSFER_PARTNER_REVENUE("WALLET_TRANSFER_PARTNER_REVENUE", "钱包转账(合作方营收)"),
	WALLET_TRANSFER_PARTNER_REPEAT_REVENUE("WALLET_TRANSFER_PARTNER_REPEAT_REVENUE", "钱包转账(合作方重复还款)"),
	WALLET_TRANSFER_SYS_RECEIVE_MONEY("WALLET_TRANSFER_SYS_RECEIVE_MONEY", "钱包转账(老产品回款)"),
	
	PAID_BALANCE_WITHDRAW("PAID_BALANCE_WITHDRAW", "代付(余额提现)"),
	PAID_BONUS_WITHDRAW("PAID_BONUS_WITHDRAW", "代付(奖励金提现)"),
	PAID_2_DEP_REPAY_CARD("PAID_2_DEP_REPAY_CARD", "代付(归集户)"),
	;
	
	private String code;

    private String description;
    
	public static BaoFooCheckBalanceTypeEnum getEnumByCode(String code){
		if (null == code) {
			return null;
		}
		for (BaoFooCheckBalanceTypeEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	BaoFooCheckBalanceTypeEnum(String code, String description){
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
