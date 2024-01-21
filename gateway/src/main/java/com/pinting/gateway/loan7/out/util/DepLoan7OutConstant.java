package com.pinting.gateway.loan7.out.util;

public class DepLoan7OutConstant {

	public static final String GET_LOAN_RELATION_NEW = "queryLoanRelationship";//债权关系查询 - 新

	
	//发给7贷服务器 交易码
	public static final String DEP_LOAN7_LOGIN = "login";//登录
	public static final String QUERY_BILL = "queryBill";//账单同步
	public static final String WAIT_FILL = "waitFill";//待补账通知
	public static final String REVENUE_SETTLE = "revenueSettle";//营收结算通知
	public static final String LOAN_RESULT_NOTICE = "loanResultNotice";//放款结果通知
	public static final String SIGN_RESULT_NOTICE = "signResultNotice";//借款协议签章结果通知
	public static final String REPAY_RESULT_NOTICE = "repayResultNotice"; //还款结果通知
	public static final String AGREEMENT_NOTICE = "agreementNotice"; //协议下载通知
	
	//7贷响应码
	public static final String RETURN_CODE_REFUSE = "REFU";//系统受理拒绝
	public static final String RETURN_CODE_ACCEPT = "ACCE";//系统受理（用于异步处理的场景）
	public static final String RETURN_CODE_FAIL = "FAIL";//系统受理失败
	public static final String RETURN_CODE_SUCCESS = "SUCC";//系统受理成功
	public static final String RETURN_CODE_EXPIRE = "EXPR";//登录（token）超时
}
