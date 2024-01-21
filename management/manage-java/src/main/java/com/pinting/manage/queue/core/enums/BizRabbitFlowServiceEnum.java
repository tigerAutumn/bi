package com.pinting.manage.queue.core.enums;

import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.enums.RabbitBusinessEnum;
import com.pinting.rabbit.queue.core.enums.RabbitEventEnum;

/**
 * 业务流程service枚举(队列与消费者映射)
 */
public enum BizRabbitFlowServiceEnum {
    BIZ_WECHAT_MSG_TEMPLATE_SCH_BONUS_GRANT_step1("step1", RabbitBindingEnum.BIZ_WECHAT_MSG_TEMPLATE.getQueues()
            + RabbitEventEnum.SCH_BONUS_GRANT.getCode() + RabbitBusinessEnum.WECHAT_MSG_SEND.getCode(),
            "sendWeChatServiceBonusImpl", "奖励金发放微信消息队列发送--自动确认"),


    MALL_POINTS_REDUCE_EXCHANGE_step1("step1", RabbitBindingEnum.BIZ_ACTIVITY_COLLECT.getQueues()
            + RabbitEventEnum.BIZ_BALANCE_BUY.getCode() + RabbitBusinessEnum.ACTIVITY_COLLECT.getCode(),
            "activityCollectServiceImpl", "余额授权活动数据采集--自动确认"),
    ;


    private BizRabbitFlowServiceEnum(String step, String serviceCode, String serviceName, String desc) {
        this.step = step; // 步骤
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    private String step;
    private String serviceCode;
    private String serviceName;
    private String desc;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @param serviceCode
     * @return
     */
    public static BizRabbitFlowServiceEnum getEnumByServiceCode(String serviceCode) {
        if (null == serviceCode) {
            return null;
        }
        for (BizRabbitFlowServiceEnum type : values()) {
            if (type.getServiceCode().equals(serviceCode.trim()))
                return type;
        }
        return null;
    }

    /**
     * @param serviceCode
     * @return
     */
    public static String getEnumByServiceName(String serviceCode) {
        if (null == serviceCode) {
            return null;
        }
        for (BizRabbitFlowServiceEnum type : values()) {
            if (type.getServiceCode().equals(serviceCode.trim()))
                return type.getServiceName();
        }
        return null;
    }
}
