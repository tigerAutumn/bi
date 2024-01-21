package com.pinting.business.accounting.loan.service;

import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_FillFinishNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_FillFinishNotice;

/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description: 云贷补账服务
 */
public interface DepFixedActFillService {
	
	/**
	 * 云贷补账完成处理服务
	 * @param req
	 */
	void depFixedActFillFinishHandle(G2BReqMsg_DafyLoan_FillFinishNotice req);

	/**
	 * 7贷补账完成处理服务
	 * @param req
     */
	void depFixedActFillFinishHandle(G2BReqMsg_DepLoan7_FillFinishNotice req);
}
