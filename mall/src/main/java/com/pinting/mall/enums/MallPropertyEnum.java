package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品信息状态
 *
 * @author zousheng
 * @date 2018年5月15日 下午2:13:19
 */
public enum MallPropertyEnum {

    EMPTY("EMPTY", "虚拟商品"),
    REAL("REAL", "实体商品"),;

    private MallPropertyEnum(String code, String message) {
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
    public static MallPropertyEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (MallPropertyEnum type : values()) {
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
        Map<String, String> enumDataMap = new HashMap<>();
        for (MallPropertyEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getMessage());
        }
        return enumDataMap;
    }

}
