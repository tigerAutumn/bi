package com.pinting.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.core.json.AjaxJsonValueProcessorImpl;
import com.pinting.core.json.JsonValueProcessorImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JSONUtil {
	public static JsonConfig DEFAULT_JSON_CONFIG = null;
	public static JsonConfig AJAX_JSON_CONFIG = null;

	public static String escapeValue(String json) {
		if (StringUtil.isBlank(json))
			return json;

		json = json.replaceAll("\\\\", "\\\\\\\\");
		json = json.replaceAll("\"", "\\\\\"");
		json = json.replaceAll("'", "\\\\'");
		json = json.replaceAll("\b", "\\\\b");
		json = json.replaceAll("\n", "\\\\n");
		json = json.replaceAll("\t", "\\\\t");
		json = json.replaceAll("\f", "\\\\f");
		json = json.replaceAll("\r", "\\\\r");

		return json;
	}

	public static String toKeyValue(String key, String value) {
		if (StringUtil.isEmpty(key))
			throw new RuntimeException("The key of json-string not exist!");

		return (value == null) ? key.concat(":null") : key.concat(":\"")
				.concat(escapeValue(value)).concat("\"");
	}

	public static String flashEscapeValue(String json) {
		if ((json == null) || (json.length() <= 0)) {
			return json;
		}
		json = json.replaceAll("<", "＜");
		json = json.replaceAll(">", "＞");
		json = json.replaceAll("\\\\", "\\\\\\\\");
		json = json.replaceAll("\"", "\\\\\"");
		json = json.replaceAll("'", "\\\\'");
		json = json.replaceAll("\b", "\\\\b");
		json = json.replaceAll("\n", "\\\\n");
		json = json.replaceAll("\t", "\\\\t");
		json = json.replaceAll("\f", "\\\\f");
		json = json.replaceAll("\r", "\\\\r");

		return json;
	}

	public static Object toBean(Object obj, Class<?> clazz) {
		if (obj instanceof JSONObject) {
			return JSONObject.toBean((JSONObject) obj, clazz);
		}
		if (obj instanceof String) {
			return JSONObject.toBean(JSONObject.fromObject(obj), clazz);
		}

		return null;
	}

	public static String toJSONString(Object obj) {
		return toJSONString(obj, DEFAULT_JSON_CONFIG);
	}

	public static String toJSONString(Object obj, JsonConfig jsonConfig) {
		if (obj == null)
			return null;

		String result = null;

		if (obj instanceof String) {
			return (String) obj;
		}
		if (obj instanceof Collection) {
			JSONArray jsons = new JSONArray();
			jsons.addAll((Collection) obj, jsonConfig);
			result = jsons.toString();
		} else if (JSONUtils.isArray(obj)) {
			JSONArray jsons = new JSONArray();
			jsons.add(obj, jsonConfig);
			result = jsons.toString();
		} else if (obj instanceof Map) {
			JSONObject json = new JSONObject();
			json.accumulateAll((Map) obj, jsonConfig);
			result = json.toString();
		} else {
			JSONObject json = new JSONObject();
			result = JSONObject.fromObject(obj, jsonConfig).toString();
		}

		if (AJAX_JSON_CONFIG.equals(jsonConfig)) {
			result = result.replaceAll("\\\\\\\\", "\\\\");
			result = result.replaceAll("\\\\\"", "\"");
			result = result.replaceAll("\\\\'", "'");
		}

		return result;
	}

	public static Object jsonObjectToBean(JSONObject json, Class<?> beanClass) {
		return JSONObject.toBean(json, beanClass);
	}

	public static Object jsonStringObjectToBean(String jsonString,
			Class<?> beanClass) {
		return JSONObject.toBean(JSONObject.fromObject(jsonString), beanClass);
	}

	static {
		AJAX_JSON_CONFIG = new JsonConfig();
		AJAX_JSON_CONFIG.registerJsonValueProcessor(Date.class,
				new AjaxJsonValueProcessorImpl());
		AJAX_JSON_CONFIG.registerJsonValueProcessor(String.class,
				new AjaxJsonValueProcessorImpl());
		DEFAULT_JSON_CONFIG = new JsonConfig();
		DEFAULT_JSON_CONFIG.registerJsonValueProcessor(Date.class,
				new JsonValueProcessorImpl());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List jsonString2BeanList(String jsonString,
			Class<?> beanClass){
		Object obj;
		List list = new ArrayList();
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		
		for(int i=0; i<jsonArray.size(); i++){
			obj = JSONObject.toBean(jsonArray.getJSONObject(i), beanClass);
			list.add(obj);
		}
		return list;
	}
}
