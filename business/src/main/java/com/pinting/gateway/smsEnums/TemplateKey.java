package com.pinting.gateway.smsEnums;

public enum TemplateKey {
	/** 注册验证码消息模板 */
    SMS_TOKEN("sms.token", "您的验证码为%s，有效期120秒，客服热线400-806-1230", "验证码消息模板",1),
    /** 普通异常或失败 */
    COMMON_EMERGENCY("common.emergency","{%s}失败或异常，详情请检查告警表","普通失败或异常",1),
    /** 定时任务异常或失败 */
    TASK_EMERGENCY("task.emergency","定时任务{%s}失败或异常，详情请检查告警表","定时任务异常或失败",1),
    /** 客户投资成功通知 */
    BUY_PRODUCT_SUC("buy.product.suc","您有一项计划已加入成功，金额：￥%s元, 期限：%s天, 预期收益率：%s，次日起息。","客户投资成功通知",1),
    /** 客户投资成功通知--赞分期 */
    BUY_PRODUCT_SUC4ZAN("buy.product.suc4zan","您有一项计划已加入成功，金额：￥%s元, 期限：%s个月内, 预期收益率：%s。","客户投资成功通知",1),
    /** 客户投资失败通知 */
    BUY_PRODUCT_Fall("buy.product.fall","您有一项计划未成功，失败原因：%s，请核实，如有疑问请拨打400-806-1230。","客户投资失败通知",1),
    /**　客户充值成功通知 */
    TOP_UP_SUC("top.up.suc","您有一笔充值已成功，充值金额：￥%s元。如有疑问请拨打400-806-1230。","客户充值成功通知",1),
    /**　客户充值失败通知 */
    TOP_UP_FALL("top.up.fall","您有一笔充值未成功，失败原因：%s，请核实，如有疑问请拨打400-806-1230。","客户充值失败通知",1),
    /** 客户回款到卡成功 */
    RETURN_SUCCESS2CARD("return.success2card","您有一项计划已回款至尾号%s的银行卡，总额：￥%s元，其中本金：￥%s元，利息：￥%s元。","客户回款到卡成功",1),
    /** 客户回款到账户余额成功*/
    RETURN_SUCCESS2BALANCE("return.success2balance","您有一项计划已回款至您的币港湾账户余额，总额：￥%s元，其中本金：￥%s元，利息：￥%s元。","客户回款到账户余额成功",1),
    /** 赞分期产品，客户回款到账户余额成功 */
    RETURN_SUCCESS2BALANCE4ZAN("return.success2balance4zan","您有一项计划已回款至您的币港湾账户余额，回款时间：%s，总额：￥%s元，其中本金：￥%s元，利息：￥%s元。","客户回款到账户余额成功_赞分期",1),
    /** 提现成功 */
    WITHDRAW_SUC("withdraw.suc","您有一笔金额为：￥%s元的提现已受理，手续费：￥%s元，实际到账：￥%s元。实际到账时间以入款行为准，如有疑问请拨打400-806-1230。","提现成功",1),
    /** 提现失败  */
    WITHDRAW_FALL("withdraw.fall","您有一笔金额为：￥%s元的提现交易失败，失败原因：%s，如有疑问请拨打400-806-1230。","提现失败",1),
    /** 银行卡绑定失败 */
    BIND_BANK_CARD_FALL("bind.bank.card.fall","尊敬的币港湾用户，抱歉，您的银行卡绑定失败，原因{%s}。","银行卡绑定失败",1),
    /** 银行卡绑定成功 */
    BIND_BANK_CARD_SUC("bind.bank.card.suc","尊敬的币港湾用户，您已成功绑定银行卡，可加入币港湾产品项目。","银行卡绑定成功",1),
    /** 红包发送短信 **/
    RED_PACKET_SEND("red.packet.send","尊敬的用户，恭喜您获得合计￥%s元%s，请登录币港湾：官网/微信公众号/APP，我的资产-优惠券中查看使用详情。","红包发送短信",1),
    /** 抵用金告警  */
    REDPACKET_GOLD_REMIND("redpacket.gold.remind","系统抵用金账户余额过低，请及时充值，以免影响业务。","抵用金告警",1),
    
    /** 希财注册短信*/
    XC_REGISTER_MSG("xc.register.msg","尊敬的用户，感谢您通过希财网注册成为币港湾用户，您在币港湾的用户名为：%s，密码为：%s，请保护好您的信息，您可以登录币港湾网站修改您的密码，若有疑问，请咨询400-806-1230。","希财注册短信通知",1),
    
    /** 产品即将开始短信通知*/
    PRODUCT_INFORM_SEND("product.inform.send","信息提示：您预约的产品%s大约%s分钟后开始申购，请知晓。","产品即将开始短信通知",1),
    
    /** 购买失败转充值*/
    BUY_PRODUCT_Fall2TopUp("buy.product.fall2topup","您有一项计划不成功，原因：理财产品额度不足。系统已自动帮您转成充值，充值金额：￥%s。您可使用余额继续其他产品或进行提现操作，如有疑问请拨打400-806-1230。","购买失败转充值",1),
    
    /** 管理台初始密码*/
    MANAGER_INIT_PASSWORD("manager.init.password","尊敬的币港湾客户经理，您的初始登录密码为：%s，请您妥善保管好密码，并在登录后立即进行修改。","管理台初始密码",1),
    
    /** 管理台重置密码*/
    MANAGER_RESET_PASSWORD("manager.reset.password","你的管理台登录密码已经重置为%s，请登录后马上更改，谢谢~","管理台重置密码",1),
    
    /** 投资成功，奖品领取通知*/
    BUY_PRODUCT_SUCCESS2GIFT("buy.product.success2gift","您的一项计划已加入成功，恭喜您获得了%s！请编辑联系地址、电话及姓名到“币港湾”微信公众号","投资成功，奖品领取通知",1),
    
    /** 2016客户年终抽奖中奖通知 */
    ENDOF2016_ACTIVITY_WINNING("endOf2016.ativity.winning","尊敬的币港湾用户您好，您的手机号码己被第二届币港湾粉丝见面会，抽奖系统后台抽取为场内：%s","年终抽奖中奖通知",1),
    
    /** 发送随机密码 */
    SITE_INIT_PASSWORD("site.init.password","感谢您参与本次活动并成为币港湾会员，您的登录密码%s，请及时登录进行修改。客服热线400-806-1230。","发送随机密码",1),
    
    /** 中奖通知*/
    ACTIVITY_WINNING_NOTICE("ativity.winning.notice","恭喜您获得%s，如有任何疑问题，请拨打客服热线400-806-1230，我们将竭诚为您服务。","中奖通知",1),

    /** 女神节-到店兑换短信 */
    ACTIVITY_FOR_GIRL_TO_THE_STORE("activity.for.girl.to.the.store", "恭喜您获得%s，凭此短信到178理财俱乐部门店领取。感谢您对币港湾的支持，如有疑问可咨询400-806-1230。", "到店兑换通知",1),
    /** 女神节-快递到家短信 */
    ACTIVITY_FOR_GIRL_EXPRESS("activity.for.girl.express", "恭喜您获得%s，我们将在3月6日给您寄出，请您留意查收快递。感谢您对币港湾的支持，如有疑问可咨询400-806-1230。", "快递到家通知",1),

    /** 云贷自主放款，还款预下单验证码 */
    INCR_REPAY_PRE_SMS_TOKEN("incr.repay.pre.sms.token","您的验证码为%s，有效期120秒，请勿泄露。如非本人操作请致电客服。","云贷自主放款，还款预下单验证码",1),
    /** 云贷自主放款，还款预下单验证码 */
    INCR_REPAY_PRE_REPEAT_SMS_TOKEN("incr.repay.pre.repeat.sms.token","您的验证码为%s，有效期10分钟，请勿泄露。如非本人操作请致电客服。","云贷自主放款，还款预下单验证码",1),
    /** 云贷自主放款，绑卡预下单验证码 */
    INCR_BIND_CARD_PRE_SMS_TOKEN("incr.bind.card.pre.sms.token","您的验证码为%s，有效期120秒，您正在进行绑卡验证，如有疑问请拨打400-806-1230。","云贷自主放款，绑卡预下单验证码",1),

    /** 赞分期还款预下单验证码 */
    ZAN_REPAY_PRE_SMS_TOKEN("zan.repay.pre.sms.token","您的验证码为%s，有效期120秒，请勿泄露。如非本人操作请致电客服。","赞分期还款，还款预下单验证码",1),

    /** 赞分期绑卡预下单验证码 */
    ZAN_BIND_CARD_PRE_SMS_TOKEN("zan.bind.card.pre.sms.token","您的验证码为%s，有效期120秒，您正在进行绑卡验证，如有疑问请拨打400-696-5858。","赞分期放款，绑卡预下单验证码",1),

    /**奖励金提现成功 */
    BONUS_WITHDRAW_SUC("bonus.withdraw.suc","您有一笔金额为：￥%s元奖励金提现已完成。如有疑问请拨打400-806-1230。","提现成功",1),
    
    /** 奖励金提现失败  */
    BONUS_WITHDRAW_FALL("bonus.withdraw.fall","您有一笔金额为：￥%s元奖励金提现交易失败，失败原因：%s。如有疑问请拨打400-806-1230。","提现失败",1),

    /** 周周乐活动10点短信提醒*/
    WEEKHAY_ACTIVITY_REMIND_10CLOCK("weekhay.activity.remind.10clock","信息提示：您预约的周周乐产品大约5分钟后开始申购，请知晓。","周周乐活动10点短信提醒",1),
    /** 周周乐活动14点短信提醒*/
    WEEKHAY_ACTIVITY_REMIND_14CLOCK("weekhay.activity.remind.14clock","信息提示：您预约的周周乐产品大约5分钟后开始申购，请知晓。","周周乐活动14点短信提醒",1),
    /** 周周乐活动20点短信提醒*/
    WEEKHAY_ACTIVITY_REMIND_20CLOCK("weekhay.activity.remind.20clock","信息提示：您预约的周周乐产品大约5分钟后开始申购，请知晓。","周周乐活动20点短信提醒",1),

    /** 赞时贷 还款预下单验证码 */
    ZSD_REPAY_PRE_REPEAT_SMS_TOKEN("zsd.repay.pre.repeat.sms.token","您的验证码为%s，有效期10分钟，请勿泄露。如非本人操作请致电客服。","赞时贷还款预下单验证码",1),

    /** 钱报APP信任免登-注册发送短信*/
    QB_APP_REGIST("qb.app.regist","尊敬的钱报178用户，恭喜您已成功注册币港湾！您的初始密码为%s，请及时登录币港湾修改您的密码：%s。客服电话：400-806-1230","钱报APP信任免登-注册发送短信",1),

    /** 管理台云贷砍头息划转验证码 */
    MANAGER_YUN_HEADFEE_TRANSFER("manager.yun.headFee.transfer", "现申请划拨%s手续费资金，金额：%s元，收款人：%s，审核验证码：%s，如有疑问请务告知财务", "管理台云贷砍头息划转验证码",1),

    /** 领取加息券通知*/
    MESSAGE_TICKET_GRANT("message.ticket.grant","尊敬的用户，恭喜你获得%s%s，请登录币港湾：官网/微信公众号/APP，我的优惠券—加息券中查看。","领取加息券通知",1),

    /** 优惠券（红包、加息券）到期提醒通知*/
    MESSAGE_TICKET_REMINDER_NOTIFY("message.ticket.reminder.notify","尊敬的用户，您有%s个优惠券将于5天后过期（%s为最后可用期限），请尽快使用。","优惠券到期提醒通知",1),

    /** 解绑卡申请通过通知*/
    UN_BINDCARD_PASS("un.bindcard.pass","您的解绑卡申请已成功通过，可进入币港湾平台重新绑定银行卡，如有疑问请拨打400-806-1230","解绑卡申请通过通知",1),
    /** 解绑卡申请未通过通知*/
    UN_BINDCARD_REFUSE("un.bindcard.refuse","您的解绑卡申请未能通过，可尽快联系客服人员查询原因，如有疑问请拨打400-806-1230","解绑卡申请未通过通知",1),
    
    ;
	
	 /** code */
    private String code;

    /** template */
    private String template;

    /** description */
    private String description;
    
    /** messageType短信类型：1通知类短信，2营销短信 */
    private Integer messageType;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private TemplateKey(String code, String template, String description, Integer messageType) {
        this.code = code;
        this.template = template;
        this.description = description;
        this.messageType = messageType;
    }


    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static TemplateKey find(String code) {
        for (TemplateKey key : TemplateKey.values()) {
            if (key.getCode().equals(code)) {
                return key;
            }
        }
		return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getTemplate() {
        return template;
    }
    
    public Integer getMessageType() {
		return messageType;
	}

}
