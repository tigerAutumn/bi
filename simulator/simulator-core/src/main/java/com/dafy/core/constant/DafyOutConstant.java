package com.dafy.core.constant;

public class DafyOutConstant {
	//发给币港湾服务器 交易码
	public static final String LOGIN = "login";//登录
	public static final String FINANCING_DETAIL_REPLY = "sysBuyProductNotice";//理财信息明细回复
	public static final String FINANCING_RETURN_MONEY = "sysReturnMoneyNotice";//理财回款
	
	//币港湾响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
}
