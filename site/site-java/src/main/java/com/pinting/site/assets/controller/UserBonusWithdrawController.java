package com.pinting.site.assets.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.WeChatShareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/4/14
 * Description: 奖励金提现
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/{channel}/bonus/")
public class UserBonusWithdrawController {

    @Autowired
    private CommunicateBusiness communicateBusiness;

    @Autowired
    private WeChatUtil weChatUtil;

    /**
     * 奖励金提现
     * @param channel
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/withdraw/index", method = RequestMethod.GET)
    public String bonusWithdrawIndex(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        // 2. 拦截未绑卡（恒丰绑卡用户）
        ReqMsg_DepGuide_FindDepGuideInfo depReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depReq.setUserId(Integer.parseInt(userId));
        depReq.setContainRisk(true);
        ResMsg_DepGuide_FindDepGuideInfo depRes =  (ResMsg_DepGuide_FindDepGuideInfo) communicateBusiness.handleMsg(depReq);
        if(depRes.getIsOpened().equals("WAIT_ACTIVATE") || "OPEN".equals(depRes.getIsOpened())) {
        } else {
            return "redirect:/" + channel + "/bankcard/index";
        }
        String qianbao = request.getParameter("qianbao");
        if(Constants.is_expire.equals(depRes.getRiskStatus())
                || Constants.is_no.equals(depRes.getRiskStatus())) {
            // 需要进行风险测评
        	if(Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
        		return "redirect:/micro2.0/assets/questionnaireMore?entry=bonus_withdraw&qianbao=qianbao";
        	} else {        		
        		return "redirect:/micro2.0/assets/questionnaireMore?entry=bonus_withdraw";
        	}
        }

        // 3. 分享
        String link = GlobEnv.getWebURL("/micro2.0/index");
        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
        // 4. 奖励金提现页面信息
        ReqMsg_Bonus_WithdrawPageInfo reqMsg = new ReqMsg_Bonus_WithdrawPageInfo();
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_Bonus_WithdrawPageInfo resp = (ResMsg_Bonus_WithdrawPageInfo) communicateBusiness.handleMsg(reqMsg);
        String cardNo = resp.getCardNo();
        model.put("bankLogo", resp.getSmallLogo());
        model.put("bankName", resp.getBankName());
        model.put("cardId", resp.getCardId());
        if (StringUtil.isNotBlank(cardNo)) {
            model.put("cardNo", cardNo.substring(cardNo.length() - 4, cardNo.length()));
            model.put("bankId", resp.getBankId());
        }
        model.put("canWithdraw", resp.getCan_withdraw());
        model.put("withdrawLimit", resp.getWithdrawLimit());
        model.put("from", request.getParameter("from"));

        if(!Constants.CHANNEL_MICRO.equals(channel)) {
            ReqMsg_User_InfoQuery userInfoReq = new ReqMsg_User_InfoQuery();
            userInfoReq.setUserId(Integer.parseInt(userId));
            ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery) communicateBusiness.handleMsg(userInfoReq);
            //判断是否存在支付密码 1:有交易密码；2：无交易密码
            model.put("payPasswordExist", Constants.USER_PAY_PASSWORD_EXIST_NO.equals(userInfoRes.getExcitPaypassword()) ? "FALSE" : "TRUE");
        }
        return channel + "/bonus/withdraw/withdraw_reward";
    }

    /**
     * 奖励金提现
     * @param channel
     * @return
     */
    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String bonusWithdraw(@PathVariable String channel, HttpServletRequest request, ReqMsg_Bonus_Withdraw req, Map<String, Object> result) {
        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        req.setUserId(Integer.parseInt(userId));
        req.setTerminalType(Constants.CHANNEL_MICRO.equals(channel) ? 1 : 2);
        ResMsg_Bonus_Withdraw res = (ResMsg_Bonus_Withdraw) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("time", DateUtil.formatDateTime(DateUtil.addDays(new Date(), 1), "yyyy年MM月dd日 HH:mm"));
            if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
                return channel + "/assets/bonus/withdraw_succ";
            }
            return channel + "/bonus/withdraw/withdraw_succ";
        } else {
            result.put("resMsg", res.getResMsg());
            if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
                return channel + "/assets/bonus/withdraw_error";
            }
            return "micro2.0/bonus/withdraw/withdraw_error";
        }
    }
}
