package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.model.DFResultInfo;
import com.pinting.business.model.LnMarketGrantRecord;
import com.pinting.business.model.vo.LnLoanVO;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Partner_MarketingTrans;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Partner_QueryBalance;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Partner_QueryMarketingTrans;

/**
 * Created by 剑钊
 *
 * @2016/10/18 10:54.
 */

public interface PartnerTransService {

    /**
     * 合作方营销代付
     * @param req
     */
    void marketingTrans(G2BReqMsg_Partner_MarketingTrans req);

    /**
     * 营销代付通知
     * @param req
     */
    void notifyMarketing(DFResultInfo req);

    /**
     * 通知合作方
     * @param record
     * @param errorMsg
     */
    void notify2Partner(LnMarketGrantRecord record, String errorMsg);

    /**
     * 查询营销代付状态
     * @return
     */
    LnLoanVO queryMarketingTransStatus(G2BReqMsg_Partner_QueryMarketingTrans req);

    /**
     * 查询宝付账户余额
     * @param req
     */
    String queryBalance(G2BReqMsg_Partner_QueryBalance req);
}
