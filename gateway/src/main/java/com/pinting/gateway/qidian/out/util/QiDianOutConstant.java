package com.pinting.gateway.qidian.out.util;

public class QiDianOutConstant {

	//发给7贷服务器 交易码
	public static final String QIDIAN_LOGIN = "login";//登录
	public static final String CUSTOMER_INFO_SYNC = "customerInfoSync";//客户信息同步
	public static final String ORDER_INFO_SYNC = "orderInfoSync";//订单信息同步
	
	
	//7贷响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
}
