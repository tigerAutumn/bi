package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

public enum PTMessageEnum {
	
	TRANS_SUCCESS("000000", "交易成功"),
	TRANS_ERROR("999999", "交易失败"),
	
	//一些公共类型异常，错误码以90开头6位数字，例：
	NO_DATA_FOUND("900001", "没有数据"),
	TRANSCODE_NOT_FOUND("900002", "交易码不符合规范"),
	DATA_VALIDATE_ERROR("900003", "数据校验失败："),
	ILLEGAL_REQUEST("900004", "非法请求"),
	DATE_TRANS_ERROR("900005", "日期格式转换失败"),
	CONNECTION_ERROR("900006","通讯异常"),
	CONNECTION_SUCCESS("900007","通讯成功"),
	LOAN_OVER("900008","该笔借款超过可借阈值"),
	HF_IN_MAINTENANCE("900009","系统维护中"),
	CONNECTION_TIME_OUT("900010","通讯超时"),
	CHECK_COUNT_OUT("900011","查询业务超过受理限制,请联系管理员"),
	REFUSE_LOAN("900012","拒绝借款"),
	
	//用户相关类型异常，错误码以91开头6位数字，例：
	USER_NO_EXIT("910000", "用户不存在"),
	USER_PASS_ERROR("910001", "登录名或密码错误"),
	USER_PAY_PASS_ERROR("910002", "用户支付密码错误"),
	USER_MOBILE_CODE_ERROR("910003","手机验证码发送异常"),
	USER_EMAIL_CODE_ERROR("910004","邮箱验证码发送异常"),
	MOBILE_IS_EXIT("910005", "手机已存在"),
	NICK_IS_EXIT("910006", "用户已存在"),
	MOBILE_IS_SAME_ERROR("910007", "手机号不能与绑定手机一样"),
	EMAIL_IS_SAME_ERROR("910008", "邮箱地址不能与绑定邮箱一样"),
	PAY_LOGIN_PASS_SAME_ERROR("910009", "登录密码不能与交易密码一样"),
	NAME_NOT_BIND("910010", "未实名认证"),
	BANKCARD_NOT_BIND("910011", "未绑定回款银行卡"),
	MOBILE_NO_EXIT("910012", "该手机未注册"),
	PASS_LOGIN_PAY_SAME_ERROR("9100013", "交易密码不能与登录密码一样"),
	USER_OLD_PASS_ERROR("910014", "原登录密码错误"),
	USER_OLD_PAY_PASS_ERROR("910015", "原交易密码错误"),
	PASS_LOGIN_PASS_SAME_ERROR("9100016", "新密码不能与旧密码一样"),
	USER_MOBILE_NOT_MATCH("910017", "手机不匹配，请不要篡改后台数据"),
	EMAIL_IS_EXIT("910018", "邮箱已存在"),
	USER_EMAIL_NOT_MATCH("910019", "邮箱不匹配，请不要篡改后台数据"),
	USER_STATUS_WRONG("910020", "用户状态不合法！请不要篡改后台数据"),
	ROLE_IS_ENPLOY("910021", "该用户角色被占用，不能被删除"),
	BANK_CARDBIN_NO_FIND("910022","无法确认此卡信息"),
	BANK_CARDID_NO_EQUAL("910023","银行卡号与银行不一致"),
	ROLENAME_IS_EXIT("910024", "角色名已存在"),
	INVITATIONCODE_NO_EXIT("910025", "该邀请码不存在"),
	USER_PAY_PASS_IS_NULL("9100026", "请先设置支付密码"),
	USER_NOT_BUY_PRODUCT("9100027", "尚未购买理财产品"),
	USER_CHANNEL_ERROR("9100028", "活动渠道处理失败"),
	USER_AGENT_ERROR("9100029", "活动渠道不存在"),
	USER_LOGIN_FAIL_OVER_MUCH("910030", "登录密码输入错误次数过多，请找回密码"),
	USER_PAY_FAIL_OVER_MUCH("910031", "交易密码输入错误次数过多，请找回密码"),
	BANK_CARD_IS_RETURN_CARD("910032", "不能解绑回款卡"),
	BANKCARD_IS_BIND("910033", "当前用户已绑卡或该卡已被绑定"),
	USER_JSH_NO_EXIT("910034", "用户的结算账户不存在"),
	SUB_ACCOUNT_NO_EXIT("910035", "用户子账户不存在"),
	ACCOUNT_NO_EXIT("910036", "用户主账户不存在"),
	PAY_LOGIN_PAY_SAME_ERROR("910037", "新交易密码不能与原交易密码一致 "),
	QIANBAO_AGENT_ERROR("9100038", "钱报渠道不存在"),
	REAL_NAME_AUTH_ERROR("961039", "实名认证异常："),
	WITHDRAW_CHECK_NO_EXIT("9100040","提现审核记录不存在"),
	IDCARD_IS_ERROR("9100041", "身份信息匹配错误，请使用本人开户的银行卡操作"),
	USER_BINDCARD_IS_ERROR("9100042", "用户绑定的银行卡信息有误"),
	CHECK_NEW_USER_ERROR("9100043", "该用户不是新用户，不能购买此产品"),
	CHECK_OVER_PRODUCT_LIMIT_ERROR("9100044", "购买金额超出剩余额度"),
	CHECK_OVER_USER_PRODUCT_LIMIT_ERROR("9100045", "购买金额超出可买额度"),
	CHECK_NO_USER_PRODUCT_LIMIT_ERROR("9100046", "新用户没有可买额度不能购买"),
	CHECK_NO_BUY_AMOUNT_ERROR("9100047", "购买金额不能为空"),
	CHECK_NO_PRODUCT_ERROR("9100048", "产品编号不能为空"),
	CHECK_USER_LOGIN_OUT_ERROR("9100049", "该用户没有登录"),
	CHECK_PRODUCT_OFF_ERROR("9100050", "该产品已下架"),
	USER_NOT_ADULT("910051", "亲，您未成年，我们等待您的成长，谢谢关注！"),
	USER_JLJ_NOT_ENOUGH("910052", "输入金额超过账户余额"),
	USER_IS_OLD_MAN("910053", "支付年龄仅限制在18-70岁之间"),
	USER_NOT_BIND_CARD("910054", "用户未绑卡"),
	WITHDRAW_MORE_THAN_3_TIMES_AMOUNT_LESS("910055", "当月提现超过3次，收取手续费后，提现余额不足"),
	SUPER_FINANCE_USER_NOT_NEED_BUY("910056", "您无需购买"),
	USER_ID_CARD_ERROR("910057", "请输入正确的身份证号码"),
	USER_BANK_CARD_BINDED("910058", "已绑卡成功，无需重新发起"),
	AGENT_ERROR("9100059", "渠道不存在"),
	RETURN_PATH_DATE_ERROR("9100060", "用户修改回款路径失败"),
	MATCH_DETAIL_NOT_EXIST_ERROR("9100061", "抱歉，该债权明细不存在"),
	SINGLE_WITHDRAW_UPPER_LIMIT("910062", "超过单笔提现上限50万"),
	DAY_WITHDRAW_UPPER_LIMIT("910063", "超过单日提现上限200万"),
	PRODUCT_EXIT_PARAM_ERROR("910064", "产品退出参数有误"),
	PRODUCT_EXIT_CECK_ERROR("910065", "产品退出校验失败"),
	PRODUCT_EXIT_APPLY_ERROR("910066", "产品退出申请失败"),
	PRODUCT_EXIT_STATUS_ERROR("910067", "产品状态校验失败"),
	USER_INVEST_INFO_ERROR("910068", "用户投资信息未找到"),
	PRODUCT_INFO_NOT_FOUND_ERROR("910069", "产品信息未找到"),
	PRODUCT_BUY_NEWER_ERROR("910070", "抱歉，计划只有新手可参与"),
	LOAN_INFO_NOT_FOUND("910071", "查询不到对应的借款信息"),
	USER_INFO_NOT_FOUND("910072", "查询不到对应的用户信息"),
	USER_INFO_ERROR("910073", "用户信息不对应"),
	CHECK_OLD_USER_ERROR("910069", "该产品仅限老用户购买"),
	PRODUCT_PAYMENT_INFO_ERROR("91072","用户待收回款未找到"),
	PRODUCT_PAYMENTPAST_INFO_ERRO("91073","用户往期回款未找到"),
	SEAL_FILE_NOT_FOUND("910074", "没有找到对应的签章信息"),
	DAFY_LATE_REPAY_AGREEMENT_NOT_EXIST("910075", "云贷增计划代偿协议地址不存在"),
	HF_NOT_BIND("910076", "未进行恒丰开户"),
	HF_BINDED_ACTIVATE_REPEAT("910077", "用户存管账户已经激活"),
	HF_QUIT_USER_RETURN_MONEY_EXIST("910078", "用户退出校验失败，已存在回款计划"),
	NO_HF_EXT("910079", "用户未开通存管，无需解绑"),
	FOUR_ELEMENTS_AUTH_ERROR("910080", "四要素绑卡异常："),
	BONUS_BALANCE_IS_NULL("910081", "亲，请输入提现金额"),
	CARD_BIN_ERROR("910082", "卡号与所选银行不一致"),
	BILL_SYNC_INFO_ERROR("910083", "账单同步信息校验失败"),
	USER_EXIST("910084", "用户已注册"),
	CARD_INFO_ERROR("910085", "银行卡信息有误："),
	BILL_SYNC_INFO_EMPTY_ERROR("910086", "账单同步失败,账单为空"),
	BILL_SYNC_EXIST_SCHEDULE_DATE_ERROR("910089", "账单表中已存在数据"),
	USER_LOGIN_FAIL_LAST("910090", "密码多次错误，您还可尝试1次"),
	USER_LOGIN_FAIL_LOCKEDE("910091", "密码错误次数过多"),
	USER_LOGIN_FAIL_LOCKEDE_AFTER("910092", "密码错误次数过多"),
	USER_NEED_FORCED_LOGOUT("910093", "需要强制退出"),	NO_ASSESSMENT("910090", "您尚未进行风险承受能力测评"),
	ASSESSMENT_EXPIRE("910094", "您的风险承受能力测评已过期，请重新测评"),
	DEPLOAN7_LATE_REPAY_AGREEMENT_NOT_EXIST("910095", "7贷增计划代偿协议地址不存在"),
	OLD_COMPENSATE_USER_INFO_NOT_EXIST("910096", "老代偿人用户信息查询不到"),
	NEW_COMPENSATE_USER_INFO_NOT_EXIST("910097", "新代偿人用户信息查询不到"),
	COMPENSATE_USER_INFO_DATA_DEFINED_ERROR("910098", "代偿人用户配置信息定义错误"),
	OLD_COMPENSATE_USER_ID_DATA_EMPTY_ERROR("910099", "老代偿人用户ID配置为空"),
	NEW_COMPENSATE_USER_ID_DATA_EMPTY_ERROR("910100", "新代偿人用户ID配置为空"),
	USER_STATUS_FROST_ERROR("910101", "您账户已冻结，请联系客服详询"),
	USER_STATUS_FROST_MANAGE_ERROR("910102", "此账户已冻结，请联系客服详询"),
	
	//账务相关类型异常，错误码以92开头6位数字，例：
	ACCOUNT_BALANCE_NOT_ENOUGH("920000", "账户余额不足"),
	ACCOUNT_BALANCE_ERROR("920001", "查询账户余额异常"),
	RECORD_ACCOUNTING_DATA_ERROR("920002", "记账数据校验错误："),
	ACCOUNT_GENERATE_ERROR_TYPE("920003", "账户号生成错误：传入账户类型有误"),
	PAYMENT_ORDER_NOT_EXIST("920004","订单不存在"),
	PAYMENT_ORDER_AMOUNT_NOT_SAME("920005","订单金额和实际支付金额不一致"),
	PAYMENT_ORDER_DUPLICATE("920006","订单已经成功，不需重复发起"),
	WITHDRAW_AMOUNT_SURPASS("920007","提现金额不足或超限"),
	WITHDRAW_FAIL("920008","提现失败："),
	PAYMENT_SYSWITHDRAW_AMOUNT_NOT_SAME("920009","系统提现金额和实际提现金额不一致"),
	PAYMENT_SYSWITHDRAW_NOT_EXIST("920010","系统提现订单不存在"),
	PAYMENT_CUSTOMERWITHDRAW_NOT_EXIST("920011","用户提现订单不存在"),
	PAYMENT_CUSTOMERWITHDRAW_BANKCADE_ERROR("920011","用户提现银行与实际提现银行不符"),
	WITHDRAW_AMOUNT_LIMIT_100("920012","可提现金额不足100元不能提现"),
	PAYMENT_ORDER_JNL_NOT_EXIST("920013","订单流水明细不存在"),
	SYS_BALANCE_NOT_ENOUGH("920014", "划出账户余额不足"),
	SYS_BUY_RETURN_ACCTTRANS_FAIL("920015","转账订单结果处理中或失败"),
	SYS_BUY_RETURN_ACCTTRANS_QUERY_FAIL("920015","转账订单查询失败"),
	SYS_BUY_ACCTTRANS_IS_SENDED("920016","该产品已进行系统理财购买，请勿重复发起"),
	SYS_BUY_ACCTTRANS_NOT_FOUND("920017","找不到该产品，或该产品对账标志未满足"),
	SYS_BUY_ACCTTRANS_ERROR("920018","差错产品转账购买重发异常，可查看告警记录确认："),
	WITHDRAW_CONFIRM_TRANSFER_ERROR("920019","确认转账失败"),
	HELP_CHANNEL_ERROR("920020","辅助渠道类型错误"),
	RETURN_NOTICE_TERM_NOT_LAST("920021", "异常回款通知，原因：期数不是最后一期"),
	RETURN_NOTICE_TERM_IS_LAST("920022", "异常结息回款通知，原因：期数不应是最后一期"),
	SYS_WITHDRAW_AMOUNT_REGISTRY_OVER("920023","提现登记金额超限"),
	RETURN_AMOUNT_ERROR("920024", "回款金额异常，请检查！"),
	RETURN_CHANNEL_ERROR("920025", "系统回款渠道错误"),
	BATCH_BIND_CARD_PARAM_ERROR("920026", "批量绑定"),
	ACCOUNT_NOT_FOUND("920027", "账户未找到"),
	ORDER_BIZTYPE_UNKNOWN("920028","订单业务类型未知"),
	LOAN_DATA_NOT_FOUND("920029","借款数据未找到"),
	REPAY_DATA_NOT_FOUND("920030","还款数据未找到"),
	WITHDRAW_NOT_ALLOWED("920031","抱歉，您的提现不被允许"),
	RETURN_LAST_ERROR("920032", "系统回款通知有误：最后期回款必须完成利息回款"),
	COMPENSATE_NOTICE_SAME("920033","代偿通知重复"),
	ORDER_STATUS_UPDATE_ERROR("920034","订单更新状态异常："),
	DEP_TRANS_ORDER_ERROR("920035","平台账户间转账失败"),
	DEP_PLATFORM_TRANS_ORDER_ERROR("920036","平台转个人转账失败"),
	ORDERS_TIMEOUT_FAILURE("920037","该笔订单已失效"),
	DEP_TARGET_PUBLISH_ERROR("920038","标的发布失败"),
	DEP_TARGET_ESTABLISH_ERROR("920039","标的成立失败"),
	DEP_PLATFORM_TRANS_ACCOUNT_ERROR("920040","转入账户非法"),
	DEP_PLATFORM_RECHARGE_FAIL("920041","平台充值失败"),
	PAYMENT_SYSRECHARGE_AMOUNT_NOT_SAME("920042","系统充值金额和实际充值金额不一致"),
	DEP_TARGET_ABANDON_ERROR("920043","标的废除失败"),
	FORBID_BUY("920044", "不允许购买该产品"),
	SYS_BALANCE_NOT_ENOUGH_FOR_MANAGE("920045", "划出账户余额不足"),
	DEP_TARGET_BATCH_BUY_TIME_OUT("920046","标的批量投标超时"),
	COMPENSATE_NOTICE_FAIL("920047","代偿失败"),
	RETURN_LAST_PRINCIPAL_ERROR("920048", "系统回款通知有误：最后期回款本金未全回"),
	RETURN_PRODUCT_CODE_ERROR("920049", "系统回款通知有误：产品编码不一致"),
	RETURN_PRODUCT_TREM_ERROR("920050", "系统回款通知有误：产品期数不一致"),
	NO_THIS_BORROWER_INFO_ERROR("920051", "抱歉，不存在该借款人详情"),
	ACCOUNT_QUOTA_NOT_ENOUGH("920052", "额度不足"),

	//产品相关类型异常，错误码以93开头6位数字，例：(第二位3表示产品，第三位1表示定期理财，2表示基金)
	REGULAR_AMOUNT_NOT_MATCH("931000", "超过单笔购买上限,上限为：￥"),
	REGULAR_BUY_NUM_REACHLIMIT("931001","当天已经购买三次"),
	TRANSFER_AMOUNT_ERROR("931002","转让金额不符合要求！"),
	TRANSFER_NOT_MYSELF("931003","无法购买本人的转让产品"),
	PRODUCT_MAX_AMOUNT_IS_OUT("931004", "产品金额规模超限，当前最多只能投资：￥"),
	PRODUCT_MAX_AMOUNT_PERSON_IS_OUT("931005", "产品个人金额规模超限，当前最多只能投资：￥"),
	FUND_AND_DATE_IS_EXIT("932000","该日该产品基金净值已设置！"),
	USER_NO_AUTH_FOR_PRODUCT("931006", "当前产品已完成，请关注其他产品"),
	MONEY_IS_NOT_SPECIFICATION("931007", "购买金额不是100的倍数！"),
	BANK_CHANNEL_LOSE("931008", "抱歉，受银行渠道影响，您的安全卡暂不可用，您可以通过网银进行购买（充值）！"),
	PASS_ONETOP_LIMIT("931009", "输入金额超过银行的单笔限额！"),
	PASS_DAYTOP_LIMIT("931010", "输入金额超过银行的单日限额！"),
	PASS_MONTHTOP_LIMIT("931011", "输入金额超过银行的单月限额！"),
	PASS_SYSTEM_LIMIT("931012", "输入金额超过系统当日累计金额！"),
	USER_ORDER_STATUS_PAYING("931014", "您还有处理中的订单，暂不能购买！"),
	USER_ORDER_PRE_FAIL("931015", "预下单失败！"),
	USER_ORDER_REGULAR_FAIL("931016", "用户提交订单失败！"),
	CARDNO_ORDER_STATUS_PAYING("931017", "当前银行卡还有处理中的订单，暂不能购买！"),
	BUY_AMOUNT_IS_NOT_PASS("931018", "购买金额未达到此产品的起投金额！"),
	RECHANGE_AMOUNT_IS_NOT_PASS("931019", "充值金额未超过最低充值金额！"),
	BANK_IS_NOT_EXIST("931020", "银行不存在！"),
	PASS_REAPAL_DAYTOP_LIMIT("931021", "输入金额超过渠道单日限额！"),
	RED_PACKET_USERD("931022", "红包已被使用！"),
	RED_PACKET_NO_ROLE_USE("931023", "当前红包不满足使用条件！"),
	PRODUCT_MAX_AMOUNT_OVER("931024", "此产品剩余金额不足"),
	PRODUCT_STATUS_OPENING_ERROR("931025", "产品当前未处于开放状态，不能进行购买！"),
	PRODUCT_IS_FINISHED("931025", "产品购买已结束，不能进行购买！"),
	PRODUCT_IS_NOT_OPENED("931025", "产品购买还未开始，不能进行购买！"),
	PRODUCT_IS_CAN_NOT_RELEASE("931026", "当前理财计划不满足发布条件！"),
	PRODUCT_IS_CAN_NOT_FINISH("931027", "当前理财计划不满足结束条件"),
	PRODUCT_IS_CAN_NOT_RECOMMEND("931028", "当前理财计划不满足推荐条件"),
	PRODUCT_IS_CAN_NOT_CANCLE_RECOMMEND("931029", "当前理财计划不满足取消推荐条件"),
	USER_ALREADY_BUY_PRODUCT("931030", "抱歉，此产品限新用户首次购买，请购买其他产品！"),
	SINGLE_PRODUCT_MAX_AMOUNT_OVER("931031", "购买金额超过单次购买最高额度，不能进行购买！"),
	BANK_CHANNEL_ERROR("931032", "银行渠道优先级异常！"),
	TRANS_TRPE_ERROR("931033", "交易类型异常！"),
	BIND_BANK_CARD_ERROR("931034", "该用户未绑卡，不能进行交易！"),
	PRODUCT_MAX_INVEST_AMOUNT_IS_OUT("931035", "产品金额规模超限，单人此项目最多购买：￥"),
	TICKET_INTEREST_USED("931036", "加息券已被使用！"),
	TICKET_INTEREST_NO_ROLE_USE("931037", "当前加息券不满足使用条件！"),

	//信息发送相关类型异常，错误码以94开头6位数字，例：(4表示信息发送，1表示手机，2表示邮箱)
	MOBILE_SEND_MAX_ERROR("941000", "当日该手机发送验证码过多！"),
	MOBILE_NOT_SEND_ERROR("941001", "未用该手机发送验证码！"),
	MOBILE_SEND_DATE_ERROR("941002", "手机验证码已经过期，请重新发送！"),
	MOBILE_CODE_WRONG_ERROR("941003", "手机验证码不正确，请重新验证！"),
	MOBILE_CODE_USER_TIMES_MAX_ERROR("941004", "该验证码被验证次数过多，请重新发送"),
	MOBILE_CODE_TOO_FAST_ERROR("941005", "验证频率过快，请注意！"),
	MOBILE_OLD_CODE_WRONG_ERROR("941006", "原手机验证码不正确，请重新验证！"),
	MOBILE_NEW_CODE_WRONG_ERROR("941007", "新手机验证码不正确，请重新验证！"),
	MOBILE_SEND_MESSAGE_ERROR("941008", "手机发送信息有异常！"),
	EMAIL_NOT_SEND_ERROR("942000", "未用该邮箱发送验证码！"),
	EMAIL_SEND_DATE_ERROR("942001", "邮箱验证码已经过期，请重新发送！"),
	EMAIL_CODE_WRONG_ERROR("942002", "邮箱验证码不正确，请验证！"),
	EMAIL_CODE_USER_TIMES_MAX_ERROR("942003", "该验证码被验证次数过多，请重新发送"),
	EMAIL_CODE_TOO_FAST_ERROR("942004", "验证频率过快，请注意！"),
	EMAIL_OLD_CODE_WRONG_ERROR("942005", "原邮箱验证码不正确，请重新验证！"),
	EMAIL_NEW_CODE_WRONG_ERROR("942006", "新邮箱验证码不正确，请重新验证！"),
	USER_IP_EMPTY("942007", "用户IP地址为空"),
	USER_MAC_EMPTY("942008", "用户MAC地址为空"),
	MOBILE_CODE_MUST_SIX("942009", "请输入6位验证码"),
	MOBILE_CODE_MUST_NUMBER("942010", "验证码必须是数字"),

	//数据库操作失败类型异常，错误码以95开头6位数字，例：
	DB_CUD_NO_EFFECT("951000", "数据库增删改操作没有起效"),
	
	//通讯相关异常，错误码以96开头6位数字，第三位1表示达飞通讯，2表示宝付 例：
	DAFY_REGIST_RETURN_FAIL("961000", "银行卡绑定失败："),
	DAFY_BINDCARD_RETURN_FAIL("961001", "银行卡绑定失败："),
	DAFY_REGIST_USER_NOT_EXIST("961002", "达飞注册用户结果通知失败：该达飞客户号下没有对应用户"),
	DAFY_BANKCARD_IS_BINDED("961003", "银行卡已绑定"),
	DAFY_BUY_PRODUCT_ERROR("961004", "购买异常："),
	DAFY_BANKCARD_IS_BINDING("961005", "银行卡绑定中"),
	DAFY_REALNAME_AUTH_ERROR("961006", "身份信息认证失败"),
	DAFY_BANKCARD_IS_SUCCESS("961007", "银行卡绑定成功"),
	DAFY_RESULT_PROCESSING_ERROR("961008", "处理达飞通知失败："),
	DAFY_RESULT_MOBILE_OR_IDNO_EXIST("961009", "手机号已经存在或者身份证号存在"),
	DAFY_FTP_DOWNLOAD_FAIL("961010", "ftp下载文件失败"),
	DAFY_READ_EXCEL_FAIL("961011", "读取Excel文件失败"),
	DAFY_RECEIVE_MONEY_NOTICE_DATA_ERROR("961012", "通知数据有误："),
	DAFY_RESULT_NOTICE_REPEAT("961013", "达飞通知重复"),
	DAFY_WX_ACCOUNT_DETAIL_FAIL("961014", "查询网新账户明细失败："),
	DAFY_FTP_DOWNLOAD_NULL("961015", "ftp下载文件为空文件，不进行处理"),
	PAY19_CONNECTION_ERROR("961016", "网络堵塞，请稍候再试！"),
	DAFY_BUY_EBANK_ERROR("961017", "请求网银支付失败"),
	BAOFOO_BUY_EBANK_ERROR("961018", "网银通知执行失败"),

	BAOFOO_PRE_BIND_CARD_FAIL("962000","预绑卡失败"),
	BAOFOO_BIND_CARD_FAIL("962001","绑卡失败"),
	BAOFOO_DF_FAIL("962002","代付失败"),
	BAOFOO_PRE_QUCIK_PAY_FAIL("962003","快捷预下单失败"),
	BAOFOO_UNBIND_CARD_FAIL("962004","解绑卡失败"),
	TOP_UP_LESS_THAN_FEE("962005","低于手续费"),

	// 活动相关异常
	ACTIVITY_LEFT_TIMES_ZERO("971000", "您没有抽奖机会"),
	ACTIVITY_DATE_NOT_MATCH("971001", "不在活动期间，无法抽奖"),
	RED_PACKET_NOT_ELIGIBLE("971002", "无资格领取红包"),
	RED_PACKET_OVER("971003", "红包已领用完"),
	RED_PACKET_NEW_USER_NOT_ELIGIBLE("971004", "亲，您已经参加过活动了哦，请注册"),
	ACTIVITY_NOT_START("971005", "活动还未开始"),
	ACTIVITY_END("971006", "活动已经结束"),
	LESS_THAN_MINIMUM_INVESTMENT_AMOUNT("971007", "'最小投资额是5000"),
	ECUP_SAME_PARTER_SUPPORTOR_MORE("971008", "已为好友完成助力"),
	ECUP_MORE_THAN_3_SUPPORTOR("971009", "抱歉，最多只能为3位好友助力"),
	NEWS_NOT_EXIST("971010", "不存在此新闻公告"),
	//2016客户年终抽奖活动相关
	ACTIVITY_END_OF_2016YEAR_COUNT("971011", "参与抽奖的人数少于设定的中奖人数"),
	ACTIVITY_END_OF_2016YEAR_COUNTMAX("971012", "该奖已抽完"),
	//PK活动相关
	PK_ACTIVITY_NOT_EXIST("971013", "暂时查询不到相关活动标的"),
	//2017元宵活动
	ACTIVITY_ONE_LIMIT("971014", "一天一次，明天再来！"),
	ACTIVITY_HAVE_BENISON("971015", "亲~今天已经送过祝福了喔~"),
	ACTIVITY_BENISON_HAVE_CHECKED("971016", "该祝福语已经审核过了"),
	SHARE_USER_IS_NOT_EXIST("971017", "分享用户不存在"),
	ACTIVITY_USER_NOT_INVEST("971018", "用户未投资"),
	SYS_JSH_BALANCE_NOT_ENOUGH("971019", "系统维护中，请稍后重试"),

	APP_ACTIVITY_NOT_EXIST("971020", "不存在此活动公告"),
	JOIN_TEAM_COUNT_LIMIT("971020", "抱团次数限制"),
	JOIN_ANSWER_ACTIVITY("971021", "您已参加答题活动"),
	JOIN_ANSWER_POLICY_ERROR("971022", "红包君正在准备红包，稍后会发送至您的账户中，请注意查收！"),
	GIFTS_FINISHED("971023", "礼品已被兑完"),
	GIFTS_HAVE_BEEN_FINISHED("971024", "已兑换过当前礼品"),
	GIFTS_ONLY_TWO_TIMES("971025", "仅可兑换两份礼品"),
	ADDRESS_HAVE_WRITE("971026", "地址已经填写过"),
	HAVE_LUCKY_DRAW("971027", "已经抽过奖"),
	NON_CONFORMITY("971028", "不符合兑换条件"),
	ACTIVITY_NO_LUCKY_TIMES("971029", "今日可抽奖0次"),

	//其他需要的类型错误码，请自行累加2位首数字
	//...
	
	//系统方面异常，错误码以99开头6位数字，例：
	SYSTEM_ERROR("990000", "系统异常"),
	
	//红包相关异常，错误码以100开头7位数字，例：
	RED_POCKET_CHECK_NOT_EXIST("1000001", "红包不存在或非可用状态"),
	AUTO_RED_POCKET_NOT_EXIST("1000002", "自动红包不存在"),
	RED_POCKET_CHECK_PARAM_ERROR("1000003", "参数有误"),
	RED_POCKET_CHECK_MANAGER_ERROR("1000004", "管理员身份非法"),
	RED_POCKET_CHECK_TYPE_ERROR("1000005", "红包类型有误"),
	TICKET_CHECK_NOT_EXIST("1000006", "加息券不存在或状态有误"),
	TICKET_CHECK_GRANT_COUNT_ERROR("1000007", "加息券有效发放用户数量与实际发放用户数量不一致"),
	TICKET_CHECK_GRANT_STATUS_ERROR("1000008", "加息券发放状态有误"),
	TICKET_ATTR_USETIMEEND_ERROR("1000009", "加息券使用结束使用早于当前时间"),
	
	NEW_OLD_CUSTOMER_ID_IS_SAME("1000006", "新老客户经理编号相同"),






	//赞分期（蜂鸟），错误码返回
	ZAN_ADDLOANCIF_USER_NOT_EXIST("980000","用户不存在"),
	ZAN_ADDLOANCIF_USER_EXIST("980001","用户已存在"),
	ZAN_ADDLOANCIF_NOT_ENOUGH("980002","参数不全"),
	ZAN_ADDLOANCIF_ALREADY_BINDED("980003","该信息已绑定"),
	ZAN_LOAN_USER_CARD_UNBIND("980004","该卡没有绑定"),
	ZAN_LOAN_USER_NOT_FOUND_IN_WHITE("980005","用户不在白名单中"),
	ZAN_REPAY_PAYMENT_ORDER_DUPLICATE("980006","该期借款不存在或已还清"),
	ZAN_REPAY_TERM_ERROR("980007", "还款期次异常"),
	ZAN_RETURN_NOT_MATCH("980008", "回款结算与计划不匹配"),
	ZAN_ORDER_NO_NOT_EXIST("980009","还款订单号不存在"),
	ZAN_DAILYAMOUNT_NOT_READY("980010","当日数据未准备好"),
	ZAN_MARKET_ORDER_NO_NOT_EXIST("980011","营销订单不存在"),
	ZAN_LOAN_USER_FOUND_IN_BLACK("980012","用户在黑名单中"),
	ZAN_REPAY_FAIL("980013", "还款失败："),
	ZAN_ORDER_NO_EXIST("980014", "该笔订单已经存在"),
	ZAN_REPAY_LOAN_ID_NOT_EXIST("980015","该期借款不存在"),
	ZAN_REPAY_BAD_USER_ERROR("980016","用户信息不对应"),
	ZAN_REPAY_BAD_PLAN_EXIST("980017","该笔坏账记录已经存在"),
	ZAN_REPAY_CAl_LATE_FAIL("980018","滞纳金计算出错"),
	LOAN_MAX_OUT("980019","借款金额超出最大值"),
	
	//还款进行中错误码
    DEP_REPAY_REPAYING("980019","该笔借款有还款正在进行中"),
	YUN_SELF_LOAN_USER_FOUND_IN_BLACK("980020","账户信息验证失败"),
	
	ZAN_OFFLINE_REPAY_STATUS_ERROR("980021","线下还款只能还代偿后的账单"),
	ZAN_OFFLINE_REPAY_PLAN_ID_EMPTY_ERROR("980022","线下还款找不到对应的还款账单"),
	ZAN_OFFLINE_REPAY_SCEDULE_CHECK_ERROR("980023","线下还款还款账单校验失败"),
	
	

	APPLY_DATA_TOO_MORE("990000", "已存在待审核数据"),
	APPLY_DATA_IS_SUCCESS("990001", "该记录已经更新成功，无需重复审核"),
	INTEREST_TICKET_OVER("990002", "加息券已领用完"),
	;
	
	private PTMessageEnum(String code, String message){
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
	public static PTMessageEnum getEnumByCode(String code) {
		if (null == code) {
			return null;
		}
		for (PTMessageEnum type : values()) {
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
		for (PTMessageEnum type : values()) {
			enumDataMap.put(type.getCode(), type.getMessage());
		}
		return enumDataMap;
	}
	
	
}
