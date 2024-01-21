package com.pinting.business.enums;

/**
 * 币港湾-恒丰账户类型，在BGW对账基础上新增
 * @author SHENGP
 * @date  2017年6月14日 上午11:51:15
 */
public enum BGWHFAccountType {
	
	ACC_TYPE_BGW_USER("BGW_USER", "用户余额"),
	ACC_TYPE_BGW_REG_YUN("BGW_REG_YUN", "云贷产品户余额"),
	ACC_TYPE_BGW_AUTH_YUN("BGW_AUTH_YUN", "云贷站岗户余额"),
	ACC_TYPE_BGW_RETURN_YUN("BGW_RETURN_YUN", "云贷回款户余额"),
	ACC_TYPE_BGW_REG_ZAN("BGW_REG_ZAN", "赞分期产品户余额"),
	ACC_TYPE_BGW_AUTH_ZAN("BGW_AUTH_ZAN", "赞分期站岗户余额"),
	ACC_TYPE_BGW_RETURN_ZAN("BGW_RETURN_ZAN", "赞分期回款户余额"),
	ACC_TYPE_BGW_REG_ZSD("BGW_REG_ZSD", "赞时贷产品户余额"),
	ACC_TYPE_BGW_AUTH_ZSD("BGW_AUTH_ZSD", "赞时贷站岗户余额"),
	ACC_TYPE_BGW_RETURN_ZSD("BGW_RETURN_ZSD", "赞时贷回款户余额"),
	ACC_TYPE_BGW_REG_7("BGW_REG_7", "7贷产品户余额"),
	ACC_TYPE_BGW_AUTH_7("BGW_AUTH_7", "7贷站岗户余额"),
	ACC_TYPE_BGW_RETURN_7("BGW_RETURN_7", "7贷回款户余额"),
	;

	
	/** code */
    private String code;

    /** description */
    private String description;
    
    private BGWHFAccountType(String code, String description) {
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
