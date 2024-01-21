package com.dafy.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.encrypt.MD5Util;
import com.dafy.tools.qidian.QiDianInConstant;

@Controller
public class QiDianController {
	private static final Logger log = LoggerFactory.getLogger(QiDianController.class);
	
	/**
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/qidian/{code}/{code2}/{code3}")
	@ResponseBody
	public String login(HttpServletRequest request, @PathVariable String code) throws Exception {
		// DES解密，获得明文
		System.out.println("币港湾-->七店");
		String decryptData = new DESUtil(Globals.QIDIAN_OUT_DES_KEY).decryptStr(request.getParameter("DATA").replace(" ", "+"));
		JSONObject json = JSON.parseObject(decryptData);
		log.debug("============请求报文明文："+code+"【" + decryptData + "】============");
		String transCode = json.get("transCode").toString();
		String clientKey = json.get("clientKey").toString();
		String requestTime = json.get("requestTime").toString();
		String hash = json.get("hash").toString();
		
		//重新组装hash，验证hash是否正确
		StringBuffer buffer = new StringBuffer("transCode="+transCode);
		buffer.append("&clientKey="+clientKey);
		buffer.append("&requestTime="+requestTime);
		log.debug("============hash字段明文：【" + buffer + "】============");
		String newHash = MD5Util.encryptMD5(buffer.toString());
		log.debug("============hash字段密文：【" + newHash + "】============");
		
		String responseTime = DateUtil.format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		String respCode = "";
		String respMsg = "";
		String encryptHash = "";

		if (QiDianInConstant.LOGIN.equals(transCode)) {
			respCode = QiDianInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
			map.put("token", "dafyLoanLoginToken001");
		}else if (QiDianInConstant.CUSTOMER_INFO_SYNC.equals(transCode)) {
			respCode = QiDianInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}else if (QiDianInConstant.ORDER_INFO_SYNC.equals(transCode)) {
			respCode = QiDianInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}else {
			respCode = QiDianInConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}

		
		String message = JSON.toJSONString(map);
		log.debug("============响应报文：【" + message + "】============");
		// 对json进行DES加密
		String ciphertext = new DESUtil(Globals.QIDIAN_OUT_DES_KEY).encryptStr(message);
		log.debug("============报文转换密文：【" + ciphertext + "】============");
		return ciphertext;
	}
	
	

}
