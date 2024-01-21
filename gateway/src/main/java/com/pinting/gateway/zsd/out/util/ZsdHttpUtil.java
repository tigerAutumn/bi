/**
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 */
/**
 * @author Administrator
 *
 */
package com.pinting.gateway.zsd.out.util;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.zsd.out.model.BaseReqModel;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * 
 * @project gateway
 * @title ZsdHttpUtil.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ZsdHttpUtil {

    private static Logger log = LoggerFactory.getLogger(ZsdHttpUtil.class);
    private static final int TIMEOUT = 3000;

    /**
     * 赞时贷通知地址
     */
    public static String url = "http://120.26.167.26:7100/credit-paycenter/api/v2/bigangwan";
    public static String token_url = "http://120.26.167.26:6300";

    /**
     * 客户端标识
     */
    public static String clientKey = "bigangwan";
    public static String org_code = "123456";
    public static String org_secret = "123456";
    
    public static String tokenExpirCode = "999004"; //原本token超时响应码
    public static String tokenExpirHttpStatusCode = "401"; //token超时http状态码

    static {
        if (Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))||Constants.GATEWAY_SERVER_MODE_TEST.equals(GlobEnvUtil.get("server.mode")) ) {
            try {
                url = GlobEnvUtil.get("zsd.notice.url");
                token_url = GlobEnvUtil.get("zsd.notice.token_url");
                
                clientKey = GlobEnvUtil.get("zsd.clientKey");
                org_code = GlobEnvUtil.get("zsd.org_code");
                org_secret = GlobEnvUtil.get("zsd.org_secret");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 向赞时贷发送请求
     * @param url 请求地址
     * @param req 请求参数
     * @param reqPrivateParamMap 私有变量
     * @return
     * @throws Exception
     */
    public static String requestForm(String requestUrl, BaseReqModel req, HashMap<String, String> reqPrivateParamMap) throws Exception {
    	log.info("通知请求地址（PUT）："+requestUrl);
        String result = NetUtils.sendJsonDataByPut(requestUrl, JSON.toJSONString(req), NetUtils.ENC_UTF8, TIMEOUT,req.getToken());
        if(StringUtil.isNotEmpty(result)){
            //String statusCode = result.substring(0, 3);
//            String content = result.substring(3);
//            return content;
        	return result;
        }
        return null;

        /*CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader(HTTP.CONTENT_TYPE, "application/json");
        StringEntity entity = new StringEntity(JSON.toJSONString(req));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        try {
            httpPut.setEntity(entity);
            HttpResponse httpResponse1 = httpclient.execute(httpPut);

            if (httpResponse1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse1.getEntity();
                String result = EntityUtils.toString(httpEntity);//取出应答字符串

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("网络连接错误");
        } finally {
            httpPut.releaseConnection();
        }

        return null;*/
    }
    
    
    /**
     * 向赞时贷发送请求
     * @param url 请求地址
     * @param req 请求参数
     * @param reqPrivateParamMap 私有变量
     * @return
     * @throws Exception
     */
    public static String requestFormByPost(String requestUrl, BaseReqModel req, HashMap<String, String> reqPrivateParamMap) throws Exception {
    	log.info("通知请求地址（POST）："+requestUrl);
    	String result = NetUtils.sendJsonDataByPost(requestUrl, JSON.toJSONString(req), NetUtils.ENC_UTF8, TIMEOUT);
        if(StringUtil.isNotEmpty(result)){
            String content = result.substring(3);
            return content;
        }
        return null;
  
    }


}