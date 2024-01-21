package com.pinting.util;

public class Constants {

	//报文类前置
	public static final String SITE_MESSAGE_RESMSG_PRE = "com.pinting.business.hessian.site.message.ResMsg_";//返回报文类前置
	//平台系统名称
	public static final String REDIS_SUBSYS_SITE = "_site";
	//平台服务模式
	public static final String SITE_SERVER_MODE_PROD = "prod";//生产模式

	//是否实名认证
	public static final int IS_BIND_NAME_YES = 1;//是
	public static final int IS_BIND_NAME_NO = 2;//否

	public static final String YES = "YES";
	public static final String NO = "NO";
	public static final String is_yes = "yes";
	public static final String is_no = "no";
	public static final String is_expire = "expire";	// 已过期
	
	//是否绑定银行卡
	public static final int IS_BIND_BANK_YES = 1;//是
	public static final int IS_BIND_BANK_NO = 2;//否
	public static final int IS_BIND_BANK_BINGING = 3;//绑定中
	public static final int IS_BIND_BANK_FAIL = 4;// 绑卡失败

	//手机发送失败
	public static final String SEND_CODE_ERROR="sending error";
	public static final String SEND_CODE_NULL="not sending";

	//分页大小
	public static final Integer EXCHANGE_PAGE_SIZE = 10;
	public static final Integer PAGE_SIZE_SIX = 6;

	//运营报告分页大小
	public static final Integer REPORT_PAGE_SIZE = 14;
	
	//micro2.0回款计划请求转态
	public static final String PAYMENT_PLANT_STATUS = "MICRO";

	//分页大小-更多的条数
	public static final Integer EXCHANGE_PAGE_SIZE_LONG = 20;
	//字典表类型编号
	public static final int DICT_ITEM_RELATION_ID= 1;//紧急联系人关系

	//金额少于0
	public static final String MONEY_MESSAGE = "金额不能小于0";

	public static final String ALL_BONUS="1";//所有奖励金明细
	public static final String CAN_WITHDRAW_BONUS="2";//能提现的奖励金明细

	//手机号类型
	public static final String MOBILE_TYPE_NORMAL="normal";//普通手机号
	public static final String MOBILE_TYPE_EMERGENCY="emergency";//内部告警手机号

	//DESede 加密密钥
	public final static byte[] keyBytes = "flzx3kmysyhdown9dayshszc".getBytes();    //密钥长度56*3+8*3=192位  24个字节

	//系统维护状态
	public static Integer sysValue = 1;//是否挂起整个系统（不允许系统中任何业务运行），1-正常，2-挂起
	public static Integer tranValue = 1;//是否挂起交易类业务（不允许用户发起购买等交易相关的业务），1-正常，2-挂起
	public static final Integer TRAN_VALUE_NORMAL = 1; //系统正常状态
	public static final Integer TRAN_VALUE_HALT = 2; //系统挂起状态

	public static final Double BONUS_MIN_CAN_WITHDRAW = 100d;//可提现满100才能提现

	//用户类型
	public static final String USER_TYPE_NORMAL="NORMAL";//普通用户
	public static final String USER_TYPE_PROMOT="PROMOT";//渠道用户

	//交易类型
	public static final int USER_TRANS_TYPE_CARD = 1; //卡购买
	public static final int USER_TRANS_TYPE_RECHANGE = 2; //充值

	public static final String USER_AGENT_QIANBAO = "qianbao"; // 钱报用户

	// 
	public static final String QUESTION_TYPE_RISK_ASSESS = "risk_assess";
	
	//渠道编号
	public static final int AGENT_QIANBAO_ID = 15; //钱报在agent表中的id
	public static final int AGENT_TONGCHENG_ID = 18; //同程在agent表中的id
	public static final int AGENT_CHANNEL_ID = 19; //地推在agent表中的id
	public static final int AGENT_MONKEY_ID = 28; //猴子活动在agent表中的id
	public static final int AGENT_CSAI_ID = 30; //CSAI在agent表中的id
	public static final int AGENT_XIAOSHOURENYUAN_ID = 31; //销售人员在agent表中的id
	public static final int AGENT_318SHAKE_ID = 32; //318摇一摇活动在agent表中的id
	public static final int LANDING_PAGE_AGENT_ID = 33;    // 广告落地页的渠道ID
	public static final int MANAGER_AGENT_ID = 34;    // 客户经理渠道ID
	public static final int AGENT_TEILU_ID = 29;   // 铁路杂志渠道ID
	public static final int AGENT_KQ_ID = 36;   // 柯桥日报渠道ID
	public static final int AGENT_XIAO_MI_STORE_ID = 40;   // 小米应用商店渠道ID
	public static final int AGENT_BAIDU_STORE_ID = 41;   // 百度手机助手推广渠道ID
	public static final int AGENT_HEFEILUNTAN_ID = 42;   // 合肥论坛
	public static final int AGENT_ANMO_ID = 43;   // 安墨
	public static final int AGENT_HAINING_ID = 46;   // 海宁日报
	public static final int AGENT_RUIAN_ID = 47;   // 瑞安日报
	// 渠道部门
	public static final String AGENT_DEPT_BEIJING = "北京团队";

	//同程回调接口地址
	public static final String TONGCHENG_URL = "http://tcmobileapi.17usoft.com/openapi/CoopApp/CoopAppDetailHandler.ashx"; //同程回调地址

	//同程MD5的key
	public static final String TONGCHENG_EKY = "TcCoper123456";


	//卡bin表银行卡功能类型
	public static final String CARDBIN_TYPE_JIE_JI = "JIE_JI"; // 借记卡
	public static final String CARDBIN_TYPE_DAI_JI = "DAI_JI"; // 贷记卡
	public static final String CARDBIN_TYPE_ZHUN_DAI_JI = "ZHUN_DAI_JI"; // 准贷记卡
	public static final String CARDBIN_TYPE_YU_FU_FEI = "YU_FU_FEI"; // 预付费卡

	// 系统新闻公告相关数据
	// 1、类型
	public static final String NEWS_TYPE_NOTICE = "NOTICE";    // 公告
	public static final String NEWS_TYPE_NEWS = "NEWS";    // 新闻
	public static final String NEWS_TYPE_COMPANY_DYNAMIC = "COMPANY_DYNAMIC";    // 公司动态
	public static final String NEWS_TYPE_WFANS_ACTIVITY = "WFANS_ACTIVITY";   // 湾粉活动

	// 2、接收对象类型
	public static final String NEWS_RECEIVER_TYPE_BGW = "BGW";    // 币港湾
	public static final String NEWS_RECEIVER_TYPE_BGW178 = "BGW178";    // 178
	public static final String NEWS_RECEIVER_TYPE_BGWKQ = "BGWKQ"; //柯桥日报
	public static final String NEWS_RECEIVER_TYPE_BGWHN = "BGWHN"; //海宁日报
	public static final String NEWS_RECEIVER_TYPE_BGWRUIAN = "BGWRUIAN"; //瑞安日报
	public static final String NEWS_RECEIVER_TYPE_BGWQD = "BGWQD"; //七店
	public static final String NEWS_RECEIVER_TYPE_BGWQHDJT = "BGWQHDJT"; //秦皇岛交通广播
	public static final String NEWS_RECEIVER_TYPE_BGWQHDXW = "BGWQHDXW"; //秦皇岛新闻891
	public static final String NEWS_RECEIVER_TYPE_BGWQHDTV = "BGWQHDTV"; //秦皇岛电视台今日报道
	public static final String NEWS_RECEIVER_TYPE_BGWQHDST = "BGWQHDST"; //视听之友
	public static final String NEWS_RECEIVER_TYPE_BGWQHDSJC = "BGWQHDSJC"; //1038私家车广播

	// 3、状态
	public static final String NEWS_STATUS_PUBLISHED = "PUBLISHED";    // 已发布
	public static final String NEWS_STATUS_NOT_PUBLISH = "NOT_PUBLISH";    // 未发布
	public static final String NEWS_STATUS_REMOVED = "REMOVED";    // 已撤下

	//用户红包状态
	public static final String RED_PACKET_STATUS_INIT = "INIT"; //未使用
	public static final String RED_PACKET_STATUS_BUYING = "BUYING"; //使用中
	public static final String RED_PACKET_STATUS_USED = "USED"; //已使用
	public static final String RED_PACKET_STTAUS_RETURN = "RETURN"; //已退回
	public static final String RED_PACKET_STATUS_OVER = "OVER"; // 数据库没有，用于前端显示，已过期

	//自动红包触发条件类型
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_NEW_USER = "NEW_USER"; //新用户注册
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL = "BUY_FULL"; //购买满额
	public static final String AUTO_RED_PACKET_TIGGER_TYPE_INVITE_FULL = "INVITE_FULL"; //邀请满额

	//是否显示产品可用总额度和用户使用额度标识
	public static final String PRODUCT_LIMIT_AMOUNT = "YES";

	//额外标的编号
	public static final int PRODUCT_ID_NEWER_1_MONTH = 49; //新手“想要发”专享产品编号
	public static final int PRODUCT_ID_ADD_RATE_1_YEAR = 50; //在一起“感恩”活动专享产品编号

	//banner 展示渠道 channel
	public static final String BANNER_CHANNEL_MICRO = "MICRO"; //H5端首页
	public static final String BANNER_CHANNEL_MICRO178 = "MICRO178"; //178-H5端首页
	public static final String BANNER_CHANNEL_GEN = "GEN"; //PC端首页
	public static final String BANNER_CHANNEL_GEN178 = "GEN178"; // 178-PC端首页
	public static final String BANNER_CHANNEL_APP = "APP"; //APP端
	public static final String BANNER_CHANNEL_GENKQ = "GENKQ"; // 柯桥日报-PC端首页banner
	public static final String BANNER_CHANNEL_GENHN = "GENHN"; // 海宁日报-PC端首页banner
	public static final String BANNER_CHANNEL_MICROKQ = "MICROKQ"; // 柯桥日报-H5端首页
	public static final String BANNER_CHANNEL_MICROHN = "MICROHN"; // 海宁日报-H5端首页

	public static final String BANNER_CHANNEL_GENRUIAN = "GENRUIAN"; // 瑞安日报-PC端首页
	public static final String BANNER_CHANNEL_MICRORUIAN = "MICRORUIAN"; // 瑞安日报-H5端首页

	public static final String BANNER_CHANNEL_MICRO_QD = "MICROQD"; //七店-H5端首页

	public static final String BANNER_CHANNEL_MICROQHDJT = "MICROJT"; //秦皇岛交通广播-H5端首页
	public static final String BANNER_CHANNEL_MICROQHDXW = "MICROXW"; //秦皇岛新闻891-H5端首页
	public static final String BANNER_CHANNEL_MICROQHDTV = "MICROTV"; //秦皇岛电视台今日报道-H5端首页
	public static final String BANNER_CHANNEL_GENQHDST = "GENSTZY"; // 视听之友-PC端首页
	public static final String BANNER_CHANNEL_MICROQHDST = "MICROSTZY"; //视听之友-H5端首页
	public static final String BANNER_CHANNEL_MICROQHDSJC = "MICROSJC"; //1038私家车广播-H5端首页


//	agent_id = 51	秦皇岛交通广播 qhd_jt（h5_qhd_jt）
//	agent_id = 52	秦皇岛新闻891 qhd_xw（h5_qhd_xw）
//	agent_id = 53	秦皇岛电视台今日报道 qhd_tv（h5_qhd_tv）
//	agent_id = 54	视听之友 qhd_st（h5_qhd_st / pc_qhd_st）
//	agent_id = 55	1038私家车广播 qhd_sjc（h5_qhd_sjc）

	// 产品额度明细表 触发事件名称
	public static final String EVENT_REGISTER = "REGISTER";   // 注册触发
	public static final String EVENT_SHARE = "SHARE";   // 分享触发
	public static final String EVENT_RECOMMEND = "RECOMMEND";   // 邀请触发

	//所有购买提交URL
	public static final String PC_NEW_USER_BUY = "/gen2.0/regular/firstBuySubmitPro";
	public static final String PC_BIND_CARD_USER_BUY = "/gen2.0/regular/pre_submit_bind";
	public static final String PC_BANK_USER_BUY = "/gen2.0/regular/netBankbuySubmit";
	public static final String MICRO_BIND_CARD_USER_BUY = "/micro2.0/regular/preOrder";
	public static final String MICRO_UNBIND_CARD_USER_BUY = "/micro2.0/regular/regularPreOrder";
	public static final String PC_BIND_CARD_ORDER_USER_BUY = "/gen2.0/regular/submit_bind";
	public static final String PC_BALANCE_USER_BUY = "/gen2.0/regular/balance_buy";

	//希财登录需要校验的IP地址
	public static final String CSAI_AUTO_LOGIN_IP_ADDR1 = "210.73.220.214";
	public static final String CSAI_AUTO_LOGIN_IP_ADDR2 = "210.73.220.210";
	public static final String CSAI_AUTO_LOGIN_IP_ADDR3 = "183.129.222.138";//币港湾外网IP
	public static final String CSAI_AUTO_LOGIN_IP_ADDR4 = "113.240.218.124";//希财开发人员对应IP


	//希财用户访问客户端类型
	public static final String CSAI_USER_DISPLAY_PC="pc";//pc端
	public static final String CSAI_USER_DISPLAY_MOBILE="mobile";//手机

	//希财用户标识
	public static final String USER_AGENT_CAAI = "xicai"; // 钱报用户

	// 用户注册来源
	public static final int USER_REG_TERMINAL_H5 = 1;   // H5
	public static final int USER_REG_TERMINAL_PC = 2;   // PC
	public static final int USER_REG_TERMINAL_WXAPP = 5;   // WXAPP

	//微信消息自动回复--回复类型
	 public static final String WX_AUTO_REPLY_TYPE_SUBSCRIBE = "SUBSCRIBE_REPLY"; //关注时回复
	 public static final String WX_AUTO_REPLY_TYPE_KEY = "KEY_REPLY"; //关键字回复

	 //微信消息自动回复--回复消息内容类型
	 public static final String WX_AUTO_REPLY_MSG_TYPE_TEXT = "text"; //文本
	 public static final String WX_AUTO_REPLY_MSG_TYPE_NEWS = "news"; //图文

	 // 产品展示端口
	 public static final String PRODUCT_SHOW_TERMINAL_H5 = "H5";  // H5
	 public static final String PRODUCT_SHOW_TERMINAL_PC = "PC";  // H5
	 public static final String PRODUCT_SHOW_TERMINAL_PC_178 = "PC_178";  // H5
	 public static final String PRODUCT_SHOW_TERMINAL_H5_178 = "H5_178";  // H5_178
	 public static final String PRODUCT_SHOW_TERMINAL_APP = "APP";  // APP
	 public static final String PRODUCT_SHOW_TERMINAL_H5_KQ = "H5_KQ";    // 柯桥日报H5
	 public static final String PRODUCT_SHOW_TERMINAL_PC_KQ = "PC_KQ";    // 柯桥日报PC

	 public static final String PRODUCT_SHOW_TERMINAL_H5_HN = "H5_HN";    // 海宁日报H5
	 public static final String PRODUCT_SHOW_TERMINAL_PC_HN = "PC_HN";    // 海宁日报PC

	 public static final String PRODUCT_SHOW_TERMINAL_H5_RUIAN = "H5_RUIAN";    // 瑞安日报H5
	 public static final String PRODUCT_SHOW_TERMINAL_PC_RUIAN = "PC_RUIAN";    // 瑞安日报PC
	 public static final String PRODUCT_SHOW_TERMINAL_H5_QD = "H5_QD";  // H5 七店
	 public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_JT = "H5_QHD_JT";  // H5 秦皇岛交通广播
	 public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_XW = "H5_QHD_XW";  // H5 秦皇岛新闻891
	 public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_TV = "H5_QHD_TV";  // H5 秦皇岛电视台今日报道
	 public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_ST = "H5_QHD_ST";  // H5 视听之友
	 public static final String PRODUCT_SHOW_TERMINAL_PC_QHD_ST = "PC_QHD_ST";  // PC 视听之友
	 public static final String PRODUCT_SHOW_TERMINAL_H5_QHD_SJC = "H5_QHD_SJC";  // H5 1038私家车广播
	 // BANNER展示编码
	 public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_JT = "MICROJT";  // H5 秦皇岛交通广播
	 public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_XW = "MICROXW";  // H5 秦皇岛新闻891
	 public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_TV = "MICROTV";  // H5 秦皇岛电视台今日报道
	 public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_ST = "MICROSTZY";  // H5 视听之友
	 public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_PC_QHD_ST = "GENSTZY";  // PC 视听之友
	 public static final String PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_SJC = "MICROSJC";  // H5 1038私家车广播

	 //标的状态
	 public static final Integer PRODUCT_STATUS_CHECK_NO = 1; //未审核
	 public static final Integer PRODUCT_STATUS_CHECKING = 2; //审核中
	 public static final Integer PRODUCT_STATUS_PASS_NO = 3; //未通过
	 public static final Integer PRODUCT_STATUS_PUBLISH_NO = 4; //待发布
	 public static final Integer PRODUCT_STATUS_PUBLISH_YES = 5; //未开放(已发布)
	 public static final Integer PRODUCT_STATUS_OPENING = 6; //进行中
	 public static final Integer PRODUCT_STATUS_FINISH = 7; //已完成


	//债权关系显示时间，标的开始时间比这个时间早的，不显示标的
	public static final String LOAD_MATCH_SHOW_TIME = "2016-05-11";

	public static final String PRODUCT_PROPERTY_TYPE_CASH_LOOP = "CASH_LOOP"; //现金循环贷
	public static final String PRODUCT_PROPERTY_TYPE_CONSUME = "CONSUME"; //消费贷


	// 产品活动类型
	public static final String PRODUCT_ACTIVITY_TYPE_NORMAL = "NORMAL";    // 普通
	public static final String PRODUCT_ACTIVITY_TYPE_NEW_BUYER = "NEW_BUYER";  // 新手标

	//2017踏青活动
	public static final String ACTIVITY_SPRING_PRODUCT_NAME = "老用户专享"; //踏青活动产品名称

	// 媒体报道来源
	public static final String NEWS_SOURCE_QJWB = "钱江晚报";
	public static final String NEWS_SOURCE_DSKB = "都市快报";
	public static final String NEWS_SOURCE_FHW = "凤凰网";
	public static final String NEWS_SOURCE_XHW = "新华网";
	public static final String NEWS_SOURCE_WYCJ = "网易财经";
	public static final String NEWS_SOURCE_SHCJ = "搜狐财经";

	//活动编号
	public static final Integer ACTIVITY_20160618 = 1; //2016-6-18日活动
	public static final Integer ACTIVITY_178_20170123 = 6; //178  2017-1-23报系活动
	public static final Integer ACTIVITY_TREBLEGIFT_2 = 7; //三重好礼-二重礼
	public static final Integer ACTIVITY_TREBLEGIFT_3 = 8; //三重好礼-三重礼
	public static final Integer ACTIVITY_SPRING = 19;//踏春活动
	public static final Integer ACTIVITY_LUCKY_LENDERS = 36; //周周乐   幸运出借人

	//6-18活动开始结束时间
	public static final String ACTIVITY_START_TIME="2016-6-17 00:00:00";
	public static final String ACTIVITY_END_TIME="2016-6-24 00:00:00";


	//欧洲杯活动开始结束实际
	public static final String Ecup_START_TIME = "2016-6-29 00:00:00";
	public static final String Ecup_END_TIME = "2016-7-11 23:59:59"; //活动结束2016-7-11 23:59:59
	public static final String Ecup_END_TIME_4_GUESS = "2016-7-10 23:59:59"; //竞猜结束2016-7-10 23:59:59



  	//资金接收方标识
  	public static final String PROPERTY_SYMBOL_YUN_DAI="YUN_DAI"; //云贷
  	public static final String PROPERTY_SYMBOL_7_DAI="7_DAI"; //七贷
  	public static final String PROPERTY_SYMBOL_ZAN="ZAN"; //赞分期
	public static final String PROPERTY_SYMBOL_YUN_DAI_SELF="YUN_DAI_SELF"; //云贷循环贷自主放款
	public static final String PROPERTY_SYMBOL_ZSD="ZSD"; //赞时贷
	public static final String PROPERTY_SYMBOL_7_DAI_SELF="7_DAI_SELF"; //七贷存管
	public static final String PROPERTY_SYMBOL_FREE="FREE"; //自由计划

	public static final String AGENT_VIEW_ID_KQ = "36";	// 显示终端-柯桥
	public static final String AGENT_VIEW_ID_QB = "15";	// 显示终端-钱报
	public static final String AGENT_VIEW_ID_HN = "46";	// 显示终端-海宁
	public static final String AGENT_VIEW_ID_RUIAN = "47";	// 显示终端-瑞安
	public static final String AGENT_VIEW_ID_QD = "49";	// 显示终端-七店
	public static final Integer AGENT_ID_QD = 49;	// 显示终端-七店
	public static final String AGENT_VIEW_ID_QHD_JT = "51";	// 显示终端-秦皇岛交通广播
	public static final Integer AGENT_ID_QHD_JT = 51;	// 显示终端-七店
	public static final String AGENT_VIEW_ID_QHD_XW = "52";	// 显示终端-秦皇岛新闻891
	public static final Integer AGENT_ID_QHD_XW = 52;	// 显示终端-秦皇岛新闻891
	public static final String AGENT_VIEW_ID_QHD_TV = "53";	// 显示终端-秦皇岛电视台今日报道
	public static final Integer AGENT_ID_QHD_TV = 53;	// 显示终端-秦皇岛电视台今日报道
	public static final String AGENT_VIEW_ID_QHD_ST = "54";	// 显示终端-视听之友
	public static final Integer AGENT_ID_QHD_ST = 54;	// 显示终端-视听之友
	public static final String AGENT_VIEW_ID_QHD_SJC = "55"; // 显示终端-1038私家车广播
	public static final Integer AGENT_ID_QHD_SJC = 55;	// 显示终端-1038私家车广播



	//系统公告对应的接收者类型
	public static final String SMS_MESSAGE_RECEIVER_TYPE_NORMAL = "USER_NORMAL"; //普通用户
	public static final String SMS_MESSAGE_RECEIVER_TYPE_178 = "USER_178"; //钱报用户
	public static final String SMS_MESSAGE_RECEIVER_TYPE_KQ = "USER_KQ"; //柯桥用户
	public static final String SMS_MESSAGE_RECEIVER_TYPE_HN = "USER_HN"; //海宁用户
	public static final String SMS_MESSAGE_RECEIVER_TYPE_RUIAN = "USER_RUIAN"; //瑞安用户

	//访问设备
	public static final String CHANNEL_MICRO = "micro2.0";
	public static final String CHANNEL_GEN = "gen2.0";
	public static final String CHANNEL_GEN_178 = "gen178";

	//渠道交易类型
	public static final String CHANNEL_DAFY = "DAFY";//达飞渠道
	public static final String CHANNEL_PAY19 = "PAY19";//19付渠道
	public static final String CHANNEL_REAPAL = "REAPAL";//融宝渠道
	public static final String CHANNEL_BAOFOO = "BAOFOO";// 宝付渠道
	public static final String CHANNEL_HF = "HFBANK";// 恒丰

	//19付银行支付类型
	public static final int PAY_TYPE_QUICK = 1; //快捷
	public static final int PAY_TYPE_PAYMENT = 2; //代付
	public static final int PAY_TYPE_HOLD = 3; //代扣
	public static final int PAY_TYPE_NET = 4; //网银

	// 订单产生端口
	public static final Integer PAY_ORDER_TERMINAL_H5 = 1;  // H5
	public static final Integer PAY_ORDER_TERMINAL_PC = 2;  // PC
	public static final Integer PAY_ORDER_TERMINAL_ANDROID = 3;  // 安卓
	public static final Integer PAY_ORDER_TERMINAL_IOS = 4;  // IOS

	// 是否支持红包
	public static final String IS_SUPPORT_RED_PACKET_TRUE = "TRUE";
	public static final String IS_SUPPORT_RED_PACKET_FALSE = "FALSE";

	public static final Integer USER_PAY_PASSWORD_EXIST_NO = 2;	// 用户未设置交易密码
	public static final Integer USER_PAY_PASSWORD_EXIST_YES = 1; // 用户已设置交易密码

	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_INIT = "INIT";	// 未还款
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAYING = "REPAYING";	//  还款中
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAIED = "REPAIED";	// 已还款
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_ADVANCE= "ADVANCE";	// 已垫付

	public static final int ACTIVITY_ID_DOUBLE11 = 2;	// 双十一活动ID

	// 恒丰存管页面引导相关状态以及账户类型
	public static final String HFBANK_GUIDE_NO_BIND_CARD = "NO_BIND_CARD"; // 未绑卡
	public static final String HFBANK_GUIDE_FAILED_BIND_HF = "FAILED_BIND_HF";	// 恒丰批量开户失败
	public static final String HFBANK_GUIDE_WAIT_ACTIVATE = "WAIT_ACTIVATE";// 待激活
	public static final String HFBANK_GUIDE_OPEN = "OPEN";//已激活
	public static final String HFBANK_GUIDE_ACCOUNT_TYPE_DEP = "DEP"; // 存管户
	public static final String HFBANK_GUIDE_ACCOUNT_TYPE_DOUBLE = "DOUBLE";	// 双帐户
	public static final String HFBANK_GUIDE_ACCOUNT_TYPE_SIMPLE = "SIMPLE";	// 普通账户。未开通存管户。

	public static final String PRODUCT_TYPE_AUTH_YUN = "AUTH_YUN";  //站岗户（云贷）
	public static final String PRODUCT_TYPE_AUTH_ZSD = "AUTH_ZSD";  //站岗户（赞时贷）
	public static final String PRODUCT_TYPE_AUTH_7 = "AUTH_7";  //站岗户（7贷）
	public static final String PRODUCT_TYPE_AUTH_FREE = "AUTH_FREE";  //站岗户（自由）

	public static final String SEND_MOBILE_CODE_TYPE_EXIST = "exist";	// 已注册用户发送
	public static final String SEND_MOBILE_CODE_TYPE_NOT_EXIST = "not_exist";	// 未注册用户发送

	public static final String CODE_MOBILE_IS_EXIST = "910005"; // 手机已存在
	public static final String CODE_MOBILE_NOT_EXIST = "910012"; // 该手机未注册

	//周周乐活动时间
	public static final Integer WEEKHAY_WEEK_1 = 1;
	public static final Integer WEEKHAY_WEEK_2 = 2;
	public static final Integer WEEKHAY_WEEK_3 = 3;
	public static final Integer WEEKHAY_WEEK_4 = 4;

	public static final String WEEKHAY_WEEK_TIME_0_00_00 = "0:00:00";
	public static final String WEEKHAY_WEEK_TIME_9_55_00 = "9:55:00";
	public static final String WEEKHAY_WEEK_TIME_10_00_00 = "10:00:00";
	public static final String WEEKHAY_WEEK_TIME_11_00_00 = "11:00:00";
	public static final String WEEKHAY_WEEK_TIME_13_55_00 = "13:55:00";
	public static final String WEEKHAY_WEEK_TIME_15_00_00 = "15:00:00";
	public static final String WEEKHAY_WEEK_TIME_19_55_00 = "19:55:00";
	public static final String WEEKHAY_WEEK_TIME_21_00_00 = "21:00:00";
	public static final String WEEKHAY_WEEK_TIME_20_00_00 = "20:00:00";
	public static final String WEEKHAY_WEEK_TIME_14_00_00 = "14:00:00";
	public static final String FROM_178_APP = "178_APP";	// 来自于178APP

	//周周乐活动url链接
	public static final String BANNER_URL_WEEKHAY_BGW_PC = "gen2.0/activity/weekhay_index";
	public static final String BANNER_URL_WEEKHAY_BGW_H5 = "micro2.0/activity/weekhay_index";
	public static final String BANNER_URL_WEEKHAY_APP = "micro2.0/activity/weekhay_index_app";

	//周周乐活动
	public static final String WEEKHAY_SCRATCH_CARD = "SCRATCH_CARD"; // 刮刮乐活动
	public static final String WEEKHAY_FIGHT_SECONDS = "FIGHT_SECONDS"; // 争标夺秒活动
	public static final String WEEKHAY_LUCKY_LENDERS = "LUCKY_LENDERS"; //幸运出借人活动

	//加薪计划活动url链接
	public static final String BANNER_URL_SALARY_INCREASE = "/activity/salary_increase_plan";
	
	//加薪计划活动时间
	public static final Integer SALARY_INCREASE_DATE_15 = 15;
	
	public static final String PRE_TOKEN_KEY = "_pre_token_";

	//活动发布端口
	public static final String APP_ACTIVE_SHOW_TERMINAL_BGW_PC = "BGW_PC"; //币港湾PC
	public static final String APP_ACTIVE_SHOW_TERMINAL_BGW_H5 = "BGW_H5"; //币港湾H5
	public static final String APP_ACTIVE_SHOW_TERMINAL_BGW_APP = "BGW_APP"; //币港湾APP

	// 展示页面
	public static final String SHOW_PAGE_INDEX = "INDEX";	// 首页
	public static final String SHOW_PAGE_LOGIN = "LOGIN";	// 登录
	public static final String SHOW_PAGE_REGISTER = "REGISTER";	// 注册
	public static final String SHOW_PAGE_FORGET_PASSWORD = "FORGET_PASSWORD";	// 忘记密码页面
	public static final String SHOW_PAGE_PRODUCT_LIST = "PRODUCT_LIST";	// 理财列表页面

	public static final String POSITION_NOTICE = "NOTICE";
	public static final String POSITION_ACTIVITY = "ACTIVITY";

	public static final String RECOMMEND_ACTIVITY_TYPE = "2018_recommend";
	public static final String ACTIVITY_IS_START = "start";	// 已经开始
	public static final String ACTIVITY_IS_END = "end";	// 已经结束
	public static final String ACTIVITY_IS_NOT_START = "not_start";	// 未开始

	//赞时贷资金迁移子账户表标志
	public static final String SUB_ACCOUNT_AUTH_ZSD_2_AUTH_YUN = "AUTH_ZSD_2_AUTH_YUN"; // 赞时贷资金迁移子账户表note标志

	//协议类型（协议版本控制）
	public static final String AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN = "hf_7dai_loanAgreement"; // 存管7贷借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_7DAI_CLAIMS = "hf_7dai_claimsAgreement"; // 存管7贷债转协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN = "stock_hf_7dai_loanAgreement"; // 存量数据七贷存管借款协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_CLAIMS = "stock_hf_7dai_claimsAgreement"; // 存量数据七贷存管债转协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN = "stock_hf_yundai_loanAgreement"; // 存量数据云贷存管借款协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_CLAIMS = "stock_hf_yundai_claimsAgreement"; // 存量数据云贷存管债转协议
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY = "hf_yundai_powerAttorney"; // 存管云贷授权委托书
	public static final String AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_POWERATTORNEY = "hf_7dai_powerAttorney"; // 存管7贷授权委托书
	public static final String AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY = "hf_7dai_loanAgreement_anyRepay"; // 存管7贷随借随还产品借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT = "hf_yundai_loanAgreement_installment"; // 存管云贷等额本息产品借款协议
	public static final String AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST = "hf_yundai_loanAgreement_principal_interest"; // 存管云贷等本等息产品借款协议

	//协议版本号
	public static final String AGREEMENT_VERSION_NUMBER_NO_VERSION = "NO_VERSION"; // 版本号不存在（老协议）
	public static final String AGREEMENT_VERSION_NUMBER_1_1 = "1_1";
	public static final String AGREEMENT_VERSION_NUMBER_1_2 = "1_2";
	public static final String AGREEMENT_VERSION_NUMBER_1_3 = "1_3";

	public static final String SESSION_TERMINAL_PC = "PC";
	public static final String SESSION_TERMINAL_H5 = "H5";
	public static final String IS_NO = "no";
	public static final String IS_YES = "yes";
	public static final String USER_NEED_FORCED_LOGOUT_CODE = "910093";

	// 优惠券-加息券
	public static final String TICKET_INTEREST_BIZ_TYPE_BUY = "BUY";	// 业务类型：BUY-投资加入；TICKET-优惠券列表
	public static final String TICKET_INTEREST_BIZ_TYPE_TICKET = "TICKET";	// 业务类型：BUY-投资加入；TICKET-优惠券列表
	public static final String TICKET_INTEREST_TYPE_RED_PACKET = "RED_PACKET";	// 红包
	public static final String TICKET_INTEREST_TYPE_INTEREST_TICKET = "INTEREST_TICKET";	// 加息券
	public static final String TICKET_INTEREST_STATUS_INIT = "INIT";	// 加息券使用状态INIT-未使用；USED-已使用；OVER-已过期
	public static final String TICKET_INTEREST_STATUS_USED = "USED";	// 加息券使用状态INIT-未使用；USED-已使用；OVER-已过期
	public static final String TICKET_INTEREST_STATUS_OVER = "OVER";	// 加息券使用状态INIT-未使用；USED-已使用；OVER-已过期
	public static final String IS_SUPPORT_TICKET_INTEREST_TRUE = "TRUE";	// 支持加息券
	public static final String IS_SUPPORT_TICKET_INTEREST_FALSE = "FALSE";	// 不支持加息券

	// 云贷产品 业务标识
	public static final String YUN_DAI_SELF_REPAY_ANY_TIME="REPAY_ANY_TIME";	//(消费循环贷)
	public static final String YUN_DAI_SELF_FIXED_INSTALLMENT="FIXED_INSTALLMENT";//(等额本息)
	public static final String YUN_DAI_SELF_FIXED_PRINCIPAL_INTEREST="FIXED_PRINCIPAL_INTEREST";//(等本等息)
	
	//迁移前的还款标记
	public static final String REPAY_STATUS_SUCC_FLAG = "SUCC"; //已还款
	public static final String REPAY_STATUS_FAIL_FLAG = "FAIL"; //未还款和部分还款
	
	//积分商城-请求端口
	public static final String REQUEST_TERMINAL_H5="h5"; //微信请求
	public static final String REQUEST_TERMINAL_ANDROID = "android"; //安卓请求
	public static final String REQUEST_TERMINAL_IOS="ios"; //苹果请求
	public static final String REQUEST_TERMINAL_PC="pc"; //官网请求
	public static final String REQUEST_TERMINAL_PC_AGENT="pc_a"; //钱报系和秦皇岛系请求
	
	public static final String SEVEN_DIAN_DEFAULT_SALES_ID_KEY = "7_DIAN_DEFAULT_SALES_ID";	
	public static void main(String[] args) {		System.out.println(new DESUtil("cfgubijn").encryptStr("2000034"));
	}
}
