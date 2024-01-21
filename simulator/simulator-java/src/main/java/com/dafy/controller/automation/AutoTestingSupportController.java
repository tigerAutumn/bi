package com.dafy.controller.automation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.StringUtil;

/**
 * 自动化测试-服务支持
 * @project simulator-java
 * @title AutoTestingSupportController.java
 * @author Dragon & cat
 * @date 2018-4-3
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
public class AutoTestingSupportController {
	private Logger log = LoggerFactory.getLogger(AutoTestingSupportController.class);
	
	@RequestMapping(value = "/auto_testing/decrypt_result", method = RequestMethod.GET)
	@ResponseBody
	public String decryptResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String ciphertext = request.getParameter("ciphertext");
		String partnerCode = request.getParameter("partnerCode");
		
		if ("YUN_DAI_SELF".equals(partnerCode)) {
			if (StringUtil.isBlank(ciphertext)) {
				return "{\"respCode\":\"ERROR\",\"respMsg\":\"parameter empty\"}";
			}
			log.info("============获得密文：【" + ciphertext + "】============");
			String decryptData = new DESUtil(Globals.DAFY_IN_DES_KEY).decryptStr(ciphertext.replace(" ", "+"));
			log.info("============解密成功，解密获得明文：【" + decryptData + "】============");
			return decryptData;
		}else if ("QIDIAN".equals(partnerCode)) {
			if (StringUtil.isBlank(ciphertext)) {
				return "{\"respCode\":\"ERROR\",\"respMsg\":\"parameter empty\"}";
			}
			log.info("============获得密文：【" + ciphertext + "】============");
			String decryptData = new DESUtil(Globals.QIDIAN_IN_DES_KEY).decryptStr(ciphertext.replace(" ", "+"));
			log.info("============解密成功，解密获得明文：【" + decryptData + "】============");
			return decryptData;
		}else {
			return "{\"respCode\":\"ERROR\",\"respMsg\":\"partnerCode do not exist\"}";
		}
		

	}
	
}
