package com.pinting.cfcatransfer.bigangwan.util;

import java.net.HttpURLConnection;
import java.net.URL;

import com.pinting.core.util.GlobEnvUtil;


public class SealHostCheckFactory {
    private volatile static int validateIndex = 0;

    /**
     * @param args
     */
    public static String getValidHost(String serviceName) {

        String[] ips = CALoadBalancing.getWebIps();
        String port = GlobEnvUtil.get("cfca.seal.port");
        SealCommunicationConfig.hosts = new StringBuffer().append("http://").append(ips[0])
            .append(":").append(port).append("/Seal").append(",").append("http://").append(ips[1])
            .append(":").append(port).append("/Seal").toString().split(",");

        String endPoint = SealCommunicationConfig.hosts[validateIndex] + "/services/" + serviceName;
        boolean valid = checkHost(endPoint);
        if (!valid) {
            synchronized (SealCommunicationConfig.hosts) {
                int hostsSize = SealCommunicationConfig.hosts.length;
                if (hostsSize > 2) {
                    hostsSize = 2;
                }
                for (int i = 0; i < hostsSize; ++i) {
                    endPoint = SealCommunicationConfig.hosts[validateIndex] + "/services/"
                               + serviceName;
                    valid = checkHost(endPoint);
                    if (!valid) {
                        validateIndex = (validateIndex + 1) % hostsSize;
                    } else {
                        break;
                    }
                    if (i == hostsSize - 1) {
                        endPoint = "";
                    }
                }
            }
        }

        return endPoint;
    }

    private static boolean checkHost(String host) {
        System.setProperty("sun.net.client.defaultConnectTimeout", "" + 1 * 50);
        boolean valid = true;
        HttpURLConnection httpUrlConnection = null;
        try {
            URL url = new URL(host + "?wsdl");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            int returnCode = httpUrlConnection.getResponseCode();
            valid = returnCode == 200;
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

}
