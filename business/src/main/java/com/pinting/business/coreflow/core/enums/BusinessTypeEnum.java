package com.pinting.business.coreflow.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品类型标识
 */
public enum BusinessTypeEnum {
    AVERAGE_CAPITAL_PLUS_INTEREST("FIXED_INSTALLMENT", "等额本息"),
    EQUAL_PRINCIPAL_INTEREST("FIXED_PRINCIPAL_INTEREST", "等本等息"),
    REPAY_ANY_TIME("REPAY_ANY_TIME","随借随还"),
    ;

    private BusinessTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param code
     * @return
     */
    public static BusinessTypeEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (BusinessTypeEnum type : values()) {
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
        for (BusinessTypeEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }

}
