package com.pinting.gateway.enums;

import java.util.HashMap;
import java.util.Map;

public enum PTMessageEnum {
	
	TRANS_SUCCESS("000000", "交易成功"),
	TRANS_ERROR("999999", "交易失败"),
	
	//一些公共类型异常，错误码以90开头6位数字，例：
	TRANSCODE_NOT_FOUND("900001", "交易码不符合规范"),
	DATA_VALIDATE_ERROR("900002", "数据校验失败："),
	TOKEN_EXPIRE("900003", "TOKEN超时，请重新获取"),
	CLIENT_KEY_ERROR("900004", "客户端编码错误"),
	BAD_REQUEST ("900005", "非法请求！"),
	CONNECTION_ERROR("900006","通讯异常"),
	//达飞相关类型异常，错误码以91开头6位数字，例：
	//...
	DAFY_LOGIN_FAIL("910000", "登录达飞失败"),
	DAFY_REGISTER_FAIL("910001", "客户达飞注册失败："),
	DAFY_BINDCARD_FAIL("910002", "用户达飞卡绑定失败："),
	DAFY_BUYPRODUCT_FAIL("910003", "购买产品失败："),
	DAFY_BUYPRODUCT_REFU("910004", "购买拒绝受理"),
	DAFY_CHECKACCOUNT_FAIL("910005", "资金对账查询失败："),
	DAFY_QUERYLOANRELATION_FAIL("910006", "查询达飞债权关系失败："),
	DAFY_BONUS_WITHDRAW_FAIL("910007", "奖励金提现失败："),
	DAFY_SYS_WITHDRAW_FAIL("910008", "网新提现失败："),
	DAFY_QUERY_SYS_ACCOUNT_DETAIL_FAIL("910009", "网新账户明细查询失败："),
	DAFY_QUERY_REPAL_JNL("910010", "查询达飞还款流水失败："),
	DAFY_CFCA_USER_CERT_ERROR("910011", "CFCA获取客户证书失败"),
	DAFY_CFCA_USER_SEAL_ERROR("910012", "CFCA客户制章失败"),
	DAFY_CFCA_PDF_AUTO_SEAL_ERROR("910013", "CFCA自动签章失败"),
	DAFY_QUERY_BILL_ERROR("910014", "账单同步查询失败"),
	DAFY_WAIT_FILL_ERROR("910015", "待补账通知失败"),
	DAFY_REVENUE_SETTLE_ERROR("910016", "营收结算通知失败"),
	DAFY_SIGN_RESULT_NOTICE_ERROR("910017", "借款协议签章结果通知失败"),
	DAFY_REPAY_RESULT_NOTICE_FAIL("910018","还款结果通知失败"),
	DAFY_AGREEMENT_NOTICE_FAIL("910019","协议下载通知失败"),
	DAFY_LOAN_RESULT_NOTICE_FAIL("910020","借款结果通知失败"),

	//19付、融宝通讯相关异常，错误码以92开头6位数字，例：
	RETURN_MSG_HASH_ERROR("920000","报文消息摘要校验失败"),
	RPC_EXCEPTION("920001","支付平台错误："),
	FTP_DOWNLOAD_FAIL("920002","FTP对账文件下载失败"),
	BUS_PROCESSING("920003","正在处理中"),
	BIZ_PAY_FAIL("920004","支付失败"),
	VERIFY_SIGNATURE_ERROR("920005","验证签名失败"),
	PARAMETER_CHECK_RESPCODE_ERROR("920006","缺少resp_code参数"),

	//借款相关异常，错误码以93开头6位数字
	LOANER_ADD_ERROR("930000","登记借款人失败："),
	LOANER_ADD_IS_EXIST("930001","借款人已存在"),
	LOAN_NOTICE_FAIL("930002","通知失败"),
	
	//信息发送相关类型异常，错误码以94开头6位数字，例：(4表示信息发送，1表示手机，2表示邮箱)
	MOBILE_CODE_WRONG_ERROR("941003", "手机验证码不正确，请重新验证！"),

	//恒丰银行相关异常
	HFBANK_PUBLISH_FAIL("951001", "存管标的发布失败"),
	HFBANK_BATCH_EXT_BUY_FAIL("951002", "批量投标失败"),
	HFBANK_COMPENSATE_REPAY_FAIL("951003", "标的代偿（委托）还款失败"),
	HFBANK_REPAY_COMPENSATE_FAIL("951004", "借款人还款代偿（委托）失败"),
	HFBANK_ESTABLISH_OR_ABANDON_FAIL("951005", "标的成废失败"),
	HFBANK_BATCH_REGIST_EXT_FAIL("951006", "开通存管账户失败"),
	HFBANK_BATCH_REGIST_EXT3_FAIL("951007", "批量开户(实名认证)失败"),
	HFBANK_BATCH_UPDATE_CARD_EXT_FAIL("951008", "换绑卡失败 "),
	HFBANK_GET_BINK_CARD_CODE2_FAIL("951009", "短验绑卡申请 "),
	HFBANK_ADD_USER2_FAIL("951010", "短验绑卡确认 "),
	HFBANK_GATEWAY_RECHARGE_FAIL("951011", "网关充值请求 "),
	HFBANK_DIRECT_QUICK_FAIL("951012", "快捷充值预下单失败"),
	HFBANK_DIRECT_QUICK_CONFIRM_FAIL("951013", "快捷充值确认下单失败"),
	HFBANK_BORROW_CUT_REPAY_FAIL("951014", "借款人扣款还款（代扣）"),
	HFBANK_BATCH_WITHDRAW_EXT_FAIL("951015", "会员提现失败 "),
	HFBANK_QUERY_ORDER_FAIL("951016", "订单状态查询失败 "),
	HFBANK_QUERY_ACCOUNT_FAIL("951017", "资金余额查询失败 "),
	HFBANK_GET_ACCOUNT_NBALACE_FAIL("951018", "账户余额明细查询失败"),
	HFBANK_QUERY_PRODUCT_BALANCE_FAIL("951019", "标的账户余额查询失败"),
	HFBANK_CHARGE_OFF_FAIL("951020", "标的出账失败"),
	HFBANK_MODIFY_PAY_OUT_ACCT_FAIL("951021", "标的出账信息修改失败"),
	HFBANK_TRANSFER_DEBT_FAIL("951022", "标的转让失败"),
	HFBANK_BATCH_EXT_REPAY_FAIL("951023", "借款人批量还款失败"),
	HFBANK_PROD_REPAY_FAIL("951024", "标的还款失败"),
	HFBANK_PLATFORM_TRANSFER_FAIL("951025", 		"平台转账个人失败"),
	HFBANK_PLATFORM_ACCOUNT_CONVERSE_FAIL("951026", "平台不同账户转账失败"),
	HFBANK_PLATFORM_WITHDRAW_FAIL("951027",			"平台提现失败"),
	HFBANK_PLATFORM_CHARGE_FAIL("951028",			"平台充值失败"),
	HFBANK_PROD_RECHARGE_FAIL("951029", "用户充值失败"),
	HFBANK_PROD_WITHDRAW_FAIL("951030", "用户提现失败"),
	HFBANK_FUNDDATA_CHECK_FAIL("951031", "对账文件资金进出数据失败"),
	HFBANK_BALANCE_INFO_FAIL("951032", "对账文件账户余额数据失败"),
	HFBANK_QUERY_LIQUIDATION_FAIL("951033", "清算状态查询失败"),
	HFBANK_GET_FUNDLIST_FAIL("951034", "资金变动明细查询失败"),
	HFBANK_BIND_CARD_FAIL("951035", "开通存管账户失败"),
	HFBANK_UPDATE_PLAT_ACCT_FAIL("951036", "客户信息变更失败"),


	//钱报178 APP资产平台接入
	QB178_PARAMETER_EMPTY_ERROR("940000", "请求参数为空"),
	QB178_SIGN_DATA_ERROR("940001", "验签失败"),
	QB178_UPDATE_REPAY_PLAN_NOTICE_ERROR("940002", "更新还款计划状态通知失败"),
	QB178_ORDER_NOTICE_ERROR("940003", "订单通知失败"),
	QB178_UPDATE_PRODUCT_INFO_ERROR("940004", "更新产品状态失败"),
	QB178_QUERY_POSITION_NO_PRODUCT_CODE("940005","查询持仓余额入参产品编码不能为空"),
	QB178_UPDATE_USER_INFO_ERROR("940006", "更新用户状态失败"),
	
	//7贷
	LOAN7_LOGIN_FAIL("950000", "登录7贷失败"),
	LOAN7_LOAN_RESULT_NOTICE_ERROR("950001", "借款结果通知失败"),
	LOAN7_QUERY_BILL_ERROR("950002", "账单同步查询失败"),
	LOAN7_WAIT_FILL_ERROR("9500035", "待补账通知失败"),
	LOAN7_REVENUE_SETTLE_ERROR("950004", "营收结算通知失败"),
	LOAN7_SIGN_RESULT_NOTICE_ERROR("950005", "借款协议签章结果通知失败"),
	LOAN7_AGREEMENT_NOTICE_FAIL("950006","协议下载通知失败"),
	LOAN7_REPAY_RESULT_NOTICE_ERROR("950007", "还款结果通知失败"),
	//七店
	QIDIAN_LOGIN_FAIL("960000", "登录七店失败"),
	QIDIAN_CUSTOMER_INFO_SYNC_ERROR("960001", "用户信息同步通知失败"),
	QIDIAN_ORDER_INFO_SYNC_ERROR("960002", "订单信息同步通知失败"),

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
