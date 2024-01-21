package com.pinting.gateway.pay19.out.enums;


public enum HoldingOrderRespTradeStatus {
    /** 请求成功 */
    SUCCESS("SUCCESS", "成功"),
    /** 请求失败 */
    FAIL("FAIL", "失败"),
    /** 异常 */
    EXCEPTION("EXCEPTION", "异常"),
	/** 处理中 */
    PROCESS("PROCESS", "处理中");

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private HoldingOrderRespTradeStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static HoldingOrderRespTradeStatus find(String code) {
        for (HoldingOrderRespTradeStatus key : HoldingOrderRespTradeStatus.values()) {
            if (key.getCode().equals(code)) {
                return key;
            }
        }
        return null;//throw new GatewayBaseException(GatewayBaseCode.SYSTEM_ERROR, "根据code=" + code + "获取组织类型失败");
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
