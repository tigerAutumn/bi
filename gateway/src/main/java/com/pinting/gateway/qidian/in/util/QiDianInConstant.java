package com.pinting.gateway.qidian.in.util;

public class QiDianInConstant {

	//七店请求（通知）的交易码
	public static final String LOGIN = "login";//登录
	public static final String FRANCHISEE_REGIST = "franchiseeRegist";//店主创建
	
	//发给七店的响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_MOBILE_FAIL = "CODE_ERROR";//验证码错误
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
	
	public static final String RETURN_MSG_DECODE_FAIL = "message parse error";//报文解密失败
	public static final String RETURN_MSG_HASH_ERROR="message check error"; //报文校验失败
	public static final String RETURN_MSG_LOGIN_EXPIRED="login expired"; //登录超时
	public static final String RETURN_MSG_TOKEN_ERROR = "token error"; //token错误
	public static final String RETURN_MSG_TOKEN_EMPTY_ERROR = "token empty"; //token为空
	public static final String REUTRN_MSG_LOGIN_FAIL = "clientKey or clientSecret error";//clientKey 或 clientSecret不匹配 
	public static final String REUTRN_MSG_LOGIN_CLIENTKEY_EMPTY_FAIL = "clientKey empty"; //clientKey为空
	public static final String REUTRN_MSG_LOGIN_CLIENTSECRET_EMPTY_FAIL = "clientSecret empty"; //clientSecret为空
	public static final String REUTRN_MSG_LOGIN_TOKEN_SET_FAIL = "token setting failed"; //token 保存redius失败
	public static final String RETURN_MSG_LOGIN_SUCCESS = "login succ";//登录成功
	public static final String RETURN_MSG_RESULT_SUCCESS = "receive succ";//七店通知成功
	public static final String RETURN_MSG_RESULT_FAIL = "receive fail";//七店通知失败
	public static final String RETURN_MSG_RESULT_DATA_ERROR = "receive fail, message unmatch";//七店通知失败，报文数据不匹配
	public static final String RETURN_MSG_RESULT_PARAMS_ERROR = "params error";//参数校验错误
	public static final String RETURN_MSG_RESULT_OVERRUN_ERROR = "list quantity exceeding limit";//参数校验错误
}
