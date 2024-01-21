/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.activity.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.Util;
import com.pinting.util.WeChatShareUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: Activity178Controller.java, v 0.1 2015-12-11 下午3:10:33 HuanXiong Exp $
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Activity178Controller {

	private Logger log = LoggerFactory.getLogger(Activity178Controller.class);
	@Autowired
	private CommunicateBusiness communicateBusiness;
	@Autowired
    private WeChatUtil weChatUtil;


	//========================= 钱报节水活动 开始 ================================

	@RequestMapping("/micro2.0/178/activity/water")
	public String water(HttpServletRequest request, Map<String, Object> modal) {
		// 钱报节水活动分享文案、图片
		String link = GlobEnv.getWebURL("/micro2.0/178/activity/water");
		String shareTitle = "晒水费单赢节能电器！钱江晚报节水公益送大礼！";
		String shareContent = "晒水费单赢节能电器！钱江晚报节水公益送大礼！";
		String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/water.jpg");
		WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, modal, weChatUtil);

		CookieManager cookieManager = new CookieManager(request);
		String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		Integer userId = !StringUtils.isEmpty(userIdStr) ? Integer.parseInt(userIdStr) : null;
		ReqMsg_AgentActivity_SignUpPageInfo req = new ReqMsg_AgentActivity_SignUpPageInfo();
		req.setUserId(userId);
		ResMsg_AgentActivity_SignUpPageInfo res = (ResMsg_AgentActivity_SignUpPageInfo) communicateBusiness.handleMsg(req);
		modal.put("result", res);
		modal.put("qianbao", "qianbao");
		return "/micro2.0/activity/water/index";
	}

	@RequestMapping("/micro2.0/178/activity/water/vote_index")
	public String vote_index(HttpServletRequest request, Map<String, Object> modal) {
		// 钱报节水活动分享文案、图片
		String link = GlobEnv.getWebURL("/micro2.0/178/activity/water");
		String shareTitle = "晒水费单赢节能电器！钱江晚报节水公益送大礼！";
		String shareContent = "晒水费单赢节能电器！钱江晚报节水公益送大礼！";
		String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/water.jpg");
		WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, modal, weChatUtil);

		CookieManager cookieManager = new CookieManager(request);
		String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		Integer userId = !StringUtils.isEmpty(userIdStr) ? Integer.parseInt(userIdStr) : null;
		ReqMsg_AgentActivity_WaterVotePageInfo req = new ReqMsg_AgentActivity_WaterVotePageInfo();
		req.setUserId(userId);
		req.setNumPerPage(20);
		String pageNum = request.getParameter("pageNum");
		req.setPageNum(StringUtil.isBlank(pageNum) ? 1 : Integer.valueOf(pageNum));
		String no = request.getParameter("signUpNo");
		if(!StringUtil.isBlank(no)) {
			DecimalFormat format = new DecimalFormat("000");
			try {
				Number serialNo = format.parse(no);
				req.setSignUpNo(serialNo.intValue());
			} catch (ParseException e) {
				modal.put("message", "编号格式不正确");
				modal.put("qianbao", "qianbao");
				return "/micro2.0/activity/water/vote_index";
			}
		}
		ResMsg_AgentActivity_WaterVotePageInfo res = (ResMsg_AgentActivity_WaterVotePageInfo) communicateBusiness.handleMsg(req);
		if("not_start".equals(res.getIsStart()) || "end".equals(res.getIsStart())) {
			return "redirect:/micro2.0/178/activity/water";
		}
		if(!CollectionUtils.isEmpty(res.getList())) {
			for (HashMap<String, Object> map: res.getList()) {
				Integer serialNo = (Integer) map.get("serialNo");
				if(map.get("voteNum") == null) {
					map.put("voteNum", "0");
				}
				DecimalFormat format = new DecimalFormat("000");
				map.put("serialNo", format.format(serialNo).toString());
			}
		}

		modal.put("result", res);
		modal.put("qianbao", "qianbao");
		modal.put("signUpNo", no);
		modal.put("pageNum", pageNum);
		return "/micro2.0/activity/water/vote_index";
	}

	@RequestMapping("/micro2.0/178/activity/water/vote/list")
	public String voteList(HttpServletRequest request, Map<String, Object> modal) {
		ReqMsg_AgentActivity_WaterVotePageInfo req = new ReqMsg_AgentActivity_WaterVotePageInfo();
		req.setNumPerPage(20);
		String pageNum = request.getParameter("pageNum");
		req.setPageNum(StringUtil.isBlank(pageNum) ? 1 : Integer.valueOf(pageNum));
		String no = request.getParameter("signUpNo");
		if(!StringUtil.isBlank(no)) {
			DecimalFormat format = new DecimalFormat("000");
			try {
				Number serialNo = format.parse(no);
				req.setSignUpNo(serialNo.intValue());
			} catch (ParseException e) {
				modal.put("message", "编号格式不正确");
				modal.put("qianbao", "qianbao");
				return "/micro2.0/activity/water/vote_list";
			}
		}
		ResMsg_AgentActivity_WaterVotePageInfo res = (ResMsg_AgentActivity_WaterVotePageInfo) communicateBusiness.handleMsg(req);
		if(!CollectionUtils.isEmpty(res.getList())) {
			for (HashMap<String, Object> map: res.getList()) {
				Integer serialNo = (Integer) map.get("serialNo");
				if(map.get("voteNum") == null) {
					map.put("voteNum", "0");
				}
				DecimalFormat format = new DecimalFormat("000");
				map.put("serialNo", format.format(serialNo).toString());
			}
		}

		modal.put("result", res);
		modal.put("pageNum", pageNum);
		return "/micro2.0/activity/water/vote_list";
	}

	@ResponseBody
	@RequestMapping("/micro2.0/178/activity/water/vote")
	public Map<String, Object> vote(HttpServletRequest request, ReqMsg_AgentActivity_WaterVote req) {
		Map<String, Object> result = new HashMap<>();
		CookieManager cookieManager = new CookieManager(request);
		String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		Integer userId = !StringUtils.isEmpty(userIdStr) ? Integer.parseInt(userIdStr) : null;
		req.setVoteUserId(userId);
		ResMsg_AgentActivity_WaterVote res = (ResMsg_AgentActivity_WaterVote) communicateBusiness.handleMsg(req);
		result.put("code", res.getResCode());
		result.put("message", res.getResMsg());
		result.put("result", res);
		return result;
	}

	@RequestMapping("/micro2.0/178/activity/water/sign_up_index")
	public String sign_up_index(HttpServletRequest request, Map<String, Object> modal) {
		// 钱报节水活动分享文案、图片
		String link = GlobEnv.getWebURL("/micro2.0/178/activity/water");
		String shareTitle = "晒水费单赢节能电器！钱江晚报节水公益送大礼！";
		String shareContent = "晒水费单赢节能电器！钱江晚报节水公益送大礼！";
		String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/water.jpg");
		WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, modal, weChatUtil);

		CookieManager cookieManager = new CookieManager(request);
		String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		Integer userId = !StringUtils.isEmpty(userIdStr) ? Integer.parseInt(userIdStr) : null;
		ReqMsg_AgentActivity_SignUpPageInfo req = new ReqMsg_AgentActivity_SignUpPageInfo();
		req.setUserId(userId);
		ResMsg_AgentActivity_SignUpPageInfo res = (ResMsg_AgentActivity_SignUpPageInfo) communicateBusiness.handleMsg(req);
		if("no".equals(res.getIsLogin()) || "not_start".equals(res.getIsStart())
				|| "end".equals(res.getIsStart()) || "INIT".equals(res.getIsJoined()) || "PASS".equals(res.getIsJoined())) {
			return "redirect:/micro2.0/178/activity/water";
		}

		modal.put("qianbao", "qianbao");
		return "/micro2.0/activity/water/sign_up";
	}


	@ResponseBody
	@RequestMapping("/micro2.0/178/activity/water/sign_up")
	public Map<String, Object> upload(HttpServletRequest request, ReqMsg_AgentActivity_WaterSignUp req) {

		Map<String, Object> modal = new HashMap<>();
		String waterSaveServerId = request.getParameter("waterSaveServerId");
		String waterRateServerId = request.getParameter("waterRateServerId");

		log.info("节水照片：{}", waterSaveServerId);
		log.info("水费单照片：{}", waterRateServerId);
		// 校验数据
		if((StringUtil.isBlank(req.getContent()) && StringUtil.isBlank(waterSaveServerId))
				|| StringUtil.isBlank(req.getUserName()) || StringUtil.isBlank(req.getMobile())
				|| null == req.getFamilyNum() || null == req.getMonthWaterRate() || StringUtil.isBlank(waterRateServerId)) {
			modal.put("code", "999999");
			modal.put("message", "请完整填写所有信息后再提交~");
			return modal;
		}

		if(containsEmoji(req.getUserName())) {
			modal.put("code", "999999");
			modal.put("message", "姓名格式不正确~");
			return modal;
		}
		if(!StringUtil.isBlank(req.getContent()) && StringUtil.isBlank(waterSaveServerId)) {
			if(req.getContent().length() < 20) {
				modal.put("code", "999999");
				modal.put("message", "节水小妙招字数太少了哟");
				return modal;
			}
		}
		if(containsEmoji(req.getContent())) {
			modal.put("code", "999999");
			modal.put("message", "节水小妙招格式不正确~");
			return modal;
		}
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		String waterSaveFileName = userId + "_" + waterSaveServerId + "_" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
		String waterSaveFile = null;
		// 存库动作
		String waterRateFileName = userId + "_" + waterRateServerId + "_" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
		String waterRateFile = null;
		try {
			String baseUrl = GlobEnv.get("activity.file.base.path");
			if(!StringUtil.isBlank(waterSaveServerId)) {
				waterSaveFile = weChatUtil.saveImageToDisk(waterSaveServerId, waterSaveFileName, baseUrl);
			}
			if(!StringUtil.isBlank(waterRateServerId)) {
				waterRateFile = weChatUtil.saveImageToDisk(waterRateServerId, waterRateFileName, baseUrl);
			}
			log.info("节水照片存储路径：{}", waterSaveFile);
			log.info("水费单照片存储路径：{}", waterRateFile);
		} catch (Exception e) {
			log.error("下载用户 {} 的节水图片 {} 失败：{}", userId, waterSaveServerId, e.getMessage());
			log.error("下载用户 {} 的水费单图片 {} 失败：{}", userId, waterRateServerId, e.getMessage());
			e.printStackTrace();
			modal.put("core", "999998");
			modal.put("message", "图片上传失败，请重新上传或上传其他格式图片~");
			return modal;
		}
		String relativeUrl = GlobEnv.get("activity.web.relative.path");
		req.setWaterSavePhoto(StringUtil.isBlank(waterSaveFile) ? null : relativeUrl + File.separator + waterSaveFile);
		req.setWaterRatePhoto(StringUtil.isBlank(waterRateFile) ? null : relativeUrl + File.separator + waterRateFile);
		req.setUserId(StringUtil.isBlank(userId) ? null : Integer.parseInt(userId));

		ResMsg_AgentActivity_WaterSignUp res = (ResMsg_AgentActivity_WaterSignUp) communicateBusiness.handleMsg(req);

		modal.put("code", res.getResCode());
		modal.put("result", res);
		return modal;
	}
	//========================= 钱报节水活动 结束 ================================








	
    @RequestMapping("/gen178/activity")
    public String activity() {
        return "gen178/activity/activity";
    }
    
    //========================= 钱报全民抽奖活动(2017-1-16 - 2017-1-27) S================================
    
    /**
     * 全民抽奖-引导注册页 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/micro2.0/activity/national_lottery_pre")
    public String nationalLotteryPre(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	//判断是否是钱报，不是则跳转至币港湾首页
    	String qianbao = request.getParameter("qianbao");
    	//判断是否是报系用户
        String agentIdStr =  request.getParameter("agentId");
        if(StringUtils.isNotBlank(agentIdStr)){
        	ReqMsg_User_CheckIsQianbaos req = new ReqMsg_User_CheckIsQianbaos();
    		req.setAgentId(Integer.parseInt(agentIdStr));
    		ResMsg_User_CheckIsQianbaos res = (ResMsg_User_CheckIsQianbaos)communicateBusiness.handleMsg(req);
    		if(!res.isQianbaos()){
				//不是报系
    			agentIdStr = "";
			}
        }
    	if("qianbao".equals(qianbao) && StringUtils.isNotBlank(agentIdStr)){
    		//分享文案、图片
        	String link = GlobEnv.getWebURL("micro2.0/activity/national_lottery_pre?qianbao=qianbao&agentId="+agentIdStr);
            String shareTitle = "2017，好礼相随！大转盘喊你来抽奖！妥妥送红包啦~";
            String shareContent = "100%中奖哦~";
            String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/national_lottery.jpg");
            WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
            
            //agentid 存cookie
            CookieManager cookieManager = new CookieManager(request);
            cookieManager.setValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_AGENT_ID.getName(),
            		agentIdStr, true);
            cookieManager.save(response, CookieEnums._ACTIVITY.getName(), true);
            
            //查询获奖用户列表
            ReqMsg_ActivityLuckyDraw_LuckyUserList req = new ReqMsg_ActivityLuckyDraw_LuckyUserList();
            req.setActivityId(Constants.ACTIVITY_178_20170123); //活动编号
            req.setLuckyLimitNum(20); //查询获奖用户数量
            ResMsg_ActivityLuckyDraw_LuckyUserList res = (ResMsg_ActivityLuckyDraw_LuckyUserList)communicateBusiness.handleMsg(req);
        	
            model.put("userLuckyList", res.getLuckyList());
            
            //查询活动时间
            ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
            reqTime.setActivityId(Constants.ACTIVITY_178_20170123);
            reqTime.setFormat("yyyy-MM-dd HH:mm:ss");
            
            ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
            model.put("isStart",resTime.getIsStart());
            
            model.put("agentId", agentIdStr);
            return "qianbao178/activity/national_lottery_pre";
    	}else{
    		 return "redirect:/micro2.0/index";
    	}
    	
    	
    }
    
	/**
	 * 已注册：校验手机验证码，校验是否是报系用户
	 * 未注册：校验手机验证码并注册，发送短信
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/micro2.0/activity/national_lottery_check")
	public @ResponseBody HashMap<String, Object> validateMobilePreCodeWordSubmit(
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();

		//1 获取手机号、验证码
    	String mobile = request.getParameter("mobile"); 
    	String mobileCode = request.getParameter("mobileCode"); 
    	CookieManager cookieManager = new CookieManager(request);
    	String agentId = cookieManager.getValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_AGENT_ID.getName(), true);
		//2.1 有手机号参数，判断是否已经是用户
		//根据手机查询用户是否存在
    	ReqMsg_User_FindUserByMobile reqMsg_User_FindUserByMobile = new ReqMsg_User_FindUserByMobile();
    	reqMsg_User_FindUserByMobile.setMobile(mobile);
    	ResMsg_User_FindUserByMobile resMsg_User_FindUserByMobile = (ResMsg_User_FindUserByMobile) communicateBusiness.handleMsg(reqMsg_User_FindUserByMobile);
		Integer userId = resMsg_User_FindUserByMobile.getId();
		
		
		if(userId != null){
			//2.2 是用户，校验短信验证码，直接存储cookie，跳转
			ReqMsg_SMS_Validation smsReq = new ReqMsg_SMS_Validation();
			smsReq.setMobile(mobile);
			smsReq.setMobileCode(mobileCode);
			ResMsg_SMS_Validation resp = (ResMsg_SMS_Validation) communicateBusiness.handleMsg(smsReq);
			try {
				if (resp.getIsValidateSuccess()!=null&&resp.getIsValidateSuccess()) {
					
					ReqMsg_User_CheckIsQianbaos req = new ReqMsg_User_CheckIsQianbaos();
					req.setUserId(userId);
					ResMsg_User_CheckIsQianbaos res = (ResMsg_User_CheckIsQianbaos)communicateBusiness.handleMsg(req);
					if(!res.isQianbaos()){
						dataMap.put("resCode", "998");
						dataMap.put("resMsg", "抱歉，该活动为报业专享，您可以试试参与其它活动哦~");
					}else{
						dataMap.put("resCode", "000");
						cookieManager.setValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_USER_ID.getName(),
				    			String.valueOf(userId), true);
						cookieManager.save(response, CookieEnums._ACTIVITY.getName(), true);
					}
				
				} else {
					dataMap.put("resCode", "999");
					dataMap.put("resMsg", "验证不正确，请确认或者重新发送验证码");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			//2.2 不是用户，注册，发送随机密码，存储cookie，跳转
			ReqMsg_User_Regist reqRegist = new ReqMsg_User_Regist();
			if(StringUtils.isNotBlank(agentId)){
				reqRegist.setAgentId(Integer.parseInt(agentId));
			}else{
				reqRegist.setAgentId(Constants.AGENT_QIANBAO_ID);
			}
			String password = Util.get6Num();
			reqRegist.setMobile(mobile);
			reqRegist.setMobileCode(mobileCode);
			reqRegist.setRegTerminal(Constants.USER_REG_TERMINAL_H5);
			reqRegist.setQianbao("qianbao");
			reqRegist.setPassword(password);
			
			ResMsg_User_Regist resRegist = (ResMsg_User_Regist) communicateBusiness.handleMsg(reqRegist);
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resRegist.getResCode())) {
				//发送密码短信，存储cookie
				ReqMsg_User_SendPassword req = new ReqMsg_User_SendPassword();
				req.setPassword(password);
				req.setMobile(mobile);
				try {
					ResMsg_User_SendPassword res = (ResMsg_User_SendPassword)communicateBusiness.handleMsg(req);
					cookieManager.setValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_USER_ID.getName(),
	        				String.valueOf(resRegist.getUserId()), true);
					cookieManager.save(response, CookieEnums._ACTIVITY.getName(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				dataMap.put("resCode", "000");
				dataMap.put("resMsg", "验证成功！");
			}else{
				dataMap.put("resCode", "999");
				dataMap.put("resMsg", resRegist.getResMsg());
			}
		}
		
		
		return dataMap;
	}
    
    /**
     * 跳转至 全民抽奖-抽奖页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/micro2.0/activity/national_lottery_index")
    public String nationalLotteryIndex(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
    	//获取手机号、验证码
    	CookieManager cookieManager = new CookieManager(request);
    	String agentId = cookieManager.getValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_AGENT_ID.getName(), true);
    	// 1.1 无手机号参数， 判断是否已有用户id
        String userId = cookieManager.getValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            //1.2 无-说明不是从引导页进入，则返回引导页
            return "redirect:/micro2.0/activity/national_lottery_pre?qianbao=qianbao";
        } 
        ReqMsg_ActivityLuckyDraw_LuckyUserList req = new ReqMsg_ActivityLuckyDraw_LuckyUserList();
        req.setActivityId(Constants.ACTIVITY_178_20170123); //活动编号
        req.setLuckyLimitNum(20); //查询获奖用户数量
        ResMsg_ActivityLuckyDraw_LuckyUserList res = (ResMsg_ActivityLuckyDraw_LuckyUserList)communicateBusiness.handleMsg(req);
        
        //分享文案、图片
    	String link = GlobEnv.getWebURL("micro2.0/activity/national_lottery_pre?qianbao=qianbao&agentId="+agentId);
        String shareTitle = "2017，好礼相随！大转盘喊你来抽奖！妥妥送红包啦~";
        String shareContent = "100%中奖哦~";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/national_lottery.jpg");
        WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        model.put("userLuckyList", res.getLuckyList());
        model.put("agentId", agentId);
        return "qianbao178/activity/national_lottery_index";
    	
    }
    
    
    /**
	 * 全民抽奖-抽取奖励
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/micro2.0/activity/national_lottery_draw")
	public @ResponseBody HashMap<String, Object> nationalLotteryDraw(
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_USER_ID.getName(), true);
		ReqMsg_ActivityLuckyDraw_Draw20170123 req = new ReqMsg_ActivityLuckyDraw_Draw20170123();
		req.setActivityId(Constants.ACTIVITY_178_20170123);
		req.setUserId(Integer.parseInt(userId));
		ResMsg_ActivityLuckyDraw_Draw20170123 res = (ResMsg_ActivityLuckyDraw_Draw20170123)communicateBusiness.handleMsg(req);
		dataMap.put("awardId", res.getAwardId());
		dataMap.put("beforeTimes", res.getBeforeTimes());
		dataMap.put("content",res.getAwardContent());
		
		return dataMap;
	}
	
	/**
	 * 全民抽奖-根据用户id判断是否是钱报下用户（柯桥、海宁瑞安等）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/micro2.0/activity/national_lottery_checkQianbao")
	public @ResponseBody HashMap<String, Object> nationalLotteryCheckQianbaoDraw(
		HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		ReqMsg_User_CheckIsQianbaos req = new ReqMsg_User_CheckIsQianbaos();
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_USER_ID.getName(), true);
		if(StringUtils.isNotBlank(userId)){
			req.setUserId(Integer.parseInt(userId));
			ResMsg_User_CheckIsQianbaos res = (ResMsg_User_CheckIsQianbaos)communicateBusiness.handleMsg(req);
			if(!res.isQianbaos()){
				dataMap.put("resCode", "990");
			}else{
				dataMap.put("resCode", "000");
			}
		}else{
			//未获取到用户id，返回999
			dataMap.put("resCode", "999");
		}
		
		return dataMap;
	}
	
	
	/**
	 * 全民抽奖-查看已中奖信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/micro2.0/activity/national_lottery_drawed")
	public @ResponseBody HashMap<String, Object> nationalLotteryDrawed(
		HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._ACTIVITY.getName(), CookieEnums._ACTIVITY_USER_ID.getName(), true);
		if(StringUtils.isNotBlank(userId)){
			ReqMsg_ActivityLuckyDraw_Get618UserLuckyList req = new ReqMsg_ActivityLuckyDraw_Get618UserLuckyList();
			req.setActivityId(Constants.ACTIVITY_178_20170123);
			req.setUserId(Integer.parseInt(userId));
			req.setStartPage(0);
            req.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
			ResMsg_ActivityLuckyDraw_Get618UserLuckyList res = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(req);
			if(CollectionUtils.isNotEmpty(res.getLuckyList())){
				dataMap.put("draw", res.getLuckyList().get(0));
			}
			dataMap.put("resCode", "000");
		}else{
			//未获取到用户id，返回999
			dataMap.put("resCode", "999");
		}
		
		
		return dataMap;
	}
    
	/**
	 * 全民抽奖-判断活动是否开始或结束
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/micro2.0/activity/national_lottery_checkActivityTime")
	public @ResponseBody HashMap<String, Object> nationalLotteryCheckActivityTime(
		HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		//查询活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime.setActivityId(Constants.ACTIVITY_178_20170123);
        reqTime.setFormat("yyyy-MM-dd HH:mm:ss");
        
        ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
        dataMap.put("isStart",resTime.getIsStart());
		
		return dataMap;
	}
	
    //========================= 钱报全民抽奖活动(2017-1-16 - 2017-1-27) E================================


	public static boolean containsEmoji(String source) {
		int len = source.length();
		boolean isEmoji = false;
		for (int i = 0; i < len; i++) {
			char hs = source.charAt(i);
			if (0xd800 <= hs && hs <= 0xdbff) {
				if (source.length() > 1) {
					char ls = source.charAt(i + 1);
					int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
					if (0x1d000 <= uc && uc <= 0x1f77f) {
						return true;
					}
				}
			} else {
				// non surrogate
				if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
					return true;
				} else if (0x2B05 <= hs && hs <= 0x2b07) {
					return true;
				} else if (0x2934 <= hs && hs <= 0x2935) {
					return true;
				} else if (0x3297 <= hs && hs <= 0x3299) {
					return true;
				} else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
						|| hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
						|| hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
					return true;
				}
				if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
					char ls = source.charAt(i + 1);
					if (ls == 0x20e3) {
						return true;
					}
				}
			}
		}
		return isEmoji;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 *
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return source;
		}
		StringBuilder buf = null;
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			}
		}
		if (buf == null) {
			return source;
		} else {
			if (buf.length() == len) {
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}
	}
}
