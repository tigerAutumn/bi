package com.pinting.business.enums;

/**
 *
 * @author Baby shark love blowing wind
 */
public enum HFBankAccountType {
    DEP_JSH("1", "自有子账户"),
    DEP_FEE("3", "手续费子账户"),
    DEP_EXP("4", "体验金子账户"),
    DEP_RED("5", "抵用金子账户"),
    DEP_INCR("6", "加息子账户"),
    DEP_MARGIN("7", "保证金子账户"),
    DEP_REWARD("9", "奖励金子账户"),
    DEP_ADVANCE("10", "垫付金现金子账户"),
    DEP_ADVANCE_TRANSIT("11", "垫付金在途子账户"),
    DEP_FEE_TRANSIT("34", "手续费在途子账户"),
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
    private HFBankAccountType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link HFBankAccountType} 实例
     */
    public static HFBankAccountType find(String code) {
        for (HFBankAccountType transCode : HFBankAccountType.values()) {
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
