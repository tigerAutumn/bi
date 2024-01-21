package com.pinting.gateway.baofoo.out.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.baofoo.out.enums.BaofooAgreementPayTxnType;
import com.pinting.gateway.baofoo.out.model.req.QueryOrderReq;
import com.pinting.gateway.baofoo.out.model.req.SinglePayReq;
import com.pinting.gateway.baofoo.out.model.resp.QueryOrderResp;
import com.pinting.gateway.baofoo.out.model.resp.SinglePayResp;
import com.pinting.gateway.baofoo.out.service.SinglePayService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.BaoFooMessageUtil;
import com.pinting.gateway.baofoo.out.util.FormatUtil;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;
import com.pinting.gateway.baofoo.out.util.PaymentChannelUtil;
import com.pinting.gateway.baofoo.out.util.RsaCodingUtil;
import com.pinting.gateway.baofoo.out.util.SecurityUtil;
import com.pinting.gateway.baofoo.out.util.rsa.SignatureUtils;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.baofoo.model.RiskItems;
import com.pinting.gateway.util.HttpClientUtil;

@Service
public class SinglePayServiceImpl implements SinglePayService {
	private static Logger logger = LoggerFactory.getLogger(SinglePayServiceImpl.class);
	
	
	@Override
	public SinglePayResp singlePay(SinglePayReq req) throws Exception {
		PaymentChannelInfo channel = PaymentChannelUtil.channelInfoMap.get(req.getMember_id());
		
		//String send_time=DateUtil.format(new Date());//报文发送日期时间		
		String  pfxpath = channel.getSinglePayPfxPath();//商户私钥        
        String  cerpath = channel.getSinglePayCerPath();//宝付公钥
   
        String aesKey = channel.getSinglePayDesKey();//商户自定义(可随机生成  AES key长度为=16位)
		String dgtl_envlp = "01|"+aesKey;//使用接收方的公钥加密后的对称密钥，并做Base64转码，明文01|对称密钥，01代表AES[密码商户自定义]
		logger.info("密码dgtl_envlp："+dgtl_envlp);	
		dgtl_envlp = RsaCodingUtil.encryptByPubCerFile(SecurityUtil.Base64Encode(dgtl_envlp), cerpath);//公钥加密	
		logger.info("签约协议号："+req.getProtocol_no());		
		String protocolNo = SecurityUtil.AesEncryptAgreementPay(SecurityUtil.Base64Encode(req.getProtocol_no()), aesKey);//先BASE64后进行AES加密
		logger.info("签约协议号AES结果:"+protocolNo);
		
		String cardInfo="";//信用卡：信用卡有效期|安全码,借记卡：传空
		//暂不支持信用卡
		//CardInfo = SecurityUtil.AesEncryptAgreementPay(SecurityUtil.Base64Encode(CardInfo), AesKey);//先BASE64后进行AES加密
		
		String returnUrl=BaoFooHttpUtil.singlePayReturnUrl;//交易成功通知地址:最多填写三个地址不同的地址用‘|’连接
		
		Map<String,String> DateArry = new TreeMap<String,String>();
		DateArry.put("send_time", req.getSend_time());
		DateArry.put("msg_id", BaoFooHttpUtil.singlePayMsgidPrefix+req.getTrans_id());//报文流水号BGW+币港湾代扣订单号
		DateArry.put("version", channel.getSinglePayVersion());
		DateArry.put("terminal_id", channel.getSinglePayTerminalId());
		DateArry.put("txn_type", BaofooAgreementPayTxnType.SINGLE_PAY.getCode());//交易类型(参看：文档中《交易类型枚举》)
		DateArry.put("member_id", req.getMember_id());
		DateArry.put("trans_id", req.getTrans_id());
		DateArry.put("dgtl_envlp", dgtl_envlp);
		DateArry.put("user_id", req.getUser_id());//用户在商户平台唯一ID (和绑卡时要一致)
		DateArry.put("protocol_no", protocolNo);//签约协议号（密文）
		DateArry.put("txn_amt", String.valueOf(req.getTxn_amt()));//交易金额 [单位：分  例：1元则提交100]，此处注意数据类型的转转，建议使用BigDecimal类弄进行转换为字串
		DateArry.put("card_info", cardInfo);//卡信息
	
		Map<String,String> RiskItem = new HashMap<String,String>();
		 /**--------风控基础参数-------------**/
		/**
		 * 说明风控参数必须，按商户开通行业、真实交易信息传，不可传固定值。
		 */
		 RiskItems risk = req.getRiskItems();
		 RiskItem.put("goodsCategory", BaoFooHttpUtil.singlePayGoodsCategory);//商品类目 详见附录《商品类目》		 
		 RiskItem.put("userLoginId", risk.getUserLoginId());//用户在商户系统中的登陆名（手机号、邮箱等标识）
		 RiskItem.put("userEmail", risk.getUserEmail());
		 RiskItem.put("userMobile", risk.getUserMobile());//用户手机号		 
		 RiskItem.put("registerUserName", risk.getRegisterUserName());//用户在商户系统中注册使用的名字
		 RiskItem.put("identifyState", risk.getIdentifyState());//用户在平台是否已实名，1：是 ；0：不是
		 RiskItem.put("userIdNo", risk.getUserIdNo());//用户身份证号		 
		 RiskItem.put("registerTime",risk.getRegisterTime());//格式为：YYYYMMDDHHMMSS
		 RiskItem.put("registerIp", risk.getRegisterIp());//用户在商户端注册时留存的IP
		 RiskItem.put("chName",risk.getChName());//持卡人姓名		 
		 RiskItem.put("chIdNo", risk.getChIdNo());//持卡人身份证号
		 RiskItem.put("chCardNo", risk.getChCardNo());//持卡人银行卡号
		 RiskItem.put("chMobile", risk.getChMobile());//持卡人手机
		 if (StringUtil.isEmpty(risk.getChPayIp())) {
			 RiskItem.put("chPayIp", "127.0.0.1");
		 } else {
			 RiskItem.put("chPayIp", risk.getChPayIp());//持卡人支付IP
		 }
		 RiskItem.put("deviceOrderNo",risk.getDeviceOrderNo());//加载设备指纹中的订单号
		 
		 
		 /**--------行业参数  互金消金-------------**/
		 RiskItem.put("tradeType", risk.getTradeType());//交易类型   1-充值 2-还款 3-投标
		 RiskItem.put("customerType", risk.getCustomerType());//用户类型  1-投资人 2-借款人
		 RiskItem.put("hasBalance", risk.getHasBalance());//商户会员账户是否有余额  0-否 1-是
		 RiskItem.put("hasBindCard", risk.getHasBindCard());//商户会员是否绑定银行卡 0-否 1-是
		 RiskItem.put("repaymentDate", risk.getRepaymentDate());//到期还款日   交易类型若为2 YYYYMMDDHHMMSS
		 RiskItem.put("lendingRate",risk.getLendingRate());//借款利率  交易类型若为2 例:10.5%
		 RiskItem.put("bidYields", risk.getBidYields());//标的收益率  交易类型若为3 例:8.5%
		 RiskItem.put("latestTradeDate", risk.getLatestTradeDate());//账户前一次交易日期 
		 DateArry.put("risk_item",JSONObject.fromObject(RiskItem).toString());//放入风控参数
		 
		 DateArry.put("return_url", returnUrl);//最多填写三个地址,不同地址用间使用‘|’分隔
		 
		 String SignVStr = FormatUtil.coverMap2String(DateArry);
		 logger.info("SHA-1摘要字串："+SignVStr);
		 String signature = SecurityUtil.sha1X16(SignVStr, "UTF-8");//签名
		 logger.info("SHA-1摘要结果："+signature);		
		 String Sign = SignatureUtils.encryptByRSA(signature, pfxpath,  channel.getSinglePayPfxPwd());
		 logger.info("RSA签名结果："+Sign);		
		 DateArry.put("signature", Sign);//签名域
		
		 String postResp  = HttpClientUtil.sendRequest(BaoFooHttpUtil.singlePayUrl, DateArry);	
		 logger.info("请求返回:"+postResp);
			
		 //验证返回报文的签名
		 BaoFooMessageUtil.verifySign(postResp,channel);
		 
		 //转换报文
		 SinglePayResp res = (SinglePayResp)BaoFooMessageUtil.parseResp4SinglePay(postResp, "SinglePayResp");
		 
		 if("S".equals(res.getResp_code())){
			 logger.info("支付成功！");
			 return res;
		 }else if("I".equals(res.getResp_code())){	
			logger.info("处理中！");
			throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
		 }else if("F".equals(res.getResp_code())){
			logger.info("失败！");
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, res.getBiz_resp_code() + "," + res.getBiz_resp_msg());
		 }else{
			 logger.info("订单异常！");//异常不得做为订单状态。
			throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
		 }
		
	}


	@Override
	public QueryOrderResp queryOrder(QueryOrderReq req) throws Exception {
		PaymentChannelInfo channel = PaymentChannelUtil.channelInfoMap.get(req.getMember_id());
		
		String send_time=DateUtil.format(new Date());//报文发送日期时间	
		String  pfxpath = channel.getSinglePayPfxPath();//商户私钥        
		
		Map<String,String> DateArry = new TreeMap<String,String>();
		DateArry.put("send_time", send_time);
		DateArry.put("msg_id", UUID.randomUUID().toString().replace("-", ""));//UUID去掉"-",刚好32位
		DateArry.put("version", channel.getSinglePayVersion());
		DateArry.put("terminal_id", channel.getSinglePayTerminalId());
		DateArry.put("txn_type", BaofooAgreementPayTxnType.QUERY_ORDER.getCode());//交易类型
		DateArry.put("member_id", req.getMember_id());
		DateArry.put("orig_trans_id", req.getOrig_trans_id());//交易时的trans_id
		DateArry.put("orig_trade_date", req.getOrig_trade_date());//
		
		 String SignVStr = FormatUtil.coverMap2String(DateArry);
		 logger.info("SHA-1摘要字串："+SignVStr);
		 String signature = SecurityUtil.sha1X16(SignVStr, "UTF-8");//签名
		 logger.info("SHA-1摘要结果："+signature);		
		 String Sign = SignatureUtils.encryptByRSA(signature, pfxpath, channel.getSinglePayPfxPwd());
		 logger.info("RSA签名结果："+Sign);	
		 DateArry.put("signature", Sign);//签名域
		 
		 String postResp  = HttpClientUtil.sendRequest(BaoFooHttpUtil.singlePayUrl, DateArry);	
		 logger.info("请求返回:"+postResp);
			
		 //验证返回报文的签名
		 BaoFooMessageUtil.verifySign(postResp,channel);
		 
		 //转换报文
		 QueryOrderResp res = (QueryOrderResp)BaoFooMessageUtil.parseResp4QueryOrder(postResp, "QueryOrderResp");
		 
		 if("S".equals(res.getResp_code())){
			 logger.info("支付成功！");
			 return res;
		 }else if("I".equals(res.getResp_code())){	
			logger.info("处理中！");
			throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
		 }else if("F".equals(res.getResp_code())){
			logger.info("失败！");
			throw new PTMessageException(PTMessageEnum.BIZ_PAY_FAIL, res.getBiz_resp_code() + "," + res.getBiz_resp_msg());
		 }else{
			 logger.info("订单异常！");//异常不得做为订单状态。
			throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
		 }
		 
	}

}
