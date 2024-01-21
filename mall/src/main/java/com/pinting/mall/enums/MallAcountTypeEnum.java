package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分商城账户流水Type枚举
 */
public enum MallAcountTypeEnum {
    OPEN_ACCOUNT("OPEN_ACCOUNT", "积分账户开户"),
    MALL_REGISTER(MallRuleEnum.MALL_REGISTER.getCode(), MallRuleEnum.MALL_REGISTER.getMessage()),
    MALL_OPEN_DEPOSIT(MallRuleEnum.MALL_OPEN_DEPOSIT.getCode(), MallRuleEnum.MALL_OPEN_DEPOSIT.getMessage()),
    MALL_FINISH_RISK_ASSESSMENT(MallRuleEnum.MALL_FINISH_RISK_ASSESSMENT.getCode(), MallRuleEnum.MALL_FINISH_RISK_ASSESSMENT.getMessage()),
    MALL_FIRST_INVEST(MallRuleEnum.MALL_FIRST_INVEST.getCode(), MallRuleEnum.MALL_FIRST_INVEST.getMessage()),
    MALL_INVEST(MallRuleEnum.MALL_INVEST.getCode(), MallRuleEnum.MALL_INVEST.getMessage()),
    MALL_TOTAL_INVEST(MallRuleEnum.MALL_TOTAL_INVEST.getCode(), MallRuleEnum.MALL_TOTAL_INVEST.getMessage()),
    MALL_SIGN(MallRuleEnum.MALL_SIGN.getCode(), MallRuleEnum.MALL_SIGN.getMessage()),
    MALL_EXCHANGE(MallRuleEnum.MALL_EXCHANGE.getCode(), MallRuleEnum.MALL_EXCHANGE.getMessage()),
    MALL_BIRTHDAY(MallRuleEnum.MALL_BIRTHDAY.getCode(),MallRuleEnum.MALL_BIRTHDAY.getMessage()),
    
    ;
    private MallAcountTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param code
     * @return
     */
    public static MallAcountTypeEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (MallAcountTypeEnum type : values()) {
            if (type.getCode().equals(code.trim()))
                return type;
        }
        return null;
    }

    /**
     * 转出Map
     *
     * @return
     */
    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (MallAcountTypeEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getMessage());
        }
        return enumDataMap;
    }


}
