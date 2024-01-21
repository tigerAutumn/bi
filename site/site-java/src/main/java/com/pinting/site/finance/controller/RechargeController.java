/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.finance.controller;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.util.Constants;
import org.apache.http.cookie.Cookie;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.Token;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Util;

/**
 * 充值
 * @author HuanXiong
 * @version $Id: RechargeController.java, v 0.1 2015-11-19 上午10:02:40 HuanXiong Exp $
 */
@Controller
public class RechargeController extends BaseController {
	
    @Autowired
    private CommunicateBusiness siteService;

    @Autowired
    private CommunicateBusiness regularService;
    /**
     * PC充值首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/gen2.0/recharge/recharge_index")
    public String recharge_index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_Recharge_RechargeIndexInfo req = new ReqMsg_Recharge_RechargeIndexInfo();
        req.setUserId(Integer.parseInt(userId));
        try {
            // 存管引导信息（未存管绑卡或激活）
            ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
            depGuideReq.setUserId(Integer.parseInt(userId));
            depGuideReq.setContainRisk(true);
            ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
            if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                    || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
                // 未存管绑卡或激活
                return "redirect:/gen2.0/bankcard/index?entry=top_up";
            } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
                return "redirect:/gen2.0/bankcard/activate/index";
            }

            ResMsg_Recharge_RechargeIndexInfo indexInfo = (ResMsg_Recharge_RechargeIndexInfo) siteService.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(indexInfo.getResCode())){
                successRes(model);
                
        		DecimalFormat format = new DecimalFormat("0.00");
            	String amount = format.format(indexInfo.getAvailableBalance());
                model.put("availableBalance", amount);
                model.put("depAvailableBalance", format.format(indexInfo.getDepAvailableBalance()));
                String rechargeLimit = indexInfo.getRechargeLimit();
                model.put("rechargeLimit", rechargeLimit);
            } else {
                errorRes(model);
            }
        } catch (Exception e) {
            errorRes(model);
            e.printStackTrace();
        }
        return "/gen2.0/recharge/recharge_index";
    }
    
    /**
     * 充值首页
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/recharge")
    public Map<String, Object> recharge(HttpServletRequest request, HttpServletResponse response, ReqMsg_Recharge_RechargeIndexInfo req){
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        try {
            ResMsg_Recharge_RechargeIndexInfo indexInfo = (ResMsg_Recharge_RechargeIndexInfo) siteService.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(indexInfo.getResCode())){
                result.put("availableBalance", indexInfo.getAvailableBalance());
                successRes(result);
            } else {
                errorRes(result);
            }
        } catch (Exception e) {
            errorRes(result);
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 【充值】(输入充值金额)
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen2.0/recharge/recharge_index_submit")
    public String rechargeIndexSubmit(HttpServletRequest request, HttpServletResponse response, String rechargeAmount, Map<String, Object> model){
        if(StringUtil.isBlank(rechargeAmount)) {
            return "redirect:/gen2.0/recharge/recharge_index";
        }

        String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Util.createToken(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN);

        // 存管引导信息（未存管绑卡或激活）
        ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depGuideReq.setUserId(Integer.parseInt(userId));
        ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
        if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
            // 未存管绑卡或激活
            return "redirect:/gen2.0/bankcard/index?entry=top_up";
        } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
            return "redirect:/gen2.0/bankcard/activate/index";
        }
        if(Constants.is_expire.equals(depGuideRes.getRiskStatus())
                || Constants.is_no.equals(depGuideRes.getRiskStatus())) {
            // 需要进行风险测评
            return "redirect:/gen2.0/assets/assets?pageFlag=questionnaire";
        }

        model.put("rechargeAmount", rechargeAmount);
        ReqMsg_Bank_bindBankList reqMsg = new ReqMsg_Bank_bindBankList();
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_bindBankList resMsg = (ResMsg_Bank_bindBankList) siteService.handleMsg(reqMsg);
        
        // 已绑卡实名
        if(resMsg.isBindBank()) {
        	//查询充值手续费
        	ReqMsg_Recharge_RechargeCommission reqMsgFee = new ReqMsg_Recharge_RechargeCommission();
  			ResMsg_Recharge_RechargeCommission resMsgFee = (ResMsg_Recharge_RechargeCommission) regularService.handleMsg(reqMsgFee);
  			model.put("actPayAmountFee", resMsgFee.getActPayAmount());
        	
  		  	//查询恒丰支持网银支付的银行
  			ReqMsg_Bank_Pay19NetBankList reqMsgBank = new ReqMsg_Bank_Pay19NetBankList();
  			ResMsg_Bank_Pay19NetBankList resMsgBank = (ResMsg_Bank_Pay19NetBankList) regularService.handleMsg(reqMsgBank);
  			model.put("netBankList", resMsgBank.getBankList());
  			
            model.put("bankList", resMsg.getBankList());
            model.put("accountId", resMsg.getSubAccountId());
            model.put("balance", resMsg.getAvailableBalance());
            return "/gen2.0/recharge/recharge_first_bind";
        }
        // 未绑卡实名
        else {
        	//跳转实名认证
        	return "redirect:/gen2.0/bankcard/index?entry=top_up";
        }
        
    }
    
    /**
     * 【充值】未绑卡用户。预下单
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @Token(save=true)
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/recharge_first")
    public Map<String, Object> rechargeFirst(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("userName", request.getParameter("userName").trim());
        result.put("idCard", request.getParameter("idCard"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankName", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        //String serverToken = (String) request.getSession().getAttribute("token");
        String serverToken = Util.getServerToken(request, userId);
        if(StringUtil.isEmpty(serverToken)){
        	errorRes(result);
        	return result;
        }
        // 根据银行ID查银行信息
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(Integer.parseInt(request.getParameter("bankName")));
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)siteService.handleMsg(reqMsg_Bank_QueryBankById);
        
        // 用户信息
        
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setBankName(resMsg_Bank_QueryBankById.getName());
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(request.getParameter("idCard"));
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setUserName(request.getParameter("userName").trim());
        reqMsg.setIsBind(2);  // 未绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        try {
            ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
                result.put("orderNo", res.getOrderNo());
                result.put("resCode", "000");
                result.put("resMsg", res.getResMsg());
                result.put("token", serverToken);
                result.put("htmlReapalString", res.getHtml());
                if (res.getHtml() !=null && !"fail".equals(res.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            null, true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
                
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", res.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 【充值】未绑卡用户。确认支付
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/recharge_submit")
    public Map<String, Object> rechargeSubmit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 防重复提交
        boolean bo = com.pinting.util.Util.isRepeatSubmit(request, response);
        if (bo) {
            result.put("resCode", "999");
            result.put("resMsg", "请勿重复提交充值订单！！");
            return result;
        }
        
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("userName", request.getParameter("userName").trim());
        result.put("idCard", request.getParameter("idCard"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankName", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        result.put("orderNo", request.getParameter("orderNo"));
        
        String orderNo = request.getParameter("orderNo");
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        // 根据银行ID查银行信息
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(Integer.parseInt(request.getParameter("bankName")));
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)siteService.handleMsg(reqMsg_Bank_QueryBankById);
        
        // 用户信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        
        // 封装数据
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setBankName(resMsg_Bank_QueryBankById.getName());
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(request.getParameter("idCard"));
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setUserName(request.getParameter("userName").trim());
        reqMsg.setOrderNo(orderNo);
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setIsBind(2);    // 未绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 正式下单
        try {
            ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
                result.put("resCode", "000");
                result.put("resMsg", res.getResMsg());
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", res.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 【充值】已绑卡用户（预下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/pre_submit_bind")
    public Map<String, Object> preSubmitBind(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("bankId", request.getParameter("bankId"));
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        if(Util.isRepeatSubmit(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN)) {
            result.put("resCode", ConstantUtil.BSRESCODE_FAIL);
            result.put("resMsg", "充值失败：重复提交的请求");
        	return result;
        }

        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(res.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setIsBind(1);    // 已绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            result.put("orderNo", resp.getOrderNo());
            result.put("mpOrderNo", resp.getMpOrderNo());
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                result.put("resCode", "000");
                result.put("resMsg", resp.getResMsg());
                result.put("htmlReapalString", resp.getHtml());
                if (resp.getHtml() !=null && !"fail".equals(resp.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            null, true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", resp.getResMsg());
                Util.createToken(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        result.put("token", Util.createToken(request, response));
        return result;
    }
    
    /**
     * 【充值】已绑卡用户。（确认下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/submit_bind")
    public Map<String, Object> submitBind(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 防重复提交
        boolean bo = Util.isRepeatSubmit(request, response);
        if (bo) {
            result.put("resCode", "999");
            result.put("resMsg", "请勿重复提交充值订单！！");
            return result;
        }
        String token = Util.createToken(request, response);
        result.put("token", token);

        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        
        result.put("orderNo", request.getParameter("orderNo"));
        result.put("mpOrderNo", request.getParameter("mpOrderNo"));
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        String orderNo = request.getParameter("orderNo");
        
        // 用户基本信息
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        // 确认下单
        reqMsg.setOrderNo(orderNo);
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(res.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setIsBind(1);    // 已绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 预下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                result.put("resCode", "000");
                result.put("resMsg", resp.getResMsg());
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 跳转至添加银行卡页面
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @RequestMapping("/gen2.0/recharge/add_bankcard_index")
    public String addBankcardIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        model.put("rechargeAmount", request.getParameter("rechargeAmount"));
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery reqMsg = new ReqMsg_User_InfoQuery();
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery)siteService.handleMsg(reqMsg);
        
        //查询19付支持快捷支付的银行
        ReqMsg_Bank_Pay19BankList req = new ReqMsg_Bank_Pay19BankList();
        req.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_Pay19BankList resp = (ResMsg_Bank_Pay19BankList) siteService.handleMsg(req);
        model.put("bankList", resp.getBankList());
        model.put("userName", res.getUserName());
        model.put("idCard", res.getIdCard());
        return "/gen2.0/recharge/recharge_old_add_bankcard";
    }
    
    /**
     * 添加银行+充值预下单
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/add_bankcard")
    public Map<String, Object> addBankcard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfoRes.getUserName());
        result.put("idCard", userInfoRes.getIdCard());
        
        // 查询银行卡信息
        /*ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById) siteService.handleMsg(req);*/
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(userInfoRes.getIdCard());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setUserName(userInfoRes.getUserName());
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setIsBind(1);    // 已绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                successRes(result);
            } else {
                errorRes(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 添加银行+确认下单
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/add_bankcard_submit")
    public Map<String, Object> addBankcardSubmit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        /*ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById) siteService.handleMsg(req);*/
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(userInfo.getIdCard());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setIsBind(1);    // 已绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 确认下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                successRes(result);
            } else {
                errorRes(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        result.put("resCode", "000");
        return result;
    }
    
    /**
     * 【充值】充值成功页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen2.0/recharge/recharge_success")
    public String rechargeSuccess(HttpServletRequest request, HttpServletResponse response) {
        return "/gen2.0/recharge/recharge_success";
    }
    
    /**
     * 网银充值提交
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen2.0/recharge/eBankSubmit")
    public Map<String, Object> eBankSubmit(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ReqMsg_Recharge_EBankRecharge req) {
        Map<String, Object> model = new HashMap<>();
        try {
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            req.setUserId(Integer.parseInt(userId));
            log.info("【页面提交充值】 ，userId = " + userId + ",  amount = " + req.getAmount());
            ResMsg_Recharge_EBankRecharge res = (ResMsg_Recharge_EBankRecharge) siteService
                .handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
//                response.setContentType("text/html; charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.print(res.getHtmlStr());
//                out.flush();
//                out.close();
                model.put("resCode", res.getResCode());
                model.put("resMsg", res.getResMsg());
                model.put("html", res.getHtmlStr());
                return model;
            } else {
                model.put("resCode", res.getResCode());
                model.put("resMsg", res.getResMsg());
            }
        } catch (Exception e) {
            log.error("=========================网银充值交易提交失败==========================");
            e.printStackTrace();
            model.put("resCode", "999999");
            model.put("resMsg", e.getMessage());
//            return "/gen2.0/recharge/recharge_error";
            return model;
        }
//        return "/gen2.0/recharge/recharge_error";
        return model;
    }


    @RequestMapping("/gen2.0/recharge/error")
    public String error(HttpServletRequest request, Map<String, Object> map) {
        map.put("resCode", request.getParameter("resCode"));
        map.put("resMsg", request.getParameter("resMsg"));
        return "/gen2.0/recharge/recharge_error";
    }

    @RequestMapping("/gen178/recharge/error")
    public String error178(HttpServletRequest request, Map<String, Object> map) {
        map.put("resCode", request.getParameter("resCode"));
        map.put("resMsg", request.getParameter("resMsg"));
        return "/gen178/recharge/recharge_error";
    }
    
    
    // ================================================ 钱报178 ===============================================
    /**
     * PC充值首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/gen178/recharge/recharge_index")
    public String recharge_index178(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_Recharge_RechargeIndexInfo req = new ReqMsg_Recharge_RechargeIndexInfo();
        req.setUserId(Integer.parseInt(userId));
        try {
            // 存管引导信息（未存管绑卡或激活）
            ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
            depGuideReq.setUserId(Integer.parseInt(userId));
            ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
            if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                    || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
                // 未存管绑卡或激活
                return "redirect:/gen178/bankcard/index?entry=top_up";
            } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
                return "redirect:/gen178/bankcard/activate/index";
            }
            ResMsg_Recharge_RechargeIndexInfo indexInfo = (ResMsg_Recharge_RechargeIndexInfo) siteService.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(indexInfo.getResCode())){
                successRes(model);
                DecimalFormat format = new DecimalFormat("0.00");
                model.put("availableBalance", format.format(indexInfo.getAvailableBalance()));
                model.put("depAvailableBalance", format.format(indexInfo.getDepAvailableBalance()));
                String rechargeLimit = indexInfo.getRechargeLimit();
                model.put("rechargeLimit", rechargeLimit);
            } else {
                errorRes(model);
            }
        } catch (Exception e) {
            errorRes(model);
            e.printStackTrace();
        }
        return "/gen178/recharge/recharge_index";
    }
    
    /**
     * 充值首页
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen178/recharge/recharge")
    public Map<String, Object> recharge178(HttpServletRequest request, HttpServletResponse response, ReqMsg_Recharge_RechargeIndexInfo req){
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        try {
            ResMsg_Recharge_RechargeIndexInfo indexInfo = (ResMsg_Recharge_RechargeIndexInfo) siteService.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(indexInfo.getResCode())){
                result.put("availableBalance", indexInfo.getAvailableBalance());
                successRes(result);
            } else {
                errorRes(result);
            }
        } catch (Exception e) {
            errorRes(result);
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 【充值】(输入充值金额)
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen178/recharge/recharge_index_submit")
    public String rechargeIndexSubmit178(HttpServletRequest request, HttpServletResponse response, String rechargeAmount, Map<String, Object> model){
        if(StringUtil.isBlank(rechargeAmount)) {
            return "redirect:/gen178/recharge/recharge_index";
        }

        String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Util.createToken(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN);

        // 存管引导信息（未存管绑卡或激活）
        ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depGuideReq.setUserId(Integer.parseInt(userId));
        depGuideReq.setContainRisk(true);
        ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
        if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
            // 未存管绑卡或激活
            return "redirect:/gen178/bankcard/index?entry=top_up";
        } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
            return "redirect:/gen178/bankcard/activate/index";
        }
        if(Constants.is_expire.equals(depGuideRes.getRiskStatus())
                || Constants.is_no.equals(depGuideRes.getRiskStatus())) {
            // 需要进行风险测评
            return "redirect:/gen178/assets/assets?pageFlag=questionnaire";
        }

        model.put("rechargeAmount", rechargeAmount);
        ReqMsg_Bank_bindBankList reqMsg = new ReqMsg_Bank_bindBankList();
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_bindBankList resMsg = (ResMsg_Bank_bindBankList) siteService.handleMsg(reqMsg);
        
//        ReqMsg_Bank_QueryDefaultBank reqDefaultBank = new ReqMsg_Bank_QueryDefaultBank();
//        reqDefaultBank.setUserId(Integer.parseInt(userId));
//        ResMsg_Bank_QueryDefaultBank resDefaultBank = (ResMsg_Bank_QueryDefaultBank) regularService.handleMsg(reqDefaultBank);
        
        // 已绑卡实名
        if(resMsg.isBindBank()) {
        	//查询充值手续费
        	ReqMsg_Recharge_RechargeCommission reqMsgFee = new ReqMsg_Recharge_RechargeCommission();
  			ResMsg_Recharge_RechargeCommission resMsgFee = (ResMsg_Recharge_RechargeCommission) regularService.handleMsg(reqMsgFee);
  			model.put("actPayAmountFee", resMsgFee.getActPayAmount());
        	
  			//查询19付支持网银支付的银行
			ReqMsg_Bank_Pay19NetBankList reqMsgBank = new ReqMsg_Bank_Pay19NetBankList();
			ResMsg_Bank_Pay19NetBankList resMsgBank = (ResMsg_Bank_Pay19NetBankList) regularService.handleMsg(reqMsgBank);
			model.put("netBankList", resMsgBank.getBankList());
        	
            model.put("bankList", resMsg.getBankList());
            model.put("accountId", resMsg.getSubAccountId());
            model.put("balance", resMsg.getAvailableBalance());
            return "/gen178/recharge/recharge_first_bind";
        }
        // 未绑卡实名
        else {/*
            //查询19付支持快捷支付的银行
            ReqMsg_Bank_Pay19BankList req = new ReqMsg_Bank_Pay19BankList();
            req.setUserId(Integer.parseInt(userId));
            ResMsg_Bank_Pay19BankList res = (ResMsg_Bank_Pay19BankList) siteService.handleMsg(req);
            model.put("bankList", res.getBankList());
            return "/gen178/recharge/recharge_first";
        */
    	//TUDO:跳转实名认证
    	return null;	
        }
        
    }
    
    /**
     * 【充值】未绑卡用户。预下单
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Deprecated
    @Token(save=true)
    @ResponseBody
    @RequestMapping("/gen178/recharge/recharge_first")
    public Map<String, Object> rechargeFirst178(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("userName", request.getParameter("userName").trim());
        result.put("idCard", request.getParameter("idCard"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankName", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        //String serverToken = (String) request.getSession().getAttribute("token");
        String serverToken = Util.getServerToken(request, userId);
        if(StringUtil.isEmpty(serverToken)){
        	errorRes(result);
        	return result;
        }
        // 根据银行ID查银行信息
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(Integer.parseInt(request.getParameter("bankName")));
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)siteService.handleMsg(reqMsg_Bank_QueryBankById);
        
        // 用户信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setBankName(resMsg_Bank_QueryBankById.getName());
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(request.getParameter("idCard"));
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setUserName(request.getParameter("userName").trim());
        reqMsg.setIsBind(2);  // 未绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        try {
            ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
                result.put("orderNo", res.getOrderNo());
                result.put("resCode", "000");
                result.put("resMsg", res.getResMsg());
                result.put("token", serverToken);
                result.put("htmlReapalString", res.getHtml());
                if (res.getHtml() !=null && !"fail".equals(res.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            "ReapalQuickCMB", true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", res.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 【充值】未绑卡用户。确认支付
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/gen178/recharge/recharge_submit")
    public Map<String, Object> rechargeSubmit178(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 防重复提交
        boolean bo = com.pinting.util.Util.isRepeatSubmit(request, response);
        if (bo) {
            result.put("resCode", "999");
            result.put("resMsg", "请勿重复提交充值订单！！");
            return result;
        }
        
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("userName", request.getParameter("userName").trim());
        result.put("idCard", request.getParameter("idCard"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankName", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        result.put("orderNo", request.getParameter("orderNo"));
        
        String orderNo = request.getParameter("orderNo");
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        // 根据银行ID查银行信息
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(Integer.parseInt(request.getParameter("bankName")));
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)siteService.handleMsg(reqMsg_Bank_QueryBankById);
        
        // 用户信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        
        // 封装数据
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setBankName(resMsg_Bank_QueryBankById.getName());
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(request.getParameter("idCard"));
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setUserName(request.getParameter("userName").trim());
        reqMsg.setOrderNo(orderNo);
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setIsBind(2);    // 未绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 正式下单
        try {
            ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
                result.put("resCode", "000");
                result.put("resMsg", res.getResMsg());
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", res.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 【充值】已绑卡用户（预下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen178/recharge/pre_submit_bind")
    public Map<String, Object> preSubmitBind178(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("bankId", request.getParameter("bankId"));
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        if(Util.isRepeatSubmit(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN)) {
            result.put("resCode", ConstantUtil.BSRESCODE_FAIL);
            result.put("resMsg", "充值失败：重复提交的请求");
            return result;
        }
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(res.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setIsBind(1);    // 已绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            result.put("orderNo", resp.getOrderNo());
            result.put("mpOrderNo", resp.getMpOrderNo());
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                result.put("resCode", "000");
                result.put("resMsg", resp.getResMsg());
                result.put("htmlReapalString", resp.getHtml());
                if (resp.getHtml() !=null && !"fail".equals(resp.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            "ReapalQuickCMB", true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", resp.getResMsg());
                Util.createToken(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        result.put("token", Util.createToken(request, response));
        return result;
    }
    
    /**
     * 【充值】已绑卡用户。（确认下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen178/recharge/submit_bind")
    public Map<String, Object> submitBind178(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        // 防重复提交
        boolean bo = Util.isRepeatSubmit(request, response);
        if (bo) {
            result.put("resCode", "999");
            result.put("resMsg", "请勿重复提交充值订单！！");
            return result;
        }
        String token = Util.createToken(request, response);
        result.put("token", token);
        
        result.put("rechargeAmount", request.getParameter("rechargeAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        
        result.put("orderNo", request.getParameter("orderNo"));
        result.put("mpOrderNo", request.getParameter("mpOrderNo"));
        String amount = request.getParameter("rechargeAmount");
        Double amount1 = Double.parseDouble(amount);
        String orderNo = request.getParameter("orderNo");
        
        // 用户基本信息
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        // 确认下单
        reqMsg.setOrderNo(orderNo);
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(res.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setIsBind(1);    // 已绑卡
        reqMsg.setTransType(2); // 充值
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 确认下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                result.put("resCode", "000");
                result.put("resMsg", resp.getResMsg());
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    
    
    /**
     * 【充值】充值成功页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen178/recharge/recharge_success")
    public String rechargeSuccess178(HttpServletRequest request, HttpServletResponse response) {
        return "/gen178/recharge/recharge_success";
    }
    
    /**
     * 网银充值提交
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen178/recharge/eBankSubmit")
    public Map<String, Object> eBankSubmit178(HttpServletRequest request,
                                   HttpServletResponse response,
                                   ReqMsg_Recharge_EBankRecharge req) {
        Map<String, Object> model = new HashMap<>();
        try {
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            req.setUserId(Integer.parseInt(userId));
            log.info("【页面提交充值】 ，userId = " + userId + ",  amount = " + req.getAmount());
          //钱报178网银购买标记
            req.setFlag("qianbao178NetBankBuy");
            ResMsg_Recharge_EBankRecharge res = (ResMsg_Recharge_EBankRecharge) siteService
                .handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
//                response.setContentType("text/html; charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.print(res.getHtmlStr());
//                out.flush();
//                out.close();
//                return null;

                model.put("resCode", res.getResCode());
                model.put("resMsg", res.getResMsg());
                model.put("html", res.getHtmlStr());
                return model;
            } else {
                model.put("resCode", res.getResCode());
                model.put("resMsg", res.getResMsg());
            }
        } catch (Exception e) {
            log.error("=========================网银充值交易提交失败==========================");
            e.printStackTrace();
            model.put("resCode", "999999");
            model.put("resMsg", e.getMessage());
            // return "/gen178/recharge/recharge_error";
            return model;
        }
        return model;
        // return "/gen178/recharge/recharge_error";
    }
    
    // ================================================ 钱报178 ===============================================
}
