package com.pinting.business.accounting.finance.service;

import com.pinting.business.model.BsBonusGrantPlan;

/**
 * Author:      cyb
 * Date:        2017/4/10
 * Description: 存管体系用户奖励金发放
 */
public interface DepUserBonusGrantService {

/**
 *  1. 推荐人拿所有奖励金。奖励金第一期发放，以及后续发放计划记录生成
 *  2. 购买人、推荐人各拿部分奖励金。奖励金第一期发放，以及后续发放计划记录生成
 *  3. 购买人拿部分奖励金。奖励金第一期发放，以及后续发放计划记录生成
 *  4. 奖励金发放
 *  5. 根据用户编号，获得该用户产生奖励金的发放类型
 *  6. 委托计划，推荐人拿所有奖励金
 *  7. 委托计划，购买人、推荐人各拿部分奖励金。奖励金第一期发放，以及后续发放计划记录生成
 *  8. 委托计划，购买人拿部分奖励金。奖励金第一期发放，以及后续发放计划记录生成
 *  9. 奖励金提现
 */

    /**
     * 推荐人拿所有奖励金
     * 奖励金第一期发放，以及后续发放计划记录生成
     * @param selfUserId 理财人编号
     * @param referrerUserId 推荐人编号
     * @param subAccountId 理财产品户编号
     * @param amount 理财金额
     */
    void referrerTakeAll(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount);

    /**
     * 购买人、推荐人各拿部分奖励金
     * 奖励金第一期发放，以及后续发放计划记录生成
     * @param selfUserId 理财人编号
     * @param referrerUserId 推荐人编号
     * @param subAccountId 理财产品户编号
     * @param amount 理财金额
     * @param newRules 是否新规则：true-新规则；false-老规则
     */
    void eachTakePart(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount, boolean newRules);

    /**
     * 购买人拿部分奖励金
     * 奖励金第一期发放，以及后续发放计划记录生成
     * @param selfUserId 理财人编号
     * @param subAccountId 理财产品户编号
     * @param amount 理财金额
     * @param newRules 是否新规则：true-新规则；false-老规则
     */
    void selfTakePart(Integer selfUserId, Integer subAccountId, Double amount, boolean newRules);

    /**
     * 奖励金发放
     * @param plan 发放计划记录
     * @return 成功返回true，否则false
     */
    boolean grantBonus(BsBonusGrantPlan plan);

    /**
     * 根据用户编号，获得该用户产生奖励金的发放类型
     * @param userId 用户编号
     * @return
     */
    String getBonusGrantTypeByUserId(Integer userId);

    /**
     * 委托计划
     * 推荐人拿所有奖励金
     * 奖励金第一期发放，以及后续发放计划记录生成
     * @param selfUserId 理财人编号
     * @param referrerUserId 推荐人编号
     * @param subAccountId 理财产品户编号
     * @param amount 理财金额
     */
    void entrustReferrerTakeAll(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount);

    /**
     * 委托计划
     * 购买人、推荐人各拿部分奖励金
     * 奖励金第一期发放，以及后续发放计划记录生成
     * @param selfUserId 理财人编号
     * @param referrerUserId 推荐人编号
     * @param subAccountId 理财产品户编号
     * @param amount 理财金额
     * @param newRules 是否新规则：true-新规则；false-老规则
     */
    void entrustEachTakePart(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount, boolean newRules);

    /**
     * 委托计划
     * 购买人拿部分奖励金
     * 奖励金第一期发放，以及后续发放计划记录生成
     * @param selfUserId 理财人编号
     * @param subAccountId 理财产品户编号
     * @param amount 理财金额
     * @param newRules 是否新规则：true-新规则；false-老规则
     */
    void entrustSelfTakePart(Integer selfUserId, Integer subAccountId, Double amount, boolean newRules);

    /**
     * 是否在活动期间之内
     * @param activityId  活动ID
     * @return
     */
    String duringActivity(Integer activityId);

    /**
     * 2019年以及之后的奖励金发放处理
     * @param selfUserId        理财人编号
     * @param referrerUserId    推荐人编号
     * @param subAccountId      理财产品户编号
     * @param amount            理财金额
     * @param bonusGrantType    奖励金发放类型
     * @param propertySymbol    资金接收方标志
     */
    void newBonus2019Process(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount, String bonusGrantType, String propertySymbol);
}
