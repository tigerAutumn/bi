package com.pinting.business.enums;

/**
 * 针对个人（现金01、在途02）
 * @author SHENGP
 * @date  2017年6月12日 上午11:06:19
 */
public enum HFBankFundType {

	DEP_CASH("01", "现金"),
    DEP_ON_PASSAGE("02", "在途"),
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
    private HFBankFundType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link HFBankFundType} 实例
     */
    public static HFBankFundType find(String code) {
        for (HFBankFundType transCode : HFBankFundType.values()) {
            if (transCode.getCode().equals(code)) {
                return transCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    
}
