package com.pinting.gateway.mobile.in.util;

public class Constants {

	//手机发送失败
	public static final String SEND_CODE_ERROR = "sending error";
	public static final String SEND_CODE_NULL = "not sending";
	
	//用户类型
	public static final String USER_TYPE_NORMAL = "NORMAL";
	public static final String USER_TYPE_PROMOT = "PROMOT";
	
	//分页大小
	public static final Integer EXCHANGE_PAGE_SIZE = 10;
	public static final Integer EXCHANGE_PAGE_SIZE_TRADING_DETAIL = 20;
	
	//交易类型1卡购买,2充值
	public static final Integer TRANS_TYPE_CARD_BUY = 1;
	public static final Integer TRANS_TYPE_TOP_UP = 2;
	
	//下单类型@1预下单,2正式下单
	public static final Integer PLACE_ORDER_PREPARE = 1;
	public static final Integer PLACE_ORDER_FORMAL = 2;
	
	//是否绑卡@1已绑卡，2未绑卡
	public static final Integer IS_BIND_YES = 1;
	public static final Integer IS_BIND_NO = 2;
	
	//分享相关参数
	public static final String SHARE_LOGO = "/resources/micro/images/bgw.jpg";
	public static final String SHARE_TITLE = "好友邀请你来赚奖励金啦，加入好友邀请，最高享预期年化收益+0.5%";
	public static final String SHARE_CONTENT = "苟富贵，一起赚！加入币港湾，你一份我一份，和我一起赚奖励金！";
	
	public static final int MANAGER_AGENT_ID = 34;    // 客户经理渠道ID
	
	//标的状态
	public static final Integer PRODUCT_STATUS_CHECK_NO = 1; //未审核
	public static final Integer PRODUCT_STATUS_CHECKING = 2; //审核中
	public static final Integer PRODUCT_STATUS_PASS_NO = 3; //未通过
	public static final Integer PRODUCT_STATUS_PUBLISH_NO = 4; //待发布
	public static final Integer PRODUCT_STATUS_PUBLISH_YES = 5; //未开放(已发布)
	public static final Integer PRODUCT_STATUS_OPENING = 6; //进行中
	public static final Integer PRODUCT_STATUS_FINISH = 7; //已完成
	
  	//资金接收方标识
    public static final String PRODUCT_PROPERTY_SYMBOL_YUN_DAI = "YUN_DAI";  // 云贷
    public static final String PRODUCT_PROPERTY_SYMBOL_7_DAI = "7_DAI";  // 7贷
    public static final String PRODUCT_PROPERTY_SYMBOL_ZAN = "ZAN";  // 赞分期
	public static final String PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF="YUN_DAI_SELF"; //云贷自主放款
	public static final String PRODUCT_PROPERTY_SYMBOL_SEVEN_DAI_SELF="7_DAI_SELF"; //云贷自主放款
	public static final String PRODUCT_PROPERTY_SYMBOL_ZSD = "ZSD";  // 赞时贷
	public static final String PRODUCT_PROPERTY_SYMBOL_MAIN = "MAIN";  //转账到主通道

	//渠道交易类型
	public static final String CHANNEL_DAFY = "DAFY";//达飞渠道
	public static final String CHANNEL_PAY19 = "PAY19";//19付渠道
	public static final String CHANNEL_REAPAL = "REAPAL";//融宝渠道
	public static final String CHANNEL_BAOFOO = "BAOFOO";// 宝付渠道
	public static final String CHANNEL_HF = "HFBANK";// 恒丰
	public static final String NEWS_TYPE_NOTICE = "NOTICE";    // 公告
	public static final String NEWS_TYPE_NEWS = "NEWS";    // 新闻
	public static final String NEWS_TYPE_COMPANY_DYNAMIC = "COMPANY_DYNAMIC";    // 公司动态
	public static final String NEWS_TYPE_WFANS_ACTIVITY = "WFANS_ACTIVITY";   // 湾粉活动

	public static final String NEWS_RECEIVER_TYPE_BGW = "BGW";    // 币港湾
	public static final String NEWS_RECEIVER_TYPE_BGW178 = "BGW178";    // 178

	public static final String BONUS_TYPE_ACTIVITY = "ACTIVITY"; // 活动奖励
	public static final String BONUS_TYPE_BONUS_WITHDRAW = "BONUS_WITHDRAW"; // 提现到银行卡
	public static final String BONUS_TYPE_DEP_FILL_INTEREST = "DEP_FILL_INTEREST"; // 奖励金返还
	public static final String BONUS_TYPE_RECOMMEND = "RECOMMEND"; // 推荐奖励
	public static final String BONUS_TYPE_INTEREST_TICKET = "INTEREST_TICKET"; // 加息收益
	
	//系统新闻公告信息类型
	public static final String SYS_NEWS_TYPE_NOTICE = "NOTICE";//公告

}

