package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_MHome_MenuQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MHome_MenuQuery;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.manage.enums.CookieEnums;


/**
 * 主页
 * @Project: manage-java
 * @Title: HomeController.java
 * @author dingpf
 * @date 2015-1-28 下午4:14:53
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class HomeController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;
	
	/**
	 * 登入后的首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/home/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		CookieManager cookie = new CookieManager(request);
		String _userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		String _roleId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_ID.getName(), true);
		String _userName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), true);
		String _roleName = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_ROLE_NAME.getName(), true);
		String isDafyUserString = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_DAFY_ISDAFYUSER.getName(), true);
		ReqMsg_MHome_MenuQuery reqMsg = new ReqMsg_MHome_MenuQuery();
		reqMsg.setUserId(_userId);
		reqMsg.setRoleId(_roleId);
		ResMsg_MHome_MenuQuery resMsg = (ResMsg_MHome_MenuQuery) manageService.handleMsg(reqMsg);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
			//model.put("userName", resMsg.getUserName());
			//model.put("roleName", resMsg.getRoleName());
			model.put("userName", _userName);
			model.put("roleName", _roleName);
			model.put("roleId", resMsg.getRoleId());
			model.put("menuList", resMsg.getMenus());
			if("yes".equals(isDafyUserString)) {
				String mobile = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_EMAIL.getName(), true);
				model.put("isDafyUser", isDafyUserString);
				model.put("customerManagerMobile", mobile);
			}
			
			return "/home/index";
		}
		return "/errors/500";
		
	}
	/**
	 * 修改密码界面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/home/updatePwd")
	public String updatePwd(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
	
		return "/home/changePwd";
		
	}
	
	@RequestMapping("/errors/noRoot")
	public String noRoot(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
	
		return "/errors/noRoot";
		
	}
	
	
}
	
	
