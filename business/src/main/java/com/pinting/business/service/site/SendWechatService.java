package com.pinting.business.service.site;

public interface SendWechatService {

    /**
     * 回款成功通知
     *
     * @param userId
     * @param userNick
     * @param paymentAmount 回款总额
     * @param principal     本金
     * @param proceeds      收益
     * @return
     */
    String paymentMgs2Card(Integer userId, String userNick,
                                  String paymentAmount, String principal, String proceeds, String cardNo);

    /**
     * 回款成功到余额通知
     * @param userId
     * @param userNick
     * @param paymentAmount
     * @param principal
     * @param proceeds
     * @return
     */
    String paymentMgs2Balance(Integer userId, String userNick,
                                     String paymentAmount, String principal, String proceeds);
    
    /**
     * 赞分期产品回款成功到余额通知
     * @param userId
     * @param paymentAmount
     * @param principal
     * @param proceeds
     * @param endTime
     * @return
     */
    String paymentMgs2Balance4Zan(Integer userId,String paymentAmount, String principal, String proceeds, String endTime);

    /**
     * 购买成功或失败
     *
     * @param userId      用户id
     * @param userNick    用户昵称
     * @param productName 产品名称
     * @param income      年化收益
     * @param corpus      投资本金
     * @param days        投资天数
     * @param sucOrFall   成功或失败 suc -成功，fall-失败
     * @param fallReason  失败原因，失败时必传
     * @return
     */
    String buyProductMgs(Integer userId, String userNick,
                                String productName, String income, String corpus, String days,
                                String sucOrFall, String fallReason);

    /**
     * 购买成功或失败-赞分期
     *
     * @param userId      用户id
     * @param userNick    用户昵称
     * @param productName 产品名称
     * @param income      年化收益
     * @param corpus      投资本金
     * @param months      投资月数
     * @param sucOrFall   成功或失败 suc -成功，fall-失败
     * @param fallReason  失败原因，失败时必传
     * @return
     */
    String buyProductMgs4Zan(Integer userId, String userNick,
                                    String productName, String income, String corpus, String months,
                                    String sucOrFall, String fallReason);

    /**
     * 提现成功或失败
     *
     * @param userId      用户id
     * @param userNick    用户昵称
     * @param cardNo      银行卡号后四位
     * @param money       提现金额
     * @param sucOrFall   成功或失败 suc -成功，fall-失败
     * @param fallReason  失败原因，失败时必传
     * @param fee         手续费
     * @param actInAmount 实际到账
     * @return
     */
    String withdrawMgs(Integer userId, String userNick, String cardNo,
                              String money, String sucOrFall, String fallReason, String fee, String actInAmount);
    
    /**
     * 奖励金提现成功或失败
     *
     * @param userId      用户id
     * @param userNick    用户昵称
     * @param cardNo      银行卡号后四位
     * @param money       提现金额
     * @param sucOrFall   成功或失败 suc -成功，fall-失败
     * @param fallReason  失败原因，失败时必传
     * @return
     */
    String bonusWithdrawMgs(Integer userId, String userNick, String cardNo,
                              String money, String sucOrFall, String fallReason);

    /**
     * 充值成功或失败
     *
     * @param userId     用户id
     * @param userNick   用户昵称
     * @param mobile     手机号
     * @param money      充值金额
     * @param sucOrFall  成功或失败 suc -成功，fall-失败
     * @param fallReason 失败原因，失败时必传
     * @return
     */
    String topUpMgs(Integer userId, String userNick, String mobile,
                           String money, String sucOrFall, String fallReason);

    /**
     * 红包发送微信模板
     *
     * @param userId
     * @param packetAmount 红包金额
     * @param packetName   红包名称
     * @return
     */
    String packetSend(Integer userId, String packetAmount, String packetName);


    /**
     * 站岗户资金自动退回微信模板
     *
     * @param userId
     * @param openBalance  委托金额
     * @param balance      实际出借金额
     * @param productId    产品ID_auth
     * @param subAccountId 子账户ID
     * @param openTime     申请时间
     * @return
     */
    String chargeAuthActBackSend(Integer userId, String openBalance, String balance, String productId, String subAccountId, String openTime);

    /**
     * 完成出借-最后一笔出借完成发送微信模板
     *
     * @param userId
     * @param openBalance  委托金额
     * @param productId    产品ID_auth
     * @param subAccountId 子账户ID
     * @param openTime     申请时间
     * @param borrowAmount 出借金额
     * @return
     */
    String finishLoanSend(Integer userId, String openBalance, String productId, String subAccountId, String openTime, String borrowAmount);

    /**
     * 奖励金到账通知
     * @author bianyatian
     * @param userId 用户id
     * @param amount 到账金额
     * @param finishTime 完成时间
     * @return
     */
    String bonusArrive(Integer userId, String amount, String finishTime);

    /**
     * 加息券发放通知
     * @param userId    用户ID
     * @param name      加息券名称
     * @param rate      加息利率（%）
     * @return
     */
    @Deprecated
    String ticketInterestGrant(Integer userId, String name, Double rate);

    /**
     * 优惠券（红包、加息券）到期提醒通知
     * @param userId 用户id
     * @param ticketCount 到期优惠券数量
     * @param dueDate 优惠券到期日期
     * @return
     */
    @Deprecated
    String ticketReminderNotify(Integer userId, Integer ticketCount, String dueDate);
}
