package com.pinting.manage.controller;

import com.pinting.business.hessian.manage.message.*;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.manage.service.MUserService;
import com.pinting.util.GeneratePasswordUtil;
import com.pinting.util.ReturnDWZAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 后台用户管理
 *
 * @author Linkin
 * @Project: manage-java
 * @Title: UserController.java
 * @date 2015-2-3 下午12:11:58
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class UserController extends BaseController {
    // 本地sqlmap测试
    @Autowired
    @Qualifier("dispatchService")
    private HessianService siteService;

    @Autowired
    @Qualifier("mUserManageService")
    private MUserService mUserService;


    public void setSiteService(HessianService siteService) {
        this.siteService = siteService;
    }

    /**
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/user/management/index")
    public String MUserInit(ReqMsg_MUser_Info req, HttpServletRequest request,
                            HttpServletResponse response, Map<String, Object> model) {
        //发起Hessian通讯（资产信息查询）
        req.setMobile(StringUtil.trim(req.getMobile()));
        req.setName(StringUtil.trim(req.getName()));
        ResMsg_MUser_Info resp = (ResMsg_MUser_Info) siteService.handleMsg(req);
        model.put("mRoleList", resp.getMRoleList());
        model.put("userGrid", resp.getMUserList());
        model.put("userVO", resp);

        // 分页信息

        return "/user/management/index";
    }

    /**
     * 用户详情界面
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/user/management/detail")
    public String MUserDetail(ReqMsg_MUser_Info req, HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> model, String op) {
        //发起Hessian通讯（资产信息查询）
        ResMsg_MUser_Info resp = (ResMsg_MUser_Info) siteService.handleMsg(req);
        if (req.getFlag().equals("update")) {
            model.put("userVO", resp);
            if (op != null) {
                model.put("op", "none");
            }
        }

        model.put("roleList", resp.getMRoleList());
        model.put("agentList", resp.getAgentList());
        model.put("flag", req.getFlag());
        return "/user/management/detail";
    }

    /**
     * 用户注销
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/user/management/delete")
    public @ResponseBody
    Map<Object, Object> MUserDelete(ReqMsg_MUser_operate req, HttpServletRequest request,
                                    HttpServletResponse response, Map<String, Object> model) {
        //发起Hessian通讯（资产信息查询）
        if (req.getId() > 0) {
            req.setFlag("delete");
            ResMsg_MUser_operate resp = (ResMsg_MUser_operate) siteService.handleMsg(req);
            if (!resp.getFlag().equals("销户")) {
                return ReturnDWZAjax.fail("对不起，销户失败！");
            }
        }
        return ReturnDWZAjax.success("操作成功！");
    }

    /**
     * 用户修改
     *
     * @return
     */

    @RequestMapping("/user/management/update")
    public @ResponseBody
    Map<Object, Object> MUserUpdate(ReqMsg_MUser_operate req) {

        try {
            mUserService.resetPassword(req.getId());
        } catch (Exception e) {
            return ReturnDWZAjax.fail("对不起，重置密码失败！");
        }

        return ReturnDWZAjax.success("密码重置成功！");
    }

    /**
     * 用户修改密码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/user/management/updatePwd")
    public @ResponseBody
    Map<Object, Object> MUserUpdatePwd(ReqMsg_MUser_changePwd req, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
    	// 新增密码复杂度校验
		String managePwd = req.getPassword();
		if (!isValidPwd(managePwd)) {
			return ReturnDWZAjax.fail("新密码格式错误");
		}
		
    	//发起Hessian通讯（资产信息查询）
        CookieManager cookie = new CookieManager(request);
        String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        req.setId(Integer.parseInt(userId));
        ResMsg_MUser_changePwd resp = (ResMsg_MUser_changePwd) siteService.handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            return ReturnDWZAjax.success("保存成功！");
        } else {
            return ReturnDWZAjax.fail(resp.getResMsg());
        }
    }

    /**
     * 密码复杂度校验
     * @param password
     * @return
     */
	private boolean isValidPwd(String password) {
		if (StringUtil.isEmpty(password)) {
			return false;
		}
		if (password.length() < 8 || password.length() > 16) {
			return false;
		}
		if (!GeneratePasswordUtil.checkPassword(password)) {
            return false;
        }
		if (!GeneratePasswordUtil.checkStr(password)) {
			return false;
		}
		return true;
	}
	
    /**
     * 用户保存
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping("/user/management/save")
    public @ResponseBody
    Map<Object, Object> MUserSave(ReqMsg_MUser_operate req, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model) {
        //发起Hessian通讯（资产信息查询）

        if (req.getFlag().equals("create")) {
            ResMsg_MUser_operate resp = (ResMsg_MUser_operate) siteService.handleMsg(req);
            if (resp.getFlag().equals("新增")) {
                return ReturnDWZAjax.success("新增成功！");

            } else if (resp.getFlag().equals("邮箱已存在")) {
                return ReturnDWZAjax.toAjaxString("301", "邮箱已存在！");
            } else {
                return ReturnDWZAjax.fail("对不起，新增失败！");
            }
        } else {
            ResMsg_MUser_operate resp = (ResMsg_MUser_operate) siteService.handleMsg(req);
            if (resp.getFlag().equals("修改")) {
                return ReturnDWZAjax.success("修改成功！");

            } else if (resp.getFlag().equals("邮箱已存在")) {
                return ReturnDWZAjax.toAjaxString("301", "邮箱已存在！");
            } else {
                return ReturnDWZAjax.fail("对不起，修改失败！");
            }
        }

    }
}
