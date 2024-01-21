package com.pinting.business.accounting.loan.enums;

/**
 * Created by BabyShark on 2017/4/22.
 */
public enum DepRepayFlagEnum {

    //标的还款：本期已还清 0-是,1-否 
    DEP_REPAY_FLAG_YES("0","本期已还清"),
    DEP_REPAY_FLAG_NO("1","本期未还清"),
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
    DepRepayFlagEnum(String code, String description) {
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
