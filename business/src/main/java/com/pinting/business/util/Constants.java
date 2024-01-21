package com.pinting.business.util;

public class Constants {
	//平台系统名称
	public static final String REDIS_SUBSYS_BUSINESS = "_business";
	//报文类前置
	public static final String SITE_MESSAGE_RESMSG_PRE = "com.pinting.business.hessian.site.message.ResMsg_";//返回报文类前置
	public static final String MANAGE_MESSAGE_RESMSG_PRE = "com.pinting.business.hessian.manage.message.ResMsg_";//返回报文类前置
	public static final String DAFY_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.dafy.G2BResMsg_";//返回报文类前置
	public static final String PAY19_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.pay19.G2BResMsg_";//返回报文类前置
	public static final String BAOFOO_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.baofoo.G2BResMsg_";//返回报文类前置
	public static final String REAPAL_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.reapal.G2BResMsg_";//返回报文类前置
	public static final String XICAI_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.xicai.G2BResMsg_";//返回报文类前置
	public static final String LOAN_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.loan.G2BResMsg_";//返回报文类前置
	public static final String HF_BANK_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.hfbank.G2BResMsg_";//返回报文类前置
	public static final String QB178_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.qb178.G2BResMsg_";//返回报文类前置
	public static final String ZSD_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.zsd.G2BResMsg_";//返回报文类前置
	public static final String DEPLOAN7_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.loan7.G2BResMsg_";//返回报文类前置
	public static final String QIDIAN_BUSINESS_MESSAGE_RESMSG_PRE = "com.pinting.gateway.hessian.message.qidian.G2BResMsg_";//返回报文类前置
	//是否实名认证
	public static final int IS_BIND_NAME_YES = 1;//是
	public static final int IS_BIND_NAME_NO = 2;//否

	//用户信息表中，是否绑定银行卡
	public static final int IS_BIND_BANK_YES = 1;//是
	public static final int IS_BIND_BANK_NO = 2;//否

	//该银行卡是否是常用卡
	public static final int IS_FIRST_BANK_YES = 1;//是
	public static final int IS_FIRST_BANK_NO = 2;//否

	//银行状态
	public static final int BANK_STATUS_NORMAL = 1; // 正常
	public static final int BANK_STATUS_FORBID = 2; // 禁止

	//手机/邮件验证码发送失败信息
	public static final String SEND_CODE_ERROR="sending error";//发送失败
	public static final String SEND_CODE_VERIFY_OR_EXPIRED="verify or expired";//验证码已经过期或者已经验证过


	//产品代码
	public static final String PRODUCT_CODE_3_80 = "012";//3月理财产品（8%）
	public static final String PRODUCT_CODE_6_90 = "013";//6月理财产品（9%）
	public static final String PRODUCT_CODE_12_132 = "014";//12月理财产品（13.2%）
	public static final String PRODUCT_CODE_1_70 = "016";//1月普通理财产品（7%）
	public static final String PRODUCT_CODE_1_120 = "019";//1月体验理财产品（12%）
	public static final String PRODUCT_CODE_1_75 = "020";//1月理财产品（7.5%）
	public static final String PRODUCT_CODE_3_85 = "021";//3月理财产品（8.5%）
	public static final String PRODUCT_CODE_6_105 = "022";//6月理财产品（10.5%）
	public static final String PRODUCT_CODE_9_115 = "023";//9月理财产品（11.5%）
	public static final String PRODUCT_CODE_1_132 = "024";//1月体验理财产品（13.2%）
	public static final String PRODUCT_CODE_1_90 = "027";//1月理财产品
	public static final String PRODUCT_CODE_3_100 = "028";//3月理财产品
	public static final String PRODUCT_CODE_6_120 = "029";//6月理财产品
	public static final String PRODUCT_CODE_12_140 = "030";//十二月理财产品
	public static final String PRODUCT_CODE_1_100 = "031";//1月理财产品
	public static final String PRODUCT_CODE_3_110 = "032";//3月理财产品
	public static final String PRODUCT_CODE_6_130 = "033";//6月理财产品

	//账户编号前缀
	public static final int ACCOUNT_PREFIX = 1001;//主账户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_JSH = 2001;//结算户
	public static final int SUB_ACCOUNT_PREFIX_CP_THREE_80 = 2002;//三月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_SIX_90 = 2003;//六月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_YEAR_132 = 2004;//十二月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_MONTH_70 = 2005;//1月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_MONTH_120 = 2006;//1月体验子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_MONTH_75 = 2007;//1月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_THREE_85 = 2008;//3月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_SIX_105 = 2009;//6月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_NINE_115 = 2010;//9月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_MONTH_132 = 2011;//1月体验子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_MONTH_90 = 2012;//1月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_THREE_100 = 2013;//3月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_SIX_120 = 2014;//6月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_YEAR_140 = 2015;//十二月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_MONTH_100 = 2016;//1月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_THREE_110 = 2017;//3月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_CP_SIX_130 = 2018;//6月子账户产品户编号前缀
	public static final int SUB_ACCOUNT_PREFIX_JLJ = 3001; // 奖励金
	public static final int SUB_ACCOUNT_PREFIX_DEP_JSH = 4001; // 存管结算户

	//账户类型
//	public static final Integer ACCOUNT_TYPE_ZZH=1;//主账户
//	public static final Integer ACCOUNT_TYPE_JSH=2;//结算户
//	public static final Integer ACCOUNT_TYPE_CPH_THREE=3;//三月子账户产品户
//	public static final Integer ACCOUNT_TYPE_CPH_SIX=4;//六月子账户产品户
//	public static final Integer ACCOUNT_TYPE_CPH_YEAR=5;//十二月子账户产品户
//	public static final Integer ACCOUNT_TYPE_CPH_MONTH=6;//1月子账户产品户
//	public static final Integer ACCOUNT_TYPE_CPH_MONTH_SPECIAL=7;//1月体验子账户产品户

	//投资人账户类型
	public static final String PRODUCT_TYPE_JSH = "JSH";//结算户
	public static final String PRODUCT_TYPE_REG = "REG";//定存宝(投资金额固定不变，一次性还款)
	public static final String PRODUCT_TYPE_AUTH = "AUTH";//站岗户（赞分期）
	public static final String PRODUCT_TYPE_REG_D = "REG_D";//分期产品户(投资金额变动，允许多次回款)
	public static final String PRODUCT_TYPE_JLJ = "JLJ";//奖励金
	public static final String PRODUCT_TYPE_DEP_JSH = "DEP_JSH";//余额子账户
	public static final String PRODUCT_TYPE_AUTH_YUN = "AUTH_YUN";//站岗户（云贷）
	public static final String PRODUCT_TYPE_DIFF = "DIFF";//补差子账户
	public static final String PRODUCT_TYPE_RED = "RED";//红包子账户
	public static final String PRODUCT_TYPE_AUTH_ZSD = "AUTH_ZSD";//站岗户（赞时贷）
	public static final String PRODUCT_TYPE_DIFF_ZSD = "DIFF_ZSD";//补差子账户（赞时贷）
	public static final String PRODUCT_TYPE_RED_ZSD = "RED_ZSD";//红包子账户（赞时贷）
	public static final String PRODUCT_TYPE_AUTH_7 = "AUTH_7";//站岗户（7贷）
	public static final String PRODUCT_TYPE_DIFF_7 = "DIFF_7";//补差子账户（7贷）
	public static final String PRODUCT_TYPE_RED_7 = "RED_7";//红包子账户（7贷）
	public static final String PRODUCT_TYPE_AUTH_FREE = "AUTH_FREE";//站岗户（自由资金）
	public static final String PRODUCT_TYPE_DIFF_FREE = "DIFF_FREE";//补差子账户（自由资金）
	public static final String PRODUCT_TYPE_RED_FREE = "RED_FREE";//红包子账户（自由资金）


	//系统子账户户头类型
	public static final String SYS_ACCOUNT_JSH = "JSH";//结算户
	public static final String SYS_ACCOUNT_USER = "USER";//用户
	public static final String SYS_ACCOUNT_REG_YUN = "REG_YUN";//产品户云贷（新）
	public static final String SYS_ACCOUNT_RETURN_YUN = "RETURN_YUN";//回款户云贷（新）
	public static final String SYS_ACCOUNT_REG_7 = "REG_7";//产品户7贷（新）
	public static final String SYS_ACCOUNT_RETURN_7 = "RETURN_7";//回款户7贷（新）
	public static final String SYS_ACCOUNT_RED_PACKET = "REDPACKET";//红包户
	public static final String SYS_ACCOUNT_AUTH_ZAN = "AUTH_ZAN";//站岗户赞分期
	public static final String SYS_ACCOUNT_REG_ZAN = "REG_ZAN";//产品户赞分期
	public static final String SYS_ACCOUNT_RETURN_ZAN = "RETURN_ZAN";//回款户赞分期
	public static final String SYS_ACCOUNT_MARGIN_ZAN = "MARGIN_ZAN";//风险保证金账户赞分期
	public static final String SYS_ACCOUNT_REVENUE_ZAN = "REVENUE_ZAN";//营收账户赞分期
	public static final String SYS_ACCOUNT_BGW_REVENUE_4_ZAN = "BGW_REVENUE_4_ZAN";//币港湾对赞分期营收账户
	public static final String SYS_ACCOUNT_BADLOANS_ZAN = "BADLOANS_ZAN";//坏账户赞分期
	//public static final String SYS_ACCOUNT_REVENUE = "REVENUE";//币港湾营收账户
	public static final String SYS_ACCOUNT_FEE = "FEE";//币港湾手续费账户
	public static final String SYS_ACCOUNT_REVENUE_YUN_DEP = "THD_REVENUE_YUN";//云贷营收户（日结给云贷）
	public static final String SYS_ACCOUNT_BGW_REVENUE_4_YUN_DEP = "BGW_REVENUE_4_YUN_DEP";//币港湾对云贷增计划营收户
	public static final String SYS_ACCOUNT_FEE_YUN_DEP = "DEP_HEADFEE_YUN";//云贷手续费户(云贷砍头息，日结给云贷)

	public static final String SYS_ACCOUNT_BGW_USER = "BGW_USER";//币港湾体系-存管用户余额户
	public static final String SYS_ACCOUNT_BGW_AUTH_ZAN = "BGW_AUTH_ZAN";//币港湾体系-存管赞分期站岗户
	public static final String SYS_ACCOUNT_BGW_AUTH_YUN = "BGW_AUTH_YUN";//币港湾体系-存管云贷站岗户
	public static final String SYS_ACCOUNT_BGW_REG_ZAN = "BGW_REG_ZAN";//币港湾体系-存管赞分期产品户
	public static final String SYS_ACCOUNT_BGW_REG_YUN = "BGW_REG_YUN";//币港湾体系-存管云贷产品户
	public static final String SYS_ACCOUNT_BGW_RETURN_ZAN = "BGW_RETURN_ZAN";//币港湾体系-存管赞分期回款户
	public static final String SYS_ACCOUNT_BGW_RETURN_YUN = "BGW_RETURN_YUN";//币港湾体系-存管云贷回款户
	public static final String SYS_ACCOUNT_THD_MARGIN_ZAN = "THD_MARGIN_ZAN";//币港湾宝付体系-赞分期保证金子账户
	public static final String SYS_ACCOUNT_THD_REVENUE_ZAN = "THD_REVENUE_ZAN";//币港湾宝付体系-赞分期营收子账户
	public static final String SYS_ACCOUNT_THD_REVENUE_YUN = "THD_REVENUE_YUN";//币港湾宝付体系-云贷营收子账户
	public static final String SYS_ACCOUNT_THD_BGW_REVENUE_ZAN = "THD_BGW_REVENUE_ZAN";//币港湾宝付体系-币港湾对赞分期营收子账户
	public static final String SYS_ACCOUNT_THD_BGW_REVENUE_ZAN_TEMP = "THD_BGW_REVENUE_ZAN_TEMP";//币港湾宝付体系-币港湾对赞分期营收子账户_临时户
	public static final String SYS_ACCOUNT_THD_BGW_REVENUE_YUN = "THD_BGW_REVENUE_YUN";//币港湾宝付体系-币港湾对云贷营收子账户
	public static final String SYS_ACCOUNT_THD_REPAY = "THD_REPAY";//币港湾宝付体系-还款资金子账户
	public static final String SYS_ACCOUNT_DEP_JSH = "DEP_JSH";//存管体系-自有子账户
	public static final String SYS_ACCOUNT_DEP_REDPACKET = "DEP_REDPACKET";//存管体系-红包子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN = "DEP_BGW_REVENUE_ZAN";//存管体系-币港湾对赞分期营收子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_YUN = "DEP_BGW_REVENUE_YUN";//存管体系-币港湾对云贷营收子账户
	public static final String SYS_ACCOUNT_DEP_HEADFEE_YUN = "DEP_HEADFEE_YUN";//存管体系-砍头息子账户
	public static final String SYS_ACCOUNT_DEP_OTHER_FEE = "DEP_OTHER_FEE";//存管体系-其他费用户
	public static final String SYS_ACCOUNT_DEP_ADVANCE = "DEP_ADVANCE";//存管体系-垫付金子账户

	public static final String SYS_ACCOUNT_BGW_AUTH_ZSD = "BGW_AUTH_ZSD";//币港湾体系-存管赞时贷站岗户
	public static final String SYS_ACCOUNT_BGW_REG_ZSD = "BGW_REG_ZSD";//币港湾体系-存管赞时贷产品户
	public static final String SYS_ACCOUNT_BGW_RETURN_ZSD = "BGW_RETURN_ZSD";//币港湾体系-存管赞时贷回款户
	public static final String SYS_ACCOUNT_THD_REVENUE_ZSD = "THD_REVENUE_ZSD";//币港湾宝付体系-赞时贷营收子账户
	public static final String SYS_ACCOUNT_THD_MARGIN_ZSD = "THD_MARGIN_ZSD";//币港湾宝付体系-赞时贷保证金子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD = "DEP_BGW_REVENUE_ZSD";//存管体系-币港湾对赞时贷营收子账户
	public static final String SYS_ACCOUNT_DEP_HEADFEE_ZSD = "DEP_HEADFEE_ZSD";//存管体系-币港湾对赞时贷砍头息子账户
	public static final String SYS_ACCOUNT_THD_BGW_REVENUE_ZSD = "THD_BGW_REVENUE_ZSD";//币港湾宝付体系-币港湾对赞时贷营收子账户

	public static final String SYS_ACCOUNT_BGW_AUTH_7 = "BGW_AUTH_7";//币港湾体系-存管7贷站岗户
	public static final String SYS_ACCOUNT_BGW_REG_7 = "BGW_REG_7";//币港湾体系-存管7贷产品户
	public static final String SYS_ACCOUNT_BGW_RETURN_7 = "BGW_RETURN_7";//币港湾体系-存管7贷回款户
	public static final String SYS_ACCOUNT_THD_REVENUE_7 = "THD_REVENUE_7";//币港湾宝付体系-7贷营收子账户
	public static final String SYS_ACCOUNT_THD_MARGIN_7 = "THD_MARGIN_7";//币港湾宝付体系-7贷保证金子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_7 = "DEP_BGW_REVENUE_7";//存管体系-币港湾对7贷营收子账户
	public static final String SYS_ACCOUNT_DEP_HEADFEE_7 = "DEP_HEADFEE_7";//存管体系-币港湾对7贷砍头息子账户
	public static final String SYS_ACCOUNT_THD_BGW_REVENUE_7 = "THD_BGW_REVENUE_7";//币港湾宝付体系-币港湾对7贷营收子账户

	public static final String SYS_ACCOUNT_BGW_AUTH_FREE = "BGW_AUTH_FREE";//币港湾体系-存管自由资金站岗户
	public static final String SYS_ACCOUNT_BGW_REG_FREE = "BGW_REG_FREE";//币港湾体系-存管自由资金产品户
	public static final String SYS_ACCOUNT_BGW_RETURN_FREE = "BGW_RETURN_FREE";//币港湾体系-回款户

	public static final String SYS_ACCOUNT_LN_USER_BALANCE = "LN_USER_BALANCE";//借款人余额

	//借款人账户类型
	public static final String LOAN_ACT_TYPE_JSH = "JSH";//结算户
	public static final String LOAN_ACT_TYPE_INSTALMENT = "INSTALMENT";//分期户
	public static final String LOAN_ACT_TYPE_DEP_JSH = "DEP_JSH";//存管体系余额子账户

	//平台转账个人原因类型
	public static final String SYS_ACCOUNT_CASETYPE_PLATFORM_PRESENTATION = "1";//奖励金发放-平台赠送
	public static final String SYS_ACCOUNT_CASETYPE_DEDUCT_BONUS = "2";//奖励金发放-提成奖励
	public static final String SYS_ACCOUNT_CASETYPE_RETURN_COMMISSIONFEE = "3";//手续费返还-返还手续费
	public static final String SYS_ACCOUNT_CASETYPE_REDPACKET = "4";//现金红包

	// 用于记录余额快照
	public static final String SYS_ACC_RED_YUN = "RED_YUN";//云贷产品站岗红包-币港湾恒丰账户
	public static final String SYS_ACC_RED_ZSD = "RED_ZSD";//赞时贷产品站岗红包-币港湾恒丰账户
	public static final String SYS_ACC_RED_7 = "RED_7";//7贷产品站岗红包-币港湾恒丰账户
	public static final String SYS_ACC_USER_INTEREST = "USER_INTEREST";//用户利息-币港湾宝付账户
	public static final String SYS_ACC_THD_REDPACKET = "THD_REDPACKET";//红包已支出-币港湾宝付账户
	public static final String SYS_ACC_BAD_LOANS_ZAN = "BAD_LOANS_ZAN";//赞分期坏账户余额-币港湾宝付账户
	public static final String SYS_ACC_HF_USER_BALANCE = "HF_USER_BALANCE";// 恒丰日终用户余额
	public static final String SYS_ACC_RED_FREE = "RED_FREE";//自有产品站岗红包-币港湾恒丰账户
	public static final String SYS_ACC_AUTH_FREE = "AUTH_FREE";// 自由站岗户余额-币港湾恒丰账户
	public static final String SYS_ACC_BGW_AUTH_FREE = "BGW_AUTH_FREE";// 自由产品户余额-币港湾恒丰账户

	public static final Integer CODE_EXIST_TIME=120;
	public static final Integer CODE_EXIST_TIME_MAX=86400;
	public static final Integer CODE_RESEND_TIME=60;

	//流水表状态
	public static final Integer ACCOUNT_JNL_STATUS_SUCCESS = 1; //成功
	public static final Integer ACCOUNT_JNL_STATUS_FAIL = 2; //失败
	public static final Integer ACCOUNT_JNL_STATUS_INHAND = 3; //处理中

	//奖励金明细查询标志
	public static final String ALL_BONUS="1";//所有奖励金明细
	public static final String CAN_WITHDRAW_BONUS="2";//能提现的奖励金明细

	//银行卡状态
	public static final Integer BANK_CARD_NORMAL = 1; //正常
	public static final Integer BANK_CARD_FORBID = 2; //禁止
	public static final Integer BANK_CARD_BINDING = 3; // 绑定中
	public static final Integer BANK_CARD_BINDFAIL = 4; // 失败
	public static final Integer BANK_CARD_UNBIND = 5;  // 注销（解绑）

	//网联签约状态
	public static final Integer NUCC_SIGN_NORMAL = 1; //正常
	public static final Integer NUCC_SIGN_FORBID = 2; //禁止
	public static final Integer NUCC_SIGN_BINDING = 3; // 签约中
	public static final Integer NUCC_SIGN_BINDFAIL = 4; // 签约失败
	public static final Integer NUCC_SIGN_UNBIND = 5;  // 解签

	//银行卡是否常用
	public static final Integer BANK_CARD_USERED = 1;  //是
	public static final Integer BANK_CARD_UNUSERED = 2;  //否

	// cookie失效时间
	public static final Integer COOKIE_MAX_AGE_FIVE_DAYS = 5*3600*24;

	//系统配置表key------------------开始
	public static final String DAILY_BUY_TIMES = "DAILY_BUY_TIMES";	//每个产品每天的购买次数限制
	public static final String EMERGENCY_MOBILE = "EMERGENCY_MOBILE";	//系统告警通知手机号码
	public static final String PRODUCT_OPERATOR_MOBILE = "PRODUCT_OPERATOR_MOBILE";	//产品运营系统告警通知手机号码
	public static final String CUSTOMER_SERVICE_MOBILE = "CUSTOMER_SERVICE_MOBILE";	//客服系统告警通知手机号码
	public static final String MOBILE_CODE_TIMES = "MOBILE_CODE_TIMES";	//一个手机号每天可以发送验证码次数
	public static final String PAGE_SIZE = "PAGE_SIZE";			//默认每页显示的数据条数
	public static final String PRICE_CEILING = "PRICE_CEILING";		//购买上限金额
	public static final String PRICE_LIMIT = "PRICE_LIMIT";		//购买下限金额
	public static final String REAPAL_DAY_LIMIT	= "REAPAL_DAY_LIMIT";		//融宝单日入金上限
	public static final String WARN_IP_OP_TIMES = "WARN_IP_OP_TIMES";	//每分钟某IP操作次数告警门限
	public static final String WARN_USER_OP_TIMES = "WARN_USER_OP_TIMES";	//每分钟某用户操作次数告警门限
	public static final String PUSH_MONEY_RATE = "PUSH_MONEY_RATE";	//推荐金提成比率（%）
	public static final String PUSH_MONEY_RATE_FOR_ONE_MONTH = "PUSH_MONEY_RATE2";	//推荐金提成比率（30天）（%）
	public static final String BONUS_WITHDRAW_DAYS = "BONUS_WITHDRAW_DAYS";	//获得奖励金超过该天数后才能提现
	public static final String BIND_CARD_TIMEOUT_INTERVAL = "BIND_CARD_TIMEOUT_INTERVAL";//卡绑定超时处理时间间隔
	public static final String VIEW_NUM = "VIEW_NUM";//主页购买数据界面显示条数
	public static final String VIEW_PAGE_NUM = "VIEW_PAGE_NUM";//主页后台数据查询条数
	public static final String WITHDRAW_DAYS = "WITHDRAW_DAYS";	//当天提现次数限制
	public static final String WITHDRAW_MONTH = "WITHDRAW_MONTHS";	//当月提现次数限制
	public static final String PUSH_MONEY_RATE_7DAY = "PUSH_MONEY_RATE_7DAY"; //7天奖励金利率
	public static final String PUSH_MONEY_RATE_1 = "PUSH_MONEY_RATE2"; //30天奖励金利率
	public static final String PUSH_MONEY_RATE_3 = "PUSH_MONEY_RATE"; //90天奖励金利率
	public static final String PUSH_MONEY_RATE_6 = "PUSH_MONEY_RATE3"; //180天奖励金利率
	public static final String PUSH_MONEY_RATE_12 = "PUSH_MONEY_RATE4"; //365天奖励金利率
	public static final String PUSH_MONEY_RATE_YEAR_INSTALLMENT = "PUSH_MONEY_RATE_YEAR_INSTALLMENT";	// 委托计划产品奖励金年化利率
	public static final String BONUS_RATE_4_SELF_INSTALLMENT = "BONUS_RATE_4_SELF_INSTALLMENT";	// 委托计划产品购买人年化利率
	public static final String BONUS_RATE_4_REFERRER_INSTALLMENT = "BONUS_RATE_4_REFERRER_INSTALLMENT";	// 委托计划产品推荐人年化利率
	public static final String LOAN_AGREEMENT_CHANGE_THREE_PART_TIME = "LOAN_AGREEMENT_CHANGE_THREE_PART_TIME";	// 借款协议四方改三方生效时间

	public static final String BONUS_RATE_4_SELF_INSTALLMENT_NEW = "BONUS_RATE_4_SELF_INSTALLMENT_NEW";	// 委托计划产品购买人年化利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_REFERRER_INSTALLMENT_NEW = "BONUS_RATE_4_REFERRER_INSTALLMENT_NEW";	// 委托计划产品推荐人年化利率 （2018年1月1日上线版本）

	public static final String BONUS_RATE_4_REFERRER_7DAY = "BONUS_RATE_4_REFERRER_7DAY"; //7天奖励金利率
	public static final String BONUS_RATE_4_REFERRER_1MONTH = "BONUS_RATE_4_REFERRER_1MONTH"; //30天奖励金利率
	public static final String BONUS_RATE_4_REFERRER_3MONTH = "BONUS_RATE_4_REFERRER_3MONTH"; //90天奖励金利率
	public static final String BONUS_RATE_4_REFERRER_6MONTH = "BONUS_RATE_4_REFERRER_6MONTH"; //180天奖励金利率
	public static final String BONUS_RATE_4_REFERRER_1YEAR = "BONUS_RATE_4_REFERRER_1YEAR"; //365天奖励金利率
	public static final String BONUS_RATE_4_SELF_7DAY = "BONUS_RATE_4_SELF_7DAY"; //7天奖励金利率
	public static final String BONUS_RATE_4_SELF_1MONTH = "BONUS_RATE_4_SELF_1MONTH"; //30天奖励金利率
	public static final String BONUS_RATE_4_SELF_3MONTH = "BONUS_RATE_4_SELF_3MONTH"; //90天奖励金利率
	public static final String BONUS_RATE_4_SELF_6MONTH = "BONUS_RATE_4_SELF_6MONTH"; //180天奖励金利率
	public static final String BONUS_RATE_4_SELF_1YEAR = "BONUS_RATE_4_SELF_1YEAR"; //365天奖励金利率

	public static final String BONUS_RATE_4_REFERRER_7DAY_NEW = "BONUS_RATE_4_REFERRER_7DAY_NEW"; //7天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_SELF_7DAY_NEW = "BONUS_RATE_4_SELF_7DAY_NEW"; //30天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_REFERRER_1MONTH_NEW = "BONUS_RATE_4_REFERRER_1MONTH_NEW"; //90天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_SELF_1MONTH_NEW = "BONUS_RATE_4_SELF_1MONTH_NEW"; //180天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_REFERRER_3MONTH_NEW = "BONUS_RATE_4_REFERRER_3MONTH_NEW"; //365天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_SELF_3MONTH_NEW = "BONUS_RATE_4_SELF_3MONTH_NEW"; //7天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_REFERRER_6MONTH_NEW = "BONUS_RATE_4_REFERRER_6MONTH_NEW"; //30天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_SELF_6MONTH_NEW = "BONUS_RATE_4_SELF_6MONTH_NEW"; //90天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_REFERRER_1YEAR_NEW = "BONUS_RATE_4_REFERRER_1YEAR_NEW"; //180天奖励金利率 （2018年1月1日上线版本）
	public static final String BONUS_RATE_4_SELF_1YEAR_NEW = "BONUS_RATE_4_SELF_1YEAR_NEW"; //365天奖励金利率 （2018年1月1日上线版本）

	public static final String REGISTDATE_SEPARATE_4_BONUS = "REGISTDATE_SEPARATE_4_BONUS";//用户注册日期分隔时间，用于区分用户奖励金发放规则

	public static final String RECHANGE_LIMIT = "RECHANGE_LIMIT";	//最小充值金额
	public static final String WITHDRAW_LIMIT = "WITHDRAW_LIMIT";	//最小提现金额
	public static final String BONUS_WITHDRAW_LIMIT = "BONUS_WITHDRAW_LIMIT";	//奖励金最小提现金额
	public static final String SINGLE_WITHDRAW_UPPER_LIMIT = "SINGLE_WITHDRAW_UPPER_LIMIT";	//单笔提现上线
	public static final String DAY_WITHDRAW_UPPER_LIMIT = "DAY_WITHDRAW_UPPER_LIMIT";	//单日回款到卡金额累计大于200万提现上线
	public static final String SMS_SEND_TIME = "SMS_SEND_TIME"; //短信发送相隔时间（秒）
	public static final String WITHDRAW_TIME_BEGIN = "WITHDRAW_TIME_BEGIN"; //提现限制开始时间
	public static final String WITHDRAW_TIME_END = "WITHDRAW_TIME_END"; //提现限制结束时间
	public static final String CHANNEL_WITHDRAW_TIME = "CHANNEL_WITHDRAW_TIME"; //提现分隔时间,用户提现时，只能提至少隔日且过了该时间点之后的钱
	public static final String FINANCE_MOBILE = "FINANCE_MOBILE"; //财务告警通知手机号码
	public static final String PRODUCT_OPERATOR_EMAIL = "PRODUCT_OPERATOR_EMAIL"; //运营邮箱
	public static final String WITHDRAW_UP_LIMIT = "WITHDRAW_UP_LIMIT"; //最大提现金额
	public static final String MATCH_LIMIT_TIME = "MATCH_LIMIT_TIME"; //匹配限制时间，该时间前会匹配2015-12-01之前已经回款的投资
	public static final String CHECK_NEWUSER_START_TIME = "CHECK_NEWUSER_START_TIME"; //判断新用户起始时间
	public static final String CHECK_NEWUSER_END_TIME = "CHECK_NEWUSER_END_TIME"; // 判断新用户结束时间
	public static final String SMS_MAX_LENGTH = "SMS_MAX_LENGTH"; //短信群发号码个数（最大1000个手机）
	public static final String PRODUCT_INFORM_MINUTE = "PRODUCT_INFORM_MINUTE"; //产品开始前x分钟发送短信提醒	单位：分钟
	public static final String MATCH_BUY_START_TIME = "MATCH_BUY_START_TIME"; //债权匹配开始的理财购买时间
	public static final String RETURN_MAX_AMOUNT_LIMIT = "RETURN_MAX_AMOUNT_LIMIT"; //单笔回款结算最大限额
	public static final String MATCH_PATCH_ADD_PERCENT = "MATCH_PATCH_ADD_PERCENT"; //债权匹配打补丁程序中，需要从全匹配的债权中获取部分债权分配给未匹配的投资时，需要添加的固定比例
	public static final String SUPER_FINANCE_USER_ID="SUPER_FINANCE_USER_ID"; //超级理财人编号列表
	public static final String DAY_4_WAIT_LOAN="DAY_4_WAIT_LOAN"; //站岗资金等待时间
	public static final String LOAN_RELATION_MATCH_TIME="LOAN_RELATION_MATCH_TIME"; //相同日期的投资债权匹配循环次数
	public static final String SUPER_PRODUCT_ID="SUPER_PRODUCT_ID"; //超级理财人购买的产品id
	public static final String EACH_MONTH_WITHDRAW_TIMES = "EACH_MONTH_WITHDRAW_TIMES";	// 每月提现次数，超过则扣除相应手续费
	public static final String FINANCE_WITHDRAW_COUNTER_FEE = "FINANCE_WITHDRAW_COUNTER_FEE";	// 超过每月提现次数的相应手续费
	public static final String LOAN_USER_LOAN_FEE ="LOAN_USER_LOAN_FEE"; //借款用户借款手续费，每笔n元
	public static final String PARTNER_MARKET_FEE = "PARTNER_MARKET_FEE";//合作方营销费用提现手续费，每笔n元
	public static final String LOAN_USER_REPAY_FEE_RATE = "LOAN_USER_REPAY_FEE_RATE"; //借款用户还款手续费,千n每笔
	public static final String ZAN_DK_FEE= "ZAN_DK_FEE"; // 赞分期代扣，3元1笔
	public static final String USER_PARTNER_MARKET_FEE= "USER_PARTNER_MARKET_FEE"; //合作方营销费用提现手续费，每笔n元（向用户收取）
	public static final String DEP_PLATFORM_TRANSFER= "DEP_PLATFORM_TRANSFER"; // 存管系统平台转个人单人单月限额
	public static final String REDPACKET_GOLD_REMIND = "REDPACKET_GOLD_REMIND"; //恒丰抵用金告警
	public static final String HF_BANK_CUT_DAY_START_TIME = "HF_BANK_CUT_DAY_START_TIME";	// 恒丰维护开始时间
	public static final String HF_BANK_CUT_DAY_END_TIME = "HF_BANK_CUT_DAY_END_TIME";	// 恒丰维护结束时间
	public static final String YUN_HEAD_FEE_2_USER = "YUN_HEAD_FEE_2_USER";	// 云贷砍头息划转目标用户
	public static final String ZSD_HEAD_FEE_2_USER = "ZSD_HEAD_FEE_2_USER"; // 赞时贷砍头息划转目标用户
	public static final String HAPPY_BIRTHDAY_REDPACKET_REMIND = "HAPPY_BIRTHDAY_REDPACKET_REMIND"; // 生日红包剩余数量告警

	public static final String ZAN_YEAR_RATE_1="ZAN_YEAR_RATE_1"; //币港湾和赞分期结算的年化利率%（1个月）
	public static final String ZAN_YEAR_RATE_2="ZAN_YEAR_RATE_2"; //币港湾和赞分期结算的年化利率%（2个月）
	public static final String ZAN_YEAR_RATE_3="ZAN_YEAR_RATE_3"; //币港湾和赞分期结算的年化利率%（3个月）
	public static final String ZAN_YEAR_RATE_4="ZAN_YEAR_RATE_4"; //币港湾和赞分期结算的年化利率%（4个月）
	public static final String ZAN_YEAR_RATE_5="ZAN_YEAR_RATE_5"; //币港湾和赞分期结算的年化利率%（5个月）
	public static final String ZAN_YEAR_RATE_6="ZAN_YEAR_RATE_6"; // 币港湾和赞分期结算的年化利率%（6个月）
	public static final String ZAN_YEAR_RATE_9="ZAN_YEAR_RATE_9"; // 币港湾和赞分期结算的年化利率%（9个月）
	public static final String ZAN_YEAR_RATE_12="ZAN_YEAR_RATE_12"; // 币港湾和赞分期结算的年化利率%（12个月）
	public static final String MATCH_VIPUSER_MATCH_MAX_AMOUNT="MATCH_VIPUSER_MATCH_MAX_AMOUNT"; //VIP理财人匹配金额的最大值
	public static final String MATCH_VIPUSER_MATCH_MIN_AMOUNT="MATCH_VIPUSER_MATCH_MIN_AMOUNT"; //VIP理财人匹配金额的最小值
	public static final String MATCH_LIMIT_AMOUNT="MATCH_LIMIT_AMOUNT"; //债权匹配时低于该金额的不进行债权承接
	public static final String HISTORY_BUY_AMOUNT_LIMIT = "HISTORY_BUY_AMOUNT_LIMIT"; //用户提现时，用户的历史购买总金额下限
	public static final String YUN_DAI_CHANGE_NAME_DATE = "YUN_DAI_CHANGE_NAME_DATE"; //达飞云贷更名时间
	public static final String ZAN_FINANCE_REPAY_NOTICE_TIME = "ZAN_FINANCE_REPAY_NOTICE_TIME"; //赞分期产品回款通知用户的时间
	public static final String WITHDRAW_APPLY_LIMIT = "WITHDRAW_APPLY_LIMIT"; // 币港湾理财人提现申请金额限制（用于判断是否审核提现）

	public static final String BAOFOO_FEE_QUICK_PAY = "BAOFOO_FEE_QUICK_PAY"; //宝付快捷手续费  千2/笔（最低2元，封顶10元）
	public static final String BAOFOO_FEE_E_BANK = "BAOFOO_FEE_E_BANK"; //宝付网银手续费  千2/笔，无最低无封顶
	public static final String BAOFOO_FEE_DF = "BAOFOO_FEE_DF"; //宝付代付手续费 （n元/笔）
	public static final String BAOFOO_FEE_DK = "BAOFOO_FEE_DK"; //宝付代扣手续费（n元/笔）
	public static final String BAOFOO_TRANS_CEILING = "BAOFOO_TRANS_CEILING";//宝付转账金额上限（元）
	public static final String ZAN_PRODUCT_MAX_MONTH_RATE = "ZAN_PRODUCT_MAX_MONTH_RATE";//赞分期产品最大月利率
	public static final String ZAN_LOAN_AGREEMENT_LATE_FEE = "ZAN_LOAN_AGREEMENT_LATE_FEE";//赞分期借款协议滞纳金规则修改时间(用于区分显示借款协议)
	public static final String TRUST_LOGIN_AGENT = "TRUST_LOGIN_AGENT";//允许信任免登的代理渠道
	public static final String TRUST_LOGIN_IP = "TRUST_LOGIN_IP";//允许信任免登的IP

	public static final String SEVEN_DAI_SELF_FREE_DAYS = "7_DAI_SELF_FREE_DAYS"; //7贷自主_免息天数（天）
	public static final String SEVEN_DAI_SELF_LOAN_FEE = "7_DAI_SELF_LOAN_FEE"; //7贷自主_用户借款   手续费，每笔n元
	public static final String SEVEN_DAI_SELF_REPAY_FEE = "7_DAI_SELF_REPAY_FEE"; //7贷自主_用户还款   手续费，每笔n元
	public static final String SEVEN_DAI_SELF_SYS_SETTLE_RATE = "7_DAI_SELF_SYS_SETTLE_RATE"; //7贷自主_系统结算_年化利率 %
	public static final String SEVEN_DAI_SELF_AGREEMENT_RATE = "7_DAI_SELF_AGREEMENT_RATE"; //7贷自主_借款协议_年化利率 %
	public static final String SEVEN_DAI_SELF_DK_FEE= "7_DAI_SELF_DK_FEE"; //7贷自主_代扣，3元1笔
	public static final String SEVEN_DAI_SELF_SUPER_PRODUCT_ID = "7_DAI_SELF_SUPER_PRODUCT_ID"; //7贷自主放款VIP理财人产品编号
	//public static final String SEVEN_DAI_COMPENSATE_USER_ID = "7_DAI_COMPENSATE_USER_ID"; //7贷代偿人用户编号

	public static final String YUN_DAI_SELF_FREE_DAYS = "YUN_DAI_SELF_FREE_DAYS"; //云贷自主_免息天数（天）
	public static final String YUN_DAI_SELF_LOAN_FEE = "YUN_DAI_SELF_LOAN_FEE"; //云贷自主_用户借款   手续费，每笔n元
	public static final String YUN_DAI_SELF_REPAY_FEE = "YUN_DAI_SELF_REPAY_FEE"; // 云贷自主_用户还款   手续费，每笔n元
	public static final String YUN_DAI_SELF_SYS_SETTLE_RATE = "YUN_DAI_SELF_SYS_SETTLE_RATE"; //云贷自主_系统结算_年化利率 %
	public static final String YUN_DAI_SELF_AGREEMENT_RATE = "YUN_DAI_SELF_AGREEMENT_RATE"; //云贷自主_借款协议_年化利率 %
	public static final String BONUS_RATE_4_YUN_DAI_SELF = "BONUS_RATE_4_YUN_DAI_SELF"; //自主放款奖励金发放利率,每人获得的利率
	public static final String YUN_DAI_SELF_DK_FEE= "YUN_DAI_SELF_DK_FEE"; // 云贷自主_代扣，3元1笔
	public static final String YUN_DAI_SELF_BETA_USER_ID = "YUN_DAI_SELF_BETA_USER_ID"; //存管内测用户ID（,分隔）
	public static final String YUN_DAI_SELF_SUPER_PRODUCT_ID = "YUN_DAI_SELF_SUPER_PRODUCT_ID"; //云贷自主放款VIP理财人产品编号
	//public static final String YUN_DAI_COMPENSATE_USER_ID = "YUN_DAI_COMPENSATE_USER_ID"; //云贷代偿人用户编号
	public static final String ZAN_COMPENSATE_USER_ID = "ZAN_COMPENSATE_USER_ID"; //赞分期代偿人用户编号
	public static final String COMPENSATE_WITHDRAW_CHECK_MOBILE = "COMPENSATE_WITHDRAW_CHECK_MOBILE"; //赞分期代偿人提现审核验证手机号
	public static final String ZAN_AGREEMENT_DATE_4_NEW = "ZAN_AGREEMENT_DATE_4_NEW"; //赞分期新旧协议时间区分标志
	public static final String HF_THRESHOLD_RATE = "HF_THRESHOLD_RATE";//恒丰-风险准备金账户余额*0.8为阈值，该值为0.8，可调
	public static final String HF_RISK_RESERVE_AMOUNT = "RISK_RESERVE_AMOUNT"; //恒丰-风险准备金账户余额
	public static final String HFBANK_SYS_TOP_UP = "HFBANK_SYS_TOP_UP"; //存管专户平台充值卡号
	public static final String HFBANK_SYS_WITHDRAW = "HFBANK_SYS_WITHDRAW"; //存管专户平台提现卡号
	public static final String YUN_DAI_SELF_LOAN_AGREEMENT_DATE_4_NEW = "YUN_DAI_SELF_LOAN_AGREEMENT_DATE_4_NEW"; //存管云贷借款协议新旧版本时间区分标志
	public static final String COMPENSATE_USER_INFO = "COMPENSATE_USER_INFO"; //云贷代偿人用户编号


	public static final String ZSD_SUPER_PRODUCT_ID = "ZSD_SUPER_PRODUCT_ID"; //赞时贷VIP理财人产品编号
	public static final String ZSD_MATCH_LIMIT_AMOUNT ="ZSD_MATCH_LIMIT_AMOUNT"; //赞时贷债权匹配时低于该金额的不进行债权承接
	public static final String ZSD_SYS_SETTLE_RATE = "ZSD_SYS_SETTLE_RATE";	//赞时贷_系统结算_年化利率 %
	public static final String ZSD_SYS_MARGIN_RATE = "ZSD_SYS_MARGIN_RATE";	//赞时贷_保证金_利率 %
	public static final String ZSD_COMPENSATE_USER_ID = "ZSD_COMPENSATE_USER_ID"; //赞时贷代偿人用户编号
	public static final String ZSD_DK_FEE = "ZSD_DK_FEE";	//赞时贷_代扣, 3元1笔 	
	public static final String YUN_DAI_SELF_MATH_MIN_BALANCE = "YUN_DAI_SELF_MATH_MIN_BALANCE"; //云贷匹配最小金额，低于该金额匹配放弃，除借款额度剩余外（最小匹配金额）
	public static final String HF_BATCH_EXT_BUY_MAX_SIZE = "HF_BATCH_EXT_BUY_MAX_SIZE"; //恒丰批量投标最大条数

	public static final String DK_REPAY_PROCESS_MAX_NUM = "DK_REPAY_PROCESS_MAX_NUM";//批量代扣还款，线程允许接收最大队列数

	public static final String LOAN_PROCESS_MAX_NUM = "LOAN_PROCESS_MAX_NUM";//借款队列，线程允许接收最大队列数
	public static final String LOAN_7DAI_PROCESS_MAX_NUM = "LOAN_7DAI_PROCESS_MAX_NUM";//7贷借款队列，线程允许接收最大队列数
	public static final String DK_REPAY_HANDLE_MAX_NUM = "DK_REPAY_HANDLE_MAX_NUM";//批量代扣还款支付结果处理定时，线程允许接收最大队列数

	public static final String SEVEN_DAI_OLD_FINISH_DATE = "7_DAI_OLD_FINISH_DATE";//7贷_存量数据回款截止日期

	public static final String YUN_DAI_OLD_FINISH_DATE = "YUN_DAI_OLD_FINISH_DATE";//云贷_存量数据回款截止日期

	public static final String HF_BATCH_DF_MAX_SIZE = "HF_BATCH_DF_MAX_SIZE"; //恒丰批量代付到归集户最大条数
	public static final String M_ROLE_MANAGER_ROLE_ID = "M_ROLE_MANAGER_ROLE_ID"; //管理台权限管理员角色id
	public static final String USER_LOGIN_ALWAYS_FAIL_TIME_LIMIT = "USER_LOGIN_ALWAYS_FAIL_TIME_LIMIT"; //理财用户连续登录失败次数限制
	public static final String USER_LOGIN_LOCK_TIME = "USER_LOGIN_LOCK_TIME"; //理财用户连续登录失败次数到达限制后锁住时长
	public static final String LOAN_MAX_SUM_AMOUNT = "LOAN_MAX_SUM_AMOUNT"; //借款允许最大总额，单位万元
	public static final String USER_LOGIN_COOKIE_MAX_AGE = "USER_LOGIN_COOKIE_MAX_AGE"; //用户登录cookie失效时间

	public static final String ZAN_CALLED_AWAY_DATE = "ZAN_CALLED_AWAY_DATE"; //赞分期提前赎回日期
	public static final String LOAN_SERVICE_RATE_YUN_DAI = "LOAN_SERVICE_RATE_YUN_DAI"; // 云贷(接口3：放款结果查询,接口16：放款结果通知)借款服务费率
	public static final String LOAN_SERVICE_RATE_SEVEN_DAI = "LOAN_SERVICE_RATE_SEVEN_DAI"; //7贷(接口3：放款结果查询,接口16：放款结果通知)借款服务费率
	public static final String LATE_AGREEMENT_GRNERATE_NUM = "LATE_AGREEMENT_GRNERATE_NUM"; //生成代偿协议数据查询记录条数（补协议）
	public static final String QIDIAN_DATA_SYNC_SWITCH = "QIDIAN_DATA_SYNC_SWITCH";//七店数据同步开关配置（可以自定义时间执行）
	public static final String LOAN_DEBT_FINANCING_BACK_RESIDENCE_TIME = "LOAN_DEBT_FINANCING_BACK_RESIDENCE_TIME"; // 债权回退：标的发布成功状态停留时间（小时）

	public static final String MALL_SEND_TICKET_PROCESS_MAX_NUM ="MALL_SEND_TICKET_PROCESS_MAX_NUM";
	public static final String MATCH_MAX_BALANCE = "MATCH_MAX_BALANCE"; // 第一级债权匹配金额配置（最大匹配金额）
	public static final String MATCH_MIDDLE_BALANCE = "MATCH_MIDDLE_BALANCE"; // 第二级债权匹配金额配置（中间匹配金额）
	public static final String MATCH_REDIS_FLAG_EXPTIME = "MATCH_REDIS_FLAG_EXPTIME"; // 云贷/七贷/自由三个站岗户初始化判断标识失效时间，true可初始化站岗户队列，false不初始化
	public static final String MATCH_REDIS_LEVEL_QUEUE_COUNT = "MATCH_REDIS_LEVEL_QUEUE_COUNT"; // 云贷/七贷/自由三个站岗户区间中各放置的站岗户数量
	public static final String MATCH_MAX_AMOUNT_NORMAL = "MATCH_MAX_AMOUNT_NORMAL"; // 普通用户最大可匹配金额 默认10000元
	public static final String MATCH_MAX_AMOUNT_QIANBAO = "MATCH_MAX_AMOUNT_QIANBAO"; // 钱报用户最大可匹配金额 默认5000元


	public static final String YUN_FIXED_INSTALLMENT_SYS_SETTLE_RATE = "YUN_FIXED_INSTALLMENT_SYS_SETTLE_RATE"; //云贷自主_等额本息_系统结算_年化利率 %
	public static final String YUN_FIXED_INSTALLMENT_AGREEMENT_RATE = "YUN_FIXED_INSTALLMENT_AGREEMENT_RATE"; //云贷自主_等额本息_借款协议_年化利率 %
	public static final String LOAN_SERVICE_RATE_YUN_DAI_FIXED_INSTALLMENT = "LOAN_SERVICE_RATE_YUN_FIXED_INSTALLMENT"; //云贷_等额本息_借款服务费率(接口3：放款结果查询,接口16：放款结果通知)

	public static final String YUN_FIXED_PRINCIPAL_INTEREST_SYS_SETTLE_RATE = "YUN_FIXED_PRINCIPAL_INTEREST_SYS_SETTLE_RATE"; 	//云贷自主_等本等息_系统结算_年化利率 %
	//public static final String YUN_FIXED_PRINCIPAL_INTEREST_AGREEMENT_RATE = "YUN_FIXED_PRINCIPAL_INTEREST_AGREEMENT_RATE"; 	//云贷自主_等本等息_借款协议_年化利率 %
	public static final String LOAN_SERVICE_RATE_FIXED_PRINCIPAL_INTEREST = "LOAN_SERVICE_RATE_FIXED_PRINCIPAL_INTEREST"; 		//云贷_等本等息_借款服务费率(接口3：放款结果查询,接口16：放款结果通知)

	public static final String MATCH_IS_PRIORITY_USE_FREE = "MATCH_IS_PRIORITY_USE_FREE"; 		// 债权匹配是否优先支持Free自由站岗户

	public static final String TRANSFER_RELATION_LIMIT_PAGE_SIZE="TRANSFER_RELATION_LIMIT_PAGE_SIZE"; //债权转让时，查询待匹配数据的分页每页限制条数

	public static final String JSH_BALANCE_ALARM_VALUE = "JSH_BALANCE_ALARM_VALUE"; 		// 宝付系统结算户余额告警阈值

	public static final String LATE_AGREEMENT_GENERATE_TIME_INTERVAL = "LATE_AGREEMENT_GENERATE_TIME_INTERVAL"; 		// 云贷代偿协议生成定时时间间隔区间配置
	public static final String LATE_AGREEMENT_NOTIFY_TIME_INTERVAL = "LATE_AGREEMENT_NOTIFY_TIME_INTERVAL"; 		// 云贷代偿签章协议通知定时时间间隔区间配置
	//系统配置表key------------------结束

	//public static final String CHANNEL_CHECK_TIME = "CHANNEL_CHECK_TIME"; //渠道互转审核提醒时间

	//借贷标识
	public static final Integer CD_FLAG_D = 1; //借 --  钱转入
	public static final Integer CD_FLAG_C = 2; //贷--  钱转出

	//用户记账流水交易类型
	public static final Integer TRANS_TYPE_TOPUP = 1; //充值
	public static final Integer TRANS_TYPE_TRANSFER = 2; //转账
	public static final Integer TRANS_TYPE_WITHDRAW = 3; //提现
	public static final Integer TRANS_TYPE_BUY = 4;//购买
	public static final Integer TRANS_TYPE_BONUS = 5;//推荐奖励
	public static final Integer TRANS_TYPE_BONUS2BALANCE = 6; //奖励金转余额

	//基金展示下架
	public static final Integer FUND_STATUS_ABLE = 1;//展示
	public static final Integer FUND_STATUS_DISABLE = 4;//下架

	//系统配置 操作标志
	public static final String SYSCONFIG_UPDATEFLAG_CREATE = "create"; //新增 系统配置
	public static final String SYSCONFIG_UPDATEFLAG_UPDATE = "update"; //修改 系统配置
	public static final String SYSCONFIG_UPDATEFLAG_DELETE = "delete"; //删除 系统配置

	//差错登记表处理状态
	public static final Integer CHECKERROR_STATUS_UNDEAL = 1 ;// 未处理
	public static final Integer CHECKERROR_STATUS_DEAL = 2 ;// 已处理


	//手机号类型
	public static final String MOBILE_TYPE_NORMAL="normal";//普通手机号
	public static final String MOBILE_TYPE_EMERGENCY="emergency";//内部告警手机号

	//转让状态
	public static final Integer TRANSFER_STATUS_WAITE = 1 ;//转让中
	public static final Integer TRANSFER_STATUS_SECCUSS = 2 ;//已转让

	//产品子账户状态
	public static final Integer SUBACCOUNT_STATUS_OPEN = 1; //开户
	public static final Integer SUBACCOUNT_STATUS_FINANCING = 2; //投资中
	public static final Integer SUBACCOUNT_STATUS_TRANSFER = 3; //转让中
	public static final Integer SUBACCOUNT_STATUS_TRANSFERED = 4; //已转让
	public static final Integer SUBACCOUNT_STATUS_SETTLE  = 5; //已结算
	public static final Integer SUBACCOUNT_STATUS_CLOSE = 6; //销户
	public static final Integer SUBACCOUNT_STATUS_SETTLEING = 7; //结算中

	//借款人产品子账户状态
	public static final String LOAN_SUBACCOUNT_STATUS_OPEN = "OPEN"; //开户
	public static final String LOAN_SUBACCOUNT_STATUS_USING = "USING"; //投资中
	public static final String LOAN_SUBACCOUNT_STATUS_REPAIED = "REPAIED"; //已结算
	public static final String LOAN_SUBACCOUNT_STATUS_CANCEL = "CANCEL"; //销户

	//达飞绑定银行卡处理状态
	public static final Integer DAFY_BINDING_STAUTS_NORMAL = 1; //正常
	public static final Integer DAFY_BINDING_STAUTS_FORBID = 2; //禁用
	public static final Integer DAFY_BINDING_STAUTS_BINDING = 3; //绑定中
	public static final Integer DAFY_BINDING_STAUTS_BIND_FAIL = 4; //绑定失败
	public static final Integer DAFY_BINDING_STAUTS_BIND_UNBIND = 5; //注销


	//发送手机号码
	public static final String SEND_MOBILE="13588449807";//发送手机

	//订单支付状态
	public static final int ORDER_STATUS_CREATE = 1;//1-创建
	public static final int ORDER_STATUS_CON_FAIL = 2;//2-通讯失败
	public static final int ORDER_STATUS_PRE_SUCC = 3;//3-预下单成功
	public static final int ORDER_STATUS_PRE_FAIL = 4;//4-预下单失败
	public static final int ORDER_STATUS_PAYING = 5;//5-支付处理中
	public static final int ORDER_STATUS_SUCCESS = 6;//6-支付成功
	public static final int ORDER_STATUS_FAIL = 7;//7-支付失败

	//订单交易代码
	public static final String ORDER_TRANS_CODE_INIT = "INIT";//1-创建
	public static final String ORDER_TRANS_CODE_COMM_FAIL = "COMM_FAIL";//2-通讯失败
	public static final String ORDER_TRANS_CODE_PRE_SUCC = "PRE_SUCC";//3-预下单成功
	public static final String ORDER_TRANS_CODE_PRE_FAIL = "PRE_FAIL";//4-预下单失败
	public static final String ORDER_TRANS_CODE_PROCCESS = "PROCCESS";//5-支付处理中
	public static final String ORDER_TRANS_CODE_PROCESS = "PROCESS";//5-支付处理中(新)
	public static final String ORDER_TRANS_CODE_SUCCESS = "SUCCESS";//6-支付成功
	public static final String ORDER_TRANS_CODE_FAIL = "FAIL";//7-支付失败

	//达飞对账返回支付状态
	public static final String DAFY_ORDER_STATUS_SUCCESS = "1";//成功
	public static final String DAFY_ORDER_STATUS_PAYING = "2";//处理中
	public static final String DAFY_ORDER_STATUS_FAIL = "3";//失败

	//订单渠道
	public static final String ORDER_CHANNEL_DAFY = "DAFY";//达飞
	public static final String ORDER_CHANNEL_BAOFOO = "BAOFOO";//宝付
	public static final String ORDER_CHANNEL_HFBANK = "HFBANK";//恒丰

	//订单类型
	public static final Integer ORDER_TYPE_TOPUP = 1; //充值
	public static final Integer ORDER_TYPE_TRANSFER = 2; //转账
	public static final Integer ORDER_TYPE_WITHDRAW = 3; //提现
	public static final Integer ORDER_TYPE_BUY = 4;//购买

	//订单金额币种
	public static final int ORDER_CURRENCY_CNY = 0;//0-人民币
	public static final int ORDER_CURRENCY_USD = 1;//1-美元

	//用户状态
	public static final Integer USER_STATUS_1 = 1; //正常
	public static final Integer USER_STATUS_2 = 2; //注销
	public static final Integer USER_STATUS_3 = 3; //禁用

	// 是否新手标志，根据是否有投资
	public static final Integer USER_IS_NOVICE_YES = 1;  // 新手标志
	public static final Integer USER_IS_NOVICE_NO = 0; // 不是新手

	//渠道标志
	public static final String CHANNEL_TRS_QUICKPAY = "QUICK_PAY";//快捷
	public static final String CHANNEL_TRS_DK = "DK";//代扣
	public static final String CHANNEL_TRS_DF = "DF";//代付
	public static final String CHANNEL_TRS_NETBANK = "NETBANK";//网银
	public static final String CHANNEL_TRS_TRANSFER = "TRANSFER";//钱包转账
	public static final String CHANNEL_TRS_BIND_CARD = "BIND_CARD";// 绑卡
	public static final String CHANNEL_TRS_UN_BIND_CARD = "UN_BIND_CARD";// 解绑卡
	public static final String CHANNEL_TRS_FILL_INTEREST = "FILL_INTEREST";//补息（平台转个人）

	//渠道交易类型
	public static final String CHANNEL_DAFY = "DAFY";//达飞渠道
	public static final String CHANNEL_PAY19 = "PAY19";//19付渠道
	public static final String CHANNEL_REAPAL = "REAPAL";//融宝渠道
	public static final String CHANNEL_BAOFOO = "BAOFOO";// 宝付渠道
	public static final String CHANNEL_HFBANK = "HFBANK";// 恒丰银行渠道


	//系统维护状态
	public static Integer sysValue = 1;//是否挂起整个系统（不允许系统中任何业务运行），1-正常，2-挂起
	public static Integer tranValue = 1;//是否挂起交易类业务（不允许用户发起购买等交易相关的业务），1-正常，2-挂起

	//卡绑定接口 业务类型
	public static final int BINDCARD_STATUS_NEW_ALL = 1;//1-新用户的注册+绑卡
	public static final int BINDCARD_STATUS_UPDATE_CARD = 2;//2-修改绑卡
	public static final int BINDCARD_STATUS_UPDATE_ALL = 3;//3-修改注册和绑卡

	//卡绑定接口 三方状态
	public static final int BINDCARD_THIRDSTATUS_PROCESSING = 1;//处理中（对应1-新用户的注册+绑卡）
	public static final int BINDCARD_THIRDSTATUS_CARD_BINDED = 2;//已绑卡成功（对应2-修改绑卡）
	public static final int BINDCARD_THIRDSTATUS_CARD_BINDING = 3;//绑卡处理中（对应2-修改绑卡）
	public static final int BINDCARD_THIRDSTATUS_REALNAME_AND_CARD_BINDING = 4;//实名处理中，绑卡处理中（对应3-修改注册和绑卡）
	public static final int BINDCARD_THIRDSTATUS_REALNAME_BINDED = 5;//实名已认证，绑卡未认证（对应3-修改注册和绑卡）
	public static final int BINDCARD_THIRDSTATUS_REALNAME_AND_CARD_BINDED = 6;//实名已认证，绑卡已认证（对应3-修改注册和绑卡）

	//明细对账结果
	public static final int CHECK_ACCOUNT_DETAIL_IS_MATCH = 1;//账目符合
	public static final int CHECK_ACCOUNT_DETAIL_NOT_MATCH = 2;//账目不符
	public static final int CHECK_ACCOUNT_DETAIL_THIRD_NOT_FOUND = 3;//三方没有此账务
	public static final int CHECK_ACCOUNT_DETAIL_LOCAL_NOT_FOUND = 4;//本地没有此账务
	//19付明细对账结果
	public static final String CHECK_ACCOUNT_DETAIL_DIFFERENT = "DIFFERENT";// 账目不符
	public static final String CHECK_ACCOUNT_DETAIL_NO_DIFFERENT = "NO_DIFFERENT";// 账目符合

	//对账状态（目前用于用户子账户表的产品户）
	public static final String CHECK_ACCOUNT_STATUS_INIT = "INIT";// 未对账
	public static final String CHECK_ACCOUNT_STATUS_SUCCESS = "SUCCESS";// 对账成功
	public static final String CHECK_ACCOUNT_STATUS_FAIL = "FAIL";// 对账失败
	public static final String CHECK_ACCOUNT_STATUS_FAIL_2_SUCCESS = "FAIL_2_SUCCESS";//失败后成功

	//明细对账差错记录 是否已处理
	public static final int CHECK_ERROR_JNL_NOT_DEAL = 1;//未处理
	public static final int CHECK_ERROR_JNL_IS_DEAL = 2;//已处理

	// 对账业务类型
	public static final String TRANS_TYPE_UN_CLEAR = "UN_CLEAR";  //未定


	//购买银行类别
	public static final int TERMINAL_TYPE_MOBILE = 1; //手机端
	public static final int TERMINAL_TYPE_PC = 2; //pc端
	public static final int TERMINAL_TYPE_MANAGE = 5; //管理台


	//结算（理财产品回款）结果标志
	public static final int RECEIVE_MONEY_SUCCESS = 0; //回款成功
	public static final int RECEIVE_MONEY_FAIL = 1; //回款失败
	//游戏每个玩家的助力次数
	public static final int HELP_PALY_COUNT = 10; //默认十次

	//网新账户交易类型
	public static final String WXACCOUNT_TRS_TYPE_CUSTOMER_WITHDRAW = "001";//用户提现
	public static final String WXACCOUNT_TRS_TYPE_WX_WITHDRAW = "002";//网新提现
	public static final String WXACCOUNT_TRS_TYPE_MARKETING_FEE = "003";//营销费用打款
	public static final String WXACCOUNT_TRS_TYPE_SERVICE_FEE = "004";//服务费用打款

	//用户提现状态
	public static final int CUSTOMER_WITHDRAW_APPLY = 1;//提现申请中
	public static final int CUSTOMER_WITHDRAW_SUCCESS = 2;//提现成功
	public static final int CUSTOMER_WITHDRAW_FAIL = 3;//提现失败

	//网新提现状态
	public static final int WX_SYS_WITHDRAW_APPLY = 1; //申请提现
	public static final int WX_SYS_WITHDRAW_SUCCESS = 2; //提现成功
	public static final int WX_SYS_WITHDRAW_FAIL = 3; //提现失败

	public static final String CUSTOMER_WITHDRAW_APPLY_NO = "CSTW";//用户提现
	public static final String SYS_WITHDRAW_APPLY_NO = "SYSW";//网新提现

	//资管计划验签结果
	public static final int ASSETS_MANAGE_PLAN_SIGN_TRUE = 1; //验签通过
	public static final int ASSETS_MANAGE_PLAN_SIGN_FALSE = 2; //验签不通过

	//用户类型
	public static final String USER_TYPE_NORMAL = "NORMAL";//普通用户
	public static final String USER_TYPE_PROMOT = "PROMOT";//渠道用户

	//19付银行通道
	public static final int IS_AVAILABLE_ABLE = 1; //可用
	public static final int IS_AVAILABLE_DISABLE = 2; //不可用

	//19付银行支付类型
	public static final int PAY_TYPE_QUICK = 1; //快捷
	public static final int PAY_TYPE_PAYMENT = 2; //代付
	public static final int PAY_TYPE_HOLD = 3; //代扣
	public static final int PAY_TYPE_NET = 4; //网银

	//特殊交易流水表处理状态
	public static final int SPECIAL_JNL_STATUS_CREATE = 1; //创建
	public static final int SPECIAL_JNL_STATUS_DEAL = 2; //处理中
	public static final int SPECIAL_JNL_STATUS_SUCCESS = 3; //成功
	public static final int SPECIAL_JNL_STATUS_FALL= 4; //失败

	//对账-异常处理类型
	public static final String HANDLE_TYPE_DK_REFUND = "DK_REFUND"; //代扣异常（退款用户）
	public static final String HANDLE_TYPE_MULTI_COMPENSATE = "MULTI_COMPENSATE"; //代偿异常（代偿多账）

	//用户交易明细表bs_user_trans_detail交易类型
	public static final String Trans_TYPE_TOP_UP = "TOP_UP"; //充值
	public static final String Trans_TYPE_BUY = "BUY"; //加入金额
	public static final String Trans_TYPE_RETURN = "RETURN"; //云贷七贷回款
	public static final String Trans_TYPE_ZSD_RETURN = "ZSD_RETURN"; //赞时贷回款
	public static final String Trans_TYPE_ZAN_RETURN = "ZAN_RETURN"; //赞分期回款
	public static final String Trans_TYPE_BONUS_2_BALANCE = "BONUS_2_BALANCE"; //奖励金转余额
	public static final String Trans_TYPE_USER_BONUS_WITHDRAW = "USER_BONUS_WITHDRAW"; //奖励金提现
	public static final String Trans_TYPE_WITHDRAW = "WITHDRAW"; //提现
	public static final String Trans_TYPE_DEP_WITHDRAW = "DEP_WITHDRAW"; //用户提现（存管）
	public static final String Trans_TYPE_DEP_LOAN_WITHDRAW = "DEP_LOAN_WITHDRAW"; //借款人提现（存管）
	public static final String Trans_TYPE_CHANNEL_WITHDRAW = "CHANNEL_WITHDRAW";   // 辅助通道提现
	public static final String Trans_TYPE_AUTH_ACCOUNT_TURN_TO_BALANCE = "AUTH_ACCOUNT_TO_BALANCE";   // 站岗户转余额
	public static final String Trans_TYPE_TOP_UP_FEE = "TOP_UP_FEE"; //手续费扣除
	public static final String Trans_TYPE_WITHDRAW_FEE = "WITHDRAW_FEE"; //手续费扣除
	public static final String Trans_TYPE_HEAD_FEE_RETURN = "HEAD_FEE_RETURN"; //云贷砍头息转入

	//用户交易明细表bs_user_trans_detail交易状态
	public static final String Trans_STATUS_DEAL ="1"; // 处理中
	public static final String Trans_STATUS_SUCCESS ="2"; //成功
	public static final String Trans_STATUS_FAIL ="3"; // 失败
	public static final String Trans_STATUS_CHECK = "4"; //审核中

	//客户回款状态
	public static final String RETURN_STATUS_NOT = "NOT"; //未回款
	public static final String RETURN_STATUS_PROCCESS = "PROCCESS"; //处理中
	public static final String RETURN_STATUS_SUCCESS = "SUCCESS"; //回款成功
	public static final String RETURN_STATUS_FAIL = "FAIL"; //回款失败

	//回款通知处理状态
	public static final String RETURN_NOTICE_DEAL_STATUS_INIT = "INIT";// 初始
	public static final String RETURN_NOTICE_DEAL_STATUS_SUCCESS = "SUCC";// 处理成功
	public static final String RETURN_NOTICE_DEAL_STATUS_FAIL = "FAIL";// 处理失败
	//回款通知类型
	public static final String RETURN_NOTICE_TYPE_INTEREST = "INTEREST_RETURN";// 系统每月结息回款
	public static final String RETURN_NOTICE_TYPE_LAST = "LAST_RETURN";// 系统本金和剩余利息回款

	//回款路径
	public static final int RETURN_PATH_BALANCE = 1; //余额
	public static final int RETURN_PATH_BANKCARD = 2; //银行卡

	//订单账户类型
	public static final int ORDER_ACCOUNT_TYPE_USER = 1;//用户
	public static final int ORDER_ACCOUNT_TYPE_SYS = 2;//系统

	//交易类型
	public static final int USER_TRANS_TYPE_CARD = 1; //卡购买
	public static final int USER_TRANS_TYPE_RECHANGE = 2; //充值

	// 退票操作还款编号前缀
	public static final String REFUND_TICKET_REPAY_PREFIX = "RGCL99900";

	//下单类型
	public static final int READY_PLANCE_ORDER = 1; //预下单
	public static final int REGULAR_PLANCE_ORDER = 2; //正式下单

	// 系统产品购买表状态
	public static final String BATCHBUY_STATUS_INIT = "INIT"; // 初始状态
	public static final String BATCHBUY_STATUS_19_PROCCESS = "MONEY_TO_19_PROCCESS"; // 打款给19付处理中
	public static final String BATCHBUY_STATUS_19_SUCCESS = "MONEY_TO_19_SUCCESS"; // 打款给19付成功
	public static final String BATCHBUY_STATUS_19_FAIL = "MONEY_TO_19_FAIL"; // 打款给19付失败
	public static final String BATCHBUY_STATUS_DAFY_PAY_FAIL = "MONEY_TO_DAFY_FAIL"; // 达飞确认打款失败
	public static final String BATCHBUY_STATUS_DAFY_PROCCESS = "BUY_DAFY_PROCCESS"; // 发送给达飞购买理财处理中
	public static final String BATCHBUY_STATUS_DAFY_SUCCESS = "BUY_DAFY_SUCCESS"; // 发送给达飞购买理财成功
	public static final String BATCHBUY_STATUS_DAFY_FAIL = "BUY_DAFY_FAIL"; // 发送给达飞购买理财失败
	public static final String BATCHBUY_STATUS_RETURN_SUCCESS = "DAFY_RETURN_SUCCESS"; // 达飞回款通知成功
	public static final String BATCHBUY_STATUS_RETURN_FAIL = "DAFY_RETURN_FAIL"; // 达飞回款通知失败

	// 用户子账户可转状态
	public static final int USER_SUB_TRANS_STATUS_0 = 0; //0-正常
	public static final int USER_SUB_TRANS_STATUS_1 = 1; //1-可入不可出
	public static final int USER_SUB_TRANS_STATUS_2 = 2; //2-不可入可出
	public static final int USER_SUB_TRANS_STATUS_3 = 3; //3-不可入不可出

	//系统记账流水状态
	public static final int SYS_ACCOUNT_STATUS_SUCCESS = 1;//成功
	public static final int SYS_ACCOUNT_STATUS_PAYING = 3;//处理中
	public static final int SYS_ACCOUNT_STATUS_FAIL = 2;//失败

	//系统记账流水交易代码
	public static final String SYS_TOP_UP = "SYS_TOP_UP"; //系统充值
	public static final String SYS_FREEZE = "SYS_FREEZE"; //冻结
	public static final String SYS_UNFREEZE = "SYS_UNFREEZE"; //解冻
	public static final String SYS_BUY = "SYS_BUY"; //系统购买达飞产品（从REG）
	public static final String SYS_RETURN = "SYS_RETURN"; //系统回款（达飞到RETURN）
	public static final String SYS_RETURN_INTEREST = "SYS_RETURN_INTEREST"; //达飞回款系统获得利息(RETURN->JSH)
	public static final String SYS_USER_BONUS_2_BALANCE  = "USER_BONUS_2_BALANCE"; //用户奖励金转余额（JSH->USER）
	public static final String SYS_USER_BONUS_WITHDRAW  = "USER_BONUS_WITHDRAW"; //用户奖励金提现
	public static final String SYS_USER_WITHDRAW = "USER_WITHDRAW"; //用户提现
	public static final String SYS_USER_RETURN_2_BALANCE = "USER_RETURN_2_BALANCE"; //用户回款到余额（本金+利息）(RETURN->USER)
	public static final String SYS_USER_RETURN_2_CARD = "USER_RETURN_2_CARD"; //用户回款到银行卡（本金+利息）(从RETURN)
	public static final String SYS_USER_TOP_UP = "USER_TOP_UP"; //用户充值（到USER）
	public static final String SYS_USER_BALANCE_BUY = "USER_BALANCE_BUY"; //用户余额购买（USER->REG）
	public static final String SYS_USER_CARD_BUY = "USER_CARD_BUY"; //用户卡购买（卡到REG）
	public static final String SYS_WITHDRAW  = "SYS_WITHDRAW"; //系统提现（从JSH）
	public static final String SYS_CHANNEL_WITHDRAW = "SYS_CHANNEL_WITHDRAW";  // 系统渠道取现（融宝）
	public static final String SYS_RED_PACKET_TOP_UP = "SYS_RED_PACKET_TOP_UP";  // 系统红包预算审核通过（红包充值）
	public static final String SYS_RED_PACKET_USED = "SYS_RED_PACKET_USED";  // 红包使用
	public static final String SYS_FINANCE_WITHDRAW_REGISTRY = "SYS_FINANCE_WITHDRAW_REGISTRY";  // 线下提现登记
	public static final String SYS_AUTH_BACK = "SYS_AUTH_BACK"; //系统站岗户资金回退
	public static final String SYS_AUTH_BACK_TRANSFER = "SYS_AUTH_BACK_TRANSFER"; //系统站岗户资金回退,在债权转让时
	public static final String SYS_AUTH_ADD = "SYS_AUTH_ADD"; //系统站岗户资金授权
	public static final String SYS_AUTH_2_REG = "SYS_AUTH_2_REG"; //合作方站岗户资金转产品户
	public static final String SYS_RED_2_REG = "SYS_RED_2_REG"; //红包户资金转产品户
	public static final String SYS_PAT_REVENUE_SETTLE = "SYS_PAT_REVENUE_SETTLE"; //合作方营收账户结算
	public static final String SYS_MARGIN_2_RETURN = "SYS_MARGIN_2_RETURN"; //保证金户转回款户（逾期垫付）
	public static final String SYS_BGW_RETURN_ZAN_ADD = "SYS_BGW_RETURN_ZAN_ADD"; //币港湾体系-存管赞分期回款户转入
	public static final String SYS_PAT_REG_OUT = "SYS_PAT_REG_OUT"; //合作方剩余产品户转出
	public static final String SYS_LOAN_2_RETURN = "SYS_LOAN_2_RETURN"; //合作方回款户转入
	public static final String SYS_LOAN_2_REVENUE = "SYS_LOAN_2_REVENUE"; //合作方营收账户入账
	public static final String SYS_REVENUE_4_MARGIN = "SYS_REVENUE_4_MARGIN"; //合作方营收账户保证金转出
	public static final String SYS_REPAY_2_MARGIN = "SYS_REPAY_2_MARGIN"; //合作方保证金户入账
	public static final String SYS_REVENUE_4_SERVICE = "SYS_REVENUE_4_SERVICE"; //合作方营收账户服务费转出
	public static final String SYS_FEE_INCOME = "SYS_FEE_INCOME"; //系统手续费账户收入
	public static final String SYS_LOAN_2_MARGIN = "SYS_LOAN_2_MARGIN"; //合作方保证金逾期补入
	public static final String SYS_REVENUE_4_OVERDUE = "SYS_REVENUE_4_OVERDUE"; //币港湾营收账户滞纳金收入
	public static final String SYS_BGW_REVENUE_IN_4_COMP = "SYS_BGW_REVENUE_IN_4_COMP"; //币港湾营收账户逾期垫付入账
	public static final String SYS_BGW_REVENUE_OUT_4_COMP = "SYS_BGW_REVENUE_OUT_4_COMP"; //币港湾营收账户逾期垫付出账
	public static final String SYS_TOP_UP_4_MARGIN = "SYS_TOP_UP_4_MARGIN"; //赞分期风险保证金系统充值
	public static final String SYS_WITHDRAW_2_DEP_REPAY_CARD = "WITHDRAW_2_DEP_REPAY_CARD"; //代付到还款专户记账
	public static final String SYS_THD_REPAY = "SYS_THD_REPAY"; //还款资金子账户入账
	public static final String SYS_THD_BGW_REVENUE_YUN = "SYS_THD_BGW_REVENUE_YUN"; //币港湾宝付体系-币港湾对云贷营收子账户
	public static final String SYS_THD_REVENUE_YUN = "SYS_THD_REVENUE_YUN"; //币港湾宝付体系-云贷营收子账户
	public static final String SYS_THD_BGW_REVENUE_ZAN = "SYS_HTD_HD_BGW_REVENUE_ZAN"; // 币港湾宝付体系-币港湾对赞分期营收子账户
	public static final String SYS_THD_BGW_REVENUE_ZAN_TEMP = "SYS_HTD_HD_BGW_REVENUE_ZAN"; // 币港湾宝付体系-币港湾对赞分期营收子账户临时户
	public static final String SYS_THD_REVENUE_ZAN = "SYS_THD_REVENUE_ZAN"; //币港湾宝付体系-赞分期营收子账户
	public static final String SYS_THD_REVENUE_7 = "SYS_THD_REVENUE_7"; //币港湾宝付体系-7贷营收子账户
	public static final String SYS_REPAY_2_INVESTOR_REVENUE = "REPAY_2_INVESTOR_REVENUE"; //标的还款营收
	public static final String SYS_REPAY_2_TARGET_REVENUE = "REPAY_2_TARGET_REVENUE"; //还款到标的营收
	public static final String SYS_REPAY_2_INVESTOR_AUTH = "REPAY_2_INVESTOR_AUTH"; //标的还款到站岗户
	public static final String SYS_DEP_REVENUE_YUN = "SYS_DEP_REVENUE_YUN"; //存管体系-币港湾对云贷营收子账户
	public static final String SYS_ACCOUNT_BGW_USER_FREEZE_ADD = "BGW_USER_FREEZE_ADD";//币港湾体系-存管用户余额户,冻结金额增加
	public static final String SYS_ACCOUNT_BGW_USER_FREEZE_SUB = "BGW_USER_FREEZE_SUB";//币港湾体系-存管用户余额户,冻结金额减少
	public static final String SYS_USER_REPAY_REPEAT_IN = "SYS_REPAY_REPEAT_IN"; //存管云贷用户重复还款入账
	public static final String SYS_ZSD_USER_REPAY_REPEAT_IN = "SYS_ZSD_REPAY_REPEAT_IN"; //存管赞时贷用户重复还款入账
	public static final String SYS_7_DAI_SELF_USER_REPAY_REPEAT_IN = "SYS_7_DAI_SELF_REPAY_REPEAT_IN"; //存管7贷用户重复还款入账
	public static final String SYS_ACCOUNT_DEP_JSH_OUT = "SYS_ACCOUNT_DEP_JSH_OUT"; //存管系统平台自有子账户减少
	public static final String SYS_ACCOUNT_BGW_RETURN_IN = "SYS_ACCOUNT_BGW_RETURN_IN_"; //存管定期产品回款户转入
	/*public static final String SYS_HEAD_FEE_4_YUN = "SYS_HEAD_FEE_4_YUN"; //砍头息计入系统账户FEE_YUN_INCR
	public static final String SYS_HEAD_FEE_4_ZSD = "SYS_HEAD_FEE_4_ZSD"; //砍头息计入系统账户FEE_ZSD_INCR
	public static final String SYS_HEAD_FEE_4_SEVEN = "SYS_HEAD_FEE_4_SEVEN";//砍头息计入系统账户7贷*/
	public static final String SYS_HEAD_FEE = "SYS_HEAD_FEE_"; //砍头息计入系统账户
	public static final String SYS_RED_2_AUTH = "SYS_RED_2_AUTH"; //红包户资金转站岗户
	public static final String SYS_RED_2_AUTH_OUT = "SYS_RED_2_AUTH_OUT"; //红包户资金转站岗户，红包户转出
	public static final String SYS_RED_2_AUTH_IN = "SYS_RED_2_AUTH_IN"; //红包户资金转站岗户，站岗户转入
	public static final String SYS_TRS_AUTH_IN = "SYS_TRS_AUTH_IN"; //债转-系统站岗户转入
	public static final String SYS_TRS_AUTH_OUT = "SYS_TRS_AUTH_OUT"; //债转-系统站岗户转出
	public static final String SYS_DEP_TOP_UP  = "SYS_DEP_TOP_UP"; //存管体系-系统充值
	public static final String SYS_DEP_WITHDRAW  = "SYS_DEP_WITHDRAW"; //存管体系-系统提现
	public static final String SYS_DEP_TRANS_ORDER = "SYS_DEP_TRANS_ORDER"; //存管体系-账户划转
	public static final String SYS_DEP_TRANS_2_PERSON = "SYS_DEP_TRANS_2_PERSON"; //存管体系-平台转账个人
	public static final String SYS_AUTH_QUIT = "SYS_AUTH_QUIT"; //赞分期VIP退出

	//用户记账流水交易代码
	public static final String USER_CARD_BUY_PRODUCT = "CARD_BUY_PRODUCT"; //卡购买
	public static final String USER_BALANCE_BUY_PRODUCT = "BALANCE_BUY_PRODUCT"; //余额购买
	public static final String USER_USE_REDPACKET = "USE_REDPACKET"; //红包抵用
	public static final String USER_TOP_UP = "TOP_UP"; //充值
	public static final String USER_FREEZE = "FREEZE"; //冻结
	public static final String USER_UNFREEZE = "UNFREEZE"; //解冻
	public static final String USER_RETURN_2_BALANCE  = "RETURN_2_BALANCE"; //回款到余额
	public static final String USER_RETURN_2_BANK_CARD = "RETURN_2_BANK_CARD"; //回款到银行卡
	public static final String USER_BONUS_2_BALANCE = "BONUS_2_BALANCE"; // 奖励金转余额
	public static final String USER_RECOMMEND_BONUS  = "RECOMMEND_BONUS"; //获得推荐奖励
	public static final String USER_BONUS_WITHDRAW = "BONUS_WITHDRAW"; //奖励金提现
	public static final String USER_BALANCE_WITHDRAW  = "BALANCE_WITHDRAW"; //余额提现
	public static final String USER_AUTH_BACK = "AUTH_BACK"; //站岗户资金回退
	public static final String USER_AUTH_ADD = "AUTH_ADD"; //站岗户资金授权
	public static final String USER_AUTH_REPAY_ADD = "AUTH_REPAY_ADD"; //标的还款到站岗户
	/*public static final String USER_AUTH_YUN_OUT = "AUTH_YUN_OUT"; //站岗户资金转出（云贷）
	public static final String USER_AUTH_ZSD_OUT = "AUTH_ZSD_OUT"; //站岗户资金转出（赞时贷）
	public static final String USER_AUTH_7_OUT = "USER_AUTH_7_OUT"; //站岗户资金转出（七贷贷）*/
	public static final String USER_AUTH_OUT = "AUTH_OUT_";//站岗户资金转出
	public static final String USER_RED_OUT = "AUTH_2_REG"; //站岗户资金转产品户
	public static final String USER_AUTH_2_REG = "AUTH_2_REG"; //站岗户资金转产品户
	public static final String USER_LOAN_FREEZE="LOAN_FREEZE"; //(借款申请授权)金额冻结
	public static final String USER_LOAN_UNFREEZE="LOAN_UNFREEZE"; //(借款申请失败)授权金额解冻
	public static final String USER_USER_BIND_CARD = "BIND_CARD";//用户绑卡
	public static final String USER_REG_2_AUTH = "REG_2_AUTH"; //产品户资金转站岗户
	public static final String USER_JSH_ADD ="USER_JSH_ADD"; //超级理财人债权转让产生利息增加到JSH
	public static final String USER_TOP_UP_FEE = "TOP_UP_FEE"; //手续费扣除
	public static final String USER_WITHDRAW_FEE = "WITHDRAW_FEE"; //手续费扣除
	public static final String USER_DIFF_MONEY_ADD = "DIFF_MONEY_ADD"; //补差额增加
	public static final String USER_AUTH_QUIT = "AUTH_QUIT"; //vip站岗户退出
	public static final String USER_DEP_2_PERSON = "DEP_2_PERSON"; //平台转账个人

	public static final String USER_TRANSFER_IN_SUB = "TRANSFER_IN_SUB"; //债权转让减少
	public static final String USER_TRANSFER_OUT_ADD = "TRANSFER_OUT_ADD"; //债权转让增加
	public static final String USER_COMPENSATE_DEP_JSH_ADD = "COMPENSATE_DEP_JSH_ADD"; //代偿人DEP_JSH增加
	public static final String USER_REPAY_REPEAT_OUT = "REPAY_REPEAT_OUT"; //云贷用户重复还款出账
	public static final String USER_LOAN_FEE_OUT = "LOAN_FEE_OUT"; //砍头息出账
	public static final String USER_REPAY_REVENUE_OUT = "REPAY_REVENUE_OUT"; //还款营收出账
	public static final String USER_DIFF_OUT  = "DIFF_OUT"; //利息补差减少
	public static final String USER_JSH_FREEZE_BALANCE_ADD ="USER_JSH_FREEZE_BALANCE_ADD"; //代偿人结算户金额冻结增加（本金代偿）
	public static final String USER_JSH_FREEZE_BALANCE_SUB ="USER_JSH_FREEZE_BALANCE_SUB"; //代偿人结算户金额冻结减少（本金代偿）
	public static final String USER_DEP_FIXED_QUIT_FILL_INTEREST = "DEP_FIXED_QUIT_FILL_INTEREST"; //存管固定期限产品退出补息
	public static final String USER_FILL_INTEREST_BONUS  = "FILL_INTEREST_BONUS"; //获得补息奖励金

	//借款用户记账流水表交易代码
	public static final String LN_USER_LOAN_OPEN = "LOAN_OPEN"; //借款子账户开户
	public static final String LN_USER_LOAN = "LOAN"; //借款
	public static final String LN_USER_REPAY = "REPAY"; //还款
	public static final String LN_USER_CUT_REPAY_2_BORROWER = "CUT_REPAY_2_BORROWER"; //代扣还款到借款人
	public static final String LN_USER_REPAY_2_DEP_TARGET = "REPAY_2_DEP_TARGET"; //借款人还款到标的
	public static final String LN_DEP_JSH_OPEN = "DEP_JSH_OPEN"; //存管体系余额子账户开户
	public static final String LN_DEP_JSH_ADD = "DEP_JSH_ADD"; //存管体系余额子账户+
	public static final String LN_DEP_JSH_SUB = "DEP_JSH_SUB"; //存管体系余额子账户-

	//订单表交易类型
	public static final String TRANS_CARD_BUY_PRODUCT = "CARD_BUY_PRODUCT"; //卡购买
	public static final String TRANS_BALANCE_BUY_PRODUCT = "BALANCE_BUY_PRODUCT"; //余额购买
	public static final String TRANS_RED_PACKET_USED = "RED_PACKET_USED"; //红包抵用转账
	public static final String TRANS_TOP_UP = "TOP_UP"; //充值
	public static final String TRANS_RETURN_2_USER_BANK_CARD = "RETURN_2_USER_BANK_CARD"; //普通产品回款到银行卡
	public static final String TRANS_RETURN_REG_D_2_USER_BANK_CARD = "RETURN_REG_D_2_USER_BANK_CARD"; //分期产品回款到银行卡
	public static final String TRANS_BALANCE_WITHDRAW  = "BALANCE_WITHDRAW"; //用户余额提现
	public static final String TRANS_BONUS_WITHDRAW  = "BONUS_WITHDRAW"; //用户奖励金提现
	public static final String TRANS_SYS_BUY_DAFY = "SYS_BUY_DAFY";//系统购买
	public static final String TRANS_SYS_PARTNER_REVENUE = "SYS_PARTNER_REVENUE";//系统合作方营收结算
	public static final String TRANS_USER_BIND_CARD = "BIND_CARD";//用户绑卡
	public static final String TRANS_USER_UN_BIND_CARD = "UN_BIND_CARD";//用户解绑卡
	public static final String TRANS_TOP_UP_FEE = "TOP_UP_FEE"; //充值手续费扣除
	public static final String TRANS_WITHDRAW_FEE = "WITHDRAW_FEE"; //提现手续费扣除
	//	public static final String TRANS_RETURN_2_USER_BALANCE = "RETURN_2_USER_BALANCE"; //普通产品回款到余额
//	public static final String TRANS_RETURN_REG_D_2_USER_BALANCE = "RETURN_REG_D_2_USER_BALANCE"; //分期产品回款到余额
	public static final String TRANS_DEP_FILL_INTEREST = "DEP_FILL_INTEREST"; //存管平台转个人（补息）
	public static final String TRANS_SYS_YUN_REPEAT = "SYS_YUN_REPEAT";//系统合作方营收结算-重复还款(云贷)
	public static final String TRANS_SYS_YUN_LOAN_FEE = "SYS_YUN_LOAN_FEE";//系统合作方营收结算-借款手续费(云贷)
	public static final String TRANS_SYS_YUN_REPAY_REVENUE = "SYS_YUN_REPAY_REVENUE";//系统合作方营收结算-还款营收(云贷)
	public static final String TRANS_SYS_ZSD_REPEAT = "SYS_ZSD_REPEAT";//系统合作方营收结算-重复还款(赞时贷)
	public static final String TRANS_SYS_ZSD_LOAN_FEE = "SYS_ZSD_LOAN_FEE";//系统合作方营收结算-借款手续费(赞时贷)
	public static final String TRANS_SYS_ZSD_REPAY_REVENUE = "SYS_ZSD_REPAY_REVENUE";//系统合作方营收结算-还款营收(赞时贷)

	public static final String TRANS_SYS_SEVEN_LOAN_FEE = "SYS_SEVEN_LOAN_FEE";//系统合作方营收结算-借款手续费(7贷)
	public static final String TRANS_SYS_SEVEN_REPEAT = "SYS_SEVEN_REPEAT";//系统合作方营收结算-重复还款(7贷)
	public static final String TRANS_SYS_SEVEN_REPAY_REVENUE = "SYS_SEVEN_REPAY_REVENUE";//系统合作方营收结算-还款营收(7贷)
	public static final String TRANS_REPAY = "REPAY";//还款

	public static final String TRANS_ZAN_COMPENSATE_TOP_UP = "ZAN_COMPENSATE_TOP_UP"; //赞分期代偿人充值
	public static final String TRANS_ZAN_COMPENSATE_WITHDRAW = "ZAN_COMPENSATE_WITHDRAW"; //赞分期代偿人提现
	public static final String TRANS_HFBANK_SYS_TOP_UP = "HFBANK_SYS_TOP_UP"; //恒丰系统充值
	public static final String TRANS_HFBANK_SYS_WITHDRAW = "HFBANK_SYS_WITHDRAW"; //恒丰系统提现
	public static final String TRANS_HFBANK_SYS_ACCOUNT_TRANSFER = "HFBANK_SYS_ACCOUNT_TRANSFER"; //恒丰系统账户划转
	public static final String TRANS_HFBANK_ADVANCE_TRANSFER = "HFBANK_ADVANCE_TRANSFER"; //恒丰系统垫付金人工调账
	public static final String TRANS_LOANER_SYS_WITHDRAW = "LOANER_SYS_WITHDRAW"; //管理台借款人提现
	public static final String TRANS_HFBANK_YUN_HEAD_FEE_TRANSFER = "HFBANK_YUN_HEAD_FEE_TRANSFER"; //云贷砍头息划转
	public static final String TRANS_HFBANK_ZSD_HEAD_FEE_TRANSFER = "HFBANK_ZSD_HEAD_FEE_TRANSFER"; //赞时贷砍头息划转
	//订单交易类型说明
	public static final String TRANS_TYPE_REPEAT_INSTR = "营收结算:重复还款";
	public static final String TRANS_TYPE_LOAN_FEE_INSTR = "营收结算:借款手续费";
	public static final String TRANS_TYPE_REPAY_REVENUE_INSTR = "营收结算:还款营收";

	//营收结算明细文件名前缀
	public static final String REVENUE_FILE_PREFIX_REPEAT = "repeat_repay"; //重复还款文件前缀
	public static final String REVENUE_FILE_PREFIX_LOAN_FEE = "loan_fee"; //借款手续费文件前缀
	public static final String REVENUE_FILE_PREFIX_REPAY_REVENUE = "repay_revenue"; //还款营收文件前缀

	//奖励金发放状态
	public static final String BONUS_GRANT_STATUS_INIT = "INIT"; //初始化（发放激活）
	public static final String BONUS_GRANT_STATUS_SUCC = "SUCC"; //发放成功
	public static final String BONUS_GRANT_STATUS_FAIL = "FAIL"; //发放失败
	public static final String BONUS_GRANT_STATUS_CLOSE = "CLOSE"; //发放注销
	//奖励金发放类型
	public static final String BONUS_GRANT_TYPE_ALL = "RECOMMEND_USER_TAKE_ALL";//推荐人拿所有奖励金（2018年1月1日之后同推荐人、购买人各拿部分奖励金）
	public static final String BONUS_GRANT_TYPE_PART = "EACH_TAKE_PART";//推荐人、购买人各拿部分奖励金
	public static final String BONUS_GRANT_TYPE_BUYER_PART = "BUYER_TAKE_PART";//购买人拿部分奖励金
	public static final String BONUS_GRANT_TYPE_BONUS_4_ACTIVITY = "BONUS_4_ACTIVITY"; //活动奖励

	//bs_daily_bonus表奖励金类型
	public static final String Daily_BONUS_TYPE_RECOMMEND = "RECOMMEND"; //推荐奖励
	public static final String Daily_BONUS_TYPE_ACTIVITY = "ACTIVITY"; //活动奖励
	public static final String Daily_BONUS_TYPE_DEP_FILL_INTEREST = "DEP_FILL_INTEREST"; //存管固定期限补息
	public static final String Daily_BONUS_TYPE_WITHDRAW = "BONUS_WITHDRAW"; //奖励金提现

	//短信记录流水表类型
	public static final String SMS_TYPE_BUSINESS = "BUSINESS"; //业务类短信
	public static final String SMS_TYPE_MARKET  = "MARKET"; //营销类短信
	public static final String SMS_TYPE_WELINK_MARKET  = "WELINK_MARKET"; //微网营销类短信

	//提现审核表审核状态
	public static final String WITHDRAW_TO_CHECK = "TO_CHECK"; //待审核
	public static final String WITHDRAW_PASS = "PASS"; //审核通过
	public static final String WITHDRAW_PASS_QUE = "PASS_QUE"; //审核通过，进入队列
	public static final String WITHDRAW_PASS_PROCESS = "PASS_PROCESS"; //审核通过，轮询处理中
	public static final String WITHDRAW_PASS_QUE_SUCC = "PASS_QUE_SUCC"; //审核通过，队列轮询提现通过
	public static final String WITHDRAW_NOT_PASS = "NOT_PASS"; //审核不通过

	//融宝绑卡状态
	public static final Integer REAPAL_BIND_YES = 1; //已绑定
	public static final Integer REAPAL_BIND_NO = 2; //未绑定

	//宝付绑定状态
	public static final Integer BAOFOO_BIND_YES = 1; //已绑定
	public static final Integer BAOFOO_BIND_NO = 2; //未绑定

	//渠道互转审核状态
	public static final String CHANNEL_CHECK_STATUS_TRANSFERED = "TRANSFERED";	//钱已到账
	public static final String CHANNEL_CHECK_STATUS_CONFIRMED = "CONFIRMED";	//财务已确认,可出发转账
	public static final String CHANNEL_CHECK_STATUS_FINISHED = "FINISHED";	//系统已转账

	//融宝接口需要的MAC地址
	public static final String REAPAL_MAC_ADDR = "00:16:3E:00:03:D8";
	//融宝接口需要的IP地址
	public static final String REAPAL_IP_ADDR = "121.43.116.175";

	// 债权关系渠道来源
	public static final String LOAN_RELATIVE_CANNEL_DAFY_OLD = "DAFY_OLD"; // 达飞1.0老后台
	public static final String LOAN_RELATIVE_CANNEL_DAFY_NEW = "DAFY_NEW"; // 达飞云贷2.0
	public static final String LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL = "DAFY_NEW_ALL"; //达飞云贷2.0-全量

	// 债权关系是否已还款
	public static final String LOAN_RELATIVE_IS_REPAY_Y = "Y"; // 已还款
	public static final String LOAN_RELATIVE_IS_REPAY_N = "N"; // 未还款

	// 达飞返回借款状态 0-未生效，1-作废(流标)，2-生效，9-结束(还款完成)  10-已转让
	public static final String DAFY_RETURN_LOAN_RELATIVE_STATE_0 = "0";
	public static final String DAFY_RETURN_LOAN_RELATIVE_STATE_1 = "1";
	public static final String DAFY_RETURN_LOAN_RELATIVE_STATE_2 = "2";
	public static final String DAFY_RETURN_LOAN_RELATIVE_STATE_9 = "9";
	public static final String DAFY_RETURN_LOAN_RELATIVE_STATE_10 = "10";

	//银行代码
	public static final String BANK_CMB_CODE = "CMB"; //招行

	//融宝银行卡认证(未认证需要调用卡密接口)
	public static final String REAPAL_CERTIFICATE_NO = "0"; //未认证
	public static final String REAPAL_CERTIFICATE_YES = "1"; //认证

	// 活动是否兑奖
	public static final Integer ACTIVITY_IS_RECEIVE_YES = 1;  // 已兑奖
	public static final Integer ACTIVITY_IS_RECEIVE_NO = 0; // 未兑奖
	// 活动是否中奖
	public static final Integer ACTIVITY_IS_WIN_YES = 1;  // 已中奖
	public static final Integer ACTIVITY_IS_WIN_NO = 0; // 未中奖

	//系统公告对应的接收者类型
	public static final String SMS_MESSAGE_RECEIVER_TYPE_NORMAL = "USER_NORMAL"; //普通用户
	public static final String SMS_MESSAGE_RECEIVER_TYPE_178 = "USER_178"; //钱报用户
	public static final String SMS_MESSAGE_RECEIVER_TYPE_KQ = "USER_KQ"; //柯桥用户

	//系统公告消息状态
	public static final String SMS_MESSAGE_STATUS_VISIBLE = "VISIBLE"; //可见
	public static final String SMS_MESSAGE_STATUS_INVISIBLE = "INVISIBLE"; //不可见

	// 系统新闻公告相关数据
	// 1、类型f
	public static final String NEWS_TYPE_NOTICE = "NOTICE";    // 公告
	public static final String NEWS_TYPE_NEWS = "NEWS";    // 新闻
	public static final String NEWS_TYPE_COMPANY_DYNAMIC = "COMPANY_DYNAMIC";    // 公司动态
	public static final String NEWS_TYPE_WFANS_ACTIVITY = "WFANS_ACTIVITY";   // 湾粉活动

	// 2、接收对象类型BGW,BGW178,BGWKQ,BGWHN,BGWRUIAN
	public static final String NEWS_RECEIVER_TYPE_BGW = "BGW";    // 币港湾
	public static final String NEWS_RECEIVER_TYPE_BGW178 = "BGW178";    // 178
	public static final String NEWS_RECEIVER_TYPE_BGWKQ = "BGWKQ";    // BGWKQ
	public static final String NEWS_RECEIVER_TYPE_BGWHN = "BGWHN";    // BGWHN
	public static final String NEWS_RECEIVER_TYPE_BGWRUIAN = "BGWRUIAN";    // 瑞安
	public static final String NEWS_RECEIVER_TYPE_BGWQD = "BGWQD";    // 七店
	// 3、状态
	public static final String NEWS_STATUS_PUBLISHED = "PUBLISHED";    // 已发布
	public static final String NEWS_STATUS_NOT_PUBLISH = "NOT_PUBLISH";    // 未发布
	public static final String NEWS_STATUS_REMOVED = "REMOVED";    // 已撤下

	//红包
	//1、用户红包状态
	public static final String RED_PACKET_STATUS_INIT = "INIT"; //未使用
	public static final String RED_PACKET_STATUS_BUYING = "BUYING"; //使用中
	public static final String RED_PACKET_STATUS_USED = "USED"; //已使用
	public static final String RED_PACKET_STTAUS_RETURN = "RETURN"; //已退回
	public static final String RED_PACKET_STATUS_OVER = "OVER";


	//2、红包申请审核状态
	public static final String RED_PACKET_APPLY_CHECK_STATUS_INIT = "INIT";//审核中
	public static final String RED_PACKET_APPLY_CHECK_STATUS_PASS = "PASS";//通过
	public static final String RED_PACKET_APPLY_CHECK_STATUS_REFUSE = "REFUSE";//拒绝


	//3、红包发放批次审核状态
	public static final String RED_PACKET_CHECK_STATUS_UNCHECKED = "UNCHECKED"; //待审核
	public static final String RED_PACKET_CHECK_STATUS_PASS = "PASS"; //已通过
	public static final String RED_PACKET_CHECK_STATUS_REFUSE = "REFUSE"; //不通过
	//手动红包发放状态
	public static final String RED_PACKET_MSG_STATUS_FINISHED = "FINISHED"; //已发送
	public static final String RED_PACKET_MSG_STATUS_NOT = "NOT"; //未发送


	//4、红包发放方式
	public static final String RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO = "AUTO"; //自动
	public static final String RED_PACKET_CHECK_DISTRIBUTE_TYPE_MANUAL = "MANUAL"; //手动

	//5、使用条件类型
	public static final String RED_PACKET_CHECK_USE_CONDITION_FULL_SUBTRACT = "FULL_SUBTRACT"; //满减

	//6、通知管道  加息券通知管道
	public static final String RED_PACKET_CHECK_NOTIFY_CHANNEL_WECHAT = "WECHAT"; //微信
	public static final String RED_PACKET_CHECK_NOTIFY_CHANNEL_SMS = "SMS"; //短信
	public static final String RED_PACKET_CHECK_NOTIFY_CHANNEL_APP = "APP"; //APP通知
	public static final int RED_PACKET_CHECK_NOTIFY_TYPE_SMS = 1; //短信
	public static final int RED_PACKET_CHECK_NOTIFY_TYPE_WECHAT = 2; //微信
	public static final int RED_PACKET_CHECK_NOTIFY_TYPE_APP = 3; //APP通知

	//7、自动红包触发条件类型
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_NEW_USER = "NEW_USER"; //新用户注册
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL = "BUY_FULL"; //购买满额
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_INVITE_FULL = "INVITE_FULL"; //邀请满额
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER = "318_GAME_OLD_USER"; //318老用户
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_WECHAT_MINI_PROGRAM = "WECHAT_MINI_PROGRAM"; //微信小程序
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_EXCHANGE_4MALL = "EXCHANGE_4MALL";//积分商城兑换
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_BIRTHDAY_BENEFIT = "BIRTHDAY_BENEFIT";//生日福利红包
	
	//红包策略-生日福利红包 
	public static final String AUTO_RED_PACKET_POLICY_TYPE_BIRTHDAY_BENEFIT = "生日福利";
	

	//8、自动红包有效期类型
	public static final String AUTO_RED_PACKET_VALID_TERM_TYPE_FIXED = "FIXED"; //固定时间段
	public static final String AUTO_RED_PACKET_VALID_TERM_TYPE_AFTER_RECEIVE = "AFTER_RECEIVE"; //发放后有效天数

	//9、自动红包规则可用状态
	public static final String AUTO_RED_PACKET_STATUS_AVAILABLE = "AVAILABLE"; //可用
	public static final String AUTO_RED_PACKET_STATUS_DISABLE = "DISABLE"; //不可用

	//10、红包申请可用状态
	public static final String AUTO_RED_PACKET_AAPLY_STATUS_INIT = "INIT"; //初始
	public static final String AUTO_RED_PACKET_AAPLY_STATUS_USING = "USING"; //使用中
	public static final String AUTO_RED_PACKET_AAPLY_STATUS_CANCEL = "CANCEL"; //已注销

	//ios的url
	public static final String IOS_URl = "https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewSoftware?id=1080904592&mt=8";

	//手动发放红包每次添加的预发放记录数
	public static int everyNum = 500;

	//渠道用户变量
	public static final String ALL_AGENT = "-1"; //所有渠道用户
	public static final String NON_AGENT = "0"; //非渠道用户

	// 318摇一摇 用户类型
	public static final String OLD_USER = "OLD";   // 老用户
	public static final String NEW_USER = "NEW";   // 新用户
	public static final int SHAKE_AGENT_ID = 32;   // 摇一摇渠道ID

	//banner 展示渠道 channel
	public static final String BANNER_CHANNEL_MICRO = "MICRO"; //H5端首页
	public static final String BANNER_CHANNEL_MICRO178 = "MICRO178"; //178-H5端首页
	public static final String BANNER_CHANNEL_MICRO_QD = "MICROQD"; //七店-H5端首页
	public static final String BANNER_CHANNEL_GEN_QD = "GENQD"; //七店-PC端首页
	public static final String BANNER_CHANNEL_GEN = "GEN"; //PC端首页
	public static final String BANNER_CHANNEL_GEN_FINANCE = "GENFINANCE"; //PC端理财页
	public static final String BANNER_CHANNEL_GEN178 = "GEN178"; // 178-PC端首页
	public static final String BANNER_CHANNEL_GENKQ = "GENKQ"; // 柯桥日报-PC端首页
	public static final String BANNER_CHANNEL_MICROKQ = "MICROKQ"; // 柯桥日报-H5端首页
	public static final String BANNER_CHANNEL_GENHN = "GENHN"; // 海宁日报-PC端首页
	public static final String BANNER_CHANNEL_MICROHN = "MICROHN"; // 海宁日报-H5端首页

	public static final String BANNER_CHANNEL_GENRUIAN = "GENRUIAN"; // 瑞安日报-PC端首页
	public static final String BANNER_CHANNEL_MICRORUIAN = "MICRORUIAN"; // 瑞安日报-H5端首页

	public static final String BANNER_CHANNEL_MICROJT= "MICROJT"; // 秦皇岛交通广播-H5端首页
	public static final String BANNER_CHANNEL_MICROXW = "MICROXW"; // 秦皇岛新闻891-H5端首页
	public static final String BANNER_CHANNEL_MICROTV = "MICROTV"; // 秦皇岛电视台今日报道-H5端首页
	public static final String BANNER_CHANNEL_MICROSTZY = "MICROSTZY"; // 视听之友-PC端首页
	public static final String BANNER_CHANNEL_GENSTZY = "GENSTZY"; // 视听之友-H5端首页
	public static final String BANNER_CHANNEL_MICROSJC = "MICROSJC"; // 1038私家车广播-H5端首页

	public static final String BANNER_CHANNEL_APP = "APP"; //APP端
	public static final String BANNER_CHANNEL_APP_START = "APP_START"; //APP端启动页

	//banner 展示位置
	public static final String BANNER_SHOWPAGE_INDEX = "INDEX"; //首页
	public static final String BANNER_SHOWPAGE_DEPSUBJECT = "DEPSUBJECT"; //存管专题页
	public static final String BANNER_SHOWPAGE_ZANSTAGES = "ZANSTAGES"; //赞分期
	public static final String BANNER_SHOWPAGE_ESTUARYPLAN = "ESTUARYPLAN"; //港湾计划
	public static final String BANNER_SHOWPAGE_REGISTER = "REGISTER"; //注册
	public static final String BANNER_SHOWPAGE_LOGIN = "LOGIN"; //登录
	public static final String BANNER_SHOWPAGE_FORGETPWD = "FORGET_PASSWORD"; //忘记密码
	public static final String BANNER_SHOWPAGE_LABEL_INVEST = "INVEST"; //展示标签-投资
	public static final String BANNER_SHOWPAGE_LABEL_USERENTRY = "USERENTRY"; //展示标签-注册/登录/忘记密码


	//banner 状态 status
	public static final String BANNER_STATUS_SHOW = "SHOW"; //显示
	public static final String BANNER_STATUS_HIDE = "HIDE"; //不显示

	public static final String BANNER_ADD_BUTTON_HIDDEN = "HIDDEN"; //banner增加按钮显示隐藏标记
	public static final String BANNER_DELETE_FLAG = "DELETEFLAG"; //APP端启动页删除标记


	public static final String TRANSTERMINAL_LOAN = "LOAN"; 	//借款端
	public static final String TRANSTERMINAL_FINANCE = "FINANCE"; 	//理财端


	//318大促新手标产品ID
	public static final Integer NEW_USER_PRODUCT_ID = 49;

	// 产品额度明细表 触发事件名称
	public static final String EVENT_REGISTER = "REGISTER";   // 注册触发
	public static final String EVENT_SHARE = "SHARE";   // 分享触发
	public static final String EVENT_RECOMMEND = "RECOMMEND";   // 邀请触发
	public static final String EVENT_REGISTER_CN = "注册初始化额度";
	public static final String EVENT_SHARE_CN = "分享获得额度";
	public static final String EVENT_RECOMMEND_CN = "邀请获得额度";

	public static final Double REGISTER_AMOUNT = 10000d;   // 每个注册未投资用户自动获得10000购买金额
	public static final Double EACH_SHARE_AMOUNT = 5000d;   // 分享活动给好友获得5000购买额度，最高20000
	public static final Double MAX_SHARE_AMOUNT = 20000d;   // 分享活动给好友获得5000购买额度，最高20000
	public static final Double RECOMMEND_AMOUNT = 20000d;   // 邀请好友注册可以获取20000购买额度


	//首页按钮显示文案类型
	public static final String INDEX_BUTTON_TEXT_TYPE_REGISTER = "REGISTER";   // 首页按钮文案显示免费注册
	public static final String INDEX_BUTTON_TEXT_TYPE_RED_POCKET_REGISTER = "POCKET_REGISTER";   //  首页按钮文案显示注册领红包

	//希财渠道ID
	public static final Integer XICAI_AGENT_ID = 30;

	//希财注册客户端类型
	public static final int TERMINAL_TYPE_USER_CSAI_MOBILE = 1; //手机端
	public static final int TERMINAL_TYPE_USER_CSAI_PC = 2; //pc端

	// 渠道ID
	public static final int LANDING_PAGE_AGENT_ID = 33;    // 广告落地页的渠道ID
	public static final int MANAGER_AGENT_ID = 34;    // 客户经理渠道ID

	//微信消息自动回复
	public static final String WX_AUTO_REPLY_TYPE_SUBSCRIBE = "SUBSCRIBE_REPLY"; //关注时回复
	public static final String WX_AUTO_REPLY_TYPE_KEY = "KEY_REPLY"; //关键字回复
	public static final String WX_REPLY_TYPE_AUTO = "AUTO_REPLY"; //自动回复

	//微信消息自动回复----消息类型
	public static final String WX_Msg_Type_text = "text"; //文本
	public static final String WX_Msg_Type_news = "news"; //图文

	//标的状态
	public static final Integer PRODUCT_STATUS_CHECK_NO = 1; //未审核
	public static final Integer PRODUCT_STATUS_CHECKING = 2; //审核中
	public static final Integer PRODUCT_STATUS_PASS_NO = 3; //未通过
	public static final Integer PRODUCT_STATUS_PUBLISH_NO = 4; //待发布
	public static final Integer PRODUCT_STATUS_PUBLISH_YES = 5; //未开放(已发布)
	public static final Integer PRODUCT_STATUS_OPENING = 6; //进行中
	public static final Integer PRODUCT_STATUS_FINISH = 7; //已完成

	//债权关系匹配表--还款状态
	public static final String BORROW_ING ="1"; //借款中
	public static final String REPAY_PART ="2"; //部分还款
	public static final String REPAY_ALL ="3"; //已还完

	//债权关系匹配表--备注
	public static final String REPAY_PART_AMOUNT ="归还部分本金"; //归还部分本金
	public static final String LOOP_LEND ="循环借出"; //循环借出
	public static final String HISTORY_LEND ="历史借款"; //历史借款

	//债权关系匹配金额变动表
	public static final String MATCH_AMOUNT_DEAL_STATUS_N = "N"; //未处理
	public static final String MATCH_AMOUNT_DEAL_STATUS_Y = "Y"; //已处理

	// 理财计划是否推荐
	public static final String PRODUCT_IS_SUGGEST_YES = "YES";    // 确认推荐
	public static final String PRODUCT_IS_SUGGEST_NO = "NO";    // 取消推荐

	//债权关系显示时间，标的开始时间比这个时间早的，不显示标的
	public static final String LOAD_MATCH_SHOW_TIME = "2016-05-11";

	// 产品展示端口
	public static final String PRODUCT_SHOW_TERMINAL_H5 = "H5";  // H5
	public static final String PRODUCT_SHOW_TERMINAL_PC = "PC";  // H5
	public static final String PRODUCT_SHOW_TERMINAL_PC_178 = "PC_178";  // H5
	public static final String PRODUCT_SHOW_TERMINAL_H5_178 = "H5_178";  // H5_178
	public static final String PRODUCT_SHOW_TERMINAL_APP = "APP";  // H5
	public static final String PRODUCT_SHOW_TERMINAL_PC_KQ = "PC_KQ";  // 柯桥
	public static final String PRODUCT_SHOW_TERMINAL_H5_KQ = "H5_KQ";  // 柯桥

	public static final String PRODUCT_SHOW_TERMINAL_PC_HN = "PC_HN";  // 海宁
	public static final String PRODUCT_SHOW_TERMINAL_H5_HN = "H5_HN";  // 海宁

	public static final String PRODUCT_SHOW_TERMINAL_PC_RUIAN = "PC_RUIAN";  // 瑞安
	public static final String PRODUCT_SHOW_TERMINAL_H5_RUIAN = "H5_RUIAN";  // 瑞安
	public static final String PRODUCT_SHOW_TERMINAL_H5_QD = "H5_QD";  // 七店H5
	public static final String PRODUCT_SHOW_TERMINAL_PC_QD = "PC_QD"; // 七店PC

	public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_JT = "H5_QHD_JT";  // H5 秦皇岛交通广播
	public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_XW = "H5_QHD_XW";  // H5 秦皇岛新闻891
	public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_TV = "H5_QHD_TV";  // H5 秦皇岛电视台今日报道
	public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_ST = "H5_QHD_ST";  // H5 视听之友
	public static final String PRODUCT_SHOW_TERMINAL_PC_QHD_ST = "PC_QHD_ST";  // PC 视听之友
	public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_SJC = "H5_QHD_SJC";  // H5 1038私家车广播

	//BNANNER展示编码
	public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_JT = "MICROJT";  // H5 秦皇岛交通广播
	public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_XW = "MICROXW";  // H5 秦皇岛新闻891
	public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_TV = "MICROTV";  // H5 秦皇岛电视台今日报道
	public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_ST = "MICROSTZY";  // H5 视听之友
	public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_PC_QHD_ST = "GENSTZY";  // PC 视听之友
	public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_SJC = "MICROSJC";  // H5 1038私家车广播

	// 母亲节活动起止时间 || 718活动
	public static final String MOTHER_DAY_ACTIVITY_START_TIME = "2016-07-18 00:00:00";   // 开始时间（包含）    2016-07-18 00:00:00
	public static final String MOTHER_DAY_ACTIVITY_END_TIME = "2016-07-21 00:00:00";   // 截止时间（不包含）    2016-07-21 00:00:00
	public static final int MOTHER_DRAW_CHANCE = 3; // 总共领取次数
	// 518理财活动起止时间
	public static final String FINANCE_DAY_518_ACTIVITY_START_TIME = "2016-05-18 00:00:00";   // 开始时间（包含）
	public static final String FINANCE_DAY_518_ACTIVITY_END_TIME = "2016-05-21 00:00:00";   // 截止时间（不包含）
	public static final int FINANCE_DAY_518_ACTIVITY_DRAW_CHANCE = 5; // 总共领取次数
	public static final double FINANCE_DAY_518_MINIMUM_INVESTMENT_AMOUNT = 5000d;   // 领用红包最低投资金额

	public static final Integer INDEX_PRODUCT_NUM_PER_PAGE = 5; // 首页查询总条数

	//产品类型
	public static final String PRODUCT_ACTIVITY_TYPE_NORMAL = "NORMAL"; //普通产品
	public static final String PRODUCT_ACTIVITY_TYPE_NEW_BUYER = "NEW_BUYER"; //新手标
	public static final String PRODUCT_ACTIVITY_TYPE_ACTIVITY = "ACTIVITY"; //活动标

	public static final int AGENT_QIANBAO_ID = 15; //钱报在agent表中的id
	public static final int AGENT_KQ_ID = 36; //柯桥日报在agent表中的id
	public static final int AGENT_HN_ID = 46; //海宁日报在agent表中的id
	public static final int AGENT_RA_ID = 47; //瑞安日报在agent表中的id

	//管理台用户类型
	public static final String M_USER_TYPE_NORMAL = "NORMAL";
	public static final String M_USER_TYPE_CUSTOMER_MANAGER = "CUSTOMER_MANAGER";

	//客户经理状态
	public static final String CUSTOMER_MANAGER_STATUS_NO_ENTRY = "NO_ENTRY"; //未入职
	public static final String CUSTOMER_MANAGER_STATUS_ENTRY = "ENTRY"; //在职
	public static final String CUSTOMER_MANAGER_STATUS_DISMISS = "DISMISS"; //离职


	//6-18活动开始结束时间
	public static final String ACTIVITY_START_TIME="2016-6-17 00:00:00";
	public static final String ACTIVITY_END_TIME="2016-6-24 00:00:00";

	public static final int CRITICAL_SUPPORT_NUM = 20;   // 临界助力值

	public static final int OLD_MAN_AGE_LIMIT = 70;    // 老年人的年龄下限


	//资金接收方标识
	public static final String PROPERTY_SYMBOL_YUN_DAI="YUN_DAI"; //云贷
	public static final String PROPERTY_SYMBOL_7_DAI="7_DAI"; //七贷
	public static final String PROPERTY_SYMBOL_ZAN="ZAN"; //赞分期
	public static final String PROPERTY_SYMBOL_YUN_DAI_SELF="YUN_DAI_SELF"; //云贷自主放款
	public static final String PROPERTY_SYMBOL_ZSD="ZSD"; //赞时贷
	public static final String PROPERTY_SYMBOL_7_DAI_SELF="7_DAI_SELF"; //七贷自主放款

	//资金接收方标识
	public static final String PRODUCT_PROPERTY_SYMBOL_YUN_DAI = "YUN_DAI";  // 云贷
	public static final String PRODUCT_PROPERTY_SYMBOL_7_DAI = "7_DAI";  // 7贷
	public static final String PRODUCT_PROPERTY_SYMBOL_ZAN = "ZAN";  // 赞分期
	public static final String PRODUCT_PROPERTY_SYMBOL_BGW = "BGW";  // 币港湾
	public static final String PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF="YUN_DAI_SELF"; //云贷自主放款
	public static final String PRODUCT_PROPERTY_SYMBOL_ZSD="ZSD"; //赞时贷
	public static final String PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF="7_DAI_SELF"; //七贷存管
	public static final String PRODUCT_PROPERTY_SYMBOL_MAIN="MAIN"; //主商户号
	public static final String PRODUCT_PROPERTY_SYMBOL_FREE="FREE"; //自由资金

	//借款用户账户信息表状态
	public static final String LN_ACCOUNT_STATUS_NORMAL="NORMAL";
	public static final String LN_ACCOUNT_STATUS_CANCEL="CANCEL";

	//宝付下一步进行的交易子类
	public static final String BAOFOO_NEXT_TXN_SUB_TYPE_BIND_CARD = "01";	// 实名建立绑定关系类交易
	public static final String BAOFOO_NEXT_TXN_SUB_TYPE_UN_BIND = "02";	// 解除绑定关系类交易
	public static final String BAOFOO_NEXT_TXN_SUB_TYPE_QUERY_BIND = "03";	// 查询绑定关系类交易
	public static final String BAOFOO_NEXT_TXN_SUB_TYPE_PAY = "04";	// 支付类交易
	public static final String BAOFOO_NEXT_TXN_SUB_TYPE_SEND_SMS = "05";	// 发送短信类交易
	public static final String BAOFOO_NEXT_TXN_SUB_TYPE_TRANS_STATUS_QUERY = "06";	// 交易状态查询类交易

	// gateway支付交易处理中返回码
	public static final String GATEWAY_PAYING_RES_CODE = "920003";


	//卡bin卡类型
	public static final String CARD_BIN_FUNCTYPE_JIEJI="JIE_JI";//借记卡
	public static final String CARD_BIN_FUNCTYPE_DAIJI="DAI_JI";//贷记卡


	// 订单产生端口
	public static final Integer PAY_ORDER_TERMINAL_H5 = 1;  // H5
	public static final Integer PAY_ORDER_TERMINAL_PC = 2;  // PC
	public static final Integer PAY_ORDER_TERMINAL_ANDROID = 3;  // 安卓
	public static final Integer PAY_ORDER_TERMINAL_IOS = 4;  // IOS


	//借贷关系表状态
	public static final String LOAN_RELATION_STATUS_PAYING = "PAYING"; //付款中
	public static final String LOAN_RELATION_STATUS_SUCCESS = "SUCCESS"; //成功
	public static final String LOAN_RELATION_STATUS_FAIL = "FAIL"; //失败
	public static final String LOAN_RELATION_STATUS_TRANSFERRED  = "TRANSFERRED"; //已转让
	public static final String LOAN_RELATION_STATUS_REPAID = "REPAID"; //已还完

	//赞分期还款计划表
	public static final String REPAY_SCHEDULE_STATUS_INIT = "INIT"; //未还款
	public static final String REPAY_SCHEDULE_STATUS_PART_REPAY = "PART_REPAY"; //部分还款
	public static final String REPAY_SCHEDULE_STATUS_REPAIED = "REPAIED"; //已还款
	public static final String REPAY_SCHEDULE_STATUS_LATE_NOT = "LATE_NOT"; //逾期未还款
	public static final String REPAY_SCHEDULE_STATUS_LATE_REPAIED = "LATE_REPAIED"; //逾期已还款

	//理财人还款计划表  还款状态
	public static final String FINANCE_REPAY_SATAE_INIT = "INIT"; //未还款/待回款
	public static final String FINANCE_REPAY_SATAE_REPAYING = "REPAYING"; //还款中/回款中
	public static final String FINANCE_REPAY_SATAE_REPAIED = "REPAIED"; //已还款/已回款
	public static final String FINANCE_REPAY_SATAE_FAIL = "FAIL"; //回款失败

	public static final String IS_SUPPORT_RED_PACKET_TRUE = "TRUE";
	public static final String IS_SUPPORT_RED_PACKET_FALSE = "FALSE";

	public static final String FEE_TYPE_FINANCE_TOPUP = "FINANCE_TOPUP";	// 理财用户充值
	public static final String FEE_TYPE_FINANCE_WITHDRAW = "FINANCE_WITHDRAW";	// 理财用户提现
	public static final String FEE_TYPE_LOAN = "LOAN";	// 借款用户借款
	public static final String FEE_TYPE_REPAY = "REPAY";	// 借款用户还款
	public static final String FEE_TYPE_PARTNER_MARKET_FEE = "PARTNER_MARKET_FEE";	// 营销代付提现
	public static final String FEE_TYPE_ZAN_REPAY_DK = "ZAN_REPAY_DK";	// 赞分期代扣
	public static final String FEE_TYPE_HF_FINANCE_WITHDRAW = "HF_FINANCE_WITHDRAW";	// 理财用户提现

	public static final String FEE_STATUS_COLLECT = "COLLECT";	// 收取
	public static final String FEE_STATUS_RETURN = "RETURN";	// 退回

	//还款计划表还款状态
	public static final String LN_REPAY_STATUS_INIT = "INIT";	// 未还款
	public static final String LN_REPAY_PART_REPAY = "PART_REPAY";	//  部分还款
	public static final String LN_REPAY_REPAIED = "REPAIED";	// 已还款
	public static final String LN_REPAY_LATE_NOT = "LATE_NOT";	// 逾期未还款
	public static final String LN_REPAY_LATE_REPAIED = "LATE_REPAIED";	// 逾期已还款
	public static final String LN_REPAY_CANCELLED = "CANCELLED"; //已废除

	//还款过程状态
	public static final String LN_REPAY_PAY_STATUS_REPAYING = "REPAYING";	// 还款中
	public static final String LN_REPAY_PAY_STATUS_REPAIED = "REPAIED";	//  还款成功
	public static final String LN_REPAY_PAY_STATUS_REPAY_FAIL = "REPAY_FAIL";	// 还款失败

	public static final String REPAY_ADVANCE = "ADVANCE";	// 已垫付

	//存管还款计划表还款类型
	public static final String LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY = "NORMAL_REPAY";
	public static final String LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT = "LATE_NOT";
	public static final String LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_REPAY= "LATE_REPAY";

	//宝付间转账 订单交易处理状态
	public static final String BAOFOO_ONLINE_TRANS_STATUS_PAYING="0"; //转账中
	public static final String BAOFOO_ONLINE_TRANS_STATUS_SUCCESS="1"; //转账成功
	public static final String BAOFOO_ONLINE_TRANS_STATUS_FAIL="-1"; //转账失败
	public static final String BAOFOO_ONLINE_TRANS_STATUS_RETURN="2"; //转账退款

	public static final String BAOFOO_CHECK_ACCOUNT_TYPE_FI="fi";//宝付对账收单类型
	public static final String BAOFOO_CHECK_ACCOUNT_TYPE_FO="fo";//宝付对账出款类型

	public static final String SUBJECT_PRINCIPAL = "PRINCIPAL";// 本金
	public static final String SUBJECT_INTEREST = "INTEREST";// 利息
	public static final String SUBJECT_LATE_FEE = "LATE_FEE";//滞纳金
	public static final String SUBJECT_SUPERVISE_FEE = "SUPERVISE_FEE";//监管费
	public static final String SUBJECT_PREMIUM = "PREMIUM";//保费
	public static final String SUBJECT_OTHER = "OTHER";//保费
	public static final String SUBJECT_INFO_SERVICE_FEE = "INFO_SERVICE_FEE";//信息服务费
	public static final String SUBJECT_ACCOUNT_MANAGE_FEE = "ACCOUNT_MANAGE_FEE";//账户管理费
	public static final String SUBJECT_PRINCIPAL_OVERDUE = "PRINCIPAL_OVERDUE"; // 逾期本金
	public static final String SUBJECT_INTEREST_OVERDUE = "INTEREST_OVERDUE"; // 逾期利息

	// 赞时贷
	public static final String SUBJECT_CODE_PLATFORM_SERVICE_FEE = "PLATFORM_SERVICE_FEE";//平台服务费
	public static final String SUBJECT_CODE_INFO_CERTIFIED_FEE = "INFO_CERTIFIED_FEE";//信息认证费
	public static final String SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE = "RISK_MANAGE_SERVICE_FEE";//风控服务费
	public static final String SUBJECT_CODE_COLLECTION_CHANNEL_FEE = "COLLECTION_CHANNEL_FEE";//代收通道费

	public static final String AGENT_VIEW_FLAG_KQ = "KQ";	// 显示终端-柯桥
	public static final String AGENT_VIEW_FLAG_QB = "QB";	// 显示终端-钱报

	public static final int ACTIVITY_ID_DOUBLE11 = 2;	// 双十一活动ID
	public static Integer AWARD_RED_PACKET_ID = 13;

	//新用户不显示回款路径时间
	//public static final String RETURN_PATH_START_TIME="2016-11-11 00:00:00"; //新用户不显示回款路径开始时间
	public static final String RETURN_PATH_OVER_TIME="2017-01-01 00:00:00";//新老都用户不显示回款路径开始时间

	//管理台，客户经理业务，产品分类
	public static final String PRODUCT_TYPE_NORMAL ="NORMAL";//普通产品
	public static final String PRODUCT_TYPE_ZAN = "ZAN";//分期产品

	public static final String PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL = "FINISH_RETURN_ALL";	//到期一次性本息兑付(到期还本付息)
	public static final String PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST = "AVERAGE_CAPITAL_PLUS_INTEREST";	// 等额本息
	public static final String PRODUCT_RETURN_TYPE_TOTAL = "TOTAL";	// 还款方式全部


	//2016客户年终抽奖活动奖项
	public static final int ACTIVITY_END_OF_2016YEAR_AWARDS_LUCKY = 25;//幸运奖
	public static final int ACTIVITY_END_OF_2016YEAR_AWARDS_THIRD = 26;//三等奖
	public static final int ACTIVITY_END_OF_2016YEAR_AWARDS_SECOND = 27;//二等奖
	public static final int ACTIVITY_END_OF_2016YEAR_AWARDS_FIRST = 28;//一等奖
	public static final int ACTIVITY_END_OF_2016YEAR_AWARDS_GRAND = 29;//特等奖

	// 周周乐-刮刮乐活动奖品修改
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_TENTH = "5元刮刮乐红包"; //5元刮刮乐红包
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_NINTH = "20元刮刮乐红包";//20元刮刮乐红包
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_EIGHTH = "1元奖励金";//1元奖励金
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH = "2元奖励金";//2元奖励金
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH = "5元奖励金";//5元奖励金
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH = "10元奖励金";//10元奖励金
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_FORTH = "50元话费";//50元话费
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_THIRD = "100元京东购物卡";//100元京东购物卡
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_SECOND = "200元京东购物卡";//200元京东购物卡
	public static final String ACTIVITY_SCRATCH_CARD_AWARDS_FIRST = "黄金吊坠";//黄金吊坠

	public static final String ACTIVITY_SCRATCH_CARD_IS_SCRATCH_NO = "no";//刮刮乐活动-是否已刮奖


	//用户的起息日在此时间前转让成功，发送奖励金，否则不发送奖励金---设为上线日后一日
	public static final String ZAN_BONUSGRANT_DIFFERENT_DATE="2016-12-21";

	public static final String YES = "YES";
	public static final String NO = "NO";
	public static final String FAIL = "FAIL";
	public static final String SUCCESS = "SUCCESS";

	public static final String GO_INVEST = "GO_INVEST";
	public static final String CAN_NOT_JOIN = "CAN_NOT_JOIN";

	// 活动相关的参数
	public static final String IS_WIN_YES = "Y";	// 已经中奖
	public static final String IS_WIN_NO = "N";	// 未中奖
	public static final String IS_CONFIRM_YES = "Y";	// 已兑奖
	public static final String IS_CONFIRM_NO = "N";	// 未兑奖
	public static final String IS_BACK_DRAW_YES = "Y";	// 后台抽奖
	public static final String IS_BACK_DRAW_NO  = "N";	// 不是后台抽奖
	public static final String IS_USER_DRAW_YES = "Y";	// 用户抽奖
	public static final String IS_USER_DRAW_NO = "N";	// 不是用户抽奖
	public static final String IS_AUTO_CONFIRM_YES = "Y";	// 自动兑奖
	public static final String IS_AUTO_CONFIRM_NO = "N";	// 人工兑奖
	public static final String ACTIVITY_IS_START = "start";	// 已经开始
	public static final String ACTIVITY_IS_END = "end";	// 已经结束
	public static final String ACTIVITY_IS_NOT_START = "not_start";	// 未开始
	public static final String ACTIVITY_IS_NOT_START_PRE = "pre";	// 预热中
	public static final String ACTIVITY_IS_WILL_END = "will_end";	// 即将结束
	public static final String IS_LOGIN_YES = "yes";
	public static final String IS_LOGIN_NO = "no";
	public static final String IS_YES = "yes";
	public static final String IS_NO = "no";
	public static final String IS_EXPIRE = "expire";	// 已过期
	// 2017元宵喜乐会活动
	public static final String ACTIVITY_LAST_DIGITS_NUMBER = "8";	// 幸运灯笼尾号
	public static final Double ACTIVITY_INVEST_AMOUNT_LIMIT = 5000d;	// 投资金额≥5000元且幸运尾号是8，发放奖励金
	public static final int YUAN_XIAO_ACTIVITY_SHAKE_1 = 43;	// 1元摇一摇奖励金（bs_activity_award的id）
	public static final int YUAN_XIAO_ACTIVITY_SHAKE_2 = 44;	// 5元摇一摇奖励金（bs_activity_award的id）
	public static final int YUAN_XIAO_ACTIVITY_SHAKE_3 = 45;	// 5元摇一摇红包（bs_activity_award的id）
	public static final int YUAN_XIAO_ACTIVITY_SHAKE_4 = 46;	// 15元摇一摇红包（bs_activity_award的id）
	public static final int YUAN_XIAO_ACTIVITY_SHAKE_5 = 47;	// 25元摇一摇红包（bs_activity_award的id）
	public static final int YUAN_XIAO_ACTIVITY_BUY = 48;	// 5元投资满额奖励金（bs_activity_award的id）
	public static final String YUAN_XIAO_RED_PACKET_SERIAL_NO_32 = "S20170207171136";	// 5元摇一摇红包的批次号（bs_red_packet_check的serial_no）
	public static final String YUAN_XIAO_RED_PACKET_SERIAL_NO_33 = "S20170207171150";	// 15元摇一摇红包的批次号（bs_red_packet_check的serial_no）
	public static final String YUAN_XIAO_RED_PACKET_SERIAL_NO_34 = "S20170207171204";	// 25元摇一摇红包的批次号（bs_red_packet_check的serial_no）
	// 2017女神节
	public static final int ACTIVITY_AWARD_FOR_GIRL_TO_STORE = 49;	// 到店领取 花球和花瓶（bs_activity_award的id）
	public static final int ACTIVITY_AWARD_FOR_GIRL_EXPRESS = 50;	// 快递领取 花球（bs_activity_award的id）
	public static final String ACTIVITY_AWARD_FOR_GIRL_TYPE_TO_STORE = "to_store";	// 到店领取，花球和花瓶
	public static final String ACTIVITY_AWARD_FOR_GIRL_TYPE_EXPRESS = "express";	// 快递领取，花球
	// 2017周年庆活动
	public static final int ACTIVITY_ANNIVERSARY_GOLD_INIT_NUMBER = 1;	// 每个用户的初始化元宝个数
	public static final String ACTIVITY_ANNIVERSARY_FIRST_PERIOD_START_TIME = "2017-03-10 00:00:00";	// 周年庆活动第一期开始时间
	public static final String ACTIVITY_ANNIVERSARY_SECOND_PERIOD_START_TIME = "2017-03-14 00:00:00";	// 周年庆活动第二期开始时间
	public static final String ACTIVITY_ANNIVERSARY_THIRD_PERIOD_START_TIME = "2017-03-18 00:00:00";	// 周年庆活动第三期开始时间
	public static final String ACTIVITY_ANNIVERSARY_FIRST_PERIOD_END_TIME = "2017-03-24 23:59:59";	// 周年庆活动第一期结束时间
	public static final String ACTIVITY_ANNIVERSARY_SECOND_PERIOD_END_TIME = "2017-03-24 23:59:59";	// 周年庆活动第二期结束时间
	public static final String ACTIVITY_ANNIVERSARY_THIRD_PERIOD_END_TIME = "2017-03-24 23:59:59";	// 周年庆活动第三期结束时间
	public static final String GOLD_INGOT_UNLOCK_TIME = "2017-03-18";	// 元宝解锁时间
	public static final String GOLD_INGOT_UNLOCK_YES = "yes";	// 可以解锁
	public static final String GOLD_INGOT_UNLOCK_NO = "no";		// 不可以解锁
	public static final String ANNIVERSARY_RED_PACKET_SERIAL_NAME_5 = "周年庆5元红包";	// 周年庆红包名称（5元红包）
	public static final String ANNIVERSARY_RED_PACKET_SERIAL_NAME_10 = "周年庆10元红包";	// 周年庆红包名称（10元红包）
	public static final String ANNIVERSARY_RED_PACKET_SERIAL_NAME_20 = "周年庆20元红包";	// 周年庆红包名称（20元红包）
	public static final String BENISON_CHECK_STATUS_INIT = "INIT";		// 待审核
	public static final String BENISON_CHECK_STATUS_PASS = "PASS";		// 通过
	public static final String BENISON_CHECK_STATUS_REFUSE = "REFUSE";		// 拒绝
	public static final String BENISON_CHECK_STATUS_REMOVE = "REMOVE";		// 删除
	public static final int HELP_TOTAL_NUM_MAX = 60;			// 单个人最多助力60
	public static final Double GOLD_INGOT_AMOUNT = 5d;			// 一个元宝对应金额
	public static final Double GOLD_INGOT_UNLOCK_AMOUNT = 5000d;	// 元宝解锁金额
	public static final int ACTIVITY_ID_ANNIVERSARY_FIRST = 12;		// 一重礼活动ID
	public static final int ACTIVITY_ID_ANNIVERSARY_SECOND = 13;	// 二重礼活动ID
	public static final int ACTIVITY_ID_ANNIVERSARY_THIRD = 14;		// 三重礼活动ID
	public static final int ACTIVITY_ID_ANNIVERSARY_FOURTH = 15;	// 四重礼活动ID
	public static final int ACTIVITY_ID_ANNIVERSARY_FIFTH = 16;		// 五重礼活动ID
	public static final int ACTIVITY_ID_ANNIVERSARY_SIXTH = 17;		// 六重礼活动ID
	public static final int AWARD_ID_ANNIVERSARY_GOLD_INGOT = 51;	// 一重礼活动奖品ID-元宝
	public static final int AWARD_ID_ANNIVERSARY_GF = 52;	// 五重礼活动奖品ID-瓜分奖金
	public static final int AWARD_ID_ANNIVERSARY_EW_1 = 53;	// 五重礼活动奖品ID-额外奖金
	public static final int AWARD_ID_ANNIVERSARY_EW_2 = 54;	// 五重礼活动奖品ID-额外奖金
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_1 = 55;	// 六重礼活动奖品ID-投资红包5元（年化投资额0-3000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_2 = 56;	// 六重礼活动奖品ID-投资红包10元（年化投资额0-3000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_3 = 57;	// 六重礼活动奖品ID-投资红包20元（年化投资额0-15000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_4 = 58;	// 六重礼活动奖品ID-（年化投资额0-15000）奖励金1元
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_5 = 59;	// 六重礼活动奖品ID-（年化投资额3000-50000）奖励金3元
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_6 = 60;	// 六重礼活动奖品ID-（年化投资额3000-50000）奖励金5元
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_7 = 61;	// 六重礼活动奖品ID-奖励金10元（年化投资额15000-100000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_8 = 62;	// 六重礼活动奖品ID-奖励金15元（年化投资额15000-300000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_9 = 63;	// 六重礼活动奖品ID-奖励金20元（年化投资额15000-300000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_10 = 64;	// 六重礼活动奖品ID-奖励金30元（年化投资额50000-600000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_11 = 65;	// 六重礼活动奖品ID-奖励金50元（年化投资额100000-1200000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_12 = 66;	// 六重礼活动奖品ID-京东卡100元（年化投资额300000-1200000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_13 = 67;	// 六重礼活动奖品ID-京东卡200元（年化投资额300000-1200000）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_14 = 68;	// 六重礼活动奖品ID-京东卡500元（年化投资额600000及以上）
	public static final int AWARD_ID_ANNIVERSARY_OF_SIX_15 = 69;	// 六重礼活动奖品ID-京东卡1000元（年化投资额1200000及以上）
	// 钱报节约用水活动
	public static final int ACTIVITY_ID_WATER_SIGN_UP = 18;	// 节约用水活动ID
	public static final int ACTIVITY_ID_WATER_VOTE = 20;	// 节约用水投票活动ID
	//2017踏青活动
	public static final String ACTIVITY_SPRING_PRODUCT_NAME = "老用户专享"; //踏青活动产品名称
	// 2017 618活动
	public static final int ACTIVITY_ID_2017_618 = 21;	// 2017 618活动ID
	// 2017 周周乐-刮刮乐活动
	public static final int ACTIVITY_ID_2017_SCRATCH_CARD = 25;	// 2017 刮刮乐活动ID
	public static final int ACTIVITY_ID_2017_ANSWER = 22;	// 2017 存管后活动-答题活动ID
	public static final int ACTIVITY_ID_2017_TEAM = 23;	// 2017 存管后活动-抱团活动ID
	public static final int ACTIVITY_ID_2017_LIGHT = 24;	// 2017 存管后活动-点亮活动ID
	public static final Integer ACTIVITY_JOIN_TEAM_TIMES = 3;	// 2017 存管后活动-抱团活动-最大参与次数
	public static final String ACTIVITY_TEAM_A = "A";
	public static final String ACTIVITY_TEAM_B = "B";
	public static final String ACTIVITY_TEAM_C = "C";
	public static final String ACTIVITY_TEAM_D = "D";
	public static final String ACTIVITY_TEAM_E = "E";
	public static final String JOIN_TEAM_STATUS_FULL = "FULL"; // 次数已满（当前用户参与次数已经等于或超过三次）；
	public static final String JOIN_TEAM_STATUS_JOIN = "JOIN"; // 加入抱团；
	public static final String ACTIVITY_IS_LIGHT_UP = "light_up"; // 用户已点亮存管图标
	public static final String ACTIVITY_IS_NOT_LIGHT_UP = "not_light_up"; // 用户未点亮存管图标

	public static final String ACTIVITY_ANSWER_TITLE = "存管答题奖励红包";
	public static final int ACTIVITY_ANSWER_0 = 0;
	public static final int ACTIVITY_ANSWER_1 = 1;
	public static final int ACTIVITY_ANSWER_2 = 2;
	public static final int ACTIVITY_ANSWER_3 = 3;
	public static final int ACTIVITY_ANSWER_4 = 4;
	public static final int ACTIVITY_ANSWER_RED2 = 2;
	public static final int ACTIVITY_ANSWER_RED4 = 4;
	public static final int ACTIVITY_ANSWER_RED6 = 6;
	public static final int ACTIVITY_ANSWER_RED8 = 8;
	// 2017 友富同享邀请活动
	public static final int ACTIVITY_ID_2017_YOUFU_SHARE = 26;	// 2017 友富同享邀请活动
	// 2017 感恩节活动
	public static final int ACTIVITY_ID_2017_THANKSGIVING_DAY_SHARE = 27;	// 2017 感恩节分享活动ID
	public static final int ACTIVITY_ID_2017_THANKSGIVING_DAY_INVEST = 28;	// 2017 感恩节分享活动ID
	public static final int ACTIVITY_ID_2017_THANKSGIVING_DAY_LUCKY_NUMBER = 29;	// 2017 感恩节分享活动ID
	public static final int AWARD_ID_2017_SHARE_RED_5 = 82; // 感恩5元红包
	public static final int AWARD_ID_2017_SHARE_RED_30 = 83; // 感恩30元红包
	public static final int AWARD_ID_2017_SHARE_RED_75 = 84; // 感恩75元红包
	public static final int AWARD_ID_2017_SHARE_RED_350 = 85; // 感恩350元红包
	public static final int AWARD_ID_2017_INVEST_TERM_1_MAOJIN = 78; // 毛巾礼盒
	public static final int AWARD_ID_2017_INVEST_TERM_3_YANGSHENG = 79; // 养生壶
	public static final int AWARD_ID_2017_INVEST_TERM_6_TIEPI = 80; // 铁皮石斛礼盒
	public static final int AWARD_ID_2017_INVEST_TERM_12_YANWO = 81; // 燕窝礼盒
	public static final int AWARD_ID_2017_LUCKY_NUMBER= 86; // 100元奖励金
	public static final Double TERM_1_INVEST_LIMIT = 50000d; // 投资港湾计划短期乐≥5万元
	public static final Double TERM_3_INVEST_LIMIT = 100000d; // 投资港湾计划短期乐≥10万元
	public static final Double TERM_6_INVEST_LIMIT = 200000d; // 投资港湾计划短期乐≥20万元
	public static final Double TERM_12_INVEST_LIMIT = 500000d; // 投资港湾计划短期乐≥50万元
	public static final int TERM_1_GIFT_NUMBER = 50; // 投资港湾计划短期乐 礼品总个数 50
	public static final int TERM_3_GIFT_NUMBER = 30; // 投资港湾计划短期乐 礼品总个数 30
	public static final int TERM_6_GIFT_NUMBER = 20; // 投资港湾计划短期乐 礼品总个数 20
	public static final int TERM_12_GIFT_NUMBER = 10; // 投资港湾计划短期乐 礼品总个数 10

	// 2017平安夜湾粉抽奖活动
	public static final int ACTIVITY_ID_2017_CHRISTMAS_EVE = 30;	// 2017平安夜抽奖活动ID
	public static final int AWARD_ID_2017_CHRISTMAS_EVE_FIRST = 87;	// 2017平安夜抽奖活动一等奖
	public static final int AWARD_ID_2017_CHRISTMAS_EVE_SECOND = 88;	// 2017平安夜抽奖活动二等奖
	public static final int AWARD_ID_2017_CHRISTMAS_EVE_THIRD = 89;	// 2017平安夜抽奖活动三等奖
	public static final int AWARD_ID_2017_CHRISTMAS_EVE_LUCKY = 90;	// 2017平安夜抽奖活动幸运奖
	public static final int AWARD_TYPE_2017_CHRISTMAS_EVE_FIRST = 1;	// 2017平安夜抽奖活动一等奖
	public static final int AWARD_TYPE_2017_CHRISTMAS_EVE_SECOND = 2;	// 2017平安夜抽奖活动二等奖
	public static final int AWARD_TYPE_2017_CHRISTMAS_EVE_THIRD = 3;	// 2017平安夜抽奖活动三等奖
	public static final int AWARD_TYPE_2017_CHRISTMAS_EVE_LUCKY = 0;	// 2017平安夜抽奖活动幸运奖
	public static final String REDIS_KEY_CHRISTMAS_EVE_DRAW_TIMES = "draw_times";

	// 2018年邀请活动改造
	public static final int ACTIVITY_ID_2017_RECOMMEND = 31;

	// 2018年新年活动
	public static final int ACTIVITY_ID_2018_NEW_YEAR_FIRST = 33;
	public static final int ACTIVITY_ID_2018_NEW_YEAR_SECOND = 34;
	public static final int ACTIVITY_ID_2018_NEW_YEAR_THIRD = 35;
	public static final int AWARD_ID_2018_NEW_YEAR_FIRST = 105;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_18_88 = 106;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_18_188 = 111;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_18_888 = 115;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_20_88 = 107;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_20_188 = 112;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_20_888 = 116;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_22_88 = 108;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_22_188 = 113;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_22_888 = 117;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_24_88 = 109;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_24_188 = 114;
	public static final int AWARD_ID_2018_NEW_YEAR_SECOND_24_888 = 118;
	public static final int AWARD_ID_2018_NEW_YEAR_THIRD = 110;
	public static final String SYS_CONFIG_KEYNEW_YEAR_2018_TIMES = "NEW_YEAR_2018_TIMES";
	public static final String SYS_CONFIG_NEW_YEAR_2018_GIFTS_NUMBER = "NEW_YEAR_2018_GIFTS_NUMBER";

	// 2018年会抽奖活动
	public static final int ACTIVITY_ID_2018_YEAR_END = 32;
	public static final int AWARD_ID_2018_YEAR_END_FIRST = 101;	// 2018年会抽奖活动1等奖
	public static final int AWARD_ID_2018_YEAR_END_SECOND = 102;	// 2018年会抽奖活动2等奖
	public static final int AWARD_ID_2018_YEAR_END_THIRD = 103;	// 2018年会抽奖活动3等奖
	public static final int AWARD_ID_2018_YEAR_END_LUCKY = 104;	// 2018年会抽奖活动幸运奖

	//微信小程序财运大转盘
	public static final int ACTIVITY_ID_WECHAT_LUCKY_TURNING = 37;//微信小程序财运大转盘
	public static final int WECHAT_LUCKY_TURNING_CHANCE_TO_DRAW = 125;	// 抽奖机会
	public static final int WECHAT_LUCKY_TURNING_RED_PACKET_3 = 126;   	//3元财运红包
	public static final int WECHAT_LUCKY_TURNING_RED_PACKET_5 = 127;	//5元财运红包
	public static final int WECHAT_LUCKY_TURNING_RED_PACKET_10 = 128;	//10元财运红包
	public static final int WECHAT_LUCKY_TURNING_RED_PACKET_30 = 129;	//30元财运红包
	public static final int WECHAT_LUCKY_TURNING_RED_PACKET_50 = 130;	//50元财运红包
	public static final int WECHAT_LUCKY_TURNING_RED_PACKET_80 = 131;	//80元财运红包
	public static final int WECHAT_LUCKY_TURNING_INTEREST_TICKET_1 = 132;	//0.1%财运加息券
	public static final int WECHAT_LUCKY_TURNING_INTEREST_TICKET_2 = 133;	//0.2%财运加息券
	public static final String WECHAT_CHANCE_TO_DRAW_TYPE_LOGIN = "login";	//抽奖机会获取类型-登录
	public static final String WECHAT_CHANCE_TO_DRAW_TYPE_SHARE = "share";	// 抽奖机会获取类型-分享

	//加薪计划
	public static final int ACTIVITY_ID_SALARY_INCREASE_PLAN = 38;//加薪计划活动id
	public static final int SALARY_INCREASE_PLAN_BONUS_1 = 134;//加薪计划活动0.1%奖励金
	public static final int SALARY_INCREASE_PLAN_BONUS_2 = 135;//加薪计划活动0.2%奖励金
	public static final int SALARY_INCREASE_PLAN_BONUS_3 = 136;//加薪计划活动0.3%奖励金
	public static final int SALARY_INCREASE_PLAN_BONUS_4 = 137;//加薪计划活动0.4%奖励金
	
	public static final int SALARY_INCREASE_FIRST_RANK_AMOUNT =  10000;
	public static final int SALARY_INCREASE_SECOND_RANK_AMOUNT = 50000;
	public static final int SALARY_INCREASE_THIRD_RANK_AMOUNT =  100000;
	public static final int SALARY_INCREASE_FOURTH_RANK_AMOUNT = 500000;
	
	public static final double SALARY_INCREASE_FIRST_RANK_BONUS = 0.001;
	public static final double SALARY_INCREASE_SECOND_RANK_BONUS = 0.002;
	public static final double SALARY_INCREASE_THIRD_RANK_BONUS = 0.003;
	public static final double SALARY_INCREASE_FOURTH_RANK_BONUS = 0.004;

	public static final  Integer PAYMENT_PLANT_PAGE = 1;   //回款计划详情查询分页开始条数
	public static  final Integer PAYMENT_PLANT_PAGESIZE = 10; //详情查询详情查询分页大小
	//借贷关系表 债转标记
	public static final String TRANS_MARK_NORMAL = "NORMAL"; //正常
	public static final String TRANS_MARK_TRANS_IN = "TRANS_IN"; //转入

	//
	public static final String QUESTION_TYPE_RISK_ASSESS = "risk_assess";

	//存管定期产品到期退出类型
	public static final String DEP_QUIT_APPLY_USER = "USER"; //用户手工退出
	public static final String DEP_QUIT_APPLY_SYS = "SYS"; //到期系统自动退出

	//存管定期产品退出状态类型
	public static final String DEP_QUIT_APPLY_INIT = "INIT"; //初始申请状态
	public static final String DEP_QUIT_APPLY_PASS = "PASS"; //通过
	public static final String DEP_QUIT_APPLY_REFU = "REFU"; //拒绝
	public static final String DEP_QUIT_APPLY_PROCESS = "PROCESS"; //处理中

	public static final String DEP_QUIT_APPLY_FAIL = "RETURN_FAIL"; //失败
	public static final String DEP_QUIT_APPLY_RETURNED = "RETURNED"; //已结算


	//标的成废标识
	public static final String ESTABLISH_OR_ABANDON_FLAG_ABLISH = "2"; //成标
	public static final String ESTABLISH_OR_ABANDON_FLAG_ABANDON = "3"; //废标

	//批量投标   科目优先级0-可提优先1可投优先
	public static final String SUBJECT_PRIORITY_1 = "1";
	public static final String SUBJECT_PRIORITY_0 = "0";

	//分配账户类型(01-自有子账户 03-手续费子账户)
	public static final String PAYOUT_PLAT_TYPE_DEFAULT = "01";
	public static final String PAYOUT_PLAT_TYPE_FEE = "03";

	//存管标的状态
	public static final String DEP_TARGET_INIT = "INIT";	// 标的初始状态
	public static final String DEP_TARGET_PUBLISH = "PUBLISH";	// 标的发布
	public static final String DEP_TARGET_BID = "BID";	// 投标
	public static final String DEP_TARGET_SET_UP = "SET_UP";	// 标的成立
	public static final String DEP_TARGET_CHARGE_OFF = "CHARGE_OFF";	// 标的已出账
	public static final String DEP_TARGET_REPAID = "REPAID";	// 标的已还款
	public static final String DEP_TARGET_CANCELLED = "CANCELLED";	// 标的废除

	//存管标的操作流水表，操作类型
	public static final String TARGET_JNL_TRANS_TYPE_PROD_PUBLISH = "PROD_PUBLISH "; //标的发布
	public static final String TARGET_JNL_TRANS_TYPE_PROD_BID = "PROD_BID"; //投标
	public static final String TARGET_JNL_TRANS_TYPE_PROD_SET_UP = "PROD_SET_UP"; //标的成立
	public static final String TARGET_JNL_TRANS_TYPE_PROD_CANCELLED = "PROD_CANCELLED"; //标的废除
	public static final String TARGET_JNL_TRANS_TYPE_PROD_CHARGE_OFF = "PROD_CHARGE_OFF"; //标的出账
	public static final String TARGET_JNL_TRANS_TYPE_PROD_CHARGE_OFF_PRE = "CHARGE_OFF_PRE"; //标的出账申请
	public static final String TARGET_JNL_TRANS_TYPE_PROD_TRANSFER = "PROD_TRANSFER"; //标的转让
	public static final String TARGET_JNL_TRANS_TYPE_PROD_LOAN_REPAY = "PROD_LOAN_REPAY"; //借款人还款
	public static final String TARGET_JNL_TRANS_TYPE_PROD_REPAY = "PROD_REPAY"; //标的还款
	//存管标的操作流水表，操作状态
	public static final String TARGET_JNL_TRANS_STATUS_SUCC = "SUCC";//标的操作状态-成功
	public static final String TARGET_JNL_TRANS_STATUS_FAIL = "FAIL";//标的操作状态-失败

	//港湾相关订单号生成前缀
	public static final String BGW_ORDER_NUMBER_GENERATE_DEP_TARGET = "DEP";	// 标的订单号

	//存管业务返回码
	public static final String DEP_RECODE_SUCCESS = "10000";	// 业务请求成功

	//免息补账-补账类型
	public static final String FILL_DETAIL_FILL_TYPE_INTEREST = "INTEREST"; //利息补账

	//免息补账-补账状态
	public static final String FILL_DETAIL_FILL_STATUS_SUCCESS = "SUCCESS"; //补账完成
	public static final String FILL_DETAIL_FILL_STATUS_FAIL = "FAIL"; //补账失败

	//支付队列表处理状态
	public static final String PAY_RESULT_QUEUE_STATUS_SUCCESS = "SUCC";// 处理成功
	public static final String PAY_RESULT_QUEUE_STATUS_FAIL = "FAIL";// 处理失败

	//合作方补账记录-补账状态
	public static final String FILL_FILL_STATUS_INIT = "INIT"; //初始状态（待通知）
	public static final String FILL_FILL_STATUS_PROCESS = "PROCESS"; //待补账
	public static final String FILL_FILL_STATUS_SUCCESS = "SUCCESS"; //补账完成
	public static final String FILL_FILL_STATUS_FAIL = "FAIL"; //补账失败

	//恒丰出账状态
	public static final String HF_OUT_OF_ACCOUNT_SUCCESS = "1";	// 出账成功
	public static final String HF_OUT_OF_ACCOUNT_FAIL = "2";	// 出账失败

	//出账入账业务标识
	public static final String BUSINESS_FLAG_IN = "IN"; //入账
	public static final String BUSINESS_FLAG_OUT = "OUT"; //出账

	//存管标的操作状态
	public static final String DEP_TARGET_OPERATE_SUCC = "SUCC";	// 标的操作成功
	public static final String DEP_TARGET_OPERATE_FAIL = "FAIL";	// 标的操作失败

	//存管还款计划表还款处理标记
	public static final String DEP_REPAY_RETURN_FLAG_INIT = "INIT"; //初始
	public static final String DEP_REPAY_RETURN_FLAG_DF_PENDING = "DF_PENDING"; //代付到还款专户待处理
	public static final String DEP_REPAY_RETURN_FLAG_DF_PROCESS = "DF_PROCESS"; //代付到还款专户处理中
	public static final String DEP_REPAY_RETURN_FLAG_DF_SUCC = "DF_SUCC"; //代付到还款专户处理完成
	public static final String DEP_REPAY_RETURN_FLAG_DF_FAIL = "DF_FAIL"; //代付到还款专户处理失败
	public static final String DEP_REPAY_RETURN_FLAG_DK_PROCESS = "DK_PROCESS"; //代扣到融资人投资账户处理中
	public static final String DEP_REPAY_RETURN_FLAG_DK_SUCC = "DK_SUCC"; //代扣到融资人投资账户处理完成
	public static final String DEP_REPAY_RETURN_FLAG_DK_FAIL = "DK_FAIL"; //代扣到融资人投资账户处理失败
	public static final String DEP_REPAY_RETURN_FLAG_REPAY_SUCC = "REPAY_SUCC"; //借款人还款到标的处理成功
	public static final String DEP_REPAY_RETURN_FLAG_REPAY_PROCESS = "REPAY_PROCESS"; //借款人还款到标的处理中
	public static final String DEP_REPAY_RETURN_FLAG_REPAY_FAIL = "REPAY_FAIL"; //借款人还款到标的处理失败
	public static final String DEP_REPAY_RETURN_FLAG_RETURN_SUCC = "RETURN_SUCC"; //标的还款至投资人账户处理成功
	public static final String DEP_REPAY_RETURN_FLAG_RETURN_PROCESS = "RETURN_PROCESS"; //标的还款至投资人账户处理中
	public static final String DEP_REPAY_RETURN_FLAG_RETURN_FAIL = "RETURN_FAIL"; //标的还款至投资人账户处理失败

	//存管定期回款状态
	public static final String DEP_RETURN_STATUS_INIT = "INIT"; //初始状态
	public static final String DEP_RETURN_STATUS_PROCESS = "PROCESS"; //处理中
	public static final String DEP_RETURN_STATUS_SUCCESS = "SUCCESS"; //回款成功
	public static final String DEP_RETURN_STATUS_FAIL = "FAIL"; //回款失败

	//代偿明细表代偿状态
	public static final String COMPENSATE_REPAYS_STATUS_INIT = "INIT"; //初始
	public static final String COMPENSATE_REPAYS_STATUS_REPEAT = "REPEAT"; //重复还款
	public static final String COMPENSATE_REPAYS_STATUS_SUCC = "SUCC"; //代偿成功
	public static final String COMPENSATE_REPAYS_STATUS_FAIL = "FAIL"; //代偿失败

	//日结明细文件表 business_type
	public static final String BUSINESS_TYPE_ACCOUNT_FILL_DETAIL = "ACCOUNT_FILL_DETAIL"; //补账明细
	public static final String BUSINESS_TYPE_REPAY_REVENUE_DETAIL = "REPAY_REVENUE_DETAIL"; //还款营收明细
	public static final String BUSINESS_TYPE_HEAD_FEE_DETAIL = "HEAD_FEE_DETAIL"; //借款砍头息明细
	public static final String BUSINESS_TYPE_REPEAT_REPAY_DETAIL = "REPEAT_REPAY_DETAIL"; //重复还款明细

	//合作方补账记录-补账类型
	public static final String FILL_FILL_TYPE_INTEREST = "INTEREST"; //利息补账

	//补账明细文件名前缀
	public static final String WAIT_FILL_FILE = "wait_fill";

	//合作方营收明细类型
	public static final String REVENUE_TYPE_REPAY_INCOME ="REVENUE_INCOME";//还款时营收收入
	public static final String REVENUE_TYPE_OVERDUE_DEDUCT = "REVENUE_DEDUCT";//逾期垫付时营收扣除
	public static final String REVENUE_TYPE_HEAD_FEE_INCOME = "HEAD_FEE_INCOME";//借款砍头息收入
	public static final String REVENUE_TYPE_OVERDUE_REPAY_INCOME ="OVERDUE_REVENUE_INCOME";//逾期还款营收


	//存管固定期限理财退出申请表增申请状态类型
	public static final String INCREASE_QUIT_APPLY_INIT = "INIT"; //初始申请状态
	public static final String INCREASE_QUIT_APPLY_PASS = "PASS"; //通过
	public static final String INCREASE_QUIT_APPLY_REFU = "REFU"; //拒绝
	public static final String INCREASE_QUIT_APPLY_PROCESS = "PROCESS"; //处理中
	public static final String INCREASE_QUIT_APPLY_RETURNED = "RETURNED"; //已结算



	//币港湾相关订单号生成前缀
	public static final String BGW_ORDER_NUMBER_GENERATE_WITHHOLD = "BDK";//还款代扣
	public static final String BGW_ORDER_NUMBER_GENERATE_QUICK_PAY = "BQP";//还款快捷
	public static final String BGW_ORDER_NUMBER_GENERATE_OFFLINE_REPAY = "OFF";//线下还款

	//还款计划表--还款处理标记return_flag
	public static final String LN_REPAY_RETURN_FLAG_INIT = "INIT"; //初始-INIT
	public static final String LN_REPAY_RETURN_FLAG_PENDING = "PENDING"; //待处理-PENDING
	public static final String LN_REPAY_RETURN_FLAG_PROCESS ="PROCESS"; //处理中-PROCESS
	public static final String LN_REPAY_RETURN_FLAG_FINISH = "FINISH"; //处理完成-FINISH
	public static final String LN_REPAY_RETURN_FLAG_FAILED = "FAILED"; //处理失败-FAILED

	//云贷账单状态
	public static final String YUN_BILL_STATUS_INIT = "INIT"; //未还款-INIT
	public static final String YUN_BILL_STATUS_REPAYING = "REPAYING"; //还款中-REPAYING
	public static final String YUN_BILL_STATUS_REPAIED ="REPAIED"; //已还款-REPAIED
	public static final String YUN_BILL_STATUS_LATE_NOT = "LATE_NOT"; //逾期未还（催收中）-LATE_NOT
	public static final String YUN_BILL_STATUS_CANCELLED = "CANCELLED"; //废除-CANCELLED

	//还款类型
	public static final String REPAY_TYPE_USER_REPAY = "USER_REPAY";
	public static final String REPAY_TYPE_COMPENSATE = "COMPENSATE";

	//营收结算类型
	public static final String REVENUE_TYPE_REPEAT = "REPAY_REPEAT"; //重复还款
	public static final String REVENUE_TYPE_LOAN_FEE = "LOAN_FEE"; //借款手续费
	public static final String REVENUE_TYPE_REPAY_REVENUE = "REPAY_REVENUE"; //还款营收

	//营收转账记录表状态
	public static final String REVENUE_TRANS_INIT = "INIT"; //初始状态
	public static final String REVENUE_TRANS_PROCESS = "PROCESS"; //处理中
	public static final String REVENUE_TRANS_SUCCESS = "SUCCESS"; //成功
	public static final String REVENUE_TRANS_FAIL = "FAIL"; //失败

	//重复还款记录表，状态
	public static final String REPEAT_REPAY_STATUS_INIT = "INIT"; //初始状态
	public static final String REPEAT_REPAY_STATUS_PROCESS = "PROCESS"; //处理中
	public static final String REPEAT_REPAY_STATUS_SUCC = "SUCC"; //已结算退回
	public static final String REPEAT_REPAY_STATUS_FAIL = "FAIL"; //退回失败

	//支付结果队列状态
	public static final String PAY_RESULT_QUEUE_INIT = "INIT"; //未处理
	public static final String PAY_RESULT_QUEUE_PROCESS = "PROCESS"; //处理中
	public static final String PAY_RESULT_QUEUE_FAIL = "FAIL"; //处理失败
	public static final String PAY_RESULT_QUEUE_SUCC = "SUCC"; //处理成功

	public static final Integer DEP_REQUEST_ITEM_ACCOUNT = 60; //恒丰接口传入数据size最大值


	//代偿协议下载地址表 是否开放
	public static final String COMPENSATE_AGREEMENT_IS_OPEN_OPEN = "OPEN"; //开放下载
	public static final String COMPENSATE_AGREEMENT_IS_OPEN_CLOSE = "CLOSE"; //关闭下载

	public static final String WITHDRAW_ACCOUNT_TYPE_DEP = "DEP"; //存管账户
	public static final String WITHDRAW_ACCOUNT_TYPE_SIMPLE = "SIMPLE"; //普通账户



	//恒丰存管投资用户扩展表状态
	public static final String HFBANK_USER_EXT_STATUS_WAIT_ACTIVATE = "WAIT_ACTIVATE"; //待激活
	public static final String HFBANK_USER_EXT_STATUS_OPEN = "OPEN"; //已激活
	public static final String HFBANK_USER_EXT_STATUS_CANCEL = "CANCEL"; //注销

	// 恒丰存管页面引导相关状态以及账户类型
	public static final String HFBANK_GUIDE_NO_BIND_CARD = "NO_BIND_CARD"; // 未绑卡
	public static final String HFBANK_GUIDE_FAILED_BIND_HF = "FAILED_BIND_HF";	// 恒丰批量开户失败
	public static final String HFBANK_GUIDE_WAIT_ACTIVATE = "WAIT_ACTIVATE";// 待激活
	public static final String HFBANK_GUIDE_OPEN = "OPEN";//已激活
	public static final String HFBANK_GUIDE_ACCOUNT_TYPE_DEP = "DEP"; // 存管户
	public static final String HFBANK_GUIDE_ACCOUNT_TYPE_DOUBLE = "DOUBLE";	// 双帐户
	public static final String HFBANK_GUIDE_ACCOUNT_TYPE_SIMPLE = "SIMPLE";	// 普通账户。未开通存管户。

	public static final Integer HF_REPAY_NUM_ALL = 1; /**还款期数，如果一次性还款，repay_num为1*/

	//恒丰存管返回订单状态
	public static final String HF_ORDER_STATUS_SUCC = "2"; //2:处理成功
	public static final String HF_ORDER_STATUS_FAIL = "3"; //3:处理失败

	//标的代偿（委托）接口，还款还款类型 0-代偿还款 1-委托还款
	public static final String HF_COMPENSATE_REPAY_TYPE_COMPENSATE = "0";
	public static final String HF_COMPENSATE_REPAY_TYPE_ENTRUST = "1";

	//支付通道号
	public static final String HF_PAY_CODE_BAOFOO = "019"; //019宝付入金
	public static final String HF_PAY_CODE_BAOFOO_OUT = "219"; //宝付出金
	public static final String HF_PAY_CODE_HFBANK_OUT = "099"; //行内通道出金

	//科目优先级0-可提优先1可投优先 新增
	public static final String HF_TRANS_SUBJECT_PRIORITY_OUT = "0"; //0-可提优先
	public static final String HF_TRANS_SUBJECT_PRIORITY_IN = "1"; //可投优先

	//原因类型 1,2,3,4
	public static final String HF_CAUSE_TYPE_BONUS_1 = "1";	//奖励金发放	活动奖励
	public static final String HF_CAUSE_TYPE_BONUS_2 = "2";	//奖励金发放	推荐奖励
	public static final String HF_CAUSE_TYPE_FEE = "3";	//手续费返还
	public static final String HF_CAUSE_TYPE_RED = "4";	//现金红包

	public static final String HFBANK_ACCOUNT_TYPE_INVEST ="1";//投资人
	public static final String HFBANK_ACCOUNT_TYPE_FINANCE ="2";//融资人

	public static final String HFBANK_WITHDRAW_TYPE_INVEST ="0";//投资人
	public static final String HFBANK_WITHDRAW_TYPE_FINANCE ="1";//融资人

	public final static String VIP_QUIT_STATUS_INIT = "INIT";	// 待审核
	public final static String VIP_QUIT_STATUS_PASS = "PASS";	// 待退出
	public final static String VIP_QUIT_STATUS_REFU = "REFU";	// 拒绝
	public final static String VIP_QUIT_STATUS_QUIT_SUCC = "QUIT_SUCC";	// 退出成功
	public final static String VIP_QUIT_STATUS_QUIT_FAIL = "QUIT_FAIL";	// 退出失败

	//借款表是否还款中标识
	public final static String DEP_REPAY_AVAILABLE = "AVAILABLE";	// 可还款
	public final static String DEP_REPAY_REPAYING = "REPAYING";	// 还款中

	public static final String HF_CARD_TYPE_DEBIT = "1";	// 借记卡
	public static final String HF_CARD_TYPE_CREDIT = "2";	// 信用卡
	public static final String HF_BIND_CARD_TYPE_PERSON = "1";	// 绑卡类型-个人
	public static final String HF_BIND_CARD_TYPE_COMPANY = "2";	// 绑卡类型-对公


	//标的产品类型
	public final static String TARGET_PRODUCT_CYCLE = "0";	// 周期性产品
	public final static String TARGET_PRODUCT_NON_CYCLE = "1";	// 不定期出账产品

	//标的产品成立方式
	public final static String TARGET_PRODUCT_ESTABLISH_FULL = "0";	// 满额成立
	public final static String TARGET_PRODUCT_ESTABLISH_DAY = "1";	// 成立日成立
	public final static String TARGET_PRODUCT_ESTABLISH_CURRENT = "2";	// 活期方式

	//标的产品起息方式
	public final static String TARGET_PRODUCT_INTEREST_FULL = "0";	// 满额起息
	public final static String TARGET_PRODUCT_INTEREST_ESTABLISH = "1";	// 成立起息
	public final static String TARGET_PRODUCT_INTEREST_BID = "2";	// 投标起息
	public final static String TARGET_PRODUCT_INTEREST_CHECK = "3";	// 成立审核后起息

	//标的产品周期单位
	public final static String TARGET_PRODUCT_UNIT_DAY = "1";	// 日
	public final static String TARGET_PRODUCT_UNIT_WEEK = "2";	// 周
	public final static String TARGET_PRODUCT_UNIT_MONTH = "3";	// 月
	public final static String TARGET_PRODUCT_UNIT_SEASON = "4";	// 季
	public final static String TARGET_PRODUCT_UNIT_YEAR = "5";	// 年

	//标的还款方式
	public final static String TARGET_REPAY_TYPE_ONCE = "0";	// 一次性还款
	public final static String TARGET_REPAY_TYPE_PERIOD = "1";	// 分期还款

	//标的出账标示
	public final static String TARGET_OUT_ACCOUNT_AUTO = "0";	// 成标后自动出账至借款人融资账户
	public final static String TARGET_OUT_ACCOUNT_ACTIVE = "1";	// 调用成标出账接口，出账至标的对应

	//标的超额限制
	public final static String TARGET_OVER_NOT_LIMIT = "0";	// 不限制
	public final static String TARGET_OVER_LIMIT = "1";	// 限制

	//恒丰借款人卡类型
	public final static String HF_LOAN_CARD_TYPE_PERSONAL = "1";	// 个人
	public final static String HF_LOAN_CARD_TYPE_COMPANY = "2";	// 企业

	//公私标示(0-个人 1-公司 )
	public final static String HF_CLIENT_PROPERTY_PERSON = "0";	// 个人
	public final static String HF_CLIENT_PROPERTY_COMPANY = "1";	// 公司

	public final static String HF_CUST_PROPERTY_PERSON = "1";	// 个人
	public final static String HF_CUST_PROPERTY_COMPANY = "2";	// 公司

	public final static String HF_WITHDRAW_IS_ADVANCE_YES= "2";	// 垫资
	public final static String HF_WITHDRAW_IS_ADVANCE_NO = "1";	// 不垫资
	public final static String HF_ID_TYPE_ID_CARD = "1";	// 身份证
	public final static String HF_CHARGE_TYPE_INVITE = "1";	// 投资账户
	public final static String HF_CHARGE_TYPE_FINANCE = "2";	// 融资账户
	public final static String HF_TYPE_USER_RECHARGE = "1";	// 用户充值

	//借款方
	public final static String PARTNER_LOAN_YUN_SELF = "云贷借款";	// 云贷借款
	public final static String PARTNER_LOAN_ZAN = "赞分期借款";	// 赞分期借款
	public final static String PARTNER_LOAN_ZSD = "赞时贷借款";	// 赞时贷借款
	public final static String PARTNER_LOAN_7 = "7贷借款";	// 7贷借款

	public static final String WITHDRAW_CLEAR_MARK_YES_T_ADD_1 = "YES"; // 提现是否需要清算-是（T+1日清算）
	public static final String WITHDRAW_CLEAR_MARK_YES_T = "YES_T"; // 提现是否需要清算-是（T日清算）
	public static final String WITHDRAW_CLEAR_MARK_NO = "NO"; // 提现是否需要清算-否

	//恒丰批量开户明细编号前缀
	public static final String BATCH_REGIST_DETAIL_NO_PREFIX = "FM"; // 四要素开户明细编号前缀

	//是否清算
	public static final String LIQUIDATION_FLAG_YES = "1"; //已完成
	public static final String LIQUIDATION_FLAG_NO = "0"; //未完成

	public static final String LIQUIDATION_FLAG = "LIQUIDATION_FLAG"; //redis存放清算标识

	public static final String HF_PRECHECK_FLAG_NO = "0";	// 不是预对账
	public static final String HF_PRECHECK_FLAG_YES = "1";	// 预对账
	public static final String HF_CHECK_ACCOUNT_ORDER_TYPE_TOP_UP = "C";	// 充值
	public static final String HF_CHECK_ACCOUNT_ORDER_TYPE_WITHDRAW= "T";	// 提现

	//bs_sys_status表的transType
	public static final String SYS_STATUS_TRANS_TYPE_TRANSACTION_FINANCE = "transaction.finance"; //交易类型-理财端普通交易
	public static final String SYS_STATUS_TRANS_TYPE_TRANSACTION_LOAN = "transaction.loan"; //交易类型-借款端普通交易
	public static final String SYS_STATUS_TRANS_TYPE_SCHEDULE = "schedule"; //交易类型-定时
	//bs_sys_status表的statusValue
	public static final int SYS_STATUS_STATUS_VALUE_NORMAL = 1; //状态值 1-正常
	public static final int SYS_STATUS_STATUS_VALUE_HANG = 2; //状态值 2-挂起

	public static final String HF_WITHDRAW_TRAN_TYPE_PAY = "pay"; //批量提现交易类型-行内
	public static final String HF_WITHDRAW_TRAN_TYPE_PAY_REAL = "payReal"; //批量提现交易类型-代表跨行

	public static final String HF_NO_PLATCUST = "hf_no_platcust";


	public static final String SYS_SNAP_ACC_TYPE_HFBANK = "HFBANK"; // 系统余额快照记录表-账户类型-恒丰银行
	public static final String SYS_SNAP_ACC_TYPE_BGW = "BGW"; // 系统余额快照记录表-账户类型-币港湾
	public static final String SYS_SNAP_ACC_TYPE_CW_BAOFOO = "CW_BAOFOO"; // 系统余额快照记录表-账户类型-宝付，管理台财务需求
	public static final String SYS_SNAP_ACC_TYPE_CW_HF = "CW_HF"; // 系统余额快照记录表-账户类型-恒丰，管理台财务需求


	public static final Integer HAS_QUESTIONNAIRE = 1;   //已进行风险测评
	public static final Integer HAS_NOT_QUESTIONNAIRE = 0;	//未进行风险测评
	public static final Integer ONCE_MORE_QUESTIONNAIRE = 1;   //再次进行风险测评
	public static final Integer IS_EXPIRED_YES = 1;   //风险测评已过期
	public static final Integer IS_EXPIRED_NO = 0;   //风险测评已过期

	public static final Integer ASSESS_EXPIRED_DAYS = 365;   //风险测评过期天数

	public static final String CONSERVATIVE_TYPE_CN = "保守型"; //保守型
	public static final String STEADY_TYPE_CN = "稳健型"; //稳健型
	public static final String BALANCED_TYPE_CN = "平衡型"; //平衡型
	public static final String AGGRESSIVE_TYPE_CN = "进取型";//进取型
	public static final String RADICAL_TYPE_CN = "激进型";//激进型
	public static final String CONSERVATIVE_TYPE = "conservative"; //保守型
	public static final String STEADY_TYPE = "steady"; //稳健型
	public static final String BALANCED_TYPE = "balanced"; //平衡型
	public static final String AGGRESSIVE_TYPE = "aggressive";//进取型
	public static final String RADICAL_TYPE = "radical";//激进型

	//恒丰订单状态查询返回状态值（0-处理中 1-成功 2-失败 ）
	public static final String HF_QUERY_ORDER_STATUS_PAYING = "0";//处理中
	public static final String HF_QUERY_ORDER_STATUS_SUCCESS = "1";//成功
	public static final String HF_QUERY_ORDER_STATUS_FAIL = "2";//失败
	public static final String HF_QUERY_ORDER_STATUS_FAIL12 = "12";//失败
	public static final String HF_QUERY_ORDER_STATUS_FAIL22 = "22";//失败
	public static final String HF_QUERY_ORDER_STATUS_SUCCESS21 = "21";//成功

	public static final String MESSAGE_TYPE_NOTICE = "NOTICE"; //短信类型-通知
	public static final String MESSAGE_TYPE_MARKETING = "MARKETING"; //短信类型-营销

	//清算记录表清算状态值
	public static final String HF_LIQUIDATE_SUC = "SUCCESS";//清算成功
	public static final String HF_LIQUIDATE_ERR = "FAIL";//清算失败

	//恒丰请求平台/个人标识
	public static final String HF_REQ_PLATFORM = "01";//平台

	//用户中心-用户表用户状态
	public static final String UC_USER_OPEN = "OPEN";//正常
	public static final String UC_USER_CLOSE = "CLOSE";//注销
	public static final String UC_USER_FORBIDDEN = "FORBIDDEN";//禁用
	public static final String UC_USER_FREEZE = "FREEZE";//冻结 
	
	//用户中心-用户扩展表用户类型
	public static final String UC_USER_TYPE_BGW = "BGW";//币港湾
	public static final String UC_USER_TYPE_YUN = "YUN_DAI_SELF";//云贷
	public static final String UC_USER_TYPE_ZAN = "ZAN";//赞分期
	public static final String UC_USER_TYPE_ZSD = "ZSD";//赞时贷
	public static final String UC_USER_TYPE_7 = "7_DAI_SELF";//7贷

	//用户中心-用户绑卡表是否恒丰绑卡
	public static final String UC_BIND_CARD_YES = "Y";//是
	public static final String UC_BIND_CARD_NO = "N";//否

	//用户中心-用户绑卡表绑卡状态
	public static final String UC_BIND_CARD_STATUS_BINDING = "BINDING";//绑卡中
	public static final String UC_BIND_CARD_STATUS_BINDED = "BINDED";//已绑定
	public static final String UC_BIND_CARD_STATUS_UNBINDED = "UNBINDED";//已解绑
	public static final String UC_BIND_CARD_FAIL = "FAIL";//绑卡失败


	//钱报178APP通知传入证件类型
	public static final String QIANBAO_ID_CARD = "0";//居民身份证

	/* 产品提醒表——提醒类型：周周乐活动*/
	public static final String REMIND_TYPE_WEEKACTIVITY = "week_activity";

	//周周乐活动时间
	public static final Integer WEEKHAY_WEEK_3 = 3;
	public static final Integer WEEKHAY_WEEK_4 = 4;

	//周周乐活动url链接
	public static final String BANNER_URL_WEEKHAY_BGW_PC = "gen2.0/activity/weekhay_index";
	public static final String BANNER_URL_WEEKHAY_BGW_H5 = "micro2.0/activity/weekhay_index";
	public static final String BANNER_URL_WEEKHAY_APP = "micro2.0/activity/weekhay_index_app";

	public static final String WEEKHAY_WEEK_TIME_9_55_00 = "9:55:00";
	public static final String WEEKHAY_WEEK_TIME_10_00_00 = "10:00:00";
	public static final String WEEKHAY_WEEK_TIME_13_55_00 = "13:55:00";
	public static final String WEEKHAY_WEEK_TIME_14_00_00 = "14:00:00";
	public static final String WEEKHAY_WEEK_TIME_19_55_00 = "19:55:00";
	public static final String WEEKHAY_WEEK_TIME_20_00_00 = "20:00:00";
	
	//加薪计划活动url链接
	public static final String BANNER_URL_SALARY_INCREASE = "/activity/salary_increase_plan";

	public static final Integer LUCKY_LENDERS_ACTIVITY_ID = 36; //幸运出借人活动id
	public static final String WEEKHAY_LUCKY_LENDERS = "LUCKY_LENDERS"; //幸运出借人活动
	public static final String LUCKY_LENDERS_ACTIVITY_TIME = "LUCKY_LENDERS_ACTIVITY_TIME"; //幸运出借人活动阶段
	public static final String LUCKY_LENDERS_ACTIVITY_START_TIME = "LUCKY_LENDERS_ACTIVITY_START_TIME"; //幸运出借人活动开始时间

	public static final Integer LUCKY_LENDERS_BOUNS_50_ID = 119; //幸运出借人奖励金为50对应的奖品id
	public static final Integer LUCKY_LENDERS_BOUNS_10_ID = 120; //幸运出借人奖励金为10对应的奖品id
	public static final Integer LUCKY_LENDERS_BOUNS_100_ID = 121; //幸运出借人奖励金为100对应的奖品id
	public static final Integer LUCKY_LENDERS_BOUNS_15_ID = 122; //幸运出借人奖励金为15对应的奖品id
	public static final Integer LUCKY_LENDERS_BOUNS_150_ID = 123; //幸运出借人奖励金为150对应的奖品id
	public static final Integer LUCKY_LENDERS_BOUNS_20_ID = 124; //幸运出借人奖励金为20对应的奖品id

	//bs_user用户状态
	public static final Integer BS_USER_STATUS_NORMAL = 1;
	public static final Integer BS_USER_STATUS_LOGOUT = 2;
	public static final Integer BS_USER_STATUS_FORBID = 3;

	public static final String KEY_AUTH = "AUTH"; // 站岗户KEY
	public static final String KEY_RED = "RED"; // 红包户KEY
	public static final String KEY_DIFF = "DIFF"; // 补差户KEY
	public static final String KEY_SYS_AUTH = "SYS_AUTH"; // 补差户KEY
	public static final String KEY_SYS_RED = "SYS_RED"; // 补差户KEY


	public static final String CUNGUAN_UP_DATE = "2017-09-18"; //存管上线日期

	//线下还款各阶段是否完成redis标识(ING/FINISH)
	public static final String OFF_REPAY_DF = "OFF_REPAY_DF"; // 线下还款代付还款单次定时是否完成标识
	public static final String OFF_REPAY_DK = "OFF_REPAY_DK"; // 线下还款代扣还款单次定时是否完成标识
	public static final String OFF_REPAY_REPAY = "OFF_REPAY_REPAY"; // 线下还款还款到标的单次定时是否完成标识
	public static final String OFF_REPAY_RETURN = "OFF_REPAY_RETURN"; // 线下还款标的还款单次定时是否完成标识

	public static final String OFF_REPAY_REPEAT_DF = "OFF_REPAY_REPEAT_DF"; // 线下还款代付还款重发单次定时是否完成标识
	public static final String OFF_REPAY_REPEAT_DK = "OFF_REPAY_REPEAT_DK"; // 线下还款代扣还款重发单次定时是否完成标识
	public static final String OFF_REPAY_REPEAT_REPAY = "OFF_REPAY_REPEAT_REPAY"; // 线下还款还款到标的重发单次定时是否完成标识
	public static final String OFF_REPAY_REPEAT_RETURN = "OFF_REPAY_REPEAT_RETURN"; // 线下还款标的还款重发单次定时是否完成标识

	public static final String HF_BANK_UPDATE_USER_APPLY_STATUS_INIT = "INIT";
	public static final String HF_BANK_UPDATE_USER_APPLY_STATUS_SUCCESS = "SUCCESS";
	public static final String HF_BANK_UPDATE_USER_APPLY_STATUS_FAIL = "FAIL";

	//线下还款入参标识
	public static final String REQ_IS_OFFLINE_Y = "Y";
	public static final String REQ_IS_OFFLINE_N = "N";

	//代扣线下还款备注
	public static final String OFFLINE_ZSD_ORDER_NOTE = "赞时贷代扣线下还款";
	public static final String OFFLINE_7_SELF_ORDER_NOTE = "七贷代扣线下还款";
	public static final String OFFLINE_ZAN_ORDER_NOTE = "赞分期代扣线下还款";

	public static final String POSITION_NOTICE = "NOTICE";
	public static final String POSITION_ACTIVITY = "ACTIVITY";	//线下还款标识
	public static final String IS_OFFLINE_REPAY = "OFFLINE_REPAY";
	// banner展示页面
	public static final String SHOW_PAGE_INDEX = "INDEX";	// 首页
	public static final String SHOW_PAGE_LOGIN = "LOGIN";	// 登录
	public static final String SHOW_PAGE_REGISTER = "REGISTER";	// 注册
	public static final String SHOW_PAGE_FORGET_PASSWORD = "FORGET_PASSWORD";	// 忘记密码页面
	public static final String SHOW_PAGE_PRODUCT_LIST = "PRODUCT_LIST";	// 理财列表页面

	public static final String WITHDRAW_BLACK_USER = "WITHDRAW_BLACK_USER"; // 提现黑名单用户（存管前账户提现、存管后账户提现、奖励金提现）

	//币港湾宝付通道标识（辅助）
	public static final String BGW_BAOFOO_ASSIST = "BAOFOO_ASSIST";  // 币港湾宝付通道标识（辅助）

	//协议类型（协议版本控制）
	public static final String AGREEMENT_VERSION_TYPE_HF_YUN_LOAN = "HF_YUN_LOAN_AGREEMENT"; // 存管云贷借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN = "hf_7dai_loanAgreement"; // 存管7贷借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_7DAI_CLAIMS = "hf_7dai_claimsAgreement"; // 存管7贷债转协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN = "stock_hf_7dai_loanAgreement"; // 存量数据七贷存管借款协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_CLAIMS = "stock_hf_7dai_claimsAgreement"; // 存量数据七贷存管债转协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN = "stock_hf_yundai_loanAgreement"; // 存量数据云贷存管借款协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_CLAIMS = "stock_hf_yundai_claimsAgreement"; // 存量数据云贷存管债转协议
	public static final String AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY = "hf_7dai_loanAgreement_anyRepay"; // 存管7贷随借随还借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT = "hf_yundai_loanAgreement_installment"; // 存管云贷等额本息产品借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST = "hf_yundai_loanAgreement_principal_interest"; // 存管云贷等本等息产品借款协议

	//协议版本号
	public static final String AGREEMENT_VERSION_NUMBER_NO_VERSION = "NO_VERSION"; // 版本号不存在（老协议）

	//存量数据迁移7贷、云贷大借款人身份证号（存量数据迁移生成的债权记录，借款、债转协议不显示）
	public static final String STOCK_7DAI_LOAN_ID_CARD_NO = "232603197612176639"; // 7贷大借款人身份证号
	public static final String STOCK_YUNDAI_LOAN_ID_CARD_NO = "130303198508280324"; // 云贷大借款人身份证号

	public static final String REPAY_TYPE_NORMAL_REPAY = "NORMAL_REPAY"; // 正常还款
	public static final String REPAY_TYPE_LATE_REPAY = "LATE_REPAY"; // 逾期还款
	public static final String REPAY_TYPE_LATE_NOT = "LATE_NOT"; // 代偿

	//签章定时重发条数
	public static final String SIGN_SEAL_REPEAT_SEND_NUM = "SIGN_SEAL_REPEAT_SEND_NUM";

	//查询频次优化中最大查询条数
	public static final String CHECK_MAX_COUNT = "CHECK_MAX_COUNT";
	//查询频次优化中时间段
	public static final String CHECK_PERIOD_OF_TIME = "CHECK_PERIOD_OF_TIME";

	public static final String SESSION_STATUS_LOGIN = "login";		// 是否登录状态-是
	public static final String SESSION_STATUS_LOGOUT = "logout";	// 是否登录状态-否
	public static final String SESSION_FORCED_LOGOUT_YES = "yes";	// 是否强制退出-是
	public static final String SESSION_FORCED_LOGOUT_NO = "no";		// 是否强制退出-否
	public static final String SESSION_TERMINAL_PC = "PC";
	public static final String SESSION_TERMINAL_H5 = "H5";
	public static final String SESSION_TERMINAL_IOS = "IOS";
	public static final String SESSION_TERMINAL_ANDROID = "ANDROID";
	public static final String CONFIG_KEY_SYS_MAX_SESSION_NUMBER = "SYS_MAX_SESSION_NUMBER";// 最大会话数（PC和H5）
	public static final String CONFIG_KEY_SAME_IP_SINGLE_MAX_SESSION_NUMBER = "SAME_IP_SINGLE_MAX_SESSION_NUMBER";// 相同IP，单个最大会话数
	public static final String CONFIG_KEY_DIFFERENT_IP_SINGLE_MAX_SESSION_NUMBER = "DIFFERENT_IP_SINGLE_MAX_SESSION_NUMBER";// 不同IP，单个最大会话数
	public static final String CONFIG_KEY_SYS_MAX_DEVICE_NUMBER = "SYS_MAX_DEVICE_NUMBER";// 最大终端数（安卓和IOS）
	public static final String CONFIG_KEY_SAME_IP_SINGLE_MAX_DEVICE_NUMBER = "SAME_IP_SINGLE_MAX_DEVICE_NUMBER";// 相同IP，单个最大终端数
	public static final String CONFIG_KEY_DIFFERENT_IP_SINGLE_MAX_DEVICE_NUMBER = "DIFFERENT_IP_SINGLE_MAX_DEVICE_NUMBER";// 不同IP，单个最大终端数

	public static final String TERMINATION_BREACH_CONTRACT_RATE = "TERMINATION_BREACH_CONTRACT_RATE"; // 购买的云贷、7贷理财产品提前终止违约金百分比
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY = "hf_yundai_powerAttorney"; // 存管云贷授权委托书
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_POWERATTORNEY = "hf_7dai_powerAttorney"; // 存管7贷授权委托书
	public static final String AGREEMENT_VERSION_NUMBER_1_1 = "1_1"; // 协议版本号

	public static final Integer BS_PRODUCT_PROPERTY_ID_4 = 4; //接收资金方编号 4 YUN_DAI_SELF 存管云贷
	public static final Integer BS_PRODUCT_PROPERTY_ID_6 = 6; //接收资金方编号 6 7_DAI_SELF 存管7贷
	public static final Integer BS_PRODUCT_PROPERTY_ID_7 = 7; //接收资金方编号 7 FREE 自由计划

	public static final String PLATFORM_DATA_REDIS_KEY_PLATFORMOVERVIEW = "platformOverview";
	public static final String PLATFORM_DATA_REDIS_KEY_TRANSACTIONLENDDATASTATISTICS = "transactionLendDataStatistics";
	public static final String PLATFORM_DATA_REDIS_KEY_USERDATA = "userData";
	public static final String PLATFORM_DATA_REDIS_KEY_OVERDUEINFO = "overdueInfo";

	//代偿协议生成状态
	public static final String AGREEMENT_GRNERATE_STATUS_SUCC = "SUCC";
	public static final String AGREEMENT_GRNERATE_STATUS_FAIL = "FAIL";

	public static final Integer AGENT_ID_QD = 49;	// 七店渠道ID
	public static final String SEVEN_DIAN_DEFAULT_SALES_ID_KEY = "7_DIAN_DEFAULT_SALES_ID";	// 未填写邀请码时，默认的币港湾销售人员ID
	//随借随还产品
	public static final String PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME = "REPAY_ANY_TIME";

	public static final String SIGN_FILE_QUEUE_KEY = "signFiles"; //签章文件队列key
	public static final String MORE_SIGN_FILE_QUEUE_KEY = "MORE_SIGN_FILES"; // 同时签章多份文件队列key

	//存订单表的，是否签约字段
// 优惠券-加息券
	public static final String TICKET_INTEREST_BIZ_TYPE_BUY = "BUY";	// 业务类型：BUY-投资加入；TICKET-优惠券列表
	public static final String TICKET_INTEREST_BIZ_TYPE_TICKET = "TICKET";	// 业务类型：BUY-投资加入；TICKET-优惠券列表
	public static final String TICKET_INTEREST_TYPE_RED_PACKET = "RED_PACKET";	// 红包

	// 卡券类型
	public static final String TICKET_INTEREST_TYPE_INTEREST_TICKET = "INTEREST_TICKET";	// 加息券

	// 加息券使用状态
	public static final String TICKET_INTEREST_STATUS_INIT = "INIT";	// 加息券使用状态INIT-未使用；USED-已使用；OVER-已过期
	public static final String TICKET_INTEREST_STATUS_USED = "USED";	// 加息券使用状态INIT-未使用；USED-已使用；OVER-已过期
	public static final String TICKET_INTEREST_STATUS_OVER = "OVER";	// 加息券使用状态INIT-未使用；USED-已使用；OVER-已过期

	public static final String IS_SUPPORT_TICKET_INTEREST_TRUE = "TRUE";	// 支持加息券
	public static final String IS_SUPPORT_TICKET_INTEREST_FALSE = "FALSE";	// 不支持加息券

	// 卡券审核状态
	public static final String TICKET_CHECK_STATUS_UNCHECKED = "UNCHECKED"; //待审核
	public static final String TICKET_CHECK_STATUS_PASS = "PASS"; //已通过
	public static final String TICKET_CHECK_STATUS_REFUSE = "REFUSE"; //不通过

	// 卡券发放状态
	public static final String TICKET_GRANT_STATUS_INIT = "INIT"; //未发放
	public static final String TICKET_GRANT_STATUS_PROCESS = "PROCESS"; // 发放中
	public static final String TICKET_GRANT_STATUS_FINISH = "FINISH"; //发放结束
	public static final String TICKET_GRANT_STATUS_CLOSE = "CLOSE"; //已停用

	// 有效期类型
	public static final String AUTO_TICKET_VALID_TERM_TYPE_FIXED = "FIXED"; //固定时间段
	public static final String AUTO_TICKET_VALID_TERM_TYPE_AFTER_RECEIVE = "AFTER_RECEIVE"; //发放后有效天数

	// 产品限制
	public static final String PRODUCT_TYPE_BIGANGWAN_SERIAL = "BIGANGWAN_SERIAL"; //（港湾系列）
	public static final String PRODUCT_TYPE_YONGJIN_SERIAL = "YONGJIN_SERIAL"; // （涌金系列）
	public static final String PRODUCT_TYPE_KUAHONG_SERIAL = "KUAHONG_SERIAL"; //（跨虹系列）
	public static final String PRODUCT_TYPE_BAOXIN_SERIAL = "BAOXIN_SERIAL"; //（保信系列）
	// 卡券消息通知状态
	public static final String ICKET_MSG_STATUS_FINISHED = "FINISHED"; //已发送
	public static final String ICKET_MSG_STATUS_NOT = "NOT"; //未发送
	//存订单表的，是否签约字段
	public static final String BAOFOO_IS_PROTOCOLPAY_YES = "YES";
	public static final String BAOFOO_IS_PROTOCOLPAY_NO = "NO";
	//宝付 - 协议支付 - 交易类型 1-充值 2-还款 3-投标
	public static final String BAOFOO_PROTOCOLPAY_TRANS_TYPE_TOPUP = "1";
	public static final String BAOFOO_PROTOCOLPAY_TRANS_TYPE_REPAY = "2";
	public static final String BAOFOO_PROTOCOLPAY_TRANS_TYPE_BID = "3";
	//宝付 - 协议支付 - 用户类型 1-投资人 2-借款人
	public static final String BAOFOO_PROTOCOLPAY_CUSTOMER_TYPE_BS = "1";
	public static final String BAOFOO_PROTOCOLPAY_CUSTOMER_TYPE_LN = "2";
	// 网联协议号导入常量
	public static final int BATCH_PROCESSING_COUNT = 5; // 网联协议号批量入库批次处理数量
	public static final String SPLIT_SYMBOL  = ","; // 导入数据分割符
	public static final String NUCC_SIGN_EXPORT_TITLE= "银行卡号,网联协议号"; // 网联协议号导入标题	// 卡券发放类型
	public static final String TICKET_DISTRIBUTE_TYPE_AUTO = "AUTO"; // 自动
	public static final String TICKET_DISTRIBUTE_TYPE_MANUAL = "MANUAL"; // 手动


	public static final String CONFIG_KEYBONUS_RATE_EFFECTIVE_DAYS = "BONUS_RATE_EFFECTIVE_DAYS"; // 推荐奖励金有效天数（针对2018年到2019年之间被邀请注册的用户投资）
	// 卡券触发类型
	public static final String TICKET_TRIGGER_TYPE_HAPPY_BIRTHDAY = "HAPPY_BIRTHDAY"; // 生日福利
	public static final String TICKET_TRIGGER_TYPE_WECHAT_MINI_PROGRAM = "WECHAT_MINI_PROGRAM"; // 微信小程序
	public static final String TICKET_TRIGGER_TYPE_EXCHANGE_4MALL = "EXCHANGE_4MALL"; //积分商城兑换

	// 卡券通知管道
	public static final String TICKET_NOTIFY_CHANNEL_SMS = "SMS"; //短信
	public static final String TICKET_NOTIFY_CHANNEL_WECHAT = "WECHAT"; //微信
	public static final String TICKET_NOTIFY_CHANNEL_APP = "APP"; //APP通知
	public static final int TICKET_NOTIFY_TYPE_SMS = 1; //短信
	public static final int TICKET_NOTIFY_TYPE_WECHAT = 2; //微信
	public static final int TICKET_NOTIFY_TYPE_APP = 3; //APP通知

	//迁移前的还款标记
	public static final String REPAY_STATUS_SUCC_FLAG = "SUCC"; //已还款
	public static final String REPAY_STATUS_FAIL_FLAG = "FAIL"; //未还款和部分还款

	//还款状态
	public static final String REPAY_STATUS_LOANING = "1"; //借款中
	public static final String REPAY_STATUS_PARTLY_REPAID = "2"; //部分还款
	public static final String REPAY_STATUS_REPAID = "3"; //已还款

	//迁移前后表格标志
	public static final String BS_MATCH = "MATCH"; //查询bs_match表数据
	public static final String LN_LOAN_RELATION = "RELATION"; //查询ln_loan_relation表数据

	public static final String CHANNEL_H5 = "micro2.0";
	public static final String CHANNEL_PC = "gen2.0";
	public static final String CHANNEL_PC_178 = "gen178";
	//app端协议	public static final String REGISTER_AGREEMENT = "REGISTER_AGREEMENT";  //注册页面《注册账户服务协议》
	public static final String REGISTER_AGREEMENT = "REGISTER_AGREEMENT";  //注册页面《注册账户服务协议》	public static final String BUY_AGREEMENT_GW = "BUY_AGREEMENT_GW";  //购买页面港湾计划《授权委托书》
	public static final String BUY_AGREEMENT_GW = "BUY_AGREEMENT_GW";  //购买页面港湾计划《授权委托书》
	public static final String RISK_WARNING_GW = "RISK_WARNING_GW";  //购买页面港湾计划《风险提示》
	public static final String BUY_AGREEMENT_ZAN = "BUY_AGREEMENT_ZAN";  //购买页面赞分期《自动出借计划协议》
	public static final String RISK_WARNING_ZAN = "RISK_WARNING_ZAN";  //购买页面赞分期《风险提示》
	public static final String BUY_AGREEMENT_ZSD = "BUY_AGREEMENT_ZSD";  //购买页面赞时贷《出借服务协议》
	public static final String RISK_WARNING_ZSD = "RISK_WARNING_ZSD";  //购买页面赞时贷《风险提示》
	public static final String REGULAR_AGREEMENT_YES = "REGULAR_AGREEMENT_YES";  //计划管理页面定期类计划《授权委托书》,powerAttorneyFlag为yes
	public static final String REGULAR_DETAILS_YES = "REGULAR_DETAILS_YES";  //计划管理页面定期类计划《债权明细》,powerAttorneyFlag为yes
	public static final String REGULAR_AGREEMENT_NO = "REGULAR_AGREEMENT_NO";  //计划管理页面定期类计划《出借服务协议》,powerAttorneyFlag为no
	public static final String REGULAR_DETAILS_NO = "REGULAR_DETAILS_NO";  //计划管理页面定期类计划《债权明细》,powerAttorneyFlag为no
	public static final String INSTALMENT_AGREEMENT = "INSTALMENT_AGREEMENT";  //计划管理页面分期类计划《自动出借计划协议》
	public static final String INSTALMENT_DETAILS = "INSTALMENT_DETAILS";  //计划管理页面分期类计划《债权明细》
	public static final String PAYMENT_AGREEMENT = "PAYMENT_AGREEMENT";  //充值页面《支付协议》和《授权委托书》
	public static final String ACTIVATE_AGREEMENT = "ACTIVATE_AGREEMENT";  //激活页面《网络交易资金存管账户服务协议》
	public static final String OPEN_AGREEMENT = "OPEN_AGREEMENT";  //开通页面《网络交易资金存管账户服务协议》
	//app端协议标题展示
	public static final String REGISTER_AGREEMENT_TITLE = "REGISTER_AGREEMENT_TITLE";  //注册页面《注册账户服务协议》，标题
	public static final String BUY_AGREEMENT_TITLE_GW = "BUY_AGREEMENT_TITLE_GW";  //购买页面港湾计划《授权委托书》，标题
	public static final String RISK_WARNING_TITLE_GW = "RISK_WARNING_TITLE_GW";  //购买页面港湾计划《风险提示》，标题
	public static final String BUY_AGREEMENT_TITLE_ZAN = "BUY_AGREEMENT_TITLE_ZAN";  //购买页面赞分期    自动出借计划协议（模板），标题
	public static final String RISK_WARNING_TITLE_ZAN = "RISK_WARNING_TITLE_ZAN";  //购买页面赞分期《风险提示》，标题
	public static final String BUY_AGREEMENT_TITLE_ZSD = "BUY_AGREEMENT_TITLE_ZSD";  //购买页面赞时贷《出借服务协议》，标题
	public static final String RISK_WARNING_TITLE_ZSD = "RISK_WARNING_TITLE_ZSD";  //购买页面赞时贷《风险提示》，标题
	public static final String REGULAR_AGREEMENT_TITLE_YES = "REGULAR_AGREEMENT_TITLE_YES";  //计划管理页面定期类计划《授权委托书》,powerAttorneyFlag为yes，标题
	public static final String REGULAR_DETAILS_TITLE_YES = "REGULAR_DETAILS_TITLE_YES";  //计划管理页面定期类计划《债权明细》,powerAttorneyFlag为yes，标题
	public static final String REGULAR_AGREEMENT_TITLE_NO = "REGULAR_AGREEMENT_TITLE_NO";  //计划管理页面定期类计划《出借服务协议》,powerAttorneyFlag为no，标题
	public static final String REGULAR_DETAILS_TITLE_NO = "REGULAR_DETAILS_TITLE_NO";  //计划管理页面定期类计划《债权明细》,powerAttorneyFlag为no，标题
	public static final String INSTALMENT_AGREEMENT_TITLE = "INSTALMENT_AGREEMENT_TITLE";  //计划管理页面分期类计划《自动出借计划协议》，标题
	public static final String INSTALMENT_DETAILS_TITLE = "INSTALMENT_DETAILS_TITLE";  //计划管理页面分期类计划《债权明细》，标题
	public static final String PAYMENT_AGREEMENT_TITLE = "PAYMENT_AGREEMENT_TITLE";  //充值页面    宝付支付用户服务协议，标题
	public static final String ACTIVATE_AGREEMENT_TITLE = "ACTIVATE_AGREEMENT_TITLE";  //激活页面《网络交易资金存管账户服务协议》，标题
	public static final String OPEN_AGREEMENT_TITLE = "OPEN_AGREEMENT_TITLE";  //开通页面《网络交易资金存管账户服务协议》，标题

	//============================积分商城相关配置 start============================
	//积分收入交易记录表-订单状态 - 初始
	public static final String MALL_POINTS_INCOME_STATUS_INIT = "INIT";
	//积分收入交易记录表-订单状态 - 已完成
	public static final String MALL_POINTS_INCOME_STATUS_FINISHED = "FINISHED";

	//积分收入交易队列KEY(注册、开通存管、风险测评)
	public static final String MALL_POINTS_INCOME_QUEUE_KEY = "mall_points_queue";
	//积分收入交易队列KEY(购买成功)
	public static final String MALL_POINTS_INCOME_INVEST_QUEUE_KEY = "mall_points_invest_queue";
	//红包、优惠券-积分商城兑换成功存入redis-key
	public static final String MALL_SEND_TICKET_QUEUE_KEY = "mall_send_ticket_queue";
	//积分商城订单表 订单状态
	public static final String MALL_EXCHANGE_ORDERS_STATUS_SUCCESS = "SUCCESS"; //支付成功
	public static final String MALL_EXCHANGE_ORDERS_STATUS_FINISHED = "FINISHED"; //已完成
	public static final String MALL_EXCHANGE_ORDERS_STATUS_SEND_FAIL = "SEND_FAIL"; //发放失败
	//积分商城商品类别编码
	public static final String MALL_COMMODITY_TYPE_RED_PACKET = "RED_PACKET"; //红包
	public static final String MALL_COMMODITY_TYPE_INTEREST_TACKET = "INTEREST_TACKET"; //加息券

	//============================积分商城相关配置 end============================

	//============================人脸核身相关配置 start============================
	public static final int POLICE_VERIFY_UNBIND_APPLY_COUNT = 3; // 人脸核身24小时内可验证次数
	public static final String POLICE_VERIFY_VERIFYRESULT_SUCC = "success"; // 人脸核身验证结果：success 验证成功 fail 验证失败
	public static final String POLICE_VERIFY_VERIFYRESULT_FAIL = "fail"; // 人脸核身验证结果：success 验证成功 fail 验证失败
	//============================人脸核身相关配置 end============================

	//============================自由资金计划Start============================
	public static final Double LN_DAILY_AMOUNT_FREE_RATE_ONE = 1d;
	public static final Double LN_DAILY_AMOUNT_FREE_RATE_TWO = 2d;
	public static final String LN_DAILY_AMOUNT_STATUS_INIT = "INIT";
	public static final String LN_DAILY_AMOUNT_STATUS_AVALIABLE = "AVALIABLE";
	public static final String LN_DAILY_AMOUNT_STATUS_CANCELLED = "CANCELLED";
	public static final String LN_LOAN_PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME = "REPAY_ANY_TIME"; // 消费循环贷
	public static final String LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_INSTALLMENT = "FIXED_INSTALLMENT"; // 等额本息
	public static final String LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_PRINCIPAL_INTEREST = "FIXED_PRINCIPAL_INTEREST"; // 等本等息
	//============================自由资金计划End============================

	//========================公众号运营数据查询 start============================
	public static final String TODAY_DATA = "今日数据";// 公众号查询今日数据
	public static final String YESTERDAY_DATA = "昨日数据";// 公众号查询昨日数据
	public static final String YUN_DAI_PRODUCT_BUY = "云贷产品购买查询";// 云贷产品购买查询
	public static final String SEVEN_DAI_PRODUCT_BUY = "七贷产品购买查询";// 七贷产品购买查询
	public static final String FREE_PRODUCT_BUY = "自由产品计划购买查询";// 自由产品购买查询
	public static final String YUN_DAI_LENDING = "云贷放款查询";// 云贷放款查询
	public static final String SEVEN_DAI_LENDING = "七贷放款查询";// 七贷放款查询
	public static final String YUN_DAI_REPAY = "云贷实时还款查询";// 云贷实时还款查询
	public static final String SEVEN_DAI_REPAY = "七贷实时还款查询";// 七贷实时还款查询
	public static final String YUN_DAI_CASH_BALANCE = "云贷次日兑付差值";// 云贷次日兑付差值
	public static final String SEVEN_DAI_CASH_BALANCE = "七贷次日兑付差值";// 七贷次日兑付差值
	public static final String FREE_CASH_BALANCE = "自由次日兑付差值";// 自由次日兑付差值
	public static final String TODAY_DATA_KEY = "1";// 公众号查询今日数据代号
	public static final String YESTERDAY_DATA_KEY = "2";// 公众号查询昨日数据代号
	public static final String YUN_DAI_PRODUCT_BUY_KEY = "3";// 云贷产品购买查询代号
	public static final String SEVEN_DAI_PRODUCT_BUY_KEY = "4";// 七贷产品购买查询代号
	public static final String FREE_PRODUCT_BUY_KEY = "5";// 自由产品计划购买查询代号
	public static final String YUN_DAI_LENDING_KEY = "6";// 云贷放款查询代号
	public static final String SEVEN_DAI_LENDING_KEY = "7";// 七贷放款查询代号
	public static final String YUN_DAI_REPAY_KEY = "8";// 云贷实时还款查询代号
	public static final String SEVEN_DAI_REPAY_KEY = "9";// 七贷实时还款查询代号
	public static final String YUN_DAI_CASH_BALANCE_KEY = "10";// 云贷次日兑付差值代号
	public static final String SEVEN_DAI_CASH_BALANCE_KEY = "11";// 七贷次日兑付差值代号
	public static final String FREE_CASH_BALANCE_KEY = "12";// 自由次日兑付差值代号
	//========================公众号运营数据查询 end==============================

	// 默认分页 信息
	public static final int MANAGE_DEFAULT_PAGENUM = 1; // 默认起始页码
	public static final int MANAGE_DEFAULT_NUMPERPAGE = 20; // 默认每页条数

	//宝付代付归集户配置相关
	//限制条件类型，AMOUNT-限制总金额；NUMBER-限制总笔数
	public static final String PAY_LIMIT_TYPE_AMOUNT = "AMOUNT";
	public static final String PAY_LIMIT_TYPE_NUMBER = "NUMBER";
	//配置是否删除，YES-是；NO-否
	public static final String PAY_LIMIT_DELETE_YES = "YES";
	public static final String PAY_LIMIT_DELETE_NO = "NO";
	
	//微信通知发送成功的返回编码
	public static final String WECHAT_SEND_SUCC_CODE="0"; 
	
	//TODO:以下后续继续优化 zhouchangzai 201511262020
	//---------------------------------------------------------------------------------------------------------
	//系统记账流水bs_sys_account_jnl
//	public static final class BsSysAccountJnl{
//		//交易代码 trans_code
//		public static final class TransCode{
//			public static final String SYS_TOP_UP = "SYS_TOP_UP"; //系统充值
//			public static final String SYS_FREEZE = "SYS_FREEZE"; //冻结
//			public static final String SYS_UNFREEZE = "SYS_UNFREEZE"; //解冻
//			public static final String SYS_BUY = "SYS_BUY"; //系统购买达飞产品（从REG）
//			public static final String SYS_RETURN = "SYS_RETURN"; //系统回款（达飞到RETURN）
//			public static final String SYS_RETURN_INTEREST = "SYS_RETURN_INTEREST"; //达飞回款系统获得利息(RETURN->JSH)
//			public static final String SYS_USER_BONUS_2_BALANCE  = "USER_BONUS_2_BALANCE"; //用户奖励金转余额（JSH->USER）
//			public static final String SYS_USER_WITHDRAW = "USER_WITHDRAW"; //用户提现
//			public static final String SYS_USER_RETURN_2_BALANCE = "USER_RETURN_2_BALANCE"; //用户回款到余额（本金+利息）(RETURN->USER)
//			public static final String SYS_USER_RETURN_2_CARD = "USER_RETURN_2_CARD"; //用户回款到银行卡（本金+利息）(从RETURN)
//			public static final String SYS_USER_TOP_UP = "USER_TOP_UP"; //用户充值（到USER）
//			public static final String SYS_USER_BALANCE_BUY = "USER_BALANCE_BUY"; //用户余额购买（USER->REG）
//			public static final String SYS_USER_CARD_BUY = "USER_CARD_BUY"; //用户卡购买（卡到REG）
//			public static final String SYS_WITHDRAW  = "SYS_WITHDRAW"; //系统提现（从JSH）
//		}
//
//	}
//	
//	//用户记账流水表bs_account_jnl 
//	public static final class BsAccountJnl{
//		//交易代码trans_code
//		public static final class TransCode{
//			public static final String USER_CARD_BUY_PRODUCT = "CARD_BUY_PRODUCT"; //卡购买
//			public static final String USER_BALANCE_BUY_PRODUCT = "BALANCE_BUY_PRODUCT"; //余额购买
//			public static final String USER_TOP_UP = "TOP_UP"; //充值
//			public static final String USER_FREEZE = "FREEZE"; //冻结
//			public static final String USER_UNFREEZE = "UNFREEZE"; //解冻
//			public static final String USER_RETURN_2_BALANCE  = "RETURN_2_BALANCE"; //回款到余额
//			public static final String USER_RETURN_2_BANK_CARD = "RETURN_2_BANK_CARD"; //回款到银行卡
//			public static final String USER_BONUS_2_BALANCE = "BONUS_2_BALANCE"; //奖励金转余额
//			public static final String USER_RECOMMEND_BONUS  = "RECOMMEND_BONUS"; //获得推荐奖励
//			public static final String USER_BONUS_WITHDRAW = "BONUS_WITHDRAW"; //奖励金提现
//			public static final String USER_BALANCE_WITHDRAW  = "BALANCE_WITHDRAW"; //余额提现
//		}
//	}
//	
//	//订单表bs_pay_orders 
//	public static final class BsPayOrders{
//		//交易类型 trans_type
//		public static final class TransType{
//			public static final String CARD_BUY_PRODUCT = "CARD_BUY_PRODUCT"; //卡购买
//			public static final String BALANCE_BUY_PRODUCT = "BALANCE_BUY_PRODUCT"; //余额购买
//			public static final String TOP_UP = "TOP_UP"; //充值
//			public static final String RETURN_2_USER_BANK_CARD = "RETURN_2_USER_BANK_CARD"; //回款到银行卡
//			public static final String BALANCE_WITHDRAW  = "BALANCE_WITHDRAW"; //用户余额提现
//			public static final String SYS_BUY_DAFY = "SYS_BUY_DAFY";//系统购买
//		}
//		
//	}
	//---------------------------------------------------------------------------------------------------------
}
