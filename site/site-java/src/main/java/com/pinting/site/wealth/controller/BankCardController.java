package com.pinting.site.wealth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Bank_AddInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_AddSave;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryFirstCard;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_ReturnPath;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_SetIsFirstBankCard;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_Unbundling;
import com.pinting.business.hessian.site.message.ReqMsg_User_BankListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_CheckPayPassword;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Bank_AddInfo;
import com.pinting.business.hessian.site.message.ResMsg_Bank_AddSave;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryFirstCard;
import com.pinting.business.hessian.site.message.ResMsg_Bank_ReturnPath;
import com.pinting.business.hessian.site.message.ResMsg_Bank_SetIsFirstBankCard;
import com.pinting.business.hessian.site.message.ResMsg_Bank_Unbundling;
import com.pinting.business.hessian.site.message.ResMsg_User_BankListQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_CheckPayPassword;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
@Scope(value = "prototype")
public class BankCardController extends BaseController {
    @Autowired
    private CommunicateBusiness siteService;
    @Autowired
	private WeChatUtil weChatUtil;

    /**
     * 进入银行卡列表
     * 
     * @param channel
     * @param reqMsg
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/wealth/bankcard_index")
    public String bankcardInit(@PathVariable String channel, ReqMsg_User_BankListQuery reqMsg,
                               HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> model) {
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            return "redirect:/" + channel + "/bankcard/self?qianbao=qianbao";
        }
        return "redirect:/" + channel + "/bankcard/self";
    }

    /**
     * 绑定银行卡页面
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("{channel}/wealth/bankcard_add")
    public String bankcardAddInit(@PathVariable String channel, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model,
                                  ReqMsg_Bank_AddInfo req) {

        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));

        ResMsg_Bank_AddInfo res = (ResMsg_Bank_AddInfo) siteService.handleMsg(req);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            return "redirect:/micro/wealth/bankcard_index";
        }

        //request.getSession().setAttribute("token", UUID.randomUUID().toString());
        model.put("res", res);
        return channel + "/wealth/bankcard_add";
    }

    /**
     * 绑定银行卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("{channel}/wealth/bankcard_addSave")
    public @ResponseBody
    HashMap<String, Object> bankcardAddSave(@PathVariable String channel,
                                            HttpServletRequest request,
                                            HttpServletResponse response,
                                            Map<String, Object> model, ReqMsg_Bank_AddSave req) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        //重复提交校验
        /*if(Util.isRepeatSubmit(request)){
        	errorRes(dataMap, "资料已提交，网络可能延迟，请稍等。。。");
        	return dataMap;
        }*/
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        req.setIdCard(req.getIdCard().toUpperCase());
        try {
            ResMsg_Bank_AddSave res = (ResMsg_Bank_AddSave) siteService.handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                successRes(dataMap, res);
            } else {
                errorRes(dataMap, res);
            }
        } catch (Exception e) {
            errorRes(dataMap, e);
            log.error("=========================绑定银行卡==========================");
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 解绑银行卡页面
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("{channel}/wealth/bankcard_unbundling_index")
    public String unbundlingIndex(@PathVariable String channel, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model,
                                  ReqMsg_Bank_Unbundling req, String smallLogo, String largeLogo) {
        model.put("bankcard", req);
        model.put("smallLogo", smallLogo);
        model.put("largeLogo", largeLogo);
        return channel + "/wealth/bankcard_unbundling_index";
    }

    /**
     * 解绑银行卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/wealth/bankcard_unbundling")
    public Map<String, Object> unbundling(@PathVariable String channel, HttpServletRequest request,
                                          HttpServletResponse response, Map<String, Object> model,
                                          ReqMsg_Bank_Unbundling req) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            ResMsg_Bank_Unbundling resMsg = (ResMsg_Bank_Unbundling) siteService.handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
                successRes(result, resMsg);
            } else {
                errorRes(result, resMsg);
            }
        } catch (Exception e) {
            errorRes(result, e);
            log.error("=========================解绑银行卡==========================");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 跳转至回款路径列表
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/safe/safe_returned_money")
    public String returnedMoney(@PathVariable String channel, HttpServletRequest request,
                                HttpServletResponse response, Map<String, Object> model, ReqMsg_Bank_ReturnPath req) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_ReturnPath resMsg = (ResMsg_Bank_ReturnPath) siteService.handleMsg(req);
        model.put("bankName", resMsg.getBankName());
        model.put("cardId", resMsg.getCardId());
        model.put("cardNo", resMsg.getCardNo());
        model.put("returnPath", resMsg.getReturnPath());
        
        model.put("isShowReturnPath", resMsg.getIsShowReturnPath());
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        return channel + "/safe/safe_returned_money";
    }
    
    /**
     * 【PC】回款路径列表
     */
    @RequestMapping("/gen2.0/safe/return_path")
    public String returnPath(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, ReqMsg_Bank_ReturnPath req) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_ReturnPath resMsg = (ResMsg_Bank_ReturnPath) siteService.handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            successRes(model);
        } else {
            errorRes(model);
        }
        model.put("bankName", resMsg.getBankName());
        model.put("cardId", resMsg.getCardId());
        model.put("cardNo", resMsg.getCardNo());
        model.put("returnPath", resMsg.getReturnPath());
        return "/gen2.0/assets/return_path";
    }
    
    /**
     * 设置回款卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/safe/set_is_first_bankcard")
    public Map<String, Object> setIsFirstBankCard(@PathVariable String channel, HttpServletRequest request,
        HttpServletResponse response, ReqMsg_Bank_SetIsFirstBankCard req) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        try {
            ResMsg_Bank_SetIsFirstBankCard resMsg = (ResMsg_Bank_SetIsFirstBankCard)siteService.handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
                successRes(result, resMsg);
            } else {
                errorRes(result, resMsg);
            }
        } catch (Exception e) {
            errorRes(result, e);
            log.error("=========================设置回款卡==========================");
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 提现：判断是否含有交易密码
     * 
     * @param channel
     * @param request
     * @param response
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/safe/checkPayPassword")
    public Map<String, Object> checkPayPassword(@PathVariable String channel, HttpServletRequest request,
        HttpServletResponse response, ReqMsg_User_InfoQuery req) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        try {
            ResMsg_User_InfoQuery msg = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
            //判断是否存在支付密码 1:有交易密码；2：无交易密码
            if (msg.getExcitPaypassword() == 2) {
                result.put("HavePayPassword", "no");
            } else {
                result.put("HavePayPassword", "yes");
            }
            //判断是否存在回款卡
            if(msg.getIsBindBank() == 2 ){
            	 result.put("HaveFirstCard", "no");
            } else {
                result.put("HaveFirstCard", "yes");
            }
            successRes(result, msg);
        } catch (Exception e) {
            errorRes(result, e);
            log.error("=========================检查交易密码==========================");
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 检查交易密码是否正确
     * @param channel
     * @param request
     * @param response
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/safe/check_pay_password")
    public Map<String, Object> safeCheckPayPassword(@PathVariable String channel, HttpServletRequest request,
        HttpServletResponse response, ReqMsg_User_CheckPayPassword req) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        try {
            ResMsg_User_CheckPayPassword msg = (ResMsg_User_CheckPayPassword) siteService.handleMsg(req);
            result.put("failNums", msg.getFailNums());
            result.put("toastMsg", msg.getToastMsg());
            result.put("failTime", msg.getFailTime());
            result.put("truePayPassword", msg.isTruePayPassword());
            
            //获取可提现次数
            ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
    		reqMsg.setUserId(Integer.parseInt(userId));
    		ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService
    				.handleMsg(reqMsg);
            
    		result.put("withdrawTimes", resp.getCan_withdraw_times() == null?0:resp.getCan_withdraw_times());
            successRes(result, msg);
        } catch (Exception e) {
            errorRes(result, e);
            log.error("=========================检查交易密码==========================");
            e.printStackTrace();
        }
        return result;
    }

}
