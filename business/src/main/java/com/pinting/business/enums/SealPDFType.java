package com.pinting.business.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum SealPDFType {
    /** 空白标签签章*/
    BLANK_SEAL("1", "空白标签签章"),
    /** 坐标签章*/
    COORD_SEAL("2", "坐标签章"),
    /** 关键字签章*/
    KEYWORD_SEAL("3", "关键字签章"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private SealPDFType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link SealPDFType} 实例
     */
    public static SealPDFType find(String code) {
        for (SealPDFType transCode : SealPDFType.values()) {
            if (transCode.getCode().equals(code)) {
                return transCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
