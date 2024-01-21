package com.pinting.util;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Messages {
	/**
	 * 页面vm 常量翻译成文字消息   获得方法
	 * @param key 传入常量名， 如PRODUCT_TYPE_JSH
	 * @return
	 */
	public static String get(String key){
		Logger log = LoggerFactory.getLogger(Messages.class);
		String value = "";
		try {
			Class<Messages> clazz = Messages.class;
			Field field = clazz.getDeclaredField(key);
			value = String.valueOf(field.get(clazz));
		} catch (NoSuchFieldException e) {
			log.warn("can't find such field:" + key);
		} catch (Exception e) {
			log.warn("can't get such field:" + key);
		}
		
		return value;
	} 
	
	//是否绑定
	public static final String IS_BIND_1 = "已绑定";//是
	public static final String IS_BIND_2 = "未绑定";//否
	
	//是否认证
	public static final String IS_AUTH_1 = "已认证";//是
	public static final String IS_AUTH_2 = "未认证";//否
	
	//用户状态
	public static final String USER_STATUS_1 = "正常使用";//正常使用
	public static final String USER_STATUS_2 = "注销";//正常使用
	public static final String USER_STATUS_3 = "禁用";//正常使用
	//记账流水状态
	public static final String ACCOUNT_JNL_STATUS_1 = "交易成功";//记账流水状态 交易成功
	public static final String ACCOUNT_JNL_STATUS_2 = "交易失败";//记账流水状态 交易失败
	public static final String ACCOUNT_JNL_STATUS_3 = "系统确认中";//记账流水状态 系统确认中
	public static final String ACCOUNT_JNL_STATUS_4 = "银行处理中";//记账流水状态 银行处理中
	public static final String ACCOUNT_JNL_STATUS_5 = "通讯超时";//记账流水状态 超时
	public static final String ACCOUNT_JNL_STATUS_9 = "状态未知";//未知
	//记账流水交易类型
	public static final String ACCOUNT_JNL_TRANS_CODE_CARD_BUY_PRODUCT = "卡购买";//记账流水交易类型   卡购买
	public static final String ACCOUNT_JNL_TRANS_CODE_BALANCE_BUY_PRODUCT = "余额购买";//记账流水交易类型   余额购买
	public static final String ACCOUNT_JNL_TRANS_CODE_TOP_UP = "充值";//记账流水交易类型  充值
	public static final String ACCOUNT_JNL_TRANS_CODE_FREEZE = "冻结";//记账流水交易类型  冻结
	public static final String ACCOUNT_JNL_TRANS_CODE_RETURN_2_BALANCE = "回款到余额";//记账流水交易类型   回款到余额
	public static final String ACCOUNT_JNL_TRANS_CODE_RETURN_2_USER_BANK_CARD = "回款到银行卡";//记账流水交易类型  回款到银行卡
	public static final String ACCOUNT_JNL_TRANS_CODE_BONUS_2_BALANCE = "奖励金转余额";//记账流水交易类型  奖励金转余额
	public static final String ACCOUNT_JNL_TRANS_CODE_RECOMMEND_BONUS = "获得推荐奖励";//记账流水交易类型  获得推荐奖励
	public static final String ACCOUNT_JNL_TRANS_CODE_BONUS_WITHDRAW = "奖励金提现";//记账流水交易类型   奖励金提现
	public static final String ACCOUNT_JNL_TRANS_CODE_BALANCE_WITHDRAW = "余额提现";//记账流水交易类型  余额提现
	public static final String ACCOUNT_JNL_TRANS_CODE_CHANNEL_WITHDRAW = "辅助通道提现";
	public static final String ACCOUNT_JNL_TRANS_CODE_SYS_BUY_DAFY = "系统购买";
	public static final String ACCOUNT_JNL_TRANS_CODE_MARKET_PAY = "营销代付";
	
	public static final String ACCOUNT_JNL_TRANS_CODE_BIND_CARD = "绑卡";
	public static final String ACCOUNT_JNL_TRANS_CODE_LOAN = "借款";
	public static final String ACCOUNT_JNL_TRANS_CODE_REPAY = "还款";
	//用户交易类型
	public static final String USER_TRADE_TRANS_TYPE_TOP_UP = "充值";//用户交易类型   充值
	public static final String USER_TRADE_TRANS_TYPE_BUY = "购买";//用户交易类型   购买
	public static final String USER_TRADE_TRANS_TYPE_RETURN = "回款";//用户交易类型  回款
	public static final String USER_TRADE_TRANS_TYPE_BONUS_2_BALANCE = "奖励金转余额";//用户交易类型  奖励金转余额
	public static final String USER_TRADE_TRANS_TYPE_WITHDRAW = "提现";//用户交易类型   提现
	//用户交易状态
	public static final String USER_TRADE_TRANS_STATUS_1 = "处理中";//用户交易状态 处理中
	public static final String USER_TRADE_TRANS_STATUS_2 = "成功";//用户交易状态  成功
	public static final String USER_TRADE_TRANS_STATUS_3 = "失败";//用户交易状态   失败
	//订单状态
	public static final String ACCOUNT_ORDER_STATUS_1 = "创建";//记账流水状态 订单创建
	public static final String ACCOUNT_ORDER_STATUS_2 = "通讯失败";//记账流水状态 通讯失败
	public static final String ACCOUNT_ORDER_STATUS_3 = "预下单成功";//记账流水状态 预下单成功
	public static final String ACCOUNT_ORDER_STATUS_4 = "预下单失败";//记账流水状态 预下单失败
	public static final String ACCOUNT_ORDER_STATUS_5 = "支付处理中";//记账流水状态 银行处理中
	public static final String ACCOUNT_ORDER_STATUS_6 = "支付成功";//记账流水状态 交易成功
	public static final String ACCOUNT_ORDER_STATUS_7 = "支付失败";//记账流水状态 交易失败
	
	//银行卡状态 
	public static final String BANK_CARD_STATUS_1 = "正常"; //正常
	public static final String BANK_CARD_STATUS_2 = "禁用"; //禁用
	public static final String BANK_CARD_STATUS_3 = "绑定中"; //绑定中
	public static final String BANK_CARD_STATUS_4 = "绑定失败"; //绑定失败
	
	//账户状态
	public static final String ACCOUNT_STATUS_1 = "开户";//开户
	public static final String ACCOUNT_STATUS_2 = "投资中";//投资中
	public static final String ACCOUNT_STATUS_3 = "转让中";//转让中
	public static final String ACCOUNT_STATUS_4 = "已转让";//已转让
	public static final String ACCOUNT_STATUS_5 = "已结算";//已结算
	public static final String ACCOUNT_STATUS_6 = "已销户";//已销户
	public static final String ACCOUNT_STATUS_7 = "结算中";//结算中
	//订单类型
	public static final String ORDER_TYPE_1 = "充值"; //充值
	public static final String ORDER_TYPE_2 = "转账"; //转账
	public static final String ORDER_TYPE_3 = "提现"; //提现
	public static final String ORDER_TYPE_4 = "购买";//购买
		
	//购买银行类别
	public static final String TERMINAL_TYPE_1 = "手机端"; //手机端
	public static final String TERMINAL_TYPE_2 = "pc端"; //pc端
	
	//系统提现状态
	public static final String SYS_WITHDRAW_1 = "发起提现"; //发起提现
	public static final String SYS_WITHDRAW_2 = "提现成功"; //发起提现
	public static final String SYS_WITHDRAW_3 = "提现失败"; //发起提现

	
	//交易类型
	public static final String TRANS_TYPE_001 = "用户提现转出"; 
	public static final String TRANS_TYPE_002 = "网新提现";
	public static final String TRANS_TYPE_003 = "营销费转入"; 
	public static final String TRANS_TYPE_004 = "服务费转入";
	
	//渠道交易类型
	public static final String ACCOUNT_ORDER_CHANNEL_ = "";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_QUICK_PAY = "快捷";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_DK = "代扣";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_DF = "代付";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_NETBANK = "网银";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_TRANSFER = "钱包转账";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_REAL_NAME = "实名认证";
	public static final String ACCOUNT_ORDER_CHANNEL_TRANS_TYPE_BIND_CARD = "绑卡";
	
	//用户提现状态
	public static final String BSUSER_WITHDRAW_STATUS_1 = "发起提现"; 
	public static final String BSUSER_WITHDRAW_STATUS_2 = "提现成功";
	public static final String BSUSER_WITHDRAW_STATUS_3 = "提现失败"; 
	//验签状态
	public static final String CHECK_SIGN_STATUS_1 = "验签通过"; 
	public static final String CHECK_SIGN_STATUS_2 = "验签不通过";
	
	//记账流水交易类型
	public static final String ACCOUNT_JNL_TRANS_TYPE_1 = "充值"; 
	public static final String ACCOUNT_JNL_TRANS_TYPE_2 = "转账";
	public static final String ACCOUNT_JNL_TRANS_TYPE_3 = "提现"; 
	
	//订单明细流水 交易代码
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_INIT = "初始";
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_COMM_FAIL = "通讯失败";
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_PRE_SUCC = "预下单成功";
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_PRE_FAIL = "预下单失败";
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_PROCCESS = "下单处理中";
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_SUCCESS = "支付成功";
	public static final String ACCOUNT_ORDERS_JNL_TRANS_CODE_FAIL = "支付失败";
	
	//账户类型
	public static final String ACCOUNT_ORDERS_ACCOUNT_TYPE_1 = "用户";
	public static final String ACCOUNT_ORDERS_ACCOUNT_TYPE_2 = "系统";
	
	//终端类型
	public static final String ACCOUNT_ORDERS_TERMINAL_TYPE_1 = "H5";
	public static final String ACCOUNT_ORDERS_TERMINAL_TYPE_2 = "PC端";
	public static final String ACCOUNT_ORDERS_TERMINAL_TYPE_3 = "Android端";
	public static final String ACCOUNT_ORDERS_TERMINAL_TYPE_4 = "iOS端";
	
	//渠道类型(错误码)
	private static final String BSERRORCODE_CHANNEL_TYPE_PAY19 = "19支付渠道";
	private static final String BSERRORCODE_CHANNEL_TYPE_REAPAL = "融宝渠道";
	private static final String BSERRORCODE_CHANNEL_TYPE_DAFY = "达飞渠道";
	private static final String BSERRORCODE_CHANNEL_TYPE_BAOFOO = "宝付渠道";
	private static final String BSERRORCODE_CHANNEL_TYPE_HFBANK = "恒丰渠道";
	
	//用户优先支付渠道(渠道类型  )
	private static final String BSUSERCHANNEL_CHANNEL_PAY19 = "19支付渠道";
	private static final String BSUSERCHANNEL_CHANNEL_REAPAL = "融宝渠道";
	
	//用户优先支付渠道(渠道优先级)
	private static final String BSUSERCHANNEL_CHANNELPRIORITY_1 = "主通道";
	private static final String BSUSERCHANNEL_CHANNELPRIORITY_2 = "从通道";
	
	//订单渠道
	private static final String ACCOUNT_ORDER_CHANNEL_DAFY = "达飞";
	private static final String ACCOUNT_ORDER_CHANNEL_PAY19 = "19付";
	private static final String ACCOUNT_ORDER_CHANNEL_REAPAL = "融宝";
	private static final String ACCOUNT_ORDER_CHANNEL_BAOFOO = "宝付";
	private static final String ACCOUNT_ORDER_CHANNEL_HFBANK = "恒丰";
	
	//红包审核状态
	private static final String BSREDPACKET_APPLY_INIT = "审核中";
	private static final String BSREDPACKET_APPLY_PASS = "已通过";
	private static final String BSREDPACKET_APPLY_REFUSE = "已拒绝";
	
	//系统新闻公告信息表状态
	private static final String BS_SYS_NEWS_STAUTS_PUBLISHED = "已发布";
	private static final String BS_SYS_NEWS_STAUTS_NOT_PUBLISH = "未发布";
	private static final String BS_SYS_NEWS_STAUTS_REMOVED = "已撤下";
	
	//理财计划状态
	private static final String PRODUCT_STATUS_1 = "未提审";
	private static final String PRODUCT_STATUS_2 = "审核中";
	private static final String PRODUCT_STATUS_3 = "未通过";
	private static final String PRODUCT_STATUS_4 = "待发布";
	private static final String PRODUCT_STATUS_5 = "未开放";
	private static final String PRODUCT_STATUS_6 = "进行中";
	private static final String PRODUCT_STATUS_7 = "已完成";

	//资金接收方标志
	private static final String PROPERTY_SYSBOL_YUN_DAI = "云贷循环贷";
	private static final String PROPERTY_SYSBOL_7_DAI = "7贷循环贷";
	private static final String PROPERTY_SYSBOL_ZAN = "赞分期";
}
