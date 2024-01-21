package com.pinting.business.accounting.loan.service;

import com.pinting.business.model.LnAccountFill;
import com.pinting.business.model.vo.LnAccountFillDetailVO;

import java.util.List;


/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description: 通知合作方服务
 */
public interface DepFixedNotifyPartnerService {

    /**
     * 云贷待补账通知
     * @param lnAccountFill
     */
    void notifyWaitFill2YunDai(LnAccountFill lnAccountFill, List<LnAccountFillDetailVO> detailList);

    /**
     * 7贷待补账通知
     * @param lnAccountFill
     * @param detailList
     */
    void notifyWaitFill2SevenDai(LnAccountFill lnAccountFill, List<LnAccountFillDetailVO> detailList);
}
