/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.AutoRedPacketParams;
import com.pinting.business.model.vo.RedPacketInfoVO;

/**
 * 
 * @author HuanXiong
 * @version $Id: RedPacketService.java, v 0.1 2016-3-1 下午12:56:26 HuanXiong Exp $
 */
public interface RedPacketService {

    /**
     * 我的红包-购买列表
     * @param req
     * @param res
     */
    public void redPacketList(ReqMsg_RedPacketInfo_RedPacketList req, ResMsg_RedPacketInfo_RedPacketList res);

    /**
     * 新版本我的红包-20170316
     * @param req
     * @param res
     */
    void queryRedPacketList(ReqMsg_RedPacketInfo_QueryRedPacketList req, ResMsg_RedPacketInfo_QueryRedPacketList res);

    /**
     * 获得红包总数
     * @param userId
     * @param status
     * @return
     */
    public Integer getRedPacketNum(Integer userId, String status);
    
    /**
     * 选择红包返回相关信息
     * @param req
     * @param res
     */
    public void findById(ReqMsg_RedPacketInfo_ChooseRedPacket req,
                         ResMsg_RedPacketInfo_ChooseRedPacket res);
    
    /**
     * 根据用户编号，获取用户注册时获得的红包列表
     * @param userId
     * @return 无则返回null
     */
    public List<RedPacketInfoVO> getUserRegistRedPakt(Integer userId);
    
    /**
     * PC：获得满足的红包
     * @param req   amount status userId
     * @param res
     */
    public void getRedPacket(ReqMsg_RedPacketInfo_GetRedPacket req, ResMsg_RedPacketInfo_GetRedPacket res);
    
    /**
     * 
     * @Title: awardBonus 
     * @Description: 购买是发放自动满额红包
     * @throws
     */
    public void awardBonus(Double amount, Integer userId);
    
    /**
     * 
     * @Title: autoRedPacketSend 
     * @param  自动红包参数类  params  (用户编号:userId   触发条件类型:triggerType  购买金额:amount)
     * @param  注册红包必填参数（userId,triggerType)
     * @param  推荐满额红包必填参数(userId,triggerType)
     * @param  购买满额红包必填参数(userId,triggerType,amount)
     * @param  318老用户规则必填参数(userId,triggerType)
     * @return List<Integer> 红包Id列表
     * @Description: 自动发放红包    
     * @throws
     */
    public List<Integer> autoRedPacketSend(AutoRedPacketParams params);
    
    /**
     * 自动红包可领取金额
     * @param triggerType   触发类型（318_GAME_OLD_USER|BUY_FULL|INVITE_FULL|NEW_USER）
     * @param showTerminal  显示端口
     * @return
     */
    public Double autoRedPacketTotalAmount(String triggerType, String showTerminal);
    
    /**
     * 母亲节活动自动红包发放
     * @param serialNo  审核批次号
     * @param userId    用户ID
     */
    public void motherDayActivityAutoRedPacketSend(String serialNo, Integer userId);

    /**
     * 518活动自动红包发放
     * @param amount    触发金额
     * @param term  触发期限
     * @param userId    用户ID
     */
    public void financeDay518ActivityAutoRedPacketSend(Double amount, Integer term, Integer userId);

    /**
     * 双十一活动红包发放
     * @param userId    用户ID
     * @param redPacketName 红包名称
     */
    public void double11ActivityAutoRedPacketSend(Integer userId, String redPacketName);

    /**
     * 给指定用户发放指定批次号的活动自动红包
     * @param userId		用户ID
     * @param serialNo  	红包批次号
     */
    void autoRedPacketSendBySeriaNo(Integer userId, String serialNo);
    
    
    /**
     * 获取用户某日使用的红包金额
     * @param userId
     * @param date 日期
     * @return
     */
    public Double sumAmountUsedRedPacket(Integer userId,String date);

    /**
     * 给指定用户发放指定名称的自动红包
     * @param userId        用户ID
     * @param serialName    红包名称
     */
    void autoRedPacketSendByName(Integer userId, String serialName);

    /**
     * 微信小程序给指定用户发放指定名称的自动红包
     * @param userId        用户ID
     * @param serialName    红包名称
     */
    void weChatAutoRedPacketSendByName(Integer userId, String serialName);
    
    /**
     * 根据策略发红包
     * @param userId
     * @param policyType
     */
    void autoRedPacketPolicyType(Integer userId, String policyType);
    
    /**
     * 给指定用户发积分商城兑换到的红包
     * @author bianyatian
     * @param userId
     * @param orderSuccTime 订单成功时间-用于判断是否在发放期内
     * @param serialName
     */
    boolean sendMallRedPacketByName(Integer userId, String serialName, Date orderSuccTime);
    
    /**
     * 用户生日触发自动红包发放规则
     * @param birthday
     */
    void happyBirthday(String birthday);

}
