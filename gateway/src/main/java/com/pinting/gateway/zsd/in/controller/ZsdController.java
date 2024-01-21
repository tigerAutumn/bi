package com.pinting.gateway.zsd.in.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.pinting.gateway.bird.in.model.*;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.hessian.message.loan.model.LateRepayment;
import com.pinting.gateway.hessian.message.loan.model.RepaySchedule;
import com.pinting.gateway.hessian.message.loan.model.Repayment;
import com.pinting.gateway.hessian.message.zsd.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.DESUtil;
import com.pinting.gateway.zsd.in.enums.PartnerMessageEnum;
import com.pinting.gateway.zsd.in.util.ZsdInConstant;
import com.pinting.gateway.bird.in.util.ModelConverterUtil;
import com.pinting.gateway.business.out.service.ZsdSendBusinessService;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_CutpaymentRepay;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_CutpaymentRepay;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdRepay_LateRepayNotice;
import com.pinting.gateway.util.Constants;

/**
 * 
 * @project gateway
 * @title ZsdController.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
@RequestMapping(value = "/rest_zsd")
public class ZsdController {

    private Logger log = LoggerFactory.getLogger(ZsdController.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

    @Autowired
    private ZsdSendBusinessService zsdSendBusinessService;

    /**
     * 借款人信息登记
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/loaner", method = RequestMethod.POST)
    @ResponseBody
    public LoanerRsp insertLoaner(@RequestBody LoanerReq req, HttpServletResponse response) {

        log.info("借款人登记入参:" + JSON.toJSONString(req));
        //客户端校验
        clientCheck(req.getClientKey(), req.getToken());
        //入参校验
        reqMsgValidate(req);
        G2BReqMsg_ZsdLoanCif_AddLoaner g2bReq = new G2BReqMsg_ZsdLoanCif_AddLoaner();
        //取值
        g2bReq.setUserId(req.getUserId());
        g2bReq.setName(req.getName());
        g2bReq.setRegMobile(req.getRegMobile());
        g2bReq.setIdCard(req.getIdCard());
        g2bReq.setProfession(req.getProfession());
        g2bReq.setAnnualIncome(req.getAnnualIncome());
        g2bReq.setProvinceCode(req.getProvinceCode());
        g2bReq.setCityCode(req.getCityCode());
        g2bReq.setAreaCode(req.getAreaCode());

        g2bReq.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);

        G2BResMsg_ZsdLoanCif_AddLoaner g2bRes = zsdSendBusinessService.addLoaner(g2bReq);
        LoanerRsp rsp = new LoanerRsp();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(g2bRes.getResCode())) {
            errorRes(rsp, g2bRes.getResCode(), g2bRes.getResMsg(), response);
        }

        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("借款人信息登记返回:" + JSON.toJSONString(rsp));
        return rsp;
    }

    
    
    /**
     * 赞时贷查询币港湾当日可用额度
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/daily_limit", method = RequestMethod.GET)
    @ResponseBody
    public DailyLimitRes queryDailyLimit(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("赞时贷查询币港湾当日可用额度入参:" + token + "/" + requestTime + "/" + clientKey);

        //客户端校验
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(requestTime)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        }
        G2BReqMsg_ZsdLoan_QueryDailyLimit g2bReq = new G2BReqMsg_ZsdLoan_QueryDailyLimit();
        G2BResMsg_ZsdLoan_QueryDailyLimit g2bRes = zsdSendBusinessService.queryDailyLimit(g2bReq);

        DailyLimitRes rsp = new DailyLimitRes();
        
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(g2bRes.getResCode())) {
        	 Amount amount = new Amount();
             amount.setAmountNoLimit(g2bRes.getAmountNoLimit());
             rsp.setAmounts(JSON.toJSONString(amount));
        } else {
            errorRes(rsp, g2bRes.getResCode(), g2bRes.getResMsg(), response);
        }

        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("赞时贷查询币港湾当日可用额度返回:" + JSON.toJSONString(rsp));
        return rsp;
    }
    

    /**
     * 查询银行卡限额
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bank_limit", method = RequestMethod.GET)
    @ResponseBody
    public BankLimitRes queryBankLimit(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("查询银行卡限额入参:" + token + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(requestTime)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        }
        BankLimitRes rsp = new BankLimitRes();

        G2BReqMsg_ZsdBankLimit_QueryBankLimit req = new G2BReqMsg_ZsdBankLimit_QueryBankLimit();
        G2BResMsg_ZsdBankLimit_QueryBankLimit res = zsdSendBusinessService.queryBankLimit(req);
        rsp.setLimits(res.getLimits());

        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("查询银行卡限额返回:" + JSON.toJSONString(rsp));
        return rsp;
    }

    
    /**
     * 借款申请
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    @ResponseBody
    public LoanRes addLoan(@RequestBody LoanReq req, HttpServletResponse response) {
        log.info("借款申请入参:{}", JSON.toJSONString(req));
        //客户端校验
        clientCheck(req.getClientKey(), req.getToken());
        //入参校验
        reqMsgValidate(req);
        
        if (req.getLoanRate() == null) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "借款利率不能为空");
        }
        if (req.getHeadFee() == null) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "砍头息不能为空");
        }
        if (StringUtil.isEmpty(req.getBankCard())) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行卡号不能为空");
        }
        if (StringUtil.isEmpty(req.getBankCode())) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行类型不能为空");
        }
        if (StringUtil.isEmpty(req.getIdCard())) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "身份证号不能为空");
        }
        if (StringUtil.isEmpty(req.getCardHolder())) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "持卡人姓名不能为空");
        }
        if (StringUtil.isEmpty(req.getMobile())) {        	
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "手机号不能为空");
        }
        
        
        List<RepaySchedule> list = req.getRepaySchedule();
        for (RepaySchedule repaySchedule : list) {
            if (repaySchedule.getPlatformServiceFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "平台服务费不能为空");
            }
            if (repaySchedule.getInfoCertifiedFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "信息认证费不能为空");
            }
            if (repaySchedule.getRiskManageServiceFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "风控服务费不能为空");
            }
            if (repaySchedule.getCollectionChannelFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代收通道费不能为空");
            }
		}
        
        G2BReqMsg_ZsdLoanApply_AddLoan loanReq = new G2BReqMsg_ZsdLoanApply_AddLoan();

        ModelConverterUtil<LoanReq, G2BReqMsg_ZsdLoanApply_AddLoan> util = new ModelConverterUtil<>();
        req.setBankCard(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getBankCard()));
        req.setIdCard(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getIdCard()));
        req.setCardHolder(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getCardHolder()));
        req.setMobile(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getMobile()));
        util.convertModel(req, loanReq);
        loanReq.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);
        
        G2BResMsg_ZsdLoanApply_AddLoan resp = zsdSendBusinessService.addLoan(loanReq);
        LoanRes res = new LoanRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("借款申请返回:" + JSON.toJSONString(res));
        return res;
    }

    /**
     * 还款确认下单
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/repay", method = RequestMethod.PUT)
    @ResponseBody
    public RepayRes repay(@RequestBody RepayReq req, HttpServletResponse response) {
        log.info("还款确认下单入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        RepayRes res = new RepayRes();
        G2BReqMsg_ZsdRepay_RepayConfirm repayConfirm = new G2BReqMsg_ZsdRepay_RepayConfirm();
        repayConfirm.setBgwOrderNo(req.getBgwOrderNo());
        repayConfirm.setSmsCode(req.getSmsCode());
        repayConfirm.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);
        G2BResMsg_ZsdRepay_RepayConfirm resp = zsdSendBusinessService.repayConfirm(repayConfirm);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            log.info("还款失败返回:"+resp.getResCode()+","+ resp.getResMsg());
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("还款确认下单返回:" + JSON.toJSONString(res));
        return res;
    }

    /**
     * 还款结果查询
     *
     * @return
     */
    @RequestMapping(value = "/repay", method = RequestMethod.GET)
    @ResponseBody
    public RepayQueryRes queryRepay(HttpServletRequest request, HttpServletResponse response) {
        String orderNo = request.getParameter("orderNo");
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("还款结果查询入参:" + orderNo + "/" + token + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(orderNo))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不能为空");
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        G2BReqMsg_ZsdRepay_QueryRepayResult reqMsg = new G2BReqMsg_ZsdRepay_QueryRepayResult();
        reqMsg.setOrderNo(orderNo);
        G2BResMsg_ZsdRepay_QueryRepayResult resMsg = zsdSendBusinessService.queryResult(reqMsg);
        RepayQueryRes res = new RepayQueryRes();
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            res.setChannel(resMsg.getChannel());
            res.setLoanId(resMsg.getLoanId());
            res.setRepayResultCode(resMsg.getRepayResultCode());
            res.setRepayResultMsg(resMsg.getRepayResultMsg());
        } else {
            errorRes(res, resMsg.getResCode(), resMsg.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("还款结果查询返回:" + JSON.toJSONString(res));
        return res;
    }

    @RequestMapping(value = "/bad_debt", method = RequestMethod.PUT)
    @ResponseBody
    public BadDebtRes badDebt(@RequestBody BadDebtReq req, HttpServletResponse response) {
        log.info("坏账入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        BadDebtRes res = new BadDebtRes();

        List<Repayment> repayments = req.getRepayments();
        for (Repayment repayment : repayments) {
            if (StringUtil.isBlank(repayment.getRepayId())) {
                String errMessage = "|repayId不能为空|";
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
            }
            if (null == repayment.getTotal()) {
                String errMessage = "|total不能为空|";
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
            }
            if (null == repayment.getPrincipal()) {
                String errMessage = "|principal不能为空|";
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
            }
            if (null == repayment.getInterest()) {
                String errMessage = "|interest不能为空|";
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
            }
            if (null == repayment.getLateFee()) {
                String errMessage = "|lateFee不能为空|";
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
            }
            if (null == repayment.getServiceFee()) {
                String errMessage = "|serviceFee不能为空|";
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
            }
        }

        G2BReqMsg_ZsdRepay_BadDebt badDebt = new G2BReqMsg_ZsdRepay_BadDebt();
        badDebt.setOrderNo(req.getOrderNo());
        badDebt.setUserId(req.getUserId());
        badDebt.setLoanId(req.getLoanId());
        badDebt.setRepayments(req.getRepayments());
        badDebt.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);
        G2BResMsg_ZsdRepay_BadDebt resp = zsdSendBusinessService.badDebt(badDebt);

        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("坏账返回:" + JSON.toJSONString(res));
        return res;
    }

    /**
     * 借款结果查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loan", method = RequestMethod.GET)
    @ResponseBody
    public LoanQueryRes queryLoan(HttpServletRequest request, HttpServletResponse response) {
        String orderNo = request.getParameter("orderNo");
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("借款结果查询入参:" + orderNo + "/" + token + "/" + requestTime + "/" + clientKey);
        //客户端校验
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(orderNo)) {
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不能为空");
        }
        if (StringUtil.isEmpty(requestTime)) {
        	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        }
        G2BReqMsg_ZsdLoanApply_QueryLoan queryLoan = new G2BReqMsg_ZsdLoanApply_QueryLoan();
        queryLoan.setOrderNo(orderNo);
        G2BResMsg_ZsdLoanApply_QueryLoan resp = zsdSendBusinessService.queryLoanStatus(queryLoan);
        LoanQueryRes res = new LoanQueryRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setChannel(resp.getChannel());
            res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
            res.setLoanId(resp.getLoanId());
            res.setLoanResultCode(resp.getLoanResultCode());
            res.setLoanResultMsg(resp.getLoanResultMsg());
        }
        log.info("借款结果查询返回:" + JSON.toJSONString(res));
        return res;
    }

    /**
     * 还款预下单
     * @param req
     * @param response  
     * @return
     */
    @RequestMapping(value = "/repay", method = RequestMethod.POST)
    @ResponseBody
    public RepayPreRes addRepay(@RequestBody RepayPreReq req, HttpServletResponse response) {
        log.info("还款预下单入参:" + JSON.toJSONString(req));
        //客户端校验
        clientCheck(req.getClientKey(), req.getToken());
        //入参校验
        reqMsgValidate(req);
        
        if (StringUtil.isEmpty(req.getBankCard()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行卡号不能为空");
        if (StringUtil.isEmpty(req.getBankCode()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行类型不能为空");
        if (StringUtil.isEmpty(req.getIdCard()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "身份证号不能为空");
        if (StringUtil.isEmpty(req.getCardHolder()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "持卡人姓名不能为空");
        if (StringUtil.isEmpty(req.getMobile()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行预留手机号不能为空");

        List<Repayment> list = req.getRepayments();
        for (Repayment repaySchedule : list) {
            if (repaySchedule.getPlatformServiceFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "平台服务费不能为空");
            }
            if (repaySchedule.getInfoCertifiedFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "信息认证费不能为空");
            }
            if (repaySchedule.getRiskManageServiceFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "风控服务费不能为空");
            }
            if (repaySchedule.getCollectionChannelFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代收通道费不能为空");
            }
		}
        
        
        RepayPreRes res = new RepayPreRes();
        G2BReqMsg_ZsdRepay_PreRepay preRepay = new G2BReqMsg_ZsdRepay_PreRepay();
        preRepay.setLoanId(req.getLoanId());
        preRepay.setOrderNo(req.getOrderNo());
        preRepay.setTotalAmount(req.getTotalAmount());
        preRepay.setUserId(req.getUserId());
        preRepay.setIp(req.getIp());
        preRepay.setBankCard(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getBankCard()));
        preRepay.setBankCode(req.getBankCode());
        preRepay.setIdCard(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getIdCard()));
        preRepay.setCardHolder(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getCardHolder()));
        preRepay.setMobile(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getMobile()));
        preRepay.setRepayments(req.getRepayments());
        preRepay.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);

        G2BResMsg_ZsdRepay_PreRepay resp = zsdSendBusinessService.preRepay(preRepay);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setBgwOrderNo(resp.getBgwOrderNo());
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("还款预下单返回:" + JSON.toJSONString(res));
        return res;
    }

    /**
     * 代扣还款
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/cutpayment", method = RequestMethod.POST)
    @ResponseBody
    public WithholdingRepayRes cutpayment(@RequestBody WithholdingRepayReq req, HttpServletResponse response) {
    	log.info("代扣还款入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        
        if (StringUtil.isEmpty(req.getBankCard()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行卡号不能为空");
        if (StringUtil.isEmpty(req.getIdCard()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "身份证号不能为空");
        if (StringUtil.isEmpty(req.getCardHolder()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "持卡人姓名不能为空");
        if (StringUtil.isEmpty(req.getMobile()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "银行预留手机号不能为空");
        if (Constants.REPAY_OffLINE_FLAG_Y.equals(req.getIsOffline()) && StringUtil.isEmpty(req.getOffPayOrderNo()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "线下还款支付单号不能为空");

        List<Repayment> list = req.getRepayments();
        for (Repayment repaySchedule : list) {
            if (repaySchedule.getPlatformServiceFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "平台服务费不能为空");
            }
            if (repaySchedule.getInfoCertifiedFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "信息认证费不能为空");
            }
            if (repaySchedule.getRiskManageServiceFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "风控服务费不能为空");
            }
            if (repaySchedule.getCollectionChannelFee()== null) {        	
            	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代收通道费不能为空");
            }
		}
        
        WithholdingRepayRes res=new WithholdingRepayRes();
        G2BReqMsg_ZsdRepay_CutpaymentRepay cutPaymentRepay=new G2BReqMsg_ZsdRepay_CutpaymentRepay();
        ModelConverterUtil<WithholdingRepayReq, G2BReqMsg_ZsdRepay_CutpaymentRepay> util = new ModelConverterUtil<>();
        req.setBankCard(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getBankCard()));
        req.setIdCard(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getIdCard()));
        req.setCardHolder(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getCardHolder()));
        req.setMobile(new DESUtil(ZsdInConstant.DESKEY).decryptStr(req.getMobile()));
        util.convertModel(req, cutPaymentRepay);
        cutPaymentRepay.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);

        G2BResMsg_ZsdRepay_CutpaymentRepay resp = zsdSendBusinessService.cutpaymentRepay(cutPaymentRepay);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setBgwOrderNo(resp.getBgwOrderNo());
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("代扣还款返回:" + JSON.toJSONString(res));
        return res;
    }
    /**
     * 代偿通知
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/lateRepay", method = RequestMethod.POST)
    @ResponseBody
    public ZsdLateRepayRes lateRepay(@RequestBody ZsdLateRepayReq req, HttpServletResponse response) {
    	log.info("代偿通知入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        
        ZsdLateRepayRes res=new ZsdLateRepayRes();
        //校验
        List<LateRepayment> repaymentList = req.getRepayments();
        if (CollectionUtils.isNotEmpty(repaymentList)) {
        	for(LateRepayment repayment: repaymentList){
				if (StringUtil.isBlank(repayment.getRepayId())) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayId为空");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				if (StringUtil.isBlank(repayment.getUserId())) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":userId为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				
				if (StringUtil.isBlank(repayment.getLoanId())) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":loanId为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				
				if (StringUtil.isBlank(repayment.getRepayType())) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repayType为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				
				if (null == repayment.getRepaySerial()) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":repaySerial为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				if (null == repayment.getTotal()) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":total为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				if (null == repayment.getPrincipal()) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":principal为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
				if (null == repayment.getInterest()) {
					res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
					res.setErrorMsg(DafyInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":interest为空("+repayment.getRepayId()+")");
					res.setResponseTime(DateUtil.format(new Date()));
					return res;
				}
			}
		}
        
        G2BReqMsg_ZsdRepay_LateRepayNotice lateRepayReq=new G2BReqMsg_ZsdRepay_LateRepayNotice();
        
        lateRepayReq.setChannel(Constants.THIRD_SYS_CHANNEL_ZSD);
        
        lateRepayReq.setOrderNo(req.getOrderNo());
        lateRepayReq.setApplyTime(req.getApplyTime());
        lateRepayReq.setFinishTime(req.getFinishTime());
        lateRepayReq.setTotalAmount(req.getTotalAmount() == null ? null :MoneyUtil.divide(req.getTotalAmount(), "100").toString());
        lateRepayReq.setPayOrderNo(req.getPayOrderNo());
        lateRepayReq.setOrderNo(req.getOrderNo());
		ArrayList<HashMap<String, Object>>  list = new ArrayList<HashMap<String, Object>>();
		for (LateRepayment lateRepays : req.getRepayments()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("userId", lateRepays.getUserId());
			map.put("loanId", lateRepays.getLoanId());
			map.put("repayId", lateRepays.getRepayId());
			map.put("repaySerial", lateRepays.getRepaySerial());
			map.put("repayType", lateRepays.getRepayType());
			map.put("total",  lateRepays.getTotal() == null ? null : MoneyUtil.divide(lateRepays.getTotal(), 100).doubleValue());
			map.put("principal", lateRepays.getPrincipal() == null ? null : MoneyUtil.divide(lateRepays.getPrincipal(), 100).doubleValue());
			map.put("interest", lateRepays.getInterest() == null ? null : MoneyUtil.divide(lateRepays.getInterest(), 100).doubleValue());
			map.put("principalOverdue",  lateRepays.getPrincipalOverdue() == null ? null : MoneyUtil.divide(lateRepays.getPrincipalOverdue(), 100).doubleValue());
			map.put("interestOverdue", lateRepays.getInterestOverdue()== null ? null : MoneyUtil.divide(lateRepays.getInterestOverdue(), 100).doubleValue());
			map.put("reservedField1", lateRepays.getReservedField1());
			list.add(map);
		}
		lateRepayReq.setRepayments(list);
        
        G2BResMsg_ZsdRepay_LateRepayNotice resp = zsdSendBusinessService.lateRepayNotice(lateRepayReq);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {

        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("代偿通知返回:" + JSON.toJSONString(res));
        return res;
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public TokenRes token(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String clientSecret = request.getParameter("clientSecret");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("token获取入参:" + clientSecret + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, null);
        if (StringUtil.isEmpty(clientSecret))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "客户端密码不能为空");
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        TokenRes res = new TokenRes();
        if (ZsdInConstant.CLIENTKEY.equals(clientKey) && ZsdInConstant.CLIENTSECRET.equals(clientSecret)) {
            res.setResponseMsg("登录成功");
            res.setToken(UUID.randomUUID().toString());
            jsClientDaoSupport.setString(res.getToken(), "zsd_access_token", 60 * 60 * 24);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            res.setResponseMsg("客户端数据异常");
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("token获取返回:" + JSON.toJSONString(res));
        return res;
    }



    /**
     * 客户端校验
     *
     * @param clientKey
     * @param token
     */
    private void clientCheck(String clientKey, String token) {
        if (!ZsdInConstant.CLIENTKEY.equals(clientKey)) {
            throw new PTMessageException(PTMessageEnum.CLIENT_KEY_ERROR);
        }
        if (StringUtil.isNotEmpty(token)) {
            try {
                String zsdAccessToken = jsClientDaoSupport.getString("zsd_access_token");
                if (!token.equals(zsdAccessToken))
                    throw new PTMessageException(PTMessageEnum.TOKEN_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof PTMessageException) {
                    throw (PTMessageException) e;
                }
                throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
            }
        }
    }

    /**
     * 请求消息字段校验
     *
     * @param checkReq
     */
    private void reqMsgValidate(BaseReqModel checkReq) {
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BaseReqModel>> violations = validator.validate(checkReq);
        if (violations != null && violations.size() > 0) {
            String errMessage = "|";
            for (ConstraintViolation<BaseReqModel> constraintViolation : violations) {
                errMessage += constraintViolation.getMessage() + "|";
            }
            log.error("入参错误：" + errMessage);
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
        }
    }

    /**
     * 公共错误响应
     *
     * @param rsp
     * @param code
     * @param msg
     * @param response
     */
    private void errorRes(BaseResModel rsp, String code, String msg, HttpServletResponse response) {
        //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        PTMessageEnum msgEnum = PartnerMessageEnum.getPTMessageEnumByBizCode(code);
        if(msgEnum != null){
            rsp.setErrorCode(msgEnum.getCode());
            rsp.setErrorMsg(msgEnum.getMessage());
        }else{
            rsp.setErrorCode(PTMessageEnum.TRANS_ERROR.getCode());
            rsp.setErrorMsg(msg);
        }
        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

}
