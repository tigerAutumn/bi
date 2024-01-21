package com.pinting.gateway.qb178.out.util;

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
}
