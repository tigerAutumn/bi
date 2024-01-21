package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.model.BillInfo;
import com.pinting.business.accounting.loan.model.Loan7BillInfo;

/**
 * 
 * @project business
 * @title DepFixedBillSyncService.java
 * @author Dragon & cat
 * @date 2017-4-4
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  存管业务固定期限产品账单获取和同步
 */
public interface DepFixedBillSyncService {


    /**
     * 获取最新账单
     * @param partnerUserId
     * @param partnerLoanId
     * @return
     */
    BillInfo getNewestBill(String partnerUserId, String partnerLoanId);
    /**
     * 账单同步处理
     * @param billInfo
     */
    void loanSyncBill(BillInfo billInfo, Integer lnLoanId);
    
    /**
     * 管理台-手动账单同步处理
     * @param billInfo
     */
	void manualLoanSyncBill(BillInfo billInfo);
	
    /**
     * 获取最新七贷账单
     * @param partnerUserId
     * @param partnerLoanId
     * @return
     */
    Loan7BillInfo getSevenNewBills(String partnerUserId, String partnerLoanId);
}
