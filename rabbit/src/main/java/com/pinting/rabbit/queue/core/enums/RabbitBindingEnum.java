package com.pinting.rabbit.queue.core.enums;

import com.pinting.rabbit.queue.core.util.QueueConstant;

/**
 * 队列绑定枚举
 */
public enum RabbitBindingEnum {
    BIZ_WECHAT_MSG_TEMPLATE(QueueConstant.BIZ_WECHAT_MSG_TEMPLATE_QUEUE, QueueConstant.WECHAT_EXCHANGE, "biz_wechat_msg_template", "微信消息模板发送队列，自动确认"),
    BIZ_ACTIVITY_COLLECT(QueueConstant.BIZ_ACTIVITY_COLLECT_QUEUE, QueueConstant.ACTIVITY_EXCHANGE, "biz_activity_collect", "活动数据采集队列，自动确认");

    private RabbitBindingEnum(String queues, String exchange, String routingKey, String desc) {
        this.queues = queues;
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.desc = desc;
    }

    private String queues;
    private String exchange;
    private String routingKey;
    private String desc;

    public String getQueues() {
        return queues;
    }

    public void setQueues(String queues) {
        this.queues = queues;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static RabbitBindingEnum getRabbitBindingEnum(String exchange, String routingKey) {

        for (RabbitBindingEnum rabbitBindingEnum : values()) {
            if (rabbitBindingEnum.getExchange().equals(exchange)
                    && rabbitBindingEnum.getRoutingKey().equals(routingKey)) {
                return rabbitBindingEnum;
            }
        }

        return null;
    }
}
