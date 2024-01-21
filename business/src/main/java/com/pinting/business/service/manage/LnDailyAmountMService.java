package com.pinting.business.service.manage;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.common.PagerModelRspDTO;
import com.pinting.business.model.common.PagerReqDTO;

import java.util.Date;
import java.util.Map;

/**
 * 每日额度管理
 */
public interface LnDailyAmountMService {

    /**
     * 查询资产方每日可用额度信息
     *
     * @return
     */
    PagerModelRspDTO<LnDailyAmount> getLnDailyAmountAll(PagerReqDTO pagerReqDTO, String status);

    /**
     * 根据资产方与状态查询每日可用额度配置
     * @param partnerCode
     * @param status
     * @return
     */
    LnDailyAmount getLnDailyAmount(String partnerCode, String status);

    /**
     * 查询资产方站岗资金当前可用余额
     *
     * @param partnerEnum
     */
    double getAvailableBalanceAuthMoney(PartnerEnum partnerEnum);

    /**
     * 查询资产方站岗资金当前可用余额
     * @param partnerEnum
     * @return
     */
    double getAvailableBalanceAuthMoneyForSevenDian(PartnerEnum... partnerEnum);

    /**
     * 查询资产方次日退出本息（本金+应付利息）
     *
     * @param partnerEnum
     */
    double getQuitAuthMoney(PartnerEnum partnerEnum, VIPId4PartnerEnum vipId4PartnerEnum);

    /**
     * 初始化每日可用额度记录
     * @param freeRateYunDai
     * @param freeRateSevenDai
     * @param useDate
     */
    void initLnDailyAmount(double freeRateYunDai, double freeRateSevenDai, Date useDate);

    /**
     * 计算云贷站岗户/七贷站岗户/自由站岗户每日总可用额度
     * @return
     */
    Map<String, Double> dailyFreeMoneyCalculate();
}