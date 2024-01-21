package com.pinting.util;



import java.util.HashMap;
import java.util.Map;


import com.pinting.core.util.StringUtil;

/**
 * 返回DWZ Ajax数据
 * 
 * @author Flouny.Caesar
 *
 */
public class ReturnDWZAjax {
	
	/**
	 * 返回成功GSON
	 * @param message
	 * @return
	 */
	public static Map<Object,Object> success(String message) {
		return success(message, new HashMap<Object, Object>());
	}
	
	/**
	 * 返回成功GSON
	 * @param message
	 * @param parameterMap
	 * @return
	 */
	public static Map<Object,Object> success(String message, Map<Object, Object> parameterMap) {
		parameterMap.put("statusCode", "200");
		if (StringUtil.isNotBlank(message)) 		
		parameterMap.put("message", message);
		return parameterMap;
	}
	
	/**
	 * 返回失败GSON
	 * @param message
	 * @return
	 */
	public static  Map<Object,Object> fail(String message) {
		return fail(message, new HashMap<Object, Object>());
	}
	
	/**
	 * 返回失败GSON
	 * @param message
	 * @param parameterMap
	 * @return
	 */
	public static  Map<Object,Object> fail(String message, Map<Object, Object> parameterMap) {
		parameterMap.put("statusCode", "300");
		if (StringUtil.isNotBlank(message)) 
			parameterMap.put("message", message);
		return parameterMap;
	}
	
	/**
	 * 返回自定义GSON
	 * @param code
	 * @return
	 */
	public static Map<Object,Object> toAjaxString(String code) {
		
		return toAjaxString(code, null);
	}
	
	/**
	 * 返回自定义GSON
	 * @param code
	 * @param message
	 * @return
	 */
	public static Map<Object,Object> toAjaxString(String code, String message) {
		return toAjaxString(code, message, new HashMap<Object, Object>());
	}
	
	/**
	 * 返回自定义GSON
	 * @param parameterMap
	 * @return
	 */
	public static Map<Object,Object> toAjaxString(String code, String message, Map<Object, Object> parameterMap) {
		parameterMap.put("statusCode", code);
		
		if (StringUtil.isNotBlank(message)) 
			parameterMap.put("message", message);
		
		return parameterMap;
	}
}