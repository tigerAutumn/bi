package com.pinting.gateway.util;

import com.google.gson.Gson;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.dafy.out.model.*;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.dafy.out.util.DafyOutMsgUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hfbank.out.enums.NoticeUrl;
import com.pinting.gateway.hfbank.out.model.GatewayRechargeRequestReqModel;
import com.pinting.gateway.hfbank.out.model.GatewayRechargeRequestResModel;
import com.pinting.gateway.hfbank.out.util.HfbankOutConstant;
import com.pinting.gateway.hfbank.out.util.HfbankOutMsgUtil;
import com.pinting.gateway.loan7.out.util.Loan7OutConstant;
import com.pinting.gateway.loan7.out.util.Loan7OutMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.pinting.gateway.util.JsonProcessUtils.json2SingleObject;

/**
 * 外部通信类，用于和外部系统底层通信
 * @Project: gateway
 * @Title: ComminuteUtil.java
 * @author Zhou Changzai
 * @date 2015-2-12 下午12:01:03
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class CommunicateUtil {
	
	private static Logger log = LoggerFactory.getLogger(CommunicateUtil.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);
	public static String clientKey = "pum4938o62ik2dpo3m";
	public static String clientSecret = "3h72ifl6rmvq2v0fmh";
	private static String dafyUrlOld = "http://118.186.255.63:10024/el_open/rest/DFServlet";//达飞旧系统对接地址
	private static String dafyUrl = "http://118.186.255.63:10024/el_open/rest/DFServlet";//达飞对接地址(准线上)
	private static String dafyPayUrl = "http://118.186.255.63:10024/el_open/OnlineRechargeEntranceServlet.svl";//达飞购买对接地址(准线上)
	private static String dafyWithdrawUrl = "http://118.186.255.63:10027/el_open/ZDWXServlet.svl";//达飞提现对接地址(准线上)
	//云贷自主放款
	private static String dafyLoanUrl = "http://bgwgateway.dafy.service:20085/fundproviders.callback";//达飞自主放款
	public static String dafyLoanClientKey = "channeldafykey1230";
	public static String dafyLoanClientSecret = "dafy7987!&Ke6!3";
	
	
	public static String clientLoan7Key = "channeldafykey1230";
	public static String clientLoan7Secret = "dafy7987!&Ke6!3";
	private static String loan7Url ="http://30fe09fd.ngrok.natapp.cn/open-api";

	
	private static final int TIMEOUT = 90; //恒丰请求时间
	
	
	static{
		//云贷配置
		clientKey = GlobEnvUtil.get("dafy.client.key");
		clientSecret = GlobEnvUtil.get("dafy.client.secret");
		dafyUrlOld = GlobEnvUtil.get("dafy.interface.url.old");
		dafyUrl = GlobEnvUtil.get("dafy.interface.url");
		dafyPayUrl = GlobEnvUtil.get("dafy.pay.url");
		dafyWithdrawUrl = GlobEnvUtil.get("dafy.withdraw.url");
		
		
		//七贷配置
		clientLoan7Key=GlobEnvUtil.get("7dai.client.key");
		clientLoan7Secret=GlobEnvUtil.get("7dai.client.secret");
		loan7Url=GlobEnvUtil.get("7dai.interface.url");
		
		
		//云贷自主放款
		dafyLoanUrl = GlobEnvUtil.get("dafy.loan.url");
		dafyLoanClientKey = GlobEnvUtil.get("dafy.loan.client.key");
		dafyLoanClientSecret = GlobEnvUtil.get("dafy.loan.client.secret");
	}
	
	/**
	 * 普通的通讯过程
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static BaseResModel doCommunicate2Dafy(BaseReqModel req){
		//确保已经登录过
		//判断是否已经登录
		String token = getDafyToken();
		if (StringUtil.isEmpty(token) || DafyOutConstant.CUSTOMER_WITHDRAW.equals(req.getTransCode())) {
			if(DafyOutConstant.CUSTOMER_WITHDRAW.equals(req.getTransCode()) || 
					DafyOutConstant.SYS_WITHDRAW.equals(req.getTransCode()) ||
					DafyOutConstant.QUERY_WXACCOUNT_DETAIL.equals(req.getTransCode())){
				token = assureLoginOld();
			}else{
				token = assureLogin();
			}
			
		}
		//开始发起交易
		req.setToken(token);
		String retStr = doSingleCommunicate(req);
		//解密报文，并返回模型对象
		BaseResModel resMsg = DafyOutMsgUtil.parseMsg(retStr, req.getTransCode());
		
		if (resMsg == null) {
			throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
		}
		//判断是否超时，如果超时，重新登录再发起此交易
		if(DafyOutConstant.RETURN_CODE_EXPIRE.equals(resMsg.getRespCode())){
			//超时了，再次登录
			if(DafyOutConstant.CUSTOMER_WITHDRAW.equals(req.getTransCode()) || 
					DafyOutConstant.SYS_WITHDRAW.equals(req.getTransCode()) ||
					DafyOutConstant.QUERY_WXACCOUNT_DETAIL.equals(req.getTransCode())){
				token = assureLoginOld();
			}else{
				token = assureLogin();
			}
			//开始发起交易
			req.setToken(token);
			retStr = doSingleCommunicate(req);
			//解密报文，并返回模型对象
			resMsg = DafyOutMsgUtil.parseMsg(retStr, req.getTransCode());
			if (resMsg == null) {
				throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
			}
		}
		
		return resMsg;
	}


	/**
	 * 普通的通讯过程(7贷)
	 * @param req 通讯请求模型
	 * @return BaseResModel 模型
	 */
	public static com.pinting.gateway.loan7.out.model.BaseResModel doCommunicate2Loan7(com.pinting.gateway.loan7.out.model.BaseReqModel req){
		//确保已经登录过
		//判断是否已经登录
		String token = getLoan7Token();
		if (StringUtil.isEmpty(token) || Loan7OutConstant.CUSTOMER_WITHDRAW.equals(req.getTransCode())) {
			token = assureLoginLoan7(req);
		}
		//开始发起交易
		req.setToken(token);
		String retStr = doSingleCommunicateLoan7(req);
		//解密报文，并返回模型对象
		com.pinting.gateway.loan7.out.model.BaseResModel resMsg = Loan7OutMsgUtil.parseMsg(retStr, req.getTransCode());

		if (resMsg == null) {
			throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
		}
		//判断是否超时，如果超时，重新登录再发起此交易
		if(Loan7OutConstant.RETURN_CODE_EXPIRE.equals(resMsg.getRespCode())){
			//超时了，再次登录
			token = assureLoginLoan7(req);
			//开始发起交易
			req.setToken(token);
			retStr = doSingleCommunicateLoan7(req);
			//解密报文，并返回模型对象
			resMsg = Loan7OutMsgUtil.parseMsg(retStr, req.getTransCode());
			if (resMsg == null) {
				throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
			}
		}

		return resMsg;
	}
	
	
	
	
	/**
	 * 普通的通讯过程(云贷自主放款)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static BaseResModel doCommunicate2DafyLoan(BaseReqModel req){
		//确保已经登录过
		//判断是否已经登录
		String token = getDafyLoanToken();
		
		if (StringUtil.isEmpty(token)) {
			token = assureDafyLoanLogin();
		}
		//开始发起交易
		req.setToken(token);
		String retStr = doSingleCommunicateDafyLoan(req);
		//解密报文，并返回模型对象
		BaseResModel resMsg = DafyOutMsgUtil.parseMsg(retStr, req.getTransCode());
		
		if (resMsg == null) {
			throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
		}
		//判断是否超时，如果超时，重新登录再发起此交易
		if(DafyOutConstant.RETURN_CODE_EXPIRE.equals(resMsg.getRespCode())){
			//超时了，再次登录
			token = assureDafyLoanLogin();
			//开始发起交易
			req.setToken(token);
			retStr = doSingleCommunicateDafyLoan(req);
			//解密报文，并返回模型对象
			resMsg = DafyOutMsgUtil.parseMsg(retStr, req.getTransCode());
			if (resMsg == null) {
				throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
			}
		}
		
		return resMsg;
	}
	
	
	
	/**
	 * 普通的通讯过程 (恒丰银行存管)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static String doCommunicate2Hf(com.pinting.gateway.hfbank.out.model.BaseReqModel req,String url){
		String retStr = doSingleCommunicateHf(req,url);
		log.info("请求url："+url);
		return retStr;
	}
	
	/**
	 * 普通的通讯过程 (恒丰银行存管)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static String doCommunicate2HfEbank(com.pinting.gateway.hfbank.out.model.BaseReqModel req,String url){
		String retStr = doSingleCommunicateHfEbank(req,url);
		log.info("返回报文："+retStr);
		return retStr;
	}
	
	
	//登录
	private static String assureDafyLoanLogin(){
		
		String transCode = DafyOutConstant.DAFY_LOAN_LOGIN;
		DafyLoanLoginReqModel reqModel = new DafyLoanLoginReqModel();
		reqModel.setClientKey(dafyLoanClientKey);
		reqModel.setClientSecret(dafyLoanClientSecret);
		reqModel.setRequestTime(new Date());
		reqModel.setTransCode(transCode);
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		String retStr = doSingleCommunicateDafyLoan(reqModel);
		//解密报文，并返回模型对象
		DafyLoanLoginResModel resMsg = (DafyLoanLoginResModel)DafyOutMsgUtil.parseMsg(retStr, transCode);
		String token = "";
		if(resMsg != null){
			try {
				jsClientDaoSupport.setString(resMsg.getToken(), "dafy_loan_token");
				token = resMsg.getToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PTMessageException(PTMessageEnum.DAFY_LOGIN_FAIL);
		}
		return token;
	}

	
	
	//7贷登录
	private static String assureLoginLoan7(com.pinting.gateway.loan7.out.model.BaseReqModel req){
		String transCode = Loan7OutConstant.LOGIN;
		com.pinting.gateway.loan7.out.model.LoginReqModel reqModel = new com.pinting.gateway.loan7.out.model.LoginReqModel();
		reqModel.setClientKey(clientLoan7Key);
		reqModel.setClientSecret(clientLoan7Secret);
		reqModel.setRequestTime(new Date());
		reqModel.setTransCode(transCode);
		String retStr = doSingleCommunicateLoan7(reqModel);
		//解密报文，并返回模型对象
		com.pinting.gateway.loan7.out.model.LoginResModel resMsg = (com.pinting.gateway.loan7.out.model.LoginResModel)Loan7OutMsgUtil.parseMsg(retStr, transCode);
		String token = "";
		if(resMsg != null){
			try {
				jsClientDaoSupport.setString(resMsg.getToken(), "loan7_token");
				token = resMsg.getToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PTMessageException(PTMessageEnum.DAFY_LOGIN_FAIL);
		}
		return token;
	}
	
	//登录
	private static String assureLogin(){
		String transCode = DafyOutConstant.LOGIN;
		LoginReqModel reqModel = new LoginReqModel();
		reqModel.setClientKey(clientKey);
		reqModel.setClientSecret(clientSecret);
		reqModel.setRequestTime(new Date());
		reqModel.setTransCode(transCode);
		String retStr = doSingleCommunicate(reqModel);
		//解密报文，并返回模型对象
		LoginResModel resMsg = (LoginResModel)DafyOutMsgUtil.parseMsg(retStr, transCode);
		String token = "";
		if(resMsg != null){
			try {
				jsClientDaoSupport.setString(resMsg.getToken(), "dafy_token");
				token = resMsg.getToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PTMessageException(PTMessageEnum.DAFY_LOGIN_FAIL);
		}
		return token;
	}
	//老系统登录
	private static String assureLoginOld(){
		String transCode = DafyOutConstant.LOGIN;
		LoginReqModel reqModel = new LoginReqModel();
		reqModel.setClientKey(clientKey);
		reqModel.setClientSecret(clientSecret);
		reqModel.setRequestTime(new Date());
		reqModel.setTransCode(transCode);
		reqModel.setToken(getDafyToken());
		//获取加密以后的参数
		String msg = DafyOutMsgUtil.packageMsg(reqModel);
		//组装HTTP参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		params.put("clientKey", clientKey);
		//发送http请求，获得返回报文
		String retStr = HttpClientUtil.sendRequest(dafyUrlOld, params);
		
		//解密报文，并返回模型对象
		LoginResModel resMsg = (LoginResModel)DafyOutMsgUtil.parseMsg(retStr, transCode);
		String token = "";
		if(resMsg != null){
			try {
				jsClientDaoSupport.setString(resMsg.getToken(), "dafy_token");
				token = resMsg.getToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PTMessageException(PTMessageEnum.DAFY_LOGIN_FAIL);
		}
		return token;
	}	
	
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicate(BaseReqModel req){
		if(StringUtil.isEmpty(req.getToken())){
			req.setToken(getDafyToken());
		}
		//获取加密以后的参数
		String msg = DafyOutMsgUtil.packageMsg(req);
		//组装HTTP参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		params.put("clientKey", clientKey);
		params.put("transCode", req.getTransCode());
		
		//发送http请求，获得返回报文
		String retStr = null;
		
		if(DafyOutConstant.BUY_PRODUCT.equals(req.getTransCode())){
			retStr = HttpClientUtil.sendRequest(dafyPayUrl, params);
		}else if(DafyOutConstant.CUSTOMER_WITHDRAW.equals(req.getTransCode()) || 
					DafyOutConstant.SYS_WITHDRAW.equals(req.getTransCode()) ||
					DafyOutConstant.QUERY_WXACCOUNT_DETAIL.equals(req.getTransCode())){
			retStr = HttpClientUtil.sendRequest(dafyWithdrawUrl, params);
		}else if (DafyOutConstant.QUERY_BILL.equals(req.getTransCode()) || DafyOutConstant.WAIT_FILL.equals(req.getTransCode()) ||
				  DafyOutConstant.REVENUE_SETTLE.equals(req.getTransCode()) || DafyOutConstant.LOAN_RESULT_NOTICE.equals(req.getTransCode()) ||
				  DafyOutConstant.SIGN_RESULT_NOTICE.equals(req.getTransCode()) || DafyOutConstant.REPAY_RESULT_NOTICE.equals(req.getTransCode()) ||
				  DafyOutConstant.AGREEMENT_NOTICE.equals(req.getTransCode())) {
			
			retStr = HttpClientUtil.sendRequest(dafyLoanUrl, params);
			
		}else{
			retStr = HttpClientUtil.sendRequest(dafyUrl, params);
		}
		return retStr;
	}
	
	//发起通讯，返回原始报文(7贷)
	private static String doSingleCommunicateLoan7(com.pinting.gateway.loan7.out.model.BaseReqModel req){
		if(StringUtil.isEmpty(req.getToken())){
			req.setToken(getLoan7Token());
		}
		//获取加密以后的参数
		String msg = Loan7OutMsgUtil.packageMsg(req);
		//组装HTTP参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		params.put("clientKey", clientLoan7Key);
		params.put("transCode", req.getTransCode());

		//发送http请求，获得返回报文
		String retStr = HttpClientUtil.sendRequest(loan7Url, params);

		return retStr;
	}
	
	
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateDafyLoan(BaseReqModel req){
		if(StringUtil.isEmpty(req.getToken())){
			req.setToken(getDafyLoanToken());
		}
		req.setClientKey(dafyLoanClientKey);
		//获取加密以后的参数
		String msg = DafyOutMsgUtil.packageMsg(req);
		//组装HTTP参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		params.put("clientKey", dafyLoanClientKey);
		params.put("transCode", req.getTransCode());
		
		//发送http请求，获得返回报文
		String retStr = null;
		retStr = HttpClientUtil.sendRequest(dafyLoanUrl, params);
		return retStr;
	}
	
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateHf(com.pinting.gateway.hfbank.out.model.BaseReqModel req,String url){
		
		//获取签名数据
		String msg = HfbankOutMsgUtil.getSignData(req);
		req.setSigndata(msg);

		//组装HTTP参数，获得返回报文
		Gson gson = new Gson();
		log.info("============恒丰银行请求的报文：【" + gson.toJson(req) + "】============");
		HashMap<String, String> params = null;
		try {
			params = json2SingleObject(gson.toJson(req), HashMap.class);
		} catch (IOException e) {
			log.info("请求JSON报文转Map异常", e);
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		//post方式请求
		String resStr = NetUtils.sendHFDataByPost(url, params, TIMEOUT, null);
		log.info("============恒丰银行返回的报文：【" + resStr + "】============");
		if (StringUtil.isBlank(resStr)) {
			throw new PTMessageException(PTMessageEnum.BUS_PROCESSING.getCode(), "通讯异常");
		}
		return resStr;
	}
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateHfEbank(com.pinting.gateway.hfbank.out.model.BaseReqModel req,String url){
		
		//获取签名数据
		String msg = HfbankOutMsgUtil.getSignData(req);
		req.setSigndata(msg);
		//组装HTTP参数,获得返回报文
		String retStr = null;
		try {
			Gson gson = new Gson();
			log.info("============恒丰银行请求的报文：【" + gson.toJson(req) + "】============");
			HashMap<String, String> params = json2SingleObject(gson.toJson(req), HashMap.class);
			//post方式请求  
			String resStr = NetUtils.sendHFDataByPost(url, params, TIMEOUT, null);
//			String resStr = HttpClientUtil4HF.sendRequest(url, params);
			log.info("============恒丰银行返回的报文：【" + resStr + "】============");
			if (resStr != null && resStr.contains("recode")) {
				retStr = resStr.substring(resStr.indexOf("{"));
			}else {
				return resStr;
			}
			
		} catch (IOException e) {
		
			e.printStackTrace();
			log.info("============恒丰银行接口返回错误===========");
		}
		
		return retStr;
	}
	
	
	public static String getDafyToken(){
		String token = "";
		try {
			token = jsClientDaoSupport.getString("dafy_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static String getLoan7Token(){
		String token = "";
		try {
			token = jsClientDaoSupport.getString("loan7_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static String getDafyLoanToken(){
		String token = "";
		try {
			token = jsClientDaoSupport.getString("dafy_loan_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 恒丰网银支付
	 * @param req
     */
	public static GatewayRechargeRequestResModel ebankHF(GatewayRechargeRequestReqModel req) {
		GatewayRechargeRequestResModel result = new GatewayRechargeRequestResModel();
//		String notifyUrl = req.getNotify_url();
//		String returnUrl = req.getReturn_url();
//		try {
//			req.setNotify_url(URLEncoder.encode(req.getNotify_url(), "UTF-8"));
//			req.setReturn_url(URLEncoder.encode(req.getReturn_url(), "UTF-8"));
//		} catch (Exception e) {
//			req.setNotify_url(notifyUrl);
//			req.setReturn_url(returnUrl);
//			e.printStackTrace();
//		}
		req.setPlatcust(req.getPlatcust() == null ? "" : req.getPlatcust());
		req.setType(req.getType() == null ? "" : req.getType());
		req.setCharge_type(req.getCharge_type() == null ? "" : req.getCharge_type());
		req.setBankcode(req.getBankcode() == null ? "" : req.getBankcode());
		req.setCard_type(req.getCard_type() == null ? "" : req.getCard_type());
		req.setCurrency_code(req.getCurrency_code() == null ? "" : req.getCurrency_code());
		req.setCard_no(req.getCard_no() == null ? "" : req.getCard_no());
		req.setAmt(req.getAmt() == null ? "0.00" : req.getAmt());
		req.setReturn_url(req.getReturn_url() == null ? "" : req.getReturn_url());
		req.setNotify_url(req.getNotify_url() == null ? "" : req.getNotify_url());
		req.setPay_code(req.getPay_code() == null ? "" : req.getPay_code());
		req.setRemark(req.getRemark() == null ? "" : req.getRemark());
		//获取签名数据
		String msg = HfbankOutMsgUtil.getSignData(req);
		//组装HTTP参数
		req.setSigndata(msg);
		String formHtml =
				"" + "正在提交请稍后。。。。。。。。" +
				"<form method='get' name='pay' id='pay' action='" + HfbankOutConstant.hfbankUrl + NoticeUrl.GATEWAY_RECHARGE_REQUEST.getCode() + "'>" +
					"<input name='plat_no' type='hidden' value='" + req.getPlat_no() + "'/>" +
					"<input name='order_no' type='hidden' value='" + req.getOrder_no() + "'/>" +
					"<input name='partner_trans_date' type='hidden' value='" + req.getPartner_trans_date() + "'/>" +
					"<input name='partner_trans_time' type='hidden' value='" + req.getPartner_trans_time() + "'/>" +
					"<input name='platcust' type='hidden' value='" + req.getPlatcust() + "'/>" +
					"<input name='type' type='hidden' value='" + req.getType() + "'/>" +
					"<input name='amt' type='hidden' value='" + req.getAmt() + "'/>" +
					"<input name='return_url' type='hidden' value='" + req.getReturn_url() + "'/>" +
					"<input name='notify_url' type='hidden' value='" + req.getNotify_url() + "'/>" +
					"<input name='pay_code' type='hidden' value='" + req.getPay_code() + "'/>" +
					"<input name='charge_type' type='hidden' value='" + req.getCharge_type() + "'/>" +
					"<input name='bankcode' type='hidden' value='" + req.getBankcode() + "'/>" +
					"<input name='signdata' type='hidden' value='" + req.getSigndata() + "'/>" +
					"<input name='card_type' type='hidden' value='" + req.getCard_type() + "'/>" +
					"<input name='currency_code' type='hidden' value='" + req.getCurrency_code() + "'/>" +
					"<input name='card_no' type='hidden' value='" + req.getCard_no() + "'/>" +
					"<input name='remark' type='hidden' value='" + req.getRemark() + "'/>" +
					"</form>" +
				"";
		result.setHtml(formHtml);
		result.setRespCode(ConstantUtil.BSRESCODE_SUCCESS);
		result.setRecode(Constants.BSRESCODE_DEP_SUCCESS);
		return result;
	}
}
