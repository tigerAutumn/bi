package com.pinting.site.wealth.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Bonus_BonusWithdraw;
import com.pinting.business.hessian.site.message.ReqMsg_User_AssetInfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Bonus_BonusWithdraw;
import com.pinting.business.hessian.site.message.ResMsg_User_AssetInfoQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
public class WithdrawController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	
	@RequestMapping("{channel}/wealth/withdraw_index")
	public String withdrawInit(@PathVariable String channel, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		model.put("canWithdraw", request.getParameter("cw"));
		return channel + "/wealth/withdraw_index";
	}
	
	@RequestMapping("{channel}/wealth/checkHasProduct")
	public @ResponseBody  HashMap<String,Object> regularBuyCheckIsLeagal(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response, ReqMsg_User_AssetInfoQuery reqMsg){
		HashMap<String,Object> dataMap = new HashMap<String,Object>();
		try{
			String result = "no";
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
			reqMsg.setUserId(Integer.parseInt(userId));
			ResMsg_User_AssetInfoQuery res = (ResMsg_User_AssetInfoQuery) siteService.handleMsg(reqMsg);
			String message = "";
			if(res.getInvestNum() <= 0 || res.getRegularAmount() < 100){
				message = "亲，您至少要购买100元理财产品，才能提现哦~";
				result="no";
			}else{
				result = "yes";
			}
			dataMap.put("result", result);
			dataMap.put("message", message);
		} catch (Exception e) {
			errorRes(dataMap, e);
			e.printStackTrace();
		}
		return dataMap;
	}
	
	
	@RequestMapping("{channel}/wealth/withdraw")
	public String withdrawSubmit(@PathVariable String channel, ReqMsg_Bonus_BonusWithdraw reqMsg, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_ID.getName(), true);
		String nick = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_NAME.getName(), true);
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setNick(nick);
		ResMsg_Bonus_BonusWithdraw resMsg = (ResMsg_Bonus_BonusWithdraw) siteService.handleMsg(reqMsg);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
			successRes(model);
			model.put("predictDate", DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), 1)));
		}else{
			errorRes(model, resMsg.getResMsg());
		}
		
		return channel + "/wealth/withdraw_result";
	}
	
	/**
	 * 验证可提现金额
	 * @param channel
	 * @param request
	 * @param response
	 * @param reqMsg
	 * @return
	 */
	@ResponseBody
    @RequestMapping("{channel}/wealth/checkCanWithdraw")
	public Map<String, Object> checkCanWithdraw(@PathVariable String channel, HttpServletRequest request,
	        HttpServletResponse response, ReqMsg_User_AssetInfoQuery reqMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService
				.handleMsg(reqMsg);
        result.put("canWithdraw", resMsg.getCanWithdraw());
        return result;
	}
}
