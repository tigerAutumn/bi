package com.pinting.mall.service.site;

import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCommodity;

/**
 * 积分商城商品兑换流程服务
 *
 * @author zousheng
 * @date 2018/5/16 10:50
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface MallExchangeFlowService {

    /**
     * 商品兑换前校验
     *
     * @param req
     * @param res
     */
    void exchangePreCheck(ReqMsg_MallExchangeFlow_ExchangeCheck req, ResMsg_MallExchangeFlow_ExchangeCheck res);

    /**
     * 商品兑换实现
     *
     * @param req
     * @param res
     */
    void exchangeCommodity(ReqMsg_MallExchangeFlow_ExchangeCommodity req, ResMsg_MallExchangeFlow_ExchangeCommodity res);
}
