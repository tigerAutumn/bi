package com.dafy.tools.hfBank;

import static com.dafy.core.util.JsonProcessUtils.json2SingleObject;
import com.dafy.core.util.NetUtils;
import com.dafy.core.util.StringUtil;
import com.dafy.model.vo.BaseReqModel;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

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
	public static String clientKey = "pum4938o62ik2dpo3m";
	public static String clientSecret = "3h72ifl6rmvq2v0fmh";
	public static String dafyLoanClientKey = "channeldafykey1230";
	public static String dafyLoanClientSecret = "dafy7987!&Ke6!3";
	
	
	public static String clientLoan7Key = "channeldafykey1230";
	public static String clientLoan7Secret = "dafy7987!&Ke6!3";
	private static final int TIMEOUT = 30; //恒丰请求时间
	

	/**
	 * 普通的通讯过程 (恒丰银行存管)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static String doCommunicate2Hf(BaseReqModel req,String url){
		String retStr = doSingleCommunicateHf(req,url);
		return retStr;
	}
	
	/**
	 * 普通的通讯过程 (恒丰银行存管)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static String doCommunicate2HfEbank(BaseReqModel req,String url){
		String retStr = doSingleCommunicateHfEbank(req,url);
		log.info("返回报文："+retStr);
		return retStr;
	}
	
	
	

	
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateHf(BaseReqModel req,String url){
		
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
			e.printStackTrace();
		}
		//post方式请求
		String resStr = NetUtils.sendHFDataByPost(url, params, TIMEOUT, null);
		log.info("============恒丰银行返回的报文：【" + resStr + "】============");
		if (StringUtil.isBlank(resStr)) {
			log.info("通讯异常");
			try {
				throw new Exception("通讯异常");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return resStr;
	}
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateHfEbank(BaseReqModel req,String url){
		
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
	
	

}
