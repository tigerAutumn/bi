package com.pinting.site.profile.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Profile_EmailBind;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_MobileBind;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_RealNameBind;
import com.pinting.business.hessian.site.message.ReqMsg_Profile_UrgentBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_EmailBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_MobileBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_RealNameBind;
import com.pinting.business.hessian.site.message.ResMsg_Profile_UrgentBind;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * @Project: site-java
 * @Title: ProfileBindController.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:39:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@Scope("prototype")
public class ProfileBindController extends BaseController {
	
		@Autowired
		private CommunicateBusiness siteService;

	/**
	 * 实名认证
	 * 
	 * @return
	 */
	@RequestMapping("{channel}/profile/bindrealname")
	public @ResponseBody HashMap<String, Object> bindRealName(@PathVariable String channel, ReqMsg_Profile_RealNameBind req, HttpServletRequest request,
			HttpServletResponse response,	Map<String,Object> model) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		if (userId != null && !"".equals(userId.trim())) {
			req.setUserId(Integer.parseInt(userId));
		}
		//发起Hessian通讯（资产信息查询）
		ResMsg_Profile_RealNameBind resp=(ResMsg_Profile_RealNameBind) siteService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			// 返回其它 非公共信息 字段
			dataMap.put("resCode", "000");
			dataMap.put("resMsg", "修改成功，购买后完成认证");
		} else {
			// 公共信息字段返回
			dataMap.put("resCode", "999");
			dataMap.put("resMsg", resp.getResMsg());
		}
		return dataMap;
	}
	/**
	 * 绑定紧急联系人
	 * 
	 * @return
	 */
	@RequestMapping("{channel}/profile/bindurgent")
	public @ResponseBody HashMap<String, Object> bingUrgent(@PathVariable String channel, ReqMsg_Profile_UrgentBind req, HttpServletRequest request,
			HttpServletResponse response,	Map<String,Object> model) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		if (userId != null && !"".equals(userId.trim())) {
			req.setUserId(Integer.parseInt(userId));
		}
		//发起Hessian通讯（资产信息查询）
		ResMsg_Profile_UrgentBind resp=(ResMsg_Profile_UrgentBind) siteService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			// 返回其它 非公共信息 字段
			dataMap.put("resCode", "000");
			dataMap.put("resMsg", "修改紧急联系人成功");
		} else {
			// 公共信息字段返回
			dataMap.put("resCode", "999");
			dataMap.put("resMsg", resp.getResMsg());
		}
		return dataMap;
	}
	
	/**
	 * 验证手机验证码并绑定
	 * 
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/profile/bindmobile")
	public @ResponseBody HashMap<String, Object> validateMobileCodeWordSubmit(ReqMsg_Profile_MobileBind req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//发起Hessian通讯（手机号码绑定）
		ResMsg_Profile_MobileBind resp=(ResMsg_Profile_MobileBind) siteService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			// 返回其它 非公共信息 字段
			dataMap.put("resCode", "000");
			dataMap.put("resMsg", "手机绑定成功");
		} else {
			// 公共信息字段返回
			errorRes(dataMap,resp);
		}
		return dataMap;
	}
	
	/**
	 * 验证邮箱验证码并绑定
	 * 
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/profile/bindemail")
	public @ResponseBody HashMap<String, Object> addEmailSubmit(ReqMsg_Profile_EmailBind req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		if (userId != null && !"".equals(userId.trim())) {
			req.setUserID(Integer.parseInt(userId));
		}
		req.setFlag(true);//标记新增
		//发起Hessian通讯（Email地址新增绑定）
		ResMsg_Profile_EmailBind resp=(ResMsg_Profile_EmailBind) siteService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			// 返回其它 非公共信息 字段
			dataMap.put("resCode", "000");
			dataMap.put("resMsg", "邮箱绑定成功");
		} else {
			// 公共信息字段返回
			errorRes(dataMap, resp);
		}
		return dataMap;
	}
	/**
	 * 修改验证邮箱验证码并绑定
	 * 
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/profile/modifybindemail")
	public @ResponseBody HashMap<String, Object> modifyEmailSubmit(ReqMsg_Profile_EmailBind req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		//组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		if (userId != null && !"".equals(userId.trim())) {
			req.setUserID(Integer.parseInt(userId));
		}
		req.setFlag(false);//标记新增
		//发起Hessian通讯（Email地址新增绑定）
		ResMsg_Profile_EmailBind resp=(ResMsg_Profile_EmailBind) siteService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			// 返回其它 非公共信息 字段
			dataMap.put("resCode", "000");
			dataMap.put("resMsg", "邮箱修改绑定成功");
		} else {
			// 公共信息字段返回
			errorRes(dataMap, resp);
		}
		return dataMap;
	}
}
