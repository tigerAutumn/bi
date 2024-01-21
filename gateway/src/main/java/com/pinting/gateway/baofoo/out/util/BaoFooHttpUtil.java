/**
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 * @author Administrator
 */
/**
 * @author Administrator
 *
 */
package com.pinting.gateway.baofoo.out.util;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.baofoo.out.enums.BalanceUrl;
import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.model.req.*;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BaoFooHttpUtil {

    private static Logger log = LoggerFactory.getLogger(BaoFooHttpUtil.class);
    /**
     * 快捷支付终端号
     */
    public static String quickterminalId = "32601";

    /**
     * 代付终端号
     */
    public static String pay4terminalId = "32631";

    /**
     * 网银终端号
     */
    public static String counterterminalId = "30409";
    
    /**
     * 代扣支付终端号
     */
    public static String cutPaymentterminalId = "32601";


    /**
     * 赞分期余额查询终端号
     */
    public static String zanterminalId = "32476";


    /**
     * 安全服务终端号
     */
    public static String securityterminalId = "31962";

    /**
     * 商户号
     */
    public static String memberId = "1017304";


    /**
     * 宝付私钥
     */
    public static String pfxpath = "d://cer//bigangwan20171214.pfx";


    /**
     * 私钥密码
     */
    public static String pfxpwd = "123567";


    /**
     * 宝付快捷公钥
     */
    public static String quickcerpath = "d://cer//bfkey_1017304@@32601.cer";
    
    /**
     * 宝付代扣公钥
     */
    public static String cutPaymentcerpath = "d://cer//bigangwan20171214.cer";

    /**
     * 宝付代付公钥
     */
    public static String pay4cerpath = "d://cer//bfkey_1017304@@32631.cer";


    /**
     * 宝付安全服务公钥
     */
    public static String securitycerpath = "d://cer//bfkey_912736@@31962.cer";

    /**
     * 版本号
     */
    public static String version = "4.0.0";

    /**
     * 代扣版本号
     */
    public static String cutVersion = "4.0.0.0";

    /**
     * 安全服务版本号
     */
    public static String securityVersion = "4.0.0.0";

    /**
     * 数据格式
     */
    public static String dataType = "json";

    /**
     * 宝付安全服务请求地址
     */
    public static String securityUrl = "https://public.baofoo.com/livesplatform/api/backTransRequest";

    /**
     * 宝付认证交易(快捷支付)请求地址
     */
    public static String baofooQuickUrl = "https://public.baofoo.com/cutpayment/api/backTransRequest";
    
    /**
     * 宝付代扣支付请求地址
     */
    public static String baofooCutPaymentUrl = "https://public.baofoo.com/cutpayment/api/backTransRequest";
    /**
     * 宝付代付交易请求地址前缀
     */
    public static String baofooPay4Url = "https://public.baofoo.com/baofoo-fopay/pay/";

    /**
     * 宝付余额查询请求地址
     */
    public static String baofooBalanceUrl = "https://public.baofoo.com/open-service/query/service.do";

    /**
     * 宝付网银支付请求地址
     */
    public static String baofooCounterUrl = "https://vgw.baofoo.com/payindex";

    /**
     * 宝付网银支付状态查询请求地址
     */
    public static String baofooCounterQueryStatusUrl = "https://vgw.baofoo.com/order/query";

    /**
     * 宝付网银支付异步通知地址
     */
    public static String retrunUrl = "http://183.129.222.138:38083/gateway/remote/baofoo/new_counter/notice";

    /**
     * 网银支付版本号
     */
    public static String counterVersion = "4.0";

    /**
     * 网银md5密钥
     */
    public static String counterMd5Key = "9t6pnlvg6xn7fjz2";


    /**
     * 对账接口版本号
     */
    public static String accountVerion = "4.0.0.1";

    /**
     * 商户下载文件所属 IP
     */
    public static String accountClientIp = "122.234.11.154";

    /**
     * 对账请求地址
     */
    public static String accountUrl = "https://public.baofoo.com/boas/api/fileLoadNewRequest";

    /**
     * 入单对账文件保存路径
     */
    public static String accountFileFiPath = "d:/fi/";

    /**
     * 出款对账文件保存路径
     */
    public static String accountFileFoPath = "d:/fo/";

    /**
     * 赞分期出款对账文件保存路径
     */
    public static String zanAccountFileFoPath="d:/zan/fo";
    

    /**
     * 宝付协议支付请求地址
     */
    public static String singlePayUrl = "https://vgw.baofoo.com/cutpayment/protocol/backTransRequest";
    /**
     * 宝付协议支付交易成功通知地址
     */
    public static String singlePayReturnUrl = "";
    
    /**
     * 宝付协议支付流水号前缀
     */
    public static String singlePayMsgidPrefix = "BGW";

    /**
     * 宝付协议支付行业类目号（互金消金）
     */
    public static String singlePayGoodsCategory = "02";
    
    
    static {
        try {
            if (Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode")) ||
                    Constants.GATEWAY_SERVER_MODE_TEST.equals(GlobEnvUtil.get("server.mode")) ||
                     Constants.GATEWAY_SERVER_MODE_DEV.equals(GlobEnvUtil.get("server.mode"))) {
                memberId = GlobEnvUtil.get("baofoo.member.id");
                quickterminalId = GlobEnvUtil.get("baofoo.quick.terminal.id");
                pay4terminalId = GlobEnvUtil.get("baofoo.pay4.terminal.id");
                pfxpath = GlobEnvUtil.get("baofoo.pfx.url");
                pfxpwd = GlobEnvUtil.get("baofoo.pfx.pwd");
                quickcerpath = GlobEnvUtil.get("baofoo.quick.cer.url");
                pay4cerpath = GlobEnvUtil.get("baofoo.pay4.cer.url");
                version = GlobEnvUtil.get("baofoo.version");
                dataType = GlobEnvUtil.get("baofoo.data_type");
                baofooQuickUrl = GlobEnvUtil.get("baofoo.quick.url");
                baofooPay4Url = GlobEnvUtil.get("baofoo.pay4.url");
                retrunUrl = GlobEnvUtil.get("baofoo.counter.retrunUrl");
                counterVersion = GlobEnvUtil.get("baofoo.counter.version");
                securityVersion = GlobEnvUtil.get("baofoo.security.version");
                counterMd5Key = GlobEnvUtil.get("baofoo.counter.md5.key");
                baofooCounterUrl = GlobEnvUtil.get("baofoo.counter.url");
                accountVerion = GlobEnvUtil.get("baofoo.account.version");
                accountClientIp = GlobEnvUtil.get("baofoo.account.clientIp");
                accountUrl = GlobEnvUtil.get("baofoo.account.url");
                accountFileFiPath = GlobEnvUtil.get("baofoo.account.fileFiPath");
                accountFileFoPath = GlobEnvUtil.get("baofoo.account.fileFoPath");
                counterterminalId = GlobEnvUtil.get("baofoo.counter.terminal.id");
                securityterminalId = GlobEnvUtil.get("baofoo.security.terminal.id");
                securitycerpath = GlobEnvUtil.get("baofoo.security.cer.url");
                baofooBalanceUrl = GlobEnvUtil.get("baofoo.balance.url");
                zanAccountFileFoPath=GlobEnvUtil.get("zan.baofoo.account.fileFoPath");
//                zanMemberId=GlobEnvUtil.get("zan.baofoo.member.id");
//                zanpay4terminalId=GlobEnvUtil.get("zan.baofoo.pay4.terminal.id");
//                zanterminalId=GlobEnvUtil.get("zan.baofoo.terminal.id");
                cutPaymentterminalId = GlobEnvUtil.get("baofoo.cut.terminal.id");
                cutPaymentcerpath = GlobEnvUtil.get("baofoo.cut.cer.url");
                baofooCutPaymentUrl = GlobEnvUtil.get("baofoo.cut.url");
                cutVersion = GlobEnvUtil.get("baofoo.cut.version");
                //协议支付相关公共配置
                singlePayUrl = GlobEnvUtil.get("baofoo.agreementpay.singlepay.url");
                singlePayReturnUrl =  GlobEnvUtil.get("baofoo.agreementpay.singlepay.returnurl");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 安全服务发送请求
     * @return
     */
    public static String securityServiceRequestForm(String url, BaoFooOutBaseReq req, HashMap<String, String> reqPrivateParamMap) throws Exception {

        //把需要加密的内容转成map
        HashMap<String, String> reqParamMap = BaoFooMessageUtil.transSecurityContentParams(req);

        //添加私有参数
        if (MapUtils.isNotEmpty(reqPrivateParamMap)) {
            reqParamMap.putAll(reqPrivateParamMap);
        }

        //转成JSON
        String reqJson = JSONObject.fromObject(reqParamMap).toString();
        log.info("宝付安全服务请求参数：" + reqJson);
        //加密参数
        String base64str = SecurityUtil.Base64Encode(reqJson);
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, BaoFooHttpUtil.pfxpath, BaoFooHttpUtil.pfxpwd);
        //System.out.println(dataContent);

        //组装最终请求参数
        HashMap<String, String> headPostParam = new HashMap();
        headPostParam.put("txn_sub_type", reqPrivateParamMap.get("txn_sub_type"));
        headPostParam.put("data_content", dataContent);

        headPostParam = BaoFooMessageUtil.commonReqParam(headPostParam);

        String respStr = HttpClientUtil.sendRequest(url, headPostParam);
        log.info("baofoo响应报文：【" + respStr + "】");

        return respStr;
    }

    /**
     * 认证支付发送请求
     * @param url 请求地址
     * @param req 请求参数
     * @param reqPrivateParamMap 私有变量
     * @return
     * @throws Exception
     */
    public static String requestForm(String url, BaoFooOutBaseReq req, HashMap<String, String> reqPrivateParamMap) throws Exception {

        //把需要加密的内容转成map
        HashMap<String, String> reqParamMap = BaoFooMessageUtil.transContentParams(req, true);
        String clientIp = "";
        //预支付风险参数特殊处理
        if (reqParamMap.containsKey("risk_content")) {
            clientIp = reqParamMap.get("risk_content");
            reqParamMap.remove("risk_content");
        }
        //添加私有参数
        if (MapUtils.isNotEmpty(reqPrivateParamMap)) {
            reqParamMap.putAll(reqPrivateParamMap);
        }

        //转成JSON
        String reqJson = JSONObject.fromObject(reqParamMap).toString();
        log.info("宝付认证支付请求参数：" + reqJson);

        //预支付风险参数特殊处理
        if (StringUtils.isNotBlank(clientIp)) {
            reqJson = reqJson.substring(0, reqJson.length() - 1);
            reqJson += ",\"risk_content\":{\"client_ip\":\"" + clientIp + "\"}}";
        }
        //加密参数
        String base64str = SecurityUtil.Base64Encode(reqJson);
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, BaoFooHttpUtil.pfxpath, BaoFooHttpUtil.pfxpwd);
        //System.out.println(dataContent);
        //组装最终请求参数
        HashMap<String, String> headPostParam = new HashMap();
        headPostParam.put("txn_sub_type", reqPrivateParamMap.get("txn_sub_type"));
        headPostParam.put("data_content", dataContent);

        headPostParam = BaoFooMessageUtil.commonReqParam(headPostParam, true);

        String respStr = HttpClientUtil.sendRequest(url, headPostParam);
        log.info("baofoo响应报文：【" + respStr + "】");

        return respStr;
    }
    
    /**
     * 代扣支付发送请求
     * @param url 请求地址
     * @param req 请求参数
     * @param reqPrivateParamMap 私有变量
     * @return
     * @throws Exception
     */
    public static String cutPaymentForm(String url, BaoFooOutBaseReq req, HashMap<String, String> reqPrivateParamMap, PaymentChannelInfo channel) throws Exception {
    	//把需要加密的内容转成map
        HashMap<String, String> reqParamMap = BaoFooMessageUtil.transContentParams4CutPayment(req, true, channel);
        
    	 //添加私有参数
        if (MapUtils.isNotEmpty(reqPrivateParamMap)) {
            reqParamMap.putAll(reqPrivateParamMap);
        }
        
        //转成JSON
        String reqJson = JSONObject.fromObject(reqParamMap).toString();
        log.info("宝付代扣支付请求参数：" + reqJson);
        log.info("宝付代扣请求地址："+url);
        //加密参数
        String base64str = SecurityUtil.Base64Encode(reqJson);
        String dataContent = "";
        if(channel == null ){
        	dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, BaoFooHttpUtil.pfxpath, BaoFooHttpUtil.pfxpwd);
        }else{
        	dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, channel.getPfxpath(), channel.getPfxpwd());
        }
        
        //组装最终请求参数
        HashMap<String, String> headPostParam = new HashMap();
        headPostParam.put("txn_sub_type", reqPrivateParamMap.get("txn_sub_type"));
        headPostParam.put("data_content", dataContent);

        headPostParam = BaoFooMessageUtil.commonReqParam4CutPayment(headPostParam,channel);

        String respStr = HttpClientUtil.sendRequest(url, headPostParam);
        log.info("baofoo响应报文：【" + respStr + "】");

        return respStr;
    }

    /**
     * 代付发送请求
     * @param url 请求地址
     * @param outReq 请求参数
     * @return
     *
     * @throws Exception
     */
    public static String requestForm(String url, BaoFooOutBaseReq outReq) throws Exception {

        Pay4AnotherReq req = new Pay4AnotherReq();
        Pay4AnotherTransContent content = new Pay4AnotherTransContent();
        content.setTrans_reqDatas(new ArrayList<Pay4AnotherTransReqData>());
        Pay4AnotherTransReqData reqData = new Pay4AnotherTransReqData();
        reqData.setTrans_reqData(new ArrayList());
        reqData.getTrans_reqData().add(outReq);
        content.getTrans_reqDatas().add(reqData);
        req.setTrans_content(content);

        //转成JSON
        String reqJson = JSON.toJSONString(req);
        log.info("宝付代付请求参数：" + reqJson);
        //加密参数
        String base64str = SecurityUtil.Base64Encode(reqJson);
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, BaoFooHttpUtil.pfxpath, BaoFooHttpUtil.pfxpwd);

        //组装最终请求参数
        HashMap<String, String> headPostParam = new HashMap();
        headPostParam.put("data_content", dataContent);
        log.info("宝付请求地址：" + url);
        headPostParam = BaoFooMessageUtil.commonReqParam(headPostParam, false);
        String respStr = HttpClientUtil.sendRequest(url, headPostParam);
        log.debug("baofoo响应报文：【" + respStr + "】");

        return respStr;
    }

    /**
     * 代付发送请求，针对不同的支付渠道
     * @param url 请求地址
     * @param outReq 请求参数
     * @return
     *
     * @throws Exception
     */
    public static String requestForm4DiffChannel(String url, BaoFooOutBaseReq outReq, PaymentChannelInfo channel) throws Exception {

        Pay4AnotherReq req = new Pay4AnotherReq();
        Pay4AnotherTransContent content = new Pay4AnotherTransContent();
        content.setTrans_reqDatas(new ArrayList<Pay4AnotherTransReqData>());
        Pay4AnotherTransReqData reqData = new Pay4AnotherTransReqData();
        reqData.setTrans_reqData(new ArrayList());
        reqData.getTrans_reqData().add(outReq);
        content.getTrans_reqDatas().add(reqData);
        req.setTrans_content(content);

        //转成JSON
        String reqJson = JSON.toJSONString(req);
        log.info("宝付代付请求参数：" + reqJson);
        //加密参数
        String base64str = SecurityUtil.Base64Encode(reqJson);
        String dataContent = "";
        if(channel == null){
        	dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, BaoFooHttpUtil.pfxpath, BaoFooHttpUtil.pfxpwd);
        }else{
        	dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, channel.getPfxpath(), channel.getPfxpwd());
        }

        //组装最终请求参数
        HashMap<String, String> headPostParam = new HashMap();
        headPostParam.put("data_content", dataContent);
        log.info("宝付请求地址：" + url);
        headPostParam = BaoFooMessageUtil.commonReqParam4DFDiffChannel(headPostParam,channel);
        String respStr = HttpClientUtil.sendRequest(url, headPostParam);
        log.debug("baofoo响应报文：【" + respStr + "】");

        return respStr;
    }

    /**
     * 合作方代付发送请求
     * @param url 请求地址
     * @param outReq 请求参数
     * @return
     * @throws Exception
     */
    public static String requestForm(String url, BaoFooOutBaseReq outReq, PartnerBaoFooEnv transferEnv) throws Exception {

        Pay4AnotherReq req = new Pay4AnotherReq();
        Pay4AnotherTransContent content = new Pay4AnotherTransContent();
        content.setTrans_reqDatas(new ArrayList<Pay4AnotherTransReqData>());
        Pay4AnotherTransReqData reqData = new Pay4AnotherTransReqData();
        reqData.setTrans_reqData(new ArrayList());
        reqData.getTrans_reqData().add(outReq);
        content.getTrans_reqDatas().add(reqData);
        req.setTrans_content(content);

        //转成JSON
        String reqJson = JSON.toJSONString(req);
        log.info("宝付合作方代付请求参数：" + reqJson);
        //加密参数
        String base64str = SecurityUtil.Base64Encode(reqJson);
        String dataContent = RsaCodingUtil.encryptByPriPfxFile(base64str, transferEnv.getPfxPath(), transferEnv.getPfxPwd());

        //组装最终请求参数
        HashMap<String, String> headPostParam = new HashMap();
        headPostParam.put("data_content", dataContent);
        log.info("宝付请求地址：" + url);
        headPostParam = BaoFooMessageUtil.commonReqParam(headPostParam, transferEnv);
        String respStr = HttpClientUtil.sendRequest(url, headPostParam);
        log.debug("baofoo响应报文：【" + respStr + "】");

        return respStr;
    }

    /**
     * 请求是否成功
     *
     * @param statusCode
     * @return
     */
    public static boolean isRequestSuccess(int statusCode) {
        return statusCode == 200;
    }


    /**
     * 网银支付发送请求
     * @param req
     * @return
     * @throws Exception
     */
    public static String requestForm(PcPayGateReq req) {

        String md5 = new String(memberId + BaoFooTxnType.COUNTER_MARK
                + req.getPayId() + BaoFooTxnType.COUNTER_MARK + req.getTradeDate() + BaoFooTxnType.COUNTER_MARK
                + req.getTransId() + BaoFooTxnType.COUNTER_MARK + req.getOrderMoney() + BaoFooTxnType.COUNTER_MARK
                + req.getPageUrl() + BaoFooTxnType.COUNTER_MARK + BaoFooHttpUtil.retrunUrl + BaoFooTxnType.COUNTER_MARK
                + BaoFooTxnType.COUNTER_NOTICE_TYPE + BaoFooTxnType.COUNTER_MARK + counterMd5Key);//MD5签名格式
        log.info("宝付网银支付请求MD5明文：" + md5);
        String signature = SecurityUtil.MD5(md5);//计算MD5值

        String formString = "<body onload=\"pay.submit()\">" +
                "正在提交请稍后。。。。。。。。" +
                "<form method=\"post\" name=\"pay\" id=\"pay\" action=\"" + BaoFooHttpUtil.baofooCounterUrl + "\">" +
                "<input name=\"MemberID\" type=\"hidden\" value=\"" + BaoFooHttpUtil.memberId + "\"/>" +
                //TODO 需要网银终端号
                "<input name=\"TerminalID\" type=\"hidden\" value=\"" + BaoFooHttpUtil.counterterminalId + "\"/>" +
                "<input name=\"InterfaceVersion\" type=\"hidden\" value= \"" + BaoFooHttpUtil.counterVersion + "\"/>" +
                "<input name=\"KeyType\" type=\"hidden\" value= \"" + BaoFooTxnType.COUNTER_KEY_TYPE + "\"/>" +
                "<input name=\"PayID\" type=\"hidden\" value= \"" + (StringUtils.isNotBlank(req.getPayId()) ? req.getPayId() : "") + "\"/>" +
                "<input name=\"TradeDate\" type=\"hidden\" value= \"" + req.getTradeDate() + "\" />" +
                "<input name=\"TransID\" type=\"hidden\" value= \"" + req.getTransId() + "\" />" +
                "<input name=\"OrderMoney\" type=\"hidden\" value= \"" + req.getOrderMoney() + "\"/>" +
                "<input name=\"ProductName\" type=\"hidden\" value= \"" + req.getProductName() + "\"/>" +
                "<input name=\"Amount\" type=\"hidden\" value= \"" + BaoFooTxnType.COUNTER_AMOUNT + "\"/>" +
                "<input name=\"Username\" type=\"hidden\" value= \"" + req.getUserName() + "\"/>" +
                "<input name=\"AdditionalInfo\" type=\"hidden\" value= \"" + req.getAdditionalInfo() + "\"/>" +
                "<input name=\"PageUrl\" type=\"hidden\" value= \"" + req.getPageUrl() + "\"/>" +
                "<input name=\"ReturnUrl\" type=\"hidden\" value= \"" + BaoFooHttpUtil.retrunUrl + "\"/>" +
                "<input name=\"Signature\" type=\"hidden\" value=\"" + signature + "\"/>" +
                "<input name=\"NoticeType\" type=\"hidden\" value= \"" + BaoFooTxnType.COUNTER_NOTICE_TYPE + "\"/>" +
                "</form></body>";

        //System.out.println(formString);
        return formString;
    }

    /**
     * 网银支付状态查询发送请求
     * @param req
     * @return
     */
    public static String[] requestForm(PcStatusQueryReq req) throws Exception {
        String md5 = new String(memberId + BaoFooTxnType.COUNTER_MARK + counterterminalId + BaoFooTxnType.COUNTER_MARK
                + req.getTransID() + BaoFooTxnType.COUNTER_MARK + counterMd5Key);//MD5签名格式

        String md5Sign = SecurityUtil.MD5(md5);//计算MD5值

        Map<String, String> postParams = new HashMap<>();
        postParams.put("MemberID", memberId);
        postParams.put("TerminalID", counterterminalId);
        postParams.put("TransID", req.getTransID());
        postParams.put("MD5Sign", md5Sign);

        String[] respStrs = HttpClientUtil.sendRequestGet(baofooCounterQueryStatusUrl, postParams).split("\\|");

        StringBuilder respMd5 = new StringBuilder();
        respMd5.append(memberId + BaoFooTxnType.COUNTER_MARK);
        respMd5.append(counterterminalId + BaoFooTxnType.COUNTER_MARK);
        respMd5.append(respStrs[2] + BaoFooTxnType.COUNTER_MARK);
        respMd5.append(respStrs[3] + BaoFooTxnType.COUNTER_MARK);
        respMd5.append(respStrs[4] + BaoFooTxnType.COUNTER_MARK);
        respMd5.append(respStrs[5] + BaoFooTxnType.COUNTER_MARK);
        respMd5.append(counterMd5Key);

        String respMD5 = SecurityUtil.MD5(respMd5.toString());

        if (!respMD5.equals(respStrs[6])) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        return respStrs;
    }


    /**
     * 宝付主通道对账
     * @param req
     * @return
     */
    public static void requestForm(CheckAccountFileReq req) throws Exception {

        Map<String, String> postParams = new HashMap<>();
        postParams.put("version", accountVerion);
        postParams.put("member_id", memberId);
        postParams.put("file_type", req.getFileType());
        postParams.put("client_ip", accountClientIp);
        postParams.put("settle_date", req.getSettleDate());

        String res = HttpClientUtil.sendRequestGet(accountUrl, postParams);
        String[] splitStr = res.split("=");    //解板返回的文件参数
        int StrOf = res.indexOf("resp_code=0000");
        if (StrOf < 0) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件下载失败");
        }

        byte[] restr = SecurityUtil.Base64DecodeByte(splitStr[3]);//进行base64解码，解密后为byte类型
        String filename;
        if ("fi".equals(req.getFileType())) {

            if(accountFileFiPath.lastIndexOf("/")!=accountFileFiPath.length()-1)
                accountFileFiPath=accountFileFiPath+"/";

            filename = accountFileFiPath + getDateDay() + "_"+req.getMerchantNo() + ".zip";    //存在本地的路径（自行设置）
        } else {

            if(accountFileFoPath.lastIndexOf("/")!=accountFileFoPath.length()-1)
                accountFileFoPath=accountFileFoPath+"/";
            filename = accountFileFoPath + getDateDay() + "_"+req.getMerchantNo() + ".zip";
        }
        InputStream DateByte = new ByteArrayInputStream(restr);//把获取的zip文件的byte放入输入流
        File targetFile = new File(filename);
        targetFile.createNewFile(); //创建文件
        OutputStream outStream = new FileOutputStream(targetFile);
        byte[] by = new byte[1024];
        while (DateByte.available() > 0) {
            DateByte.read(by); //读取接收的文件流
            outStream.write(by); //写入文件
        }
        DateByte.close();
        outStream.flush();
        outStream.close();
    }
    
    
    /**
     * 宝付辅助通道对账-下载对账文件
     * @param req
     * @return
     */
    public static void requestFormBaoFooAssist(CheckAccountFileReq req) throws Exception {

        Map<String, String> postParams = new HashMap<>();
        postParams.put("version", accountVerion);
        postParams.put("member_id", req.getMerchantNo());
        postParams.put("file_type", req.getFileType());
        postParams.put("client_ip", accountClientIp);
        postParams.put("settle_date", req.getSettleDate());

        String res = HttpClientUtil.sendRequestGet(accountUrl, postParams);
        String[] splitStr = res.split("=");    //解板返回的文件参数
        int StrOf = res.indexOf("resp_code=0000");
        if (StrOf < 0) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件下载失败");
        }

        byte[] restr = SecurityUtil.Base64DecodeByte(splitStr[3]);//进行base64解码，解密后为byte类型
        String filename;
        if ("fi".equals(req.getFileType())) {

            if(accountFileFiPath.lastIndexOf("/")!=accountFileFiPath.length()-1)
                accountFileFiPath=accountFileFiPath+"/";

            filename = accountFileFiPath + getDateDay() + "_"+req.getMerchantNo()+".zip";    //存在本地的路径（自行设置）
        } else {

            if(accountFileFoPath.lastIndexOf("/")!=accountFileFoPath.length()-1)
                accountFileFoPath=accountFileFoPath+"/";
            filename = accountFileFoPath + getDateDay() + "_"+req.getMerchantNo()+".zip";
        }
        InputStream DateByte = new ByteArrayInputStream(restr);//把获取的zip文件的byte放入输入流
        File targetFile = new File(filename);
        targetFile.createNewFile(); //创建文件
        OutputStream outStream = new FileOutputStream(targetFile);
        byte[] by = new byte[1024];
        while (DateByte.available() > 0) {
            DateByte.read(by); //读取接收的文件流
            outStream.write(by); //写入文件
        }
        DateByte.close();
        outStream.flush();
        outStream.close();
    }

    /**
     * 其它对账方对账
     * @param req
     * @return
     */
    public static void requestForm(CheckAccountFileReq req,PartnerBaoFooEnv transferEnv) throws Exception {

        Map<String, String> postParams = new HashMap<>();
        postParams.put("version", accountVerion);
        postParams.put("member_id", transferEnv.getMemberIdTo());
        postParams.put("file_type", req.getFileType());
        postParams.put("client_ip", accountClientIp);
        postParams.put("settle_date", req.getSettleDate());

        String res = HttpClientUtil.sendRequestGet(accountUrl, postParams);
        String[] splitStr = res.split("=");    //解板返回的文件参数
        int StrOf = res.indexOf("resp_code=0000");
        if (StrOf < 0) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "对账文件下载失败");
        }

        byte[] restr = SecurityUtil.Base64DecodeByte(splitStr[3]);//进行base64解码，解密后为byte类型
        String filename;
        if ("fi".equals(req.getFileType())) {

            //TODO 如果后期做入单的对账  需要增加入账文件保存路径
            if(zanAccountFileFoPath.lastIndexOf("/")!=zanAccountFileFoPath.length()-1)
                zanAccountFileFoPath=zanAccountFileFoPath+"/";

            filename = accountFileFiPath + getDateDay() + ".zip";    //存在本地的路径（自行设置）
        } else {

            if(zanAccountFileFoPath.lastIndexOf("/")!=zanAccountFileFoPath.length()-1)
                zanAccountFileFoPath=zanAccountFileFoPath+"/";
            filename = zanAccountFileFoPath + getDateDay() + ".zip";
        }
        InputStream DateByte = new ByteArrayInputStream(restr);//把获取的zip文件的byte放入输入流
        File targetFile = new File(filename);
        targetFile.createNewFile(); //创建文件
        OutputStream outStream = new FileOutputStream(targetFile);
        byte[] by = new byte[1024];
        while (DateByte.available() > 0) {
            DateByte.read(by); //读取接收的文件流
            outStream.write(by); //写入文件
        }
        DateByte.close();
        outStream.flush();
        outStream.close();
    }

    /**
     * 查询余额
     * @param accountType
     * @param transferEnv
     * @return
     * @throws Exception
     */
    public static String requestForm(String accountType, PartnerBaoFooEnv transferEnv) throws Exception {

        String md5 = new String("member_id=" + transferEnv.getMemberIdTo() + "&terminal_id=" + transferEnv.getTerminalId() + "&return_type=" + dataType
                + "&trans_code=" + BalanceUrl.BALANCE_QUERY.getCode() + "&version=" + version + "&account_type=" +accountType
        +"&key="+transferEnv.getMd5Key());//MD5签名格式

        log.info(">>>查询宝付余额 md5加密前参数："+md5+"<<<");
        String md5Sign = SecurityUtil.MD5(md5).toUpperCase();//计算MD5值
        log.debug(">>>查询宝付余额 md5加密后参数："+md5Sign+"<<<");

        Map<String, String> postParams = new HashMap<>();
        postParams.put("member_id", transferEnv.getMemberIdTo());
        postParams.put("terminal_id", transferEnv.getTerminalId());
        postParams.put("return_type", dataType);
        postParams.put("trans_code", BalanceUrl.BALANCE_QUERY.getCode());
        postParams.put("account_type",accountType);
        postParams.put("version", version);
        postParams.put("sign", md5Sign);

        String respStr =HttpClientUtil.sendRequestGet(baofooBalanceUrl, postParams);
        log.debug("baofoo响应报文：【" + respStr + "】");
        return respStr;
    }

    private static String getDateDay() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}