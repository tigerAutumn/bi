package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bianyatian
 * @2018-4-3 下午5:59:29
 */
public enum SealPdfPathEnum {
    ZAN("ZAN", "zan", null),//赞分期
    YUN_DAI_SELF("YUN_DAI_SELF", "yundai", null),//云贷
    ZSD("ZSD", "zsd", null),//赞时贷
    SEVEN_DAI_SELF("7_DAI_SELF", "qidai", null),//七贷
    BIGANGWAN("BIGANGWAN", "bigangwan", 131754),//币港湾
    ;

    private SealPdfPathEnum(String code, String name, Integer sealId) {
        this.code = code;
        this.name = name;
        this.sealId = sealId;
    }

    private String code; // 签章合作公司标识
    private String name; // 签章合作公司名称
    private Integer sealId; // 初始化签章表bs_user_sign_seal.id

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

    public Integer getSealId() {
        return sealId;
    }

    public void setSealId(Integer sealId) {
        this.sealId = sealId;
    }

    /**
     * @param code
     * @return
     */
    public static SealPdfPathEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (SealPdfPathEnum type : values()) {
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
        for (SealPdfPathEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }
}
