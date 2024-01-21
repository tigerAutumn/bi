package com.pinting.core.util;

import java.util.ResourceBundle;

public class MsgTranslateUtil {
	protected static ResourceBundle res;
	protected static final String MSGTRANSLATE_PROPERTIES_FILE = "msgtranslate";
	

	public static String get(String key) {
		return res.getString(key);
	}

	static {
		if (res == null)
			res = ResourceBundle.getBundle(MSGTRANSLATE_PROPERTIES_FILE);
	}
}
