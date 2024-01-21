/**
 * SpringMVC restful 风格
 * 首页跳转控制器
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.core.constant.DafyInConstant;
import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.encrypt.MD5Util;
import com.dafy.model.vo.QueryBillResModel;
import com.dafy.model.vo.Repayments;
import com.dafy.service.YunSelfLoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器注解 请求映射注解 controller 代表类是控制器 restcontroller 代表类是控制器， 所有的方法 都是ajax方法
 * 
 * @author yanwl
 *
 */
@Controller
public class YunSelfLoanController {
	private static final Logger log = LoggerFactory.getLogger(YunSelfLoanController.class);
	@Autowired 
	private YunSelfLoanService yunSelfLoanService;


	/**
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/yunSelfLoan/{code}")
	@ResponseBody
	public String login(HttpServletRequest request, @PathVariable String code) throws Exception {
		// DES解密，获得明文
		String decryptData = new DESUtil(Globals.DAFY_OUT_DES_KEY).decryptStr(request.getParameter("DATA").replace(" ", "+"));
		JSONObject json = JSON.parseObject(decryptData);
		log.debug("============请求报文明文："+code+"【" + decryptData + "】============");
		String transCode = json.get("transCode").toString();
		String clientKey = json.get("clientKey").toString();
		//String clientSecret = json.get("clientSecret").toString();
		String requestTime = json.get("requestTime").toString();
		String hash = json.get("hash").toString();
		
		//重新组装hash，验证hash是否正确
		StringBuffer buffer = new StringBuffer("transCode="+transCode);
		buffer.append("&clientKey="+clientKey);
		//buffer.append("&clientSecret="+clientSecret);
		buffer.append("&requestTime="+requestTime);
		log.debug("============hash字段明文：【" + buffer + "】============");
		String newHash = MD5Util.encryptMD5(buffer.toString());
		log.debug("============hash字段密文：【" + newHash + "】============");
		
		String responseTime = DateUtil.format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		String respCode = "";
		String respMsg = "";
		String encryptHash = "";
		/*if(StringUtil.isNotEmpty(hash) && newHash.equals(hash)) {
			if(StringUtil.isEmpty(clientKey) || StringUtil.isEmpty(clientSecret) || 
					!Globals.DAFY_CLIENT_KEY.equals(clientKey) || !Globals.DAFY_CLIENT_SECRET.equals(clientSecret)) {
				respCode = DafyInConstant.RETURN_CODE_FAIL;
				respMsg = DafyInConstant.RETURN_MSG_LOGIN_FAIL;
				
				StringBuffer buffer3 = new StringBuffer("respCode="+respCode);
				buffer3.append("&respMsg="+respMsg);
				buffer3.append("&responseTime="+responseTime);
				log.debug("============hash字段明文：【" + buffer3 + "】============");
				encryptHash = MD5Util.encryptMD5(buffer3.toString());
				log.debug("============hash字段密文：【" + encryptHash + "】============");
			}else {
				respCode = DafyInConstant.RETURN_CODE_SUCCESS;
				respMsg = DafyInConstant.RETURN_MSG_LOGIN_SUCCESS;
				DafyInMsgUtil.genToken();
				String token = DafyInMsgUtil.token;
				map.put("token", token);
				
				StringBuffer buffer2 = new StringBuffer("respCode="+respCode);
				buffer2.append("&respMsg="+respMsg);
				buffer2.append("&responseTime="+responseTime);
				buffer2.append("&token="+token);
				log.debug("============hash字段明文：【" + buffer2 + "】============");
				encryptHash = MD5Util.encryptMD5(buffer2.toString());
				log.debug("============hash字段密文：【" + encryptHash + "】============");
			}
		}else {
			respCode = DafyInConstant.RETURN_CODE_REFUSE;
			respMsg = DafyInConstant.RETURN_MSG_HASH_ERROR;
			
			StringBuffer buffer1 = new StringBuffer("respCode="+respCode);
			buffer1.append("&respMsg="+respMsg);
			buffer1.append("&responseTime="+responseTime);
			log.debug("============hash字段明文：【" + buffer1 + "】============");
			encryptHash = MD5Util.encryptMD5(buffer1.toString());
			log.debug("============hash字段密文：【" + encryptHash + "】============");
		}*/
		if ("dafyLoanLogin".equals(transCode)) {
			respCode = DafyInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
			map.put("token", "dafyLoanLoginToken001");
		}else if ("waitFill".equals(transCode)) {
			respCode = DafyInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}else if ("queryBill".equals(transCode)) {
			String userId = json.get("userId").toString();
			String loanId = json.get("loanId").toString();
			
			QueryBillResModel queryBillResModel = yunSelfLoanService.queryBill(userId, loanId);
			
			respCode = DafyInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
			map.put("userId", queryBillResModel.getUserId());
			map.put("loanId", queryBillResModel.getLoanId());
			map.put("agreementNo", queryBillResModel.getAgreementNo());
			map.put("agreementUrl", queryBillResModel.getAgreementUrl());
			ArrayList<HashMap<String, Object>>  listMap = new ArrayList<HashMap<String, Object>>();
			for (Repayments repayment : queryBillResModel.getRepayments()) {
				HashMap<String, Object> maps = new HashMap<>();
				maps.put("repayId", repayment.getRepayId());
				maps.put("status", repayment.getStatus());
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
				maps.put("repayDate", time.format(repayment.getRepayDate()));
				maps.put("repaySerial", repayment.getRepaySerial());
				maps.put("total", repayment.getTotal() );
				maps.put("principal", repayment.getPrincipal());
				maps.put("interest", repayment.getInterest());
				maps.put("principalOverdue", repayment.getPrincipalOverdue() );
				maps.put("interestOverdue", repayment.getInterestOverdue() );
				maps.put("reservedField1", repayment.getReservedField1());
				maps.put("bgwOrderNo", repayment.getBgwOrderNo());
				listMap.add(maps);
			}
			map.put("repayments", listMap);
			
		}else {
			respCode = DafyInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}

		
		String message = JSON.toJSONString(map);
		log.debug("============响应报文：【" + message + "】============");
		// 对json进行DES加密
		String ciphertext = new DESUtil(Globals.DAFY_OUT_DES_KEY).encryptStr(message);
		log.debug("============报文转换密文：【" + ciphertext + "】============");
		return ciphertext;
	}
}
