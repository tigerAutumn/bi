package com.pinting.mall.controller.commodity;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.controller.MallBaseController;
import com.pinting.mall.enums.AgentViewFlagEnum;
import com.pinting.mall.hessian.site.message.ReqMsg_MallCommodity_GetDetail;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.hessian.site.message.ResMsg_MallCommodity_GetDetail;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.Token;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.BeanUtils;
import com.pinting.util.Constants;
import com.pinting.util.EditorUtil;
import com.pinting.util.Util;

/**
 * 积分商城-商品信息相关Controller
 *
 * @author shh
 * @date 2018/5/12 21:49
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallCommodityController extends MallBaseController {

    @Autowired
    private CommunicateBusiness mallCommodityService;

    /**
     * 商品详情页面
     *
     * @param channel
     * @param model
     * @return
     */
    @RequestMapping(value = "{channel}/mallCommodity/commodityDetail", method = RequestMethod.GET)
    public String commodityDetail(@PathVariable String channel, @RequestParam Integer commodityId, Map<String, Object> model, HttpServletRequest request) {
        //新查询商品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");

        // 商品详情信息
        ReqMsg_MallCommodity_GetDetail reqMsg = new ReqMsg_MallCommodity_GetDetail();
        reqMsg.setId(commodityId);
        ResMsg_MallCommodity_GetDetail resMsgCommodity = (ResMsg_MallCommodity_GetDetail) mallCommodityService.handleMallMsg(reqMsg);
        resMsgCommodity.setCommDetails(EditorUtil.replace(resMsgCommodity.getCommDetails(), resources, manageWeb, web));
        resMsgCommodity.setExchangeNote(EditorUtil.replace(resMsgCommodity.getExchangeNote(), resources, manageWeb, web));
        resMsgCommodity.setCommPictureUrl(web + resMsgCommodity.getCommPictureUrl());
        model.put("commodityDetail", resMsgCommodity);

        if (Constants.REQUEST_TERMINAL_H5.equals(channel)) {
            CookieManager manager = new CookieManager(request);
            String qianbao = request.getParameter("qianbao");//钱报标识
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            }
            String agentViewFlag = request.getParameter("agentViewFlag");//钱报标识
            if (StringUtils.isBlank(agentViewFlag)) {
                agentViewFlag = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            }
            model.put("agentViewFlag", agentViewFlag);
            return "mall/commodity/commodity_detail_h5";
        } else if (Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
            String userIdStr = request.getParameter("userId");
            // 加密后的userId回传给app页面
            model.put("userId", userIdStr);
            String client = request.getParameter("client");
            model.put("client", client);
            return "mall/commodity/commodity_detail_app";
        } else {
        	AgentViewFlagEnum agentViewFlagEnum = super.getAgentViewFlagEnum(request, channel, model);
            return agentViewFlagEnum.getPathPrefix() + "/commodity/commodity_detail";
        }
    }

    /**
     * 商品兑换弹出页面信息
     *
     * @param channel
     * @param request
     * @return
     */
    @RequestMapping(value = "{channel}/mallCommodity/exchangeCheck", method = RequestMethod.GET)
    @Token(save = true)
    @ResponseBody
    public Map<String, Object> exchangeCheck(@PathVariable String channel, @RequestParam Integer commodityId, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        // 用户信息
        Integer userId = getUserId(channel, request);
        if (userId == null) {
            errorRes(result);
            return result;
        }

        ReqMsg_User_InfoQuery reqMsgInfoQuery = new ReqMsg_User_InfoQuery();
        reqMsgInfoQuery.setUserId(userId);
        ResMsg_User_InfoQuery resMsgInfoQuery = (ResMsg_User_InfoQuery) mallCommodityService.handleMsg(reqMsgInfoQuery);
        if ("000000".equals(resMsgInfoQuery.getResCode()) && StringUtils.isNotBlank(resMsgInfoQuery.getMobile())) {
            String mobile = resMsgInfoQuery.getMobile();
            String userName = resMsgInfoQuery.getUserName();

            // 商品兑换前校验，如果是实体商品,返回用户收货地址
            ReqMsg_MallExchangeFlow_ExchangeCheck reqMsg = new ReqMsg_MallExchangeFlow_ExchangeCheck();
            reqMsg.setId(commodityId);
            reqMsg.setUserId(userId);
            ResMsg_MallExchangeFlow_ExchangeCheck resMsgCommodity = (ResMsg_MallExchangeFlow_ExchangeCheck) mallCommodityService.handleMallMsg(reqMsg);

            if ("000000".equals(resMsgCommodity.getResCode())) {
                if (resMsgCommodity.getAddrShow()) {
                    // 实体商品，没有收货地址,补充用户手机号,姓名
                    if (StringUtils.isBlank(resMsgCommodity.getRecMobile()) && StringUtils.isNotBlank(mobile)) {
                        resMsgCommodity.setRecMobile(mobile);
                    }
                    if (StringUtils.isBlank(resMsgCommodity.getRecName()) && StringUtils.isNotBlank(userName)) {
                        resMsgCommodity.setRecName(userName);
                    }
                } else {
                    // 虚拟商品，固定用户手机号
                    resMsgCommodity.setRecMobile(mobile);
                }
            }
            return BeanUtils.transBeanMap(resMsgCommodity);
        } else {
            return BeanUtils.transBeanMap(resMsgInfoQuery);
        }
    }

    /**
     * 商品兑换接口
     *
     * @param channel
     * @param request
     * @return
     */
    @RequestMapping(value = "{channel}/mallCommodity/exchangeCommodity")
    @ResponseBody
    public Map<String, Object> exchangeCommodity(@PathVariable String channel, ReqMsg_MallExchangeFlow_ExchangeCommodity reqMsg,
                                                 HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();

        Integer userId = getUserId(channel, request);
        if (userId == null) {
            errorRes(result);
            return result;
        }
        if(Constants.REQUEST_TERMINAL_H5.equals(channel) && Util.isRepeatSubmit(request, response)) {
            result.put("resCode", "999");
            result.put("resMsg","请勿重复提交订单！");
            return result;
        }

        reqMsg.setUserId(userId);
        ResMsg_MallExchangeFlow_ExchangeCommodity resMsgCommodity = (ResMsg_MallExchangeFlow_ExchangeCommodity) mallCommodityService.handleMallMsg(reqMsg);
        result = BeanUtils.transBeanMap(resMsgCommodity);
        return result;
    }

    /**
     * 商品兑换成功页
     *
     * @param channel
     * @param model
     * @return
     */
    @RequestMapping(value = "{channel}/mallCommodity/exchangeSuccPage", method = RequestMethod.GET)
    public String exchangeSuccPage(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {

        if (Constants.REQUEST_TERMINAL_H5.equals(channel)) {
            return "mall/commodity/exchange_succ_page_h5";
        } else if (Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
            String userIdStr = request.getParameter("userId");
            // 加密后的userId回传给app页面
            model.put("userId", userIdStr);
            String client = request.getParameter("client");
            model.put("client", client);
            return "mall/commodity/exchange_succ_page_app";
        } else {
        	AgentViewFlagEnum agentViewFlagEnum = super.getAgentViewFlagEnum(request, channel, model);
            return agentViewFlagEnum.getPathPrefix() + "/commodity/commodity_success";
        }
    }
}
