package com.pinting.site.assets.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.WeChatShareUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * Author:      cyb
 * Date:        2016/8/27
 * Description: 绑卡Controller
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/{channel}/bankcard/")
public class BindCardController extends BaseController {

    @Autowired
    private CommunicateBusiness communicateBusiness;

    @Autowired
    private WeChatUtil weChatUtil;

    // 我的银行卡
    // 绑卡页面
    // 预绑卡
    // 正式绑卡
    // 银行列表
    // 是否绑卡

    /**
     * 我的银行卡
     * @param channel   访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/self", method = RequestMethod.GET)
    public String selfBankCard(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        // 2、H5分享
        share(channel, model, request);

        // 3、发起请求，获得本人绑卡信息
        ReqMsg_User_BankListQuery reqMsg = new ReqMsg_User_BankListQuery();
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_BankListQuery resMsg = (ResMsg_User_BankListQuery) communicateBusiness.handleMsg(reqMsg);
        model.put("isBinding", resMsg.getIsBinding());
        model.put("bankcards", resMsg.getBankCards());
        model.put("size", resMsg.getBankCards() == null ? 0 : resMsg.getBankCards().size());

        return channel + "/assets/bankcard/bankcard_self";
    }

    /**
     * 进入绑卡界面
     * @param channel   访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
     * @param entry     入口：self-我的银行卡；top_up-充值；withdraw-提现
     * @param userName  用户姓名
     * @param idCard    身份证
     * @param cardNo    卡号
     * @param bankId    银行ID
     * @param mobile    手机号
     * @param smsCode   验证码
     * @param orderNo   订单号
     * @param bankName  银行名
     * @param oneTop    单笔限额
     * @param dayTop    单日限额
     * @param notice    提示信息
     * @param productId 产品Id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "index")
    public String bindCardIndex(@PathVariable String channel, String entry, String userName, String idCard,
                                String cardNo, String bankId, String mobile, String smsCode, String orderNo,
                                String bankName, String oneTop, String dayTop, String notice, String productId,
                                String amount, HttpServletRequest request, Map<String, Object> model) {
        share(channel, model, request);

        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_IsBindCard isBindCardReq = new ReqMsg_User_IsBindCard();
        isBindCardReq.setUserId(userId);

        ReqMsg_DepGuide_FindDepGuideInfo depReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depReq.setUserId(Integer.parseInt(userId));
        ResMsg_DepGuide_FindDepGuideInfo depRes =  (ResMsg_DepGuide_FindDepGuideInfo) communicateBusiness.handleMsg(depReq);
        if(depRes.getIsOpened().equals("WAIT_ACTIVATE") || "OPEN".equals(depRes.getIsOpened())) {
            if(Constants.CHANNEL_MICRO.equals(channel)) {
                return "redirect:/" + channel + "/bankcard/self";
            } else {
                return "redirect:/" + channel + "/assets/assets?selfbank=selfbank";
            }
        } else {
            // 未绑卡
            // 查询宝付支持快捷支付的银行
            ReqMsg_Bank_ChannelBank req = new ReqMsg_Bank_ChannelBank();
            req.setPayChannel(Constants.CHANNEL_HF);
            req.setPayType(Constants.PAY_TYPE_QUICK);
            ResMsg_Bank_ChannelBank res = (ResMsg_Bank_ChannelBank) communicateBusiness.handleMsg(req);
            if(Constants.CHANNEL_MICRO.equals(channel)) {
                JSONArray bankList = JSONArray.fromObject(res.getBankList());
                model.put("bankList", bankList);
            } else {
                model.put("bankList", res.getBankList());
            }
            model.put("entry", entry);
            model.put("userName", userName);
            model.put("idCard", idCard);
            model.put("cardNo", cardNo);
            model.put("bankId", bankId);
            model.put("mobile", mobile);
            model.put("smsCode", smsCode);
            model.put("orderNo", orderNo);
            model.put("bankName", bankName);
            model.put("oneTop", oneTop);
            model.put("dayTop", dayTop);
            model.put("notice", notice);
            model.put("productId", productId);
            model.put("amount", amount);

            //qianbao178绑卡交易H5
            String from = StringUtil.isBlank(request.getParameter("from")) ? (String) request.getAttribute("from") : request.getParameter("from");
            if(Constants.FROM_178_APP.equals(from)) {
				String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(),
	                    CookieEnums._SITE_178_BACK_URL.getName(), true);
				model.put("backUrl", backUrl);
                model.put("from", from);
            	return "/qianbao178/app/bankcard/bind_card_index";
            }
            return channel + "/assets/bankcard/bind_card_index";
        }
    }

    /**
     * 预绑卡
     * @param channel
     * @param req
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/pre")
    public Map<String, Object> preBindCard(@PathVariable String channel, ReqMsg_BaoFooBank_PreBindCard req, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 0、数据去空格
        req.setBankId(StringUtil.trimStr(req.getBankId()));
        req.setCardNo(StringUtil.trimStr(req.getCardNo()));
        req.setIdCard(StringUtil.trimStr(req.getIdCard()));
        req.setMobile(StringUtil.trimStr(req.getMobile()));
        req.setUserName(StringUtil.trimStr(req.getUserName()));

        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(userId);
        req.setTerminalType(Constants.CHANNEL_MICRO.equals(channel) ? String.valueOf(Constants.PAY_ORDER_TERMINAL_H5) : String.valueOf(Constants.PAY_ORDER_TERMINAL_PC));

        // 2、发送预绑卡请求
        ResMsg_BaoFooBank_PreBindCard res = (ResMsg_BaoFooBank_PreBindCard) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("orderNo", res.getOrderNo());
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
        } else {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
        }
        return result;
    }

    /**
     * 正式绑卡
     * @param channel
     * @param req
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bind")
    public Map<String, Object> bindCard(@PathVariable String channel, ReqMsg_BaoFooBank_BindCard req, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 0、数据去空格
        req.setOrderNo(StringUtil.trimStr(req.getOrderNo()));
        req.setSmsCode(StringUtil.trimStr(req.getSmsCode()));

        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(userId);

        // 2、发送正式绑卡请求
        ResMsg_BaoFooBank_BindCard res = (ResMsg_BaoFooBank_BindCard) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
        } else {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
        }
        return result;
    }

    /**
     * 宝付支持的银行列表
     * @param channel
     * @param userName  用户姓名
     * @param idCard    身份证
     * @param cardNo    卡号
     * @param bankId    银行ID
     * @param mobile    手机号
     * @param smsCode   验证码
     * @param orderNo   订单号
     * @param bankName  银行名
     * @param oneTop    单笔限额
     * @param dayTop    单日限额
     * @param notice    提示信息
     * @param entry     入口
     * @param productId 产品Id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/banklist")
    public String bankList(@PathVariable String channel, String userName, String idCard, String cardNo, String bankId,
                           String mobile, String smsCode, String orderNo, String bankName, String oneTop, String dayTop,
                           String notice, String entry, String productId, HttpServletRequest request, Map<String,Object> model) {
        share(channel, model, request);

        // 查询宝付支持快捷支付的银行
        ReqMsg_Bank_ChannelBank req = new ReqMsg_Bank_ChannelBank();
        req.setPayChannel(Constants.CHANNEL_HF);
        req.setPayType(Constants.PAY_TYPE_QUICK);
        ResMsg_Bank_ChannelBank res = (ResMsg_Bank_ChannelBank) communicateBusiness.handleMsg(req);
        model.put("bankList", res.getBankList());
        model.put("userName", userName);
        model.put("idCard", idCard);
        model.put("cardNo", cardNo);
        model.put("bankId", bankId);
        model.put("mobile", mobile);
        model.put("smsCode", smsCode);
        model.put("orderNo", orderNo);
        model.put("bankName", bankName);
        model.put("oneTop", oneTop);
        model.put("dayTop", dayTop);
        model.put("notice", notice);
        model.put("entry", entry);
        model.put("from", request.getParameter("from"));
        CookieManager cookieManager = new CookieManager(request);
        String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_178_BACK_URL.getName(), true);
        model.put("backUrl", backUrl);
        model.put("productId", productId);
        return channel + "/assets/bankcard/bank_list";
    }

    /**
     * 校验用户是否绑卡
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isbind")
    public Map<String, Object> isBindCard(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 1、获取请求信息：用户ID
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        ReqMsg_User_IsBindCard req = new ReqMsg_User_IsBindCard();
        req.setUserId(userId);
        ResMsg_User_IsBindCard res = (ResMsg_User_IsBindCard)communicateBusiness.handleMsg(req);
        result.put("isBindCard", res.isBindCard() ? "YES" : "NO");

        return result;
    }

    /**
     * 激活页面
     */
    @RequestMapping("/activate/index")
    public String activateIndex(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);

        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_DepGuide_WaiteActivateInfo req = new ReqMsg_DepGuide_WaiteActivateInfo();
        req.setUserId(Integer.parseInt(userId));
        ResMsg_DepGuide_WaiteActivateInfo result = (ResMsg_DepGuide_WaiteActivateInfo) communicateBusiness.handleMsg(req);
        model.put("result", result);

        // 查询宝付支持快捷支付的银行
        String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_178_BACK_URL.getName(), true);
        if(StringUtil.isNotBlank(backUrl)) {
            model.put("from", Constants.FROM_178_APP);
            model.put("backUrl", backUrl);
        }

        if(!Constants.CHANNEL_MICRO.equals(channel)) {
            ReqMsg_Bank_ChannelBank bankReq = new ReqMsg_Bank_ChannelBank();
            bankReq.setPayChannel(Constants.CHANNEL_HF);
            bankReq.setPayType(Constants.PAY_TYPE_QUICK);
            ResMsg_Bank_ChannelBank res = (ResMsg_Bank_ChannelBank) communicateBusiness.handleMsg(bankReq);
            model.put("bankList", res.getBankList());
        }
        return channel + "/assets/bankcard/activation_card_index";
    }

    /**
     * 激活
     */
    @ResponseBody
    @RequestMapping("/activate")
    public Map<String, Object> activate(@PathVariable String channel, ReqMsg_DepGuide_Activate req, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        req.setVerifyCode(null != req.getVerifyCode() ? StringUtil.trimStr(req.getVerifyCode()) : null);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        ResMsg_DepGuide_Activate res = (ResMsg_DepGuide_Activate) communicateBusiness.handleMsg(req);
        result.put("resCode", res.getResCode());
        result.put("resMsg", res.getResMsg());
        return result;
    }


    /**
     * 分享
     * @param channel   访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
     * @param model
     * @param request
     */
    private void share(String  channel, Map<String, Object> model, HttpServletRequest request) {
        if(Constants.CHANNEL_MICRO.equals(channel)){
            String link = GlobEnv.getWebURL("/micro2.0/index");
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao)
                    && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
    	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
    	        	link += "&agentViewFlag="+viewAgentFlagCookie;
    	        }
                model.put("qianbao", qianbao);
            }
            // 分享
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
        }
    }

}
