package com.pinting.site.assets.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.MoneyUtil;
import com.pinting.util.WeChatShareUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.Token;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;
import com.pinting.util.Util;

/**
 * 我的资产
 * 
 * @author HuanXiong
 * @version $Id: AssetsController.java, v 0.1 2015-11-12 上午10:19:39 HuanXiong
 *          Exp $
 */
@Controller
public class AssetsController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(AssetsController.class);
	
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
	private WeChatUtil weChatUtil;

	private final static String CHANNEL_H5 = "micro2.0";
	  
	/**
	 * 我的资产
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/assets/assets")
	public String assets(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> dataMap, ReqMsg_User_AssetInfoQuery reqMsg) {
	    CookieManager manager = new CookieManager(request);

		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		String nick = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_NAME.getName(), true);
		String userType = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_TYPE.getName(), true);
		String agentId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_AGENT_ID.getName(), true);

		// 存管引导信息
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		depGuideReq.setContainRisk(true);
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		request.setAttribute("hfDepGuideInfo", depGuideRes);

		// 由于会有拦截器进行userId是否为空的判断，这里就不再重复
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setNick(nick);
		ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService
				.handleMsg(reqMsg);
		dataMap.put("resMsg", resMsg);
		dataMap.put("userType", userType);
		dataMap.put("isShowReturnPath", resMsg.getIsShowReturnPath());
		dataMap.put("h5ReturnPathHide", resMsg.getH5ReturnPathHide());
		
		Map<String, String> sign = weChatUtil.createAuth(request);
		String url = GlobEnv.getWebURL("/micro2.0/index");
		String link = "";

		if (url.contains("?")) {
			link = url + "&user=" + userId + UUID.randomUUID().toString();
		} else {
			link = url + "?user=" + userId + UUID.randomUUID().toString();
		}
		sign.put("link", link);
		dataMap.put("weichat", sign);
		
		String flag = request.getParameter("flag");
		String withdraw = request.getParameter("withdraw");
		String recharge = request.getParameter("recharge");
		String safe = request.getParameter("safe");
		String invite = request.getParameter("invite");
		String selfbank = request.getParameter("selfbank");
		String accountOverview = request.getParameter("accountOverview");
		String redPacket = request.getParameter("redPacket");
		String investManage = request.getParameter("investManage");
		String repayPlan = request.getParameter("repayPlan");
		String transactionDetails = request.getParameter("transactionDetails");
		String securityCenter = request.getParameter("securityCenter");
		if(StringUtil.isNotBlank(flag) && "RETURN_PATH".equals(flag)) {
		    dataMap.put("flag", flag);
		}
		dataMap.put("withdraw", withdraw);
		dataMap.put("recharge", recharge);
		dataMap.put("safe", safe);
		dataMap.put("invite", invite);
		dataMap.put("selfbank", selfbank);
		dataMap.put("accountOverview", accountOverview);
		dataMap.put("redPacket", redPacket);
		dataMap.put("investManage", investManage);
		dataMap.put("repayPlan", repayPlan);
		dataMap.put("transactionDetails", transactionDetails);
		dataMap.put("securityCenter", securityCenter);
		dataMap.put("pageFlag", request.getParameter("pageFlag"));
		//投资分布列表查询
        ReqMsg_Invest_InvestProportion investReq = new ReqMsg_Invest_InvestProportion();
        investReq.setUserId(Integer.valueOf(userId));
        ResMsg_Invest_InvestProportion investRes = (ResMsg_Invest_InvestProportion)siteService.handleMsg(investReq);
        dataMap.put("gwInvestRes", investRes.getInvestProportionList().get(0));
        dataMap.put("zanInvestRes", investRes.getInvestProportionList().get(1));

		// 2. 查询委托计划投资列表
		ReqMsg_Invest_InvestEntrustListQuery req = new ReqMsg_Invest_InvestEntrustListQuery();
		req.setUserId(Integer.valueOf(userId));
		req.setPageSizeEntrust(Integer.MAX_VALUE);
		req.setStartPageEntrust(1);
		ResMsg_Invest_InvestEntrustListQuery res = (ResMsg_Invest_InvestEntrustListQuery) siteService.handleMsg(req);
		ArrayList<HashMap<String,Object>> list = res.getInvestAccountsEntrust();
		dataMap.put("commissionList", list);

		// 风险测评
        ReqMsg_User_QuestionnaireQuery questionReq = new ReqMsg_User_QuestionnaireQuery();
        questionReq.setUserId(Integer.valueOf(userId));
        ResMsg_User_QuestionnaireQuery questionRes = (ResMsg_User_QuestionnaireQuery)siteService.handleMsg(questionReq);
        dataMap.put("questionResMsg", questionRes);

		// 安全等级
		int safeLevel = 0;
		if("OPEN".equals(depGuideRes.getIsOpened()) || "WAIT_ACTIVATE".equals(depGuideRes.getIsOpened())){
			safeLevel++;
		}
		if("YES".equals(depGuideRes.getIsBindName())){
			safeLevel++;
		}
		if("YES".equals(resMsg.getHavePayPsd())) {
			safeLevel++;
		}
		if(questionRes.getHasQuestionnaire() != null) {
			if(questionRes.getHasQuestionnaire().equals(1)) {
				safeLevel++;
			}
		}

		int percent = 20;
		switch (safeLevel) {
			case 0:case 1: percent = 20; break;
			case 2:case 3: percent = 50; break;
			default: percent = 100; break;
		}
		dataMap.put("percent", percent);

		// 钱报标识
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            dataMap.put("qianbao", "qianbao");
            return "qianbao178/assets/assets";
        }
        
		return channel + "/assets/assets";
	}

	/**
	 * 账户总资产
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/assets/total_assets")
	public String totalAssets(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> dataMap, ReqMsg_User_AssetInfoQuery reqMsg) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		String nick = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_NAME.getName(), true);
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setNick(nick);
		ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService
				.handleMsg(reqMsg);
		dataMap.put("resMsg", resMsg);

		dataMap.put("withdrawPointFlag", Constants.BONUS_MIN_CAN_WITHDRAW
				.compareTo(resMsg.getCanWithdraw()) > 0 ? false : true);

		// 存管引导信息
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		request.setAttribute("hfDepGuideInfo", depGuideRes);

		String link = GlobEnv.getWebURL("/micro2.0/index");
		WeChatShareUtil.share(channel, link, null, null, null, false, request, dataMap, weChatUtil);

		return channel + "/assets/total_assets";
	}

	/**
	 * 修改密码
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/safe_change_password")
	public String changePassword(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/safe_change_password";
	}

	/**
	 * 修改密码
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/safe_returned_money")
	public String returnedMoney(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/safe_returned_money";
	}

	/**
	 * 安全中心
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping("{channel}/assets/safe_account")
	public String account(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/safe_account";
	}

	/**
	 * 交易密码
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/safe_traders_password")
	public String traderPassword(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/safe_traders_password";
	}

	/**
	 * 【安全中心】回款路径
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/safe_remittance_path")
	public String safeRemittancePath(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
	    String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager cookie = new CookieManager(request);
            String viewAgentFlagCookie = cookie.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }

        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
		return channel + "/assets/safe_remittance_path";
	}

	/**
	 * 交易明细
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/trading_detail")
	public String tradingDetail(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model, ReqMsg_TransDetail_QueryByUserId req) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(),
					CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(0);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_TransDetail_QueryByUserId resp = (ResMsg_TransDetail_QueryByUserId) siteService
					.handleMsg(req);
			model.put("pageIndex", 1);
			model.put("totalCount", resp.getTotalCount());
			model.put("transsList", resp.getTransPrincipals());
			model.put("processingNum", resp.getProcessingNum());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager cookie = new CookieManager(request);
            String viewAgentFlagCookie = cookie.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
		return channel + "/assets/trading_detail";
	}

	/**
	 * 交易明细-加载更多
	 * 
	 * @param channel
	 * @param pageIndex
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/trading_detail_content")
	public String tradingDetailContent(@PathVariable String channel,
			Integer pageIndex, ReqMsg_TransDetail_QueryByUserId req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(),
					CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(pageIndex-1);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_TransDetail_QueryByUserId resp = (ResMsg_TransDetail_QueryByUserId) siteService
					.handleMsg(req);
			model.put("pageIndex", pageIndex);
			model.put("totalCount", resp.getTotalCount());
			model.put("transsList", resp.getTransPrincipals());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/assets/trading_detail_content";
	}

	/**
	 * 分期产品-交易明细的回款记录列表
	 * @param channel
	 * @param req
	 * @param request
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "{channel}/assets/payment_stage_details")
	public Map<String, Object> paymentStageDetails(@PathVariable String channel, ReqMsg_TransDetail_QueryZanReturnDetail req,
												   HttpServletRequest request) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		Map<String, Object> result = new HashMap<>();
		req.setUserId(Integer.valueOf(userId));
		ResMsg_TransDetail_QueryZanReturnDetail res = (ResMsg_TransDetail_QueryZanReturnDetail) siteService.handleMsg(req);
		for (Map<String, Object> map : res.getList()) {
			map.put("transAmount", MoneyUtil.format((Double)map.get("transAmount")));
		}
		result.put("list", res.getList());
		return result;
	}


	/**
	 * 当日收益
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/day_income")
	public String dayIncome(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);

		ReqMsg_Invest_EarningsListQuery reqIn = new ReqMsg_Invest_EarningsListQuery();
		reqIn.setUserId(Integer.valueOf(userId));
		reqIn.setPageIndex(0);
		reqIn.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_Invest_EarningsListQuery resp = (ResMsg_Invest_EarningsListQuery) siteService
				.handleMsg(reqIn);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("interest", request.getParameter("interest"));
		model.put("investList", resp.getInvestEarnings());
		
		String link = GlobEnv.getWebURL("/micro2.0/index");
		// 钱报的跳转参数
		String qianbao = request.getParameter("qianbao");
		if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
		    model.put("qianbao", Constants.USER_AGENT_QIANBAO);
		    link += "?qianbao=qianbao";
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
		}
		// 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
		return channel + "/assets/day_income";
	}

	/**
	 * 当日收益-加载更多
	 * 
	 * @param channel
	 * @param pageIndex
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/day_income_content")
	public String dayIncomeContent(@PathVariable String channel,
			Integer pageIndex, ReqMsg_Invest_EarningsListQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(),
					CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(pageIndex-1);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_Invest_EarningsListQuery resp = (ResMsg_Invest_EarningsListQuery) siteService
					.handleMsg(req);
			model.put("investList", resp.getInvestEarnings());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/assets/day_income_content";
	}

	/**
	 * 我的奖励
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("{channel}/assets/my_bonus")
	public String wealthBonusDetail(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);

		ReqMsg_Bonus_RecommendBonusListQuery reqBonus = new ReqMsg_Bonus_RecommendBonusListQuery();
		reqBonus.setUserId(Integer.valueOf(userId));
		reqBonus.setPageIndex(0);
		reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		reqBonus.setWithdrawFlag(request.getParameter("withdrawFlag"));
		// 查询用户表奖励数据
		ResMsg_Bonus_RecommendBonusListQuery resp = (ResMsg_Bonus_RecommendBonusListQuery) siteService
				.handleMsg(reqBonus);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("bonus", resp.getBonus());
		model.put("withdrawFlag", request.getParameter("withdrawFlag"));
		model.put("bonusList", resp.getBonuss());
		model.put("haveSpecial", resp.getHaveSpecial());
		model.put("specialAmout",resp.getSpecialBonusAmount());

		String link = GlobEnv.getWebURL("/micro2.0/index");
		// 钱报的跳转参数
		String qianbao = request.getParameter("qianbao");
		if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
		    model.put("qianbao", Constants.USER_AGENT_QIANBAO);
		    link += "?qianbao=qianbao";
		    String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
		}
		// 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
		return channel + "/assets/my_bonus";
	}

	/**
	 * 我的奖励-加载更多
	 * 
	 * @param channel
	 * @param pageIndex
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/my_bonus_content")
	public String wealthBonusDetailContent(@PathVariable String channel,
			Integer pageIndex, ReqMsg_Bonus_RecommendBonusListQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(),
					CookieEnums._SITE_USER_ID.getName(), true);
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
		return channel + "/assets/my_bonus_content";
	}

	/**
	 * 奖励金转余额
	 * @param channel
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/bonus_to_JSH")
	public String bonusToJSH(@PathVariable String channel,
			HttpServletResponse response, HttpServletRequest request,
			Map<String, Object> model,RedirectAttributes redirectAttributes) {
        
		try {
		    
	        // 钱报的跳转参数
	        String qianbao = request.getParameter("qianbao");
	        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
	            redirectAttributes.addAttribute("qianbao", Constants.USER_AGENT_QIANBAO);
	            
	        }
			//重复提交
			if(Util.isRepeatSubmit(request, response)) {
				redirectAttributes.addAttribute("errorMsg", "奖励金转余额失败，重复提交的请求！");
			}else{
				CookieManager manager = new CookieManager(request);
				String userId = manager.getValue(CookieEnums._SITE.getName(),
						CookieEnums._SITE_USER_ID.getName(), true);
				ReqMsg_Bonus_BonusToJSH req = new ReqMsg_Bonus_BonusToJSH();
				req.setUserId(Integer.valueOf(userId));
				req.setAmount(Double.valueOf(request.getParameter("bonus")));
				ResMsg_Bonus_BonusToJSH res = (ResMsg_Bonus_BonusToJSH) siteService
						.handleMsg(req);
				if("910052".equals(res.getResCode())) {
				    redirectAttributes.addAttribute("errorMsg", "用户奖励金余额不足");
				} else {
				    if (!res.isFlag()) {
	                    redirectAttributes.addAttribute("flag", "fall");
	                    redirectAttributes.addAttribute("errorMsg", res.getResMsg());
	                    
	                }else{
	                    redirectAttributes.addAttribute("flag", "success");
	                }
				}
			}
			
		} catch (Exception e) {
			redirectAttributes.addAttribute("errorMsg", "港湾网络堵塞，请稍后再试哟");
			e.printStackTrace();
		}
		redirectAttributes.addAttribute("channel", channel);
		return "redirect:/assets/bonus2JSH_result_page";
	}
	
	/**
	 * 奖励金转余额结果页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/assets/bonus2JSH_result_page")
	public String bonus2JSHResultPage(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		 String channel = request.getParameter("channel");
	     String errorMsg = request.getParameter("errorMsg");
	     String qianbao = request.getParameter("qianbao");
	     String flag = request.getParameter("flag");
	     String link = GlobEnv.getWebURL("/micro2.0/index");
	     
	     
	     if(StringUtil.isNotBlank(qianbao)
					&& Constants.USER_AGENT_QIANBAO.equals(qianbao)){
			model.put("qianbao", Constants.USER_AGENT_QIANBAO);
			link += "?qianbao=qianbao";
			CookieManager manager = new CookieManager(request);
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
	     }
	     
	     // 分享
	     Map<String,String> sign = weChatUtil.createAuth(request);
	     sign.put("link", link);
	     model.put("weichat", sign);
	     
	     if("success".equals(flag)){
	    	model.put("flag", flag);
			return channel + "/assets/my_bonus_success";
	     }else if("fall".equals(flag)){
	    	model.put("flag", flag);
	    	if(StringUtil.isNotBlank(errorMsg)){
	    		model.put("errorMsg", errorMsg);
	    	}else{
	    		model.put("errorMsg", "");
	    	}
			model.put("time", DateUtil.addDays(new Date(),3));
			return channel + "/assets/my_bonus_success";
	     }else{
	    	 model.put("errorMsg", errorMsg);
	    	 return channel + "/assets/my_bonus_fall";
	     }
	     
		
	}

	/**
	 * 账户余额
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/assets/account_balance")
	public String accountBalance(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> dataMap, ReqMsg_User_AssetInfoQuery reqMsg) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		String nick = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_NAME.getName(), true);
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setNick(nick);
		ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService
				.handleMsg(reqMsg);
		dataMap.put("resMsg", resMsg);
		
		 String link = GlobEnv.getWebURL("/micro2.0/index");
		// 钱报的跳转参数
		String qianbao = request.getParameter("qianbao");
		if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
		    dataMap.put("qianbao", Constants.USER_AGENT_QIANBAO);
		    link += "?qianbao=qianbao";
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
		}

		// 分享
		Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        dataMap.put("weichat", sign);
		return channel + "/assets/account_balance";
	}



	/**
	 * 【账户余额】充值（已购买、充值首页）2
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/top_up")
	public String topUp(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/top_up";
	}





	/**
	 * 我的投资
	 * @param channel
	 * @param request
	 * @param model
	 * @param reqMsg
     * @return
     */
	@RequestMapping("{channel}/assets/my_investment")
	public String myInvestment(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model, ReqMsg_Invest_InvestListQuery reqMsg) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		// 0. 分享
		share(channel, model, request, GlobEnv.getWebURL("/micro2.0/index"));

		// 1. 查询非委托计划投资列表
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setStartPage(0);
		reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
		ResMsg_Invest_InvestListQuery resp = (ResMsg_Invest_InvestListQuery) siteService.handleMsg(reqMsg);
		List<HashMap<String, Object>> dataGrid = resp.getValueList();
		if(null != dataGrid) {
			for (HashMap<String, Object> data : dataGrid) {
				Date date = (Date) data.get("investEndTime");
				Date now = new Date();
				int days = DateUtil.getDiffeDay(date, now);
				data.put("days", days);
				if ((Double) data.get("redAmount") == null) {
					data.put("redAmount", 0);
				} else {
					data.put("redAmount", (Double) data.get("redAmount"));
				}
			}
		}
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotal());
		model.put("investList", dataGrid);

		// 2. 查询委托计划投资列表
		ReqMsg_Invest_InvestEntrustListQuery req = new ReqMsg_Invest_InvestEntrustListQuery();
		req.setUserId(Integer.valueOf(userId));
		req.setPageSizeEntrust(Constants.EXCHANGE_PAGE_SIZE_LONG);
		req.setStartPageEntrust(1);
		ResMsg_Invest_InvestEntrustListQuery res = (ResMsg_Invest_InvestEntrustListQuery) siteService.handleMsg(req);
		ArrayList<HashMap<String,Object>> list = res.getInvestAccountsEntrust();
		int totalPage = res.getTotalPageEntrust();
		model.put("commissionList", list);
		model.put("totalPage", totalPage);
		model.put("pageNum", 1);

		// 3. 查询处理中购买订单数
		ReqMsg_User_ProcessingOrder reqMsg_User_ProcessingOrder = new ReqMsg_User_ProcessingOrder();
		reqMsg_User_ProcessingOrder.setUserId(Integer.valueOf(userId));
		ResMsg_User_ProcessingOrder resMsg_User_ProcessingOrder = (ResMsg_User_ProcessingOrder)siteService.handleMsg(reqMsg_User_ProcessingOrder);
		model.put("processingNum", resMsg_User_ProcessingOrder.getProcessingNum());

        model.put("tomorrowDate", DateUtil.formatDateTime(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd"));
		return channel + "/assets/my_investment";
	}

	/**
	 * 委托计划列表加载更多
	 * @param channel
	 * @param pageNum	当前页码
	 * @param request
	 * @param req
	 * @param model
     * @return
     */
	@RequestMapping("{channel}/account/my_investment_commission")
	public String myInvestmentCommission(@PathVariable String channel, Integer pageNum, HttpServletRequest request, ReqMsg_Invest_InvestEntrustListQuery req, Map<String, Object> model) {
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		req.setUserId(Integer.valueOf(userId));
		req.setStartPageEntrust(pageNum);
		req.setPageSizeEntrust(Constants.EXCHANGE_PAGE_SIZE_LONG);
		ResMsg_Invest_InvestEntrustListQuery res = (ResMsg_Invest_InvestEntrustListQuery) siteService.handleMsg(req);
		ArrayList<HashMap<String,Object>> list = res.getInvestAccountsEntrust();
		int totalPage = res.getTotalPageEntrust();
		model.put("commissionList", list);
		model.put("totalPage", totalPage);
		model.put("pageNum", pageNum);

		return channel + "/assets/my_investment_commission";
	}

	/**
	 * 我的投资-加载更多
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/account/my_investment_content")
	public String myInvestmentContentInit(@PathVariable String channel,
			Integer pageIndex, HttpServletRequest request,
			HttpServletResponse response, ReqMsg_Invest_InvestListQuery reqMsg,
			HashMap<String, Object> model) {

		try {
		    // 组织请求报文
	        CookieManager manager = new CookieManager(request);
	        String userId = manager.getValue(CookieEnums._SITE.getName(),
	                CookieEnums._SITE_USER_ID.getName(), true);
	        reqMsg.setUserId(Integer.valueOf(userId));
	        reqMsg.setStartPage(pageIndex * Constants.EXCHANGE_PAGE_SIZE_LONG);
	        reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
	        ResMsg_Invest_InvestListQuery resp = (ResMsg_Invest_InvestListQuery) siteService
	                .handleMsg(reqMsg);
	        List<HashMap<String, Object>> dataGrid = resp.getValueList();
	        if(null != dataGrid) {
	            for (HashMap<String, Object> data : dataGrid) {
	                Date date = (Date) data.get("investEndTime");
	                Date now = new Date();
	                int days = DateUtil.getDiffeDay(date, now);
	                data.put("days", days);
	                if((Double)data.get("redAmount") == null) {
	                    data.put("redAmount", 0);
	                } else {
	                    data.put("redAmount", (Double)data.get("redAmount"));
	                }
	            }
	        }
	        //获取用户当前正在处理中的购买订单的数量
	        ReqMsg_User_ProcessingOrder reqMsg_User_ProcessingOrder = new ReqMsg_User_ProcessingOrder();
	        reqMsg_User_ProcessingOrder.setUserId(Integer.valueOf(userId));
	        ResMsg_User_ProcessingOrder resMsg_User_ProcessingOrder = (ResMsg_User_ProcessingOrder)siteService.handleMsg(reqMsg_User_ProcessingOrder);
	        model.put("processingNum", resMsg_User_ProcessingOrder.getProcessingNum());
	        
	        model.put("pageIndex", pageIndex);
	        model.put("totalCount", resp.getTotal());
	        model.put("investList", dataGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/assets/my_investment_content";
	}

	/**
	 * 银行卡
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/bank_card")
	public String bankCard(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/bank_card";
	}

	/**
	 * 【银行卡】银行卡详情
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/bank_card_detail")
	public String bankCardDetail(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/bank_card_detail";
	}

	/**
	 * PC账户概述
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param reqMsg
	 * @return
	 */
	@RequestMapping("/gen2.0/assets/assets_index")
    public String assetsIndex(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> model, ReqMsg_User_AssetInfoQuery reqMsg) {
	    CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        String nick = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_NAME.getName(), true);
        String userType = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_TYPE.getName(), true);
        // 由于会有拦截器进行userId是否为空的判断，这里就不再重复
        reqMsg.setUserId(Integer.valueOf(userId));
        reqMsg.setNick(nick);
        ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService
                .handleMsg(reqMsg);
        model.put("resMsg", resMsg);
        model.put("userType", userType);

		// 存管引导信息
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		depGuideReq.setContainRisk(true);
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		request.setAttribute("hfDepGuideInfo", depGuideRes);

		//投资分布列表查询
        ReqMsg_Invest_InvestProportion investReq = new ReqMsg_Invest_InvestProportion();
        investReq.setUserId(Integer.valueOf(userId));
        ResMsg_Invest_InvestProportion investRes = (ResMsg_Invest_InvestProportion)siteService.handleMsg(investReq);
        model.put("gwInvestRes", investRes.getInvestProportionList().get(0));
        model.put("zanInvestRes", investRes.getInvestProportionList().get(1));
        
		// 2. 查询委托计划投资列表
		/*ReqMsg_Invest_InvestEntrustListQuery req = new ReqMsg_Invest_InvestEntrustListQuery();
		req.setUserId(Integer.valueOf(userId));
		req.setPageSizeEntrust(Constants.EXCHANGE_PAGE_SIZE_LONG);
		req.setStartPageEntrust(1);
		ResMsg_Invest_InvestEntrustListQuery res = (ResMsg_Invest_InvestEntrustListQuery) siteService.handleMsg(req);
		ArrayList<HashMap<String,Object>> list = res.getInvestAccountsEntrust();
		int totalPage = res.getTotalPageEntrust();
		model.put("commissionList", list);
		model.put("totalPage", totalPage);
		model.put("pageNum", 1);*/

        // 风险测评
        ReqMsg_User_QuestionnaireQuery questionReq = new ReqMsg_User_QuestionnaireQuery();
        questionReq.setUserId(Integer.valueOf(userId));
        ResMsg_User_QuestionnaireQuery questionRes = (ResMsg_User_QuestionnaireQuery)siteService.handleMsg(questionReq);
        model.put("questionResMsg", questionRes);

		// 安全等级
		int safeLevel = 0;
		if("OPEN".equals(depGuideRes.getIsOpened()) || "WAIT_ACTIVATE".equals(depGuideRes.getIsOpened())){
			safeLevel++;
		}
		if(resMsg.getIsBindName().equals(1)){
			safeLevel++;
		}
		if("YES".equals(resMsg.getHavePayPsd())) {
			safeLevel++;
		}
		if(questionRes.getHasQuestionnaire() != null) {
			if(questionRes.getHasQuestionnaire().equals(1)) {
				safeLevel++;
			}
		}

		int percent = 20;
		switch (safeLevel) {
			case 0:case 1: percent = 20; break;
			case 2:case 3: percent = 50; break;
			default: percent = 100; break;
		}
		model.put("percent", percent);

        return "/gen2.0/assets/assets_index";
    }
	
	@RequestMapping("/{channel}/assets/questionnaire")
	public String questionnaireIndex(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
		String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
        	model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        } 	
		String userId = request.getParameter("userId");
		String client = request.getParameter("client");
		if (StringUtil.isNotBlank(userId) && CHANNEL_H5.equals(channel)) {
			userId = new DESUtil("cfgubijn").decryptStr(userId);
	    	model.put("userId", userId);
	    	model.put("client", client);
	    }
		return channel + "/assets/questionnaire";
	}
	
	@RequestMapping("/{channel}/assets/questionnaireResult")
	public String questionnaireResult(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
			 Map<String, Object> model, ReqMsg_User_Questionnaire req) {
	    // 组织请求报文
		String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
        	model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        } 	
	    CookieManager manager = new CookieManager(request);
	    String userId = manager.getValue(CookieEnums._SITE.getName(),
	        CookieEnums._SITE_USER_ID.getName(), true);
	    if (StringUtils.isNotBlank(userId)) {
	        req.setUserId(Integer.parseInt(userId));
	    }
	    if(CHANNEL_H5.equals(channel)) {
	    	if(StringUtil.isNotBlank(request.getParameter("userId"))) {
	    		req.setUserId(Integer.parseInt(request.getParameter("userId")));
		    	model.put("userId", new DESUtil("cfgubijn").encryptStr(request.getParameter("userId")));
		    	model.put("client", request.getParameter("client"));
	    	}
	    }
	    ResMsg_User_Questionnaire resp = (ResMsg_User_Questionnaire) siteService.handleMsg(req);
	    if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            model.put("resCode", "000");
            model.put("resMsg", resp.getAssessType());
        } else {
            // 公共信息字段返回
        	model.put("resCode", "999");
        	model.put("resMsg", resp.getResMsg());
        }
	    return channel + "/assets/questionnaire_result";
	}

	@RequestMapping("/{channel}/assets/questionnaireMore")
	public String questionnaireMore(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
		String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
        	model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        } 		
	    ReqMsg_User_QuestionnaireMoreQuery questionMoreReq = new ReqMsg_User_QuestionnaireMoreQuery();
	    CookieManager manager = new CookieManager(request);
	    String userId = manager.getValue(CookieEnums._SITE.getName(),
	        CookieEnums._SITE_USER_ID.getName(), true);
	    questionMoreReq.setUserId(Integer.valueOf(userId));
	    ResMsg_User_QuestionnaireMoreQuery questionMore = (ResMsg_User_QuestionnaireMoreQuery)siteService.handleMsg(questionMoreReq);
	    model.put("questionMoreMsg", questionMore);

		// 存管引导信息
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		depGuideReq.setContainRisk(true);
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		request.setAttribute("hfDepGuideInfo", depGuideRes);

		model.put("entry", request.getParameter("entry"));
		return channel + "/assets/questionnaire_more";
	}
	
	@RequestMapping("/{channel}/assets/questionnaireMore_app")
	public String questionnaireMoreApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
	    ReqMsg_User_QuestionnaireMoreQuery questionMoreReq = new ReqMsg_User_QuestionnaireMoreQuery();
	    String client = request.getParameter("client");
		model.put("client", client);
		String userId = request.getParameter("userId");
		model.put("userId", userId);
		if (StringUtil.isNotBlank(userId)) {
	    	if(userId.length() >= 12) {
				userId = new DESUtil("cfgubijn").decryptStr(userId);
			}
	    }
	    questionMoreReq.setUserId(Integer.valueOf(userId));
	    ResMsg_User_QuestionnaireMoreQuery questionMore = (ResMsg_User_QuestionnaireMoreQuery)siteService.handleMsg(questionMoreReq);
	    model.put("questionMoreMsg", questionMore);

		// 存管引导信息
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		depGuideReq.setContainRisk(true);
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		request.setAttribute("hfDepGuideInfo", depGuideRes);
		return channel + "/assets/questionnaire_more_app";
	}
	
	/**
	 * PC回款路径首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/gen2.0/assets/return_path_index")
	public String returnPathIndex(HttpServletRequest request, HttpServletResponse response,
	                              Map<String, Object> model) {
	    ReqMsg_Bank_ReturnPath req = new ReqMsg_Bank_ReturnPath();
	    CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_ReturnPath resMsg = (ResMsg_Bank_ReturnPath) siteService.handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            successRes(model);
        } else {
            errorRes(model);
        }
        model.put("bankName", resMsg.getBankName());
        model.put("cardId", resMsg.getCardId());
        model.put("cardNo", resMsg.getCardNo());
        model.put("returnPath", resMsg.getReturnPath());
        model.put("smallLogo", resMsg.getSmallLogo());
        model.put("largeLogo", resMsg.getLargeLogo());
        return "/gen2.0/assets/return_path_index";
	}
	
	
	// ================== 钱报178 ======================================
	/**
     * PC账户概述
     * 
     * @param request
     * @param response
     * @param model
     * @param reqMsg
     * @return
     */
    @RequestMapping("/gen178/assets/assets_index")
    public String assetsIndex178(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> model, ReqMsg_User_AssetInfoQuery reqMsg) {
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        String nick = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_NAME.getName(), true);
        String userType = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_TYPE.getName(), true);

		// 存管引导信息
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		depGuideReq.setContainRisk(true);
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		request.setAttribute("hfDepGuideInfo", depGuideRes);

		// 由于会有拦截器进行userId是否为空的判断，这里就不再重复
        reqMsg.setUserId(Integer.valueOf(userId));
        reqMsg.setNick(nick);
        ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService
                .handleMsg(reqMsg);
        model.put("resMsg", resMsg);
        model.put("userType", userType);

        //投资分布列表查询
        ReqMsg_Invest_InvestProportion investReq = new ReqMsg_Invest_InvestProportion();
        investReq.setUserId(Integer.valueOf(userId));
        ResMsg_Invest_InvestProportion investRes = (ResMsg_Invest_InvestProportion)siteService.handleMsg(investReq);
        model.put("gwInvestRes", investRes.getInvestProportionList().get(0));
        model.put("zanInvestRes", investRes.getInvestProportionList().get(1));
        
		// 2. 查询委托计划投资列表
		/*ReqMsg_Invest_InvestEntrustListQuery req = new ReqMsg_Invest_InvestEntrustListQuery();
		req.setUserId(Integer.valueOf(userId));
		req.setPageSizeEntrust(Constants.EXCHANGE_PAGE_SIZE_LONG);
		req.setStartPageEntrust(1);
		ResMsg_Invest_InvestEntrustListQuery res = (ResMsg_Invest_InvestEntrustListQuery) siteService.handleMsg(req);
		ArrayList<HashMap<String,Object>> list = res.getInvestAccountsEntrust();
		int totalPage = res.getTotalPageEntrust();
		model.put("commissionList", list);
		model.put("totalPage", totalPage);
		model.put("pageNum", 1);*/

        // 风险测评
        ReqMsg_User_QuestionnaireQuery questionReq = new ReqMsg_User_QuestionnaireQuery();
        questionReq.setUserId(Integer.valueOf(userId));
        ResMsg_User_QuestionnaireQuery questionRes = (ResMsg_User_QuestionnaireQuery)siteService.handleMsg(questionReq);
        model.put("questionResMsg", questionRes);

		// 安全等级
		int safeLevel = 0;
		if("OPEN".equals(depGuideRes.getIsOpened()) || "WAIT_ACTIVATE".equals(depGuideRes.getIsOpened())){
			safeLevel++;
		}
		if(resMsg.getIsBindName().equals(1)){
			safeLevel++;
		}
		if("YES".equals(resMsg.getHavePayPsd())) {
			safeLevel++;
		}
		if(questionRes.getHasQuestionnaire() != null) {
			if(questionRes.getHasQuestionnaire().equals(1)) {
				safeLevel++;
			}
		}
		int percent = 20;
		switch (safeLevel) {
			case 0:case 1: percent = 20; break;
			case 2:case 3: percent = 50; break;
			default: percent = 100; break;
		}
		model.put("percent", percent);
        return "/gen178/assets/assets_index";
    }
    
    /**
     * PC回款路径首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/gen178/assets/return_path_index")
    public String returnPathIndex178(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> model) {
        ReqMsg_Bank_ReturnPath req = new ReqMsg_Bank_ReturnPath();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_ReturnPath resMsg = (ResMsg_Bank_ReturnPath) siteService.handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            successRes(model);
        } else {
            errorRes(model);
        }
        model.put("bankName", resMsg.getBankName());
        model.put("cardId", resMsg.getCardId());
        model.put("cardNo", resMsg.getCardNo());
        model.put("returnPath", resMsg.getReturnPath());
        model.put("smallLogo", resMsg.getSmallLogo());
        model.put("largeLogo", resMsg.getLargeLogo());
        return "/gen178/assets/return_path_index";
    }
	// ================== 钱报178 ======================================




	/**
	 * 分享
	 * @param channel   访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
	 * @param model
	 * @param request
	 */
	private void share(String  channel, Map<String, Object> model, HttpServletRequest request, String link) {
		if(Constants.CHANNEL_MICRO.equals(channel)){
			String qianbao = request.getParameter("qianbao");
			if (StringUtil.isNotBlank(qianbao)
					&& Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
				link += "?qianbao=qianbao";
				CookieManager manager = new CookieManager(request);
	            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
		        	link += "&agentViewFlag="+viewAgentFlagCookie;
		        }
				model.put("qianbao", qianbao);
			}
			// 分享
			Map<String, String> sign = weChatUtil.createAuth(request);
			sign.put("link", link);
			model.put("weichat", sign);
		}
	}

	/**
	 * 投资管理    -- 首页进入页面
	 * @param channel
	 * @param request
	 * @param model
	 * @param reqMsg ReqMsg_User_InvestManage
     * @return
     */
	@RequestMapping("{channel}/assets/invest_manage")
	public String investManage(@PathVariable String channel, 
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,ReqMsg_User_InvestManage reqMsg) {
		// 0、取出用户ID
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		// 1、分享
		share(channel, model, request, GlobEnv.getWebURL("/micro2.0/index"));
		
		// 2、目标页面
		String  targetPageType = request.getParameter("type");
		model.put("targetPage", targetPageType);
		// 3、请求计划内容
		//	3.1、港湾计划
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setReturnType("FINISH_RETURN_ALL");
		reqMsg.setInvestStatus("HOLDING");
		reqMsg.setPageNum(1);
		reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_User_InvestManage resMsgBgw = (ResMsg_User_InvestManage) siteService.handleMsg(reqMsg);
		//	3.2、赞分期计划
		reqMsg.setReturnType("AVERAGE_CAPITAL_PLUS_INTEREST");
		ResMsg_User_InvestManage resMsgZan = (ResMsg_User_InvestManage) siteService.handleMsg(reqMsg);
		// 4、组织返回数据
		// 4.1、港湾计划分页数据
		model.put("bgwTotalPages", (int) Math.ceil((double) resMsgBgw.getCount() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("bgwHoldTotalPages", (int) Math.ceil((double) resMsgBgw.getCount() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("bgwFinishTotalPages", (int) Math.ceil((double) resMsgBgw.getCountFinishInvestBgw() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("bgwPageNum", 1);
		// 4.2、赞分期计划分页数据
		model.put("zanTotalPages", (int) Math.ceil((double) resMsgZan.getCount() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("zanHoldTotalPages", (int) Math.ceil((double) resMsgZan.getCount() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("zanFinishTotalPages", (int) Math.ceil((double) resMsgZan.getCountFinishInvestBgw() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("zanEntrustTotalPages", (int) Math.ceil((double) resMsgZan.getCountEntrustInvestBgw() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("zanPageNum", 1);
		// 4.3、返回对象
		model.put("resBgw", resMsgBgw);
		model.put("resZan", resMsgZan);
		model.put("req", reqMsg);
		return channel + "/assets/invest_manage";
	}
	/**
	 * 投资管理    -- 加载投资列表
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param reqMsg
	 * @return
	 */
	@RequestMapping("{channel}/assets/invest_manage_page")
	public String investManagePage(@PathVariable String channel, 
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,ReqMsg_User_InvestManage reqMsg) {
		// 0、取出用户ID
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		// 1、请求查询列表数据
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_User_InvestManage resMsg = (ResMsg_User_InvestManage) siteService.handleMsg(reqMsg);
		// 2、返回数据
		model.put("totalPages", (int) Math.ceil((double) resMsg.getCount() / Constants.EXCHANGE_PAGE_SIZE));
		model.put("res", resMsg);
		model.put("req", reqMsg);
		// 3、根据列表类型返回对应页面（港湾计划-FINISH_RETURN_ALL  、 委托计划   - AVERAGE_CAPITAL_PLUS_INTEREST 、  增计划 - EXIT_RETURN_ALL)
		if ("FINISH_RETURN_ALL".equals(reqMsg.getReturnType())) {
			return channel + "/assets/invest_manage_page";
		}else {
			return channel + "/assets/invest_manage_zan_page";
		}
		
	}

	/**
	 * 回款计划   -- 首页进入页面
	 * @param channel
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/payment_plant")
	public String paymentPlant(@PathVariable String channel,
							   HttpServletRequest request,HttpServletResponse response,
							   Map<String, Object> model){
		// 0、取出用户ID
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		// 1、分享
		share(channel, model, request, GlobEnv.getWebURL("/micro2.0/index"));
		String status = request.getParameter("status");
		String dateTime = request.getParameter("dateTime");
		ReqMsg_RepaySchedule_PaymentPlant reqMsg = new ReqMsg_RepaySchedule_PaymentPlant();
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setDateTime(dateTime);
		reqMsg.setStatus(status);
		reqMsg.setPageIndex(0);
		reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);

		model.put("status", status);
		try{
		    /*查询待收回款记录*/
			if("PASTDEFAULT".equals(status)){
				ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
				/*待收回款记录条数*/
				model.put("totalCount", resMsg.getTotalRecord() != null ? resMsg.getTotalRecord(): 0);
				/*返回的记录*/
				model.put("resMsgPaymentPlant", resMsg);
				model.put("pageIndex", 1);
				model.put("totalPages", resMsg.getTotalRecord() != null ? resMsg.getTotalRecord(): 0);
			}
			/*根据时间查询往期回款*/
			else if("PASTYEAR".equals(status)){
				ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
				model.put("pastTotalCount", resMsg.getTotalRecordPast() != null ? resMsg.getTotalRecordPast() : 0);
				model.put("resMsgPaymentPlant", resMsg);
				model.put("pastPageIndex", 1);
				model.put("pastTotalPages", resMsg.getTotalRecordPast() != null ? resMsg.getTotalRecordPast() : 0);
				return channel+"/assets/payment_plant_date";
			}else{
				reqMsg.setStatus(Constants.PAYMENT_PLANT_STATUS);
				ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
				model.put("resMsgPaymentPlant", resMsg);
				model.put("microTotalCount", resMsg.getTotalRecord() != null ? resMsg.getTotalRecord():0);
				model.put("microPageIndex", 1);
				model.put("microTotalPages", resMsg.getTotalRecord() != null ? resMsg.getTotalRecord():0);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return channel+"/assets/payment_plant";
	}

	/**
	 * 回款详情查询
	 * @param channel
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("{channel}/assets/payment_plant_details")
	public String paymentPlantDate(@PathVariable String channel,
							   HttpServletRequest request,HttpServletResponse response,Map<String, Object> model){

		// 0、取出用户ID
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		String status = request.getParameter("status");
		String dateTime = request.getParameter("dateTime");
		if(dateTime != null && dateTime != ""){
			dateTime = dateTime.substring(0,4) + "-" +dateTime.substring(5,7);
		}
		ReqMsg_RepaySchedule_PaymentPlant reqMsg = new ReqMsg_RepaySchedule_PaymentPlant();
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setDetailsTime(dateTime);
		reqMsg.setStatus(status);
		model.put("status", status);
		try{
		    /*详情分页查询*/
		    if("DETAILS".equals(status) || "PAGE".equals(status)){
				Integer page = Integer.parseInt(request.getParameter("pageNum"));
				reqMsg.setPage(page);
				reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
                ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
                model.put("paymentDetaclsCount",resMsg.getCount() != null?resMsg.getCount():0);
                model.put("paymentDetaclsList",resMsg);
                model.put("totalRepayScheduleTotalAmount",resMsg.getTotalRepayScheduleTotalAmount());
                model.put("totalPlanTotalRepaied",resMsg.getTotalPlanTotalRepaied());
                model.put("totalReceivableAmount",resMsg.getTotalReceivableAmount());
                model.put("bgwTotalPages", (int) Math.ceil((double) resMsg.getCount() / Constants.EXCHANGE_PAGE_SIZE));
                model.put("bgwPageNum", page);
				model.put("dateTime",dateTime);
                model.put("DateclsTime", reqMsg.getDetailsTime());
            }

			/*H5回款详情*/
			if("MICRODERICLS".equals(status)){
				ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
				model.put("count",resMsg.getTotalRecord() != null ? resMsg.getTotalRecord():0);
				model.put("totalRepayScheduleTotalAmount",resMsg.getTotalRepayScheduleTotalAmount());
				model.put("totalPlanTotalRepaied",resMsg.getTotalPlanTotalRepaied());
				model.put("paymentDetaclsList",resMsg);
				model.put("dataTime",dateTime);
				return channel+"/assets/payment_plant_page";
			}
		}catch (Exception e){

			e.printStackTrace();
		}
		return channel+"/assets/payment_plant_details";
	}
	

	/**
	 * 待收回款加载数据
	 * @param channel
	 * @param pageIndex 当前页
	 * @param status 状态标志  待收回款/往期回款
	 * @param request
	 * @param response
     * @param model
     * @return
     */
	@RequestMapping("{channel}/assets/payment_plant/loadDatas")
	public String paymentLoadDatas(@PathVariable String channel, Integer pageIndex,
								 String reqStatus, HttpServletRequest request,
								 HttpServletResponse response,HashMap<String,Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			ReqMsg_RepaySchedule_PaymentPlant reqMsg = new ReqMsg_RepaySchedule_PaymentPlant();
			reqMsg.setUserId(Integer.valueOf(userId));
			reqMsg.setPageIndex(pageIndex-1);
			reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			reqMsg.setReqStatus(reqStatus);
			String status = request.getParameter("status");
			String dateTime = request.getParameter("dateTime");
			reqMsg.setStatus(status);
			reqMsg.setDateTime(dateTime);
			
			ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
			/*待收回款记录条数*/
			model.put("totalCount", resMsg.getTotalRecord() != null ? resMsg.getTotalRecord(): 0);
			/*返回的记录*/
			model.put("resMsgPaymentPlant", resMsg);
			model.put("pageIndex", pageIndex);
			model.put("totalPages", resMsg.getTotalRecord() != null ? resMsg.getTotalRecord(): 0);
			model.put("reqMsg", reqMsg);
			model.put("status", status);
		}catch(Exception e) {
			log.error("paymentLoadDatas method exception{}", e);
		}
		return channel + "/payment/collect_list";
	}
	
	/**
	 * 往期回款加载数据
	 * @param channel
	 * @param pageIndex 当前页
	 * @param status 状态标志 待收回款/往期回款
	 * @param request
	 * @param response
	 * @param model
     * @param req
     * @return
     */
	@RequestMapping("{channel}/assets/payment_past/loadDatas")
	public String paymentPastLoadDatas(@PathVariable String channel, Integer pageIndex,
									 String reqStatus, HttpServletRequest request,
									 HttpServletResponse response, HashMap<String,Object> model,
									 ReqMsg_User_BsSubUserListQuery req) {
		try {
			CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			ReqMsg_RepaySchedule_PaymentPlant reqMsg = new ReqMsg_RepaySchedule_PaymentPlant();
			reqMsg.setUserId(Integer.valueOf(userId));
			reqMsg.setPageIndex(pageIndex-1);
			reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			reqMsg.setReqStatus(reqStatus);
			String status = request.getParameter("status");
			String dateTime = request.getParameter("dateTime");
			reqMsg.setStatus(status);
			reqMsg.setDateTime(dateTime);
			
			ResMsg_RepaySchedule_PaymentPlant resMsg = (ResMsg_RepaySchedule_PaymentPlant)siteService.handleMsg(reqMsg);
			model.put("pastTotalCount", resMsg.getTotalRecordPast() != null ? resMsg.getTotalRecordPast() : 0);
			model.put("resMsgPaymentPlant", resMsg);
			model.put("pastPageIndex", pageIndex);
			model.put("pastTotalPages", resMsg.getTotalRecordPast() != null ? resMsg.getTotalRecordPast() : 0);
			model.put("pastReqMsg", reqMsg);
			model.put("status", status);
			model.put("year", dateTime);
		}catch(Exception e) {
			log.error("paymentPastLoadDatas method exception{}", e);
		}
		return channel + "/payment/past_list";
	}

}
