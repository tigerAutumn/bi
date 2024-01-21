package com.pinting.rabbit.queue.core.enums;

/**
 * 业务类型定义--队列使用
 */
public enum RabbitBusinessEnum {
    WECHAT_MSG_SEND("wechat_msg_send", "微信消息发送"),
    ACTIVITY_COLLECT("activity_collect", "活动数据采集");

    private RabbitBusinessEnum(String code, String name) {
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

    public static RabbitBusinessEnum getRabbitEventEnum(String code) {
        for (RabbitBusinessEnum rabbitEventEnum : values()) {
            if (rabbitEventEnum.getCode().equals(code)) {
                return rabbitEventEnum;
            }
        }

        return null;
    }
}
