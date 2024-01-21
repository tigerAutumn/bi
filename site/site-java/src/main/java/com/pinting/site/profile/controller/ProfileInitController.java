package com.pinting.site.profile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_Dict_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_SMS_Generate;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Dict_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_SMS_Generate;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * @Project: site-java
 * @Title: ProfileInitController.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:39:35
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@Scope("prototype")
public class ProfileInitController extends BaseController {

    @Autowired
    private CommunicateBusiness siteService;
    @Autowired
	private WeChatUtil weChatUtil;

    /**
     * 打开我的资料界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/index")
    public String profileInfoInit(@PathVariable String channel, ReqMsg_User_InfoQuery req,
                                  HttpServletRequest request, Map<String, Object> model) {
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        
        model.put("isShowReturnPath", resp.getIsShowReturnPath());
        model.put("h5ReturnPathHide", resp.getH5ReturnPathHide());
        //是否已经实名认证？
        //判断是否存在支付密码 1:有交易密码；2：无交易密码
        if (resp.getExcitPaypassword() == 2) {
            model.put("isBindPayPassword", false);
        }
        if (resp.getIsBindName() == Constants.IS_BIND_NAME_YES) {
            model.put("userName", resp.getUserName());
            model.put("isBindName", true);
        } else {
            model.put("isBindName", false);
        }
        
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        model.put("bankName", resp.getBankName());
        model.put("cardNo", resp.getCardNo());
        model.put("cardId", resp.getCardId());
        return channel + "/safe/safe_account";
    }

    /**
     * 打开实名认证界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/authentication")
    public String authenticationInit(@PathVariable String channel, HttpServletRequest request,
                                     Map<String, Object> model) {
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("userName", resp.getUserName());
        model.put("idCard", resp.getIdCard());
        return channel + "/profile/authentication";
    }

    /**
     * 打开设置支付密码
     * 
     * @param channel
     * @param request
     * @param response
     * @return
    */
    @RequestMapping("{channel}/user/paypassword_regist")
    public String paypasswordRegistInit(@PathVariable String channel, HttpServletRequest request,
                                        Map<String, Object> model) {
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        ReqMsg_SMS_Generate req2 = new ReqMsg_SMS_Generate();
        req2.setMobile(resp.getMobile());
        model.put("mobile", resp.getMobile());
        model.put("userId", resp.getUserId());
        ResMsg_SMS_Generate resp2 = (ResMsg_SMS_Generate) siteService.handleMsg(req2);
        return channel + "/user/paypassword_regist";
    }

    /**
     * 打开紧急联系人界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/urgent_user")
    public String urgentUserInit(@PathVariable String channel, HttpServletRequest request,
                                 Map<String, Object> model) {
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        ReqMsg_Dict_ListQuery reqdic = new ReqMsg_Dict_ListQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        reqdic.setDictId(Constants.DICT_ITEM_RELATION_ID);
        //发起Hessian通讯（资产信息查询）
        //要发送短信

        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        //判断是否存在支付密码

        if (resp.getExcitPaypassword() == 2) {
            model.put("mobile", resp.getMobile());
            model.put("userId", resp.getUserId());
            model.put("burl", channel + "/profile/urgent_user");

            return channel + "/profile/urgent_paypassword";

        }
        ResMsg_Dict_ListQuery respdic = (ResMsg_Dict_ListQuery) siteService.handleMsg(reqdic);

        model.put("urgentName", resp.getUrgentName());
        model.put("urgentMobile", resp.getUrgentMobile());
        model.put("relationId", resp.getRelation());
        model.put("relationList", respdic.getItemList());
        return channel + "/profile/urgent_user";
    }

    /**
     * 修改手机号码为****模式
     * 
     * @param m 手机号
     * @return ****手机号
     */
    private String replaceMobile(String m) {
        StringBuffer m4 = new StringBuffer();
        for (int i = 0; i < m.length(); i++) {
            if (i > 2 && i < 7) {
                m4.append("*");
            } else {
                m4.append(m.charAt(i));
            }
        }
        return m4.toString();
    }

    /**
     * 打开查看绑定手机界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/show_mobile")
    public String bindMobileOneInit(@PathVariable String channel, HttpServletRequest request,
                                    HttpServletResponse response, Map<String, Object> model) {

        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("user", resp);
        model.put("userMobile", replaceMobile(resp.getMobile()));
        return channel + "/profile/show_mobile";
    }

    /**
     * 打开修改绑定手机界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/bind_mobile")
    public String bindMobileTwoInit(@PathVariable String channel, HttpServletRequest request,
                                    HttpServletResponse response, Map<String, Object> model) {
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("oldMobile", resp.getMobile());
        return channel + "/profile/bind_mobile";
    }

    /**
     * 打开绑定邮箱界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/bind_email")
    public String bindEmailInit(@PathVariable String channel, HttpServletRequest request,
                                HttpServletResponse response, Map<String, Object> model) {
        //查询是否已经绑定邮箱
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（用户信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        if (resp.getEmail() == null || "".equals(resp.getEmail())) {
            return channel + "/profile/bind_email";
        } else {
            model.put("userEmail", resp.getEmail());
            return channel + "/profile/show_email";
        }
    }

    /**
     * 打开修改绑定邮箱界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/modify_bind_email")
    public String modifyEmailInit(@PathVariable String channel, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model) {
        //查询是否已经绑定邮箱
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（用户信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("oldEmail", resp.getEmail());
        return channel + "/profile/modify_bind_email";
    }

    /**
     * 打开修改登录密码界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/modify_login_password")
    public String modifyLoginPasswordInit(@PathVariable String channel, HttpServletRequest request,
                                          HttpServletResponse response, Map<String, Object> model) {
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
        
        return channel + "/safe/safe_change_password";
    }

    /**
     * 打开修改支付密码界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/modify_pay_password")
    public String modifyPayPasswordInit(@PathVariable String channel, HttpServletRequest request,
                                        HttpServletResponse response, Map<String, Object> model) {
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        
        String wfrom = request.getParameter("wfrom");
        String backUrl = request.getParameter("backUrl");
        
        model.put("wfrom", request.getParameter("wfrom"));
        model.put("backUrl", request.getParameter("backUrl"));
        log.info("修改支付密码界面  from="+wfrom + "backurl="+backUrl);
        
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        String link = GlobEnv.getWebURL("/micro2.0/index");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("mobile", resp.getMobile());

        model.put("from", request.getParameter("from"));
        model.put("amount", request.getParameter("amount"));
        //判断是否存在支付密码;2:不存在；1：存在
        if (resp.getExcitPaypassword() == 2) {
        	
        	if(Constants.FROM_178_APP.equals(wfrom) ) {
        		return  "qianbao178/safe/safe_save_traders_password";
			}
        	
            return channel + "/safe/safe_save_traders_password";
        } else {
        	if(Constants.FROM_178_APP.equals(wfrom) ) {
        		return  "qianbao178/safe/safe_traders_password";
			}
            return channel + "/safe/safe_traders_password";
        }
    }

    /**
     * 【2.0】主动忘记交易密码界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/profile/forget_pay_password_index")
    public String forgetPayPasswordIndex(@PathVariable String channel, HttpServletRequest request,
                                         HttpServletResponse response, Map<String, Object> model) {
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("mobile", resp.getMobile());
        return channel + "/safe/safe_forget_trader_password";
    }

}
