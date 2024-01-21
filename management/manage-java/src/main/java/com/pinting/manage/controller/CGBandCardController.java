package com.pinting.manage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.model.vo.CGBindCardResVO;
import com.pinting.manage.service.CGBindCardService;
import com.pinting.util.Util;
/**
 * 存管上线-临时绑卡
 * @author bianyatian
 * @2017-9-20 下午3:30:29
 */
@Controller
@RequestMapping("/cgBandCard")
public class CGBandCardController {

	private final Logger logger = LoggerFactory.getLogger(CGBandCardController.class);
	@Autowired
	private   CGBindCardService   cgBindCardService;
	
	@RequestMapping("/index")
	public @ResponseBody
	String cgBandCardIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String id = request.getParameter("ucUserId");
		Integer ucUserId = null;
		if(StringUtils.isNotBlank(id)){
			ucUserId = Integer.parseInt(id);
		}
		
		List<CGBindCardResVO> list = cgBindCardService.cgBindCard(ucUserId);
		Util.convertToFile(list);
		return "OK";
	}
}
