package com.dafy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dafy.core.util.StringUtil;
import com.dafy.tools.baofoodemo.signutil.SignUtil;

@Controller
public class BaofooGetSignController {
	
		public static void main(String[] args) throws Exception {
			String userinfo = "userid001|6228481868077077179|尚家乐|130204199107177276|13588733000";
			String[] usuerInfos=userinfo.split("\\|", 2);
			System.out.println("");
			String string = SignUtil.sign(usuerInfos[1], usuerInfos[0]);
		}
		
		
		@RequestMapping(value = "/baofoo/sign/get_sign", method = RequestMethod.GET)
		@ResponseBody
		public String business(HttpServletRequest request) throws Exception {
			String userinfo = request.getParameter("userinfo");
			if (StringUtil.isBlank(userinfo)) {
				return "参数为空";
			}
			String[] usuerInfos=userinfo.split("\\|", 2);
			System.out.println("");
			String res = SignUtil.sign(usuerInfos[1], usuerInfos[0]);
			return res;
		}
		
		

}
