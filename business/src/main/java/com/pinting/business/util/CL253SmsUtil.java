package com.pinting.business.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

/**
 * 创蓝253短信平台
 * @author bianyatian
 * @2017-6-27 下午1:53:07
 */
public class CL253SmsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CL253SmsUtil.class);
	private static String username = GlobEnvUtil.get("cl253.username").trim();
	private static String password = GlobEnvUtil.get("cl253.password").trim();
	private static String url = "https://sms.253.com/msg/send";
	
	/**
	 * 短信发送
	 * @param mobile 手机号，以英文逗号隔开
	 * @param message 发送内容
	 * @param sendTime -为空时，是即时发送，否则为定时发送
	 * @return 序列号
	 */
	public static String sendSMS(String mobile, String message) {
		String str = "";
		try {
			//判断手机号长度和是否为1开头
			if(StringUtil.isNotBlank(mobile) && mobile.length()>= 11 && mobile.startsWith("1")){
				String returnString = batchSend(mobile, message);
				String[] returnArr = returnString.replace("\n", ",").split(",");
				if("0".equals(returnArr[1]) && returnArr.length >=3){
					return returnArr[2];
				}else{
					return "error:"+returnArr[1];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}

	/**
	 * 短信发送组装
	 * @param phone
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	private static String batchSend(String phone, String msg) throws Exception {
        HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        GetMethod method = new GetMethod();
        try {
            URI base = new URI(url, false);
            method.setURI(new URI(base, "send", false));
            method.setQueryString(new NameValuePair[] {
                    new NameValuePair("un", username),
                    new NameValuePair("pw", password),
                    new NameValuePair("phone", phone),
                    new NameValuePair("rd", "1"),//是否需要状态报告，0表示不需要，1表示需要
                    new NameValuePair("msg", msg),
                    new NameValuePair("ex", null),
                });
            int result = client.executeMethod(method);
            if (result == HttpStatus.SC_OK) {
                InputStream in = method.getResponseBodyAsStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return URLDecoder.decode(baos.toString(), "UTF-8");
            } else {
            	logger.info("========【253短信】发送失败:"+phone+"==============");
                throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
            }
        } finally {
            method.releaseConnection();
        }
	}
	
	
	public static void main(String []args){
		try {
			String returnString = CL253SmsUtil.sendSMS("15990147161", "【赞分期】	验证码为1110，有效期120秒，您正在进行绑卡验证，如有疑问请拨打400-806-1230。");
			System.out.println(">>>>>>>>>>>>>>"+returnString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
