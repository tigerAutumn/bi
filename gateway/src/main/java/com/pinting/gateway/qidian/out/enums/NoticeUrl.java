package com.pinting.gateway.qidian.out.enums;

/**
 * 
 * @project gateway
 * @title NoticeUrl.java
 * @author Dragon & cat
 * @date 2018-3-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public enum NoticeUrl {

    /** 获取七店token*/
    TOKEN_NOTICE("login","/bgw/token", "获取七店token"),
    /** 订单信息同步*/
    ORDER_INFO_SYNC_NOTICE("orderInfoSync","/order/add", "推送订单信息"),
    /** 客户信息同步*/
    CUSTOMER_INFO_SYNC_NOTICE("customerInfoSync","/customer/add", "推送客户信息"),
    ;
    
    /** transCode */
    private String transCode;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    NoticeUrl(String transCode,String code, String description) {
    	 this.transCode = transCode;
        this.code = code;
        this.description = description;
    }
    
    

    public String getTransCode() {
		return transCode;
	}



	public void setTransCode(String transCode) {
		this.transCode = transCode;
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
    
	public static NoticeUrl getEnumByTransCode(String transCode){
		if (null == transCode) {
			return null;
		}
		for (NoticeUrl noticeUrl : values()) {
			if (noticeUrl.getTransCode().equals(transCode.trim()))
				return noticeUrl;
		}
		return null;
	}
    
}
