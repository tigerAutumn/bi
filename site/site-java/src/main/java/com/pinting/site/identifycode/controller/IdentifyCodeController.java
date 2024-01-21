package com.pinting.site.identifycode.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.base.BaseController;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * @Project: site-java
 * @Title: IdentifyCodeController.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:38:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@Scope("prototype")
public class IdentifyCodeController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	private Logger logger = LoggerFactory.getLogger(IdentifyCodeController.class);

	/**
	 * 发送手机验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/identity/mobilecode")
	public @ResponseBody HashMap<String, Object> mobileCodeSubmit(@PathVariable String channel, ReqMsg_SMS_Generate req, String verCode, String type,
			HttpServletRequest request, HttpServletResponse response) {
		
		//将type设置为存在，排除未注册号码修改请求参数调用忘记密码接口
		type = Constants.SEND_MOBILE_CODE_TYPE_EXIST;
		HashMap<String, Object> dataMap = sendMessage(req, verCode, type, request, response);
		return dataMap;
	}

	/**
	 * 未注册用户发送手机验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/identity/registerMobilecode")
	public @ResponseBody HashMap<String, Object> registerMobileCodeSubmit(@PathVariable String channel, ReqMsg_SMS_Generate req, String verCode,
			HttpServletRequest request, HttpServletResponse response) {
		
		//将type设置为存在，排除未注册号码修改请求参数调用忘记密码接口
		String type = Constants.SEND_MOBILE_CODE_TYPE_NOT_EXIST;
		HashMap<String, Object> dataMap = sendMessage(req, verCode, type, request, response);
		return dataMap;
	}
	
	/**
	 * 发送验证码短信
	 *	
	 * @param request
	 * @param response
	 * @return
	 */
	private HashMap<String, Object> sendMessage(ReqMsg_SMS_Generate req, String verCode, String type,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		CookieManager cookieManager = new CookieManager(request);
		String userID = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		String mobile = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(), true);

		if(StringUtil.isBlank(userID)) {
			/* 检查验证码是正确 */
			if(StringUtil.isBlank(verCode)) {
				dataMap.put("resCode", "error_img_code");
				dataMap.put("code", "999");
				dataMap.put("message", "请输入图片验证码！");
				return dataMap;
			}
			if (!isCode(verCode, request,response)) {
				dataMap.put("code", "999");
				dataMap.put("message", "图片验证码错误！");
				dataMap.put("resCode", "error_img_code");
				dataMap.put("resMsg", "图片验证码错误！");
				return dataMap;
			}
		}

		try {
			//组织请求报文
			ReqMsg_User_InfoValidation reqMobile = new ReqMsg_User_InfoValidation();
			reqMobile.setMobile(StringUtil.isBlank(mobile) ? req.getMobile() : mobile);
			ResMsg_User_InfoValidation resMobile = (ResMsg_User_InfoValidation) siteService.handleMsg(reqMobile);

			if((Constants.SEND_MOBILE_CODE_TYPE_NOT_EXIST.equals(type) && Constants.CODE_MOBILE_NOT_EXIST.equals(resMobile.getResCode())) // 数据库不存在手机号的情况下发送
					|| (Constants.SEND_MOBILE_CODE_TYPE_EXIST.equals(type) && Constants.CODE_MOBILE_IS_EXIST.equals(resMobile.getResCode())) // 数据库存在手机号的情况下发送
					) {
				dataMap.put("code", resMobile.getResCode());
				dataMap.put("message", resMobile.getResMsg());
				// 未注册用户发送
				req.setMobile(reqMobile.getMobile());
				ResMsg_SMS_Generate resp = (ResMsg_SMS_Generate) siteService.handleMsg(req);
				if (!Constants.SEND_CODE_ERROR.equals(resp.getMobileCode())) {
					if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
						// 返回其它 非公共信息 字段
						dataMap.put("resCode", "000");
						dataMap.put("resMsg", "发送成功，请注意接收！");
					} else {
						// 公共信息字段返回
						dataMap.put("resCode", "999");
						dataMap.put("resMsg", resp.getResMsg());
					}
				}else{
					dataMap.put("resCode", "999");
					dataMap.put("resMsg", "发送失败，请刷新页面重新发送！");
				}
			} else {
				dataMap.put("code", resMobile.getResCode());
				dataMap.put("message", resMobile.getResMsg());
				dataMap.put("resCode", resMobile.getResCode());
				dataMap.put("resMsg", resMobile.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorRes(dataMap, e);
			dataMap.put("code", "999");
			dataMap.put("message", e.getMessage());
		}
		
		return dataMap;
	}


	/**
	 * 判断验证码
	 * @param code
	 * @param request
	 * @return
	 */
	private boolean isCode(String code, HttpServletRequest request,HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		String _code = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_CODE.getName(), true);
		cookie.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_CODE.getName(), null, true);
		cookie.save(response, CookieEnums._SITE.getName(), true);
		return _code.equalsIgnoreCase(code);
	}

	
	/**
	 * 验证手机验证码
	 * 
	 * @param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/identity/validatemobile")
	public @ResponseBody HashMap<String, Object> validateMobilePreCodeWordSubmit(ReqMsg_SMS_Validation req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ResMsg_SMS_Validation resp = (ResMsg_SMS_Validation) siteService.handleMsg(req);
		try {
			//组织请求报文
			if (resp.getIsValidateSuccess()!=null&&resp.getIsValidateSuccess()) {
				// 返回其它 非公共信息 字段
					dataMap.put("resCode", "000");
					dataMap.put("resMsg", "验证成功！");
			} else {
				dataMap.put("resCode", "999");
				dataMap.put("resMsg", resp.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorRes(dataMap, e);
		}
		
		return dataMap;
	}
	/**
	 * 发送邮箱验证码
	 * 
	 * @param
	 * @param
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/identity/emailcode")
	public @ResponseBody HashMap<String, Object> emailCodeSubmit(ReqMsg_Email_Generate req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		try {
			//组织请求报文
			ResMsg_Email_Generate resp = (ResMsg_Email_Generate) siteService.handleMsg(req);
			if (!Constants.SEND_CODE_ERROR.equals(resp.getEmailCode())) {
				if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
					// 返回其它 非公共信息 字段
					dataMap.put("resCode", "000");
					dataMap.put("resMsg", "发送成功，请注意接收！");
				} else {
					// 公共信息字段返回
					dataMap.put("resCode", "999");
					dataMap.put("resMsg", resp.getResMsg());
				}
			}else{
				dataMap.put("resCode", "999");
				dataMap.put("resMsg", "发送失败，请刷新页面重新发送！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			errorRes(dataMap, e);
		}
		
		return dataMap;
	}
	/**
	 * 验证邮箱验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/identity/validateemail")
	public @ResponseBody HashMap<String, Object> validateEmailPreCodeWordSubmit(ReqMsg_Email_Validation req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		ResMsg_Email_Validation resp = (ResMsg_Email_Validation) siteService.handleMsg(req);
		try {
			//组织请求报文
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
				// 返回其它 非公共信息 字段
				dataMap.put("resCode", "000");
				dataMap.put("resMsg", "验证成功！");
			} else {
				dataMap.put("resCode", "999");
				dataMap.put("resMsg", resp.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorRes(dataMap, e);
		}
		
		return dataMap;
	}
	
	
	/**
	 * 获取短信验证码发送的时间差距
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("gen2.0/identity/interval_time")
	public Map<String, Object> identityInterval(ReqMsg_SMS_Interval req,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ResMsg_SMS_Interval resp = (ResMsg_SMS_Interval) siteService.handleMsg(req);
			//组织请求报文
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
				// 返回其它 非公共信息 字段
				dataMap.put("resCode", "000");
				dataMap.put("resInterval", resp.getInterval());
			} else {
				dataMap.put("resCode", "999");
				dataMap.put("resMsg", resp.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorRes(dataMap, e);
		}
		
		return dataMap;
	}
	
	

}
