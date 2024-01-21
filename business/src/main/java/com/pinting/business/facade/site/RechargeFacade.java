/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site;

import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.site.*;
import com.pinting.gateway.out.service.NetBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 充值
 * @author HuanXiong
 * @version $Id: RechargeFacade.java, v 0.1 2015-11-19 上午10:38:40 HuanXiong Exp $
 */
@Component("Recharge")
public class RechargeFacade {
    
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private NetBankService  netBankService;
    @Autowired
    private UserTopUpService userTopUpService;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private DepUserTopUpService depUserTopUpService;
    @Autowired
    private SubAccountService subAccountService;
    
    private Logger log = LoggerFactory.getLogger(RegularBuyFacade.class);
    
    /**
     * 充值首页信息
     * 
     * @param req
     * @param resp
     */
    public void rechargeIndexInfo(ReqMsg_Recharge_RechargeIndexInfo req, ResMsg_Recharge_RechargeIndexInfo resp) {
        Double availableBalance = rechargeService.findAvailableBalanceByUserId(req.getUserId());
        BsSubAccount depJsh = subAccountService.findDEPJSHSubAccountByUserId(req.getUserId());
        resp.setAvailableBalance(availableBalance);
        resp.setDepAvailableBalance(depJsh == null ? 0d : (depJsh.getAvailableBalance() == null ? 0d : depJsh.getAvailableBalance()));
        resp.setUserId(req.getUserId());
        Map<String, Object> rechargeLimitMap = assetsService.queryTopUpInstruction();
        resp.setRechargeLimit((String)rechargeLimitMap.get("rechangeLimit"));
    }
    
    /**
     * 网银充值
     * 
     * @param reqMsg
     * @param resMsg
     */
    public void eBankRecharge(ReqMsg_Recharge_EBankRecharge reqMsg,ResMsg_Recharge_EBankRecharge resMsg) {
        
        /**
         * 1.判断是否已经实名认证，绑定银行卡
         * 2.判断购买次数是否充足，判断购买金额是否低于下限，高于上限
         * 3.插入产品子账户，当前状态为银行处理中
         * 4.购买流水前置登记
         * 5.新增订单
         * ==============以上过程全部封装到 【产品前置操作】 方法当中 ==========
         * 6.向19付发起购买申请
         */
        BsUser bsUser = userService.findUserById(reqMsg.getUserId());
        //用户网银充值
        //充值前准备工作
        log.info("【用户发起充值信息：】 userId = " + reqMsg.getUserId() + ", amount=" + reqMsg.getAmount() );
        ReqMsg_RegularBuy_EBankBuy eBankReq = new ReqMsg_RegularBuy_EBankBuy();
        eBankReq.setAmount(reqMsg.getAmount());
        eBankReq.setUserId(reqMsg.getUserId());
        eBankReq.setTransType(2);   // 充值
        eBankReq.setBankCode(reqMsg.getBankId());
        eBankReq.setWebFlag(reqMsg.getFlag());
        ResMsg_RegularBuy_EBankBuy eBankRes = new ResMsg_RegularBuy_EBankBuy();
        depUserTopUpService.hfEBank(eBankReq, eBankRes);
        //返回页
        resMsg.setHtmlStr(eBankRes.getResHtml());
    }
    
    /**
     * 查询充值手续费
     * @param req
     * @param resp
     */
    public void rechargeCommission(ReqMsg_Recharge_RechargeCommission req, ResMsg_Recharge_RechargeCommission resp) {
    	 // 手续费，记录手续费登记表
        CommissionVO commissionVO = commissionService.calServiceFee(null, TransTypeEnum.USER_TOP_UP_QUICK_PAY, null);
        resp.setNeedPayAmount(commissionVO.getNeedPayAmount());
        resp.setActPayAmount(commissionVO.getActPayAmount());
    }
    
    
}
