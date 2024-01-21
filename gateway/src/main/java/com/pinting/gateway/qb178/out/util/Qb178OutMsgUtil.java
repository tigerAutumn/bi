package com.pinting.gateway.qb178.out.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.qb178.out.model.BaseReqModel;
import com.pinting.gateway.util.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.pinting.gateway.qb178.out.util.HttpClientUtil.getAccessToken;

public class Qb178OutMsgUtil {
	private static Logger log = LoggerFactory.getLogger(Qb178OutMsgUtil.class);
	private static final String TOKEN_ERROR_CODE = "invalid_token";
	
	//签名盐值
	public static String qb178AppOutMacKey = "12478";
	
	static{
		try{
			if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))){
				qb178AppOutMacKey= GlobEnvUtil.get("qb178.app.mac.key");
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	public static <T> T requestQb178(String url, Map<String, String> params,
												  Class<T> resClazz, String interfacename, String method){

		String result = "";
		if(HttpClientUtil.METHOD_GET.equals(method)){
			result = HttpClientUtil.sendGet(url, params, HttpClientUtil.CHARSET, null,
					HttpClientUtil.BEARER + getAccessToken(), interfacename);
		}else{
			result = HttpClientUtil.sendPost(url, params, HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null,
					HttpClientUtil.BEARER + getAccessToken(), interfacename);
		}
		if(StringUtil.isEmpty(result)){
			return null;
		}
		JSONObject resJosn = JSON.parseObject(result);
		if(TOKEN_ERROR_CODE.equals(resJosn.getString("error"))){
			log.error("请求钱报178失败：" + resJosn.getString("error_description"));
			return null;
		}
		if(!StringUtil.isEmpty(resJosn.getString("data"))){
			JSONArray arr = resJosn.getJSONArray("data");
			T res = parseObject(arr.get(0).toString(), resClazz);
			return res;
		}
		return null;
		
	}


	/**
	 * 取得签名数据字符串
	 * @param req 待响应bean对象
	 * @return
	 */
	public static String getSignData(BaseReqModel req) {
		String signData = null;
		try {
			String sign = packageMsgSign(req);
			log.info("============组装报文，signData明文：【" + sign + "】============");
			String encryptSign = Md5Encrypt.md5(sign);
			log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
			signData = encryptSign;
			
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
		}
		return signData;
	}
	
	/**
	 * 签名校验
	 * @param req
	 * @return
	 */
	public static boolean checkSignData(BaseReqModel req) {
		String signData = "";
		try {
			
			String sign = packageMsgSign(req);
			log.info("============组装报文，signData明文：【" + sign + "】============");
			String encryptSign = Md5Encrypt.md5(sign);
			log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
			signData = encryptSign;
			
			if (!signData.endsWith(req.getCert_sign()) ) {
				return false;
			}
			
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
			return false;
		}
		return true;
	}
	
	/**
	 * 组装签名数据md5加密前的报文
	 * @param t model类型
	 * @return
	 */
	private static <T> String packageMsgSign(T t) {
		HashMap<String, String> tempMap = transBeanMap(t);
		tempMap.put("mackey", qb178AppOutMacKey);
		String sign = Md5Encrypt.sortSource(tempMap);
		return sign;
	}
	
	public static HashMap<String, String> transBeanMap(Object obj) {  
		  
        if(obj == null){  
            return null;  
        }          
        HashMap<String, String> map = new HashMap<String, String>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value == null ? "" : value.toString());  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  

} 

}
