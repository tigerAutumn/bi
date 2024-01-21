package com.pinting.gateway.mobile.in.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: CompatibleController 
 * @Description: 将app老版本的请求替换成新版本的请求
 * @author lenovo
 * @date 2016年2月18日 下午2:33:23 
 *
 */
@Controller
@Scope("prototype")
public class CompatibleController {

	@RequestMapping("mobile/{first}/{second}")
	public String replaceSecondRequest(@PathVariable String second, HttpServletRequest req, HttpServletResponse resp) {
		return "forward:"+second+"/v_1.0.0";
	}
	
	@RequestMapping("mobile/{first}/{second}/{third}")
	public String replaceThridRequest(@PathVariable String third, HttpServletRequest req, HttpServletResponse resp) {
		return "forward:"+third+"/v_1.0.0";
	}
}
