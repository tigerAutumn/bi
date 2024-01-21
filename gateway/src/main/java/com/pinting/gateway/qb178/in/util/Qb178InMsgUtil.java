package com.pinting.gateway.qb178.in.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.qb178.in.model.BaseReqModel;
import com.pinting.gateway.qb178.in.model.BaseResModel;
import com.pinting.gateway.qb178.in.util.Md5Encrypt.CustomTreeMapComparator;
import com.pinting.gateway.util.Constants;

/**
 * 
 * @project gateway
 * @title Qb178InMsgUtil.java
 * @author Dragon & cat
 * @date 2017-7-29
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class Qb178InMsgUtil {

	private static Logger log = LoggerFactory.getLogger(Qb178InMsgUtil.class);
	
	//恒丰模拟环境证书(回调验签)
	public static String certificateIn = "kl94bifcdmil6ash3wwpsp2z/3usx71o9amphpv5f22b8x-u8fb5b-77-1sf9q6329+hh/vigiiqsf/p3i1yhp6krbi4vr/oj6iplju+dsyckivarq5wd9ta/l9xjjk5q4np584l66165j-snpzi2chec1drii2awh25nz55fme679dyhb8tsdktz12kg6bhuy1knpxcqpjhxw4g7m/mi-sc4x1xn1dqsllcw5cpfpp6pbqcmbwa0exoj71s+//sdnpcc0ou8z6zmyp-3zo4u9adht/yy2y/i7jxtv81qorl8-aft14y2/36ol7r17in6hwel945zuhvkt2pyxtq231op56m4hbs+qtl5/9p2pe4jy6/0y23xxvpy/-uc+ncyxx/e/y6v8qatxwrj0u9egy3-u10kxfyalth5phzajbf6pixx+aik6jc7ykt27yij0+6wefx8+uej0rjjcdvlgswrt++nk8kao1b2zh8fsxudydglupx3mobr853h5+p";
	public static long lastAccessTime;//恒丰系统最后访问时间
	
	//合作商ID 
	public static String qb178AppInExchangeCode = "108991002";
	//合作商渠道
	public static String qb178AppInChannelCode = "bgw";
	//签名盐值
	public static String qb178AppInMacKey = "12478";
	
	static{
		try{
			if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))){
				qb178AppInExchangeCode = GlobEnvUtil.get("qb178.app.in.exchange.code");
				qb178AppInChannelCode= GlobEnvUtil.get("qb178.app.in.channel.code");
				qb178AppInMacKey= GlobEnvUtil.get("qb178.app.mac.key");
			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	

	/**
	 * 组装签名数据md5加密前的报文
	 * @param t model类型
	 * @param transCode 交易码
	 * @return
	 */
	private static <T> String packageMsgSign(T t) {
		HashMap<String, String> tempMap = transBeanMap(t);
		tempMap.put("mackey", qb178AppInMacKey);
		String sign = Md5Encrypt.sortSource(tempMap);
		return sign;
	}
	
	
	/**
	 * 取得签名数据字符串
	 * @param req 待响应bean对象
	 * @return
	 */
	public static String getSignData(BaseResModel req) {
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
	 * 返回参数取得签名数据
	 * @param req 待响应bean对象
	 * @return
	 */
	public static String getSignDataRes(Map<String, String> paramMap) {
		paramMap.put("mackey", qb178AppInMacKey);
		paramMap.remove("cert_sign");
		String signData = null;
		try {
			String sign = Md5Encrypt.sortSource(paramMap);
			log.info("============组装报文，signData明文：【" + sign + "】============");
			String encryptSign = Md5Encrypt.md5(sign);
			log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
			signData = encryptSign;
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
		}
		return signData;
	}
	
	
	public static boolean checkSignData(BaseReqModel req) {
		String signData = "";
		try {
			
			String sign = packageMsgSign(req);
			log.info("============组装报文，signData明文：【" + sign + "】============");
			String encryptSign = Md5Encrypt.md5(sign);
			log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
			signData = encryptSign;
			
			if (!signData.equals(req.getCert_sign()) ) {
				return false;
			}
			
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
			return false;
		}
		return true;
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
	                    
	                    if ("data".equals(key) || value instanceof List) {
	                    	if (value == null) {
	                    		map.put(key, "[]");  
							}else {
						    	JsonConfig config = new JsonConfig();
								config.registerJsonValueProcessor(Date.class,
										new JsonValueProcessorImpl());
						        //JSONObject jsonObject = JSONObject.fromObject(obj, config);
		                    	JSONArray jsonString = JSONArray.fromObject(value, config);
								map.put(key, jsonString.toString());  
							}
						}else if ("total_num".equals(key) || "current_page".equals(key) ) {
							map.put(key, value == null ? "0":value.toString());   
						} else {
							map.put(key, value == null ? "":value.toString());  
						}
	                   
	                }  
	  
	            }  
	        } catch (Exception e) {  
	            System.out.println("transBean2Map Error " + e);  
	        }  
	  
	        return map;  
	
	} 
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String encryptSign = Md5Encrypt.md5("create_time_begin=&create_time_end=&mackey=12478&page_num=1&page_size=1&product_code=&user_account=15271841099");
		log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
	}


	public static String sortResponse(String resp) {
		Map<String, Object> map = StandardClientUtil.parseResponseObject(resp);
		if (map.containsKey("data")) {
			com.alibaba.fastjson.JSONArray dataValue = (com.alibaba.fastjson.JSONArray) map.get("data");
			String resValue = Md5Encrypt.sortMapData(dataValue.toString());
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(resValue);
			map.put("data", jsonArray);
		}
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>(new CustomTreeMapComparator<String>());
		treeMap.putAll(map);
		String res = JSONObject.fromObject(treeMap).toString();
		return res;
	}
	
}
