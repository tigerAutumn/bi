package com.pinting.mall.facade;

import com.pinting.mall.hessian.site.message.ReqMsg_MallAddr_GetDetail;
import com.pinting.mall.hessian.site.message.ResMsg_MallAddr_GetDetail;
import com.pinting.mall.model.MallConsigneeAddressInfo;
import com.pinting.mall.service.site.MallAddrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户收货地址相关
 *
 * @author zousheng
 * @project mall
 * @2018-5-16 下午2:04:33
 */
@Component("MallAddr")
public class MallAddrFacade {
    private static Logger logger = LoggerFactory.getLogger(MallAddrFacade.class);

    @Autowired
    private MallAddrService mallAddrService;

    /**
     * 查询用户收货地址信息
     *
     * @param req
     * @param res
     */
    public void getDetail(ReqMsg_MallAddr_GetDetail req, ResMsg_MallAddr_GetDetail res) {

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
