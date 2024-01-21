/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.finance.controller;

import com.pinting.business.hessian.site.message.*;
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
import com.pinting.util.Util;
import com.pinting.util.WeChatShareUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现
 * 
 * @author HuanXiong
 * @version $Id: Withdraw.java, v 0.1 2015-11-19 上午10:02:59 HuanXiong Exp $
 */
@Controller
public class UserWithdrawController extends BaseController {

	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
    private WeChatUtil weChatUtil;

	@Token(save = true)
	@RequestMapping("{channel}/withdraw/withdraw_deposit")
	public String userWithdraw(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		CookieManager manager = new CookieManager(request);
		String wfrom = StringUtil.isBlank(request.getParameter("wfrom")) ? (String) request.getAttribute("wfrom") : request.getParameter("wfrom");

		String userId = "";
		try {
			userId = manager.getValue(CookieEnums._SITE.getName(),
					CookieEnums._SITE_USER_ID.getName(), true);
			share(channel, model, request, GlobEnv.getWebURL("/micro2.0/index"));

			// 存管引导信息
			ReqMsg_DepGuide_FindDepGuideInfo depReq = new ReqMsg_DepGuide_FindDepGuideInfo();
			depReq.setUserId(Integer.parseInt(userId));
			depReq.setContainRisk(true);
			ResMsg_DepGuide_FindDepGuideInfo depRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depReq);
			if(Constants.CHANNEL_MICRO.equals(channel)) {
				if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depRes.getIsOpened())
						|| Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depRes.getIsOpened())) {
					// 未存管绑卡或激活
					if(Constants.FROM_178_APP.equals(wfrom)) {
						String backUrl = manager.getValue(CookieEnums._SITE.getName(),
								CookieEnums._SITE_178_BACK_URL.getName(), true);
						model.put("backUrl", backUrl);
						model.put("wfrom", wfrom);
						request.setAttribute("wfrom", null);
						request.setAttribute("channel", null);
						request.setAttribute("qianbao", null);
						return "redirect:/micro2.0/bankcard/index?entry=withdraw&from="+wfrom;
					}
					return "redirect:/micro2.0/bankcard/index?entry=withdraw";
				} else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depRes.getIsOpened())) {
					return "redirect:/micro2.0/bankcard/activate/index";
				}
				String qianbao = request.getParameter("qianbao");
				if(Constants.is_expire.equals(depRes.getRiskStatus())
						|| Constants.is_no.equals(depRes.getRiskStatus())) {
					// 需要进行风险测评
					if(Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
						return "redirect:/micro2.0/assets/questionnaireMore?entry=withdraw&qianbao=qianbao";
					} else {						
						return "redirect:/micro2.0/assets/questionnaireMore?entry=withdraw";
					}
				}

			}
			model.put("hfDepGuideInfo", depRes);

			ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
			reqMsg.setUserId(Integer.parseInt(userId));
			ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService
					.handleMsg(reqMsg);
			String cardNo = resp.getCardNo();
			model.put("bankLogo", resp.getSmallLogo());
			model.put("bankName", resp.getBankName());
			if (StringUtil.isNotBlank(cardNo)) {
				model.put("cardNo",
						cardNo.substring(cardNo.length() - 4, cardNo.length()));
				model.put("bankId", resp.getBankId());
			} else {
				model.put("cardNo", cardNo);
				model.put("bankId", "");
			}
			model.put("canWithdraw", resp.getCan_withdraw());
			model.put("depCanWithdraw", resp.getDepCanWithdraw());
			model.put("withdrawTimes", resp.getCan_withdraw_times() == null ? 0
					: resp.getCan_withdraw_times());
			model.put("cardId", resp.getCardId());

			model.put("withdrawLimit", resp.getWithdrawLimit());
			model.put("singleWithdrawUpperLimit", resp.getSingleWithdrawUpperLimit());
			model.put("dayWithdrawUpperLimit", resp.getDayWithdrawUpperLimit());

			//获取提现时间限制
			ReqMsg_UserWithdraw_CanWithdrawTime reqcw = new ReqMsg_UserWithdraw_CanWithdrawTime();
			ResMsg_UserWithdraw_CanWithdrawTime rescw = (ResMsg_UserWithdraw_CanWithdrawTime) siteService
					.handleMsg(reqcw);

			model.put("begin", rescw.getBegin()); //页面文案显示
			model.put("end", rescw.getEnd());
			long beginTime = DateUtil.parseDateTime(rescw.getBeginTime()).getTime();
			long endTime = DateUtil.parseDateTime(rescw.getEndTime()).getTime();
			if(beginTime >= endTime){
				endTime = endTime + 24*3600000;
				model.put("end", "次日"+rescw.getEnd());
			}
			model.put("beginTime",beginTime );
			model.put("endTime",endTime);
			model.put("withdrawLimit", resp.getWithdrawLimit());
			// 每月提现总次数
			model.put("eachMonthWithdrawTimes", resp.getEachMonthWithdrawTimes());
			// 手续费
			model.put("withdrawCounterFee", resp.getWithdrawCounterFee());
			// 提现金额限制值
			model.put("withdrawLimitAmount", resp.getWithdrawLimitAmount());

			if(!Constants.CHANNEL_MICRO.equals(channel)) {
				ReqMsg_User_InfoQuery userInfoReq = new ReqMsg_User_InfoQuery();
				userInfoReq.setUserId(Integer.parseInt(userId));
				ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery) siteService.handleMsg(userInfoReq);
				//判断是否存在支付密码 1:有交易密码；2：无交易密码
				if (Constants.USER_PAY_PASSWORD_EXIST_NO.equals(userInfoRes.getExcitPaypassword())) {
					model.put("payPasswordExist", "FALSE");
				} else {
					model.put("payPasswordExist", "TRUE");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
			return channel + "/assets/withdraw_index";
		} else {
			model.put("from", request.getParameter("from"));
			model.put("amount", request.getParameter("amount"));
		}
		
		//qianbao178提现交易H5
        if(Constants.FROM_178_APP.equals(wfrom)) {
			String backUrl = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_178_BACK_URL.getName(), true);
			model.put("backUrl", backUrl);
            model.put("wfrom", wfrom);
            
            request.setAttribute("wfrom", null);
			request.setAttribute("channel", null);
			request.setAttribute("qianbao", null);

			// 查询用户状态是否冻结
			ReqMsg_User_ConfirmTransaction userStatusReq = new ReqMsg_User_ConfirmTransaction();
			userStatusReq.setUserId(Integer.parseInt(userId));
			ResMsg_User_ConfirmTransaction userStatusRes = (ResMsg_User_ConfirmTransaction) siteService.handleMsg(userStatusReq);
			model.put("userStatus", userStatusRes.getUserStatus());
			
			// 3、查询绑定银行
			ReqMsg_Bank_bindBankList reqMsgBankList = new ReqMsg_Bank_bindBankList();
			reqMsgBankList.setUserId(Integer.parseInt(userId));
			ResMsg_Bank_bindBankList resMsgBankList = (ResMsg_Bank_bindBankList) siteService
					.handleMsg(reqMsgBankList);
			if (!resMsgBankList.isBindBank()) {
				return "redirect:/micro2.0/bankcard/index?entry=withdraw&from="+wfrom;
			}
			
        	return "/qianbao178/app/withdraw/withdraw_deposit";
        }
		return channel + "/withdraw/withdraw_deposit";
	}

	@ResponseBody
	@RequestMapping("{channel}/withdraw/before_withdraw")
	public Map<String, Object> beforeWithdraw(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			ReqMsg_User_CheckPayPassword req) {
		Map<String, Object> result = new HashMap<String, Object>();
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		req.setUserId(Integer.parseInt(userId));
		try {
			// 核对交易密码
			ResMsg_User_CheckPayPassword msg = (ResMsg_User_CheckPayPassword) siteService
					.handleMsg(req);
			result.put("failNums", msg.getFailNums());
			result.put("toastMsg", msg.getToastMsg());
			result.put("failTime", msg.getFailTime());
			result.put("truePayPassword", msg.isTruePayPassword());

			// 获取可提现次数
			ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
			reqMsg.setUserId(Integer.parseInt(userId));
			ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService
					.handleMsg(reqMsg);
			result.put("canWithdraw", resp.getCan_withdraw());
			result.put(
					"withdrawTimes",
					resp.getCan_withdraw_times() == null ? 0 : resp
							.getCan_withdraw_times());
			
			//获取提现时间限制
			ReqMsg_UserWithdraw_CanWithdrawTime reqcw = new ReqMsg_UserWithdraw_CanWithdrawTime();
			ResMsg_UserWithdraw_CanWithdrawTime rescw = (ResMsg_UserWithdraw_CanWithdrawTime) siteService
					.handleMsg(reqcw);
			long beginTime = DateUtil.parseDateTime(rescw.getBeginTime()).getTime();
			long endTime = DateUtil.parseDateTime(rescw.getEndTime()).getTime();
			if(beginTime >= endTime){
				endTime = endTime + 24*3600000;
			}
			long nowDate = new Date().getTime();
			if(nowDate>= beginTime && nowDate<= endTime){
				result.put("timeLimit", "NO");
			}else{
				result.put("timeLimit", "YES");
			}
			successRes(result, msg);

		} catch (Exception e) {
			errorRes(result, e);
			log.error("=========================检查交易密码==========================");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 我要提现
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/withdraw/withdraw_submit")
	public String withdrawSubmit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,RedirectAttributes redirectAttributes) {
		try {
			// 钱报的参数
			String qianbao = request.getParameter("qianbao");
			
			if (StringUtil.isNotBlank(qianbao)
					&& Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
				redirectAttributes.addAttribute("qianbao", Constants.USER_AGENT_QIANBAO);
			}
			
			
			String from = request.getParameter("wfrom");
			redirectAttributes.addAttribute("from", from);
			String backUrl = request.getParameter("backUrl");
			redirectAttributes.addAttribute("backUrl", backUrl);
			log.info("进入提现业务逻辑from="+from + "backurl="+backUrl);
			
			// 重复提交
			if (Util.isRepeatSubmit(request, response)) {
				redirectAttributes.addAttribute("errorMsg", "重复提交的请求！交易结果请查看交易明细");
			}else {
				CookieManager manager = new CookieManager(request);

				String userId = manager.getValue(CookieEnums._SITE.getName(),
						CookieEnums._SITE_USER_ID.getName(), true);
				String amount = request.getParameter("amount");
				String payPassword = request.getParameter("payPassword");

				ReqMsg_UserBalance_Withdraw reqMsg = new ReqMsg_UserBalance_Withdraw();
				reqMsg.setAmount(Double.parseDouble(amount));
				reqMsg.setPassword(payPassword);
				reqMsg.setUserId(Integer.parseInt(userId));
				reqMsg.setAccountType(request.getParameter("accountType"));
				// 终端类型@1手机端,2PC端
				if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
					reqMsg.setTerminalType(2);
				} else {
					reqMsg.setTerminalType(1);
				}

				ResMsg_UserBalance_Withdraw res = (ResMsg_UserBalance_Withdraw) siteService
						.handleMsg(reqMsg);

				if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					redirectAttributes.addAttribute("resCode", "000");
					redirectAttributes.addAttribute("resMsg", res.getResMsg());
					redirectAttributes.addAttribute("isNeedCheck", res.isNeedCheck());
					redirectAttributes.addAttribute("time", DateUtil.format(DateUtil.addDays(res.getTime(),1)));
				} else {
					redirectAttributes.addAttribute("resCode", res.getResCode());
					redirectAttributes.addAttribute("resMsg", res.getResMsg());
				}
			}
		} catch (Exception e) {
			redirectAttributes.addAttribute("resCode", "999");
			redirectAttributes.addAttribute("resMsg", e.getMessage());
			e.printStackTrace();
		}

		redirectAttributes.addAttribute("channel", channel);
		return "redirect:/withdraw/withdraw_result_page";
	}



	/**
	 * 我要提现
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{channel}/withdraw/withdraw_submit_qb")
	public Map<String, Object> withdrawSubmitQb(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			 ReqMsg_UserBalance_Withdraw withdraw) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// 钱报的参数
			String qianbao = request.getParameter("qianbao");

			if (StringUtil.isNotBlank(qianbao)
					&& Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
				//redirectAttributes.addAttribute("qianbao", Constants.USER_AGENT_QIANBAO);
				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
			}


			String from = request.getParameter("wfrom");
			//redirectAttributes.addAttribute("from", from);
			model.put("from", from);
			String backUrl = request.getParameter("backUrl");
			//redirectAttributes.addAttribute("backUrl", backUrl);
			model.put("backUrl", backUrl);
			log.info("进入提现业务逻辑请求信息：{}", JSONObject.fromObject(withdraw));

			// 重复提交
			if (Util.isRepeatSubmit(request, response)) {
				//redirectAttributes.addAttribute("errorMsg", "重复提交的请求！交易结果请查看交易明细");
				model.put("errorMsg", "重复提交的请求！交易结果请查看交易明细");
				model.put("resMsg", "重复提交的请求！交易结果请查看交易明细");
			}else {
				CookieManager manager = new CookieManager(request);
				
				String userId = manager.getValue(CookieEnums._SITE.getName(),
						CookieEnums._SITE_USER_ID.getName(), true);
				String amount = request.getParameter("amount");
				String payPassword = request.getParameter("payPassword");
				
				ReqMsg_UserBalance_Withdraw reqMsg = new ReqMsg_UserBalance_Withdraw();
				reqMsg.setAmount(Double.parseDouble(amount));
				reqMsg.setPassword(payPassword);
				reqMsg.setUserId(Integer.parseInt(userId));
				// 终端类型@1手机端,2PC端
				if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
					reqMsg.setTerminalType(2);
				} else {
					reqMsg.setTerminalType(1);
				}
				reqMsg.setAccountType(withdraw.getAccountType());
				ResMsg_UserBalance_Withdraw res = (ResMsg_UserBalance_Withdraw) siteService
						.handleMsg(reqMsg);
				
				if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					//redirectAttributes.addAttribute("resCode", "000");
					//redirectAttributes.addAttribute("resMsg", res.getResMsg());
					//redirectAttributes.addAttribute("isNeedCheck", res.isNeedCheck());
					//redirectAttributes.addAttribute("time", DateUtil.format(DateUtil.addDays(res.getTime(),1)));
				
					model.put("resCode", "000");
					model.put("resMsg", res.getResMsg());
					model.put("isNeedCheck", res.isNeedCheck());
					model.put("time", DateUtil.format(DateUtil.addDays(res.getTime(),1)));
				} else {
					//redirectAttributes.addAttribute("resCode", res.getResCode());
					//redirectAttributes.addAttribute("resMsg", res.getResMsg());
					model.put("time", DateUtil.format(DateUtil.addDays(new Date(),1)));
					model.put("resCode", res.getResCode());
					model.put("resMsg", res.getResMsg());
				}
			}
		} catch (Exception e) {
			//redirectAttributes.addAttribute("resCode", "999");
			//redirectAttributes.addAttribute("resMsg", e.getMessage());
			model.put("resCode", "999");
			model.put("resMsg", e.getMessage());
			e.printStackTrace();
		}
		
		//redirectAttributes.addAttribute("channel", channel);
		model.put("channel", channel);
		return model;
	}
	/**
	 * 提现结果页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/withdraw/withdraw_result_page")
	public String withdrawResultPage(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String resCode = request.getParameter("resCode");
        String resMsg = request.getParameter("resMsg");
        String channel = request.getParameter("channel");
        String errorMsg = request.getParameter("errorMsg");
        String qianbao = request.getParameter("qianbao");
        String time = request.getParameter("time");
        String isNeedCheck = request.getParameter("isNeedCheck");

		String link = GlobEnv.getWebURL("/micro2.0/index");
		WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);

        String from = request.getParameter("from");
        String backUrl = request.getParameter("backUrl");
        
        log.info("提现结果 resCode="+resCode);
        log.info("进入提现结果 from="+from + "backurl="+backUrl);
        
        model.put("time", StringUtil.isBlank(time) ? null :DateUtil.parseDateTime(time));
        model.put("isNeedCheck", isNeedCheck);
        model.put("resCode", resCode);
		model.put("resMsg", resMsg);
		if(StringUtil.isNotBlank(qianbao)
					&& Constants.USER_AGENT_QIANBAO.equals(qianbao)){
			model.put("qianbao", Constants.USER_AGENT_QIANBAO);
		}
        if("000".equals(resCode)) {
        	if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
        		return channel + "/assets/withdraw_succ";
        	}
        	if(Constants.FROM_178_APP.equals(from) ) {
        		return "redirect:"+backUrl;
			}
			return channel + "/withdraw/withdraw_succ";
        }else {
        	model.put("errorMsg", StringUtil.isBlank(errorMsg) ? resMsg : errorMsg);
        	if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
        		return channel + "/assets/withdraw_error";
        	}
        	if(Constants.FROM_178_APP.equals(from) ) {
				model.put("from", from);
				model.put("backUrl", backUrl);
        		return "qianbao178/app/withdraw/withdraw_error";
			}
        	
        	return "micro2.0/withdraw/withdraw_error";
        }
	}

	/**
	 * 校验日限额
	 * @param request
	 * @return
     */
	@ResponseBody
	@RequestMapping("{channel}//withdraw/checkDayLimit")
	public Map<String, Object> checkDayLimit(HttpServletRequest request, @PathVariable String channel) {
		Map<String, Object> result = new HashMap<>();
		Double amount = Double.parseDouble(request.getParameter("amount"));
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);

		ReqMsg_UserBalance_CheckDayLimit req = new ReqMsg_UserBalance_CheckDayLimit();
		req.setUserId(Integer.parseInt(userId));
		req.setAmount(amount);
		ResMsg_UserBalance_CheckDayLimit res = (ResMsg_UserBalance_CheckDayLimit) siteService.handleMsg(req);
		result.put("moreThanDayLimit", res.isMoreThanDayLimit());
		result.put("leftAmount", res.getLeftAmount()<0?0:res.getLeftAmount());
		result.put("resCode", "000000");
		return result;
	}

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
				model.put("qianbao", qianbao);
				CookieManager manager = new CookieManager(request);
	            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
		        	link += "&agentViewFlag="+viewAgentFlagCookie;
		        }
			}
			// 分享
			Map<String, String> sign = weChatUtil.createAuth(request);
			sign.put("link", link);
			model.put("weichat", sign);
		}
	}
}
