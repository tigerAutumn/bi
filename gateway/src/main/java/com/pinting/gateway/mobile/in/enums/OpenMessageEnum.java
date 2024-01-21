package com.pinting.gateway.mobile.in.enums;

import java.util.HashMap;
import java.util.Map;

public enum OpenMessageEnum {
	
	//app数据验证异常，以60开头
	MOBILE_OR_PASSWORD_EMPTY("600001", "手机号或密码为空"),
	VERIFYCODE_ERROR("600002", "验证码发送失败，请重试"),
	BALANCE_RECHAGE_AMOUNT_EMPTY("600003", "充值金额不能为空"),
	BALANCE_RECHAGE_BANK_EMPTY("600004", "请选择银行"),
	BALANCE_RECHAGE_VERIFYCODE_EMPTY("600005", "验证码不能为空"),
	BALANCE_RECHAGE_ORDERNO_EMPTY("600006", "正式下单订单号不能为空"),
	REQUEST_PARAM_EMPTY("600007", "请求的部分参数为空"),
	PASSWORD_NOT_NORM("600008", "密码格式不正确"),
	USER_TYPE_ERROR("600009", "用户类型错误"),
	MOBILE_IS_EXIT("600010", "手机号已注册"),
	MOBILE_NOT_NORM("600011", "手机号格式不正确"),
	VERIFYCODE_NOT_NORM("600012", "验证码格式不正确"),
	RECOMMENT_NOT_NORM("600013", "邀请码格式不正确"),
	CARDNO_NOT_NORM("600014", "银行卡号格式不正确"),
	MONEY_IS_NOT_SPECIFICATION("600015", "购买金额输入不规范"),
	USERNAME_NOT_NORM("600016", "姓名超过长度限制"),
	IDCARD_NOT_NORM("600017", "身份证格式不正确"),
	PREPARE_ERROR("600018", "预下单失败，用户不存在或银行卡已失效"),
	FORMAL_ERROR("600019", "正式下单失败，用户不存在或银行卡已失效"),
	VERIFYCODE_INPUT_ERROR("600020", "验证码输入错误"),
	USER_ID_EMPTY("600021", "用户ID不能为空"),
	RECHARGE_AMOUNT_IS_NOT_SPECIFICATION("600022", "充值金额不规范"),
	USER_NAME_IS_EMPTY("600023", "姓名不能为空"),
	SUB_ACCOUNT_IS_NOT_EXIT("600024", "子账户信息异常"),
	FIND_PAY_PASSWORD_ERROR("600025", "找回交易密码失败！"),
	LENGTH_LARGE_ERROR("600026", "意见反馈不能超过500字符"),
	RESEND_CODE_ERROR("600027", "重发验证码失败！"),
	QUESTIONNAIRE_SCORE_ERROR("600028", "风险测评分数不正确！"),
	EMPTY_PLATFORM_TYPE("600029", "数据类型不能为空！"),
	;
	
	private OpenMessageEnum(String code, String message){
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
	public static OpenMessageEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (OpenMessageEnum type : values()) {
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
		for (OpenMessageEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
	
}
