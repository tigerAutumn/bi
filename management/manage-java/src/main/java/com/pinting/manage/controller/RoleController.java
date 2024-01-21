package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MRole_RoleMenuQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_RoleMenuUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_roleDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_roleQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_roleSave;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_WxMenuListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_RoleMenuQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_RoleMenuUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_roleDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_roleQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_roleSave;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_WxMenuListQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.ReturnDWZAjax;
/**
 * 角色管理
 * @Project: manage-java
 * @Title: RoleController.java
 * @author Linkin
 * @date 2015-2-3 下午12:12:37
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class RoleController extends BaseController {
	// 本地sqlmap测试
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;
	public void setmanageService(HessianService manageService) {
		this.manageService = manageService;
	}

	/**
	 * 角色管理界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("/sys/role/index")
	public String MRoleInit(ReqMsg_MRole_roleQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		ResMsg_MRole_roleQuery resp=(ResMsg_MRole_roleQuery) manageService.handleMsg(req);
		model.put("roleGrid",resp.getMRoleList());
		return  "/system/role_index";
	}
	/**
	 * 角色删除
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("/sys/role/delete")
	public @ResponseBody Map<Object,Object> MRoleDelete(ReqMsg_MRole_roleDelete req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		ResMsg_MRole_roleDelete resp=(ResMsg_MRole_roleDelete) manageService.handleMsg(req);
		//model.put("roleGrid",resp.getMRoleList());
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
	
	}
	/**
	 * 角色详情
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("/sys/role/detail")
	public String MRoleDetail(ReqMsg_MRole_roleQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		if(req.getId()!=null)
		{
			ResMsg_MRole_roleQuery resp=(ResMsg_MRole_roleQuery) manageService.handleMsg(req);
			model.put("id",resp.getId());
			model.put("name",resp.getName());
			model.put("note",resp.getNote());
		}
		return  "/system/role_detail";
	}
	/**
	 * 角色保存
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("/sys/role/save")
	public @ResponseBody Map<Object,Object> MRoleSave(ReqMsg_MRole_roleSave req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MRole_roleSave resp=(ResMsg_MRole_roleSave) manageService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("保存成功！");
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
		
	}
	
	@RequestMapping("/sys/rolemenu/index")
	public String roleMenuIndex(ReqMsg_MRole_roleQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		if(StringUtils.isNotBlank(mUserId)){
			req.setmUserId(Integer.valueOf(mUserId));
		}
		ResMsg_MRole_roleQuery resp=(ResMsg_MRole_roleQuery) manageService.handleMsg(req);
		model.put("roleList",resp.getMRoleList());

		return "/system/rolemenu_index";

	}
	
	@RequestMapping("/sys/rolemenu/detail")
	public String roleMenuDetail(ReqMsg_MRole_RoleMenuQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {

		ResMsg_MRole_RoleMenuQuery resp=(ResMsg_MRole_RoleMenuQuery) manageService.handleMsg(req);
		model.put("menuList", resp.getMenus());
		model.put("roleId", resp.getRoleId());

		return "/system/rolemenu_detail";

	}
	
	@RequestMapping("/sys/rolemenu/update")
	public @ResponseBody Map<Object,Object> roleMenuUpdate(ReqMsg_MRole_RoleMenuUpdate req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MRole_RoleMenuUpdate resp = (ResMsg_MRole_RoleMenuUpdate) manageService.handleMsg(req);
		
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("保存成功！");
		} else {
			return ReturnDWZAjax.fail("保存失败！");
		}
		
	}
}
