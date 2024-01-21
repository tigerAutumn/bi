package com.pinting.core.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RegExpUtil {

	protected static ResourceBundle res;
	protected static final String REGEXP_PROPERTIES_FILE = "regexp";
	

	public static String get(String key) {
		return res.getString(key);
	}

	static {
		if (res == null)
			res = ResourceBundle.getBundle(REGEXP_PROPERTIES_FILE);
	}

}
