package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateUserInfo;

public interface App178TransportService {
	
    /**
     * 订单通知
     * @param req
     * @return
     */
    B2GResMsg_APP178_OrderNotice orderNotice(B2GReqMsg_APP178_OrderNotice req);
    
    /**
     * 更新产品状态
     * @param req
     * @return
     */
    B2GResMsg_APP178_UpdateProductInfo updateProductInfo(B2GReqMsg_APP178_UpdateProductInfo req);

    /**
     * 更新会员信息
     * @param req
     * @return
     */
    B2GResMsg_APP178_UpdateUserInfo UpdateUserInfo(B2GReqMsg_APP178_UpdateUserInfo req);
    
    /**
     * 更新还款计划状态
     * @param req
     * @return
     */
    B2GResMsg_APP178_UpdateRepayPlan updateRepayPlan(B2GReqMsg_APP178_UpdateRepayPlan req);


}
