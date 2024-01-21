package com.pinting.core.json;

import java.util.Date;

import com.pinting.core.util.DateUtil;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonValueProcessorImpl implements JsonValueProcessor {

	public Object processArrayValue(Object value, JsonConfig arg1) {
		String[] obj = new String[0];

		if (value instanceof Date[]) {
			Date[] dates = (Date[]) (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; ++i) {
				obj[i] = DateUtil.format(dates[i]);
			}
		}

		return obj;
	}

	public Object processObjectValue(String key, Object value, JsonConfig arg2) {
		if (value instanceof Date) {
			return DateUtil.format((Date) value);
		}

		return value;
	}

}
