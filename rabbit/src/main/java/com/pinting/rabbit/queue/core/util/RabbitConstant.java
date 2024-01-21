package com.pinting.rabbit.queue.core.util;

import com.pinting.core.util.GlobEnvUtil;

/**
 * rabbit 配置资源文件常量类
 * Created by zousheng on 2018/8/3.
 */
public class RabbitConstant {

    // rabbit 配置
    public static Integer RABBIT_LISTENER_CONCURRENTCONSUMERS = 1;
    public static String RABBIT_MODE = "";

    static {
        try {
            RABBIT_LISTENER_CONCURRENTCONSUMERS = Integer.valueOf(GlobEnvUtil.get("rabbit.listener.concurrentConsumers"));
            RABBIT_MODE = "_" + GlobEnvUtil.get("rabbit.mode");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
