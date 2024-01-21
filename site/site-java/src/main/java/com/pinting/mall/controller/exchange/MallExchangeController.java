package com.pinting.mall.controller.exchange;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchange_MallExchangeList;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchange_mallExchangeDetail;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchange_MallExchangeList;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchange_mallExchangeDetail;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 积分商城-商品兑换相关Controller
 *
 * @author shh
 * @date 2018/5/12 21:50
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallExchangeController extends BaseController {

    @Autowired
    private CommunicateBusiness siteMallService;

    /**
     * 我的兑换列表页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mallExchange/myExchangeList")
    public String myExchange(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                             Map<String,Object> model, ReqMsg_MallExchange_MallExchangeList req){

        String url = "";
        String userId = "";
        if(Constants.REQUEST_TERMINAL_H5.equals(channel)){
            CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            // userId加密
            String h5UserId = new DESUtil("cfgubijn").encryptStr(userId);
            model.put("h5UserId", h5UserId);
            // 钱报的参数
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                CookieManager managerQianbao = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    model.put("agentViewFlag", viewAgentFlagCookie);
                }
            }
            url = "mall/exchange/myexchange";
        }else if(Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
            userId = StringUtil.isNotBlank(request.getParameter("appUserId")) ? request.getParameter("appUserId") : null;
            String userIdStr = request.getParameter("userId");

            if(StringUtil.isBlank(userId)){
                userId = userIdStr;
            }
            // 加密后的userId回传给app页面
            model.put("userId", userId);

            if(StringUtil.isNotBlank(userId)){
                userIdStr = new DESUtil("cfgubijn").decryptStr(userId);
                userId = userIdStr;
            }
            String client = request.getParameter("client");
            model.put("client", client);

            url = "mall/exchange/myexchange_app";
        }else if(Constants.REQUEST_TERMINAL_PC.equals(channel)) {
        	CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
            url ="mallpc/exchange/my_exchange";
        }else if(Constants.REQUEST_TERMINAL_PC_AGENT.equals(channel)) {
        	CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                model.put("agentViewFlag", viewAgentFlagCookie);
            }
            url = "mallpc/agent/exchange/my_exchange";
        }

        try {
            if(StringUtil.isNotBlank(userId)) {
                req.setUserIdParam(Integer.valueOf(userId));
            }else {
                req.setUserIdParam(0);
            }
            req.setPageIndex(0);
            //判断是否是pc端，pc端的列表是一页6条
            if(Constants.REQUEST_TERMINAL_PC.equals(channel)||Constants.REQUEST_TERMINAL_PC_AGENT.equals(channel)) {
            	req.setPageSize(Constants.PAGE_SIZE_SIX);
            }else {
            	req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            }
            ResMsg_MallExchange_MallExchangeList res = (ResMsg_MallExchange_MallExchangeList) siteMallService.handleMallMsg(req);
            model.put("pageIndex", 1);
            model.put("totalCount", res.getTotalCount());
            model.put("exchangeList", res.getMallExchangeData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 我的兑换列表-加载更多
     * @param channel
     * @param pageIndex
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mallExchange/myExchangeList_content")
    public String tradingDetailContent(@PathVariable String channel, Integer pageIndex, ReqMsg_MallExchange_MallExchangeList req,
                                       HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> model) {

        String url = "";
        String userId = "";
        if(Constants.REQUEST_TERMINAL_H5.equals(channel)){
            CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            url = "mall/exchange/myexchange_content";
        }else if(Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
            userId = StringUtil.isNotBlank(request.getParameter("appUserId")) ? request.getParameter("appUserId") : null;
            String userIdStr = request.getParameter("userId");
            if(StringUtil.isBlank(userId)){
                userId = userIdStr;
            }
            // 加密后的userId回传给app页面
            model.put("userId", userId);

            if(StringUtil.isNotBlank(userId)){
                userIdStr = new DESUtil("cfgubijn").decryptStr(userId);
                userId = userIdStr;
            }
            String client = request.getParameter("client");
            model.put("client", client);
            url = "mall/exchange/myexchange_content_app";
        }else if(Constants.REQUEST_TERMINAL_PC.equals(channel)) {
        	CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
            url = "mallpc/exchange/my_exchange_content";
        }else if(Constants.REQUEST_TERMINAL_PC_AGENT.equals(channel)) {
        	CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                model.put("agentViewFlag", viewAgentFlagCookie);
            }
            url = "mallpc/agent/exchange/my_exchange_content";
        }

        try {
            if(StringUtil.isNotBlank(userId)) {
                req.setUserIdParam(Integer.valueOf(userId));
            }else {
                req.setUserIdParam(0);
            }
            req.setPageIndex(pageIndex-1);
          //判断是否是pc端，pc端的列表是一页6条
            if(Constants.REQUEST_TERMINAL_PC.equals(channel)||Constants.REQUEST_TERMINAL_PC_AGENT.equals(channel)) {
            	req.setPageSize(Constants.PAGE_SIZE_SIX);
            }else {
            	req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            }
            ResMsg_MallExchange_MallExchangeList resp = (ResMsg_MallExchange_MallExchangeList) siteMallService
                    .handleMallMsg(req);
            model.put("pageIndex", pageIndex);
            model.put("totalCount", resp.getTotalCount());
            model.put("exchangeList", resp.getMallExchangeData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 兑换详情页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param commId 商品编号
     * @param orderId 订单表id
     * @param commProperty 商品属性：虚拟、实物
     * @param req
     * @return
     */
    @RequestMapping("{channel}/mallExchange/myExchangeDetail")
    public String myExchangeDetail(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                   Map<String,Object> model, String commId, String orderId, String commProperty,
                                   ReqMsg_MallExchange_mallExchangeDetail req){

        String url = "";
        String userId = "";
        if(Constants.REQUEST_TERMINAL_H5.equals(channel)){
            CookieManager manager = new CookieManager(request);
            userId = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);

            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                CookieManager managerQianbao = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    model.put("agentViewFlag", viewAgentFlagCookie);
                }
            }

            String h5UserId = request.getParameter("h5UserId");
            // userId解密
            h5UserId = StringUtil.isNotBlank(h5UserId) ? new DESUtil("cfgubijn").decryptStr(h5UserId) : null;
            if(h5UserId.equals(userId)) {
                url = "mall/exchange/myexchange_index";
            }else {
                // 进入错误页
                url = "mall/exchange/myexchange_error_page";
            }

        }else if(Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
            userId = StringUtil.isNotBlank(request.getParameter("appUserId")) ? request.getParameter("appUserId") : null;
            String userIdStr = request.getParameter("userId");
            if(StringUtil.isBlank(userId)){
                userId = userIdStr;
            }
            // 加密后的userId回传给app页面
            model.put("userId", userId);

            if(StringUtil.isNotBlank(userId)){
                userIdStr = new DESUtil("cfgubijn").decryptStr(userId);
                userId = userIdStr;
            }
            String client = request.getParameter("client");
            model.put("client", client);
            url = "mall/exchange/myexchange_index_app";
        }
        if(StringUtil.isNotBlank(userId)) {
            req.setUserIdParam(Integer.valueOf(userId));
        }else {
            req.setUserIdParam(0);
        }
        if(StringUtil.isNotBlank(commId)) {
            req.setCommId(Integer.parseInt(commId));
        }else {
            req.setCommId(0);
        }
        if(StringUtil.isNotBlank(orderId)) {
            req.setOrderId(Integer.parseInt(orderId));
        }else {
            req.setOrderId(0);
        }
        if(StringUtil.isNotBlank(commProperty)) {
            req.setCommProperty(commProperty);
        }
        ResMsg_MallExchange_mallExchangeDetail resp = (ResMsg_MallExchange_mallExchangeDetail) siteMallService.handleMallMsg(req);
        model.put("res", resp);
        return url;
    }

}