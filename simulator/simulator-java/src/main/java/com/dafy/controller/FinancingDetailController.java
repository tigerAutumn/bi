/**
 * SpringMVC restful 风格
 * 理财明细控制器，控制关于理财明细的一些业务逻辑操作
 * @author yanwl
 * @date 2015-11-20
 */
package com.dafy.controller;

import java.math.BigDecimal;
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
import com.dafy.core.enums.FinancingDetailStatus;
import com.dafy.core.enums.ProductRate;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.DafyInMsgUtil;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.HttpRequest;
import com.dafy.core.util.MoneyUtil;
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
@RequestMapping("/financing_detail")
public class FinancingDetailController {
	private static final Logger log = LoggerFactory.getLogger(FinancingDetailController.class);
	/**
	 * 注入 业务逻辑层
	 */
	@Autowired
	private SimFinancingDetailService simFinancingDetailService;
	@Autowired
	private SimFinancingService simFinancingService;

	/**
	 * 理财信息列表页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("financing_detail");
		return mav;
	}
	
	/**
	 * 获取理财信息列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request,String sort,String order) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sortField",sort );
		map.put("sortOrder", order);
		List<SimFinancingDetail> simFinancingDetails = simFinancingDetailService.selectAllSimFinancingDetails(map);
		return ResultHolder.getPageData(simFinancingDetails);
	}
	
	/**
	 * 理财信息回复页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/modal/reply/{id}", method = RequestMethod.GET)
	public ModelAndView modal(@PathVariable Integer id) throws Exception {
		ModelAndView mav = new ModelAndView();
		SimFinancingDetail sfd = simFinancingDetailService.selectSimFinancingDetailById(id);
		mav.addObject("obj", sfd);
		mav.setViewName("financing_reply");
		return mav;
	}
	
	/**
	 * 理财信息明细回复
	 * @throws Exception
	 */
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	@ResponseBody
	public String reply(HttpServletRequest request,String id,String result,String errorMsg) throws Exception {
		if(StringUtil.isEmpty(errorMsg)) {
			errorMsg = "";
		}
		Map<String,Object> retMap = new HashMap<String, Object>();
		String code = DafyInConstant.RETURN_CODE_FAIL;
		String msg = "回复处理失败";
		if(StringUtil.isNotEmpty(id)) {
			SimFinancingDetail sfd = simFinancingDetailService.selectSimFinancingDetailById(Integer.valueOf(id));
			SimFinancing sf = simFinancingService.selectSimFinancingById(sfd.getTotalId());
			
			Map<String,Object> resqMap = new HashMap<String,Object>();
			String requestTime = DateUtil.format(new Date());
			resqMap.put("token", DafyInMsgUtil.token);
			resqMap.put("transCode", DafyOutConstant.FINANCING_DETAIL_REPLY);
			resqMap.put("requestTime", requestTime);
			resqMap.put("clientKey", Globals.DAFY_CLIENT_KEY);
			resqMap.put("payPlatform", sf.getPayPlatform());
			resqMap.put("amount", sf.getAmount());
			resqMap.put("productOrderNo", sfd.getProductOrderNo());
			resqMap.put("productCode", sfd.getProductCode());
			resqMap.put("productAmount", sfd.getProductAmount());
			resqMap.put("result", Integer.valueOf(result));
			resqMap.put("errorMsg", errorMsg);
			
			//组装加密的hash
			StringBuffer buffer = new StringBuffer();
			buffer.append("token="+DafyInMsgUtil.token);
			buffer.append("&transCode="+DafyOutConstant.FINANCING_DETAIL_REPLY);
			buffer.append("&requestTime="+requestTime);
			buffer.append("&clientKey="+Globals.DAFY_CLIENT_KEY);
			buffer.append("&payPlatform="+sf.getPayPlatform());
			buffer.append("&amount="+sf.getAmount());
			buffer.append("&productOrderNo="+sfd.getProductOrderNo());
			buffer.append("&productCode="+sfd.getProductCode());
			buffer.append("&productAmount="+sfd.getProductAmount());
			buffer.append("&result="+Integer.valueOf(result));
			buffer.append("&errorMsg="+errorMsg);
			
			log.debug("============hash字段明文：【" + buffer + "】============");
			String encryptHash = MD5Util.encryptMD5(buffer.toString());
			log.debug("============hash字段密文：【" + encryptHash + "】============");
			resqMap.put("hash", encryptHash);
			
			String message = JSON.toJSONString(resqMap);
			log.debug("============发送报文：【" + message + "】============");
			// 对json进行DES加密
			String ciphertext = new DESUtil(Globals.DAFY_IN_DES_KEY).encryptStr(message);
			log.debug("============报文转换密文：【" + ciphertext + "】============");
			
			String ret = HttpRequest.sendPost(Globals.DAFY_BUY_PRODUCT_NOTICE_URL, "DATA="+ciphertext);
			if(StringUtil.isEmpty(ret)) {
				String decryptData = new DESUtil(Globals.DAFY_IN_DES_KEY).decryptStr(ret);
				JSONObject json = JSON.parseObject(decryptData);
				if(json != null) {
					String respCode = json.get("respCode").toString();
					String respMsg = json.get("respMsg").toString();
					String responseTime = json.get("responseTime").toString();
					String hash = json.get("hash").toString();
					
					if(StringUtil.isNotEmpty(hash)) {
						//组装加密的hash
						StringBuffer buffer1 = new StringBuffer();
						buffer1.append("respCode="+respCode);
						buffer1.append("&respMsg="+respMsg);
						buffer1.append("&responseTime="+responseTime);
						
						String newHash = MD5Util.encryptMD5(buffer1.toString());
						if(hash.equals(newHash)) {
							if(StringUtil.isNotEmpty(respCode) && DafyInConstant.RETURN_CODE_SUCCESS.equals(respCode)) {
								sfd.setStatus(FinancingDetailStatus.CONFIRMED.getCode());
							}else {
								sfd.setStatus(FinancingDetailStatus.CONFIRM_FAIL.getCode());
							}
							simFinancingDetailService.updateSimFinancingDetail(sfd);
							code = respCode;
							msg = respMsg;
						}
					}
				}
			}
		}
		
		/*SimFinancing sf = simFinancingService.selectSimFinancingById(id);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("totalId", id);
		List<SimFinancingDetail> list = simFinancingDetailService.selectAllSimFinancingDetails(paramMap);*/
		
		retMap.put("code", code);
		retMap.put("msg", msg);
		return JSON.toJSONString(retMap);
	}
	
	/**
	 * 理财回款
	 * @throws Exception
	 */
	@RequestMapping(value = "/return_money/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String return_money(HttpServletRequest request,@PathVariable Integer id) throws Exception {
		Map<String,Object> retMap = new HashMap<String, Object>();
		String code = DafyInConstant.RETURN_CODE_FAIL;
		String msg = "回款失败";
		SimFinancingDetail sfd = simFinancingDetailService.selectSimFinancingDetailById(id);
		SimFinancing sf = simFinancingService.selectSimFinancingById(sfd.getTotalId());
		
		Map<String,Object> resqMap = new HashMap<String,Object>();
		String requestTime = DateUtil.format(new Date());
		String payReqTime = DateUtil.format(new Date());
		String payFinshTime = DateUtil.format(new Date());
		String payPlatform = Globals.DAFY_PAY_PLATFORM;
		String merchantId = Globals.DAFY_MERCHANT_ID;
		String payOrderNo = DafyInMsgUtil.getPayOrderNo();
		
		//计算产品收益
		double rate =  Double.valueOf(ProductRate.find(sfd.getProductCode()).getRate());
		double days = Double.valueOf(ProductRate.find(sfd.getProductCode()).toString().substring(5));
		int diffDays = DateUtil.getDiffeDay(sf.getPayFinishTime());
		if(diffDays < days) {
			days = diffDays;
		}
		BigDecimal bd1 = MoneyUtil.multiply(sfd.getProductAmount() == null ? BigDecimal.ZERO : BigDecimal.valueOf(sfd.getProductAmount()), MoneyUtil.multiply(rate,days));
		double productInterest = MoneyUtil.divide((MoneyUtil.divide(bd1.doubleValue(),100)).doubleValue(),365).doubleValue();
		//计算回款总金额
		double amount = MoneyUtil.add(sfd.getProductAmount() == null ? 0 : sfd.getProductAmount(), productInterest).doubleValue();
		
		resqMap.put("token", DafyInMsgUtil.token);
		resqMap.put("clientKey", Globals.DAFY_CLIENT_KEY);
		resqMap.put("transCode", DafyOutConstant.FINANCING_RETURN_MONEY);
		resqMap.put("requestTime", requestTime);
		resqMap.put("payPlatform", payPlatform);
		resqMap.put("merchantId", merchantId);
		resqMap.put("payOrderNo", payOrderNo);
		resqMap.put("payReqTime", payReqTime);
		resqMap.put("payFinshTime", payFinshTime);
		resqMap.put("amount", amount);
		resqMap.put("productOrderNo", sfd.getProductOrderNo());
		resqMap.put("productCode", sfd.getProductCode());
		resqMap.put("productAmount", sfd.getProductAmount());
		resqMap.put("productInterest", productInterest);
		
		//组装加密的hash
		StringBuffer buffer = new StringBuffer();
		buffer.append("token="+DafyInMsgUtil.token);
		buffer.append("&clientKey="+Globals.DAFY_CLIENT_KEY);
		buffer.append("&transCode="+DafyOutConstant.FINANCING_RETURN_MONEY);
		buffer.append("&requestTime="+requestTime);
		buffer.append("&payPlatform="+payPlatform);
		buffer.append("&merchantId="+merchantId);
		buffer.append("&payOrderNo="+payOrderNo);
		buffer.append("&payReqTime="+payReqTime);
		buffer.append("&payFinshTime="+payFinshTime);
		buffer.append("&amount="+amount);
		buffer.append("&productOrderNo="+sfd.getProductOrderNo());
		buffer.append("&productCode="+sfd.getProductCode());
		buffer.append("&productAmount="+sfd.getProductAmount());
		buffer.append("&productInterest="+productInterest);
		
		log.debug("============hash字段明文：【" + buffer + "】============");
		String encryptHash = MD5Util.encryptMD5(buffer.toString());
		log.debug("============hash字段密文：【" + encryptHash + "】============");
		resqMap.put("hash", encryptHash);
		
		String message = JSON.toJSONString(resqMap);
		log.debug("============发送报文：【" + message + "】============");
		// 对json进行DES加密
		String ciphertext = new DESUtil(Globals.DAFY_IN_DES_KEY).encryptStr(message);
		log.debug("============报文转换密文：【" + ciphertext + "】============");
		
		String ret = HttpRequest.sendPost(Globals.DAFY_RETURN_MONEY_NOTICE_URL, "DATA="+ciphertext);
		if(StringUtil.isEmpty(ret)) {
			String decryptData = new DESUtil(Globals.DAFY_IN_DES_KEY).decryptStr(ret);
			JSONObject json = JSON.parseObject(decryptData);
			if(json != null) {
				String respCode = json.get("respCode").toString();
				String respMsg = json.get("respMsg").toString();
				String responseTime = json.get("responseTime").toString();
				String hash = json.get("hash").toString();
				
				if(StringUtil.isNotEmpty(hash)) {
					//组装加密的hash
					StringBuffer buffer1 = new StringBuffer();
					buffer1.append("respCode="+respCode);
					buffer1.append("&respMsg="+respMsg);
					buffer1.append("&responseTime="+responseTime);
					
					String newHash = MD5Util.encryptMD5(buffer1.toString());
					if(hash.equals(newHash)) {
						if(StringUtil.isNotEmpty(respCode) && DafyInConstant.RETURN_CODE_SUCCESS.equals(respCode)) {
							sfd.setStatus(FinancingDetailStatus.RETURNED.getCode());
						}else {
							sfd.setStatus(FinancingDetailStatus.RETURN_FAIL.getCode());
						}
						simFinancingDetailService.updateSimFinancingDetail(sfd);
						code = respCode;
						msg = respMsg;
					}
				}
			}
		}
		
		retMap.put("code", code);
		retMap.put("msg", msg);
		return JSON.toJSONString(retMap);
	}
}
