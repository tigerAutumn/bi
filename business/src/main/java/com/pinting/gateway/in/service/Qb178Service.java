package com.pinting.gateway.in.service;

import com.pinting.gateway.hessian.message.qb178.*;

/**
 * Created by babyshark on 2017/7/31.
 */
public interface Qb178Service {

    /**
     * 分页查询产品详情列表
     * @param req
     * @return
     */
    G2BResMsg_Qb178_QueryProductList queryProductDetails(G2BReqMsg_Qb178_QueryProductList req);
    
    /**
     * 分页订单列表
     * @param req
     * @return
     */
    G2BResMsg_Qb178_QueryOrderList queryOrderDetails(G2BReqMsg_Qb178_QueryOrderList req);

    /**
     * 分页查询会员详情
     * @param req
     * @return
     */
    G2BResMsg_Qb178_QueryUserDetails queryUserDetails(G2BReqMsg_Qb178_QueryUserDetails req);
    
    
    /**
     * 查询会员持仓余额
     * @param req
     * @param res
     */
    void queryPositionBalance(G2BReqMsg_Qb178_QueryPositionBalance req, G2BResMsg_Qb178_QueryPositionBalance res);

    /**
     * 分页查询会员资金流水
     * @param req
     * @return
     */
    G2BResMsg_Qb178_QueryBalanceJnl queryUserBalanceJnl(G2BReqMsg_Qb178_QueryBalanceJnl req);
    

    /**
     * 查询还款计划
     * @param req
     * @param res
     */
    void queryRepayPlan(G2BReqMsg_Qb178_QueryRepayPlan req, G2BResMsg_Qb178_QueryRepayPlan res);

    /**
     * 查询会员资金余额
     * @param req
     * @return
     */
    G2BResMsg_Qb178_QueryBalance queryBalance(G2BReqMsg_Qb178_QueryBalance req);


}
