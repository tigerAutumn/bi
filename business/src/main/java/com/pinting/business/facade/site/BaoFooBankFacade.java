package com.pinting.business.facade.site;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.hessian.site.message.ReqMsg_BaoFooBank_BindCard;
import com.pinting.business.hessian.site.message.ReqMsg_BaoFooBank_PreBindCard;
import com.pinting.business.hessian.site.message.ResMsg_BaoFooBank_BindCard;
import com.pinting.business.hessian.site.message.ResMsg_BaoFooBank_PreBindCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:      cyb
 * Date:        2016/8/23
 * Description: 宝付Facade
 */
@Component("BaoFooBank")
public class BaoFooBankFacade {

    @Autowired
    private UserCardOperateService userCardOperateService;

    /**
     * 预绑卡
     * @param req   ReqMsg_BaoFooBank_PreBindCard
     * @param res   ResMsg_BaoFooBank_PreBindCard
     */
    public void preBindCard(ReqMsg_BaoFooBank_PreBindCard req, ResMsg_BaoFooBank_PreBindCard res) {
        String orderNo = userCardOperateService.preBindCard(req.getUserName(), req.getIdCard(), req.getCardNo(), req.getMobile(), req.getBankId(), req.getUserId(), req.getTerminalType());
        res.setOrderNo(orderNo);
    }

    /**
     * 正式绑卡
     * @param req   ReqMsg_BaoFooBank_BindCard
     * @param res   ResMsg_BaoFooBank_BindCard
     */
    public void bindCard(ReqMsg_BaoFooBank_BindCard req, ResMsg_BaoFooBank_BindCard res) {
        userCardOperateService.bindCard(req.getOrderNo(), req.getSmsCode(), req.getUserId());
    }

}
