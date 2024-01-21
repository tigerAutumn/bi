/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.enums.AcctTransRespCode;
import com.pinting.gateway.pay19.out.enums.AcctTransRespStatus;
import com.pinting.gateway.pay19.out.enums.AcctTransUrl;
import com.pinting.gateway.pay19.out.model.req.AcctTransReq;
import com.pinting.gateway.pay19.out.model.req.QueryRecvAcctTransReq;
import com.pinting.gateway.pay19.out.model.resp.AcctTransResp;
import com.pinting.gateway.pay19.out.model.resp.QueryRecvAcctTransResp;
import com.pinting.gateway.pay19.out.service.AcctTransServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransServiceClientImpl.java, v 0.1 2015-11-3 下午5:12:26 BabyShark Exp $
 */
@Service("acctTransServiceClient")
public class AcctTransServiceClientImpl implements AcctTransServiceClient {

    /** 
     * @see com.pinting.gateway.pay19.out.service.AcctTransServiceClient#acctTrans(com.pinting.gateway.pay19.out.model.req.AcctTransReq)
     */
    @Override
    public AcctTransResp acctTrans(AcctTransReq req) {
        AcctTransResp resp = (AcctTransResp) Pay19HttpUtil.acctTransSend(AcctTransUrl.ACCT_TRANS,
            req);
        if (!AcctTransRespStatus.SUCCESS.getCode().equals(resp.getReqStatus())) {
            AcctTransRespCode code = AcctTransRespCode.find(resp.getRetCode());
            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getRetCode());
        }
        return resp;
    }

    /**
     * 查询转账订单信息
     * @param req
     * @return
     */
	@Override
	public QueryRecvAcctTransResp queryRecvAcctTrans(QueryRecvAcctTransReq req) {
		QueryRecvAcctTransResp resp = (QueryRecvAcctTransResp) Pay19HttpUtil.
				acctTransSend(AcctTransUrl.QUERY_RECV_ACCT_TRANS, req);
	    if (!AcctTransRespStatus.SUCCESS.getCode().equals(resp.getReqStatus())) {
	    	AcctTransRespCode code = AcctTransRespCode.find(resp.getRetCode());
	        String errMsg = code != null ? code.getDescription() : resp.getRetCode();
	        throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + resp.getRetCode());
	    }
	    return resp;
	}

}
