package com.pinting.csai.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 *
 */
public class UrlParmHelper {
	
	/**
	 * @param urlparam ��ָ���url����
	 * @return
	 */
	public static Map<String,String> Split(String urlparam){
		Map<String,String> map = new HashMap<String,String>();
		String[] param =  urlparam.split("&");
		for(String keyvalue:param){
			String[] pair = keyvalue.split("=");
			if(pair.length==2){
				map.put(pair[0], pair[1]);
			}
		}
		return map;
	}
}
