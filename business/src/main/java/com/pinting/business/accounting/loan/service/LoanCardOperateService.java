package com.pinting.business.accounting.loan.service;

import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_BindCardConfirm;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_PreBindCard;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_UnBindCard;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface LoanCardOperateService {

    /**
     * 预绑卡
     * @param req
     */
    String preBindCard(G2BReqMsg_LoanCif_PreBindCard req) throws Exception;

    /**
     * 确认绑卡
     * @param req
     */
    String bindCardConfirm(G2BReqMsg_LoanCif_BindCardConfirm req) throws Exception;


    /**
     * 解绑卡
     * @param req
     */
    void unBindCard(G2BReqMsg_LoanCif_UnBindCard req) throws Exception;
}
