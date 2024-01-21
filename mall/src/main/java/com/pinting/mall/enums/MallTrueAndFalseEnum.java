package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * true与false枚举
 *
 * @author zousheng
 * @date 2018年5月16日 下午2:13:19
 */
public enum MallTrueAndFalseEnum {

    TRUE(1, "是"),
    FALSE(0, "否"),;

    private MallTrueAndFalseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
    public static MallTrueAndFalseEnum getEnumByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (MallTrueAndFalseEnum type : values()) {
            if (type.getCode().equals(code))
                return type;
        }
        return null;
    }

    /**
     * 转出Map
     *
     * @return
     */
    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new HashMap<>();
        for (MallTrueAndFalseEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getMessage());
        }
        return enumDataMap;
    }

}
