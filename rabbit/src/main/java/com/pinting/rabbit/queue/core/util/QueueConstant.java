package com.pinting.rabbit.queue.core.util;

/**
 * 队列名常量类
 * Created by babyshark on 2018/8/3.
 */
public interface QueueConstant {

    //---------------队列Exchange定义start------------------------

    String WECHAT_EXCHANGE = "wechat_exchange";
    String ACTIVITY_EXCHANGE = "activity_exchange";

    //---------------队列Exchange定义end------------------------

    //---------------队列标识定义start------------------------

    //微信消息模板发送队列
    String BIZ_WECHAT_MSG_TEMPLATE_QUEUE = "biz_wechat_msg_template";
    //活动数据采集队列
    String BIZ_ACTIVITY_COLLECT_QUEUE = "biz_activity_collect";

    //---------------队列标识定义end------------------------
    // ---------------队列queueNO前缀定义start------------------------

    // 微信通知
    String QUEUENO_PREFIX_WXTZ = "WXTZ";
    // 加薪计划
    String QUEUENO_PREFIX_JXJH = "JXJH";

    // ---------------队列queueNO前缀定义end------------------------
}
