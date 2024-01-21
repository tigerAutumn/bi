package com.pinting.manage.service.impl;

import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsSysReceiveMoneyMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.util.Constants;
import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.manage.model.SysReturnMoneyNoticeDo;
import com.pinting.manage.service.MSysReturnMoneyService;
import com.pinting.util.DESUtil;
import com.pinting.util.HttpClientUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by babyshark on 2017/7/14.
 */
@Service
public class MSysReturnMoneyServiceImpl implements MSysReturnMoneyService{
    private final static String CLIENT_KEY = "pum4938o62ik2dpo3m";
    private final static String PAY_ORDER_NO = "WZ999999";
    private final static String MERCHANT_ID = "684454";
    private final static String PAY_PLATFORM = "BAOFOO";
    private final static String TRANS_CODE = "sysReturnMoneyNotice";
    private final static Double YUN_DAI_RATE = 0.2;
    private final static Double SEVEN_DAI_RATE = 0.18;
    private static String URL = "http://121.43.110.214:8084/remote/dafy/business?DATA=";
    private final static String TOKEN_DATA = "J%2FqVmWoIferSQYRnl7cHC8Jw6ABdWa7o2FVZNRvmdtlCzgE%2Beh2U5VQkqaZEglLowAkCc76TNEB5il1A64em%2BBBoWGbAb3smz1NhQ5ShrlaYQ50QBiM5Jfj7KFcpkHAdbP%2FQm0G7btWRzjpyy2Yh3s3AAdLr820qO9a4zZGU34E38NEdmB712KELE%2BCIrbUeyTM2uN2n%2BY7gEfuhLwUxtA%3D%3D";
    private final static String dafyInDESKey = "pinting1234567890Oopinting.com1,.*";
    private static String URL_SEVEN_DAI = "http://121.43.110.214:8084/remote/loan7/business?DATA=";
    private final static String TOKEN_DATA_SEVEN_DAI = "5X42fdRD0fjAh3BZHwcWTEFGCajpKu006edjq32FAfyuf9eLclq0XbRnXj9cUvfesV4V2AYgWfdLXyVgnrPKTyHpEOwbIP5NcXa4ty%2FC0Y5blMJL2YwBbdirdLnKcXph3VzyWMhDNt8o8TwFxiYr7hDbiPplxgngIF9KVXcJIUuGrCxshYWtluSHFoM9slNAiJhx60cOXNpvZY%2FKqv9cMYVpDL%2BKjpSv2gneTAbYIA%2Fljpj7j7Mf7eXKmePeZrlYGOp4vmdEUVPkHwE0Pr3eRg%3D%3D";
    private final static String dafyInDESKey_SEVEN_DAI = "CCMu*X.,Yd5Fffy.cAm1,.*";
    private static Logger log = LoggerFactory.getLogger(MSysReturnMoneyServiceImpl.class);
    static {
        try {
            if ("prod".equals(GlobEnvUtil.get("server.mode"))) {
                URL = "http://121.43.116.175:8084/remote/dafy/business?DATA=";
                URL_SEVEN_DAI = "http://121.43.116.175:8084/remote/loan7/business?DATA=";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Autowired
    private BsBatchBuyMapper bsBatchBuyMapper;
    @Autowired
    private BsSysReceiveMoneyMapper bsSysReceiveMoneyMapper;
    @Autowired
    private BsProductMapper bsProductMapper;
    @Override
    public SysReturnMoneyNoticeDo generateSysReturnMoneyPlan(String productOrderNo) {
        BsBatchBuyExample buyExample = new BsBatchBuyExample();
        buyExample.createCriteria().andSendBatchIdEqualTo(productOrderNo);
        List<BsBatchBuy> buys = bsBatchBuyMapper.selectByExample(buyExample);
        if(CollectionUtils.isEmpty(buys)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        BsBatchBuy buy = buys.get(0);
        if(Constants.BATCHBUY_STATUS_RETURN_SUCCESS.equals(buy.getStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "产品已结算，无需回款");
        }
        SysReturnMoneyNoticeDo buyDo = new SysReturnMoneyNoticeDo();
        buyDo.setPayOrderNo(PAY_ORDER_NO);//人工触发情况，支付单号未知
        buyDo.setClientKey(CLIENT_KEY);
        buyDo.setMerchantId(MERCHANT_ID);
        buyDo.setPayFinshTime(new Date());
        buyDo.setPayPlatform(PAY_PLATFORM);
        buyDo.setPayReqTime(new Date());
        buyDo.setProductCode(buy.getProductCode());
        buyDo.setProductOrderNo(productOrderNo);
        buyDo.setPropertySymbol(buy.getPropertySymbol());
        buyDo.setProductPrincipal(buy.getSendAmount());
        String url = URL;
        if(Constants.PROPERTY_SYMBOL_7_DAI.equals(buy.getPropertySymbol())){
            url = URL_SEVEN_DAI;
        }
        buyDo.setNoticeUrl(url);
        buyDo.setHash("");//待正式通知时生成
        //buyDo.setRequestTime(new Date());
        buyDo.setToken("");//待正式通知时获取
        buyDo.setTransCode(TRANS_CODE);
        //期数及金额计算
        BsSysReceiveMoneyExample moneyExample = new BsSysReceiveMoneyExample();
        moneyExample.createCriteria().andProductOrderNoEqualTo(productOrderNo).andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
        List<BsSysReceiveMoney> moneys = bsSysReceiveMoneyMapper.selectByExample(moneyExample);
        BsProductExample productExample = new BsProductExample();
        productExample.createCriteria().andCodeEqualTo(buy.getProductCode());
        BsProduct product = bsProductMapper.selectByExample(productExample).get(0);
        Integer productTerm = product.getTerm();//产品总期数
        Integer productReturnedTerm = CollectionUtils.isEmpty(moneys) ? 0: moneys.size();//已回款期数
        Integer productReturnTerm = productReturnedTerm + 1;//本次应回款期次
        Double productAmount = 0d;//本次回款本金
        Double productInterest = 0d;//本次回款利息
        Double producntRate = StringUtil.equals(buy.getPropertySymbol(),Constants.PROPERTY_SYMBOL_YUN_DAI)
                ? YUN_DAI_RATE : SEVEN_DAI_RATE;
        //根据起息日和回款日计算期数，并与已回款做比较，判断期数是否正确
        Integer maxCurrReturnTerm = null;//当前允许最大回款期数
        Integer maxReturnTerm = productTerm;//产品允许最大回款期数
        try {
            maxCurrReturnTerm = DateUtil.getDiffeDay(DateUtil.getDateBegin(buy.getSendTime()))/30;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }
        if(productReturnTerm > maxReturnTerm){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "超过产品允许最大回款期数，请检查该批次状态");
        }
        if(productReturnTerm > maxCurrReturnTerm){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请检查本期是否已回款或本期无需回款");
        }

        //根据产品总期数判断是否利息回款或者本息回款
        //=总期数，则本息回款；否则，利息回款
        productAmount = productReturnTerm == productTerm ? buy.getAmount():0d;
        if(productReturnTerm == 12){
            productInterest = MoneyUtil.defaultRound(MoneyUtil.divide(MoneyUtil.multiply(MoneyUtil.multiply(
                    buy.getAmount(), producntRate).doubleValue(),35d).doubleValue(), 365)).doubleValue();
        }else {
            productInterest = MoneyUtil.defaultRound(MoneyUtil.divide(MoneyUtil.multiply(MoneyUtil.multiply(
                    buy.getAmount(), producntRate).doubleValue(),30d).doubleValue(), 365)).doubleValue();
        }
        buyDo.setProductReturnTerm(String.valueOf(productReturnTerm));
        buyDo.setAmount(MoneyUtil.add(productAmount, productInterest).doubleValue());
        buyDo.setProductAmount(productAmount);
        buyDo.setProductInterest(productInterest);
        return buyDo;
    }

    @Override
    public void sysReturnMoneySucc(String productOrderNo) {
        //获得回款数据
        SysReturnMoneyNoticeDo returnData= generateSysReturnMoneyPlan(productOrderNo);
        //获得token值
        String symbol = returnData.getPropertySymbol();
        String url = URL;
        String tokenData = TOKEN_DATA;
        String dafyInDES = dafyInDESKey;
        if(Constants.PROPERTY_SYMBOL_7_DAI.equals(symbol)){
            url = URL_SEVEN_DAI;
            tokenData = TOKEN_DATA_SEVEN_DAI;
            dafyInDES = dafyInDESKey_SEVEN_DAI;
        }
        String tokenStr = HttpClientUtil.sendRequest(url + tokenData, new HashMap<String, String>());
        String decryptData = new DESUtil(dafyInDES).decryptStr(tokenStr);
        log.info("============TOKEN解密获得明文：【" + decryptData + "】============");
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Date.class,
                new JsonValueProcessorImpl());
        decryptData = decryptData.replace("\\", "").replace("\"[{", "[{").replace("}]\"", "}]");
        JSONObject jsonObject = JSONObject.fromObject(decryptData, config);
        String token = jsonObject.getString("token");
        log.info("============获得token：【" + token + "】============");
        //组装并生成hash
        String hashTemplate = "token=%s&transCode=%s&requestTime=%s&clientKey=%s&payPlatform=%s&merchantId=%s&payOrderNo=%s&payReqTime=%s&payFinshTime=%s&amount=%s&productOrderNo=%s&productCode=%s&productAmount=%s&productInterest=%s&productReturnTerm=%s";
        List<String> hashParams = new ArrayList<>();
        hashParams.add(token);
        hashParams.add(TRANS_CODE);
        hashParams.add(DateUtil.format(new Date()));
        hashParams.add(CLIENT_KEY);
        hashParams.add(PAY_PLATFORM);
        hashParams.add(MERCHANT_ID);
        hashParams.add(returnData.getPayOrderNo());
        hashParams.add(DateUtil.format(returnData.getPayReqTime()));
        hashParams.add(DateUtil.format(returnData.getPayFinshTime()));
        hashParams.add(String.format("%.2f", returnData.getAmount()));
        hashParams.add(returnData.getProductOrderNo());
        hashParams.add(returnData.getProductCode());
        hashParams.add(String.format("%.2f", returnData.getProductAmount()));
        hashParams.add(String.format("%.2f", returnData.getProductInterest()));
        hashParams.add(returnData.getProductReturnTerm());
        hashTemplate = formatMsg(hashTemplate, "%s", hashParams);
        log.info("============hash明文：【" + hashTemplate + "】============");
        String hash = MD5Util.encryptMD5(hashTemplate);
        log.info("============hash密文：【" + hash + "】============");
        String returnTemplate = "{\"token\":\"%s\",\"transCode\":\"%s\",\"requestTime\":\"%s\",\"clientKey\":\"%s\",\"payPlatform\":\"%s\",\"merchantId\":\"%s\",\"payOrderNo\":\"%s\",\"payReqTime\":\"%s\",\"payFinshTime\":\"%s\",\"amount\":\"%s\",\"productOrderNo\":\"%s\",\"productCode\":\"%s\",\"productAmount\":\"%s\",\"productInterest\":\"%s\",\"productReturnTerm\":\"%s\",\"hash\":\"%s\"}";
        hashParams.add(hash);
        returnTemplate =  formatMsg(returnTemplate, "%s", hashParams);
        log.info("============回款请求报文明文：【" + hashTemplate + "】============");
        String returnStr = "";
        try {
            returnStr = URLEncoder.encode(new DESUtil(dafyInDES).encryptStr(returnTemplate), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("============回款请求报文密文：【" + returnStr + "】============");
        String resStr = HttpClientUtil.sendRequest(url + returnStr, new HashMap<String, String>());
        String resData = new DESUtil(dafyInDES).decryptStr(resStr);
        log.info("============系统回款通知返回报文解密获得：【" + resData + "】============");
        resData = resData.replace("\\", "").replace("\"[{", "[{").replace("}]\"", "}]");
        JSONObject resJson = JSONObject.fromObject(resData, config);
        String respCode = resJson.getString("respCode");
        String respMsg = resJson.getString("respMsg");
        if(!"SUCC".equals(respCode)){
            throw new PTMessageException(PTMessageEnum.DAFY_RESULT_PROCESSING_ERROR, respMsg);
        }
    }

    private String formatMsg(String template, String templateKey, List<String> params){
        String formatMsg = template;
        for (String param : params) {
            formatMsg = formatMsg.replaceFirst(templateKey, param);
        }
        return formatMsg;
    }

}
