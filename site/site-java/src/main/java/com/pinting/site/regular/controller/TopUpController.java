package com.pinting.site.regular.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.util.WeChatShareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.Token;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.Util;

/**
 * micro2.0 充值
 * 
 * @author bianyatian
 * 
 */
@Controller
public class TopUpController {
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
	private CommunicateBusiness regularService;
	@Autowired
	private WeChatUtil weChatUtil;

	/**
	 * 进入”我的余额“中的充值，判断是否已经绑卡
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/micro2.0/topUp/top_up")
	public String topUpIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		Util.createToken(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN);
		// 1、数据准备
		// 2、分享
		// 3、查询绑定银行
		// 4、获取账户余额
		// 5、判断是否来自钱报APP

		// 1、数据准备
		CookieManager cookieManager = new CookieManager(request);
		String userId = new CookieManager(request).getValue(
				CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		String from = request.getParameter("from")==null ? (String)request.getAttribute("from") : request.getParameter("from");
		String qianbao = request.getParameter("qianbao");
		// 存管引导信息（未存管绑卡或激活）
		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
		depGuideReq.setUserId(Integer.parseInt(userId));
		depGuideReq.setContainRisk(true);
		ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
		if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
				|| Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
			// 未存管绑卡或激活
			if(Constants.FROM_178_APP.equals(from)) {
				// 购买产品ID
				String id = StringUtil.isNotBlank(request.getParameter("id")) ? request.getParameter("id") : "";
				String buyAmount = StringUtil.isNotBlank(request.getParameter("buyAmount")) ? request.getParameter("buyAmount") : "";

				model.put("id", id);
				model.put("source", request.getParameter("source"));
				model.put("from", from);
				model.put("buyAmount", buyAmount);
				String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(),
						CookieEnums._SITE_178_BACK_URL.getName(), true);
				model.put("backUrl", backUrl);
				request.setAttribute("from", null);
				request.setAttribute("channel", null);
				request.setAttribute("qianbao", null);
				return "redirect:/micro2.0/bankcard/index?entry=top_up&from="+from+"&productId="+ id +"&amount=" + buyAmount;
			}
			if(Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
				return "redirect:/micro2.0/bankcard/index?entry=top_up&qianbao=qianbao";
			}
			return "redirect:/micro2.0/bankcard/index?entry=top_up";
		} else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
			return "redirect:/micro2.0/bankcard/activate/index";
		}
		if(Constants.is_expire.equals(depGuideRes.getRiskStatus())
				|| Constants.is_no.equals(depGuideRes.getRiskStatus())) {
			// 需要进行风险测评
			if(Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
				return "redirect:/micro2.0/assets/questionnaireMore?entry=top_up&qianbao=qianbao";
			} else {				
				return "redirect:/micro2.0/assets/questionnaireMore?entry=top_up";
			}
		}

		// 2、分享
		String link = GlobEnv.getWebURL("/micro2.0/index");
		// 钱报的跳转参数
		if(Constants.FROM_178_APP.equals(from) ) {
			qianbao = Constants.USER_AGENT_QIANBAO;
		}
		if (StringUtil.isNotBlank(qianbao)
				&& Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
			model.put("qianbao", Constants.USER_AGENT_QIANBAO);
			link += "?qianbao=qianbao";
			CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
		}
		Map<String, String> sign = weChatUtil.createAuth(request);
		sign.put("link", link);
		model.put("weichat", sign);

		// 3、查询绑定银行
		ReqMsg_Bank_bindBankList reqMsgBankList = new ReqMsg_Bank_bindBankList();
		reqMsgBankList.setUserId(Integer.parseInt(userId));
		ResMsg_Bank_bindBankList resMsgBankList = (ResMsg_Bank_bindBankList) regularService
				.handleMsg(reqMsgBankList);
		if (resMsgBankList.isBindBank()) {
			// 已绑卡实名
			for (int i = 0; i < resMsgBankList.getBankList().size(); i++) {
				Integer isFirst = (Integer) resMsgBankList.getBankList().get(i)
						.get("isFirst");
				if (isFirst == 1) {
					model.put("bankList", resMsgBankList.getBankList().get(i));
					model.put("bankId", resMsgBankList.getBankList().get(i)
							.get("bankId"));
					model.put("cardNo", resMsgBankList.getBankList().get(i)
							.get("cardNo"));
					model.put("oneTop", resMsgBankList.getBankList().get(i)
							.get("oneTop"));
					model.put("dayTop", resMsgBankList.getBankList().get(i)
							.get("dayTop"));
					model.put("bankName", resMsgBankList.getBankList().get(i)
							.get("bankName"));
					model.put("isFirst", resMsgBankList.getBankList().get(i)
							.get("isFirst"));
					model.put("isAvailable", resMsgBankList.getBankList()
							.get(i).get("isAvailable"));
					model.put("dailyNotice", resMsgBankList.getBankList()
                            .get(i).get("dailyNotice"));
					model.put("bankBigLogo", resMsgBankList.getBankList()
							.get(i).get("largeLogo"));
				}
			}
			model.put("rechangeLimit", resMsgBankList.getRechangeLimit());

			// 4、获取账户余额
			
	        ReqMsg_User_UserBalanceQuery reqMsg = new ReqMsg_User_UserBalanceQuery();
	        reqMsg.setUserId(userId);
	        ResMsg_User_UserBalanceQuery resMsg = (ResMsg_User_UserBalanceQuery) regularService.handleMsg(reqMsg);
	        model.put("balance", resMsg.getAvailableBalance());
			model.put("depBalance", resMsg.getDepBalance() == null ? 0 : resMsg.getDepBalance());
			// 5、判断是否来自钱报APP
			if(Constants.FROM_178_APP.equals(from) ) {
				// 购买产品ID
				model.put("id", request.getParameter("id"));
				model.put("source", request.getParameter("source"));
				model.put("from", from);
				model.put("buyAmount", request.getParameter("buyAmount"));
				String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(),
						CookieEnums._SITE_178_BACK_URL.getName(), true);
				model.put("backUrl", backUrl);
				request.setAttribute("from", null);
				request.setAttribute("channel", null);
				request.setAttribute("qianbao", null);
				return "/qianbao178/app/topUp/buy_product_bind";
			}
			return "/micro2.0/topUp/buy_product_bind";
		} else {
			// 未绑卡实名
			model.put("rechangeLimit", resMsgBankList.getRechangeLimit());
			if(Constants.FROM_178_APP.equals(from)) {
				// 购买产品ID
				String id = StringUtil.isNotBlank(request.getParameter("id")) ? request.getParameter("id") : "";
				String buyAmount = StringUtil.isNotBlank(request.getParameter("buyAmount")) ? request.getParameter("buyAmount") : "";

				model.put("id", id);
				model.put("source", request.getParameter("source"));
				model.put("from", from);
				model.put("buyAmount", buyAmount);
				String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(),
						CookieEnums._SITE_178_BACK_URL.getName(), true);
				model.put("backUrl", backUrl);
				request.setAttribute("from", null);
				request.setAttribute("channel", null);
				request.setAttribute("qianbao", null);
				return "redirect:/micro2.0/bankcard/index?entry=top_up&from="+from+"&productId="+ id +"&amount=" + buyAmount;
			}
			return "redirect:/micro2.0/bankcard/index?entry=top_up";
		}
	}

	/**
	 * 【账户余额】充值（添加银行卡）
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Deprecated
	@Token(save = true)
	@RequestMapping("{channel}/topUp/top_up_add_card")
	public String topUpAddCard(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		// 查询19付支持快捷支付的银行
		ReqMsg_Bank_Pay19BankList reqMsg = new ReqMsg_Bank_Pay19BankList();
		reqMsg.setUserId(Integer.parseInt(userId));
		ResMsg_Bank_Pay19BankList resMsg = (ResMsg_Bank_Pay19BankList) regularService
				.handleMsg(reqMsg);
		model.put("bankList", resMsg.getBankList());
		model.put("rechangeLimit", request.getParameter("rechangeLimit"));

		String link = GlobEnv.getWebURL("/micro2.0/index");
		// 钱报的参数
		String qianbao = request.getParameter("qianbao");
		if (StringUtil.isNotBlank(qianbao)
				&& Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
			model.put("qianbao", Constants.USER_AGENT_QIANBAO);
			link += "?qianbao=qianbao";
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
		}
		// 分享
		Map<String, String> sign = weChatUtil.createAuth(request);
		sign.put("link", link);
		model.put("weichat", sign);

		return channel + "/topUp/top_up_add_card";
	}

	/**
	 * 【账户余额】充值（更换银行卡）
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping("{channel}/topUp/top_up_change_card")
	public String topUpNext(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String userId = new CookieManager(request).getValue(
				CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_Bank_UserBankInfo reqMsg = new ReqMsg_Bank_UserBankInfo();
		reqMsg.setUserId(Integer.parseInt(userId));
		ResMsg_Bank_UserBankInfo resMsg = (ResMsg_Bank_UserBankInfo) regularService
				.handleMsg(reqMsg);
		model.put("userName", resMsg.getUserName());
		model.put("bankList", resMsg.getBankList());
		model.put("rechangeLimit", request.getParameter("rechangeLimit"));
		return channel + "/topUp/top_up_change_card";
	}

	/**
	 * 【账户余额】充值（结果页）
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/assets/top_up_result")
	public String topUpResult(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		return channel + "/assets/top_up_result";
	}

	/**
	 * 充值发送验证短信前验证： 充值金额，大于1且小数点后小于两位； 姓名必填，身份证验证
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("{channel}/topUp/checkTopUp")
	@Deprecated
	public Map<String, Object> checkTopUp(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		String rechangeLimit = request.getParameter("rechangeLimit");
		return result;

	}

	/**
	 * 
	 * @Title: order
	 * @Description: 未绑定银行卡用户正式下单
	 * @param channel
	 * @param request
	 * @param response
	 * @param reqMsg
	 * @return
	 * @throws
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping("micro2.0/topUp/order")
	public Map<String, Object> order(HttpServletRequest request,
			HttpServletResponse response, ReqMsg_RegularBuy_Order reqMsg,
			Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 重复提交
		if (Util.isRepeatSubmit(request, response)) {
			result.put("errorCode", "999");
			result.put("errorMsg", "充值失败：重复提交的请求！");
			return result;
		}

		String userId = new CookieManager(request).getValue(
				CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setPlaceOrder(2);
		reqMsg.setTerminalType(1);
		ResMsg_RegularBuy_Order resMsg = (ResMsg_RegularBuy_Order) regularService
				.handleMsg(reqMsg);

		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
			result.put("errorCode", "000");
			result.put("errorMsg", "充值成功");
			return result;
		} else {
			result.put("errorCode", "999");
			result.put("errorMsg", "充值失败：" + resMsg.getResMsg());
			return result;
		}
	}

	/**
	 * 
	 * @Title: order
	 * @Description: 绑定银行卡用户正式下单
	 * @param request
	 * @param response
	 * @param reqMsg
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("micro2.0/topUp/regularOrder")
	public Map<String, Object> regularOrder(HttpServletRequest request,
			HttpServletResponse response, ReqMsg_RegularBuy_Order reqMsg,
			Map<String, Object> model) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 重复提交
		if (Util.isRepeatSubmit(request, response)) {
			result.put("errorCode", "999");
			result.put("errorMsg", "充值失败：重复提交的请求！");
			return result;
		}
		String userId = new CookieManager(request).getValue(
				CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_Bank_QueryBankInfoByUser req = new ReqMsg_Bank_QueryBankInfoByUser();
		req.setUserId(Integer.parseInt(userId));
		req.setBankId(reqMsg.getBankId());
		ResMsg_Bank_QueryBankInfoByUser res = (ResMsg_Bank_QueryBankInfoByUser) regularService
				.handleMsg(req);

		String token = Util.createToken(request, response);
		result.put("token", token);
		if (StringUtil.isNotBlank(res.getIdCard())) {
			reqMsg.setBankName(res.getBankName());
			reqMsg.setCardNo(res.getCardNo());
			reqMsg.setIdCard(res.getIdCard());
			reqMsg.setMobile(res.getMobile());
			reqMsg.setUserId(Integer.parseInt(userId));
			reqMsg.setUserName(res.getUserName());
			reqMsg.setPlaceOrder(2);
			reqMsg.setTerminalType(1);
			ResMsg_RegularBuy_Order resMsg = (ResMsg_RegularBuy_Order) regularService
					.handleMsg(reqMsg);
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
				result.put("errorCode", "000");
				result.put("errorMsg", "充值成功");
				return result;
			} else {
				result.put("errorCode", "999");
				result.put("errorMsg", "充值失败：" + resMsg.getResMsg());
				return result;
			}
		} else {
			result.put("errorCode", "999");
			result.put("errorMsg", "充值失败：用户不存在或银行卡已失效");
			return result;
		}
	}
	/**
	 * 
	 * @Title: gotoResultPage 
	 * @Description: 正式下单之后跳转到结果页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
	@RequestMapping("micro2.0/topUp/gotoResultPage")
	public String gotoResultPage(HttpServletRequest request,HttpServletResponse response,Map<String, Object> model) {
	    String link = GlobEnv.getWebURL("/micro2.0/regular/list");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
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
	    String code = request.getParameter("code");
		String msg = request.getParameter("msg");
		if("000".equals(code)) {
			return "micro2.0/topUp/buy_product_success";
		}
		else {
			model.put("errorMsg", msg);
			return "micro2.0/topUp/buy_product_error";
		}
	}

}
