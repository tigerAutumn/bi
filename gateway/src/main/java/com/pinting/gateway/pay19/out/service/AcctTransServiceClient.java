/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.AcctTransReq;
import com.pinting.gateway.pay19.out.model.req.QueryRecvAcctTransReq;
import com.pinting.gateway.pay19.out.model.resp.AcctTransResp;
import com.pinting.gateway.pay19.out.model.resp.QueryRecvAcctTransResp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransServiceClient.java, v 0.1 2015-11-3 下午5:11:36 BabyShark Exp $
 */
public interface AcctTransServiceClient {

	/**
	 * 钱包转账
	 * @param req
	 * @return
	 */
    AcctTransResp acctTrans(AcctTransReq req);
    
    /**
     * 收款方转账订单查询
     * @param req
     * @return
     */
    QueryRecvAcctTransResp queryRecvAcctTrans(QueryRecvAcctTransReq req);

}
