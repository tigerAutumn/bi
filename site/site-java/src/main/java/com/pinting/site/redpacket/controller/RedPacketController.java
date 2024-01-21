/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.redpacket.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.util.WeChatShareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

/**
 * 红包
 * @author HuanXiong
 * @version $Id: RedPacketController.java, v 0.1 2016-3-1 下午1:45:48 HuanXiong Exp $
 */
@Controller
public class RedPacketController {
    
    @Autowired
    private CommunicateBusiness communicateBusiness;
    @Autowired
    private WeChatUtil weChatUtil;
    
    
    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        Double a = 2.1d;
        hashMap.put("subtract", a);
        double subtract = (double)hashMap.get("subtract");
        int integer = (int)subtract;
        if(subtract == integer) {
            System.out.println("整数");
            hashMap.put("subtract", integer);
            System.out.println(hashMap.get("subtract"));
            System.out.println((String.valueOf(hashMap.get("subtract"))).length());
        } else {
            System.out.println("非整数");
            System.out.println(hashMap.get("subtract"));
            System.out.println((String.valueOf(hashMap.get("subtract"))).length());
        }
    }

    /**
     * 我的红包
     */
    @RequestMapping("/{channel}/redPacket/myRedPacket")
    public String myRedPacket(@PathVariable String channel, HttpServletRequest request,
                              Map<String, Object> model, Integer pageIndex) {
        // 分享相关
        String link = GlobEnv.getWebURL("/micro2.0/index");
        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);

        // 请求参数
        ReqMsg_RedPacketInfo_QueryRedPacketList req = new ReqMsg_RedPacketInfo_QueryRedPacketList();
        //加息券
        ReqMsg_Ticket_TicketList ticketReq = new ReqMsg_Ticket_TicketList();
        CookieManager cookie = new CookieManager(request);
        String userIdStr = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userIdStr));
        //从cookie中取出渠道id
        String pageFlag = cookie.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        model.put("pageFlag", pageFlag);

        //加息券
        ticketReq.setUserId(Integer.parseInt(userIdStr));
      

        if(!Constants.CHANNEL_MICRO.equals(channel)) {
            if(null == pageIndex) {
                req.setPageNum(1);
                //加息券
                ticketReq.setPageNum(1);
            }else {
                req.setPageNum(pageIndex);
              //加息券
                ticketReq.setPageNum(pageIndex);
            }
        }else {
            // h5分页还是原先逻辑
            req.setPageNum(1);
            //加息券
            ticketReq.setPageNum(1);
        }

        req.setNumPerPage(Constants.CHANNEL_MICRO.equals(channel) ? 10 : 6);
        //加息券
        ticketReq.setNumPerPage(Constants.CHANNEL_MICRO.equals(channel) ? 10 : 6);

        // 查询红包列表
        req.setStatus(Constants.RED_PACKET_STATUS_INIT);
        ResMsg_RedPacketInfo_QueryRedPacketList initRes = (ResMsg_RedPacketInfo_QueryRedPacketList) communicateBusiness.handleMsg(req);
        req.setStatus(Constants.RED_PACKET_STATUS_OVER);
        ResMsg_RedPacketInfo_QueryRedPacketList overRes = (ResMsg_RedPacketInfo_QueryRedPacketList) communicateBusiness.handleMsg(req);
        req.setStatus(Constants.RED_PACKET_STATUS_USED);
        ResMsg_RedPacketInfo_QueryRedPacketList usedRes = (ResMsg_RedPacketInfo_QueryRedPacketList) communicateBusiness.handleMsg(req);
        
        // 查询加息券列表
        ticketReq.setBizType("TICKET");
        ticketReq.setType("INTEREST_TICKET");
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_INIT);
        ResMsg_Ticket_TicketList initTicketRes = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(ticketReq);
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_OVER);
        ResMsg_Ticket_TicketList overTicketRes = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(ticketReq);
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_USED);
        ResMsg_Ticket_TicketList usedTicketRes = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(ticketReq);

        model.put("initResult", initRes);
        model.put("overResult", overRes);
        model.put("usedResult", usedRes);
        model.put("initTotalPages", (int) Math.ceil((double) initRes.getCount() / req.getNumPerPage()));
        model.put("overTotalPages", (int) Math.ceil((double) overRes.getCount() / req.getNumPerPage()));
        model.put("usedTotalPages", (int) Math.ceil((double) usedRes.getCount() / req.getNumPerPage()));
        
        //加息券
        model.put("initTicketResult", initTicketRes);
        model.put("overTicketResult", overTicketRes);
        model.put("usedTicketResult", usedTicketRes);
        model.put("initTicketTotalPages", (int) Math.ceil((double) initTicketRes.getCount() / ticketReq.getNumPerPage()));
        model.put("overTicketTotalPages", (int) Math.ceil((double) overTicketRes.getCount() / ticketReq.getNumPerPage()));
        model.put("usedTicketTotalPages", (int) Math.ceil((double) usedTicketRes.getCount() / ticketReq.getNumPerPage()));

        if(!Constants.CHANNEL_MICRO.equals(channel)) {
            model.put("initPageNum", req.getPageNum());
            model.put("overPageNum", req.getPageNum());
            model.put("usedPageNum", req.getPageNum());
            //加息券
            model.put("initTicketPageNum", ticketReq.getPageNum());
            model.put("overTicketPageNum", ticketReq.getPageNum());
            model.put("usedTicketPageNum", ticketReq.getPageNum());
        }else {
            // h5分页还是原先逻辑
            model.put("initPageNum", 1);
            model.put("overPageNum", 1);
            model.put("usedPageNum", 1);
            //加息券
            model.put("initTicketPageNum", 1);
            model.put("overTicketPageNum", 1);
            model.put("usedTicketPageNum", 1);
        }
        Integer initcount = initRes.getCount() == null ? 0 : initRes.getCount();
        Integer initTicketCount = initTicketRes.getCount() == null ? 0 : initTicketRes.getCount();
        model.put("initCount", initcount+initTicketCount);
        String url = Constants.CHANNEL_MICRO.equals(channel) ? channel + "/red_pack/my_red_packet" : channel + "/assets/my_red_packet";
        return url;
    }
    
    /**
     * 已使用红包   已过期红包  已使用加息券  已过期加息券
     */
    @RequestMapping("/{channel}/redPacket/otherCoupons")
    public String usedRedPacket(@PathVariable String channel, HttpServletRequest request,
                              Map<String, Object> model, Integer pageIndex) {
        // 分享相关
        String link = GlobEnv.getWebURL("/micro2.0/index");
        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);

        String status = request.getParameter("status");
        String type = request.getParameter("type");
        
        ReqMsg_Ticket_TicketList ticketReq = new ReqMsg_Ticket_TicketList();
        CookieManager cookie = new CookieManager(request);
        String userIdStr = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ticketReq.setUserId(Integer.parseInt(userIdStr));
		ticketReq.setPageNum(1);
		ticketReq.setNumPerPage(10);
		ticketReq.setStatus(status);
		ticketReq.setType(type);
		ticketReq.setBizType("TICKET");
		
		ResMsg_Ticket_TicketList res = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(ticketReq);
	    model.put("result", res);
	    model.put("totalPages", (int) Math.ceil((double) res.getCount() / ticketReq.getNumPerPage()));
	    model.put("pageNum", 1);
		
	    String url = null;
	    if (Constants.TICKET_INTEREST_TYPE_RED_PACKET.equals(type)) {
			if (Constants.RED_PACKET_STATUS_OVER.equals(status)) {
				url = channel + "/red_pack/over_red_packet";
			} else if (Constants.RED_PACKET_STATUS_USED.equals(status)) {
				url = channel + "/red_pack/used_red_packet";
			}
		} else if (Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(type)) {
			if (Constants.RED_PACKET_STATUS_OVER.equals(status)) {
				url = channel + "/red_pack/over_ticket";
			} else if (Constants.RED_PACKET_STATUS_USED.equals(status)) {
				url = channel + "/red_pack/used_ticket";
			}
		}
        return url;
    }
    
    /**
     * 我的红包
     */
    @RequestMapping("/{channel}/redPacket/myRedPacketPage")
    public String myRedPacketPage(@PathVariable String channel, ReqMsg_Ticket_TicketList req,
                                  HttpServletRequest request, Map<String, Object> model,
                                  Integer pageIndex) {

        // 请求参数
        CookieManager cookie = new CookieManager(request);
        String userIdStr = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userIdStr));
        req.setNumPerPage(Constants.CHANNEL_MICRO.equals(channel) ? 10 : 6);
        // h5分页还是原先逻辑
        if(!Constants.CHANNEL_MICRO.equals(channel)) {
            req.setPageNum(pageIndex);
            model.put("pageIndex", req.getPageNum());
        }
        // 查询红包列表
        req.setBizType("TICKET");
        ResMsg_Ticket_TicketList res = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(req);
        model.put("res", res);
    	model.put("req", req);
        model.put("totalPages", (int) Math.ceil((double) res.getCount() / req.getNumPerPage()));
        String url = null;
        if (Constants.TICKET_INTEREST_TYPE_RED_PACKET.equals(req.getType())) {
			url = Constants.CHANNEL_MICRO.equals(channel) ? channel + "/red_pack/my_red_packet_page" : channel + "/assets/my_red_packet_page";
		} else {
			url = Constants.CHANNEL_MICRO.equals(channel) ? channel + "/red_pack/my_increase_page" : channel + "/assets/my_increase_page";
		}
        return url;
    }
    
    /**
     * 购买选择红包列表
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/{channel}/redPacket/chooseRedPacketList")
    public String chooseRedPacketList(@PathVariable String channel, ReqMsg_Ticket_TicketList ticketReq, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        // 分享相关
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);

        CookieManager cookie = new CookieManager(request);
        String userIdStr = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        ticketReq.setBizType(Constants.TICKET_INTEREST_BIZ_TYPE_BUY);
        ticketReq.setUserId(Integer.parseInt(userIdStr));
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_INIT);
        ticketReq.setType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
        // 加息券
        ResMsg_Ticket_TicketList ticketRes = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(ticketReq);
        model.put("ticketList", ticketRes.getDataList());
        model.put("ticketCount", ticketRes.getCount());
        model.put("isSupportInterestTicket", ticketRes.getIsSupport());

        // 红包
        ticketReq.setType(Constants.TICKET_INTEREST_TYPE_RED_PACKET);
        ResMsg_Ticket_TicketList redRes = (ResMsg_Ticket_TicketList) communicateBusiness.handleMsg(ticketReq);
        model.put("redPacketList", redRes.getDataList());
        model.put("redCount", redRes.getCount());
        model.put("isSupportRedPacket", redRes.getIsSupport());


        // 购买页面需要传入金额做判断，还要保存相应的数据
        model.put("amount", ticketReq.getAmount());
        model.put("id", request.getParameter("id"));
        model.put("name", request.getParameter("name"));
        model.put("rate", request.getParameter("rate"));
        model.put("term", request.getParameter("term"));
        model.put("minInvestAmount", request.getParameter("minInvestAmount"));
        model.put("bankName", request.getParameter("bankName"));
        model.put("cardNo", request.getParameter("cardNo"));
        model.put("bankId", request.getParameter("bankId"));
        model.put("isFirst", request.getParameter("isFirst"));
        model.put("oneTop", request.getParameter("oneTop"));
        model.put("dayTop", request.getParameter("dayTop"));
        model.put("accountId", request.getParameter("accountId"));
        model.put("balance", request.getParameter("balance"));
        model.put("regMobile", request.getParameter("regMobile"));
        model.put("alreadyUse", request.getParameter("alreadyUse"));
        model.put("qianbao", request.getParameter("qianbao"));
        model.put("dailyNotice", request.getParameter("dailyNotice"));
        model.put("redPacketId", request.getParameter("redPacketId"));
        model.put("isBank", request.getParameter("isBank"));
        model.put("from", request.getParameter("from"));
        model.put("backUrl", request.getParameter("backUrl"));
        model.put("useFlag", StringUtil.isEmpty(request.getParameter("useFlag"))? "yes": request.getParameter("useFlag"));
        return channel + "/red_pack/choose_red_packet_list";
    }
    
}
