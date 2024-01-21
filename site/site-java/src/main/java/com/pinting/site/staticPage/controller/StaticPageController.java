package com.pinting.site.staticPage.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.ReqMsg_Activity_BaseData;
import com.pinting.business.hessian.site.message.ResMsg_Activity_BaseData;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.WeChatShareUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

/**
 * 
 * @ClassName: StaticPageController 
 * @Description: 静态页面处理Controller
 * @author lenovo
 * @date 2016年4月11日 上午10:29:53 
 *
 */
@Controller
@Scope("prototype")
public class StaticPageController {
	@Autowired
	private WeChatUtil weChatUtil;
    @Autowired
    private CommunicateBusiness siteService;


    @RequestMapping("/micro2.0/staticApp/aboutBiGangWan")
    public String aboutBigangWan(HttpServletRequest request,HttpServletResponse response,Map<String,Object> model) {
        String client = request.getParameter("client");
        model.put("client", client);
        model.put("now", DateUtil.formatYYYYMMDD(new Date()));
        return "/static/micro2.0/aboutBigangWan";
    }
    
	@RequestMapping("{terminal}/static/{page}")
	public String show(@PathVariable String terminal,@PathVariable String page,
			HttpServletRequest request,HttpServletResponse response,Map<String,Object> model) {
        String client = request.getParameter("client");
        model.put("client", client);
        if ("interes_ticket".equals(page)) {
            // 分享内容
            String link = GlobEnv.getWebURL("/micro2.0/static/interes_ticket");
            String shareTitle = "币港湾神券问世";
            String shareContent = "听说，有一张神券，可以让收益变更多";
            String logo = GlobEnv.getWebURL("/resources/micro2.0/images/common/bgw_logo.png");
            WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        } else if("zan_jiaxi".equals(page)) {
            String logo = GlobEnv.getWebURL("/resources/micro2.0/images/common/bgw_logo.png");
            WeChatShareUtil.share("micro2.0", GlobEnv.getWebURL("/micro2.0/regular/list")
                    , "加息又返利，月月有回款", "赞分期，教你如何灵活的赚钱~", logo, true, request, model, weChatUtil);
        } else {
            if("recommend_calculator".equals(page) || "recommend_calculatorapp".equals(page)) {
                ReqMsg_Activity_BaseData recommendReq  = new ReqMsg_Activity_BaseData();
                recommendReq.setActivityType(Constants.RECOMMEND_ACTIVITY_TYPE);
                ResMsg_Activity_BaseData recommendRes = (ResMsg_Activity_BaseData) siteService.handleMsg(recommendReq);
                model.put("newRules", Constants.ACTIVITY_IS_NOT_START.equals(recommendRes.getIsStart()) ? Constants.is_no : Constants.is_yes);
                model.put("startTime", recommendRes.getStartTime());
                model.put("endTime", recommendRes.getEndTime());
                model.put("rate", recommendRes.getExtendInfo());
            }
            share("micro2.0", model, request, GlobEnv.getWebURL("/micro2.0/regular/list"));
        }
		return "static/"+terminal+"/"+page;
	}
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
     * 四季生财活动页app专属
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/micro2.0/staticFourSeasons/index")
    public String staticFourSeasons(HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) {
        String client = request.getParameter("client");
        model.put("client", client);
        return "/static/micro2.0/four_seasons_app";
    }

    /**
     * 新的赞分期产品详情页app专属
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/micro2.0/zanProductDetails/index")
    public String zanProductDetails(HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) {
        String client = request.getParameter("client");
        model.put("client", client);
        return "/static/micro2.0/zan_product_details_app";
    }

    /**
     * 银行存管迁移-公告app专属
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/micro2.0/bankCustodyMoveApp/index")
    public String bankCustodyMoveApp(HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) {
        String client = request.getParameter("client");
        model.put("client", client);
        return "/static/micro2.0/bank_custody_move_app";
    }

    /**
     * 存管专题页app专属
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/micro2.0/depositoryProjectApp/index")
    public String depositoryProjectApp(HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) {
        String client = request.getParameter("client");
        String userId = request.getParameter("userId");
        model.put("client", client);
        model.put("userId", userId);
        return "/static/micro2.0/depository_project_app";
    }
	
}
