package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * redis锁枚举
 * @author dingpf
 *
 */
public enum RedisLockEnum {
	
	LOCK_WITHDRAW_FACADE("withdraw_facade", "提现（Facade）锁"),
	LOCK_WITHDRAW_SERVICE("withdraw_service", "提现（Service）锁"),
	LOCK_WITHDRAW_CHECK_PASS_SERVICE("withdraw_pass_service", "提现审核通过（Service）锁"),
	LOCK_WITHDRAW_PASS_QUE("withdraw_pass_que", "提现审核通过队列处理锁"),
	LOCK_TRANS2DAFY_RESULT("trans2DafyResultNotice", "转账给达飞结果通知锁"),
	LOCK_DAFYPAYMENT_FACADE("dafyPayment_facade", "达飞支付相关（Facade）锁"),
	LOCK_BAOFOOPAYMENT_RESULT("baofooPaymentResult", "宝付查询订单结果查询锁"),
	LOCK_OLD_RECEIVEMONEY("oldReceiveMoney", "云贷1.0老系统回款锁"),
	LOCK_BONUS2JSH("bonusToJSH", "用户奖励金转结算户锁"),
	LOCK_USER_TOPUP("userTopUp", "用户充值锁"),
	LOCK_USER_BUYPRODUCT("userBuyProduct", "用户理财购买锁"),
	LOCK_SYS_RECEIVEMONEY("sysReceiveMoney", "系统回款锁"),
	LOCK_USER_RECEIVEMONEY("userReceiveMoney", "用户回款锁"),
	LOCK_ACCESSTOKEN_SYNC("accessTokenSync", "微信accessToken锁"),
	LOCK_TICKET_SYNC("ticketSync", "微信ticket锁"),
	LOCK_LOAN("loanSync", "借款锁"),
	LOCK_LOAN_MATCH("loanMatchSync", "借款债权匹配锁"),
	LOCK_REPAY("repaySync", "还款锁"),
	LOCK_MARKETING("marketingSync", "营销代付锁"),
	LOCK_BIND_CARD("user_bind_card", "用户绑卡锁"),
	LOCK_TRANS4PARTNER_TOKEN("trans4Partner", "划账锁"),
	LOCK_ACTIVITY_LUCKY_DRAW("activityLuckyDraw","抽奖活动锁"),
	LOCK_LOAN_SELF("selfLoanSync_", "自主放款借款锁"),
	LOCK_LOAN_SELF_OUTOF_ACCOUNT("selfLoanSync_outOfAccount", "自主放款借款出账记账锁"),
	LOCK_REPAY_YUN_SELF("yunSelfRepaySync","云贷自主放款还款锁"),
	LOCK_REPAY_7_DAI_SELF("sevenSelfRepaySync", "7贷自主放款还款锁"),	//存管
	LOCK_REPAY_SELF("selfRepaySync_", "自主放款还款锁"),
	LOCK_DEPFIXED_LOAN_MATCH("depFixedLoanMatchSync", "存管固定期限产品借款债权匹配锁(云贷)"),
	LOCK_DEPFIXED_LOAN_MATCH_NEW("depFixedLoanMatchNewSync", "存管固定期限产品借款债权匹配锁(新)(云贷/七贷)"),
	LOCK_DEPFIXED_ZSD_LOAN_MATCH("depFixedZsdLoanMatchSync", "存管固定期限产品借款债权匹配锁(赞时贷)"),
	LOCK_WITHDRAW_2_DEP_REPAY_CARD("withdraw2DepRepayCard", "提现到恒丰还款实体户（卡）锁"),
	LOCK_DEPFIXED_QUIT_APPLY("depFixedLoanQuitApply", "存管固定期限产品统计退出数据"),
	LOCK_CUT_REPAY_2_BORROWER("cutRepay2Borrower", "代扣还款到借款人子账户锁"),
	LOCK_DEP_QUIT("depQuit", "存管定期产品退出服务"),
	LOCK_DEP_QUIT_GENERATE_PLANS("depQuitGeneratePlans", "存管定期产品退出生成回款计划"),
	LOCK_DEP_FIXED_ACTFILL("depFixedActfill", "存管固定期限产品补账完成锁"),
	LOCK_DEP_FIXED_USER_RECEIVEMONEY("depFixedUserReceiveMoney", "存管固定期限产品退出回款到余额"),
	LOCK_DEP_FIXED_LATE_REPAY_NOTICE("depFixedLateRepayNotice","存管固定期限产品代偿通知锁"),
	LOCK_DEP_LOAN_RELATION("depLoanRelation","存管借贷关系处理锁"),
	LOCK_UMENG_SYNC("lockUmengSync", "友盟消息通知锁"),
	LOCK_BILL_SYNC("lockBillSync", "账单推送通知锁"),
	LOCK_DEP_FIXED_LATE_REPAY_NOTICE_SECOND("depFixedLateRepayNoticeSecond","存管固定期限产品代偿通知业务逻辑锁"),
	//赞时贷
	LOCK_ZSD_LOAN_MATCH("zsdLoanMatchSync","存管固定期限产品借款债权匹配锁(赞时贷)"),
	LOCK_ZSD_REPAY("zsdRepaySync","赞时贷还款锁"),
	LOCK_LOAN_ZSD("zsdLoanSync", "赞时贷借款锁"),
	LOCK_ZSD_LOAN_RELATION("zsdLoanRelation","赞时贷借贷关系处理锁"),
	LOCK_QUESTIONNAIRE("questionnaireSync", "风险测评锁"),
	
	LOCK_SESSION_CONNECTION("sessionConnection", "用户连接数锁"),
	TICKET_HAPPY_BIRTHDAY("ticketHappyBirthday", "生日触发发放加息券锁"),
	REDPACKET_HAPPY_BIRTHDAY("redpacketHappyBirthday", "生日触发发放红包锁"),
	INTEREST_TICKET_NOTIFY("interestTicketNotify", "加息券信息通知提醒用户锁"),
	OLD_USER_AGREEMENT_SIGN("oldUserAgreementSign", "旧用户补签协议锁"),
	
	MALL_SEND_RED_PACKET("mallSendRedPacket","积分商城发放红包"),
	MALL_SEND_TICKET("mallSendTicket","积分商城发放加息券")

	;
	private RedisLockEnum(String key,String keyName){
		this.key = key;
		this.keyName = keyName;
	}
	private String key;
	private String keyName;
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static RedisLockEnum getEnumByKey(String key){
		if (null == key) {
			return null;
		}
		for (RedisLockEnum type : values()) {
			if (type.getKey().equals(key.trim()))
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
		for (RedisLockEnum type : values()) {
			enumDataMap.put(type.getKey(), type.getKeyName());
		}
		return enumDataMap;
	}
	
}
