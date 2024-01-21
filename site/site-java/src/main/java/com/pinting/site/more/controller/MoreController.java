package com.pinting.site.more.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.MatrixToImageWriter;


@Controller
public class MoreController extends BaseController {
    
    @Autowired
    private CommunicateBusiness siteService;
    @Autowired
	private WeChatUtil weChatUtil;
    
	/**
	 * 更多首页
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/more")
	public String more(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    String link = GlobEnv.getWebURL("/micro2.0/index");
	    CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        
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
        model.put("userId", userId);
        if (StringUtil.isNotBlank(userId)) {
        	// 风险测评
        	ReqMsg_User_QuestionnaireQuery questionReq = new ReqMsg_User_QuestionnaireQuery();
            questionReq.setUserId(Integer.valueOf(userId));
            ResMsg_User_QuestionnaireQuery questionRes = (ResMsg_User_QuestionnaireQuery)siteService.handleMsg(questionReq);
            model.put("questionResMsg", questionRes);
            
            // 已获得推荐奖励
            ReqMsg_User_RecommendBonus reqMsg = new ReqMsg_User_RecommendBonus();
            reqMsg.setUserId(Integer.valueOf(userId));
            ResMsg_User_RecommendBonus resMsg = (ResMsg_User_RecommendBonus)siteService.handleMsg(reqMsg);
            model.put("recommendResMsg", resMsg);
        }
        return channel + "/more/more";
	}
	
	/**
	 * 更多-推荐赢奖金
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/toRecommend")
	public String toRecommend(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	    String recommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(userId));
	    model.put("recommend", recommend);
	    String user = userId + UUID.randomUUID().toString();
	    Map<String,String> sign = weChatUtil.createAuth(request);
        // String link = GlobEnv.getWebURL("/micro2.0/index?user="+user);
	    // 新的分享连接——注册页
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
		
		model.put("matrix", matrix);
		model.put("weichat", sign);
		model.put("source", "share");
	    return channel + "/more/toRecommend";
	}
	
	/**
	 * 推荐送奖金-信息
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/toRecommendInfo")
	public String toRecommendInfo(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
	    CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String recommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(userId));
        model.put("recommend", recommend);

		// 获取
		ReqMsg_Activity_BaseData recommendReq  = new ReqMsg_Activity_BaseData();
		recommendReq.setActivityType(Constants.RECOMMEND_ACTIVITY_TYPE);
		ResMsg_Activity_BaseData recommendRes = (ResMsg_Activity_BaseData) siteService.handleMsg(recommendReq);
		model.put("newRules", Constants.ACTIVITY_IS_NOT_START.equals(recommendRes.getIsStart()) ? Constants.is_no : Constants.is_yes);
		model.put("startTime", recommendRes.getStartTime());
		model.put("endTime", recommendRes.getEndTime());
		model.put("rate", recommendRes.getExtendInfo());

        // 分享
        String link = GlobEnv.getWebURL("/micro2.0/index");
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
	    return channel + "/more/recommend_info";
	}
	
	/**
     * 更多-推荐赢奖金-移动APP专属
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/more/toRecommendApp")
    public String toRecommendApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
        String userId = request.getParameter("userId");
        String recommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(userId));
        model.put("recommend", recommend);
        String user = userId + UUID.randomUUID().toString();
        Map<String,String> sign = weChatUtil.createAuth(request);
        // String link = GlobEnv.getWebURL("/micro2.0/index?user="+user);
        // 新的分享连接——注册页
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
        model.put("userId", userId);
        model.put("matrix", matrix);
        model.put("weichat", sign);
        model.put("source", "share");

        return channel + "/more/toRecommendApp";
    }
    
    /**
     * 推荐送奖金-信息：APP专享
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/more/toRecommendInfoApp")
    public String toRecommendInfoApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String userId = request.getParameter("userId");
        String recommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(userId));
        model.put("recommend", recommend);

		// 获取
		ReqMsg_Activity_BaseData recommendReq  = new ReqMsg_Activity_BaseData();
		recommendReq.setActivityType(Constants.RECOMMEND_ACTIVITY_TYPE);
		ResMsg_Activity_BaseData recommendRes = (ResMsg_Activity_BaseData) siteService.handleMsg(recommendReq);
		model.put("newRules", Constants.ACTIVITY_IS_NOT_START.equals(recommendRes.getIsStart()) ? Constants.is_no : Constants.is_yes);
		model.put("startTime", recommendRes.getStartTime());
		model.put("endTime", recommendRes.getEndTime());
		model.put("rate", recommendRes.getExtendInfo());

		return channel + "/more/recommend_info_app";
    }
    
    
	/**
	 * 更多-我的推荐
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/myRecommend")
	public String myRecommend(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model, ReqMsg_User_BsSubUserListQuery req){
	    try {
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            req.setUserId(Integer.valueOf(userId));
            req.setPageIndex(0);
            req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            ResMsg_User_BsSubUserListQuery resp = (ResMsg_User_BsSubUserListQuery) siteService
                    .handleMsg(req);
            model.put("pageIndex", 0);
            model.put("totalCount", resp.getTotalCount());
            model.put("bsUserList", resp.getBsUserList());
        } catch (Exception e) {
            e.printStackTrace();
        }
	    
	    String link = GlobEnv.getWebURL("/micro2.0/index");
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
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
	    return channel + "/more/myRecommend";
	}
	/**
	 * 更多-关于币港湾
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/aboutBiGangWan")
	public String aboutBiGangWan(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    String link = GlobEnv.getWebURL("/micro2.0/index");
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
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        model.put("now", DateUtil.formatYYYYMMDD(new Date()));
	    return channel + "/more/aboutBiGangWan";
	}
	
	/**
	 * 更多-关于币港湾-App专享
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/aboutBiGangWanApp")
	public String aboutBiGangWanAPP(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    //String link = GlobEnv.getWebURL("/micro2.0/index");
	    String client = request.getParameter("client");
	    model.put("client", client);
	    model.put("now", DateUtil.formatYYYYMMDD(new Date()));
        // 钱报的参数
        /*String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
        }*/
//        Map<String,String> sign = weChatUtil.createAuth(request);
//        sign.put("link", link);
//        model.put("weichat", sign);
        
	    return channel + "/more/aboutBiGangWanApp";
	}
	
	/**
	 * 更多-安全保障
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/security")
	public String security(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    String link = GlobEnv.getWebURL("/micro2.0/index");
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
        
        String type = request.getParameter("type");
        model.put("type", type);
	    
	    return channel + "/more/security";
	}
	
	/**
	 * 更多-帮助中心
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/help")
	public String help(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    String link = GlobEnv.getWebURL("/micro2.0/index");
	    
	    // 钱报的参数
	    String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
	    return channel + "/more/help";
	}
	/**
	 * 更多-意见反馈
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/feedback")
	public String feedback(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    String link = GlobEnv.getWebURL("/micro2.0/index");
        
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
	    
	    return channel + "/more/feedback";
	}
	/**
	 * 更多-币港湾地址
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/more/biGangWanURL")
	public String biGangWanURL(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
	    String link = GlobEnv.getWebURL("/micro2.0/index");
        
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
	    
	    return channel + "/more/biGangWanURL";
	}
	
	/**
     * 分享成功后获取新手分享额度
     * @param request
     * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping("/micro2.0/more/newUserAddProductLimit")
	public Map<String, Object> newUserAddProductLimit(HttpServletRequest request, HttpServletResponse response){
	    log.info("================== 分享回调添加新手额度  ====================");
	    Map<String, Object> model = new HashMap<String, Object>();
	    ReqMsg_UserProductLimit_UserProductLimitAdd reqMsg = new ReqMsg_UserProductLimit_UserProductLimitAdd();
	    CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	    reqMsg.setUserId(Integer.parseInt(userId));
	    reqMsg.setEvent(Constants.EVENT_SHARE);
	    ResMsg_UserProductLimit_UserProductLimitAdd res = (ResMsg_UserProductLimit_UserProductLimitAdd) siteService.handleMsg(reqMsg);
	    model.put("resCode", res.getResCode());
	    model.put("resMsg", res.getResMsg());
	    log.info("================== 分享回调添加新手额度结束："+res.getResCode()+" "+res.getResMsg()+" ====================");
	    return model;
	}
	
}
