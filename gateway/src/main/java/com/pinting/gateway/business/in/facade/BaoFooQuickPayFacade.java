package com.pinting.gateway.business.in.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.baofoo.out.enums.Pay4AnotherRespCode;
import com.pinting.gateway.baofoo.out.model.req.*;
import com.pinting.gateway.baofoo.out.model.resp.*;
import com.pinting.gateway.baofoo.out.service.*;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;
import com.pinting.gateway.baofoo.out.util.PaymentChannelUtil;
import com.pinting.gateway.baofoo.out.util.TransAccount;
import com.pinting.gateway.baofoo.out.util.TransAccountUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.util.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/8/3.
 */
@Component("BaoFooQuickPay")
public class BaoFooQuickPayFacade {

    private Logger logger = LoggerFactory.getLogger(BaoFooQuickPayFacade.class);

    @Autowired
    private BindCardService bindCardService;
    @Autowired
    private AttestationPayService attestationPayService;
    @Autowired
    private Pay4AnotherService pay4AnotherService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private NewCounterService newCounterService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AccountService accountService;


    /**
     * 发送短信
     * 预绑卡和认证支付预下单都是此接口
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    @Deprecated
    public void sendSms(B2GReqMsg_BaoFooQuickPay_SendSms req,
                        B2GResMsg_BaoFooQuickPay_SendSms resp) throws Exception {

        SendSmsReq sendSmsReq = new SendSmsReq();
        sendSmsReq.setAcc_no(req.getAccNo());
        sendSmsReq.setBind_id(req.getBindId());
        sendSmsReq.setMobile(req.getMobile());
        sendSmsReq.setNext_txn_sub_type(req.getNextTxnSubType());
        sendSmsReq.setTxn_amt(req.getTxnAmt());
        sendSmsReq.setTrans_id(req.getTransId());
        sendSmsReq.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        sendSmsReq.setAdditional_info("");
        sendSmsReq.setReq_reserved("");
        sendSmsReq.setTrans_serial_no(req.getTrans_serial_no());
        smsService.sendSms(sendSmsReq);
    }

    /**
     * 预绑卡
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void bindCard(B2GReqMsg_BaoFooQuickPay_BindCard req,
                         B2GResMsg_BaoFooQuickPay_BindCard resp) throws Exception {

        SendSmsReq sendSmsReq = new SendSmsReq();
        sendSmsReq.setAcc_no(req.getAccNo());
        sendSmsReq.setMobile(req.getMobile());
        sendSmsReq.setNext_txn_sub_type("01");
        sendSmsReq.setTrans_id(req.getTransId());
        sendSmsReq.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        sendSmsReq.setAdditional_info("");
        sendSmsReq.setReq_reserved("");
        sendSmsReq.setTrans_serial_no(req.getTrans_serial_no());
        smsService.sendSms(sendSmsReq);
    }


    /**
     * 确认绑卡
     *
     * @param req
     * @param resp
     */
    public void bindCardConfirm(B2GReqMsg_BaoFooQuickPay_BindCardConfirm req,
                                B2GResMsg_BaoFooQuickPay_BindCardConfirm resp) throws Exception {
        //确认绑卡
        BindCardReq req1 = new BindCardReq();
        req1.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        req1.setTrans_id(req.getTrans_id());
        req1.setSms_code(req.getSms_code());
        req1.setMobile(req.getMobile());
        req1.setPay_code(req.getPay_code());
        req1.setId_holder(req.getId_holder());
        req1.setAcc_no(req.getAcc_no());
        req1.setId_card(req.getId_card());
        req1.setAdditional_info("");
        req1.setReq_reserved("");
        req1.setTrans_serial_no(req.getTrans_serial_no());
        BindCardResp res = bindCardService.bindCard(req1);

        resp.setBind_id(res.getBind_id());
    }

    /**
     * 解绑卡
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void unBindCard(B2GReqMsg_BaoFooQuickPay_UnBindCard req,
                           B2GResMsg_BaoFooQuickPay_UnBindCard res) throws Exception {

        UnbindCardReq unbindCardReq = new UnbindCardReq();
        unbindCardReq.setBind_id(req.getBind_id());
        unbindCardReq.setReq_reserved("");
        unbindCardReq.setAdditional_info("");
        unbindCardReq.setTrans_serial_no(req.getTrans_serial_no());
        unbindCardReq.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        bindCardService.unbindCard(unbindCardReq);
    }

    /**
     * 预支付
     */
    public void quickPay(B2GReqMsg_BaoFooQuickPay_QuickPay req,
                         B2GResMsg_BaoFooQuickPay_QuickPay resp) throws Exception {

        SendSmsReq sendSmsReq = new SendSmsReq();
        sendSmsReq.setBind_id(req.getBindId());
        sendSmsReq.setNext_txn_sub_type("04");
        sendSmsReq.setTxn_amt(req.getTxnAmt());
        sendSmsReq.setTrans_id(req.getTransId());
        sendSmsReq.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        sendSmsReq.setAdditional_info("");
        sendSmsReq.setReq_reserved("");
        sendSmsReq.setTrans_serial_no(req.getTrans_serial_no());
        smsService.sendSms(sendSmsReq);
    }

    /**
     * 确认支付
     *
     * @param req
     */
    public void quickPayConfirm(B2GReqMsg_BaoFooQuickPay_QuickPayConfirm req,
                                B2GResMsg_BaoFooQuickPay_QuickPayConfirm resp) throws Exception {

        //支付
        QuickPayReq req1 = new QuickPayReq();
        req1.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        req1.setSms_code(req.getSms_code());
        req1.setTxn_amt(req.getTxn_amt());
        req1.setTrans_id(req.getTrans_id());
        req1.setBind_id(req.getBind_id());
        req1.setTrans_serial_no(req.getTrans_serial_no());
        req1.setReq_reserved("");
        req1.setAdditional_info(req.getAdditional_info());
        QuickPayResp res = attestationPayService.quickPay(req1);

        resp.setTrans_id(res.getTrans_id());
        resp.setTrans_no(res.getTrans_no());
        resp.setSucc_amt(res.getSucc_amt());
    }

    /**
     * 查询快捷支付状态
     *
     * @param req
     * @param resp
     */
    public void quickPayStatusQuery(B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery req,
                                    B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery resp) throws Exception {

        //查询交易状态
        QuickPayStatusReq req1 = new QuickPayStatusReq();
        req1.setOrig_trans_id(req.getOrig_trans_id());
        req1.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        req1.setTrans_serial_no(req.getTrans_serial_no());
        QuickPayStatusResp res = attestationPayService.quickPayStatusQuery(req1);
        resp.setTrans_id(res.getTrans_id());
        resp.setSucc_amt(res.getSucc_amt());
        resp.setTrans_no(res.getTrans_no());

    }

    /**
     * 代付转账
     *
     * @param req
     * @param res
     */
    public void pay4Trans(B2GReqMsg_BaoFooQuickPay_Pay4Trans req,
                          B2GResMsg_BaoFooQuickPay_Pay4Trans res) throws Exception {

        Pay4AnotherTransReq req1 = new Pay4AnotherTransReq();
        req1.setTo_acc_no(req.getTo_acc_no());
        req1.setTo_acc_dept(req.getTo_acc_dept());
        req1.setTo_acc_name(req.getTo_acc_name());
        req1.setTo_bank_name(req.getTo_bank_name());
        req1.setTo_city_name(req.getTo_city_name());
        req1.setTo_pro_name(req.getTo_pro_name());
        req1.setTrans_money(req.getTrans_money());
        req1.setTrans_no(req.getTrans_no());
        req1.setTrans_summary(req.getTrans_summary());
        req1.setTrans_card_id(req.getTrans_card_id());
        req1.setTrans_mobile(req.getTrans_mobile());
        Pay4AnotherResp<Pay4AnotherTransResp> resp = pay4AnotherService.trans(req1);
        Pay4AnotherTransResp transResp = JSON.parseObject(((Pay4AnotherRespData) resp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherTransResp.class);
        res.setTrans_no(transResp.getTrans_no());
        res.setTrans_orderid(transResp.getTrans_orderid());
        res.setTrans_batchid(transResp.getTrans_batchid());
        res.setTrans_money(transResp.getTrans_money());
        res.setRes_Code(resp.getTrans_content().getTrans_head().getReturn_code());
        res.setRes_Msg(resp.getTrans_content().getTrans_head().getReturn_msg());


    }

    /**
     * 合作方代付转账
     *
     * @param req
     * @param res
     */
    public void partnerPay4Trans(B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans req,
                          B2GResMsg_BaoFooQuickPay_PartnerPay4Trans res) throws Exception {

        Pay4AnotherTransReq req1 = new Pay4AnotherTransReq();
        req1.setTo_acc_no(req.getTo_acc_no());
        req1.setTo_acc_dept(req.getTo_acc_dept());
        req1.setTo_acc_name(req.getTo_acc_name());
        req1.setTo_bank_name(req.getTo_bank_name());
        req1.setTo_city_name(req.getTo_city_name());
        req1.setTo_pro_name(req.getTo_pro_name());
        req1.setTrans_money(req.getTrans_money());
        req1.setTrans_no(req.getTrans_no());
        req1.setTrans_summary(req.getTrans_summary());
        req1.setTrans_card_id(req.getTrans_card_id());
        req1.setTrans_mobile(req.getTrans_mobile());
        Pay4AnotherResp<Pay4AnotherTransResp> resp = pay4AnotherService.partnerTrans(req1,req.getPartner());
        Pay4AnotherTransResp transResp = JSON.parseObject(((Pay4AnotherRespData) resp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherTransResp.class);
        res.setTrans_no(transResp.getTrans_no());
        res.setTrans_money(transResp.getTrans_money());
        res.setRes_Code(resp.getTrans_content().getTrans_head().getReturn_code());
        res.setRes_Msg(resp.getTrans_content().getTrans_head().getReturn_msg());
    }

    /**
     * 代付状态查询
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void pay4StatusQuery(B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req,
                                B2GResMsg_BaoFooQuickPay_Pay4StatusQuery res) throws Exception {

        //转账状态查询
        Pay4AnotherTransStatusQueryReq statusQueryReq = new Pay4AnotherTransStatusQueryReq();
        statusQueryReq.setTrans_no(req.getTrans_no());

        Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> resp;

        if(StringUtils.isBlank(req.getPartner())) {
            resp = pay4AnotherService.transStatusQuery(statusQueryReq);
        }else {
            resp = pay4AnotherService.transStatusQuery(statusQueryReq,req.getPartner());
        }
        Pay4AnotherTransStatusQueryResp queryResp = JSON.parseObject(((Pay4AnotherRespData) resp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherTransStatusQueryResp.class);
        if (queryResp.getState().equals("1")) {
            res.setTrans_no(queryResp.getTrans_no());
            res.setState(queryResp.getState());
            res.setTrans_money(queryResp.getTrans_money());
            res.setTrans_remark(queryResp.getTrans_remark());
            res.setTrans_orderid(queryResp.getTrans_orderid());
        } else if(queryResp.getState().equals("-1")) {
            throw new PTMessageException(PTMessageEnum.BIZ_PAY_FAIL);
        } else {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }
    }


    /**
     * 合作方代付状态查询
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void partnerPay4StatusQuery(B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req,
                                B2GResMsg_BaoFooQuickPay_Pay4StatusQuery res) throws Exception {

        //转账状态查询
        Pay4AnotherTransStatusQueryReq statusQueryReq = new Pay4AnotherTransStatusQueryReq();
        statusQueryReq.setTrans_no(req.getTrans_no());
        Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> resp = pay4AnotherService.transStatusQuery(statusQueryReq,req.getPartner());

        Pay4AnotherTransStatusQueryResp queryResp = JSON.parseObject(((Pay4AnotherRespData) resp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherTransStatusQueryResp.class);
        res.setTrans_no(queryResp.getTrans_no());
        res.setState(queryResp.getState());
        res.setTrans_money(queryResp.getTrans_money());
        res.setTrans_remark(queryResp.getTrans_remark());
        res.setTrans_orderid(queryResp.getTrans_orderid());
    }


    /**
     * 网银支付
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void eBank(B2GReqMsg_BaoFooQuickPay_EBank req,
                      B2GResMsg_BaoFooQuickPay_EBank res) throws Exception {
        PcPayGateReq pcPayGateReq = new PcPayGateReq();
        pcPayGateReq.setPayId(req.getPayId());
        pcPayGateReq.setTradeDate(req.getTradeDate());
        pcPayGateReq.setPageUrl(req.getPageUrl());
        pcPayGateReq.setOrderMoney(req.getOrderMoney());
        pcPayGateReq.setTransId(req.getTransId());
        pcPayGateReq.setUserName("");
        pcPayGateReq.setAdditionalInfo("");
        pcPayGateReq.setProductName("");

        res.setHtmlStr(newCounterService.pcPayGate(pcPayGateReq));
    }

    /**
     * 网银支付状态查询
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void eBankStatusQuery(B2GReqMsg_BaoFooQuickPay_EBankStatusQuery req,
                                 B2GResMsg_BaoFooQuickPay_EBankStatusQuery res) throws Exception {

        PcStatusQueryReq statusQueryReq = new PcStatusQueryReq();
        statusQueryReq.setTransID(req.getTransId());

        PcStatusQueryResp resp = newCounterService.pcStatusQuery(statusQueryReq);

        res.setTransID(resp.getTransID());
        res.setCheckResult(resp.getCheckResult());
        res.setSuccMoney(resp.getSuccMoney());
        res.setSuccTime(resp.getSuccTime());
    }

    /**
     * 查询卡bin
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void cardBinQuery(B2GReqMsg_BaoFooQuickPay_CardBinQuery req, B2GResMsg_BaoFooQuickPay_CardBinQuery res) throws Exception {

        CardBinQueryReq cardBinQueryReq = new CardBinQueryReq();
        cardBinQueryReq.setBank_card_no(req.getBank_card_no());
        cardBinQueryReq.setTrans_serial_no(req.getTrans_serial_no());
        cardBinQueryReq.setTrade_date(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        CardBinQueryResp resp = securityService.queryCardBin(cardBinQueryReq);

        res.setBank_card_no(resp.getBank_card_no());
        res.setBank_description(resp.getBank_description());
        res.setCard_bin(resp.getCard_bin());
        res.setCard_description(resp.getCard_description());
        res.setCard_type(resp.getCard_type());
        res.setPay_code(resp.getPay_code());
    }

    /**
     * 宝付间转账
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void pay4OnlineTrans(B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans req, B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res) throws Exception {

        logger.info("pay4OnlineTrans propertySymbol = {}", req.getPropertySymbol());
        if(com.pinting.gateway.mobile.in.util.Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPropertySymbol())) {
            req.setPropertySymbol(com.pinting.gateway.mobile.in.util.Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
        }
        if(com.pinting.gateway.mobile.in.util.Constants.PRODUCT_PROPERTY_SYMBOL_SEVEN_DAI_SELF.equals(req.getPropertySymbol())) {
            req.setPropertySymbol(com.pinting.gateway.mobile.in.util.Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
        }
        logger.info("pay4OnlineTrans propertySymbol = {}", req.getPropertySymbol());
        TransAccount transAccount= TransAccountUtil.transferEnvMap.get(req.getPropertySymbol());
        logger.info("pay4OnlineTrans 资金接收方信息:transAccount = {}", JSONObject.fromObject(transAccount));
        if(transAccount==null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"转账账户信息未找到");
        }

        Pay4AnotherOnlineTransReq transReq = new Pay4AnotherOnlineTransReq();
        transReq.setTo_acc_no(transAccount.getAccountTo());
        transReq.setTo_acc_name(transAccount.getToAcctName());
        transReq.setTo_member_id(transAccount.getMemberIdTo());
        transReq.setTrans_money(req.getTrans_money());
        transReq.setTrans_no(req.getTrans_no());
        transReq.setTrans_summary(req.getTransSummary());

        Pay4AnotherResp<Pay4AnotherOnlineTransResp> resp = null;
        logger.info("宝付间转账，商户号："+ req.getMerchantNo());
        if(!com.pinting.gateway.mobile.in.util.Constants.PRODUCT_PROPERTY_SYMBOL_MAIN.equals(req.getPropertySymbol())){
            resp = pay4AnotherService.onlineTrans(transReq);
        }else {
            PaymentChannelInfo channel = PaymentChannelUtil.channelInfoMap.get(req.getMerchantNo());
            resp = pay4AnotherService.onlineTrans4DiffChannel(transReq,channel);
        }

        if (Pay4AnotherRespCode.SUCCESS_CODE_00000.getCode().equals(resp.getTrans_content().getTrans_head().getReturn_code())) {
            Pay4AnotherOnlineTransResp transResp = JSON.parseObject(((Pay4AnotherRespData) resp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherOnlineTransResp.class);
            res.setTrans_no(transResp.getTrans_no());
            res.setState(transResp.getState());
            res.setTrans_money(transResp.getTrans_money());
            res.setTrans_remark(transResp.getTrans_remark());
            res.setTrans_orderid(transResp.getTrans_orderid());
        }
    }

    /**
     * 下载对账文件
     *
     * @param req
     * @param res
     * @throws Exception
     */
    public void downLoadFile(B2GReqMsg_BaoFooQuickPay_DownLoadFile req, B2GResMsg_BaoFooQuickPay_DownLoadFile res) throws Exception {

        CheckAccountFileReq fileReq = new CheckAccountFileReq();
        fileReq.setSettleDate(req.getDate());
        fileReq.setFileType(req.getType());
        fileReq.setPartner(req.getPartner());
        fileReq.setMerchantNo(req.getMerchantNo());
        accountService.downloadCheckAccountFile(fileReq);
    }

    /**
     * 查询余额
     * @param req
     * @param res
     */
    public void balanceQuery(B2GReqMsg_BaoFooQuickPay_BalanceQuery req,B2GResMsg_BaoFooQuickPay_BalanceQuery res) throws Exception {

        BalanceQueryReq balanceQueryReq=new BalanceQueryReq();
        balanceQueryReq.setPartner(req.getPartner());
        balanceQueryReq.setAccountType("0");
        BalanceResp<BalanceQueryResp> resp=accountService.queryBalance(balanceQueryReq);

        BalanceQueryResp rep=JSON.parseObject(JSONArray.toJSONString((resp.getTrans_content().getTrans_reqDatas())), BalanceQueryResp.class);

        String balance="0";
        for (BaoFooBalance baoFooBalance:rep.getTrans_reqData()) {

            if(baoFooBalance.getAccount_type().equals("1")){
                balance=baoFooBalance.getBalance();
                break;
            }
        }
        res.setBalance(balance);
    }

}
