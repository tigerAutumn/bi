package com.pinting.gateway.bird.in.controller;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.bird.in.enums.PartnerMessageEnum;
import com.pinting.gateway.bird.in.model.*;
import com.pinting.gateway.bird.in.util.ModelConverterUtil;
import com.pinting.gateway.business.out.service.BirdSendBusinessService;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.hessian.message.loan.model.Repayment;
import com.pinting.gateway.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 剑钊 on 2016/8/10.
 */
@Controller
@RequestMapping(value = "/rest")
public class BirdController {

    private Logger log = LoggerFactory.getLogger(BirdController.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

    @Autowired
    private BirdSendBusinessService birdSendBusinessService;

    /**
     * 借款人信息登记
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/loaner", method = RequestMethod.POST)
    @ResponseBody
    public LoanerRsp insertLoaner(@RequestBody LoanerReq req, HttpServletRequest request, HttpServletResponse response) {

        log.info("借款人登记入参:" + JSON.toJSONString(req));
        //客户端校验
        clientCheck(req.getClientKey(), req.getToken());
        //入参校验
        reqMsgValidate(req);
        G2BReqMsg_LoanCif_AddLoaner g2bReq = new G2BReqMsg_LoanCif_AddLoaner();
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

        g2bReq.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);

        G2BResMsg_LoanCif_AddLoaner g2bRes = birdSendBusinessService.addLoaner(g2bReq);
        LoanerRsp rsp = new LoanerRsp();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(g2bRes.getResCode())) {
            errorRes(rsp, g2bRes.getResCode(), g2bRes.getResMsg(), response);
        }

        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("借款人信息登记返回:" + JSON.toJSONString(rsp));
        return rsp;
    }

    /**
     * 蜂鸟查询币港湾当日可用额度
     *
     * @return
     */
    @RequestMapping(value = "/daily_limit", method = RequestMethod.GET)
    @ResponseBody
    public DailyLimitRes queryDailyLimit(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("日可用额度查询入参:" + token + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        G2BReqMsg_Loan_DailyAmount req = new G2BReqMsg_Loan_DailyAmount();
        G2BResMsg_Loan_DailyAmount res = birdSendBusinessService.getDailyAmount(req);
        DailyLimitRes rsp = new DailyLimitRes();
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            Amount amount = new Amount();
            amount.setAmount1(res.getAmount1());
            amount.setAmount2(res.getAmount2());
            amount.setAmount3(res.getAmount3());
            amount.setAmount4(res.getAmount4());
            amount.setAmount5(res.getAmount5());
            amount.setAmount6(res.getAmount6());
            amount.setAmount9(res.getAmount9());
            amount.setAmount12(res.getAmount12());
            amount.setAmountNoLimit(res.getAmountNoLimit());
            rsp.setAmounts(JSON.toJSONString(amount));
        } else {
            errorRes(rsp, res.getResCode(), res.getResMsg(), response);
        }
        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("日可用额度查询返回:" + JSON.toJSONString(res));
        return rsp;
    }

    /**
     * 查询银行卡限额
     *
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
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        BankLimitRes rsp = new BankLimitRes();
        G2BReqMsg_BankLimit_LimitList req = new G2BReqMsg_BankLimit_LimitList();
        G2BResMsg_BankLimit_LimitList res = birdSendBusinessService.getBankLimit(req);
        rsp.setLimits(res.getBankLimits());

        rsp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("查询银行卡限额返回:" + JSON.toJSONString(rsp));
        return rsp;
    }

    @RequestMapping(value = "/bankcard", method = RequestMethod.POST)
    @ResponseBody
    public BindPreRes preBindCard(@RequestBody BindPreReq req, HttpServletResponse response) {
        log.info("预绑卡入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        G2BReqMsg_LoanCif_PreBindCard bindCard = new G2BReqMsg_LoanCif_PreBindCard();
        bindCard.setBankCard(req.getBankCard());
        bindCard.setCardHolder(req.getCardHolder());
        bindCard.setIdCard(req.getIdCard());
        bindCard.setMobile(req.getMobile());
        bindCard.setOrderNo(req.getOrderNo());
        bindCard.setUserId(req.getUserId());
        bindCard.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        bindCard.setBankCode(req.getBankCode());
        G2BResMsg_LoanCif_PreBindCard res = birdSendBusinessService.preBindCard(bindCard);

        BindPreRes resp = new BindPreRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            errorRes(resp, res.getResCode(), res.getResMsg(), response);
        } else {
            resp.setBgwOrderNo(res.getBgwOrderNo());
        }

        resp.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("预绑卡返回:" + JSON.toJSONString(res));
        return resp;
    }

    @RequestMapping(value = "/bankcard", method = RequestMethod.PUT)
    @ResponseBody
    public BankCardBindRes bindCard(@RequestBody BankCardBindReq req, HttpServletResponse response) {
        log.info("绑卡入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        G2BReqMsg_LoanCif_BindCardConfirm bindCardConfirm = new G2BReqMsg_LoanCif_BindCardConfirm();
        bindCardConfirm.setBgwOrderNo(req.getBgwOrderNo());
        bindCardConfirm.setSmsCode(req.getSmsCode());

        G2BResMsg_LoanCif_BindCardConfirm resp = birdSendBusinessService.bindCard(bindCardConfirm);

        BankCardBindRes res = new BankCardBindRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setBindId(resp.getBindId());
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("绑卡返回:" + JSON.toJSONString(res));
        return res;
    }

    @RequestMapping(value = "/bankcard", method = RequestMethod.DELETE)
    @ResponseBody
    public BankCardUnBindRes unBindCard(HttpServletRequest request, HttpServletResponse response) {
        String bindId = request.getParameter("bindId");
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("解绑卡入参:" + bindId + "/" + token + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(bindId))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "绑卡编号不能为空");
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");

        G2BReqMsg_LoanCif_UnBindCard req = new G2BReqMsg_LoanCif_UnBindCard();
        req.setBgwBindId(bindId);

        G2BResMsg_LoanCif_UnBindCard resp = birdSendBusinessService.unBindCard(req);

        BankCardUnBindRes res = new BankCardUnBindRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("解绑卡返回:" + JSON.toJSONString(res));

        return res;
    }

    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    @ResponseBody
    public LoanRes insertLoan(@RequestBody LoanReq req, HttpServletResponse response) {
        log.info("借款申请入参:" + JSON.toJSONString(req));
        reqMsgValidate(req);
        
        if (StringUtil.isEmpty(req.getBindId()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "绑卡编号不能为空");
        
        
        G2BReqMsg_Loan_Loan loanReq = new G2BReqMsg_Loan_Loan();

        ModelConverterUtil<LoanReq, G2BReqMsg_Loan_Loan> util = new ModelConverterUtil<>();
        util.convertModel(req, loanReq);

        loanReq.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        G2BResMsg_Loan_Loan resp = birdSendBusinessService.loan(loanReq);

        LoanRes res = new LoanRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("借款申请返回:" + JSON.toJSONString(res));
        return res;
    }


    /**
     * 赞分期营销代付
     * @param req
     * @param response
     * @return
     */
    @RequestMapping(value = "/marketing_trans", method = RequestMethod.POST)
    @ResponseBody
    public MarketingTransRes marketingTrans(@RequestBody MarketingTransReq req, HttpServletResponse response){

        log.info("赞分期营销代付入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);

        G2BReqMsg_Partner_MarketingTrans reqMsg=new G2BReqMsg_Partner_MarketingTrans();
        ModelConverterUtil<MarketingTransReq, G2BReqMsg_Partner_MarketingTrans> util = new ModelConverterUtil<>();
        util.convertModel(req, reqMsg);
        reqMsg.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        G2BResMsg_Partner_MarketingTrans resp=birdSendBusinessService.marketingTrans(reqMsg);

        MarketingTransRes res=new MarketingTransRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return res;
    }

    @RequestMapping(value = "/loan", method = RequestMethod.GET)
    @ResponseBody
    public LoanQueryRes queryLoan(HttpServletRequest request, HttpServletResponse response) {
        String orderNo = request.getParameter("orderNo");
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("借款结果查询入参:" + orderNo + "/" + token + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(orderNo))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不能为空");
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");
        G2BReqMsg_Loan_QueryLoan queryLoan = new G2BReqMsg_Loan_QueryLoan();
        queryLoan.setOrderNo(orderNo);
        G2BResMsg_Loan_QueryLoan resp = birdSendBusinessService.queryLoanStatus(queryLoan);
        LoanQueryRes res = new LoanQueryRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setChannel(resp.getChannel());
            res.setResponseTime(StringUtil.isEmpty(resp.getLoanTime()) ?
                    DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss") : resp.getLoanTime());
            res.setLoanId(resp.getLoanId());
            res.setLoanResultCode(resp.getLoanResultCode());
            res.setLoanResultMsg(resp.getLoanResultMsg());
        }
        log.info("借款结果查询返回:" + JSON.toJSONString(res));
        return res;
    }

    @RequestMapping(value = "/marketing_trans", method = RequestMethod.GET)
    @ResponseBody
    public MarketingTransQueryRes queryMarketingTrans(HttpServletRequest request, HttpServletResponse response) {
        String orderNo = request.getParameter("orderNo");
        String token = request.getParameter("token");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        log.info("营销代付结果查询入参:" + orderNo + "/" + token + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(orderNo))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号不能为空");
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");

        G2BReqMsg_Partner_QueryMarketingTrans queryMarketingTrans = new G2BReqMsg_Partner_QueryMarketingTrans();
        queryMarketingTrans.setOrderNo(orderNo);
        G2BResMsg_Partner_QueryMarketingTrans resp = birdSendBusinessService.queryMarketingTrans(queryMarketingTrans);

        MarketingTransQueryRes res = new MarketingTransQueryRes();
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setChannel(resp.getChannel());
            res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
            res.setResultCode(resp.getResultCode());
            res.setResultMsg(resp.getResultMsg());
            res.setPayTime(resp.getPayTime());
            res.setOrderNo(resp.getOrderNo());
        }
        log.info("营销代付结果查询返回:" + JSON.toJSONString(res));
        return res;
    }

    @RequestMapping(value = "/repay", method = RequestMethod.POST)
    @ResponseBody
    public RepayPreRes insertRepay(@RequestBody RepayPreReq req, HttpServletResponse response) {
        log.info("还款预下单入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        if (StringUtil.isEmpty(req.getBindId()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "绑卡编号不能为空");
        
        reqMsgValidate(req);
        RepayPreRes res = new RepayPreRes();
        G2BReqMsg_Repay_PreRepay preRepay = new G2BReqMsg_Repay_PreRepay();
        preRepay.setLoanId(req.getLoanId());
        preRepay.setOrderNo(req.getOrderNo());
        preRepay.setBindId(req.getBindId());
        preRepay.setTotalAmount(req.getTotalAmount());
        preRepay.setUserId(req.getUserId());
        preRepay.setIp(req.getIp());
        preRepay.setRepayments(req.getRepayments());
        preRepay.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        G2BResMsg_Repay_PreRepay resp = birdSendBusinessService.preRepay(preRepay);

        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setBgwOrderNo(resp.getBgwOrderNo());
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("还款预下单返回:" + JSON.toJSONString(res));
        return res;
    }

    @RequestMapping(value = "/cutpayment", method = RequestMethod.POST)
    @ResponseBody
    public WithholdingRepayRes cutpayment(@RequestBody WithholdingRepayReq req, HttpServletResponse response){

        log.info("还款代扣入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        
        if (StringUtil.isEmpty(req.getBindId()))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "绑卡编号不能为空");
        
        reqMsgValidate(req);

//        if("BINDED".equals(req.getCutTarget()) && StringUtils.isBlank(req.getBindId())){
//            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代扣目标为BINDED，bindId不能为空");
//        }
//
//        if("CUSTOMIZE".equals(req.getCutTarget())
//                && (StringUtils.isBlank(req.getPayUserCardNo()) || StringUtils.isBlank(req.getPayUserIdCard())
//                || StringUtils.isBlank(req.getPayUserMobile()) || StringUtils.isBlank(req.getPayUserName()) ||  StringUtils.isBlank(req.getBankCode()))){
//            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代扣目标为CUSTOMIZE，当前还款用户信息不能为空");
//        }

        WithholdingRepayRes res=new WithholdingRepayRes();

        G2BReqMsg_Repay_WithholdingRepay withholdingRepay=new G2BReqMsg_Repay_WithholdingRepay();
        ModelConverterUtil<WithholdingRepayReq, G2BReqMsg_Repay_WithholdingRepay> util = new ModelConverterUtil<>();
        util.convertModel(req, withholdingRepay);
        withholdingRepay.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);

        G2BResMsg_Repay_WithholdingRepay resp = birdSendBusinessService.repay(withholdingRepay);
        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        } else {
            res.setBgwOrderNo(resp.getBgwOrderNo());
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("还款代扣返回:" + JSON.toJSONString(res));
        return res;

    }

    @RequestMapping(value = "/repay", method = RequestMethod.PUT)
    @ResponseBody
    public RepayRes repay(@RequestBody RepayReq req, HttpServletResponse response) {
        log.info("还款确认下单入参:" + JSON.toJSONString(req));
        clientCheck(req.getClientKey(), req.getToken());
        reqMsgValidate(req);
        RepayRes res = new RepayRes();
        G2BReqMsg_Repay_RepayConfirm repayConfirm = new G2BReqMsg_Repay_RepayConfirm();
        repayConfirm.setBgwOrderNo(req.getBgwOrderNo());
        repayConfirm.setSmsCode(req.getSmsCode());
        repayConfirm.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        G2BResMsg_Repay_RepayConfirm resp = birdSendBusinessService.repayConfirm(repayConfirm);
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
        G2BReqMsg_Repay_QueryRepayResult reqMsg = new G2BReqMsg_Repay_QueryRepayResult();
        reqMsg.setOrderNo(orderNo);
        G2BResMsg_Repay_QueryRepayResult resMsg = birdSendBusinessService.queryResult(reqMsg);
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

    @ResponseBody
    @RequestMapping(value = "/balance",method = RequestMethod.GET)
    public BalanceQueryRes queryBalance(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String clientSecret = request.getParameter("clientSecret");
        String requestTime = request.getParameter("requestTime");
        String clientKey = request.getParameter("clientKey");
        String token = request.getParameter("token");
        log.info("查询宝付余额入参:" + clientSecret + "/" + requestTime + "/" + clientKey);
        clientCheck(clientKey, token);
        if (StringUtil.isEmpty(requestTime))
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请求时间不能为空");

        G2BReqMsg_Partner_QueryBalance reqMsg=new G2BReqMsg_Partner_QueryBalance();
        reqMsg.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        G2BResMsg_Partner_QueryBalance res=birdSendBusinessService.queryBalance(reqMsg);
        BalanceQueryRes resp=new BalanceQueryRes();
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            resp.setBalance(String.valueOf(MoneyUtil.multiply(res.getBalance(),"100").longValue()));
        } else {
            errorRes(resp, res.getResCode(), res.getResMsg(), response);
        }

        return resp;
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
			if (null == repayment.getSuperviseFee()) {
				String errMessage = "|superviseFee不能为空|";
	            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
			}
			if (null == repayment.getInfoServiceFee()) {
				String errMessage = "|infoServiceFee不能为空|";
	            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
			}
			if (null == repayment.getAccountManageFee()) {
				String errMessage = "|accountManageFee不能为空|";
	            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
			}
			if (null == repayment.getPremium()) {
				String errMessage = "|premium不能为空|";
	            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
			}
			if (null == repayment.getOther()) {
				String errMessage = "|other不能为空|";
	            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, errMessage);
			}
		}

        G2BReqMsg_Repay_BadDebt badDebt = new G2BReqMsg_Repay_BadDebt();
        badDebt.setOrderNo(req.getOrderNo());
        badDebt.setUserId(req.getUserId());
        badDebt.setLoanId(req.getLoanId());
        badDebt.setRepayments(req.getRepayments());
        badDebt.setChannel(Constants.THIRD_SYS_CHANNEL_ZAN);
        G2BResMsg_Repay_BadDebt resp = birdSendBusinessService.badDebt(badDebt);

        if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            errorRes(res, resp.getResCode(), resp.getResMsg(), response);
        }
        res.setResponseTime(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.info("坏账返回:" + JSON.toJSONString(res));
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
		res.setErrorCode(DafyInConstant.RETURN_CODE_REFUSE);
		res.setErrorMsg("赞分期无代偿");
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
        if (CLIENTKEY.equals(clientKey) && CLIENTSECRET.equals(clientSecret)) {
            res.setResponseMsg("登录成功");
            res.setToken(UUID.randomUUID().toString());
            jsClientDaoSupport.setString(res.getToken(), "bird_access_token", 60 * 30);
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
        if (!CLIENTKEY.equals(clientKey)) {
            throw new PTMessageException(PTMessageEnum.CLIENT_KEY_ERROR);
        }
        if (StringUtil.isNotEmpty(token)) {
            try {
                String birdAccessToken = jsClientDaoSupport.getString("bird_access_token");
                if (!token.equals(birdAccessToken))
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
    private final static String CLIENTKEY = "ZAN_YI79VweHF57OSI5IEBO0";
    private final static String CLIENTSECRET = "YTGO53LJNsaRF66q78wre";


}
