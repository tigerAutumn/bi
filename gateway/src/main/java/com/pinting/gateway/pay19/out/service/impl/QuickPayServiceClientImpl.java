/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.enums.QuickPayRespCode;
import com.pinting.gateway.pay19.out.enums.QuickPayRespStatus;
import com.pinting.gateway.pay19.out.enums.QuickPayUrl;
import com.pinting.gateway.pay19.out.model.req.ConfirmOrderReq;
import com.pinting.gateway.pay19.out.model.req.PreOrderReq;
import com.pinting.gateway.pay19.out.model.req.QueryBankCardListReq;
import com.pinting.gateway.pay19.out.model.req.QueryBankListReq;
import com.pinting.gateway.pay19.out.model.req.QueryMOrderReq;
import com.pinting.gateway.pay19.out.model.req.RSendSmsReq;
import com.pinting.gateway.pay19.out.model.req.UnBindCardReq;
import com.pinting.gateway.pay19.out.model.resp.ConfirmOrderResp;
import com.pinting.gateway.pay19.out.model.resp.PreOrderResp;
import com.pinting.gateway.pay19.out.model.resp.QueryBankCardListResp;
import com.pinting.gateway.pay19.out.model.resp.QueryBankListResp;
import com.pinting.gateway.pay19.out.model.resp.QueryMOrderResp;
import com.pinting.gateway.pay19.out.model.resp.RSendSmsResp;
import com.pinting.gateway.pay19.out.model.resp.UnBindCardResp;
import com.pinting.gateway.pay19.out.service.QuickPayServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay19QuickPayServiceImpl.java, v 0.1 2015-8-6 下午3:33:24 BabyShark Exp $
 */
@Service("quickPayServiceClient")
public class QuickPayServiceClientImpl implements QuickPayServiceClient {

    /** 
     * @see QuickPayServiceClient#preOrder(PreOrderReq)
     */
    @Override
    public PreOrderResp preOrder(PreOrderReq req) {
        PreOrderResp resp = (PreOrderResp) Pay19HttpUtil.quickPaySend(QuickPayUrl.PRE_ORDER, req);
        if ((QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())
                && QuickPayRespCode.SUCCESS_CODE_00000.getCode().equals(resp.getError_code()))
            || (QuickPayRespStatus.FAIL.getCode().equals(resp.getStatus()) && QuickPayRespCode.ERROR_CODE_31017
                .getCode().equals(resp.getError_code()))) {
            return resp;
        } else {
            QuickPayRespCode code = QuickPayRespCode.find(resp.getError_code());
            String errCode = resp.getError_code();
            String errMsg = code != null ? code.getDescription() : resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
        }
    }

    /** 
     * @see QuickPayServiceClient#confirmOrder(ConfirmOrderReq)
     */
    @Override
    public ConfirmOrderResp confirmOrder(ConfirmOrderReq req) {
        ConfirmOrderResp resp = (ConfirmOrderResp) Pay19HttpUtil.quickPaySend(
            QuickPayUrl.CONFIRM_ORDER, req);
        if (!(QuickPayRespStatus.SUCCESS.getCode().equals(resp.getReq_status())
                && QuickPayRespCode.SUCCESS_CODE_00000.getCode().equals(resp.getError_code()))) {
            QuickPayRespCode code = QuickPayRespCode.find("C" + resp.getError_code());
            String errCode = "C" + resp.getError_code();
            String errMsg = code != null ? code.getDescription() : "C"
                                                                   + resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
        }
        return resp;
    }

    /** 
     * @see QuickPayServiceClient#rSendSms(RSendSmsReq)
     */
    @Override
    public RSendSmsResp rSendSms(RSendSmsReq req) {
        RSendSmsResp resp = (RSendSmsResp) Pay19HttpUtil.quickPaySend(QuickPayUrl.RE_SEND_SMS, req);
        if (!QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
            QuickPayRespCode code = QuickPayRespCode.find(resp.getError_code());
            String errMsg = code != null ? code.getDescription() : resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getError_code());
        }
        return resp;
    }

    /** 
     * @see QuickPayServiceClient#queryMOrder(QueryMOrderReq)
     */
    @Override
    public QueryMOrderResp queryMOrder(QueryMOrderReq req) {
        QueryMOrderResp resp = (QueryMOrderResp) Pay19HttpUtil.quickPaySend(
            QuickPayUrl.QUERY_ORDER, req);
        if (!QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
            QuickPayRespCode code = QuickPayRespCode.find(resp.getError_code());
            String errMsg = code != null ? code.getDescription() : resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getError_code());
        }
        return resp;
    }

    /** 
     * @see QuickPayServiceClient#queryBankList(QueryBankListReq)
     */
    @Override
    public QueryBankListResp queryBankList(QueryBankListReq req) {
        QueryBankListResp resp = (QueryBankListResp) Pay19HttpUtil.quickPaySend(
            QuickPayUrl.QUERY_BANK_LIST, req);
        if (!QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
            QuickPayRespCode code = QuickPayRespCode.find(resp.getError_code());
            String errMsg = code != null ? code.getDescription() : resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getError_code());
        }
        return resp;
    }

    /** 
     * @see QuickPayServiceClient#queryBankCardList(QueryBankCardListReq)
     */
    @Override
    public QueryBankCardListResp queryBankCardList(QueryBankCardListReq req) {
        QueryBankCardListResp resp = (QueryBankCardListResp) Pay19HttpUtil.quickPaySend(
            QuickPayUrl.QUERY_BANK_CARD_LIST, req);
        if (!QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
            QuickPayRespCode code = QuickPayRespCode.find(resp.getError_code());
            String errMsg = code != null ? code.getDescription() : resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getError_code());
        }
        return resp;
    }

    /** 
     * @see QuickPayServiceClient#unBindCard(UnBindCardReq)
     */
    @Override
    public UnBindCardResp unBindCard(UnBindCardReq req) {
        UnBindCardResp resp = (UnBindCardResp) Pay19HttpUtil.quickPaySend(QuickPayUrl.UNBIND_CARD,
            req);
        if (!QuickPayRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
            QuickPayRespCode code = QuickPayRespCode.find(resp.getError_code());
            String errMsg = code != null ? code.getDescription() : resp.getError_code();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getError_code());
        }
        return resp;
    }

}
