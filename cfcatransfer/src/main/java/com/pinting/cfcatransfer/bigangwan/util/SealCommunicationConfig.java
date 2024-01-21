package com.pinting.cfcatransfer.bigangwan.util;

import java.util.Arrays;
import java.util.ResourceBundle;

import com.pinting.core.util.GlobEnvUtil;

public class SealCommunicationConfig {

    public static final String DEFAULT_CHARSET       = "UTF-8";

    public static final String ASSIST_SEAL_SERVICE   = "AssistSealService";
    public static final String MAKE_SEAL_SERVICE     = "MakeSealService";
    public static final String WEB_SEAL_SERVICE      = "WebSealService";
    public static final String PDF_SEAL_SERVICE      = "PdfSealService";
    public static final String BUSINESS_SEAL_SERVICE = "BusinessSealService";

    public static String[]     hosts                 = new StringBuffer().append("http://")
                                                         .append(CALoadBalancing.getWebIp1())
                                                         .append(":")
                                                         .append(GlobEnvUtil.get("cfca.seal.port"))
                                                         .append("/Seal").append(",")
                                                         .append("http://")
                                                         .append(CALoadBalancing.getWebIp2())
                                                         .append(":")
                                                         .append(GlobEnvUtil.get("cfca.seal.port"))
                                                         .append("/Seal").toString().split(",");

    public static String       nameSpace             = "http://impl.ws.service.front.seal.cfca"; //"http://webservice.fep.yuzhi.cfca";
    public static int          timeOut               = 300;

    /**
     * 初始化配置文件参数，资源文件为classpath下根目录的config.properties,参数值为:config
     * 
     * @param configPath
     * @throws Exception
     */
    public static void initClientEnvironment(String configPath) {
        System.out.println("config dir:" + configPath);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(configPath);
        hosts = resourceBundle.getString("hosts").trim().split(",");
        nameSpace = resourceBundle.getString("nameSpace").trim();
        String tempTimeOut = resourceBundle.getString("timeOut").trim();
        timeOut = Integer.parseInt(tempTimeOut);
        System.out.println("visit url:" + Arrays.asList(hosts));
    }

}
