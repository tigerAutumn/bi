package com.pinting.gateway.qb178.in.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class StandardClientUtil {

	/**
	 * 解析参数
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseParam(HttpServletRequest request) {
		Map<String, String> paramsMap = new HashMap<String, String>();

		Enumeration<String> paramsEnum = request.getParameterNames();
		while (paramsEnum.hasMoreElements()) {
			String paramName = (String) paramsEnum.nextElement();
			String paramValue = request.getParameter(paramName);
			paramsMap.put(paramName, paramValue);
		}
		return paramsMap;
	}

	/**
	 * 应答数据解析成map
	 * 
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseResponse(String msg) {
		Map<String, Object> map = JSONObject.parseObject(msg, HashMap.class);
		Map<String, String> paramsMap = new HashMap<String, String>();
		for(String key : map.keySet()){
			if(!(map.get(key) instanceof String)){
			paramsMap.put(key, JSONObject.toJSONString(map.get(key)));
			}else{
			paramsMap.put(key, (String) map.get(key));
			}
		}
		return paramsMap;
	}
	
	/**
	 * 应答数据解析成List<map>
	 * 
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseResponseList(String msg) {
		List<Map<String, Object>> mapList = JSONArray.parseObject(msg, List.class);
		return mapList;
	}
	

	/**
	 * 转换成url参数
	 * 
	 * @param map
	 * @param isSort
	 *            是否排序
	 * @param removeKey
	 *            不包含的key元素集
	 * @return
	 */
	public static String getURLParam(Map<String, String> map, boolean isSort,
			Set<String> removeKey) {
		StringBuffer param = new StringBuffer();
		List<String> msgList = new ArrayList<String>();
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			String value = (String) map.get(key);
			if (removeKey != null && removeKey.contains(key)) {
				continue;
			}
			msgList.add(key + "=" + value);
		}

		if (isSort) {
			// 排序
			Collections.sort(msgList);
		}

		for (int i = 0; i < msgList.size(); i++) {
			String msg = (String) msgList.get(i);
			if (i > 0) {
				param.append("&");
			}
			param.append(msg);
		}
		return param.toString();
	}

	/**
	 * 签名
	 * 
	 * @param signedMsg
	 * @param key
	 * @return
	 */
	public static String sign(String signedMsg, String key) {
		try {
			MessageDigest digit = MessageDigest.getInstance("MD5");
			digit.update((signedMsg + key).getBytes("UTF-8"));
			return byte2hex(digit.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * byte array to hex
	 * 
	 * @param b
	 *            byte array
	 * @return hex string
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp;
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString();
	}

	/**
	 * is not empty
	 * 
	 * @param s
	 *            srt
	 * @return boolean
	 */
	public static boolean isNotEmpty(String s) {
		if (s == null || s.trim().length() == 0) {
			return false;
		}
		return true;
	}
	
	
	public static Map<String, Object> parseResponseObject(String msg) {
		Map<String, Object> map = JSONObject.parseObject(msg, HashMap.class);
		return map;
	}
	
	
	public static void main(String[] args) {
		String msg = "{\"error_no\":\"\",\"total_num\":13054,\"cert_sign\":\"\",\"data\":[{\"user_account\":\"1586815790201\",\"order_time\":\"20170803144624\",\"product_code\":\"1130\",\"product_name\":\"报业专享短期乐0080期\",\"delegate_type\":\"applyTrade\",\"delegation_code\":\"201708031446241000561030491913\",\"delegate_status\":\"delegating\",\"order_balance\":100000},{\"user_account\":\"1586815790201\",\"order_time\":\"20170803144525\",\"product_code\":\"1130\",\"product_name\":\"报业专享短期乐0080期\",\"delegate_type\":\"applyTrade\",\"delegation_code\":\"201708031445251000561053886303\",\"delegate_status\":\"delegating\",\"order_balance\":100000}],\"error_info\":\"\",\"current_page\":1}";
		Map<String, Object> map = JSONObject.parseObject(msg, HashMap.class);
		System.out.println("AAAAAAA<<<<<<<<<<"+map.get("data").toString());
		System.out.println("BBBBBBB<<<<<<<<<<"+JSONObject.toJSONString(map.get("data")));
	}


	
	
}
