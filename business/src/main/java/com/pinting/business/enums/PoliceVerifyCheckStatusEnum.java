package com.pinting.business.enums;

/**
 * 人脸核身审核状态：UNCHECKED 待审核            PASS 已通过            REFUSE 未通过
 * @project business
 * @author bianyatian
 * @2018-5-24 下午4:58:15
 */
public enum PoliceVerifyCheckStatusEnum {

	UNCHECKED("UNCHECKED","待审核"),
	PASS("PASS","已通过"),
	REFUSE("REFUSE","未通过"),
    ;
	
	private String code;

	private String description;
	
	PoliceVerifyCheckStatusEnum(String code, String description){
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
