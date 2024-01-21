package com.pinting.business.accounting.loan.enums;

/**
 * Created by zhangbao on 2017/4/6.
 */
public enum DepTargetEnum {

    //存管标的操作类型
    DEP_TARGET_OPERATE_PUBLISH("PROD_PUBLISH","标的发布"),
    DEP_TARGET_OPERATE_BID("PROD_BID","投标"),
    DEP_TARGET_OPERATE_SET_UP("PROD_SET_UP","标的成立"),
    DEP_TARGET_OPERATE_CANCELLED("PROD_CANCELLED","标的废除"),
    DEP_TARGET_OPERATE_CHARGE_OFF("PROD_CHARGE_OFF","标的出账回调"),
    DEP_TARGET_OPERATE_CHARGE_OFF_PRE("CHARGE_OFF_PRE","标的出账申请"),
    DEP_TARGET_OPERATE_TRANSFER("PROD_TRANSFER","标的转让"),
    DEP_TARGET_OPERATE_LOAN_REPAY("PROD_LOAN_REPAY","借款人还款"),
    DEP_TARGET_OPERATE_PROD_REPAY("PROD_REPAY","标的还款"),
    DEP_TARGET_OPERATE_SET_UP_RESEND("PROD_SET_UP","标的成立重发"),
    DEP_TARGET_OPERATE_CANCELLED_RESEND("PROD_CANCELLED","标的废除重发"),
    DEP_TARGET_OPERATE_CHARGE_OFF_RESEND("CHARGE_OFF_PRE","标的出账重发"),

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
    DepTargetEnum(String code, String description) {
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
