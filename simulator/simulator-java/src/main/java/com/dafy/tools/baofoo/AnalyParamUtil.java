package com.dafy.tools.baofoo;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.core.util.DateUtil;



/**
 * 用于解析参数请求
 * @project simulator-java
 * @title AnalyParamUtil.java
 * @author Dragon & cat
 * @date 2018-4-2
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class AnalyParamUtil {
	private static final Logger log = LoggerFactory.getLogger(AnalyParamUtil.class);
	
	private static Map<String, List<String>> transUnitMap = new HashMap<String, List<String>>();
	private static String[] transUnits = new String[]{
		//组装顺序
		"singlePay,transCode,clientKey,clientSecret,requestTime",//登录
	};

	public static Map paramReturn(Map properties){
		
	      // 返回值Map
	   	  Map returnMap = new HashMap();
	    
		  Iterator entries = properties.entrySet().iterator();
		  Map.Entry entry;
		  String name = "";
		  String value = "";
		  while (entries.hasNext()) {
		        entry = (Map.Entry) entries.next();
		        name = (String) entry.getKey();
		        Object valueObj = entry.getValue();
		        if(null == valueObj){
		            value = "";
		        }else if(valueObj instanceof String[]){
		            String[] values = (String[])valueObj;
		            for(int i=0;i<values.length;i++){
		                value = values[i] + ",";
		            }
		            value = value.substring(0, value.length()-1);
		        }else{
		            value = valueObj.toString();
		        }
		        returnMap.put(name, value);
		  }
		  return returnMap;
	}
	
	
	/**
	 * 
	 * @param t model类型
	 * @return
	 */
	public static <T> String parseResult(T t) {
		// 组装hash字段
		HashMap<String, Object> tempMap = (HashMap<String, Object>) t;
		StringBuffer result = new StringBuffer();
		for(String key:tempMap.keySet())
        {
			String unit = key;
         	Object obj = tempMap.get(key);
			if(obj instanceof Date){
				obj = DateUtil.format((Date) obj);
			}
			if(obj instanceof Double){
				obj = new DecimalFormat("0.00").format((obj));
			}
			if(obj instanceof List){
				result.append(unit).append("=").append(tempMap.get("jsonList")).append("&");
				continue;
			}
			if (obj == null) {
				result.append(unit).append("=").append("").append("&");
			}else {
				result.append(unit).append("=").append(obj).append("&");
			}
         
        }

		//去除&并返回
		return result.substring(0, result.length() - 1);
	}


	public static <T> String getSignature(T t) throws Exception {
		 String a = AnalyParamUtil.parseResult(t);
		 Map<String, String> ReturnData = FormatUtil.getParm(a);
		 String SignVStr = FormatUtil.coverMap2String(ReturnData);
		 log.info("SHA-1摘要字串："+SignVStr);
		 String signature = SecurityUtil.sha1X16(SignVStr, "UTF-8");//签名
		 log.info("SHA-1摘要结果："+signature);	
		 String singlePayPfxPath = System.getProperty("user.dir")+"\\src\\main\\webapp\\resources\\cer\\bfkey_100025773@@200001173.pfx";
		 String Sign = SignatureUtils.encryptByRSA(signature, singlePayPfxPath, "100025773_286941");
		 log.info("RSA签名结果："+Sign);	
		 return Sign;
	}
	
	
}
