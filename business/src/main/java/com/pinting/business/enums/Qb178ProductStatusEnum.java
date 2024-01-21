package com.pinting.business.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Qb178ProductStatusEnum.java, v 0.1 2017-8-1 下午7:21:59 BabyShark Exp $
 */
public enum Qb178ProductStatusEnum {
    BUYING("buying", "6", "进行中/申购中"),
    CONFIRMED("confirmed", "7", "已完成/已确权"),
    PREPARED("prepared", "5", "已发布未开始/拟定"),
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
    private Qb178ProductStatusEnum(String code, String mappingCode, String description) {
        this.code = code;
        this.mappingCode = mappingCode;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link Qb178ProductStatusEnum} 实例
     */
    public static Qb178ProductStatusEnum find(String code) {
        for (Qb178ProductStatusEnum userState : Qb178ProductStatusEnum.values()) {
            if (userState.getCode().equals(code)) {
                return userState;
            }
        }
        return null;
    }
    
    public static Qb178ProductStatusEnum findStatus(String mappingCode) {
        for (Qb178ProductStatusEnum userState : Qb178ProductStatusEnum.values()) {
            if (userState.getMappingCode().equals(mappingCode)) {
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
