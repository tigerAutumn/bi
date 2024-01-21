package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.dto.Pay4OnlineTransDTO;
import com.pinting.business.service.manage.Pay4OnlineTransService;
import com.pinting.business.service.site.SMSService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.ReturnDWZAjax;

/**
 * 手动划拨
 * @author Dragon & cat
 * @date 2016-11-16
 */
@Controller
@RequestMapping("/manualTransfer")
public class ManualTransferController {

	@Autowired
	private SMSService smsService;
	@Autowired
	private Pay4OnlineTransService pay4OnlineTransService;
	
	@RequestMapping("/index")
	public String userRechangeStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {

		
		return "/manualTransfer/index";
	}
	
	
	/**
	 * 发送手机验证码
	 * 
	 * @param mobile
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sendMobileCode")
	@ResponseBody
	public Map<Object, Object> mobileCodeSubmit(String mobile,
			HttpServletRequest request, HttpServletResponse response) {
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		try {

			CookieManager cookie = new CookieManager(request);
			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			if(StringUtils.isBlank(mUserId)){
				return ReturnDWZAjax.fail("未获取到登录者信息");
			}
			//组织请求报文
			String resString = smsService.sendIdentify("13777588488");
			
			if ("sending error".equals(resString)) {
				return ReturnDWZAjax.fail("发送短信失败");
			}
			
			String token = pay4OnlineTransService.createMarketingToken();
			
			// 返回其它 非公共信息 字段
			dataMap.put("statusCode", 200);
			dataMap.put("message", "发送短信成功");
			dataMap.put("token", token);
			return dataMap;
			
		} catch (Exception e) {
			return ReturnDWZAjax.fail("发送短信失败");
		}
		
	}
	
	
	@RequestMapping("/transfer")
	@ResponseBody
	public Map<Object, Object> transfer(String token,String amount,String propertySymbol,String mobileCode,HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			if (smsService.validateIdentity("13777588488", mobileCode)) {
				Pay4OnlineTransDTO vo = new Pay4OnlineTransDTO();
				vo.setAmount(amount);
				vo.setPartner(propertySymbol);
				vo.setToken(token);
				// 返回其它 非公共信息 字段
				try{
					pay4OnlineTransService.pay4OnlineTrans(vo);
					return ReturnDWZAjax.success("划拨成功");
				}catch (Exception e) {
					return ReturnDWZAjax.fail("划拨失败");
				}
			}else {
				return ReturnDWZAjax.fail("短信验证码错误");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("发送短信失败");
		}
		
	}
	
}
