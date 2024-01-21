/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.GlobEnvUtil;

import cfca.kt.toolkit.ClientContext;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: KTCommunicationUtil.java, v 0.1 2015-9-14 下午4:48:30 BabyShark Exp $
 */
public class KTCommunicationConfig {
    private static Logger log = LoggerFactory.getLogger(KTCommunicationConfig.class);

    public KTCommunicationConfig() {
    }

    public static ClientContext getCFCARAClientContext() {
        // 服务器地址
        String[] ips = CALoadBalancing.getWebIps();
        int port = Integer.valueOf(GlobEnvUtil.get("cfca.kt.port"));
        try {
            //测试：String ip = "192.168.0.233";
            String ip = ips[0];
            return initClient(ip, port);

        } catch (Exception e) {
            log.error(">>>>>>KT初始化连接失败", e);
            log.info(">>>>>>KT尝试初始化备用连接");
            String ip = ips[1];
            return initClient(ip, port);
        }
    }

    private static ClientContext initClient(String ip, int port) {
        // 连接超时 时间（ms）
        int connectTimeout = 6000;
        // 读取超时 时间（ms）
        int readTimeout = 6000;
        ClientContext client = null;
        // 初始化连接。
        log.info(">>>>>>KT初始化连接：" + ip + " " + port);
        ClientContext.initSocket(ip, port, connectTimeout, readTimeout);
        log.info(">>>>>>KT初始化连接成功");
        client = ClientContext.getInstance();
        return client;
    }
}
