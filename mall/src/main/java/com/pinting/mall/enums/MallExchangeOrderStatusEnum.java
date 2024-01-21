package com.pinting.mall.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 兑换订单状态
 *
 * @author zousheng
 * @date 2018年5月16日 下午2:13:19
 */
public enum MallExchangeOrderStatusEnum {

    PROCESS("PROCESS", "支付处理中"),
    SUCCESS("SUCCESS", "支付成功"),
    FAIL("FAIL", "支付失败"),
    FINISHED("FINISHED", "已完成"),;

    private MallExchangeOrderStatusEnum(String code, String message) {
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
    public static MallExchangeOrderStatusEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (MallExchangeOrderStatusEnum type : values()) {
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
        for (MallExchangeOrderStatusEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getMessage());
        }
        return enumDataMap;
    }

}
