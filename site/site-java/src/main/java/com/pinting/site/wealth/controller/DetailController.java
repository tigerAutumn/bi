package com.pinting.site.wealth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_Account_AccountJnlListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Account_AccountJnlListQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
public class DetailController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	/**
	 * 交易明细信息加载
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/wealth/trans_detail_content")
	public String transDetailContent(@PathVariable String channel, Integer pageIndex,ReqMsg_Account_AccountJnlListQuery req,HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(pageIndex);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_Account_AccountJnlListQuery resp = (ResMsg_Account_AccountJnlListQuery) siteService
					.handleMsg(req);
			model.put("pageIndex", resp.getPageIndex());
			model.put("totalCount", resp.getTotalCount());
			model.put("transsList", resp.getTransPrincipals());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/wealth/trans_detail_content";
	}
	
	@RequestMapping("{channel}/wealth/trans_detail")
	public String transDetailInit(@PathVariable String channel, HttpServletRequest request,ReqMsg_Account_AccountJnlListQuery req,
		HttpServletResponse response, Map<String,Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(0);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_Account_AccountJnlListQuery resp = (ResMsg_Account_AccountJnlListQuery) siteService
					.handleMsg(req);
			model.put("pageIndex", 0);
			model.put("totalCount", resp.getTotalCount());
			model.put("transsList", resp.getTransPrincipals());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/wealth/trans_detail";
	}
	
	
}
