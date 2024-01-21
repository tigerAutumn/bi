package com.pinting.mall.util;

public class Constants {
	//和business服务共用的redis
	public static final String REDIS_SUBSYS_BUSINESS = "_business";
	//报文类前置
	public static final String SITE_MESSAGE_RESMSG_PRE = "com.pinting.mall.hessian.site.message.ResMsg_";//返回报文类前置

	//特殊交易流水表处理状态
	public static final int SPECIAL_JNL_STATUS_CREATE = 1; //创建
	public static final int SPECIAL_JNL_STATUS_DEAL = 2; //处理中
	public static final int SPECIAL_JNL_STATUS_SUCCESS = 3; //成功
	public static final int SPECIAL_JNL_STATUS_FALL= 4; //失败

	//系统Key值
	public static final String TASK_MAX_RECORDS_ONE		= "MALL_POINTS_MAX_ROWS_ONE";  	//每批次最大处理笔数
	public static final String TASK_MAX_RECORDS_MORE	= "MALL_POINTS_MAX_ROWS_MORE";  //每批次最大处理笔数

	//账户状态
	public static final Integer MALL_ACCOUNT_STATUS_OPEN 	= 1;
	public static final Integer MALL_ACCOUNT_STATUS_CANCEL 	= 2;
	public static final String MALL_ACCOUNT_ACCOUNT_TYPE_JSH = "POINTS_JSH";
	//规则状态
	public static final String MALL_RULE_STATUS_OPEN = "OPEN";
	public static final String MALL_RULE_STATUS_CLOSE = "CLOSE";
	public static final String MALL_RULE_STATUS_DELETED = "DELETED";
	//积分行为状态
	public static final String MALL_POINTS_INCOME_STATUS_INIT = "INIT";
	public static final String MALL_POINTS_INCOME_STATUS_FINISHED = "FINISHED";
	public static final String MALL_POINTS_INCOME_STATUS_CANCELLED = "CANCELLED";
	public static final String MALL_POINTS_INCOME_FIRST_INVEST_FLAG_YES = "YES";
	public static final String MALL_POINTS_INCOME_FIRST_INVEST_FLAG_NO = "NO";


	//积分规则-获取次数
	public static final String MALL_GET_TIMES_ONE 	= "ONE";	//单次获取
	public static final String MALL_GET_TIMES_MORE 	= "MORE";   //多次获取

	//积分规则-获取时间类型
	public static final String MALL_GET_TIME_TYPE_DAY = "DAY";	//日终
	public static final String MALL_GET_TIME_TYPE_REAL_TIME = "REAL_TIME";   //实时

	//积分规则-启用状态
	public static final String MALL_STATUS_OPEN 	= "OPEN";	//开启
	public static final String MALL_STATUS_CLOSE 	= "CLOSE";   //关闭
	public static final String MALL_STATUS_DELETED	= "DELETED";   //已删除

	//积分发货状态
	public static final String SEND_COMMODITY_STATUS_STAY_SEND = "STAY_SEND";	//待发货
	public static final String SEND_COMMODITY_STATUS_FINISHED = "FINISHED";   //已发货
	
	// 积分订单状态
	public static final String ORDER_STATUS_FINISHED = "FINISHED";   // 已完成
	public static final String ORDER_STATUS_SEND_FAIL = "SEND_FAIL"; // 发放失败
	
	//积分发货状态
	public static final String COMM_PROPERTY_REAL = "REAL";	//实体
	public static final String COMM_PROPERTY_EMPTY = "EMPTY";   //虚拟
	
	//红包、优惠券-积分商城兑换成功存入redis-key
	public static final String MALL_SEND_TICKET_QUEUE_KEY = "mall_send_ticket_queue";

	// 默认分页 信息
	public static final int MANAGE_DEFAULT_PAGENUM = 1; // 默认起始页码
	public static final int MANAGE_DEFAULT_NUMPERPAGE = 20; // 默认每页条数

	// 积分商品类型CODE：
	public static final String COMM_PROPERTY_RED_PACKET = "RED_PACKET";	// 红包
	public static final String COMM_PROPERTY_INTEREST_TACKET = "INTEREST_TACKET";   // 加息券

	//积分商城-请求端口
	public static final String REQUEST_TERMINAL_H5="h5"; //微信请求
	public static final String REQUEST_TERMINAL_ANDROID = "android"; //安卓请求
	public static final String REQUEST_TERMINAL_IOS="ios"; //苹果请求
	public static final String REQUEST_TERMINAL_PC="pc"; //pc请求
}
