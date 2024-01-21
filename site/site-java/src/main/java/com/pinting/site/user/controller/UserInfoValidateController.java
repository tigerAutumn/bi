package com.pinting.site.user.controller;

import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_User_InfoValidation;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoValidation;
import com.pinting.core.base.BaseController;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.communicate.CommunicateBusiness;
/**
 * @Project: site-java
 * @Title: UserInfoValidateController.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:40:00
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@Scope("prototype")
public class UserInfoValidateController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;

	/**
	 * 验证
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/reg/{flag}")
	public @ResponseBody HashMap<String, Object> userValidateQuery(ReqMsg_User_InfoValidation reqValidate,
			HttpServletRequest request, HttpServletResponse response,@PathVariable String flag) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ReqMsg_User_InfoValidation req = new ReqMsg_User_InfoValidation();
		try {
			Field f1=req.getClass().getDeclaredField(flag);
			Field f2=reqValidate.getClass().getDeclaredField(flag);
			f1.setAccessible(true); 
			f2.setAccessible(true); 
			f1.set(req,f2.get(reqValidate)); 
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("bsCode", "999");
			dataMap.put("bsMsg","请检查链接地址！");
			return dataMap;
		}
		//验证登录名是手机号
/*		String nick = req.getNick();
		if(nick!=null&&nick.matches("^[1][34587]\\d{9}$"))
		{
			dataMap.put("bsMsg", "用户名不能为手机号");
			return dataMap;
		}*/
		ResMsg_User_InfoValidation resp = (ResMsg_User_InfoValidation) siteService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			dataMap.put("bsCode", "000");
			dataMap.put("bsMsg",resp.getResMsg());
			dataMap.put("nick",resp.getNick());
		}else
		{
			dataMap.put("bsCode", resp.getResCode());
			dataMap.put("bsMsg", resp.getResMsg());
		}
		
		
		return dataMap;
	}
}
