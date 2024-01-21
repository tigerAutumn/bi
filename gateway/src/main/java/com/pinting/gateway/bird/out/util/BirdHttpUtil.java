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
package com.pinting.gateway.bird.out.util;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.bird.out.model.BaseReqModel;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class BirdHttpUtil {

    private static Logger log = LoggerFactory.getLogger(BirdHttpUtil.class);
    private static final int TIMEOUT = 3000;

    /**
     * 蜂鸟通知地址
     */
    public static String url = "http://192.168.1.125:8044";
    public static String url_v2 = "http://120.26.167.26:7100/creditpaycenter/api/v2/bigangwan/rest";
    public static String token_url = "http://120.26.167.26:6300";

    /**
     * 客户端标识
     */
    public static String clientKey = "bigangwan";
    public static String org_code = "6001341951590400";
    public static String org_secret = "bigangwan";
    public static String tokenExpirCode = "999004"; //原本token超时响应码
    public static String tokenExpirHttpStatusCode = "401"; //token超时http状态码

    static {
        if (!Constants.GATEWAY_SERVER_MODE_DEV.equals(GlobEnvUtil.get("server.mode"))) {
            try {
                url = GlobEnvUtil.get("zan.notice.url");
                url_v2 = GlobEnvUtil.get("zan.notice.url.v2");
                token_url = GlobEnvUtil.get("zan.notice.token_url");
                clientKey = GlobEnvUtil.get("zan.clientKey");
                org_code = GlobEnvUtil.get("zan.org_code");
                org_secret = GlobEnvUtil.get("zan.org_secret");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 向蜂鸟发送请求
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
     * 向蜂鸟发送请求
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