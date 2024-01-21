/**
 * All rights reserved.
 * 
 * StringUtil
 * @author yanwl
 * @version 1.0
 * @date 2015-11-19
 */
package com.dafy.core.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * StringUtil 工具类
 * @author yanwl
 * @date 2015-11-19
 *
 */
public class StringUtil extends StringUtils {
	
	public static final String CHARSET_ENCODING = "UTF-8";
	
	/**
	 * 通过字符串驻留池来进行比较
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean intern(String str1, String str2) {
		if (str1 == null) return str2 == null;
		
		str1 = str1.intern();
		
		return (str1 == str2)?true : false;
	}
	
	/**
	 * 将String aaBc 转为 aa_bc的格式
	 * 用于java bean 属性转为数据库字段名
	 * @param str
	 * @return
	 */
	public static String propertyToFieldName(String str) {
		if (isEmpty(str)) return str;
		
		if(str.charAt(0) > 'A' && str.charAt(0) < 'Z') return str;
		
		if(Character.isUpperCase(str.charAt(0))) 
			str = Character.toString(Character.toLowerCase(str.charAt(0)))+str.substring(1);
		
		for(int i= 1; i < str.length(); i++) {
			char a = str.charAt(i);
			if(a >= 'A' && a <= 'Z') {
				if(str.charAt(i-1) != '_') {
					str = str.replace(String.valueOf(a), ("_".concat(String.valueOf(a))).toLowerCase());
					i++;
				}
				
				continue ;
			}
		}
		
		return str;
	}
	
	/**
	 * 返回截取字符串
	 * @param str
	 * @param num
	 * @return
	 */
	public static String omit(String str, int num) {
		
		if (StringUtil.isBlank(str)) return null;
		if(str.length() < num) return str;
		
		return num > 0?str.substring(0, num):str;
	}
	
	/**
	 * 截断字符串，只适用中文和单字节表示字符的国家
	 * @param str
	 * @param byteLength
	 * @return
	 */
	public static String limit(String str, int byteLength) {
		if (isBlank(str)) return null;
		if (byteLength <= 0) return null;
		
		try {
			if (str.getBytes(StringUtil.CHARSET_ENCODING).length <= byteLength) return str;
		} catch (UnsupportedEncodingException e) {
			
			return null;
		}
		
		StringBuffer buff = new StringBuffer();
		int index = 0;
		char c;
		char[] arr = new char[1];
		while (byteLength > 0) {
			c = str.charAt(index);
			arr[0] = c;
			if (!isChineseString(new String(arr))) {
				byteLength--;
			} else {
				byteLength--;
				byteLength--;
			}
			
			buff.append(c);
			index ++;
		}
		
		buff.append("...");
		
		return buff.toString();
	}
	
	/**
	 * 检查给定的字符串中是否包含中文信息
	 * @param string
	 * @return
	 */
	public static boolean isChineseString(String string) {
		if(isBlank(string)) return false;
		
		String patternRange = "[\\u4E00-\\u9FA5]+";
		Pattern pattern = Pattern.compile(patternRange);
		Matcher matcher = pattern.matcher(string);
		
		return matcher.find();
	}
	
	/**
	 * 将String中Null转换成""
	 * @param string
	 * @return
	 */
	public static String nullToString(String string) {
		
		return StringUtil.isBlank(string)?"":string;
	}
	
	public static String trim0(String num) {
		int begin = 0;
	    int end = num.length() - 1;
	    
	    while (num.charAt(end) == '0')
	    	--end; 
	    
	    if (num.charAt(end) == '.') 
	    	--end;
	    
	    return begin <= end?num.substring(begin, end + 1):"0";
	}
	
	public static String trimStr(String str) {
		
		return trimToEmpty(str).replaceAll("[\\r\\n]", "");
	}
	
	public static int toInt(String str){
		if(isBlank(str)){
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} 
	}
	
	//将小于10的数字转换为前面带0的字符串（例如  4-->04）
	public static String int2Str(int num){
		if(num > 0 && num < 10 ) {
			return "0"+num;
		}else {
			return ""+num;
		}
	}
	
	//获取固定位数是20位的单号
	public static String generateOrderNum(int userId,int projectId){
		String dateStr = (toHex(new Date().getTime())).
				replace("a", "1").replace("b", "2").replace("c", "3").
				replace("d", "4").replace("e", "5").replace("f", "6");
		String orderNum = dateStr;
		
		//用户编号格式化
		DecimalFormat df1 = new DecimalFormat("00000000");
		df1.setMaximumIntegerDigits(8);
		orderNum += df1.format(userId);
		
		//项目编号格式化
		DecimalFormat df2 = new DecimalFormat("0000");
		df2.setMaximumIntegerDigits(4);
		orderNum += df2.format(projectId);
		return orderNum;
	}
	
	private static String toHex(long time){    
		String str = Integer.toHexString((int)time);
		int len = str.length();
		if(len < 8) {
			for (int i = 0; i < 8-len; i++) {
				str = "0" + str;
			}
		}else {
			str = str.substring(len-8);
		}
        return str;
	}
	
	
	public static String generateNum(){
		long time = new Date().getTime();
		String result = Integer.toHexString((int)time);
		
		char[] chars = result.toCharArray();
		System.out.println(chars);
		for(int i=0; i<chars.length; i++){
			if(chars[i] >= 97){
				chars[i] -= 49;
			}
		}
		return new String(chars);
	}
	
	public static String generateOrderNum(String number){
		
		return generateNum() + number;
	}
	
	public static final String randomString(int length) {
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		if (length < 1) {
			return "";
		}
		Random randGen = new Random();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}
	
	public static String null2String(Object obj) {
		if(obj == null) {
			return "";
		}
		return obj.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(generateNum());
	}
}
