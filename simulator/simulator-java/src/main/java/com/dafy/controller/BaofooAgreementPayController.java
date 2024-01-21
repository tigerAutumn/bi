package com.dafy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dafy.tools.baofoo.AnalyParamUtil;

/**
 * 宝付协议支付模拟器
 * @project simulator-java
 * @title BaofooAgreementController.java
 * @author Dragon & cat
 * @date 2018-4-2
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
public class BaofooAgreementPayController {
	private static final Logger log = LoggerFactory.getLogger(BaofooAgreementPayController.class);
	
	/**
	 * 宝付请求模拟器
	 * @throws Exception
	 */
	@RequestMapping(value = "/baofoo/agreementPay/{code}", method = RequestMethod.POST)
	@ResponseBody
	public String business(HttpServletRequest request, @PathVariable String code) throws Exception {
		
		
		log.info("币港湾请求宝付协议支付接口:"+code);
		// 获取参数并解析成可用map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map requestMap = AnalyParamUtil.paramReturn(properties);
	    log.info("币港湾请求宝付接口参数:"+requestMap);

	    Map<String,String> resultMap = new HashMap<String,String>();
	    
	    /*宝付代扣模拟器
	     * 要使用此模拟器，更改gateway发起宝付协议支付请求URL地址
	     * ##代扣模拟器请求地址 baofoo.cut.url = http://localhost:8098/simulator/baofoo/agreementPay/business
	     * */
	    String txn_type = (String) requestMap.get("txn_type");
	   
	    if ("08".equals(txn_type)) { //协议支付
	    	String txn_amt = (String) requestMap.get("txn_amt");
	    	String trans_id = (String) requestMap.get("trans_id");
	    	
           
	    	//模拟情况,代扣交易和代扣查询为同一接口，用txn_sub_type区分
	    	String type  = "SUCCESS"; //SUCCESS 成功、FAIL 失败、PROCESS处理中
	    	if ("FAIL".equals(type)) {
				resultMap.put("resp_code", "F");
				resultMap.put("biz_resp_code", "BF00111");
				resultMap.put("biz_resp_msg", "失败");
			}else if ("SUCCESS".equals(type)) {
				resultMap.put("resp_code", "S");
				resultMap.put("biz_resp_code", "0000");
				resultMap.put("biz_resp_msg", "成功");
			}else  if ("PROCESS".equals(type)) {
				resultMap.put("resp_code", "I");
				resultMap.put("biz_resp_code", "BF00115");
				resultMap.put("biz_resp_msg", "处理中");
			}

			resultMap.put("succ_amt", txn_amt);
			resultMap.put("trans_id", trans_id);
			resultMap.put("order_id", String.valueOf(System.currentTimeMillis()));
			
			String signature = AnalyParamUtil.getSignature(resultMap);
			resultMap.put("signature", signature);
			
		}else if ("07".equals(txn_type)) {  //协议支付查询
	    	//String txn_amt = (String) requestMap.get("txn_amt");
	    	String trans_id = (String) requestMap.get("orig_trans_id");
	    	
           
	    	//模拟情况,协议支付交易和查询为同一接口，用txn_type区分
	    	String type  = "SUCCESS"; //SUCCESS 成功、FAIL 失败、PROCESS处理中
	    	if ("FAIL".equals(type)) {
				resultMap.put("resp_code", "F");
				resultMap.put("biz_resp_code", "BF00111");
				resultMap.put("biz_resp_msg", "失败");
			}else if ("SUCCESS".equals(type)) {
				resultMap.put("resp_code", "S");
				resultMap.put("biz_resp_code", "0000");
				resultMap.put("biz_resp_msg", "成功");
			}else  if ("PROCESS".equals(type)) {
				resultMap.put("resp_code", "I");
				resultMap.put("biz_resp_code", "BF00115");
				resultMap.put("biz_resp_msg", "处理中");
			}
			resultMap.put("succ_amt", "1000");
			resultMap.put("trans_id", trans_id);
			resultMap.put("order_id", String.valueOf(System.currentTimeMillis()));
			
			String signature = AnalyParamUtil.getSignature(resultMap);
			resultMap.put("signature", signature);
			
		}
	    String resp = AnalyParamUtil.parseResult(resultMap);
	    log.info("resp{}"+resp);
	    return resp;
	}
	
}
