/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.GlobEnvUtil;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: CALoadBalancing.java, v 0.1 2015-9-22 上午9:47:40 BabyShark Exp $
 */
public class CALoadBalancing {
    private static Logger   log        = LoggerFactory.getLogger(CALoadBalancing.class);
    private static String   ip1        = GlobEnvUtil.get("cfca.web.ip1");
    private static String   ip2        = GlobEnvUtil.get("cfca.web.ip2");
    private static int      loadFactor = 0;
    private static String[] ips        = new String[2];

    /**
     * 获得ca服务器ip地址组
     * 
     * @return
     */
    public static String[] getWebIps() {
        if (getLoadFactor() % 2 == 0) {
            log.info(">>>>>>获得CA服务器IP1地址组：[" + ip1 + "," + ip2 + "]");
            ips[0] = ip1;
            ips[1] = ip2;
            return ips;
        } else {
            log.info(">>>>>>获得CA服务器IP2地址组：[" + ip2 + "," + ip1 + "]");
            ips[0] = ip2;
            ips[1] = ip1;
            return ips;
        }
    }

    public static String getWebIp1() {
        return ip1;
    }

    public static String getWebIp2() {
        return ip2;
    }

    private static synchronized int getLoadFactor() {
        if (loadFactor == 10000) {
            loadFactor = 0;
        }
        loadFactor++;
        return loadFactor;
    }
}
