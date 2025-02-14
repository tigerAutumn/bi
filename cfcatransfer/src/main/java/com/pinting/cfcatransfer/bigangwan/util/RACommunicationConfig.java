package com.pinting.cfcatransfer.bigangwan.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.GlobEnvUtil;

import cfca.ra.toolkit.CFCARAClient;
import cfca.ra.toolkit.exception.RATKException;

public class RACommunicationConfig {
    private static Logger      log                 = LoggerFactory
                                                       .getLogger(RACommunicationConfig.class);
    // 连接超时时间 毫秒
    public static final int    connectTimeout      = 3000;
    // 读取超时时间 毫秒
    public static final int    readTimeout         = 30000;
    // URL（http、https方式）
    public static String       url                 = "http://" + CALoadBalancing.getWebIp1() + ":"
                                                     + GlobEnvUtil.get("cfca.ra.port")
                                                     + "/RA/CSHttpServlet";
    // 服务器ip（socket、ssl socket方式）
    public static final String ip                  = GlobEnvUtil.get("cfca.web.ip1");
    // 服务器端口（socket、ssl socket方式）
    public static final int    port                = Integer.valueOf(GlobEnvUtil
                                                       .get("cfca.ra.port"));

    // 通信证书配置
    public static final String keyStorePath        = "D:/apache-tomcat-5.5.36/yanzhengT.jks";
    public static final String keyStorePassword    = "Abcd1234";
    // 信任证书链配置
    public static final String trustStorePath      = "D:/apache-tomcat-5.5.36/yanzhengT.jks";
    public static final String trustStorePassword  = "Abcd1234";

    public static final int    DEFAULT_CLIENT_TYPE = 1;

    // 客户端与RA之间为短链接
    // 该方法仅作为demo示例，使用时直接创建CFCARAClient对象即可
    // 连接参数不变时，CFCARAClient对象可重复使用，无需重新创建
    public static CFCARAClient getCFCARAClient(int type) throws RATKException {
        CFCARAClient client = null;
        String[] ips = CALoadBalancing.getWebIps();
        try {
            url = "http://" + ips[0] + ":" + port + "/RA/CSHttpServlet";
            client = initClient(url, type);
        } catch (Exception e) {
            log.info(">>>>>>RA初始化连接失败", e);
            url = "http://" + ips[1] + ":" + port + "/RA/CSHttpServlet";
            log.info(">>>>>>RA尝试初始化备用连接");
            client = initClient(url, type);
        }

        return client;
    }

    private static CFCARAClient initClient(String url, int type) throws RATKException {
        CFCARAClient client = null;
        log.info(">>>>>>RA初始化连接：" + url);
        switch (type) {
            case 1:
                // 初始化为http连接方式，指定url
                client = new CFCARAClient(url, connectTimeout, readTimeout);
                break;
            case 2:
                // 初始化为https连接方式，指定url，另需配置ssl的证书及信任证书链
                client = new CFCARAClient(url, connectTimeout, readTimeout);
                client.initSSL(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword);
                // 如需指定ssl协议、算法、证书库类型，使用如下方式
                // client.initSSL(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword, "SSL", "IbmX509", "IbmX509", "JKS", "JKS");
                break;
            case 3:
                // 初始化为socket 连接方式，指定ip和端口
                client = new CFCARAClient(ip, port, connectTimeout, readTimeout);
                break;
            case 4:
                // 初始化为ssl socket 连接方式，指定ip和端口，另需配置ssl的证书及信任证书链
                client = new CFCARAClient(ip, port, connectTimeout, readTimeout);
                client.initSSL(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword);
                // 如需指定ssl协议、算法、证书库类型，使用如下方式
                // client.initSSL(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword, "SSL", "IbmX509", "IbmX509", "JKS", "JKS");
                break;
            default:
                break;
        }
        log.info(">>>>>>RA初始化连接成功");
        return client;
    }
}
