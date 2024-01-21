package com.dafy.tools.loan7;

public class Loan7OutConstant {
	//发给7贷服务器 交易码
	public static final String LOGIN = "login";//登录
	public static final String REGISTER_CUSTOMER = "registerCustomer";//客户注册
	public static final String BIND_BANKCARD = "bindBankcard";//银行卡绑定
	public static final String BUY_PRODUCT="buyProduct";//购买
	public static final String CHECK_ACCOUNT="checkAccount";//资金对账
	public static final String GET_LOAN_RELATION_URL="getLoanRelationUrl";//债权关系查询 - 旧
	public static final String BASIC_INFORMATION="basicInformation";//基本信息接口
	public static final String CUSTOMER_WITHDRAW = "customerWithdraw";//用户提现
	public static final String SYS_WITHDRAW = "sysWithdraw"; //网新提现
	public static final String QUERY_WXACCOUNT_DETAIL = "queryWXAccountDetail"; //查询网新账户明细
	
	public static final String SYS_BATCH_BUY_PRODUCT = "sysBatchBuyProduct"; //系统发起批量理财购买
	public static final String GET_LOAN_RELATION_NEW = "queryLoanRelationship";//债权关系查询 - 新
	public static final String QUERY_REPAY_JNL = "queryRepayJnl";//借款人某笔借款的还款流水查询
	
	//7贷响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
}
