package com.pinting.site.wealth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_Bonus_RecommendBonusListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Invest_EarningsListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_AssetInfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Bonus_RecommendBonusListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Invest_EarningsListQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_AssetInfoQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
public class AccountAssetController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	
	@RequestMapping("{channel}/wealth/asset_index")
	public String assetInit(@PathVariable String channel, ReqMsg_User_AssetInfoQuery reqMsg, 
			Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response) {
		
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_ID.getName(), true);
		String nick = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_NAME.getName(), true);
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setNick(nick);
		ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService.handleMsg(reqMsg);
		dataMap.put("resMsg", resMsg);
		
		dataMap.put("withdrawPointFlag", Constants.BONUS_MIN_CAN_WITHDRAW.compareTo(resMsg.getCanWithdraw())>0 ? false : true);
		
		return channel + "/wealth/asset_index";
	}
	
	@RequestMapping("{channel}/wealth/profit_detail")
	public String profitDetail(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		
		return channel + "/wealth/profit_detail";
	}
	
	
	/**
	 * //推荐奖励信息加载
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/wealth/bonus_detail_content")
	public String bonusDetailContent(@PathVariable String channel, Integer pageIndex,ReqMsg_Bonus_RecommendBonusListQuery req,HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(pageIndex);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			req.setWithdrawFlag(request.getParameter("withdrawFlag"));
			ResMsg_Bonus_RecommendBonusListQuery resp = (ResMsg_Bonus_RecommendBonusListQuery) siteService
					.handleMsg(req);
			model.put("bonusList", resp.getBonuss());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/wealth/bonus_detail_content";
	}
	//推荐奖励
	@RequestMapping("{channel}/wealth/bonus_detail")
	public String bonusDetail(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		
		ReqMsg_Bonus_RecommendBonusListQuery reqBonus=new ReqMsg_Bonus_RecommendBonusListQuery();
		reqBonus.setUserId(Integer.valueOf(userId));
		reqBonus.setPageIndex(0);
		reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		//reqBonus.setWithdrawFlag(request.getParameter("withdrawFlag"));
		//备注：目前提现不设置时间限制，故此处提现标志 默认查询为查询所有奖励金
		reqBonus.setWithdrawFlag(Constants.ALL_BONUS);
		//查询用户表数据
		ResMsg_Bonus_RecommendBonusListQuery resp = (ResMsg_Bonus_RecommendBonusListQuery) siteService
				.handleMsg(reqBonus);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("bonus", request.getParameter("bonus"));
		model.put("withdrawFlag", request.getParameter("withdrawFlag"));
		model.put("bonusList", resp.getBonuss());
		return channel + "/wealth/bonus_detail";
	}
	@RequestMapping("{channel}/wealth/wealth_bonus_detail")
	public String wealthBonusDetail(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		
		ReqMsg_Bonus_RecommendBonusListQuery reqBonus=new ReqMsg_Bonus_RecommendBonusListQuery();
		reqBonus.setUserId(Integer.valueOf(userId));
		reqBonus.setPageIndex(0);
		reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		reqBonus.setWithdrawFlag(request.getParameter("withdrawFlag"));
		//查询用户表数据
		ResMsg_Bonus_RecommendBonusListQuery resp = (ResMsg_Bonus_RecommendBonusListQuery) siteService
				.handleMsg(reqBonus);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("bonus", request.getParameter("bonus"));
		model.put("withdrawFlag", request.getParameter("withdrawFlag"));
		model.put("bonusList", resp.getBonuss());
		return channel + "/wealth/wealth_bonus_detail";
	}
	/**
	 * //投资收益信息加载
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/wealth/interest_detail_content")
	public String interestDetailContent(@PathVariable String channel, Integer pageIndex,ReqMsg_Invest_EarningsListQuery req,HttpServletRequest request,
			HttpServletResponse response, Map<String,Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(pageIndex);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_Invest_EarningsListQuery resp = (ResMsg_Invest_EarningsListQuery) siteService
					.handleMsg(req);
			model.put("investList", resp.getInvestEarnings());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/wealth/interest_detail_content";
	}
	//投资收益
	@RequestMapping("{channel}/wealth/interest_detail")
	public String interestDetail(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		
		ReqMsg_Invest_EarningsListQuery reqIn=new ReqMsg_Invest_EarningsListQuery();
		reqIn.setUserId(Integer.valueOf(userId));
		reqIn.setPageIndex(0);
		reqIn.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_Invest_EarningsListQuery resp = (ResMsg_Invest_EarningsListQuery) siteService
				.handleMsg(reqIn);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("interest", request.getParameter("interest"));
		model.put("investList", resp.getInvestEarnings());
		return channel + "/wealth/interest_detail";
	}
	@RequestMapping("{channel}/wealth/wealth_interest_detail")
	public String wealthInterestDetail(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		
		ReqMsg_Invest_EarningsListQuery reqIn=new ReqMsg_Invest_EarningsListQuery();
		reqIn.setUserId(Integer.valueOf(userId));
		reqIn.setPageIndex(0);
		reqIn.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_Invest_EarningsListQuery resp = (ResMsg_Invest_EarningsListQuery) siteService
				.handleMsg(reqIn);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("interest", request.getParameter("interest"));
		model.put("investList", resp.getInvestEarnings());
		return channel + "/wealth/wealth_interest_detail";
	}
}
