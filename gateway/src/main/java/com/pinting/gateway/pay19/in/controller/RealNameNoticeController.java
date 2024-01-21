/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.gateway.pay19.in.model.req.RealNameAuthReq;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameNoticeController.java, v 0.1 2015-8-7 下午2:24:28
 *          BabyShark Exp $
 */
@Controller
@RequestMapping("pay19/realname")
public class RealNameNoticeController {
	private static Logger log = LoggerFactory
			.getLogger(RealNameNoticeController.class);

	@RequestMapping("notice")
	public @ResponseBody
	String notice(HttpServletRequest request, HttpServletResponse response,
			final RealNameAuthReq req) {
		// 验签
		// 实名验证实时返回，故此处通知不处理
		log.info("收到通知>>>>>>" + req);

		return "Y";

	}
}
