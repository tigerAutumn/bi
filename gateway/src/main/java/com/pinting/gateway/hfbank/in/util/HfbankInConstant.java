package com.pinting.gateway.hfbank.in.util;

public class HfbankInConstant {
	//恒丰银行请求（通知）的交易码
	public static final String OUT_OF_ACCOUNT = "outOfAccount";//成标出账通知

	
	
	//发给恒丰银行的响应码
	public static final String RETURN_CODE_SUCCESS = "success";//成功
	public static final String RETURN_CODE_SIGN_DATA_ERROR = "verify signature";//验签失败
	public static final String RETURN_CODE_PARAMETER_EMPTY_ERROR = "parameter empty";//验签失败
	
}
