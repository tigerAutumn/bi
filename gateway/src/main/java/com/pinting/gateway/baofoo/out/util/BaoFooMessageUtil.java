package com.pinting.gateway.baofoo.out.util;

import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.model.req.BaoFooOutBaseReq;
import com.pinting.gateway.baofoo.out.model.resp.BaoFooBaseResp;
import com.pinting.gateway.baofoo.out.model.resp.QueryOrderResp;
import com.pinting.gateway.baofoo.out.model.resp.SinglePayResp;
import com.pinting.gateway.baofoo.out.util.rsa.SignatureUtils;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.util.Pay19MessageUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/7/18.
 */
public class BaoFooMessageUtil {

    private static Logger logger = LoggerFactory.getLogger(BaoFooMessageUtil.class);
    private final static String respBeanPre = "com.pinting.gateway.baofoo.out.model.resp.";

    /**
     * 内容参数对象转为map
     *
     * @param req
     * @return
     */
    public static HashMap<String, String> transSecurityContentParams(BaoFooOutBaseReq req) {

        HashMap<String, String> map = Pay19MessageUtil.transBeanMap(req);

        map.put("terminal_id", BaoFooHttpUtil.securityterminalId);
        map.put("member_id", BaoFooHttpUtil.memberId);
        map.put("txn_type", BaoFooTxnType.SECURITY_TXN_TYPE);

        return map;
    }


    /**
     * 内容参数对象转为map
     *
     * @param req
     * @param txnType 是否需要交易子类参数
     * @return
     */
    public static HashMap<String, String> transContentParams(BaoFooOutBaseReq req, boolean txnType) {

        HashMap<String, String> map = Pay19MessageUtil.transBeanMap(req);

        map.put("terminal_id", BaoFooHttpUtil.quickterminalId);
        map.put("member_id", BaoFooHttpUtil.memberId);
        if (txnType) {
            map.put("biz_type", BaoFooTxnType.BIZ_TYPE);
        }

        return map;
    }
    
    /**
     * 代扣：内容参数对象转为map
     * @param req
     * @param txnType
     * @return
     */
    public static HashMap<String, String> transContentParams4CutPayment(BaoFooOutBaseReq req, boolean txnType, PaymentChannelInfo channel) {

        HashMap<String, String> map = Pay19MessageUtil.transBeanMap(req);

        map.put("terminal_id", channel != null ? channel.getCutPaymentterminalId() : BaoFooHttpUtil.cutPaymentterminalId);
        //商户号暂不确定
        map.put("member_id", channel != null ? channel.getMerchantNo() : BaoFooHttpUtil.memberId);
        if (txnType) {
            map.put("biz_type", BaoFooTxnType.BIZ_TYPE);
        }

        return map;
    } 

    /**
     * 安全服务公共请求参数
     *
     * @param map
     * @return
     */
    public static HashMap<String, String> commonReqParam(HashMap<String, String> map) {
        map.put("version", BaoFooHttpUtil.securityVersion);
        map.put("member_id", BaoFooHttpUtil.memberId);
        map.put("data_type", BaoFooHttpUtil.dataType);
        map.put("terminal_id", BaoFooHttpUtil.securityterminalId);
        map.put("txn_type", BaoFooTxnType.SECURITY_TXN_TYPE);

        return map;
    }

    /**
     * 公共请求参数
     *
     * @param map
     * @param txnType 是否需要交易类型
     * @return
     */
    public static HashMap<String, String> commonReqParam(HashMap<String, String> map, boolean txnType) {
        map.put("version", BaoFooHttpUtil.version);
        map.put("member_id", BaoFooHttpUtil.memberId);
        map.put("data_type", BaoFooHttpUtil.dataType);
        if (txnType) {
            map.put("terminal_id", BaoFooHttpUtil.quickterminalId);
            map.put("txn_type", BaoFooTxnType.TXN_TYPE);
        } else {
            map.put("terminal_id", BaoFooHttpUtil.pay4terminalId);
        }
        return map;
    }
    
    
    /**
     * 针对不同支付渠道（商户号）的代付公共请求参数
     *
     * @param map
     * @param channel不同支付渠道（商户号）
     * @return
     */
    public static HashMap<String, String> commonReqParam4DFDiffChannel(HashMap<String, String> map, PaymentChannelInfo channel) {
        map.put("version", BaoFooHttpUtil.version);
        map.put("member_id", channel.getMerchantNo());
        map.put("data_type", BaoFooHttpUtil.dataType);
        map.put("terminal_id", channel.getPay4terminalId());
        return map;
    }

    /**
     * 针对不同支付渠道（商户号）的代扣请求参数
     * @param map
     * @param channel
     * @return
     */
    public static HashMap<String, String> commonReqParam4CutPayment(HashMap<String, String> map, PaymentChannelInfo channel) {
        map.put("version", channel == null ? BaoFooHttpUtil.cutVersion : channel.getCutVersion());
        map.put("member_id", channel == null ? BaoFooHttpUtil.memberId : channel.getMerchantNo());
        map.put("data_type", BaoFooHttpUtil.dataType);
        map.put("terminal_id", channel == null ? BaoFooHttpUtil.cutPaymentterminalId : channel.getCutPaymentterminalId());
        map.put("txn_type", BaoFooTxnType.TXN_TYPE);
        return map;
    }

    /**
     * 合作方公共请求参数
     *
     * @param map
     * @param transferEnv 合作方编码
     * @return
     */
    public static HashMap<String, String> commonReqParam(HashMap<String, String> map, PartnerBaoFooEnv transferEnv) {

        logger.info("合作方宝付信息：" + JSONObject.fromObject(transferEnv).toString());
        map.put("version", BaoFooHttpUtil.version);
        map.put("member_id", transferEnv.getMemberIdTo());
        map.put("data_type", BaoFooHttpUtil.dataType);
        map.put("terminal_id", transferEnv.getPay4TerminalId());

        return map;
    }

    /**
     * resp JSON 转对象
     *
     * @param respStr
     * @param className
     * @return
     */
    public static BaoFooBaseResp parseResp(String respStr, String className) throws IOException, ClassNotFoundException {

        //如果返回的数据不包含字符串“trans_content”,则需要解密
        if (StringUtils.isNotBlank(respStr) && !respStr.contains("trans_content")) {
            respStr = RsaCodingUtil.decryptByPubCerFile(respStr, BaoFooHttpUtil.quickcerpath);
            //解密返回值
            respStr = SecurityUtil.Base64Decode(respStr);
        }

        JSONObject jsonObject = JSONObject.fromObject(respStr);
        logger.info(respStr);
        BaoFooBaseResp respBean = (BaoFooBaseResp) JSONObject.toBean(jsonObject, Class.forName(respBeanPre + className));

        return respBean;
    }
    
    /**
     * resp JSON 转对象 ---代扣
     *
     * @param respStr
     * @param className
     * @return
     */
    public static BaoFooBaseResp parseResp4CutPayment(String respStr, String className,PaymentChannelInfo channel) throws IOException, ClassNotFoundException {

        //如果返回的数据不包含字符串“trans_content”,则需要解密
        if (StringUtils.isNotBlank(respStr) && !respStr.contains("trans_content")) {
        	if(channel == null){
        		respStr = RsaCodingUtil.decryptByPubCerFile(respStr, BaoFooHttpUtil.cutPaymentcerpath);
        	}else{
        		respStr = RsaCodingUtil.decryptByPubCerFile(respStr, channel.getCutPaymentcerPath());
        	}
            
            //解密返回值
            respStr = SecurityUtil.Base64Decode(respStr);
        }

        JSONObject jsonObject = JSONObject.fromObject(respStr);
        logger.info(respStr);
        BaoFooBaseResp respBean = (BaoFooBaseResp) JSONObject.toBean(jsonObject, Class.forName(respBeanPre + className));

        return respBean;
    }


	public static void verifySign(String postResp,PaymentChannelInfo channel) throws Exception {
		 Map<String, String> ReturnData = FormatUtil.getParm(postResp);
			
		 if(!ReturnData.containsKey("signature")){
			 throw new Exception("缺少验签参数！");
		 }
		 String RSign=ReturnData.get("signature");
		 logger.info("返回的验签签名值："+RSign);
		 ReturnData.remove("signature");//需要删除签名字段		
		 String RSignVStr = FormatUtil.coverMap2String(ReturnData);
		 logger.info("返回SHA-1摘要字串："+RSignVStr);
		 String RSignature = SecurityUtil.sha1X16(RSignVStr, "UTF-8");//签名
		 logger.info("返回SHA-1摘要结果："+RSignature);
			
		 if(!SignatureUtils.verifySignature(channel.getSinglePayCerPath(),RSignature,RSign)){
			 throw new PTMessageException(PTMessageEnum.VERIFY_SIGNATURE_ERROR);
		 }
		
	}


	public static SinglePayResp parseResp4SinglePay(String respStr,
			String className) throws Exception {
		
		 Map<String, String> returnData = FormatUtil.getParm(respStr);
		 JSONObject jsonObject = JSONObject.fromObject(returnData);
	     logger.info(respStr);
	     SinglePayResp respBean = (SinglePayResp) JSONObject.toBean(jsonObject, Class.forName(respBeanPre + className));
	     return respBean;
	}


	public static QueryOrderResp parseResp4QueryOrder(String respStr,
			String className) throws Exception {
		 Map<String, String> returnData = FormatUtil.getParm(respStr);
		 JSONObject jsonObject = JSONObject.fromObject(returnData);
	     logger.info(respStr);
	     QueryOrderResp respBean = (QueryOrderResp) JSONObject.toBean(jsonObject, Class.forName(respBeanPre + className));
	     return respBean;
	}
}
