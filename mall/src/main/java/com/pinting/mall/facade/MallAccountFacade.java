package com.pinting.mall.facade;

import com.pinting.mall.hessian.site.message.ReqMsg_MallAccount_GetDetail;
import com.pinting.mall.hessian.site.message.ResMsg_MallAccount_GetDetail;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.service.site.MallAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 积分账户相关
 *
 * @author zousheng
 * @project mall
 * @2018-5-16 下午2:04:33
 */
@Component("MallAccount")
public class MallAccountFacade {
    private static Logger logger = LoggerFactory.getLogger(MallAccountFacade.class);

    @Autowired
    private MallAccountService mallAccountService;

    /**
     * 查询用户积分账户信息
     *
     * @param req
     * @param res
     */
    public void getDetail(ReqMsg_MallAccount_GetDetail req, ResMsg_MallAccount_GetDetail res) {

        MallAccount mallAccount = mallAccountService.getAccountByUserId(req.getUserId());
        if (mallAccount != null) {
            res.setId(mallAccount.getId());
            res.setUserId(mallAccount.getUserId());
            res.setAccountType(mallAccount.getAccountType());
            res.setBalance(mallAccount.getBalance());
            res.setAvaliableBalance(mallAccount.getAvaliableBalance());
            res.setFreezeBalance(mallAccount.getFreezeBalance());
            res.setTotalGainPoints(mallAccount.getTotalGainPoints());
            res.setTotalUsedPoints(mallAccount.getTotalUsedPoints());
        }
    }
}
