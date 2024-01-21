/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.dao.BsAgentMapper;
import com.pinting.business.hessian.site.message.ReqMsg_LandingPage_GetSupportTerminal;
import com.pinting.business.hessian.site.message.ReqMsg_LandingPage_Index318;
import com.pinting.business.hessian.site.message.ResMsg_LandingPage_GetSupportTerminal;
import com.pinting.business.hessian.site.message.ResMsg_LandingPage_Index318;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.MoneyUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: LandingPageFacade.java, v 0.1 2016-3-23 上午11:21:59 HuanXiong Exp $
 */
@Component("LandingPage")
public class LandingPageFacade {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BsAgentMapper bsAgentMapper;
    
    /**
     * 2016 318活动的广告落地页
     * @param req
     * @param res
     */
    public void index318(ReqMsg_LandingPage_Index318 req, ResMsg_LandingPage_Index318 res) {
        res.setTotalInterest(MoneyUtil.format(userService.countUserIncome()));
    }
    
    public void getSupportTerminal(ReqMsg_LandingPage_GetSupportTerminal req, ResMsg_LandingPage_GetSupportTerminal res){
    	res.setSupport_terminal(bsAgentMapper.selectByPrimaryKey(req.getId()).getSupportTerminal());
    }
    
}
