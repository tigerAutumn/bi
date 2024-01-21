package com.pinting.site.regular.enums;

/**
 * 根据用户类型 区分 产品
 * @Project: site-java
 * @Title: UserTypePrdctCode.java
 * @author dingpf
 * @date 2015-10-9 下午1:47:54
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public enum UserTypePrdctCode {
    /** 普通用户产品 */
    NORMAL_PRODUCT_CODE("11,12,13,14", "普通用户产品 "),
    /** 渠道推广用户产品 */
    PROMOT_PRODUCT_CODE("15,16,17,14", "渠道推广用户产品"),
    ;

    private String code;

    private String desciption;

    private UserTypePrdctCode(String code, String desciption) {
        this.code = code;
        this.desciption = desciption;
    }

    public static String getDesciption(String code) {
        UserTypePrdctCode[] statusEnums = UserTypePrdctCode.values();
        for (UserTypePrdctCode statusEnum : statusEnums) {
            if (statusEnum.getCode().equals(code))
                return statusEnum.getDesciption();
        }
        return null;
    }

    /**
     * 
     * @param code
     * @return {@link MessageState} 实例
     */
    public static UserTypePrdctCode find(String code) {
        for (UserTypePrdctCode loanStatus : UserTypePrdctCode.values()) {
            if (loanStatus.getCode().equals(code)) {
                return loanStatus;
            }
        }
        return null;
    }
    
    public static boolean isPromotProductCode(String code){
    	String[] codes = PROMOT_PRODUCT_CODE.getCode().split(",");
    	for (String str : codes) {
    		if(str.equals(code)){
    			return true;
    		}
		}
    	return false;
    }
    
    public static boolean isNormalProductCode(String code){
    	String[] codes = NORMAL_PRODUCT_CODE.getCode().split(",");
    	for (String str : codes) {
    		if(str.equals(code)){
    			return true;
    		}
		}
    	return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

}
