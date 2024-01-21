package com.pinting.business.accounting.finance.service;

import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;

/**
 * Created by babyshark on 2016/9/9.
 */
public interface UserBalanceBuyService {

    /**
     * 余额购买
     * @param req
     * @param res
     */
    void buy(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res);

    /**
     * 余额授权
     * @param req
     * @param res
     */
    void auth(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res);

    /**
     * 超级理财人余额授权
     * @param req
     */
    void superAuth(ReqMsg_RegularBuy_BalanceBuy req);

}
