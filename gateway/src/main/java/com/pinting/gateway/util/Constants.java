package com.pinting.gateway.util;

public class Constants {
	//gateway服务模式
	public static final String GATEWAY_SERVER_MODE_DEV = "dev";//开发模式
	public static final String GATEWAY_SERVER_MODE_PROD = "prod";//生产模式
	public static final String GATEWAY_SERVER_MODE_TEST = "test";//测试模式

	//报文类前置 Dafy
	public static final String BUSINESS_DAFY_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.dafy.B2GResMsg_";//返回报文类前置
	public static final String DAFY2GATEWAY_MESSAGE_MODEL_PRE = "com.pinting.gateway.dafy.in.model.";//返回报文类前置(达飞为客户端)
	public static final String GATEWAY2DAFY_MESSAGE_MODEL_PRE = "com.pinting.gateway.dafy.out.model.";//返回报文类前置(达飞为服务端)

	public static final String HESSIAN_MESSAGE_MODEL_PRE = "com.pinting.gateway.hessian.message.dafy.model.";//hessian信息model
	//报文类前置CFCA
	public static final String GATEWAY2CFCA_MESSAGE_MODEL_PRE = "com.pinting.gateway.cfca.out.model.";//返回报文类前置(CFCA为服务端)
	public static final String CFCA_HESSIAN_MESSAGE_MODEL_PRE = "com.pinting.gateway.hessian.message.cfca.model.";//hessian信息model
	//报文类前置 Pay19
	public static final String BUSINESS_PAY19_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.pay19.B2GResMsg_";//返回报文类前置
	//报文类前置 baofoo
	public static final String BUSINESS_BAOFOO_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.baofoo.B2GResMsg_";//返回报文类前置
	//报文类前置 借款通知
	public static final String BUSINESS_LOAN_NOITCE_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.loan.B2GResMsg_";//返回报文类前置
	//报文类前置 融宝
	public static final String BUSINESS_REAPAL_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.reapal.B2GResMsg_";//返回报文类前置
	//报文类前置 希财
	public static final String BUSINESS_XICAI_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.xicai.B2GResMsg_";//返回报文类前置
	//报文类前置 有贝
	public static final String BUSINESS_YOUBEI_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.youbei.B2GResMsg_";//返回报文类前置
	//报文类前置 恒丰银行
	public static final String BUSINESS_HF_BANK_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.hfbank.B2GResMsg_";//返回报文类前置

	//报文类前置 7贷
	public static final String LOAN72GATEWAY_MESSAGE_MODEL_PRE = "com.pinting.gateway.loan7.in.model.";//返回报文类前置(7贷为客户端)
	public static final String GATEWAY2LOAN7_MESSAGE_MODEL_PRE = "com.pinting.gateway.loan7.out.model.";//返回报文类前置(7贷为服务端)
	public static final String BUSINESS_DEPLOAN7_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.loan7.B2GResMsg_";//返回报文类前置
	public static final String LOAN7_HESSIAN_MESSAGE_MODEL_PRE = "com.pinting.gateway.hessian.message.loan7.model.";//hessian信息model
	//报文类前置 钱报178APP
	public static final String BUSINESS_APP178_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.qb178.B2GResMsg_";//返回报文类前置
	//报文类前置 赞时贷
	public static final String BUSINESS_ZSD_NOITCE_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.zsd.B2GResMsg_";//返回报文类前置
		
	//报文类前置 七店
	public static final String QIDIAN2GATEWAY_MESSAGE_MODEL_PRE = "com.pinting.gateway.qidian.in.model.";//返回报文类前置(7贷为客户端)
	public static final String BUSINESS_QIDIAN_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.qidian.B2GResMsg_";//返回报文类前置
	public static final String QIDIAN_HESSIAN_MESSAGE_MODEL_PRE = "com.pinting.gateway.qidian.out.model.";//hessian信息model
	//希财请求需要校验的IP地址
	public static final String CSAI_AUTO_LOGIN_IP_ADDR1 = "210.73.220.214";
	public static final String CSAI_AUTO_LOGIN_IP_ADDR2 = "210.73.220.210";
	public static final String CSAI_AUTO_LOGIN_IP_ADDR3 = "183.129.222.138";//币港湾外网IP
	//public static final String CSAI_AUTO_LOGIN_IP_ADDR4 = "113.240.218.124";//希财开发人员对应IP
	
	public static final String REDIS_SUBSYS_GATEWAY = "_gateway";
	//三方系统渠道
	public static final String THIRD_SYS_CHANNEL_YUNDAI = "YUN_DAI";//云贷
	public static final String THIRD_SYS_CHANNEL_7DAI = "7_DAI";//7贷
	public static final String THIRD_SYS_CHANNEL_ZAN = "ZAN";//赞分期
	public static final String THIRD_SYS_CHANNEL_ZSD = "ZSD";//赞时贷

	//恒分返回的成功码
	public static final String BSRESCODE_DEP_SUCCESS = "10000";//交易成功
	
	//对账文件资金进出数据文件名前缀
	public static final String HF_FUNDDTACHECK_FILENAME_PREFIX = "hf_funddata";
	//对账文件账户余额数据文件名前缀
	public static final String HF_BALANCEINFO_FILENAME_PREFIX = "hf_balanceinfo";
	
	//恒丰回调通知返回码
	public static final String HFRESCODE_DEP_NOTICE_SUCCESS = "success";//通知成功
	public static final String HFRESCODE_DEP_NOTICE_FAIL = "fail";//通知失败
	
	//宝付错误码,错误信息格式
	public static final String BAOFOO_ERROR_NO = "error_no";
	public static final String BAOFOO_ERROR_INFO = "error_info";
	
	public static final String HF_FILE_NOT_EXIST = "文件不存在";//文件不存在
	public static final String HF_FILE_CREATE_FAIL = "创建文件或目录失败";//创建文件或目录失败
	
	//钱报178返回的成功码
	public static final String Qianbao178_SUCCESS_CODE = "0000";//交易成功

	//是否线下还款标志
	public static final String REPAY_OffLINE_FLAG_Y = "Y";//线下还款
	public static final String REPAY_OffLINE_FLAG_N = "N";//线上代扣还款
	
	//币港湾宝付通道标识（辅助）
	public static final String BGW_BAOFOO_ASSIST = "BAOFOO_ASSIST";  // 币港湾宝付通道标识（辅助）
	
	//线下还款入参标识
	public static final String REQ_IS_OFFLINE_Y = "Y";
}
