package com.pinting.mall.facade;

import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.model.MallConsigneeAddressInfo;
import com.pinting.mall.service.site.MallAddrService;
import com.pinting.mall.service.site.MallExchangeFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商城商品兑换流程相关
 *
 * @author bianyatian
 * @project mall
 * @2018-5-15 下午2:04:33
 */
@Component("MallExchangeFlow")
public class MallExchangeFlowFacade {
    private static Logger logger = LoggerFactory.getLogger(MallExchangeFlowFacade.class);

    @Autowired
    private MallExchangeFlowService mallExchangeFlowService;
    @Autowired
    private MallAddrService mallAddrService;

    /**
     * 商品兑换前校验
     *
     * @param req
     * @param res
     */
    public void exchangeCheck(ReqMsg_MallExchangeFlow_ExchangeCheck req, ResMsg_MallExchangeFlow_ExchangeCheck res) {

        mallExchangeFlowService.exchangePreCheck(req, res);

        // 实体商品展示用户收货地址
        if (res.getAddrShow()) {
            MallConsigneeAddressInfo addressInfo = mallAddrService.getAddressInfoByUserId(req.getUserId());
            if (addressInfo != null) {
                res.setId(addressInfo.getId());
                res.setUserId(addressInfo.getUserId());
                res.setRecMobile(addressInfo.getRecMobile());
                res.setRecName(addressInfo.getRecName());
                res.setRecAdress(addressInfo.getRecAdress());
                res.setRecAdressDetail(addressInfo.getRecAdressDetail());
            }
        }
    }

    /**
     * 商品兑换实现
     *
     * @param req
     * @param res
     */
    public void exchangeCommodity(ReqMsg_MallExchangeFlow_ExchangeCommodity req, ResMsg_MallExchangeFlow_ExchangeCommodity res) {

        mallExchangeFlowService.exchangeCommodity(req, res);
    }
}
