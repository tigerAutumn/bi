package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.finance.model.TransferAccountInfo;
import com.pinting.business.accounting.loan.model.FixedRepaySysSplitInfo;
import com.pinting.business.model.LnRepeatRepayRecord;

/**
 * 
 * @project business
 * @title DepFixedRepayAccountService.java
 * @author Dragon & cat
 * @date 2017-4-4
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 增计划还款记账服务（主动还款、代扣还款、代偿还款）
 * F：理财人；S：系统；B：借款人
 * OF：出让人；IF：承接人
 */
public interface DepFixedRepayAccountService {

	
	/**
	 * 重复还款入营收账记账	
	 * @param repeatRepay
	 */
    void repayRepeat2AccRecord(LnRepeatRepayRecord repeatRepay, Double marginAmount);
    
    /**
     * 还款系统分账,系统记账
     * @param repaySysSplitInfo
     */
    void repaySysSplit(FixedRepaySysSplitInfo repaySysSplitInfo);

    /**
     * 退出时定时债权转让记账
     * OF:AUTH_YUN + (leftPlanInterest-)
     * OF:DIFF -
     * IF:AUTH_YUN -
     * IF:RED - 
     * S:DEP_BGW_REVENUE_YUN + fee
     * S:DEP_REDPACKET -
     * S:BGW_AUTH_YUN 分账
     * @param transferInfo
     */
    void chargeRelationTransfer(TransferAccountInfo transferInfo);
}
