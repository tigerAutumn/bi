package com.pinting.business.accounting.finance.service;

import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;

/**
 * 存管体系用户余额购买服务
 * Created by babyshark on 2017/4/1.
 */
public interface DepUserBalanceBuyService {
	
    /**
     * 固定期限产品购买
     * @param req
     * @param res
     */
    void buyFixed(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res);

    /**
     * 分期产品购买
     * @param req
     * @param res
     */
    void buyStage(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res);
    
    /**
     * VIP固定期限产品购买
     * @param req
     */
    void buyFixed4Super(ReqMsg_RegularBuy_BalanceBuy req);

    /**
     * VIP分期产品购买
     * @param req
     */
    void buyStage4Super(ReqMsg_RegularBuy_BalanceBuy req);

}
