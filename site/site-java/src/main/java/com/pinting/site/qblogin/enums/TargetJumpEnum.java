package com.pinting.site.qblogin.enums;

/**
 * 跳转目标页面
 * @author babyshark
 */
public enum TargetJumpEnum {
    PRODUCT_DETAIL("product_detail", "产品详情页"),
    FUND_IN("fund_in", "充值页"),
    FUND_OUT("fund_out", "提现页"),
    COUPON_VIEW("coupon_view", "优惠券页"),;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private TargetJumpEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link TargetJumpEnum} 实例
     */
    public static TargetJumpEnum find(String code) {
        for (TargetJumpEnum transCode : TargetJumpEnum.values()) {
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
