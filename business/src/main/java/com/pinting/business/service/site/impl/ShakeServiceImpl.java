/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsActivity318Mapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_Shake_GetWinUserNumber;
import com.pinting.business.hessian.site.message.ResMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_Shake_GetWinUserNumber;
import com.pinting.business.model.AutoRedPacketParams;
import com.pinting.business.model.BsActivity318;
import com.pinting.business.model.BsActivity318Example;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserExample;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.ShakeService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: ShakeServiceImpl.java, v 0.1 2016-3-10 下午8:14:13 HuanXiong Exp $
 */
@Service
public class ShakeServiceImpl implements ShakeService {

    @Autowired
    private BsActivity318Mapper        bsActivity318Mapper;
    @Autowired
    private BsUserMapper               bsUserMapper;
    @Autowired
    private RedPacketService           redPacketService;
    @Autowired
    private BsRedPacketInfoMapper      bsRedPacketInfoMapper;
    @Autowired
    private BsProductMapper bsProductMapper;

    @Override
    public void drawRedPacket(ReqMsg_Shake_DrawRedPacket req, ResMsg_Shake_DrawRedPacket res) {
        
        if("finance_day_518".equals(req.getActivityFlag())) {
            // 518“我要发”理财活动 业务代码
            financeDay518DrawRedPacket(req.getAmount(), req.getTerm(), req.getUserId());
        } else {
            // 母亲节活动
            motherDayDrawRedPacket(req.getSerialNo(), req.getUserId());
        }
        // 318摇一摇活动
        // shakeDrawRedPacket(req, res);
    }

    /**
     * 518“我要发”理财活动领取红包
     * @param amount
     * @param term
     * @param userId
     */
    private void financeDay518DrawRedPacket(Double amount, Integer term, Integer userId) {
        redPacketService.financeDay518ActivityAutoRedPacketSend(amount, term, userId);
    }

    /**
     * 母亲节领红包活动
     * @param serialNo
     * @param userId
     */
    private void motherDayDrawRedPacket(String serialNo, Integer userId) {
        // 领取红包
        redPacketService.motherDayActivityAutoRedPacketSend(serialNo, userId);
    }

    /** 
     * 318摇一摇活动
     * 1、检查本表无数据，bs_user表中无数据，则为新用户，可以领；
     * 2、检查本表无数据，bs_user表中有数据，则为老用户，可以领；
     * 3、检查本表有数据，bs_user表中无数据，则为新用户，可以领；
     * 4、检查本表有数据，bs_user表中有数据，不可领
     * @see com.pinting.business.service.site.ShakeService#drawRedPacket(com.pinting.business.hessian.site.message.ReqMsg_Shake_DrawRedPacket, com.pinting.business.hessian.site.message.ResMsg_Shake_DrawRedPacket)
     */
    @Deprecated
    private void shakeDrawRedPacket(ReqMsg_Shake_DrawRedPacket req, ResMsg_Shake_DrawRedPacket res) {
        // 318表
        BsActivity318Example bsActivity318Example = new BsActivity318Example();
        bsActivity318Example.createCriteria().andMobileEqualTo(req.getMobile());
        List<BsActivity318> activity318s = bsActivity318Mapper
            .selectByExample(bsActivity318Example);
        // 用户
        BsUserExample bsUserExample = new BsUserExample();
        bsUserExample.createCriteria().andMobileEqualTo(req.getMobile());
        List<BsUser> bsUsers = bsUserMapper.selectByExample(bsUserExample);

        // 1、检查本表无数据，bs_user表中无数据，则为新用户，可以领；
        if (CollectionUtils.isEmpty(activity318s) && CollectionUtils.isEmpty(bsUsers)) {
            // 插入本表数据，跳入注册页
            BsActivity318 activity318 = new BsActivity318();
            activity318.setAgentId(Constants.SHAKE_AGENT_ID);
            activity318.setCreateType(new Date());
            activity318.setMobile(req.getMobile());
            activity318.setUserType(Constants.NEW_USER);
            bsActivity318Mapper.insertSelective(activity318);
            res.setIsNewUser(true);
        }
        // 2、检查本表无数据，bs_user表中有数据，则为老用户，可以领；
        if (CollectionUtils.isEmpty(activity318s) && !CollectionUtils.isEmpty(bsUsers)) {
            res.setIsNewUser(false);
            AutoRedPacketParams params = new AutoRedPacketParams();
            params.setUserId(bsUsers.get(0).getId());
            params.setTriggerType(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER);
            List<Integer> redPacketInfoIds = redPacketService.autoRedPacketSend(params);
            if (!CollectionUtils.isEmpty(redPacketInfoIds)) {
                // 插入本表数据，领取红包，并跳入我的红包页面
                BsActivity318 activity318 = new BsActivity318();
                activity318.setAgentId(bsUsers.get(0).getAgentId());
                activity318.setCreateType(new Date());
                activity318.setMobile(req.getMobile());
                activity318.setUserType(Constants.OLD_USER);
                bsActivity318Mapper.insertSelective(activity318);
            } else {
                throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
            }
        }
        // 3、检查本表有数据，bs_user表中无数据，则为新用户
        if (!CollectionUtils.isEmpty(activity318s) && CollectionUtils.isEmpty(bsUsers)) {
            // 进入注册页面，注册触发领取红包。（算是渠道用户注册）
            res.setIsNewUser(true);
            throw new PTMessageException(PTMessageEnum.RED_PACKET_NEW_USER_NOT_ELIGIBLE);
        }
        // 4、检查本表有数据，bs_user表中有数据，不可领
        if (!CollectionUtils.isEmpty(activity318s) && !CollectionUtils.isEmpty(bsUsers)) {
            res.setIsNewUser(false);
            throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE);
        }
    }

    /** 
     * @see com.pinting.business.service.site.ShakeService#getWinUserNumber(com.pinting.business.hessian.site.message.ReqMsg_Shake_GetWinUserNumber, com.pinting.business.hessian.site.message.ResMsg_Shake_GetWinUserNumber)
     */
    @Override
    public void getWinUserNumber(ReqMsg_Shake_GetWinUserNumber req,
                                 ResMsg_Shake_GetWinUserNumber res) {
        if("activity_528".equals(req.getActivityFlag())) {
            activity528(req, res);
        } else if("finance_day_518".equals(req.getActivityFlag())) {
            // 518“我要发”理财活动 业务代码
            financeDay518ChanceCount(req, res);
        } else {
            // 母亲节活动 业务代码 
            mothdayDayChanceCount(req, res);
        }
        
        // ====================================== 318活动 业务代码  开始======================================
        /*BsActivity318Example activity318Example = new BsActivity318Example();
        int winUserNumber = bsActivity318Mapper.countByExample(activity318Example);
        res.setCount(winUserNumber * 11 + 318);*/
        // ====================================== 318活动 业务代码  结束======================================
    }

    /**
     * 528活动
     * @param req
     * @param res
     */
    private void activity528(ReqMsg_Shake_GetWinUserNumber req, ResMsg_Shake_GetWinUserNumber res) {
        BsProduct product = bsProductMapper.selectNewUserProduct(req.getTerminal());
        res.setProduct(BeanUtils.classToHashMap(product));
    }

    /**
     * 518“我要发”理财活动
     * @param req
     * @param res
     */
    private void financeDay518ChanceCount(ReqMsg_Shake_GetWinUserNumber req, 
                               ResMsg_Shake_GetWinUserNumber res) {
        // 活动是否截止
        Date startTime;
        Date endTime;
        Date now = new Date();
        try {
            startTime = DateUtil.parse(Constants.FINANCE_DAY_518_ACTIVITY_START_TIME, "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtil.parse(Constants.FINANCE_DAY_518_ACTIVITY_END_TIME, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            throw new PTMessageException(PTMessageEnum.DATE_TRANS_ERROR);
        }
        if(DateUtil.compareTo(now, startTime) < 0 || DateUtil.compareTo(now, endTime) >= 0) {    // 活动还未开始 // 活动已经结束
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        if(null == req.getUserId()) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        int redPacketInfoCount = bsRedPacketInfoMapper.countByRedPacketMotherDay(req.getUserId());
        res.setChanceCount(Constants.FINANCE_DAY_518_ACTIVITY_DRAW_CHANCE - redPacketInfoCount);
    }

    /**
     * 母亲节活动 业务代码
     * @param req
     * @param res
     */
    private void mothdayDayChanceCount(ReqMsg_Shake_GetWinUserNumber req,
                                       ResMsg_Shake_GetWinUserNumber res) {
        // 活动是否截止
        Date startTime;
        Date endTime;
        Date now = new Date();
        try {
            startTime = DateUtil.parse(Constants.MOTHER_DAY_ACTIVITY_START_TIME, "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtil.parse(Constants.MOTHER_DAY_ACTIVITY_END_TIME, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            throw new PTMessageException(PTMessageEnum.DATE_TRANS_ERROR);
        }
        if(DateUtil.compareTo(now, startTime) < 0) {    // 活动还未开始 
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        }
        if(DateUtil.compareTo(now, endTime) >= 0) {    // 活动已经结束
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        // 查询该用户在所有当前活动批次的红包表中是否有三条数据
        if(null == req.getUserId()) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // 母亲节活动总共5次机会，718活动改为每天拥有3次机会
        int redPacketInfoCount = bsRedPacketInfoMapper.countByRedPacketMotherDay(req.getUserId());
        res.setChanceCount(Constants.MOTHER_DRAW_CHANCE - redPacketInfoCount);
    }
    
    
}
