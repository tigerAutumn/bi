package com.pinting.gateway.dafy.in.util;

public class DafyInConstant {
	//达飞请求（通知）的交易码
	public static final String LOGIN = "login";//登录
	public static final String REAL_CERTIFICATE_RESULT = "realCertificateResult";//绑卡实名认证通知
	public static final String BIND_CARD_RESULT = "bindBankcardResult";//银行卡绑定结果通知
	public static final String BUY_PRODUCT_RESULT = "buyProductResult";//购买产品结果通知
	public static final String RECEIVE_MONEY_NOTICE = "receiveMoneyNotice";//回款结果通知
	public static final String SYS_WITHDRAW_RESULT = "sysWithdrawResult"; //系统提现结果通知
	public static final String CUSTOMER_WITHDRAW_RESULT = "customerWithdrawResult";//用户提现结果通知
	public static final String CUSTOMER_WITHDRAW_CHECK = "customerWithdrawCheck";//用户提现验证
	public static final String SYS_BUY_PRODUCT_NOTICE = "sysBuyProductNotice";//系统购买产品结果通知
	public static final String SYS_RETURN_MONEY_NOTICE = "sysReturnMoneyNotice";//系统回款通知
	//飞请求（通知）的交易码--自主放款相关
	public static final String QUERY_DAILY_AMOUNT = "queryDailyAmount";//系统回款通知
	public static final String APPLY_LOAN = "applyLoan";//放款
	public static final String QUERY_LOAN_RESULT = "queryLoanResult";//放款结果查询
	public static final String PUSH_BILL = "pushBill";//账单推送
	public static final String QUERY_SIGN_RESULT = "querySignResult";//协议签章结果查询
	public static final String ACTIVE_REPAY_PRE = "activeRepayPre";//主动还款预下单
	public static final String ACTIVE_REPAY_CONFIRM = "activeRepayConfirm";//主动还款确认下单
	public static final String CUT_REPAY_CONFIRM = "cutRepayConfirm";//代扣还款
	public static final String QUERY_REPAY_RESULT = "queryRepayResult";//还款结果查询
	public static final String LATE_REPAY = "lateRepay";//代偿通知
	public static final String FILL_FINISH = "fillFinish";//补账完成通知
	public static final String AGREEMENT_NOTICE = "agreementNotice";//协议下载地址查询
	public static final String ACTIVE_REPAY_SMS_CODE_REPEAT = "activeRepaySmsCodeRepeat";//还款预下单重发短信验证码
	
	
	
	//发给达飞的响应码
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
	public static final String RETURN_MSG_RESULT_SUCCESS = "receive succ";//达飞通知成功
	public static final String RETURN_MSG_RESULT_FAIL = "receive fail";//达飞通知失败
	public static final String RETURN_MSG_RESULT_DATA_ERROR = "receive fail, message unmatch";//达飞通知失败，报文数据不匹配
	public static final String RETURN_MSG_RESULT_PARAMS_ERROR = "params error";//参数校验错误
}
