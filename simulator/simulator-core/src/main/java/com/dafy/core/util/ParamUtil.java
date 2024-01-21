package com.dafy.core.util;



import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

public class ParamUtil {

	public static String createLinkString(JSONObject obj) {
		Map<String,String> map = new HashMap<String,String>();
		Set<Entry<String, Object>> set = obj.entrySet();
		Iterator<Entry<String, Object>> iter = set.iterator();
		while(iter.hasNext()) {
			Entry<String, Object> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue().toString();
			map.put(key, value);
		}
		List<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	public static Map<String, Object> calParamsObject(Object ex) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		Class<?> cs = ex.getClass();
		Field[] fs = cs.getDeclaredFields();
		for (Field f : fs) {
			//2表示private
			if (f.getModifiers() == 2) {
				Method m = cs.getDeclaredMethod("get" + methodName(f.getName()), new Class[0]);
				Object val = m.invoke(ex, new Object[0]);
				param.put(f.getName(), val);
			}
		}
		return param;
	}

	public static Map<String, Object> calParamsObject(Object ex, Class<?> cs) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		Field[] fs = cs.getDeclaredFields();
		for (Field f : fs) {
			//2表示private
			if (f.getModifiers() == 2) {
				Method m = cs.getDeclaredMethod("get" + methodName(f.getName()), new Class[0]);
				Object val = m.invoke(ex, new Object[0]);
				param.put(f.getName(), val);
			}
		}
		return param;
	}

	public static String methodName(String fieldName) {
		if ((fieldName == null) || ("".equals(fieldName))) {
			return fieldName;
		}
		if (fieldName.length() == 1) {
			return fieldName.toUpperCase();
		}
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
	public static Map<String,Class<?>> objectFieldListConvert(Class<?> respClass) {
		Map<String,Class<?>> resMap = new HashMap<String,Class<?>>();
		//得到所有的fields
		Field[] fs = respClass.getDeclaredFields();
		for(Field f : fs) {
			//得到field的class及类型全路径
			Class<?> fieldClazz = f.getType();
			//判断是否为基本类型
			if(fieldClazz.isPrimitive()) {
				continue;
			}
			//getName()返回field的类型全路径
			if(fieldClazz.getName().startsWith("java.lang")) {
				continue;
			}
			//isAssignableFrom判断fieldClazz是不是ArrayList本身或者父类
			if(fieldClazz.isAssignableFrom(ArrayList.class)) {
				//如果是List类型，得到其Generic的类型
				Type fc = f.getGenericType();
				if(fc == null) {
					continue;
				}
				//泛型参数类型
				if(fc instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) fc;
					//得到泛型里的class类型对象
					Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
					resMap.put(f.getName(), genericClazz);
				}
			}
		}
		return resMap;
	}
}
