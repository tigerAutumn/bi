package com.pinting.gateway.mobile.in.util;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	/**
	 * 
	 * @Title: RegCheck 
	 * @Description: 通过正则表达式判断字符串
	 * @param param
	 * @param reg
	 * @return 匹配返回true,不匹配返回false
	 * @throws
	 */
	public static boolean RegCheck(String param, String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(param);
		if(matcher.matches()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @Title: ParamCheck 
	 * @Description: 判断对象中制定属性是否为空
	 * @param c
	 * @param property
	 * @return 全部不为空返回true,否则返回false
	 * @throws
	 */
	public static boolean ParamCheck(Object o, String...pros) {
		boolean flag = true;
		for(String pro : pros) {
			String obj = getProperty(o, pro);
			if(obj == null || "".equals(obj.trim())) {
				flag = false;
				break;
			}
		}
		
		return flag;
	}
	
	private static String getProperty(Object obj, String propertyName) {
		String str = null;
		try {
			Method method = obj.getClass().getDeclaredMethod(getterName(propertyName));
			str = type2string(method.invoke(obj));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	private static String getterName(String propertyName) {
		return "get" + propertyName.substring(0,1).toUpperCase() + propertyName.substring(1);
	}
	
	private static String type2string(Object value) {
		String temp = "";
		if(value != null) {
			if(value instanceof String) {
				temp = (String)value;
			}
			else {
				temp = String.valueOf(value);
			}
		}
		return temp;
	}
}
