package com.pinting.site.appActive.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.util.StringUtil;
import com.pinting.util.DESUtil;

/**
 * 
 * @ClassName: AppActiveController 
 * @Description: app活动处理Controller
 * @author lenovo
 * @date 2016年4月11日 上午10:29:53 
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("app")
public class AppActiveController {

	@RequestMapping("active/demo")
	public String demo(HttpServletRequest request,HttpServletResponse response,Map<String,Object> model) {
		String userId = request.getParameter("userId");
		if(StringUtil.isNotBlank(userId)) {
			userId = new DESUtil("cfgubijn").decryptStr(userId);
			model.put("userId", userId);
		}
		String client = request.getParameter("client");
		model.put("client", client);
		return "app/active/demo";
	}
}
