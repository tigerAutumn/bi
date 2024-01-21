package com.pinting.business.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Qb178TransCodeEnum.java, v 0.1 2017-8-1 下午7:21:59 BabyShark Exp $
 */
public enum Qb178TransCodeEnum {
    TOP_UP("TOP_UP", "101", "充值"),
    WITHDRAW("WITHDRAW", "102", "提现"),
    DEP_WITHDRAW("DEP_WITHDRAW", "102", "提现"),
    WITHDRAW_FEE("WITHDRAW_FEE", "02", "提现手续费"),
    RETURN("RETURN", "01", "回款到余额"),
    AUTH_ACCOUNT_TO_BALANCE("AUTH_ACCOUNT_TO_BALANCE", "01", "站岗户转余额"),
    BONUS_2_BALANCE("BONUS_2_BALANCE", "01", "奖励金转余额"),
    USER_BONUS_WITHDRAW("USER_BONUS_WITHDRAW", "01", "奖励金提现"),
    BUY("BUY", "02", "购买"),
    ZAN_RETURN("ZAN_RETURN", "01", "赞分期回款到余额"),

    ;

    /** code */
    private String code;

    private String mappingCode;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private Qb178TransCodeEnum(String code, String mappingCode, String description) {
        this.code = code;
        this.mappingCode = mappingCode;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link Qb178TransCodeEnum} 实例
     */
    public static Qb178TransCodeEnum find(String code) {
        for (Qb178TransCodeEnum userState : Qb178TransCodeEnum.values()) {
            if (userState.getCode().equals(code)) {
                return userState;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMappingCode() {
        return mappingCode;
    }

    public String getDescription() {
        return description;
    }
}
