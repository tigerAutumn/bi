package com.pinting.business.accounting.loan.enums;

/**
 * Created by BabyShark on 2017/4/8.
 */
public enum DepIsPayOffEnum {

    //标的还款：是否整个标的还清：0-是，1-否
    DEP_IS_PAY_OFF_YES("0","整个标的还清"),
    DEP_IS_PAY_OFF_NO("1","未还清"),
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
    DepIsPayOffEnum(String code, String description) {
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
