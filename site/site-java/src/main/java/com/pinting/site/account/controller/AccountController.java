package com.pinting.site.account.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_Invest_InvestListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Invest_InvestListQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
@Scope("prototype")
public class AccountController extends BaseController {
	
	@Autowired
	private CommunicateBusiness siteService;

	/**
	 * 打开固定理财界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/account/FixFinance")
	public String fixFinanceInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_Invest_InvestListQuery reqMsg,HashMap<String,Object> model) {
		
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setStartPage(0);
		reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_Invest_InvestListQuery resp = (ResMsg_Invest_InvestListQuery) siteService
				.handleMsg(reqMsg);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotal());
		model.put("investList", resp.getValueList());
		return channel + "/account/FixFinance";
	}
	
	
	/**
	 * 打开固定理财界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/account/FixFinanceContent")
	public String fixFinanceContentInit(@PathVariable String channel,Integer pageIndex, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_Invest_InvestListQuery req,HashMap<String,Object> model) {

		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setStartPage(pageIndex * Constants.EXCHANGE_PAGE_SIZE);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_Invest_InvestListQuery resp = (ResMsg_Invest_InvestListQuery) siteService
					.handleMsg(req);
			model.put("pageIndex", pageIndex);
			model.put("totalCount", resp.getTotal());
			model.put("investList", resp.getValueList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return channel + "/account/FixFinance_content";
	}
	
}
