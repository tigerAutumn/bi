package com.pinting.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	//管理平台 默认分页 信息
	public static final int MANAGE_DEFAULT_PAGENUM = 1; //管理平台默认起始页码
	public static final int MANAGE_DEFAULT_NUMPERPAGE = 20; //管理平台默认每页条数
	public static final int MANAGE_50_NUMPERPAGE = 50; //管理平台每页条数50
	public static final int MANAGE_100_NUMPERPAGE = 100; //管理平台每页条数100
	public static final int MANAGE_200_NUMPERPAGE = 200; //管理平台每页条数100
	public static final int MANAGE_1000_NUMPERPAGE = 1000; //管理平台每页条数1000

	//系统配置 操作标志
	public static final String SYSCONFIG_UPDATEFLAG_CREATE = "create"; //新增 系统配置
	public static final String SYSCONFIG_UPDATEFLAG_UPDATE = "update"; //修改 系统配置
	public static final String SYSCONFIG_UPDATEFLAG_DELETE = "delete"; //删除 系统配置

	public static final Integer APPOINT_DEAL = 2; //处理
	public static final Integer APPOINT_NO_DEAL = 1;//未处理
	//折线图起始时间2015-3-02 （月份-1）
	public static final String CHARTS_START_TIME = "2015, 2, 17"; //3,6,12个月折线图起始时间
	public static final String CHARTS_START_ONETIME = "2015, 2, 26"; //一个月折线图起始时间
	public static final String CHARTS_START_ACONETIME = "2015, 2, 27"; //一个月折线图起始时间

	public static final String BSRESCODE_SUCCESS = "000000";//交易成功
	public static final String BSRESCODE_FAIL = "999999";//交易失败

	//app是否测试标识
	public static String IS_PUSH_TEST = "test";
	//推送消息每次添加的记录数
	public static int everyNum = 500;

	//标的状态
	public static final Integer PRODUCT_STATUS_CHECK_NO = 1; //未审核
	public static final Integer PRODUCT_STATUS_CHECKING = 2; //审核中
	public static final Integer PRODUCT_STATUS_PASS_NO = 3; //未通过
	public static final Integer PRODUCT_STATUS_PUBLISH_NO = 4; //待发布
	public static final Integer PRODUCT_STATUS_PUBLISH_YES = 5; //未开放(已发布)
	public static final Integer PRODUCT_STATUS_OPENING = 6; //进行中
	public static final Integer PRODUCT_STATUS_FINISH = 7; //已完成

	//还款计划表还款状态
	public static final String LN_REPAY_STATUS_INIT = "INIT";	// 未还款
	public static final String LN_REPAY_PART_REPAY = "PART_REPAY";	//  部分还款
	public static final String LN_REPAY_REPAIED = "REPAIED";	// 已还款
	public static final String LN_REPAY_LATE_NOT = "LATE_NOT";	// 逾期未还款
	public static final String LN_REPAY_LATE_REPAIED = "LATE_REPAIED";	// 逾期已还款
	public static final String LN_REPAY_CANCELLED = "CANCELLED"; //已废除

	//产品类型
	public static final String PRODUCT_TYPE = "REG";

	//产品是否推荐
	public static final String PRODUCT_IS_SUGGEST_YES = "YES";
	public static final String PRODUCT_IS_SUGGEST_NO = "NO";

	//系列名称类型
	public static final String PRODUCT_SERIAL_NAME_SERIAL = "SERIAL"; //系列
	public static final String PRODUCT_SERIAL_NAME_CUSTOMIZE = "CUSTOMIZE"; //自定义

	// 产品活动类型
	public static final String PRODUCT_ACTIVITY_TYPE_NORMAL = "NORMAL";    // 普通
	public static final String PRODUCT_ACTIVITY_TYPE_NEW_BUYER = "NEW_BUYER";  // 新手标

	// APP推送消息推送类型bs_app_message
	public static final String APP_MESSAGE_PUSH_TYPE_1 = "url";
	public static final String APP_MESSAGE_PUSH_TYPE_2 = "文本";
	public static final String APP_MESSAGE_PUSH_TYPE_3 = "app内页";

	//产品类型是否支持红包
	public static final String IS_SUPPORT_RED_PACKET_TRUE = "TRUE";  //支持使用红包
	public static final String IS_SUPPORT_RED_PACKET_FALSE = "FALSE"; //不支持使用

	public static final String IS_SUPPORT_INCRINTEREST_FALSE = "FALSE"; //不支持使用加息券
	
	//资金接收方标志
	public static final String PROPERTY_SYMBOL_YUN_DAI = "YUN_DAI"; //云贷循环贷
	public static final String PROPERTY_SYMBOL_7_DAI = "7_DAI"; //七贷循环贷
	public static final String PROPERTY_SYMBOL_ZAN = "ZAN"; //赞分期
	public static final String PROPERTY_SYMBOL_YUN_DAI_SELF = "YUN_DAI_SELF"; //云贷循环贷自主放款
	public static final String PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF="7_DAI_SELF"; //七贷存管
	public static final String PROPERTY_SYMBOL_ZSD = "ZSD"; //赞时贷
	public static final String PROPERTY_SYMBOL_FREE = "FREE"; //自由站岗户

	public static final String YUN_HEAD_FEE_2_USER = "YUN_HEAD_FEE_2_USER";	// 云贷砍头息划转目标用户
	public static final String ZSD_HEAD_FEE_2_USER = "ZSD_HEAD_FEE_2_USER"; // 赞时贷砍头息划转目标用户

	//管理台，客户经理业务，产品分类
  	public static final String PRODUCT_TYPE_NORMAL ="NORMAL";//普通产品
  	public static final String PRODUCT_TYPE_ZAN = "ZAN";//分期产品

	public static final String SYS_ACCOUNT_DEP_JSH = "DEP_JSH";//存管体系-自有子账户
	public static final String SYS_ACCOUNT_DEP_REDPACKET = "DEP_REDPACKET";//存管体系-红包子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN = "DEP_BGW_REVENUE_ZAN";//存管体系-币港湾对赞分期营收子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_YUN = "DEP_BGW_REVENUE_YUN";//存管体系-币港湾对云贷营收子账户
	public static final String SYS_ACCOUNT_DEP_HEADFEE_YUN = "DEP_HEADFEE_YUN";//存管体系-砍头息子账户
	public static final String SYS_ACCOUNT_DEP_OTHER_FEE = "DEP_OTHER_FEE";//存管体系-其他费用户
	public static final String SYS_ACCOUNT_DEP_ADVANCE = "DEP_ADVANCE";//垫付金子账户
	public static final String PRODUCT_TYPE_RED_ZSD = "RED_ZSD";//红包子账户（赞时贷）
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD = "DEP_BGW_REVENUE_ZSD";//存管体系-币港湾对赞时贷营收子账户
	public static final String SYS_ACCOUNT_DEP_HEADFEE_ZSD = "DEP_HEADFEE_ZSD";//存管体系-币港湾对赞时贷砍头息子账户
	public static final String SYS_ACCOUNT_DEP_BGW_REVENUE_7 = "DEP_BGW_REVENUE_7";//存管体系-币港湾对7贷营收子账户

	public static final String COMPENSATE_ZAN_TOP_UP = "ZAN_TOP_UP";//赞分期代偿人充值标志
	public static final String COMPENSATE_ZAN_WITHDRAW = "ZAN_WITHDRAW";//赞分期代偿人提现标志

	public final static String VIP_QUIT_STATUS_INIT = "INIT";	// 待审核
	public final static String VIP_QUIT_STATUS_PASS = "PASS";	// 待退出
	public final static String VIP_QUIT_STATUS_REFU = "REFU";	// 拒绝
	public final static String VIP_QUIT_STATUS_QUIT_SUCC = "QUIT_SUCC";	// 退出成功
	public final static String VIP_QUIT_STATUS_QUIT_FAIL = "QUIT_FAIL";	// 退出失败

	//产品子账户状态
	public static final Integer SUBACCOUNT_STATUS_OPEN = 1; //开户
	public static final Integer SUBACCOUNT_STATUS_FINANCING = 2; //投资中
	public static final Integer SUBACCOUNT_STATUS_TRANSFER = 3; //转让中
	public static final Integer SUBACCOUNT_STATUS_TRANSFERED = 4; //已转让
	public static final Integer SUBACCOUNT_STATUS_SETTLE  = 5; //已结算
	public static final Integer SUBACCOUNT_STATUS_CLOSE = 6; //销户
	public static final Integer SUBACCOUNT_STATUS_SETTLEING = 7; //结算中

	//订单支付状态
	public static final String ORDER_STATUS_CREATE = "1";//1-创建
	public static final String ORDER_STATUS_CON_FAIL = "2";//2-通讯失败
	public static final String ORDER_STATUS_PRE_SUCC = "3";//3-预下单成功
	public static final String ORDER_STATUS_PRE_FAIL = "4";//4-预下单失败
	public static final String ORDER_STATUS_PAYING = "5";//5-支付处理中
	public static final String ORDER_STATUS_SUCCESS = "6";//6-支付成功
	public static final String ORDER_STATUS_FAIL = "7";//7-支付失败

	public static final String PRODUCT_TYPE_AUTH_YUN = "AUTH_YUN";//站岗户（云贷）
	public static final String PRODUCT_TYPE_AUTH_ZSD = "AUTH_ZSD";//站岗户（赞时贷）
	public static final String PRODUCT_TYPE_AUTH_7 = "AUTH_7";//站岗户（7贷）
	public static final String PRODUCT_TYPE_AUTH = "AUTH";//站岗户（赞分期）
	public static final String PRODUCT_TYPE_AUTH_FREE = "AUTH_FREE";//自由站岗户

	//资产方线下还款对账状态
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_INIT = "INIT"; // 未还款
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAYING = "REPAYING"; //  还款中
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAIED = "REPAIED"; // 已还款
	public static final String LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAY_FAIL = "REPAY_FAIL"; //还款失败

    //借贷关系表状态
    public static final String LOAN_RELATION_STATUS_PAYING = "PAYING"; //付款中
    public static final String LOAN_RELATION_STATUS_SUCCESS = "SUCCESS"; //成功
    public static final String LOAN_RELATION_STATUS_FAIL = "FAIL"; //失败
    public static final String LOAN_RELATION_STATUS_TRANSFERRED  = "TRANSFERRED"; //已转让
    public static final String LOAN_RELATION_STATUS_REPAID = "REPAID"; //已还完

	//代收代付常量
	public static final String COLLECT_PAY_DS = "代收"; //代收
	public static final String COLLECT_PAY_DF = "代付"; //代付

	public static final String NEWS_RECEIVER_TYPE_BGW = "BGW";    // 币港湾
	public static final String NEWS_RECEIVER_TYPE_BGW178 = "BGW178";    // 178
	public static final String NEWS_RECEIVER_TYPE_BGWKQ = "BGWKQ";    // BGWKQ
	public static final String NEWS_RECEIVER_TYPE_BGWHN = "BGWHN";    // BGWHN
	public static final String NEWS_RECEIVER_TYPE_BGWRUIAN = "BGWRUIAN";    // 瑞安
	public static final String NEWS_RECEIVER_TYPE_BGWQD = "BGWQD";    // 七店

	public static final String NEWS_RECEIVER_TYPE_BGWQHDJT = "BGWQHDJT";    // 秦皇岛交通广播
	public static final String NEWS_RECEIVER_TYPE_BGWQHDXW = "BGWQHDXW";    // 秦皇岛新闻891
	public static final String NEWS_RECEIVER_TYPE_BGWQHDTV = "BGWQHDTV";    // 秦皇岛电视台今日报道
	public static final String NEWS_RECEIVER_TYPE_BGWQHDST = "BGWQHDST";    // 视听之友
	public static final String NEWS_RECEIVER_TYPE_BGWQHDSJC = "BGWQHDSJC";    // 1038私家车广播
	
	// 用户注册时终端
	public static final String BS_USER_REG_TERMINAL_1 = "1"; // H5端
	public static final String BS_USER_REG_TERMINAL_2 = "2"; // PC端
	public static final String BS_USER_REG_TERMINAL_3 = "3"; // Android端
	public static final String BS_USER_REG_TERMINAL_4 = "4"; // iOS端

	// 报业分销渠道
	public static final String BS_USER_AGENT_ID_15 = "15"; // 钱报
	public static final String BS_USER_AGENT_ID_36 = "36"; // 柯桥日报
	public static final String BS_USER_AGENT_ID_46 = "46"; // 海宁日报
	public static final String BS_USER_AGENT_ID_47 = "47"; // 瑞安日报

	// 产品标签类型
	public static final String BS_PRODUCT_TAG_INTEREST_RATES_TAG = "INTEREST_RATES_TAG"; // 加息标签
	public static final String BS_PRODUCT_TAG_REMIND_TAG = "REMIND_TAG"; // 提醒标签

	//积分商城常量

	//商品类别编码
	public static final String MALL_COMMODITY_TYPE_CODE_RED_PACKET = "RED_PACKET"; // 红包
	public static final String MALL_COMMODITY_TYPE_CODE_INTEREST_TACKET = "INTEREST_TACKET"; // 加息券

	public static final int MYSQL_TEXT_MAX_LENGHT = 65535; // mysql数据库text最大长度

	//系统维护状态
	public static Integer sysValue = 1;//是否挂起整个系统（不允许系统中任何业务运行），1-正常，2-挂起
	public static Integer tranValue = 1;//是否挂起交易类业务（不允许用户发起购买等交易相关的业务），1-正常，2-挂起
		
	//微信消息自动回复--回复类型
	public static final String WX_AUTO_REPLY_TYPE_SUBSCRIBE = "SUBSCRIBE_REPLY"; //关注时回复
	public static final String WX_AUTO_REPLY_TYPE_KEY = "KEY_REPLY"; //关键字回复

	//微信消息自动回复--回复消息内容类型
	public static final String WX_AUTO_REPLY_MSG_TYPE_TEXT = "text"; //文本
	public static final String WX_AUTO_REPLY_MSG_TYPE_NEWS = "news"; //图文
		
	// 用户地址管理默认值
	public static final String USER_ADDRESS_INFO_IS_DEFAULT_YES = "YES"; // 是
	public static final String USER_ADDRESS_INFO_IS_DEFAULT_NO = "NO"; // 否

	public static final int EXCEL_SHEET_EXPORT_MAX_SIZE = 30000; // 导出excel单个sheet的最大行数60000

	// 公众号渠道管理是否删除标记
	public static final int WX_AGENT_IS_DELETE_1 = 1; // 是
	public static final int WX_AGENT_IS_DELETE_0 = 0; // 否

	/**
	 * 页面vm常量获得方法
	 * @param key 传入常量名， 如SYSCONFIG_UPDATEFLAG_CREATE
	 * @return
	 */
	public static String get(String key){
		Logger log = LoggerFactory.getLogger(Constants.class);
		String value = "";
		try {
			Class<Constants> clazz = Constants.class;
			Field field = clazz.getDeclaredField(key);
			value = String.valueOf(field.get(clazz));
		} catch (NoSuchFieldException e) {
			log.warn("can't find such field:" + key);
		} catch (Exception e) {
			log.warn("can't get such field:" + key);
		}

		return value;
	}
}
