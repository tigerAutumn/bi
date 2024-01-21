package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.service.manage.YouBeiService;
import com.pinting.util.ReturnDWZAjax;

@Controller
@RequestMapping("/youbei")
public class YouBeiController {
	
	@Autowired
	private YouBeiService youbeiService;
	
	@RequestMapping("/checkRealName")
	@ResponseBody
	public Map<Object,Object> checkRealName(HttpServletRequest request, HttpServletResponse response) {
		String idCard = request.getParameter("idCard");
		String name = request.getParameter("name");
		String cardNo = request.getParameter("cardNo");
		String mobile = request.getParameter("mobile");
		Map<String,Object> map = youbeiService.checkRealName(idCard, name, cardNo, mobile);
		if("yes".equals((String)map.get("isOk"))) {
			return ReturnDWZAjax.success("实名认证成功，结果：匹配");
		}
		else {
			return ReturnDWZAjax.fail((String)map.get("msg"));
		}
	}
	
}
