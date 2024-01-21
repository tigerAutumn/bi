package com.pinting.gateway.cfca.out.util;

public class CFCAOutConstant {
	//企业印模： hzbgwbm_  bigangwan123_
	//发给达飞服务器 交易码
	public static final String USER_CERT = "userCert";//
	public static final String USER_SEAL = "userSeal";//
	public static final String SEAL_AUTO_PDF = "sealAutoPdf";//
	
	//达飞响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
}
