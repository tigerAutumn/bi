package com.pinting.site.profile.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Profile_PasswordModify;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_PayPasswordModify;
import com.pinting.business.hessian.site.message.ReqMsg_User_FindPayPassword;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_SetUpTraderPassword;
import com.pinting.business.hessian.site.message.ResMsg_Profile_PasswordModify;
import com.pinting.business.hessian.site.message.ResMsg_Profile_PayPasswordModify;
import com.pinting.business.hessian.site.message.ResMsg_User_FindPayPassword;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_SetUpTraderPassword;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * @Project: site-java
 * @Title: ProfileModifyController.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:39:48
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@Scope("prototype")
public class ProfileModifyController extends BaseController {

    @Autowired
    private CommunicateBusiness siteService;

    /**
     * 修改登录密码
     * 
     * @return
     */
    @RequestMapping("{channel}/profile/modifyloginpass")
    public @ResponseBody
    HashMap<String, Object> bindMobile(@PathVariable String channel,
                                       ReqMsg_Profile_PasswordModify req,
                                       HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> model) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（登录密码修改）
        ResMsg_Profile_PasswordModify resp = (ResMsg_Profile_PasswordModify) siteService
            .handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            // 返回其它 非公共信息 字段
            dataMap.put("resCode", "000");
            dataMap.put("resMsg", "登录密码修改成功");
        } else {
            // 公共信息字段返回
            dataMap.put("resCode", "999");
            dataMap.put("resMsg", resp.getResMsg());
        }
        return dataMap;
    }

    /**
     * 修改支付密码
     * 
     * @return
     */
    @RequestMapping("{channel}/profile/modifypaypass")
    public @ResponseBody
    HashMap<String, Object> bindEmail(@PathVariable String channel,
                                      ReqMsg_Profile_PayPasswordModify req,
                                      HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> model) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（支付密码修改）
        ResMsg_Profile_PayPasswordModify resp = (ResMsg_Profile_PayPasswordModify) siteService
            .handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            // 返回其它 非公共信息 字段
            dataMap.put("resCode", "000");
            dataMap.put("resMsg", "支付密码修改成功");
        } else {
            // 公共信息字段返回
            dataMap.put("resCode", "999");
            dataMap.put("resMsg", resp.getResMsg());
        }
        return dataMap;
    }

    /**
     * 忘记支付密码界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/forgetpaypassword")
    public String forgetPayPasswordInit(@PathVariable String channel, HttpServletRequest request,
                                        HttpServletResponse response, Map<String, Object> model) {
        // 测试hessian是否连通
        // System.err.println(">>>>>>>>hessian测试>>>>>>>>>>>>>"+basicService.hello()+"<<<<<<<hessian测试<<<<<<<<<");
        //return channel + "/user/user_login";
        //组织请求报文
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("mobile", resp.getMobile());
        return channel + "/profile/forget_paypassword";
    }

    /**
     * 修改新支付密码
     * 
     * @return
     */
    @RequestMapping("{channel}/profile/paypasswordsubimt")
    public @ResponseBody
    HashMap<String, Object> changePayPasswordSubmit(ReqMsg_User_FindPayPassword req,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        try {
            ResMsg_User_FindPayPassword resp = (ResMsg_User_FindPayPassword) siteService
                .handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
                //返回其它 非公共信息 字段
                dataMap.put(ConsMsgUtil.DATA, resp.getExtendMap());
                dataMap.put("bsCode", "000");
                dataMap.put("mobile", req.getMobile());
            } else {
                //公共信息字段返回
                errorRes(dataMap, resp);
                dataMap.put("bsCode", "999");
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	errorRes(dataMap, "港湾航道堵塞，稍后再试吧");
            dataMap.put("bsCode", "999");
        }

        return dataMap;
    }

    /**
     * 【2.0】设置交易密码 （之前没有设置过交易密码）
     * 
     * @param req
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/profile/set_up_trader_password")
    public Map<String, Object> setUpTraderPassword(ReqMsg_User_SetUpTraderPassword req,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (!StringUtils.isBlank(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        try {
            ResMsg_User_SetUpTraderPassword resp = (ResMsg_User_SetUpTraderPassword) siteService
                .handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
                //返回其它 非公共信息 字段
                result.put(ConsMsgUtil.DATA, resp.getExtendMap());
                result.put("resCode", "000");
            } else {
                //公共信息字段返回
                errorRes(result, resp);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            errorRes(result, e);
        }
        return result;
    }

    /**
     * 【2.0】修改交易密码（已经存在交易密码）
     * 
     * @param req
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/profile/change_trader_password")
    public Map<String, Object> changeTraderPassword(ReqMsg_Profile_PayPasswordModify req,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (StringUtils.isNotBlank(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        // 修改交易密码
        ResMsg_Profile_PayPasswordModify resp = (ResMsg_Profile_PayPasswordModify) siteService
            .handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            // 返回其它 非公共信息 字段
            result.put("resCode", "000");
            result.put("resMsg", "支付密码修改成功");
        } else {
            // 公共信息字段返回
            result.put("resCode", "999");
            result.put("resMsg", resp.getResMsg());
        }
        return result;
    }

    /**
     * 【2.0】主动忘记交易密码
     * 
     * @param req
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/profile/forget_trader_password")
    public Map<String, Object> forgetTraderPassword(ReqMsg_User_FindPayPassword req,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        //组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (StringUtils.isNotBlank(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        try {
            ResMsg_User_FindPayPassword resp = (ResMsg_User_FindPayPassword) siteService
                .handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
                //返回其它 非公共信息 字段
                result.put(ConsMsgUtil.DATA, resp.getExtendMap());
                result.put("resCode", "000");
                result.put("mobile", req.getMobile());
            } else {
                //公共信息字段返回
                errorRes(result, resp);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            errorRes(result, e);
        }
        return result;
    }

}
