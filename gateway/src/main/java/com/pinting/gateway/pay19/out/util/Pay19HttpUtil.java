/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.pay19.out.enums.AcctBalanceUrl;
import com.pinting.gateway.pay19.out.enums.AcctTransUrl;
import com.pinting.gateway.pay19.out.enums.HoldingOrderUrl;
import com.pinting.gateway.pay19.out.enums.NewCounterUrl;
import com.pinting.gateway.pay19.out.enums.NewDFStatus;
import com.pinting.gateway.pay19.out.enums.Pay4AnotherRespCode;
import com.pinting.gateway.pay19.out.enums.Pay4AnotherUrl;
import com.pinting.gateway.pay19.out.enums.QuickPayUrl;
import com.pinting.gateway.pay19.out.enums.RealNameUrl;
import com.pinting.gateway.pay19.out.model.req.AcctBalanceBaseReq;
import com.pinting.gateway.pay19.out.model.req.AcctTransBaseReq;
import com.pinting.gateway.pay19.out.model.req.EBankReq;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderBaseReq;
import com.pinting.gateway.pay19.out.model.req.NewCounterBaseReq;
import com.pinting.gateway.pay19.out.model.req.Pay4AnotherBaseReq;
import com.pinting.gateway.pay19.out.model.req.QuickPayBaseReq;
import com.pinting.gateway.pay19.out.model.req.RealNameBaseReq;
import com.pinting.gateway.pay19.out.model.resp.AcctBalanceBaseResp;
import com.pinting.gateway.pay19.out.model.resp.AcctTransBaseResp;
import com.pinting.gateway.pay19.out.model.resp.HoldingOrderBaseResp;
import com.pinting.gateway.pay19.out.model.resp.NewCounterBaseResp;
import com.pinting.gateway.pay19.out.model.resp.Pay4AnotherBaseResp;
import com.pinting.gateway.pay19.out.model.resp.QuickPayBaseResp;
import com.pinting.gateway.pay19.out.model.resp.RealNameBaseResp;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.HttpClientUtil;
import com.pinting.gateway.util.Pay19CipherUtil;
import com.pinting.gateway.util.Pay19KeyedDigestMD5;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay19HttpUtil.java, v 0.1 2015-8-5 下午5:07:24 BabyShark Exp $
 */
public class Pay19HttpUtil {
    private static Logger log                = LoggerFactory.getLogger(Pay19HttpUtil.class);
    //public static String  merchant_id        = "175244";                                                                                                                          //"190044";                                       //测试商户id
    //public static String  merchant_key       = "z6aaqr4i27ui3mr875ylrry8k06b19t8ffme85jod2kf78ojpv7oxmics07pcv5lx2du4uc70ya62wnk049vap9njrv3nflr267nogifz687bshjsx0ozszp3n4wro15"; //"19pay.com.cn";                                 //测试商户key
    public static String  merchant_id        = "190044";
//    public static String  merchant_id        = "175294";

    public static String  merchant_key       = "oy6z6jn6rh67gjysthbv2gogdvr82wgi58qq5f1kjsjm2am6uv2zg10btz6nrqwzkbowehqcof264s6bbe519eqqp77l5x19g9x35murof6mz5agrol52799t4al4p11";
    private static String quickPayUrlRoot    = "http://114.247.40.72:443/mservice/quickPay/";                                                                                     //GlobEnvUtil.get("19pay.quickpay.url") + "/";
    private static String pay4AnotherUrlRoot = "http://114.247.40.72:443/mservice/trade/";                                                                                        //GlobEnvUtil.get("19pay.pay4Another.url") + "/";
    private static String realNameUrlRoot    = "http://114.247.40.72:443/mservice/infoverify/";                                                                                   //GlobEnvUtil.get("19pay.realName.url") + "/";
    private static String acctBalanceUrlRoot = "http://114.247.40.72:443/mservice/query/";                                                                                              //"http://114.247.40.72:443/mservice/query/";
    private static String acctTransUrlRoot   = "http://114.247.40.72:443/mservice/trans/";
    private static String newCounterEBankUrl = "http://114.247.40.72/counter/newcounter/";
    private static String newCounterRefundUrl= "http://114.247.40.72:443/mservice/refundNew/";
    private static String holdingOrderUrl    = "http://114.247.40.72:443/mservice/withholding/";
    private static String holdingOrderQueryUrl = "http://114.247.40.72:443/mservice/withHoldingQueryNew/";

    static {
        try {
        	if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))){
        		quickPayUrlRoot = GlobEnvUtil.get("19pay.quickpay.url") + "/";
                pay4AnotherUrlRoot = GlobEnvUtil.get("19pay.pay4Another.url") + "/";
                realNameUrlRoot = GlobEnvUtil.get("19pay.realName.url") + "/";
//                acctBalanceUrlRoot = GlobEnvUtil.get("19pay.acctBalance.url") + "/";
                acctTransUrlRoot = GlobEnvUtil.get("19pay.acctTrans.url") + "/";
                merchant_id = Pay19CipherUtil.decryptData(GlobEnvUtil.get("19pay.merchant.id"),
                    Pay19CipherUtil.getDefaultdeskey());
                merchant_key = Pay19CipherUtil.decryptData(GlobEnvUtil.get("19pay.merchant.key"),
                    Pay19CipherUtil.getDefaultdeskey());
                newCounterEBankUrl = GlobEnvUtil.get("19pay.newCounterEBank.url") + "/";
//                newCounterRefundUrl = GlobEnvUtil.get("19pay.newCounterRefund.url") + "/";
            	holdingOrderUrl = GlobEnvUtil.get("19pay.holdingOrder.url") + "/";
//            	holdingOrderQueryUrl = GlobEnvUtil.get("19pay.holdingOrderQuery.url") + "/";
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 快捷支付发起并获得响应
     * 
     * @param url
     * @param req
     * @return
     */
    public static QuickPayBaseResp quickPaySend(QuickPayUrl url, QuickPayBaseReq req) {
        req.setMerchant_id(merchant_id);
        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap,
            Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifystring", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + quickPayUrlRoot + url.getCode() + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(quickPayUrlRoot + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        QuickPayBaseResp resp = (QuickPayBaseResp) Pay19MessageUtil.parseResp(respStr,
            url.getCode());

        return resp;
    }

    /**
     * 代付
     * 
     * @param url
     * @param req
     * @return
     */
    public static Pay4AnotherBaseResp pay4AnotherSend(Pay4AnotherUrl url, Pay4AnotherBaseReq req) {
        req.setMxId(merchant_id);

        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap,
            Pay19KeyedDigestMD5.encode_GBK);
        reqMap.put("hmac", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + pay4AnotherUrlRoot + url.getCode()
                 + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(pay4AnotherUrlRoot + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        
        Pay4AnotherBaseResp resp = new Pay4AnotherBaseResp();
        //通讯异常情况
        if(StringUtil.isEmpty(respStr) || "null".equals(respStr)){
        	//解析响应报文，验签
            resp.setRetCode(Pay4AnotherRespCode.SUCCESS_CODE_00000.getCode());
            resp.setStatus(NewDFStatus.SUCCESS.getCode());
        }else{
        	//解析响应报文，验签
            resp = (Pay4AnotherBaseResp) Pay19MessageUtil.parseResp(respStr,
                url.getCode());
        }
        return resp;
    }

    /**
     * 实名认证
     * 
     * @param url
     * @param req
     * @return
     */
    public static RealNameBaseResp realNameSend(RealNameUrl url, RealNameBaseReq req) {
        req.setMerchantId(merchant_id);
        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap,
            Pay19KeyedDigestMD5.encode_GBK);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + realNameUrlRoot + url.getCode() + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(realNameUrlRoot + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        RealNameBaseResp resp = (RealNameBaseResp) Pay19MessageUtil.parseResp(respStr,
            url.getCode());

        return resp;
    }

    /**
     * 余额查询
     * 
     * @param url
     * @param req
     * @return
     */
    public static AcctBalanceBaseResp acctBalanceSend(AcctBalanceUrl url, AcctBalanceBaseReq req) {
        req.setMerchantId(merchant_id);
        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap,
            Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + acctBalanceUrlRoot + url.getCode()
                 + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(acctBalanceUrlRoot + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        AcctBalanceBaseResp resp = (AcctBalanceBaseResp) Pay19MessageUtil.parseResp(respStr,
            url.getCode());

        return resp;
    }

    public static AcctTransBaseResp acctTransSend(AcctTransUrl url, AcctTransBaseReq req) {
        req.setMerchantId(merchant_id);
        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap,
            Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + acctTransUrlRoot + url.getCode()
                 + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(acctTransUrlRoot + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        AcctTransBaseResp resp = (AcctTransBaseResp) Pay19MessageUtil.parseResp(respStr,
            url.getCode());

        return resp;
    }
    
    /**
     * 新网银支付请求接口(进入19付收银台)
     * 
     * @param url
     * @param req
     * @return
     */
    public static String newCounterEBankSend(NewCounterUrl url, EBankReq req) {
        req.setMerchantId(merchant_id);

        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap, Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + newCounterEBankUrl + url.getCode() + "】");
        //发送请求
        String sendStr = HttpClientUtil.sendFormRequest(newCounterEBankUrl + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + sendStr + "】");
        return sendStr;
    }
    
    /**
     * 
     * @Title: newCounterSend
     * @Description: 网银支付退款接口
     * @param @param url
     * @param @param req
     * @param @return
     * @return NewCounterBaseResp
     * @throws
     */
    public static NewCounterBaseResp newCounterRefund(NewCounterUrl url, NewCounterBaseReq req) {
        req.setMerchantId(merchant_id);
        Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
        //消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap, Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + newCounterRefundUrl + url.getCode() + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(newCounterRefundUrl + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        NewCounterBaseResp resp = (NewCounterBaseResp) Pay19MessageUtil.parseRespRide(respStr, url.getCode());

        return resp;
    }
    
    /**
     * 
     * @Title: holdingOrder
     * @Description: 代扣下单接口
     * @param @param url
     * @param @param req
     * @param @return
     * @return HoldingOrderBaseResp
     * @throws
     */
    public static HoldingOrderBaseResp holdingOrder(HoldingOrderUrl url, HoldingOrderBaseReq req) {
    	req.setMerchantId(merchant_id);
    	Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
    	//消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap, Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + holdingOrderUrl + url.getCode() + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(holdingOrderUrl + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        HoldingOrderBaseResp resp = (HoldingOrderBaseResp) Pay19MessageUtil.parseResp(respStr, url.getCode());

        return resp;
    }
    
    /**
     * 
     * @Title: holdingOrderQuery
     * @Description: 代扣订单查询接口
     * @param @param url
     * @param @param req
     * @param @return
     * @return HoldingOrderBaseResp
     * @throws
     */
    public static HoldingOrderBaseResp holdingOrderQuery(HoldingOrderUrl url, HoldingOrderBaseReq req) {
    	req.setMerchantId(merchant_id);
    	Map<String, String> reqMap = Pay19MessageUtil.transBeanMap(req);
    	//消息摘要明文组装及加密
        String verifyString = Pay19MessageUtil.buildVerifyString(reqMap, Pay19KeyedDigestMD5.encode_UTF8);
        reqMap.put("verifyString", verifyString);
        log.info("19Pay交易名称：【" + url.getDescription() + "：" + holdingOrderQueryUrl + url.getCode() + "】");
        //发送请求，获得响应
        String respStr = HttpClientUtil.sendRequest(holdingOrderQueryUrl + url.getCode(), reqMap);
        log.info("19Pay响应报文：【" + respStr + "】");
        //解析响应报文，验签
        HoldingOrderBaseResp resp = (HoldingOrderBaseResp) Pay19MessageUtil.parseResp(respStr, url.getCode());

        return resp;
    }

    public static void main(String[] args) {
    	/*转账订单查询(收款方)*/
    	/*QueryRecvAcctTransReq req = new QueryRecvAcctTransReq();
        req.setOriOutMxId("175227");
        req.setOriOutOrderDate("20151120");
        req.setOriOutOrderId("144800733811b3c41420");
        req.setTradeType("TRANSFER");
        req.setTs(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        acctTransSend(AcctTransUrl.QUERY_RECV_ACCT_TRANS, req);*/
        /*转账订单查询*/
        /*QueryAcctTransReq req = new QueryAcctTransReq();
        req.setOrderDate("20151104141611");
        req.setOrderId("1111111111111111112");
        req.setTradeType("TRANSFER");
        req.setTs(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        acctTransSend(AcctTransUrl.QUERY_ACCT_TRANS, req);*/
        /*账户转账*/
        /*AcctTransReq req = new AcctTransReq();
        req.setAccountFrom("zhouchangzai@dafy.com");
        req.setAccountTo("zhulingna@dafy.com");
        req.setNotifyUrl("http://121.43.110.214/remote/pay19/acctTrans/notice");
        req.setOrderAmount("0.01");
        req.setOrderDate(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        req.setOrderId("1111111111111111111");
        req.setRemarks("测试：泓淳测试到泓淳生产");
        req.setToAcctName("泓淳(杭州)科技有限公司");
        req.setToAcctType("ENTPRISE");
        req.setTradeDesc("转账测试");
        req.setTradeType("TRANSFER");
        AcctTransBaseResp resp = acctTransSend(AcctTransUrl.ACCT_TRANS, req);
        System.out.println(resp.getRetCode());*/
        /*余额查询*/
        /*QueryAcctBalanceReq req0 = new QueryAcctBalanceReq();
        req0.setAccount("zhouchangzai@dafy.com");
        req0.setReqDate(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        req0.setReqSno("1111111111111111111");
        QueryAcctBalanceResp resp = (QueryAcctBalanceResp) acctBalanceSend(
            AcctBalanceUrl.QUERY_ACCT_BALANCE, req0);
        System.out.println(resp.getTotalBalance());*/

        /*快捷支付*/
        /*QueryBankListReq req = new QueryBankListReq();
        req.setTs(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        quickPaySend(QuickPayUrl.QUERY_BANK_LIST, req);*/

        /*代付*/
        /* MerchantDfQueryReq req1 = new MerchantDfQueryReq();
         req1.setMxOrderId("1111111111111111111");
         req1.setReqTime(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
         req1.setTradeDate(DateUtil.formatDateTime(new Date(), "yyyyMMdd"));
         pay4AnotherSend(Pay4AnotherUrl.MERCHANTDF_QUERY, req1);*/

        /*try {
            System.out
                .println(Pay19CipherUtil
                    .decryptData(
                        "c27b967c9069c5cb28101ab67631d752d9b53567ff6c0b83d76583dd749c7b819ad057b0e47fbec9606c875c83aa8eff222d6368dfa2ad908bc04b086e88d1b116165a7d0a0456e54c38d0b0821c8653247c6ad3434a402f3f457a8e9426fbd6e0dd64152bdeca0dba287f147b232074742e4c27328119e855720d0cffb82fa76c9d3dda9a4e4e67",
                        Pay19CipherUtil.getDefaultdeskey()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    	
    	/*System.out.println(Pay19CipherUtil.encryptData("175294", Pay19CipherUtil.getDefaultdeskey()));
    	System.out.println(Pay19CipherUtil.encryptData("oy6z6jn6rh67gjysthbv2gogdvr82wgi58qq5f1kjsjm2am6uv2zg10btz6nrqwzkbowehqcof264s6bbe519eqqp77l5x19g9x35murof6mz5agrol52799t4al4p11", Pay19CipherUtil.getDefaultdeskey()));
    	 */
        /*卡实名认证
         * RealNameVerifyReq req = new RealNameVerifyReq();
        req.setMxUserId("15723");
        req.setMxReqSno("157230000000001");
        req.setMxReqDate(DateUtil.formatDateTime(new Date(), DateUtil.DATE_SHORT_FORMAT));
        req.setCardHolder(Pay19CipherUtil.encryptData("王琪冲", Pay19HttpUtil.merchant_key));
        req.setIdType("00");
        req.setIdNo(Pay19CipherUtil.encryptData(Util.formatIdNo2Upper("430421199609124914"),
            Pay19HttpUtil.merchant_key));
        req.setMobile(Pay19CipherUtil.encryptData("15521082707", Pay19HttpUtil.merchant_key));
        req.setPcId("POSTGC");
        req.setBankCardNo(Pay19CipherUtil.encryptData("6217995800018568521",
            Pay19HttpUtil.merchant_key));
        req.setCardType("DEBIT");
        req.setCardAttr("PRIVATE");
        req.setCvv2(null);
        req.setValidDate(null);
        //req.setNotifyUrl(request.getNotifyUrl());
        req.setNotifyUrl("http://gateway.51wenzi.com/remote/pay19/realname/notice");
        req.setRemark("rengong");
        req.setReserved(null);
        RealNameVerifyResp resp = (RealNameVerifyResp) realNameSend(RealNameUrl.REAL_NAME_VERIFY,
            req);
        System.out.println(resp.getTradeStatus());*/
    	
    	//网银退款
//    	RefundReq req = new RefundReq();
//    	req.setAmount("0.01");
//    	req.setMxResq("20151109100702");
//    	req.setOriPayOrderId("20151105134313");
//    	req.setNotifyUrl("http://121.43.116.175/remote/pay19/newcounter/refundNotify");
//    	req.setOriPayDate("20151105171246");
//    	req.setTradeDesc("新网银支付退款");
//    	req.setCurrencyType("RMB");
//    	req.setTradeType("E_BANK");
//    	RefundResp resp = (RefundResp) newCounterRefund(NewCounterUrl.REFUND, req);
//    	System.out.println(resp);
    	
    	//代扣下单
//    	HoldingOrderReq req = new HoldingOrderReq();
//    	req.setMxOrderId("201511101409006");
//    	req.setMxUserId("123456789");
//    	req.setMxOrderDate(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
//    	req.setPcId("CCB");
//    	req.setBankCardNum(Pay19CipherUtil.encryptData("6217001540000904076", Pay19HttpUtil.merchant_key));
//    	req.setCardHolder(Pay19CipherUtil.encryptData("朱媛", Pay19HttpUtil.merchant_key));
//    	req.setIdCardNum(Pay19CipherUtil.encryptData("330781198910221128", Pay19HttpUtil.merchant_key));
//    	req.setUserMobile(Pay19CipherUtil.encryptData("15158191349", Pay19HttpUtil.merchant_key));
//    	req.setAmount("0.01");
//    	req.setCurrency("RMB");
//    	req.setNotifyUrl("http://www.baidu.com");
//    	req.setOrderDesc("19付代扣");
//    	req.setTradeType("WITH");
//    	req.setTradeDesc("19付代扣交易");
//    	req.setMxGoodsName("测试:19付代扣");
//    	req.setMxGoodsType("2");
//    	WithholdingOrderResp resp = (WithholdingOrderResp)holdingOrder(HoldingOrderUrl.HOILDINGORDER, req);
//    	System.out.println(resp.getVerifyString());
    	
    	//代扣查询
//    	HoldingOrderQueryReq req = new HoldingOrderQueryReq();
//    	req.setMxOrderId("201511101409006");
//    	req.setMxOrderDate("20151110162652");
//    	req.setMxUserId("123456789");
//    	req.setTs(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
//    	QueryWithHoldingNewResp resp = (QueryWithHoldingNewResp)holdingOrderQuery(HoldingOrderUrl.HOILDINGORDERQUERY, req);
//    	System.out.println(resp);
    }
}
