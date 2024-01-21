package com.pinting.business.enums;

/**
 * 币港湾-宝付账户类型
 * @author SHENGP
 * @date  2017年6月14日 上午11:48:47
 */
public enum BAOFOOAccountType {

	ACC_TYPE_JSH("JSH", "系统账户余额"),
	ACC_TYPE_USER("USER", "用户余额"),
	ACC_TYPE_REG_YUN("REG_YUN", "云贷产品户余额"),
	ACC_TYPE_RETURN_YUN("RETURN_YUN", "云贷回款户余额"),
	ACC_TYPE_REG_7("REG_7", "七贷产品户余额"),
	ACC_TYPE_RETURN_7("RETURN_7", "七贷回款户余额"),
	ACC_TYPE_THD_BGW_REVENUE_YUN("THD_BGW_REVENUE_YUN", "币港湾营收(云贷存管)"),
	ACC_TYPE_THD_BGW_REVENUE_ZAN("THD_BGW_REVENUE_ZAN", "币港湾营收(赞分期)"),
	ACC_TYPE_THD_REVENUE_YUN("THD_REVENUE_YUN", "云贷营收账户余额"),
	ACC_TYPE_THD_REVENUE_ZAN("THD_REVENUE_ZAN", "赞分期营收账户余额"),
	ACC_TYPE_THD_MARGIN_ZAN("THD_MARGIN_ZAN", "赞分期保证金账户余额"),
	ACC_TYPE_FEE("FEE", "手续费余额"),
	ACC_TYPE_THD_REPAY("THD_REPAY", "还款资金户余额"),
	ACC_TYPE_THD_BGW_REVENUE_ZSD("THD_BGW_REVENUE_ZSD", "币港湾营收(赞时贷)"),
	ACC_TYPE_THD_REVENUE_ZSD("THD_REVENUE_ZSD", "赞时贷营收账户余额"),
	ACC_TYPE_THD_MARGIN_ZSD("THD_MARGIN_ZSD", "赞时贷保证金账户余额"),
	ACC_TYPE_THD_BGW_REVENUE_7("THD_BGW_REVENUE_7", "币港湾营收(7贷)"),
	ACC_TYPE_THD_REVENUE_7("THD_REVENUE_7", "7贷营收账户余额"),
	ACC_TYPE_THD_MARGIN_7("THD_MARGIN_7", "7贷保证金账户余额"),
	;
	
	/** code */
    private String code;

    /** description */
    private String description;
    
    private BAOFOOAccountType(String code, String description) {
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
