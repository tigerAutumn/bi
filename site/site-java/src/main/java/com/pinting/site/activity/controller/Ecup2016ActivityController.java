package com.pinting.site.activity.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetEcup2016SupportorList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetEcup2016WinnerList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_SaveSupportor;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_SaveUserChampionSilver;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetEcup2016SupportorList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetChampionSilverList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetUserActInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_QueryEcupProductGrid;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetChampionSilverList;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetEcup2016WinnerList;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetUserActInfo;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_QueryEcupProductGrid;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_SaveSupportor;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_SaveUserChampionSilver;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;
import com.pinting.util.MatrixToImageWriter;

/**
 * 欧洲杯活动相关
 * @author HuanXiong
 * @version 2016-6-22 下午5:39:23
 */
@Controller
@RequestMapping("{channel}/activity/ecup")
public class Ecup2016ActivityController {

    @Autowired
    private CommunicateBusiness communicateBusiness;
    @Autowired
    private WeChatUtil          weChatUtil;
    
    public static void main(String[] args) {
        Double a = 10.6d;
        Double b = 10.4d;
        NumberTool tool = new NumberTool();
        System.out.println(Integer.parseInt(tool.integer(a)));
        System.out.println(Integer.parseInt(tool.integer(b)));
    }

    /**
     * 竞猜结果页(PC含二维码)
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/quizResults")
    public String quizResults(@PathVariable String channel, HttpServletRequest request,
                              ReqMsg_Ecup2016Activity_GetEcup2016SupportorList reqMsg,
                              HttpServletResponse response, Map<String, Object> model) {
        CookieManager manager = new CookieManager(request);
        if ("micro2.0".equals(channel)) {
            // 分享内容
            String link = GlobEnv.getWebURL("/micro2.0/activity/ecup/quizResults");
            link = link + "?userId=" + reqMsg.getUserId() + "&menu=yes";
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            sign.put("share_title", "【币港湾】激情欧洲杯！Apple Watch免费送！");
            sign.put("share_content", "一键分享，30元现金轻松到手！更有iPhone 6s plus和Apple Watch等你拿！");
            sign.put("share_logo", GlobEnv.getWebURL("/resources/micro2.0/images/share/ecup_logo.jpg"));
            model.put("weichat", sign);
            model.put("source", "all");
            
            // 分享进入当前页，存入助力者
            // 判断活动是否已经截止
            Date now = new Date();
            Date startTime = DateUtil.parse(Constants.Ecup_START_TIME, "yyyy-MM-dd HH:mm:ss");
            Date endTime = DateUtil.parse(Constants.Ecup_END_TIME, "yyyy-MM-dd HH:mm:ss");
            if(now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0) {
                if(null == reqMsg.getUserId()) {
                    return "redirect:/"+channel + "/activity/ecup/ecup2016_index";
                }
                ReqMsg_Ecup2016Activity_SaveSupportor reqSaveSupportor = new ReqMsg_Ecup2016Activity_SaveSupportor();
                String wxNick = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_WX_NICK.getName(), true);
                String wxId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_OPEN_ID.getName(), true);
                if(StringUtil.isNotBlank(wxId)) {
                    reqSaveSupportor.setUserId(reqMsg.getUserId());
                    reqSaveSupportor.setWxId(wxId);
                    reqSaveSupportor.setWxNick(wxNick);
                    ResMsg_Ecup2016Activity_SaveSupportor resSaveSupportor = (ResMsg_Ecup2016Activity_SaveSupportor) communicateBusiness.handleMsg(reqSaveSupportor);
                    if("971009".equals(resSaveSupportor.getResCode())) {
                        model.put("errCode", "971009");
                    } else if("900001".equals(resSaveSupportor.getResCode())) {
                        return "redirect:/"+channel + "/activity/ecup/ecup2016_index";
                    }
                    model.put("activity_is_end", "no");
                } else {
                    return "redirect:/"+channel + "/activity/ecup/ecup2016_index";
                }
            } else {
                model.put("activity_is_end", "yes");
            }
            
            // 竞猜支持率
            ReqMsg_Ecup2016Activity_GetUserActInfo actInfoReq = new ReqMsg_Ecup2016Activity_GetUserActInfo();
            actInfoReq.setUserId(reqMsg.getUserId());
            ResMsg_Ecup2016Activity_GetUserActInfo resUserAct = (ResMsg_Ecup2016Activity_GetUserActInfo) communicateBusiness.handleMsg(actInfoReq);
            model.put("championRate", resUserAct.getChampionRate());
            model.put("silverRate", resUserAct.getSilverRate());
            model.put("champion", resUserAct.getChampion());
            model.put("silver", resUserAct.getSilver());
            model.put("supportRanking", resUserAct.getSupportRanking());
            model.put("userId", reqMsg.getUserId());
            // 助力者列表
            if(null == reqMsg.getPageIndex()) {
                reqMsg.setPageIndex(0);
            }
            reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            ResMsg_Ecup2016Activity_GetEcup2016SupportorList resMsg = (ResMsg_Ecup2016Activity_GetEcup2016SupportorList) communicateBusiness
                .handleMsg(reqMsg);
            model.put("dataGrid", resMsg.getList());
            model.put("totalCount", resMsg.getTotalCount());    // 总页数
            model.put("count", resMsg.getCount());    // 总条数
        }
        return channel + "/activity/ecup2016/quizResults";
    }
    
    /**
     * 助力者列表分页
     * @param channel
     * @param request
     * @param reqMsg
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/quizResultsPage")
    public String quizResultsPage(@PathVariable String channel, HttpServletRequest request,
                                  ReqMsg_Ecup2016Activity_GetEcup2016SupportorList reqMsg,
                                  HttpServletResponse response, Map<String, Object> model) {
        // 助力者列表
        if(null == reqMsg.getPageIndex()) {
            reqMsg.setPageIndex(0);
        }
        reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
        ResMsg_Ecup2016Activity_GetEcup2016SupportorList resMsg = (ResMsg_Ecup2016Activity_GetEcup2016SupportorList) communicateBusiness
            .handleMsg(reqMsg);
        model.put("dataGrid", resMsg.getList());
        model.put("totalCount", resMsg.getTotalCount());    // 总页数
        model.put("count", resMsg.getCount());    // 总条数
        return channel + "/activity/ecup2016/quizResultsPage";
    }
    
    /**
     * 活动主页
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/ecup2016_index")
    public String ecup2016Index(@PathVariable String channel, HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
        // PC
        if ("gen2.0".equals(channel)) {
            CookieManager manager = new CookieManager(request);
            // 生成二维码
            String link = GlobEnv.getWebURL("/micro2.0/activity/ecup/quizResults");
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            link = link + "?userId="+userId+"&menu=yes";
            // 二维码相关
            MatrixToImageWriter.createMatrixImage(link, userId + "_ecup2016", null, true);
            String matrix = GlobEnv.get("wxQRcode.url.prefix");
            if (matrix.charAt(matrix.length() - 1) != '/') {
                matrix = matrix + "/";
            }
            matrix += userId + "_ecup2016.png";
            model.put("matrix", matrix);
            
            if("show_after".equals(request.getParameter("show"))) {
                model.put("show", "show_after");
            }
        }
        if("micro2.0".equals(channel)) {
            // 分享内容
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            String link = GlobEnv.getWebURL("/micro2.0/activity/ecup/quizResults");
            link = link + "?userId=" + userId + "&menu=yes";
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            sign.put("share_title", "【币港湾】激情欧洲杯！Apple Watch免费送！");
            sign.put("share_content", "一键分享，30元现金轻松到手！更有iPhone 6s plus和Apple Watch等你拿！");
            sign.put("share_logo", GlobEnv.getWebURL("/resources/micro2.0/images/share/ecup_logo.jpg"));
            model.put("weichat", sign);
            model.put("source", "all");
        }
        
    	/* 活动欧洲杯特享 */
    	ReqMsg_Ecup2016Activity_QueryEcupProductGrid ecupProReq = new ReqMsg_Ecup2016Activity_QueryEcupProductGrid();
    	if("micro2.0".equals(channel)){
    		ecupProReq.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
    	}else{
    		ecupProReq.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
    	}
    	ecupProReq.setType("NORMAL");
    	ResMsg_Ecup2016Activity_QueryEcupProductGrid ecupProRes = (ResMsg_Ecup2016Activity_QueryEcupProductGrid)communicateBusiness.handleMsg(ecupProReq);
    	model.put("ecupPro", ecupProRes.getDataGrid());
    	/* 欧洲杯新手专享 */
    	ReqMsg_Ecup2016Activity_QueryEcupProductGrid ecupNewUserProReq = new ReqMsg_Ecup2016Activity_QueryEcupProductGrid();
    	if("micro2.0".equals(channel)){
    		ecupNewUserProReq.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
    	}else{
    		ecupNewUserProReq.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
    	}
    	ecupNewUserProReq.setType("NEW_USER");
    	ResMsg_Ecup2016Activity_QueryEcupProductGrid ecupNewUserProRes = (ResMsg_Ecup2016Activity_QueryEcupProductGrid)communicateBusiness.handleMsg(ecupNewUserProReq);
    	model.put("newUserPro", ecupNewUserProRes.getDataGrid());
    	
    	 /* 球队支持率*/
    	ReqMsg_Ecup2016Activity_GetChampionSilverList championSilverListReq = new ReqMsg_Ecup2016Activity_GetChampionSilverList();
    	ResMsg_Ecup2016Activity_GetChampionSilverList championSilverListRes = (ResMsg_Ecup2016Activity_GetChampionSilverList)communicateBusiness.handleMsg(championSilverListReq);
    	model.put("championList", championSilverListRes.getChampionList());
    	model.put("silverList", championSilverListRes.getSilverList());
    	
    	/*用户竞猜结果 */
    	CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
       if(StringUtil.isBlank(userId)){
    	   model.put("userFlag", "No");//No表示用户未竞猜
       }else{
    	   ReqMsg_Ecup2016Activity_GetUserActInfo userReq = new ReqMsg_Ecup2016Activity_GetUserActInfo();
    	   userReq.setUserId(Integer.parseInt(userId));
    	   ResMsg_Ecup2016Activity_GetUserActInfo userRes = (ResMsg_Ecup2016Activity_GetUserActInfo)communicateBusiness.handleMsg(userReq);
    	   if(userRes.getUserId() == null){
    		   model.put("userFlag", "No");//No表示用户未竞猜
    	   }else{
    		   model.put("userFlag", "Yes");
    		   model.put("userChampion", userRes.getChampion());
    		   model.put("userSilver", userRes.getSilver());
    		   model.put("userSupportRanking", userRes.getSupportRanking());
    		   model.put("userChampionRate", userRes.getChampionRate());
    		   model.put("userSilverRate", userRes.getSilverRate());
    		   model.put("userSupportNum", userRes.getSupportNum());
    		   model.put("overLuckyNum", userRes.getOverLuckyNum());
    	   }
       }
       
       Date now = new Date();
       String today = DateUtil.format(now);
       model.put("now", today);
       Date startTime = DateUtil.parseDateTime(Constants.Ecup_START_TIME);
       Date endTime = DateUtil.parseDateTime(Constants.Ecup_END_TIME);
       Date endTime4Guess = DateUtil.parseDateTime(Constants.Ecup_END_TIME_4_GUESS);
       if(startTime.compareTo(now)>0){
    	   model.put("ecupStatus", "noStart");
    	   model.put("ecupStatus4Guess", "noStart");
       }
       if(endTime.compareTo(now)<0){
    	   model.put("ecupStatus", "isEnd");
       }
       if(endTime4Guess.compareTo(now)<0){
    	   model.put("ecupStatus4Guess", "isEnd");
       }
       ReqMsg_Ecup2016Activity_GetEcup2016WinnerList reqMsg = new ReqMsg_Ecup2016Activity_GetEcup2016WinnerList();
       //助力排行榜
       if(null == reqMsg.getPageIndex()) {
           reqMsg.setPageIndex(0);
       }
       reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
       if("gen2.0".equals(channel)) {
           reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
       }
       ResMsg_Ecup2016Activity_GetEcup2016WinnerList resMsg = (ResMsg_Ecup2016Activity_GetEcup2016WinnerList) communicateBusiness
           .handleMsg(reqMsg);
       model.put("dataGrid", resMsg.getList());
       model.put("totalCount", resMsg.getTotalCount());    // 总页数
       model.put("count", resMsg.getCount());    // 总条数
       
       return channel+"/activity/ecup2016/index";
	}
    
    
    /**
     * 助力值排行分页
     * @param channel
     * @param request
     * @param reqMsg
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/supportListPage")
    public String supportListPage(@PathVariable String channel, HttpServletRequest request,
    		ReqMsg_Ecup2016Activity_GetEcup2016WinnerList reqMsg,HttpServletResponse response, Map<String, Object> model) {
        // 助力者列表
        if(null == reqMsg.getPageIndex()) {
            reqMsg.setPageIndex(0);
        }
        reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
        if("gen2.0".equals(channel)) {
            reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
        }
        ResMsg_Ecup2016Activity_GetEcup2016WinnerList resMsg = (ResMsg_Ecup2016Activity_GetEcup2016WinnerList) communicateBusiness
            .handleMsg(reqMsg);
        model.put("dataGrid", resMsg.getList());
        model.put("totalCount", resMsg.getTotalCount());    // 总页数
        model.put("count", resMsg.getCount());    // 总条数
        if("gen2.0".equals(channel)) {
            model.put("index", reqMsg.getPageIndex()*Constants.EXCHANGE_PAGE_SIZE_LONG);
        } else {
            model.put("index", reqMsg.getPageIndex()*Constants.EXCHANGE_PAGE_SIZE);
        }
        
        return channel + "/activity/ecup2016/supportListPage";
    }
    
    
    /**
     * 用户选择冠亚军添加
     * @param request
     * @param response
     * @param champion
     * @param silver
     * @return
     */
   	@ResponseBody
   	@RequestMapping("/addUserChoose")
   	public Map<String, Object> financeDay518DrawRedPacket(HttpServletRequest request, HttpServletResponse response,
   			String champion,String silver ) {
           Map<String, Object> result = new HashMap<String, Object>(); 

           Date now = new Date();
           Date startTime = DateUtil.parseDateTime(Constants.Ecup_START_TIME);
           Date endTime4Guess = DateUtil.parseDateTime(Constants.Ecup_END_TIME_4_GUESS);
           if(startTime.compareTo(now)>0){
        	   result.put("ecupStatus4Guess", "noStart");
           }
           if(endTime4Guess.compareTo(now)<0){
        	   result.put("ecupStatus4Guess", "isEnd");
           }
           if(startTime.compareTo(now)<0 && endTime4Guess.compareTo(now)>0){
        	   CookieManager manager = new CookieManager(request);
               String userId = manager.getValue(CookieEnums._SITE.getName(),
                   CookieEnums._SITE_USER_ID.getName(), true);
               if(StringUtil.isBlank(userId)){
            	   result.put("userFlag", "noLogin");
               }else{
            	   ReqMsg_Ecup2016Activity_SaveUserChampionSilver req = new ReqMsg_Ecup2016Activity_SaveUserChampionSilver();
            	   req.setUserId(Integer.parseInt(userId));
            	   req.setChampion(champion);
            	   req.setSilver(silver);
            	   ResMsg_Ecup2016Activity_SaveUserChampionSilver res = (ResMsg_Ecup2016Activity_SaveUserChampionSilver)communicateBusiness.handleMsg(req);
            	   if("999999".equals(res.getResCode())){
            		   result.put("userFlag", "noLogin");
            	   }
            	   if(!res.isFlag()){
            		   result.put("addFlag", "noAdd");
            	   }
            	   
               }
           }
          
           
           return result;
    
    }
   	
   	
   	/**
     * 活动主页
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/ecup2016_index_app")
    public String ecup2016IndexAPP( HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
        String today = DateUtil.format(new Date());
        model.put("now", today);
    	/* 活动欧洲杯特享 */
    	ReqMsg_Ecup2016Activity_QueryEcupProductGrid ecupProReq = new ReqMsg_Ecup2016Activity_QueryEcupProductGrid();
    	ecupProReq.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_APP);
    	
    	ecupProReq.setType("NORMAL");
    	ResMsg_Ecup2016Activity_QueryEcupProductGrid ecupProRes = (ResMsg_Ecup2016Activity_QueryEcupProductGrid)communicateBusiness.handleMsg(ecupProReq);
    	model.put("ecupPro", ecupProRes.getDataGrid());
    	/* 欧洲杯新手专享 */
    	ReqMsg_Ecup2016Activity_QueryEcupProductGrid ecupNewUserProReq = new ReqMsg_Ecup2016Activity_QueryEcupProductGrid();
    	ecupProReq.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_APP);
    	ecupNewUserProReq.setType("NEW_USER");
    	ResMsg_Ecup2016Activity_QueryEcupProductGrid ecupNewUserProRes = (ResMsg_Ecup2016Activity_QueryEcupProductGrid)communicateBusiness.handleMsg(ecupNewUserProReq);
    	model.put("newUserPro", ecupNewUserProRes.getDataGrid());
    	 /* 球队支持率*/
    	ReqMsg_Ecup2016Activity_GetChampionSilverList championSilverListReq = new ReqMsg_Ecup2016Activity_GetChampionSilverList();
    	ResMsg_Ecup2016Activity_GetChampionSilverList championSilverListRes = (ResMsg_Ecup2016Activity_GetChampionSilverList)communicateBusiness.handleMsg(championSilverListReq);
    	model.put("championList", championSilverListRes.getChampionList());
    	model.put("silverList", championSilverListRes.getSilverList());
    	
    	/*用户竞猜结果 */
    	String client = request.getParameter("client");
		model.put("client", client);
		String userId = request.getParameter("userId");
		String afterUserId = request.getParameter("afterUserId");
		if(StringUtil.isNotBlank(afterUserId)){
			userId = afterUserId;
			model.put("userId", afterUserId);
			model.put("afterUserId", afterUserId);
		}else{
			if(StringUtil.isNotBlank(userId)){
				userId = new DESUtil("cfgubijn").decryptStr(userId);
				model.put("userId", userId);
				model.put("afterUserId", userId);
				
			}
		}
       if(StringUtil.isBlank(userId)){
    	   model.put("userFlag", "No");//No表示用户未竞猜
       }else{
    	   ReqMsg_Ecup2016Activity_GetUserActInfo userReq = new ReqMsg_Ecup2016Activity_GetUserActInfo();
    	   userReq.setUserId(Integer.parseInt(userId));
    	   ResMsg_Ecup2016Activity_GetUserActInfo userRes = (ResMsg_Ecup2016Activity_GetUserActInfo)communicateBusiness.handleMsg(userReq);
    	   if(userRes.getUserId() == null){
    		   model.put("userFlag", "No");//No表示用户未竞猜
    	   }else{
    		   model.put("userFlag", "Yes");
    		   model.put("userChampion", userRes.getChampion());
    		   model.put("userSilver", userRes.getSilver());
    		   model.put("userSupportRanking", userRes.getSupportRanking());
    		   model.put("userChampionRate", userRes.getChampionRate());
    		   model.put("userSilverRate", userRes.getSilverRate());
    		   model.put("userSupportNum", userRes.getSupportNum());
    		   model.put("overLuckyNum", userRes.getOverLuckyNum());
    	   }
       }
       
       Date now = new Date();
       Date startTime = DateUtil.parseDateTime(Constants.Ecup_START_TIME);
       Date endTime = DateUtil.parseDateTime(Constants.Ecup_END_TIME);
       Date endTime4Guess = DateUtil.parseDateTime(Constants.Ecup_END_TIME_4_GUESS);
       if(startTime.compareTo(now)>0){
    	   model.put("ecupStatus", "noStart");
    	   model.put("ecupStatus4Guess", "noStart");
       }
       if(endTime.compareTo(now)<0){
    	   model.put("ecupStatus", "isEnd");
       }
       if(endTime4Guess.compareTo(now)<0){
    	   model.put("ecupStatus4Guess", "isEnd");
       }
       ReqMsg_Ecup2016Activity_GetEcup2016WinnerList reqMsg = new ReqMsg_Ecup2016Activity_GetEcup2016WinnerList();
       //助力排行榜
       if(null == reqMsg.getPageIndex()) {
           reqMsg.setPageIndex(0);
       }
       reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
       ResMsg_Ecup2016Activity_GetEcup2016WinnerList resMsg = (ResMsg_Ecup2016Activity_GetEcup2016WinnerList) communicateBusiness
           .handleMsg(reqMsg);
       model.put("dataGrid", resMsg.getList());
       model.put("totalCount", resMsg.getTotalCount());    // 总页数
       model.put("count", resMsg.getCount());    // 总条数
       
       return "micro2.0/activity/ecup2016/index_app";
	}
    
    /**
     *  用户选择冠亚军添加-APP
     * @param request
     * @param response
     * @param champion
     * @param silver
     * @return
     */
   	@ResponseBody
   	@RequestMapping("/addUserChoose_app")
   	public Map<String, Object> financeDay518DrawRedPacketAPP(HttpServletRequest request, HttpServletResponse response,
   			String champion,String silver ) {
           Map<String, Object> result = new HashMap<String, Object>(); 

           Date now = new Date();
           Date startTime = DateUtil.parseDateTime(Constants.Ecup_START_TIME);
           Date endTime4Guess = DateUtil.parseDateTime(Constants.Ecup_END_TIME_4_GUESS);
           if(startTime.compareTo(now)>0){
        	   result.put("ecupStatus4Guess", "noStart");
           }
           if(endTime4Guess.compareTo(now)<0){
        	   result.put("ecupStatus4Guess", "isEnd");
           }
           if(startTime.compareTo(now)<0 && endTime4Guess.compareTo(now)>0){
        	   String userId = request.getParameter("userId");
               
               if(StringUtil.isBlank(userId)){
            	   result.put("userFlag", "noLogin");
               }else{
            	   ReqMsg_Ecup2016Activity_SaveUserChampionSilver req = new ReqMsg_Ecup2016Activity_SaveUserChampionSilver();
            	   req.setUserId(Integer.parseInt(userId));
            	   req.setChampion(champion);
            	   req.setSilver(silver);
            	   ResMsg_Ecup2016Activity_SaveUserChampionSilver res = (ResMsg_Ecup2016Activity_SaveUserChampionSilver)communicateBusiness.handleMsg(req);
            	   if("999999".equals(res.getResCode())){
            		   result.put("userFlag", "noLogin");
            	   }
               }
           }
          
           
           return result;
    
    }
   	
   	/**
   	 * 获取当前时间
   	 * @param request
   	 * @param response
   	 * @return
   	 */
   	@ResponseBody
   	@RequestMapping("/getNowTime")
  	public Map<String, Object> getNowTime(HttpServletRequest request, HttpServletResponse response){
  		Map<String, Object> result = new HashMap<String, Object>(); 
  		Date now = new Date();
  		String today = DateUtil.format(now);
  		result.put("now", today);
  		return result;
  	}
   	

}
