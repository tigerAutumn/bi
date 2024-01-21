package com.pinting.business.service.loan;

import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_Repay_QueryRepayResult;

public interface RepayQueryService {

    /**
     * 还款结果查询
     * @param req
     * @param res
     * @throws Exception
     */
    void queryRepayResult(G2BReqMsg_Repay_QueryRepayResult req, G2BResMsg_Repay_QueryRepayResult res)throws Exception;

}
