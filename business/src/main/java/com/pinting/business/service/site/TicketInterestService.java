package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ReqMsg_Ticket_TicketList;
import com.pinting.business.hessian.site.message.ResMsg_Ticket_TicketList;

import java.util.Date;

/**
 * Created by cyb on 2018/3/30.
 */
public interface TicketInterestService {

    /**
     * 查询加息券列表
     * @param req
     * @param res
     */
    void ticketInterestList(ReqMsg_Ticket_TicketList req, ResMsg_Ticket_TicketList res);

    /**
     * 用户生日触发自动加息券发放规则
     * @param birthday 生日日期
     */
    boolean happyBirthday(String birthday, Integer numPerPage);

    /**
     * 获得加息券总数
     * @param userId
     * @param status
     * @return
     */
    public Integer getInterestTicketNum(Integer userId, String status);
    
    /**
     * 给指定用户发积分商城兑换到的加息券
     * @author bianyatian
     * @param userId
     * @param ticketName
     * @param orderSuccTime
     */
    boolean sendMallTicketByName(Integer userId, String ticketName, Date orderSuccTime);

	    
    /**
     * 微信小程序给指定用户发放指定名称的自动加息券
     * @param userId        用户ID
     * @param serialName    加息券名称名称
     */
    void weChatAutoTicketSendByName(Integer userId, String serialName);


}