package com.pinting.business.enums;

/**
 * 用户状态
 * @author SHENGUOPING
 * @date  2018年8月1日 下午4:02:10
 */
public enum BsUserStatus {

    USER_STATUS_NORMAL("1", 1, "正常"),
    USER_STATUS_CANCEL("2", 2, "注销"),
    USER_STATUS_DISABLE("3", 3, "禁用"),
    USER_STATUS_FREEZE("4", 4, "冻结"),
    ;

    /** code */
    private String code;

    private Integer intValue;
    
    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private BsUserStatus(String code, Integer intValue, String description) {
        this.code = code;
        this.intValue = intValue;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link HFBankAccountType} 实例
     */
    public static BsUserStatus find(String code) {
        for (BsUserStatus transCode : BsUserStatus.values()) {
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

	public Integer getIntValue() {
		return intValue;
	}
    
}
