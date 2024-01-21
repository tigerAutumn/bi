package com.pinting.business.accounting.finance.service;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsBatchBuyDetail;
import com.pinting.business.model.BsBatchReturnDetail;
import com.pinting.business.model.BsDepositionReturn;

import java.util.List;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface UserReturnMoneyService {
    /**
     * 用户批量回款
     * @param receiveBatchs
     */
    void returnBatch(List<BsBatchBuy> receiveBatchs);

    /**
     * 回款结算计划生成
     * @param batchBuyDetails
     * @return
     */
    void generatePlans(List<BsBatchBuyDetail> batchBuyDetails);

    /**
     * 根据回款结算表进行单笔回款
     * @param batchReturnDetail
     */
    void return2Card(BsBatchReturnDetail batchReturnDetail);

    /**
     * 根据回款结算表进行单笔回款
     * @param batchReturnDetail
     */
    void return2Balance(BsBatchReturnDetail batchReturnDetail);

    /**
     * 用户回款到卡结果通知处理
     * @param req
     */
    void notifyReturn2CardResult(DFResultInfo req);

    /**
     * 存管定期产品退出生成回款计划
     * @param subAccountId
     * @param userId
     * @param principal
     * @param interest
     * @param penalty
     * @param bonus
     * @param overflowInterest
     */
	void depGeneratePlans(Integer subAccountId, Integer userId,
			Double principal, Double interest, Double penalty,Double bonus,Double overflowInterest);
	/**
	 * 存管定期产品退出回款到余额
	 * @param bsDepositionReturn
	 */
	void depReturn2Balance(BsDepositionReturn bsDepositionReturn);
	
	/**
	 * 存量回款3.10之前的回款
	 * @param batchReturnDetail
	 */
	void return2Balance4Before(BsBatchReturnDetail batchReturnDetail);

}
