package com.dafy.core.constant;

public class DafyInConstant {
	//达飞请求（通知）的交易码
	public static final String LOGIN = "login";//登录
	public static final String FINANCING_BUY = "sysBatchBuyProduct";//理财购买
	
	//发给达飞的响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
	
	public static final String RETURN_MSG_DECODE_FAIL = "报文解密失败";//报文解密失败
	public static final String RETURN_MSG_HASH_ERROR="报文校验失败"; //报文校验失败
	public static final String RETURN_MSG_LOGIN_EXPIRED="登录超时"; //登录超时
	public static final String RETURN_MSG_TOKEN_ERROR = "token错误"; //token错误
	public static final String RETURN_MSG_CLIENT_KEY_ERROR = "clientKey标识错误"; //token错误
	public static final String RETURN_MSG_LOGIN_FAIL = "登录校验失败";//clientKey 或 clientSecret不匹配 
	public static final String RETURN_MSG_LOGIN_SUCCESS = "登录成功";//登录成功
	public static final String RETURN_MSG_RESULT_SUCCESS = "接收通知成功";//达飞通知成功
	public static final String RETURN_MSG_RESULT_FAIL = "接收通知失败";//达飞通知失败
	public static final String RETURN_MSG_RESULT_DATA_ERROR = "接收通知失败，报文数据不匹配";//达飞通知失败，报文数据不匹配
	
}
