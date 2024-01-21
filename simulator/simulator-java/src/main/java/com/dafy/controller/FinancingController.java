/**
 * SpringMVC restful 风格
 * 理财信息控制器，控制关于理财信息的一些业务逻辑操作
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.core.constant.DafyInConstant;
import com.dafy.core.constant.DafyOutConstant;
import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.DafyInMsgUtil;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.StringUtil;
import com.dafy.core.util.encrypt.MD5Util;
import com.dafy.model.pojo.SimFinancing;
import com.dafy.model.pojo.SimFinancingDetail;
import com.dafy.service.SimFinancingDetailService;
import com.dafy.service.SimFinancingService;
import com.dafy.tools.ResultHolder;

/**
 * 控制器注解 请求映射注解 controller 代表类是控制器 restcontroller 代表类是控制器， 所有的方法 都是ajax方法
 * 
 * @author yanwl
 *
 */
@Controller
@RequestMapping("/financing")
public class FinancingController {
	private static final Logger log = LoggerFactory.getLogger(FinancingController.class);
	
	/**
	 * 注入 业务逻辑层
	 */
	@Autowired
	private SimFinancingService simFinancingService;
	@Autowired
	private SimFinancingDetailService simFinancingDetailService;

	/**
	 * 理财信息列表页面
	 * @throws Exception
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView financing() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("financing");
		return mav;
	}
	
	/**
	 * 理财信息列表页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request,String sort,String order) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sortField",sort );
		map.put("sortOrder", order);
		List<SimFinancing> simFinancings = simFinancingService.selectAllSimFinancings(map);
		
		return ResultHolder.getPageData(simFinancings);
		
		/*int totalCount = simFinancingService.countSimFinancings(null);
		map.put("start", Integer.valueOf(request.getParameter("offset")));
		map.put("pageSize", Integer.valueOf(request.getParameter("limit")));
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", simFinancings);
		resultMap.put("total", totalCount);
		return JSON.toJSONString(resultMap);*/
		
	}
	
	/**
	 * 理财信息列表页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	@ResponseBody
	public void test(HttpServletRequest request) throws Exception {
		//System.out.println(request.getParameter("name"));
		/*for(int i = 1 ;  i< 20 ; i++) {
			SimFinancing sf = new SimFinancing();
			sf.setAmount(1000d);
			sf.setCreateTime(new Date());
			sf.setCustomerId(i+"");
			sf.setPayFinishTime(new Date());
			sf.setRequestTime(new Date());
			sf.setPayOrderNo(i+"");
			sf.setStatus(FinancingStatus.RECEIVED.getCode());
			simFinancingService.insertSimFinancing(sf);
		}*/
		
		SimFinancing sf = new SimFinancing();
		sf.setAmount(1000d);
		sf.setCreateTime(new Date());
		sf.setCustomerId("33333333");
		sf.setPayFinishTime(new Date());
		sf.setRequestTime(new Date());
		sf.setPayOrderNo("00003333");
		simFinancingService.insertSimFinancing(sf,null);
		
	}
	
	/**
	 * 理财信息接受（理财购买）
	 * @throws Exception
	 */
	@RequestMapping(value = "/financing_buy")
	@ResponseBody
	public String financingBuy(HttpServletRequest request) throws Exception {
		// DES解密，获得明文
		String decryptData = new DESUtil(Globals.DAFY_OUT_DES_KEY).decryptStr(request.getParameter("DATA").replace(" ", "+"));
		System.out.println("decryptData:"+decryptData);
		JSONObject json = JSON.parseObject(decryptData);
		String token = json.get("token").toString();
		String transCode = json.get("transCode").toString();
		String requestTime = StringUtil.null2String(json.get("requestTime"));
		String clientKey = StringUtil.null2String(json.get("clientKey"));
		String customerId = StringUtil.null2String(json.get("customerId"));
		String payPlatform = StringUtil.null2String(json.get("payPlatform"));
		String merchantId = StringUtil.null2String(json.get("merchantId"));
		String payOrderNo = StringUtil.null2String(json.get("payOrderNo"));
		String payReqTime = StringUtil.null2String(json.get("payReqTime"));
		String payFinshTime = StringUtil.null2String(json.get("payFinshTime"));
		String amount = json.get("amount").toString();
		String products = json.get("products").toString();
		String hash = json.get("hash").toString();
		
		//重新组装hash，验证hash是否正确
		StringBuffer buffer = new StringBuffer();
		buffer.append("token="+token);
		buffer.append("&transCode="+transCode);
		buffer.append("&requestTime="+requestTime);
		buffer.append("&clientKey="+clientKey);
		buffer.append("&customerId="+customerId);
		buffer.append("&payPlatform="+payPlatform);
		buffer.append("&merchantId="+merchantId);
		buffer.append("&payOrderNo="+payOrderNo);
		buffer.append("&payReqTime="+payReqTime);
		buffer.append("&payFinshTime="+payFinshTime);
		buffer.append("&amount="+amount);
		buffer.append("&products="+products);
		log.debug("============hash字段明文：【" + buffer + "】============");
		String newHash = MD5Util.encryptMD5(buffer.toString());
		log.debug("============hash字段密文：【" + newHash + "】============");
		
		String responseTime = DateUtil.format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		String respCode = "";
		String respMsg = "";
		if(StringUtil.isNotEmpty(hash) && newHash.equals(hash)) {
			if(StringUtil.isEmpty(token) || !token.equals(DafyInMsgUtil.token)) {
				respCode = DafyInConstant.RETURN_CODE_REFUSE;
				respMsg = DafyInConstant.RETURN_MSG_TOKEN_ERROR;
			}else {
				if(StringUtil.isEmpty(clientKey) || !Globals.DAFY_CLIENT_KEY.equals(clientKey)) {
					respCode = DafyInConstant.RETURN_CODE_REFUSE;
					respMsg = DafyInConstant.RETURN_MSG_CLIENT_KEY_ERROR;
				}else {
					List<SimFinancingDetail> list = (List<SimFinancingDetail>) JSON.parseArray(products, SimFinancingDetail.class);
					
					SimFinancing sf = new SimFinancing();
					sf.setAmount(Double.valueOf(amount));
					sf.setCreateTime(new Date());
					sf.setCustomerId(customerId);
					sf.setPayFinishTime(DateUtil.parseDateTime(payFinshTime));
					sf.setRequestTime(DateUtil.parseDateTime(requestTime));
					sf.setPayOrderNo(payOrderNo);
					sf.setMerchantId(merchantId);
					sf.setPayPlatform(payPlatform);
					sf.setPayReqTime(DateUtil.parseDateTime(payReqTime));
					boolean isSuccess = simFinancingService.insertSimFinancing(sf,list);
					if(isSuccess) {
						respCode = DafyInConstant.RETURN_CODE_ACCEPT;
						respMsg = DafyInConstant.RETURN_MSG_RESULT_SUCCESS;
					}else {
						respCode = DafyInConstant.RETURN_CODE_FAIL;
						respMsg = DafyInConstant.RETURN_MSG_RESULT_FAIL;
					}
				}
			}
		}else {
			respCode = DafyInConstant.RETURN_CODE_REFUSE;
			respMsg = DafyInConstant.RETURN_MSG_HASH_ERROR;
		}
		
		StringBuffer buffer1 = new StringBuffer("respCode="+respCode);
		buffer1.append("respCode="+respCode);
		buffer1.append("&respMsg="+respMsg);
		buffer1.append("&responseTime="+responseTime);
		log.debug("============hash字段明文：【" + buffer1 + "】============");
		String encryptHash = MD5Util.encryptMD5(buffer1.toString());
		log.debug("============hash字段密文：【" + encryptHash + "】============");
		
		map.put("respCode", respCode);
		map.put("respMsg", respMsg);
		map.put("responseTime", responseTime);
		map.put("hash", encryptHash);
		
		String message = JSON.toJSONString(map);
		log.debug("============发送报文：【" + message + "】============");
		// 对json进行DES加密
		String ciphertext = new DESUtil(Globals.DAFY_OUT_DES_KEY).encryptStr(message);
		log.debug("============报文转换密文：【" + ciphertext + "】============");
		return ciphertext;
	}
	
}
