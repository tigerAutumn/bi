package com.dafy.tools.qidian;

public class QiDianInConstant {
	//七店请求（通知）的交易码
	public static final String LOGIN = "login";//登录
	public static final String CUSTOMER_INFO_SYNC = "customerInfoSync";//客户信息同步
	public static final String ORDER_INFO_SYNC = "orderInfoSync";//客户信息同步

	
	//发给七店的响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
	
	public static final String RETURN_MSG_DECODE_FAIL = "message parse error";//报文解密失败
	public static final String RETURN_MSG_HASH_ERROR="message check error"; //报文校验失败
	public static final String RETURN_MSG_LOGIN_EXPIRED="login expired"; //登录超时
	public static final String RETURN_MSG_TOKEN_ERROR = "token error"; //token错误
	public static final String REUTRN_MSG_LOGIN_FAIL = "clientKey or clientSecret error";//clientKey 或 clientSecret不匹配 
	public static final String RETURN_MSG_LOGIN_SUCCESS = "login succ";//登录成功
	public static final String RETURN_MSG_RESULT_SUCCESS = "receive succ";//达飞通知成功
	public static final String RETURN_MSG_RESULT_FAIL = "receive fail";//达飞通知失败
	public static final String RETURN_MSG_RESULT_DATA_ERROR = "receive fail, message unmatch";//达飞通知失败，报文数据不匹配
	public static final String RETURN_MSG_RESULT_REPEAT = "repeat notice";//重复通知
	
}
