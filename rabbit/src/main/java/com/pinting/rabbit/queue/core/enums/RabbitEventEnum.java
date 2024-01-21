package com.pinting.rabbit.queue.core.enums;

/**
 * 队列事件标识
 */
public enum RabbitEventEnum {
    SCH_BONUS_GRANT("shc_bonus_grant", "定时奖励金发放"),
    BIZ_BALANCE_BUY("biz_balance_buy", "业务余额授权"),;

    private RabbitEventEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RabbitEventEnum getRabbitEventEnum(String code) {
        for (RabbitEventEnum rabbitEventEnum : values()) {
            if (rabbitEventEnum.getCode().equals(code)) {
                return rabbitEventEnum;
            }
        }

        return null;
    }
}
