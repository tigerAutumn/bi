package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品信息状态
 *
 * @author zousheng
 * @date 2018年5月15日 下午2:13:19
 */
public enum MallInfoStatusEnum {

    FOR_SALE("FOR_SALE", "上架"),
    SOLD_OUT("SOLD_OUT", "下架"),
    DELETED("DELETED", "删除"),
    ;

    private MallInfoStatusEnum(String code, String message) {
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
    public static MallInfoStatusEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (MallInfoStatusEnum type : values()) {
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
        for (MallInfoStatusEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getMessage());
        }
        return enumDataMap;
    }

    /**
     * 转出Map，排除删除状态
     *
     * @return
     */
    public static Map<String, String> toMapWithoutDeleted() {
        Map<String, String> enumDataMap = new HashMap<>();
        for (MallInfoStatusEnum type : values()) {
        	if (!MallInfoStatusEnum.DELETED.getCode().equals(type.getCode())) {        		
        		enumDataMap.put(type.getCode(), type.getMessage());
        	}
        }
        return enumDataMap;
    }
    
}
