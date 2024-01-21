package com.pinting.business.util.WelinkUtil;
/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: PostUrlEnums.java, v 0.1 2015-7-22 下午4:10:39 BabyShark Exp $
 */
public enum PostUrlEnums {
    /** 普通短信提交接口 */
    SMS_G_SUBMIT("g_Submit", "普通短信提交接口"),
    /** 普通短信提交接口 */
    SMS_G_SUBMITWITHKEY("g_SubmitWithKey", "自定义短信提交接口"),
    /** 普通短信提交接口 */
    SMS_G_SCHEDULERSUBMIT("g_SchedulerSubmit", "定时短信提交接口"),
    /** 普通短信提交接口 */
    SMS_G_SCHEDULERSUBMITWITHKEY("g_SchedulerSubmitWithKey", "自定义定时短信提交接口"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private PostUrlEnums(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static PostUrlEnums find(String code) throws Exception {
        for (PostUrlEnums key : PostUrlEnums.values()) {
            if (key.getCode().equals(code)) {
                return key;
            }
        }
        throw new Exception(GatewayBaseCodeEnum.SYSTEM_ERROR+"根据code=" + code + "获取组织类型失败");
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
