package com.pinting.business.enums;

/**
 * 人脸核身业务类型
 * @project business
 * @author zousheng
 * @2018-5-29 下午4:58:15
 */
public enum PoliceVerifyBusinessTypeEnum {

    UNBIND_BANK_CARD("unbind_bank_card","解绑卡"),
    ;

	private String code;

	private String description;

	PoliceVerifyBusinessTypeEnum(String code, String description){
        this.setCode(code);
        this.setDescription(description);
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
