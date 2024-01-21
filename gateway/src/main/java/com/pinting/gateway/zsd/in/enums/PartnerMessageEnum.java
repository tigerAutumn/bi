package com.pinting.gateway.zsd.in.enums;

import com.pinting.gateway.enums.PTMessageEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码转换
 * @project gateway
 * @title PartnerMessageEnum.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public enum PartnerMessageEnum {

	LOANER_ADD_IS_EXIST("980001","930001","用户已存在"),

	;

	/**
	 *
	 * @param bizCode business返回码
	 * @param code	  本系统返回码
	 * @param message 返回信息描述
     */
	private PartnerMessageEnum(String bizCode, String code, String message){
		this.bizCode = bizCode;
		this.code = code;
		this.message = message;
	}

	private String bizCode;
	private String code;
	private String message;

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 *
	 * @param bizCode
	 * @return
	 */
	public static PTMessageEnum getPTMessageEnumByBizCode(String bizCode) {
		if (null == bizCode) {
			return null;
		}
		for (PartnerMessageEnum type : values()) {
			if (type.getBizCode().equals(bizCode.trim())){
				PTMessageEnum msgEnum = PTMessageEnum.getEnumByCode(type.getCode());
				if(msgEnum != null){
					return msgEnum;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static PartnerMessageEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (PartnerMessageEnum type : values()) {
			if (type.getCode().equals(code.trim()))
				return type;
		}
		return null;
	}
	
	/**
	 * 转出Map
	 * @return
	 */
	public static Map<String, String> toMap() {
		Map<String, String> enumDataMap = new HashMap<String, String>();
		for (PartnerMessageEnum type : values()) {
			enumDataMap.put(type.getBizCode(), type.getCode());
		}
		return enumDataMap;
	}
	
	
}
