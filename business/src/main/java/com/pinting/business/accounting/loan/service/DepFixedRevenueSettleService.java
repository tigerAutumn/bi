package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.RevenueSettleResultInfo;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.dto.OrderResultInfo;

/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description: 营收结算及通知（还款营收、砍头息）
 */
public interface DepFixedRevenueSettleService {

    /**
     * 每日借款手续费、还款营收结算
     */
    void revenueSettle();
    
    /**
     * 每日重复还款结算
     */
    void repayRepeatSettle();

    /**
     * 营收结算结果处理-云贷
     * @param revenueSettleResultInfo
     */
    void revenueSettleResult(RevenueSettleResultInfo revenueSettleResultInfo, PartnerEnum partnerEnum);
    
    /**
     * 每日借款手续费、还款营收结算-赞时贷
     */
    void zsdRevenueSettle();
    
    /**
     * 营收结算结果处理-赞时贷
     * @param revenueSettleResultInfo
     */
    void zsdRevenueSettleResult(RevenueSettleResultInfo revenueSettleResultInfo);
    
    /**
     * 营收结算通知云贷
     */
    void revenueSettleNotify(BsPayOrders order);

    /**
     * 营收结算通知7贷
     */
    void sevenRevenueSettleNotify(BsPayOrders order);
    
    /**
     * 每日重复还款结算
     */
    void zsdRepayRepeatSettle();

    /**
     * 7贷每日营收结算及通知
     */
    void sevenDaiRevenueSettle();

    /**
     * 7贷每日重复还款结算
     */
    void sevenRepayRepeatSettle();
}
