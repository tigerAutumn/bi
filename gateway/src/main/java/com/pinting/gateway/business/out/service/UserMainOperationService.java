package com.pinting.gateway.business.out.service;

import com.pinting.business.hessian.site.message.ReqMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserMainOperation_UserMainOperationAdd;

/**
 *
 */
public interface UserMainOperationService {

	/**
	 * 新增用户关键业务信息
	 * @param req
	 * @return
     */
	ResMsg_UserMainOperation_UserMainOperationAdd saveUserMainOperation(ReqMsg_UserMainOperation_UserMainOperationAdd req);

}
