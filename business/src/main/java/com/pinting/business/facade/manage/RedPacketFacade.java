/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.manage;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_BudgetStat;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_BudgetStat;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.model.vo.RedPaktBudgetStatVO;
import com.pinting.business.service.manage.BsRedPackatInfoService;
import com.pinting.business.service.manage.BsRedPacketApplyService;
import com.pinting.business.service.manage.BsRedPacketService;

/**
 * 红包
 * @author HuanXiong
 * @version $Id: RedPacketFacade.java, v 0.1 2016-2-29 下午5:52:55 HuanXiong Exp $
 */
@Component("RedPacket")
public class RedPacketFacade {
    
    @Autowired
    private BsRedPackatInfoService bsRedPackatInfoService;
    @Autowired
    private BsRedPacketService bsRedPacketService;
    @Autowired
    private BsRedPacketApplyService bsRedPacketApplyService;
    
    //红包查询
    public void findRedPacketInfoGrid(ReqMsg_RedPacket_FindRedPacketInfoGrid req,ResMsg_RedPacket_FindRedPacketInfoGrid res) {
        bsRedPackatInfoService.findRedPacketInfoGrid(req, res);
    }
    
    //查询红包发放管理数据
    public void grantManagermentGrid(ReqMsg_RedPacket_GrantManagermentGrid req, ResMsg_RedPacket_GrantManagermentGrid res) {
    	bsRedPacketService.grantManagermentGrid(req, res);
    }

    //自动红包停用
    @ClearRedisCache(clearKey = ConstantsForCache.CacheKey.HOME_REDPACKETAMOUNT)
    public void autoRedPocketDisable(ReqMsg_RedPacket_AutoRedPocketDisable req, ResMsg_RedPacket_AutoRedPocketDisable res) {
    	bsRedPacketService.autoRedPocketDisable(req, res);
    }
    
    //查看自动红包信息
    public void autoRedPocketReview(ReqMsg_RedPacket_AutoRedPocketReview req, ResMsg_RedPacket_AutoRedPocketReview res) {
    	bsRedPacketService.autoRedPocketReview(req, res);
    }
    
    //查看手动红包信息
    public void manualRedPocketReview(ReqMsg_RedPacket_ManualRedPocketReview req, ResMsg_RedPacket_ManualRedPocketReview res) {
    	bsRedPacketService.manualRedPocketReview(req, res);
    }
    
    
    //查询红包审核管理数据
    public void redPacketCheckGrid(ReqMsg_RedPacket_RedPacketCheckGrid req, ResMsg_RedPacket_RedPacketCheckGrid res) {
    	bsRedPacketService.redPacketCheckGrid(req, res);
    }
    public void budgetStat(ReqMsg_RedPacket_BudgetStat req, ResMsg_RedPacket_BudgetStat res){
    	RedPaktBudgetStatVO resVO = bsRedPacketApplyService.queryRedPaktBudgetStat();
    	res.setExpiryAmount(resVO.getExpiryAmount());
    	res.setTotalBudgetAmount(resVO.getTotalBudgetAmount());
    	res.setUnUsedRedPaktAmount(resVO.getUnUsedRedPaktAmount());
    	res.setUsedBudgetAmount(resVO.getUsedBudgetAmount());
    	res.setUsedRedPaktAmount(resVO.getUsedRedPaktAmount());
    	res.setBadloansZanAccBalance(resVO.getBadloansZanAccBalance());
    }
    
    //红包发放审核通过操作
    @ClearRedisCache(clearKey = ConstantsForCache.CacheKey.HOME_REDPACKETAMOUNT)
    public void redPacketCheckPass(ReqMsg_RedPacket_RedPacketCheckPass req, ResMsg_RedPacket_RedPacketCheckPass res) {
    	bsRedPacketService.redPacketCheckPass(req, res);
    }
    
    //红包发放审核通过操作
    public void redPacketCheckRefuse(ReqMsg_RedPacket_RedPacketCheckRefuse req, ResMsg_RedPacket_RedPacketCheckRefuse res) {
    	bsRedPacketService.redPacketCheckRefuse(req, res);
    }
    
    
    //红包数据统计
    public void redPacketStatistics(ReqMsg_RedPacket_RedPacketStatistics req, ResMsg_RedPacket_RedPacketStatistics res) {
    	bsRedPacketService.redPacketStatistics(req, res);
    }
    
    
    //获取最新的自动红包
    public void getNewAutoRedPacket(ReqMsg_RedPacket_GetNewAutoRedPacket req, ResMsg_RedPacket_GetNewAutoRedPacket res) {
    	bsRedPacketService.getNewAutoRedPacket(req, res);
    }
}
