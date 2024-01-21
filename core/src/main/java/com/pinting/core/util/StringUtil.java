package com.pinting.core.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil extends StringUtils {
	public static final String CHARSET_ENCODING = "UTF-8";

	public static boolean intern(String str1, String str2) {
		if (str1 == null)
			return str2 == null;

		str1 = str1.intern();

		return str1 == str2;
	}

	public static String propertyToFieldName(String str) {
		if (isEmpty(str))
			return str;

		if ((str.charAt(0) > 'A') && (str.charAt(0) < 'Z'))
			return str;

		if (Character.isUpperCase(str.charAt(0))) {
			str = Character.toString(Character.toLowerCase(str.charAt(0)))
					+ str.substring(1);
		}
		for (int i = 1; i < str.length(); ++i) {
			char a = str.charAt(i);
			if ((a < 'A') || (a > 'Z') || (str.charAt(i - 1) == '_'))
				continue;
			str = str.replace(String.valueOf(a), "_".concat(String.valueOf(a))
					.toLowerCase());
			++i;
		}

		return str;
	}

	public static String omit(String str, int num) {
		if (isBlank(str))
			return null;
		if (str.length() < num)
			return str;

		return (num > 0) ? str.substring(0, num) : str;
	}

	public static String limit(String str, int byteLength) {
		if (isBlank(str))
			return null;
		if (byteLength <= 0)
			return null;
		try {
			if (str.getBytes("UTF-8").length <= byteLength)
				return str;
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		StringBuffer buff = new StringBuffer();
		int index = 0;

		char[] arr = new char[1];
		while (byteLength > 0) {
			char c = str.charAt(index);
			arr[0] = c;
			if (!isChineseString(new String(arr))) {
				--byteLength;
			} else {
				--byteLength;
				--byteLength;
			}

			buff.append(c);
			++index;
		}

		buff.append("...");

		return buff.toString();
	}

	public static boolean isChineseString(String string) {
		if (isBlank(string))
			return false;

		String patternRange = "[\\u4E00-\\u9FA5]+";
		Pattern pattern = Pattern.compile(patternRange);
		Matcher matcher = pattern.matcher(string);

		return matcher.find();
	}

	public static String nullToString(String string) {
		return (isBlank(string)) ? "" : string;
	}

	public static String trimStr(String str) {
		return trimToEmpty(str).replaceAll("[\\r\\n]", "");
	}
}
