package com.pinting.business.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 亿美通用错误码
 * @author bianyatian
 * @2016-10-29 上午11:15:10
 */
public enum SmsErrorCode4EMayEnum {

	EM_101("EM:101", "黑名单"),
	EM_103("EM:103", "黑字典"),
	EM_106("EM:106", "签名未报备失败"),
	EM_104("EM:104", "无签名"),
	EM_105("EM:105", "无签名"),
	EM_2001("EM:2001", "双签名"),
	EM_001("EM:001", "重复过滤,十分钟三条"),
	;
	private SmsErrorCode4EMayEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	private String code;

	private String description;

	private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	static {
		for (SmsErrorCode4EMayEnum s : EnumSet.allOf(SmsErrorCode4EMayEnum.class)) {
			Map<String, Object> lookup = new HashMap<String, Object>();
			lookup.put("code", s.getCode());
			lookup.put("description", s.getDescription());
			list.add(lookup);
		}
	}

	public static List<Map<String, Object>> getMapList() {
		return list;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
