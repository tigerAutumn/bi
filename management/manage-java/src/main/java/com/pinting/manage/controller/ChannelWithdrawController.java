/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.util.AddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsPayOrders_UserOrdersList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUser_BsUserFinancialConfirmation;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_AuditWithdrawCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ConfirmTransfer;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_QueryWithdrawCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.business.hessian.manage.message.ResMsg_BsPayOrders_UserOrdersList;
import com.pinting.business.hessian.manage.message.ResMsg_BsUser_BsUserFinancialConfirmation;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_AuditWithdrawCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ConfirmTransfer;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_QueryWithdrawCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author HuanXiong
 * @version $Id: FinanceWithdrawController.java, v 0.1 2016-1-7 上午10:26:25 HuanXiong Exp $
 */
@Controller
@RequestMapping("/channelWithdraw")
public class ChannelWithdrawController {

    @Autowired
    @Qualifier("dispatchService")
    private HessianService hessianService;

    @Autowired
    private BsUserService bsUserService;
    
    /**
     * 8 财务提现首页
     * 
     * @param request
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/withdrawIndex")
    public String withdraw(HttpServletRequest request, ReqMsg_ChannelWithdraw_WithdrawIndex req, Map<String, Object> model){
        if (StringUtil.isBlank(req.getChannelType())) {
            req.setChannelType("REAPAL");
        }
        model.put("req", req);
        ResMsg_ChannelWithdraw_WithdrawIndex res = (ResMsg_ChannelWithdraw_WithdrawIndex)hessianService.handleMsg(req);
        if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            model.put("amount", res.getAmount());
            model.put("channelBankCardList", res.getChannelBankCardList());
            model.put("channelList", res.getChannelList());
            model.put("withdrawStatus", res.getWithdrawStatus());
            model.put("oneTop", res.getOneTop());
            model.put("dayTop", res.getDayTop());
            model.put("resCode", "000");
            model.put("resMsg", "提现正在处理中，请耐心等待");
        } else {
            model.put("resCode", res.getResCode());
            model.put("resMsg", res.getResMsg());
        }
        return "channel/withdraw/withdrawIndex";
    }
    
    /**
     * 8 提现操作
     * 
     * @param request
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/channelWithdraw")
    public Map<String, Object> channelWithdraw(HttpServletRequest request, ReqMsg_ChannelWithdraw_ChannelWithdraw req){
        Map<String, Object> map = new HashMap<String, Object>();
        ResMsg_ChannelWithdraw_ChannelWithdraw res = (ResMsg_ChannelWithdraw_ChannelWithdraw)hessianService.handleMsg(req);
        if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            if("提现正在处理中，请耐心等待".equals(res.getMsg())){
                map.put("resCode", "000");
                map.put("resMsg", res.getMsg());
            } else {
                map.put("resCode", "999");
                map.put("resMsg", res.getMsg());
            }
        } else {
            map.put("resCode", res.getResCode());
            map.put("resMsg", res.getResMsg());
        }
        return map;
    }
    
    /**
     * 9 提现确认操作
     * 
     * @param request
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/confirmTransfer")
    public Map<Object, Object> confirmTransfer(HttpServletRequest request, ReqMsg_ChannelWithdraw_ConfirmTransfer req) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(mUserId));
        ResMsg_ChannelWithdraw_ConfirmTransfer res = (ResMsg_ChannelWithdraw_ConfirmTransfer)hessianService.handleMsg(req);
        if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            return ReturnDWZAjax.success("提现确认成功");
        } else {
            return ReturnDWZAjax.fail(res.getResMsg());
        }
    }
    
    /**
     * 10 用户提现申请，财务确认处理查询
     * 
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("/queryWithdrawCheck")
    public String queryWithdrawCheck(HttpServletRequest request, ReqMsg_ChannelWithdraw_QueryWithdrawCheck req, Map<String, Object> model) {
        if (StringUtil.isBlank(req.getStatus())) 
            req.setStatus("ALL");
        model.put("req", req);
        ResMsg_ChannelWithdraw_QueryWithdrawCheck res = (ResMsg_ChannelWithdraw_QueryWithdrawCheck)hessianService.handleMsg(req);
        res.getQueryWithdrawCheckList();
        model.put("count", res.getCount());
        model.put("dataGrid", res.getQueryWithdrawCheckList());
        return "channel/withdraw/withdraw_check";
    }
    
    /**
     * 10 财务确认处理审核操作
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping("/auditWithdrawCheck")
    public Map<Object, Object> auditWithdrawCheck(HttpServletRequest request, ReqMsg_ChannelWithdraw_AuditWithdrawCheck req) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        req.setmUserId(Integer.parseInt(mUserId));
        ResMsg_ChannelWithdraw_AuditWithdrawCheck res = (ResMsg_ChannelWithdraw_AuditWithdrawCheck)hessianService.handleMsg(req);
        if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            return ReturnDWZAjax.success("审核操作成功");
        } else {
            return ReturnDWZAjax.fail(res.getResMsg());
        }
    }
    
    /**
     * 财务确认处理查看详情页面
     */
    @RequestMapping("/auditWithdrawDetails")
    public String auditWithdrawDetails(HttpServletRequest request, Map<String, Object> model) {
    	String userId = request.getParameter("userId");
        model.put("userId", userId);

        ReqMsg_BsUser_BsUserFinancialConfirmation reqUserFinancial = new ReqMsg_BsUser_BsUserFinancialConfirmation();
        reqUserFinancial.setUserId(Integer.parseInt(userId));
        ResMsg_BsUser_BsUserFinancialConfirmation resUserFinancial = (ResMsg_BsUser_BsUserFinancialConfirmation) hessianService.handleMsg(reqUserFinancial);
        model.put("userName", resUserFinancial.getUserName());
        model.put("idCard", resUserFinancial.getIdCard());
        model.put("mobile", resUserFinancial.getMobile());
        model.put("reservedMobile", resUserFinancial.getReservedMobile());
        model.put("cardNo", resUserFinancial.getCardNo());
        model.put("age", resUserFinancial.getAge());
        model.put("gender", resUserFinancial.getGender());

    	ReqMsg_BsPayOrders_UserOrdersList reqUserOrder = new ReqMsg_BsPayOrders_UserOrdersList();
    	reqUserOrder.setUserId(Integer.parseInt(userId));
    	ResMsg_BsPayOrders_UserOrdersList resUserOrder = (ResMsg_BsPayOrders_UserOrdersList) hessianService.handleMsg(reqUserOrder);
    	model.put("userOrdersList", resUserOrder.getValueList());
    	
    	return "channel/withdraw/withdraw_details";
    }

    /**
     * 财务确认处理查看详情 ajax加载用户基本信息
     * a.身份证归属地
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/auditWithdrawDetailInfo")
    public Map<String, Object> auditWithdrawDetailInfo(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        model.put("userId", userId);
        BsUserAssetVO bsUserAssetVO = bsUserService.queryUserById(Integer.parseInt(userId));
        if(bsUserAssetVO != null && !"".equals(bsUserAssetVO)) {
            //身份证归属地
            String idCardAttribution = AddressUtil.idCardNoAddress(bsUserAssetVO.getIdCard());
            model.put("idCardAttribution", idCardAttribution);
        }
        return model;
    }

    /**
     * b.注册手机归属地
     */
    @ResponseBody
    @RequestMapping("/auditWithdrawDetailRegMobile")
    public Map<String, Object> auditWithdrawDetailRegMobile(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        BsUserAssetVO bsUserAssetVO = bsUserService.queryUserById(Integer.parseInt(userId));
        if(bsUserAssetVO != null && !"".equals(bsUserAssetVO)) {
            //注册手机归属地
            String registeredMobileAttribution =AddressUtil.mobileAddress(bsUserAssetVO.getMobile());
            model.put("registeredMobileAttribution", registeredMobileAttribution.equals("nullnull") ? "未查询到手机号归属地" : registeredMobileAttribution);
        }
        return model;
    }

    /**
     * c.预留手机归属地
     */
    @ResponseBody
    @RequestMapping("/auditWithdrawDetailResMobile")
    public Map<String, Object> auditWithdrawDetailResMobile(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        BsUserAssetVO bsUserAssetVO = bsUserService.queryUserById(Integer.parseInt(userId));
        if(bsUserAssetVO != null && !"".equals(bsUserAssetVO)) {
            //预留手机归属地
            String reservedMobileAttribution = AddressUtil.mobileAddress(bsUserAssetVO.getReservedMobile());
            model.put("reservedMobileAttribution", reservedMobileAttribution.equals("nullnull") ? "未查询到手机号归属地" : reservedMobileAttribution);
        }
        return model;
    }

    /**
     * d.银行卡归属地
     */
    @ResponseBody
    @RequestMapping("/auditWithdrawDetailCardNo")
    public Map<String, Object> auditWithdrawDetailCardNo(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        BsUserAssetVO bsUserAssetVO = bsUserService.queryUserById(Integer.parseInt(userId));
        if(bsUserAssetVO != null && !"".equals(bsUserAssetVO)) {
            //银行卡归属地
            String cardNoAttribution = AddressUtil.bankCardAddress(bsUserAssetVO.getCardNo());
            model.put("cardNoAttribution", cardNoAttribution);
        }
        return model;
    }
    
}
