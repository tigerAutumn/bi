package com.pinting.business.coreflow.compensate.enums;

/**
 * Created by zousheng on 2018/8/02.
 */
public enum CompensateStatusEnum {

    //通知状态
    NOTICE_STATUS_INIT("INIT","未通知"),
    NOTICE_STATUS_SUCCESS("SUCCESS","通知成功"),
    NOTICE_STATUS_FAIL("FAIL","通知失败"),

    ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    CompensateStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
