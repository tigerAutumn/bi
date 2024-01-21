package com.pinting.site.invite.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.Token;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.MatrixToImageWriter;

@Controller
@Scope("prototype")
public class InviteController extends BaseController {
	
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
	private WeChatUtil weChatUtil;

	/**
	 * 打开邀请好友界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@Token(save = true)
	@RequestMapping("{channel}/assets/inviteFriends")
	public String inviteFriends(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_Invest_InvestListQuery reqMsg,HashMap<String,Object> model) {
		
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	    String recommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(userId));
	    model.put("recommend", recommend);
	    String user = userId + UUID.randomUUID().toString();
	    Map<String,String> sign = weChatUtil.createAuth(request);
        String link = GlobEnv.getWebURL("/micro2.0/user/register_index_share?user="+user);
        sign.put("link", link);
		
		String webPath = request.getSession().getServletContext().getRealPath("/");
        String logoPath = webPath + "/resources/micro2.0/images/qrcode_logo.png";
        
        MatrixToImageWriter.createMatrixImage(link, userId,logoPath,true);
		String matrix = GlobEnv.get("wxQRcode.url.prefix") ;
		
		if(matrix.charAt(matrix.length() - 1) != '/'){
			matrix = matrix + "/";
		}
		
		matrix += userId + ".png";
		
		String shareLink = GlobEnv.get("gen.server.web")+"/gen2.0/index?user="+user;
		model.put("matrix", matrix);
		model.put("shareLink", shareLink);
		
		ReqMsg_Bonus_RecommendBonusListQuery reqBonus = new ReqMsg_Bonus_RecommendBonusListQuery();
		reqBonus.setUserId(Integer.valueOf(userId));
		reqBonus.setPageIndex(0);
		reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		reqBonus.setWithdrawFlag("1");
		// 查询用户表奖励数据
		ResMsg_Bonus_RecommendBonusListQuery resp = (ResMsg_Bonus_RecommendBonusListQuery) siteService
				.handleMsg(reqBonus);

		// 我的奖励分页参数
		// 当前页
		model.put("pageIndex", 1);
		// 总页数
		model.put("totalPages", resp.getTotalCount());
		model.put("totalCount", resp.getTotalCount());
		model.put("bonus", resp.getBonus());
		model.put("bonusList", resp.getBonuss());
		model.put("haveSpecial", resp.getHaveSpecial());
		model.put("specialAmout",resp.getSpecialBonusAmount());

		ReqMsg_Activity_BaseData recommendReq  = new ReqMsg_Activity_BaseData();
		recommendReq.setActivityType(Constants.RECOMMEND_ACTIVITY_TYPE);
		ResMsg_Activity_BaseData recommendRes = (ResMsg_Activity_BaseData) siteService.handleMsg(recommendReq);
		model.put("newRules", Constants.ACTIVITY_IS_NOT_START.equals(recommendRes.getIsStart()) ? Constants.is_no : Constants.is_yes);
		model.put("startTime", recommendRes.getStartTime());
		model.put("endTime", recommendRes.getEndTime());
		model.put("rate", recommendRes.getExtendInfo());

		ReqMsg_User_BsSubUserListQuery reqUser = new ReqMsg_User_BsSubUserListQuery();
        int pageIndex = request.getParameter("pageIndex") == null ? 0 : Integer.valueOf(request.getParameter("pageIndex"));
		reqUser.setUserId(Integer.valueOf(userId));
		reqUser.setPageIndex(pageIndex);
		reqUser.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
        ResMsg_User_BsSubUserListQuery respUser = (ResMsg_User_BsSubUserListQuery) siteService
                .handleMsg(reqUser);
		model.put("bsUserList", respUser.getBsUserList());

		// 我的推荐分页参数
		model.put("recommendPageIndex", 1);
		model.put("recommendTotalCount", respUser.getTotalCount());
		model.put("recommendTotalPages", respUser.getTotalCount());
		
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

		ReqMsg_Bonus_WithdrawPageInfo withdrawReq = new ReqMsg_Bonus_WithdrawPageInfo();
		withdrawReq.setUserId(Integer.parseInt(userId));
		ResMsg_Bonus_WithdrawPageInfo withdrawRes = (ResMsg_Bonus_WithdrawPageInfo) siteService.handleMsg(withdrawReq);
		String cardNo = withdrawRes.getCardNo();
		model.put("bankLogo", withdrawRes.getSmallLogo());
		model.put("bankName", withdrawRes.getBankName());
		model.put("cardId", withdrawRes.getCardId());
		if (StringUtil.isNotBlank(cardNo)) {
			model.put("cardNo", cardNo.substring(cardNo.length() - 4, cardNo.length()));
			model.put("bankId", withdrawRes.getBankId());
		}
		model.put("canWithdraw", withdrawRes.getCan_withdraw());
		model.put("withdrawLimit", withdrawRes.getWithdrawLimit());
		return channel + "/invite/invite_friends";
	}
	
	/**
	 * 我的奖励选项卡点击加载数据
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/assets/inviteFriends/myBonus/index")
	public String myBonusIndex(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,HashMap<String,Object> model) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		
		ReqMsg_Bonus_RecommendBonusListQuery reqBonus = new ReqMsg_Bonus_RecommendBonusListQuery();
		reqBonus.setUserId(Integer.valueOf(userId));
		reqBonus.setPageIndex(0);
		reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		reqBonus.setWithdrawFlag("1");
		// 查询用户表奖励数据
		ResMsg_Bonus_RecommendBonusListQuery resp = (ResMsg_Bonus_RecommendBonusListQuery) siteService
				.handleMsg(reqBonus);
		model.put("pageIndex", 0);
		model.put("totalCount", resp.getTotalCount());
		model.put("bonus", resp.getBonus());
		model.put("bonusList", resp.getBonuss());
		model.put("haveSpecial", resp.getHaveSpecial());
		model.put("specialAmout",resp.getSpecialBonusAmount());
		return channel + "/invite/bouns_index";
	}
	
	/**
	 * 我的奖励金加载数据
	 * @param channel
	 * @param pageIndex 当前页
	 * @param status 状态标志 奖励金分页/推荐人分页
	 * @param request
	 * @param response
     * @param model
     * @return
     */
	@RequestMapping("{channel}/assets/inviteFriends/myBonus/loadDatas")
	public String bonusLoadDatas(@PathVariable String channel, Integer pageIndex,
								 String status, HttpServletRequest request,
								 HttpServletResponse response,HashMap<String,Object> model) {
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			ReqMsg_Bonus_BonusListQuery reqBonus = new ReqMsg_Bonus_BonusListQuery();
			reqBonus.setUserId(Integer.valueOf(userId));
			reqBonus.setPageIndex(pageIndex-1);
			reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			reqBonus.setWithdrawFlag("1");
			reqBonus.setStatus(status);

			// 查询用户表奖励数据
			ResMsg_Bonus_BonusListQuery resp = (ResMsg_Bonus_BonusListQuery) siteService
					.handleMsg(reqBonus);
			model.put("pageIndex", pageIndex);
			model.put("totalCount", resp.getTotalCount());
			model.put("bonusList", resp.getBonuss());
			model.put("totalPages", resp.getTotalCount());
			model.put("reqBonus", reqBonus);

		}catch(Exception e) {
			e.printStackTrace();
		}
		return channel + "/invite/bouns_list";
	}
	
	/**
	 * 我的推荐加载数据
	 * @param channel
	 * @param pageIndex 当前页
	 * @param status 状态标志 奖励金分页/推荐人分页
	 * @param request
	 * @param response
	 * @param model
     * @param req
     * @return
     */
	@RequestMapping("{channel}/assets/inviteFriends/myRecommend/loadDatas")
	public String recommendLoadDatas(@PathVariable String channel, Integer pageIndex,
									 String status, HttpServletRequest request,
									 HttpServletResponse response, HashMap<String,Object> model,
									 ReqMsg_User_BsSubUserListQuery req) {
		try {
			CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            req.setUserId(Integer.valueOf(userId));
            req.setPageIndex(pageIndex-1);
            req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			req.setStatus(status);
            ResMsg_User_BsSubUserListQuery resp = (ResMsg_User_BsSubUserListQuery) siteService
                    .handleMsg(req);
            model.put("recommendPageIndex", pageIndex);
            model.put("recommendTotalCount", resp.getTotalCount());
            model.put("bsUserList", resp.getBsUserList());
			model.put("req", req);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return channel + "/invite/recommend_list";
	}
}
