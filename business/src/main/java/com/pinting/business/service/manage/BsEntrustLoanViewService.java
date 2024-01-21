package com.pinting.business.service.manage;


import com.pinting.business.hessian.manage.message.ReqMsg_BsEntrustLoanView_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsEntrustLoanView_GetList;
import com.pinting.business.model.BsEntrustLoanView;

public interface BsEntrustLoanViewService {


	/**
	 * 根据时间段和资金接收方查询列表（委托和借款概览）
	 * @param req
	 * @param res
	 * @return
	 */
	void getListByTimePropertySymbol(ReqMsg_BsEntrustLoanView_GetList req,ResMsg_BsEntrustLoanView_GetList res);

	/**
	 * 查询VIP当前持有债权金额，按投资期限统计
	 * @return
	 */
	BsEntrustLoanView getZANVIPList();
}
