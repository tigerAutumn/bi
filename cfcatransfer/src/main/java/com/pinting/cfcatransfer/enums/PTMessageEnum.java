package com.pinting.cfcatransfer.enums;

import java.util.HashMap;
import java.util.Map;

public enum PTMessageEnum {
	
	TRANS_SUCCESS("000000", "交易成功"),
	TRANS_ERROR("999999", "交易失败"),
	
	//一些公共类型异常，错误码以90开头6位数字，例：
	TRANSCODE_NOT_FOUND("900001", "交易码不符合规范"),
	DATA_VALIDATE_ERROR("900002", "数据校验失败："),
	
	//达飞相关类型异常，错误码以91开头6位数字，例：
	//...
	/** PFX证书保存失败 */
    PFX_SAVE_FAIL("910000", "PFX证书保存失败"),
    /** 获取PFX证书失败 */
    GET_PFX_FAIL("910001", "获取PFX证书失败"),
    /** 申请证书及下载执行失败 */
    APPLY_AND_DOWNLOAD_CERT_FAIL("910002", "申请证书及下载执行失败"),
    /** 申请证书P10失败 */
    APPLY_P10_FAIL("910003", "申请证书P10失败"),
    /** 制章失败 */
    MAKE_USER_SEAL_FAIL("910004", "制章失败"),
    /** PDF自动签章失败 */
    AUTO_SEAL_PDF_FAIL("910005", "PDF自动签章失败"),
    
	//19付通讯相关异常，错误码以92开头6位数字，例：
	RETURN_MSG_HASH_ERROR("920000","报文消息摘要校验失败"),
	RPC_EXCEPTION("920001","支付平台错误："),
	FTP_DOWNLOAD_FAIL("920002","FTP对账文件下载失败"),
	
	//其他需要的类型错误码，请自行添加
	//...
	
	//系统方面异常，错误码以99开头6位数字，例：
	SYSTEM_ERROR("990000", "系统异常")
	;
	
	private PTMessageEnum(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	private String code;
	private String message;
	
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
	 * @param code
	 * @return
	 */
	public static PTMessageEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (PTMessageEnum type : values()) {
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
		for (PTMessageEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
	
}
