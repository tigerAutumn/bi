package com.pinting.site.platform.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.DESUtil;
import com.pinting.util.URLConstant;
import com.pinting.util.WeChatShareUtil;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 帮助中心
 * 
 * @author yanwl
 * @version $Id: AssetsController.java, v 0.1 2015-11-12 上午10:19:39 yanwl
 *          Exp $
 */
@Controller
@Scope("prototype")
@RequestMapping("{channel}/platform")
public class PlatformController extends BaseController {
    
    @Autowired
    private CommunicateBusiness communicateBusiness;

    @Autowired
    private WeChatUtil weChatUtil;

	/**
	 * 平台简介首页
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
    @RequestMapping("/index")
	public String platform(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("help", "platform");
		return channel + "/help/help_index";
	}
	
	
	/**
	 * 管理团队首页
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
    @RequestMapping("/team/index")
	public String team(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("help", "team");
		return channel + "/help/help_index";
	}
	
	
	/**
	 * 联系我们首页
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
    @RequestMapping("/contact/index")
	public String contact(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("help", "contact");
		return channel + "/help/help_index";
	}
	
	
	/**
	 * 服务协议首页
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
    @RequestMapping("/service/index")
	public String service(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("help", "service");
		return channel + "/help/help_index";
	}
	
	
	/**
	 * 隐私条例首页
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
	@RequestMapping("/privacy/index")
	public String privacy(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("help", "privacy");
		return channel + "/help/help_index";
	}
	
	/**
	 * 常见问题首页
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
	@RequestMapping("/help/index")
	public String help(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("help", "help");
		return channel + "/help/help_index";
	}
	
	/**
	 * 公告内容
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @Deprecated
	@RequestMapping("/notice/index")
    public String notice(@PathVariable String channel, ReqMsg_News_QueryNewsPage req, 
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        model.put("help", "notice");
        model.put("list", "list");
        ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
        if("gen178".equals(channel)) {
        	CookieManager manager = new CookieManager(request);
 			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
 			//默认接收类型
 			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 			if(StringUtil.isNotBlank(viewAgentFlagCookie)){
 	        	//判断钱报or柯桥or海宁OR瑞安
 	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
 	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 	        	}
 	        }
            
        } else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
        }
        req.setType(Constants.NEWS_TYPE_NOTICE);
        res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
        req.setTotolRows(res.getCount());
        model.put("req", req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        return channel + "/help/help_index";
    }
	
	
	/**
     * 公告详情
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/notice_detail/index")
    public String noticeDetail(@PathVariable String channel, ReqMsg_News_Details req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        model.put("help", "notice");
        model.put("list", "detail");
        req.setType(Constants.NEWS_TYPE_NOTICE);
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);

        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            res.getDetails().put("content", dest);
            model.put("details", res.getDetails());
            return channel + "/help/help_index";
        } else {
            model.put("url", "platform/notice/index");
            model.put("errMsg", "不存在此新闻公告");
            return channel + "/help/help_error";
        }
    }
	
	/**
     * 媒体报道
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("/news_list/index")
    public String media(@PathVariable String channel, ReqMsg_News_QueryNewsPage req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        model.put("help", "news");
        model.put("list", "list");
        ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
        if("gen178".equals(channel)) {
        	CookieManager manager = new CookieManager(request);
 			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
 			//默认接收类型
 			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 			if(StringUtil.isNotBlank(viewAgentFlagCookie)){
 	        	//判断钱报or柯桥or海宁OR瑞安
 	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
 	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 	        	}
 	        }
            
        } else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
        }
        req.setType(Constants.NEWS_TYPE_NEWS);
        res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
        req.setTotolRows(res.getCount());
        model.put("req", req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        return channel + "/help/help_index";
    }
    
    /**
     * 新闻详情
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/news_detail/index")
    public String newsDetail(@PathVariable String channel, ReqMsg_News_Details req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        model.put("help", "news");
        model.put("list", "detail");
        req.setType(Constants.NEWS_TYPE_NEWS);
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            
            String resources = GlobEnv.get("news.resources");
            String manageWeb = GlobEnv.get("manage.web");
            String web = GlobEnv.get("gen.server.web");
            dest = dest.replaceAll(manageWeb, "");
            
            // 替换emoj表情URL
            String[] a = dest.split(resources);
            int i = 0;
            StringBuffer contentBuf = new StringBuffer();
            contentBuf.append(a[0]);
            for (String string : a) {
                if(i > 0) {
                    contentBuf.append(web);
                    contentBuf.append(resources);
                    contentBuf.append(string);
                }
                i++;
            }
            res.getDetails().put("content", contentBuf.toString());
            model.put("details", res.getDetails());
            return channel + "/help/help_index";
        } else {
            model.put("url", "platform/news_list/index");
            model.put("errMsg", "不存在此新闻公告");
            return channel + "/help/help_error";
        }
        
    }
    
    
    
    
	/**
     * 公司动态
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("/company_dynamic_list/index")
    public String companyDynamic(@PathVariable String channel, ReqMsg_News_QueryNewsPage req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        model.put("help", "company_dynamic");
        model.put("list", "list");
        ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
        if("gen178".equals(channel)) {
        	CookieManager manager = new CookieManager(request);
 			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
 			//默认接收类型
 			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 			if(StringUtil.isNotBlank(viewAgentFlagCookie)){
 	        	//判断钱报or柯桥or海宁OR瑞安
 	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
 	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 	        	}
 	        }
           
        } else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
        }
        req.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
        res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
        req.setTotolRows(res.getCount());
        model.put("req", req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        return channel + "/help/help_index";
    }
    
    /**
     * 公司动态
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_dynamic_detail/index")
    public String companyDynamicDetail(@PathVariable String channel, ReqMsg_News_Details req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        model.put("help", "company_dynamic");
        model.put("list", "detail");
        req.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            
            String resources = GlobEnv.get("news.resources");
            String manageWeb = GlobEnv.get("manage.web");
            String web = GlobEnv.get("gen.server.web");
            dest = dest.replaceAll(manageWeb, "");
            
            // 替换emoj表情URL
            String[] a = dest.split(resources);
            int i = 0;
            StringBuffer contentBuf = new StringBuffer();
            contentBuf.append(a[0]);
            for (String string : a) {
                if(i > 0) {
                    contentBuf.append(web);
                    contentBuf.append(resources);
                    contentBuf.append(string);
                }
                i++;
            }
            res.getDetails().put("content", contentBuf.toString());
            model.put("details", res.getDetails());
            return channel + "/help/help_index";
        } else {
            model.put("url", "platform/company_dynamic_list/index");
            model.put("errMsg", "不存在此公司动态");
            return channel + "/help/help_error";
        }
        
    }
    
    @Deprecated
    @RequestMapping("/data/index")
    public String dataIndex(@PathVariable String channel,
                            HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> model) {
        model.put("help", "data");
        ReqMsg_Invest_PlatformStatistics reqMsg = new ReqMsg_Invest_PlatformStatistics();
        ResMsg_Invest_PlatformStatistics resMsg = (ResMsg_Invest_PlatformStatistics) communicateBusiness.handleMsg(reqMsg);
        model.put("totalInterestAmount", resMsg.getTotalInterestAmount());
        model.put("totalInvestAmount", resMsg.getTotalInvestAmount());
        model.put("totalRegUser", resMsg.getTotalRegUser());
        // 按月累计投资额
        if (null != resMsg.getInvestMentOverDateMonth()) {
            List<String> investMonth = new ArrayList<String>();
            List<String> investAmountMonth = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestMentOverDateMonth()) {
                investMonth.add((String) hashMap.get("investMonth"));
                investAmountMonth.add((String) hashMap.get("totalInvestString"));
            }
            JSONArray investMonthArray = JSONArray.fromObject(investMonth);
            JSONArray investAmountMonthArray = JSONArray.fromObject(resMsg.getInvestMentOverDateMonth());
            model.put("investMonthArray", investMonthArray);
            model.put("investAmountMonthArray", investAmountMonthArray);
        }
        // 短、中、长期 产品
        if(null != resMsg.getInvestTotalGroupByProductTerm()) {
            List<String> productNames = new ArrayList<String>();
            List<String> productAmounts = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestTotalGroupByProductTerm()) {
                productNames.add((String) hashMap.get("productName"));
                productAmounts.add((String) hashMap.get("investTotalGroupByProductAmountString"));
            }
            JSONArray productNameArray = JSONArray.fromObject(productNames);
            JSONArray productAmountArray = JSONArray.fromObject(productAmounts);
            model.put("productNameArray", productNameArray);
            model.put("productAmountArray", productAmountArray);
        }
        
        model.put("investInterestWill", resMsg.getInvestInterestWill());
        model.put("averageInvestRateNormal", resMsg.getAverageInvestRateNormal());
        model.put("averageInvestRate178", resMsg.getAverageInvestRate178());
        model.put("qianbao", request.getParameter("qianbao"));
        
        //年龄
        if(null != resMsg.getInvestorTypeAge()) {
            List<String> investorTypeAges = new ArrayList<String>();
            List<String> investorTypeAgeVals = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestorTypeAge()) {
            	investorTypeAges.add((String) hashMap.get("investorTypeName"));
            	investorTypeAgeVals.add((String) hashMap.get("investorTypeRateString"));
            }
            JSONArray investorTypeAgeArray = JSONArray.fromObject(investorTypeAges);
            JSONArray investorTypeAgeValArray = JSONArray.fromObject(investorTypeAgeVals);
            model.put("investorTypeAgeArray", investorTypeAgeArray);
            model.put("investorTypeAgeValArray", investorTypeAgeValArray);
        }
        //性别
        if(null != resMsg.getInvestorTypeSex()) {
            List<String> investorTypeSexs = new ArrayList<String>();
            List<String> investorTypeSexVals = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestorTypeSex()) {
            	investorTypeSexs.add((String) hashMap.get("investorTypeName"));
            	investorTypeSexVals.add((String) hashMap.get("investorTypeRateString"));
            }
            JSONArray investorTypeSexArray = JSONArray.fromObject(investorTypeSexs);
            JSONArray investorTypeSexValArray = JSONArray.fromObject(investorTypeSexVals);
            model.put("investorTypeSexArray", investorTypeSexArray);
            model.put("investorTypeSexValArray", investorTypeSexValArray);
        }
        
        model.put("currTime", DateUtil.format(new Date()));
        if("micro2.0".equals(channel)){
        	return channel + "/more/platformData";
        }
        return channel + "/help/help_index";
    }
    
    /***************************** 下面是新做的页面 ****************************************************************/
    
    
    /** 信息披露模块开始 **/
    
    /**
	 * 平台公告
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/info/notice/index")
	public String noticeInfo(@PathVariable String channel, ReqMsg_News_QueryNewsPage req, 
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
        CookieManager manager = new CookieManager(request);
        String userID = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(StringUtils.isEmpty(userID) ? null : Integer.parseInt(userID));
		if("gen178".equals(channel)) {
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	//默认接收类型
	        	req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
	        	//判断钱报or柯桥or海宁OR瑞安
	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
	        	}else if(Constants.AGENT_VIEW_ID_QHD_ST.equals(viewAgentFlagCookie)) {
	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWQHDST);
	        	}
	        }
		} else if("gen2.0".equals(channel)) {
			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
		}
		req.setType(Constants.NEWS_TYPE_NOTICE);
		res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
		req.setTotolRows(res.getCount());
		model.put("req", req);
		model.put("count", res.getCount());
		model.put("dataGrid", res.getDataGrid());
		return channel + "/platform/info/website_notice";
	}
	
	/**
     * 平台公告详情
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/info/notice_detail/index")
    public String noticeInfoDetail(@PathVariable String channel, ReqMsg_News_Details req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {

        CookieManager manager = new CookieManager(request);
        String userID = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        ReqMsg_News_ReadMessage readReq = new ReqMsg_News_ReadMessage();
        readReq.setUserId(StringUtils.isEmpty(userID) ? null : Integer.parseInt(userID));
        readReq.setType(Constants.POSITION_NOTICE);
        readReq.setId(req.getId());
        readReq.setReceiverType(this.recieverType(channel, request, model));
        communicateBusiness.handleMsg(readReq);

        req.setType(Constants.NEWS_TYPE_NOTICE);
        req.setUserId(StringUtils.isEmpty(userID) ? null : Integer.parseInt(userID));
        req.setReceiverType(this.recieverType(channel, request, model));
        req.setRequestChannel(channel);
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            res.getDetails().put("content", dest);
            model.put("details", res.getDetails());
            return channel + "/platform/info/website_notice_detail";
        } else {
            model.put("url", "platform/info/notice/index");
            model.put("errMsg", "不存在此平台公告");
            model.put("titleName", "平台公告");
            return channel + "/platform/help_error";
        }
    }
    
    /**
	 * 股东介绍
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/info/share_holders/index")
    public String shareHolders(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
        return channel + "/platform/info/share_holders";
    }
	
	/**
	 * 组织架构
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/info/organization/index")
	public String organization(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/info/organization";
	}
	
	/**
	 * 平台数据
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/info/platform_data/index")
    public String platformData(@PathVariable String channel,
                            HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> model) {
        ReqMsg_Invest_PlatformStatistics reqMsg = new ReqMsg_Invest_PlatformStatistics();
        ResMsg_Invest_PlatformStatistics resMsg = (ResMsg_Invest_PlatformStatistics) communicateBusiness.handleMsg(reqMsg);
        model.put("totalInterestAmount", resMsg.getTotalInterestAmount());
        model.put("totalInvestAmount", resMsg.getTotalInvestAmount());
        model.put("totalRegUser", resMsg.getTotalRegUser());
        // 按月累计投资额
        if (null != resMsg.getInvestMentOverDateMonth()) {
            List<String> investMonth = new ArrayList<String>();
            List<String> investAmountMonth = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestMentOverDateMonth()) {
                investMonth.add((String) hashMap.get("investMonth"));
                investAmountMonth.add((String) hashMap.get("totalInvestString"));
            }
            JSONArray investMonthArray = JSONArray.fromObject(investMonth);
            JSONArray investAmountMonthArray = JSONArray.fromObject(resMsg.getInvestMentOverDateMonth());
            model.put("investMonthArray", investMonthArray);
            model.put("investAmountMonthArray", investAmountMonthArray);
        }
        // 短、中、长期 产品
        if(null != resMsg.getInvestTotalGroupByProductTerm()) {
            List<String> productNames = new ArrayList<String>();
            List<String> productAmounts = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestTotalGroupByProductTerm()) {
                productNames.add((String) hashMap.get("productName"));
                productAmounts.add((String) hashMap.get("investTotalGroupByProductAmountString"));
            }
            JSONArray productNameArray = JSONArray.fromObject(productNames);
            JSONArray productAmountArray = JSONArray.fromObject(productAmounts);
            model.put("productNameArray", productNameArray);
            model.put("productAmountArray", productAmountArray);
        }
        
        model.put("investInterestWill", resMsg.getInvestInterestWill());
        model.put("averageInvestRateNormal", resMsg.getAverageInvestRateNormal());
        model.put("averageInvestRate178", resMsg.getAverageInvestRate178());
        model.put("qianbao", request.getParameter("qianbao"));
        
        //年龄
        if(null != resMsg.getInvestorTypeAge()) {
            List<String> investorTypeAges = new ArrayList<String>();
            List<String> investorTypeAgeVals = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestorTypeAge()) {
            	investorTypeAges.add((String) hashMap.get("investorTypeName"));
            	investorTypeAgeVals.add((String) hashMap.get("investorTypeRateString"));
            }
            JSONArray investorTypeAgeArray = JSONArray.fromObject(investorTypeAges);
            JSONArray investorTypeAgeValArray = JSONArray.fromObject(investorTypeAgeVals);
            model.put("investorTypeAgeArray", investorTypeAgeArray);
            model.put("investorTypeAgeValArray", investorTypeAgeValArray);
        }
        //性别
        if(null != resMsg.getInvestorTypeSex()) {
            List<String> investorTypeSexs = new ArrayList<String>();
            List<String> investorTypeSexVals = new ArrayList<String>();
            for (HashMap<String, Object> hashMap : resMsg.getInvestorTypeSex()) {
            	investorTypeSexs.add((String) hashMap.get("investorTypeName"));
            	investorTypeSexVals.add((String) hashMap.get("investorTypeRateString"));
            }
            JSONArray investorTypeSexArray = JSONArray.fromObject(investorTypeSexs);
            JSONArray investorTypeSexValArray = JSONArray.fromObject(investorTypeSexVals);
            model.put("investorTypeSexArray", investorTypeSexArray);
            model.put("investorTypeSexValArray", investorTypeSexValArray);
        }
        
        model.put("currTime", DateUtil.format(new Date()));
        model.put("result", resMsg);
        if("micro2.0".equals(channel) || "app".equals(channel)){
        	return channel + "/more/platformData";
        }
        return channel + "/platform/info/platform_data";
    }
	
	/**
	 * 运营报告
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/info/report/index")
	public String report(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
			ReqMsg_OperationReport_queryOperationReport reqMsg, Map<String, Object> model) {
		reqMsg.setYear(request.getParameter("pyear"));
		reqMsg.setNumPerPage(Constants.REPORT_PAGE_SIZE);
		
		ResMsg_OperationReport_queryOperationReport resMsg = (ResMsg_OperationReport_queryOperationReport) communicateBusiness.handleMsg(reqMsg);
		reqMsg.setTotolRows(resMsg.getCount());
        model.put("req", reqMsg);
        model.put("count", resMsg.getCount());
		model.put("reportList", resMsg.getReportList());
		model.put("currYear", resMsg.getCurrYear());
		return channel + "/platform/info/report";
	}
	
	/**
	 * 实时监控
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/info/real_time/index")
	public String realTime(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/info/real_time";
	}
	
	/** 信息披露模块结束 **/
	
	
	/** 关于我们模块开始 **/
	
	/**
	 * 平台简介
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/platform_profile/index")
	public String platformProfile(@PathVariable String channel,Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) {
		//获取当前时间
		model.put("now", DateUtil.formatYYYYMMDD(new Date()));
		return channel + "/platform/about/platform_profile";
	}
	
	/**
	 * 发展历程
	 * @param channel
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/about/development_history/index")
	public String developmentIndex(@PathVariable String channel, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/about/development_index";
	}
	
	/**
	 * 公司动态
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/company_dynamics/index")
    public String companyDynamics(@PathVariable String channel,ReqMsg_News_QueryNewsPage req,
    		HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		req.setNumPerPage(5);
		ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
		if("gen178".equals(channel)) {
			CookieManager manager = new CookieManager(request);
 			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
 			//默认接收类型
 			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 			if(StringUtil.isNotBlank(viewAgentFlagCookie)){
 	        	//判断钱报or柯桥or海宁OR瑞安
 	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
 	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 	        	}else if(Constants.AGENT_VIEW_ID_QHD_ST.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWQHDST);
 	        	}
 	        }
            
        }else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
        }
		req.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
		res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
		req.setTotolRows(res.getCount());
        model.put("req", req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        return channel + "/platform/about/company_dynamics";
    }
	
	/**
	 * 湾粉活动列表
	 * @param channel
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/wfans_activity/index")
    public String wfansActivity(@PathVariable String channel,ReqMsg_News_QueryNewsPage req,
            HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
        req.setNumPerPage(5);
        ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
        if("gen178".equals(channel)) {
        	CookieManager manager = new CookieManager(request);
 			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
 			//默认接收类型
 			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 			if(StringUtil.isNotBlank(viewAgentFlagCookie)){
 	        	//判断钱报or柯桥or海宁OR瑞安
 	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
 	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 	        	}else if(Constants.AGENT_VIEW_ID_QHD_ST.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWQHDST);
 	        	}
 	        }
           
        }else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
        }
        req.setType(Constants.NEWS_TYPE_WFANS_ACTIVITY);
        res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
        req.setTotolRows(res.getCount());
        model.put("req", req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        return channel + "/platform/about/wfans_activity_index";
    }
	
	
	@RequestMapping("/about/wfans_activity/detail")
    public String wfansActivityDetail(@PathVariable String channel, ReqMsg_News_Details req,
                                      HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> model) {
        req.setType(Constants.NEWS_TYPE_WFANS_ACTIVITY);
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            
            String resources = GlobEnv.get("news.resources");
            String manageWeb = GlobEnv.get("manage.web");
            String web = GlobEnv.get("gen.server.web");
            dest = dest.replaceAll(manageWeb, "");
            
            // 替换emoj表情URL
            String[] a = dest.split(resources);
            int i = 0;
            StringBuffer contentBuf = new StringBuffer();
            contentBuf.append(a[0]);
            for (String string : a) {
                if(i > 0) {
                    contentBuf.append(web);
                    contentBuf.append(resources);
                    contentBuf.append(string);
                }
                i++;
            }
            res.getDetails().put("content", contentBuf.toString());
            model.put("details", res.getDetails());
            return channel + "/platform/about/wfans_activity_detail";
        } else {
            model.put("url", "platform/about/wfans_activity/index");
            model.put("errMsg", "不存在此湾粉活动");
            model.put("titleName", "湾粉活动");
            return channel + "/platform/help_error";
        }
        
    }
	
	/**
     * 公司动态详情
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/about/company_dynamic_detail/index")
    public String companyDynamicInfoDetail(@PathVariable String channel, ReqMsg_News_Details req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        req.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            
            String resources = GlobEnv.get("news.resources");
            String manageWeb = GlobEnv.get("manage.web");
            String web = GlobEnv.get("gen.server.web");
            dest = dest.replaceAll(manageWeb, "");
            
            // 替换emoj表情URL
            String[] a = dest.split(resources);
            int i = 0;
            StringBuffer contentBuf = new StringBuffer();
            contentBuf.append(a[0]);
            for (String string : a) {
                if(i > 0) {
                    contentBuf.append(web);
                    contentBuf.append(resources);
                    contentBuf.append(string);
                }
                i++;
            }
            res.getDetails().put("content", contentBuf.toString());
            model.put("details", res.getDetails());
            return channel + "/platform/about/company_dynamics_detail";
        } else {
            model.put("url", "platform/about/company_dynamic/index");
            model.put("errMsg", "不存在此公司动态");
            model.put("titleName", "公司动态");
            return channel + "/platform/help_error";
        }
        
    }
	
	/**
	 * 媒体报道
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/media/index")
    public String newsList(@PathVariable String channel, ReqMsg_News_QueryNewsPage req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
		req.setNumPerPage(5);
        ResMsg_News_QueryNewsPage res = new ResMsg_News_QueryNewsPage();
        if("gen178".equals(channel)) {
        	CookieManager manager = new CookieManager(request);
 			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
 			//默认接收类型
 			req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 			if(StringUtil.isNotBlank(viewAgentFlagCookie)){
 	        	//判断钱报or柯桥or海宁OR瑞安OR瑞安
 	        	if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWKQ);
 	        	}else if (Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWHN);
				}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
				}else if(Constants.AGENT_VIEW_ID_QB.equals(viewAgentFlagCookie)){
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW178);
 	        	}else if(Constants.AGENT_VIEW_ID_QHD_ST.equals(viewAgentFlagCookie)) {
 	        		req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGWQHDST);
 	        	}
 	        }
            
        } else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
        }
        req.setType(Constants.NEWS_TYPE_NEWS);
        res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
        req.setTotolRows(res.getCount());
        model.put("req", req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        return channel + "/platform/about/media";
    }
	
	/**
     * 媒体报道详情
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/about/media_detail/index")
    public String newsInfoDetail(@PathVariable String channel, ReqMsg_News_Details req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
        req.setType(Constants.NEWS_TYPE_NEWS);
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            String content = (String) res.getDetails().get("content");
            String dest = content.replaceAll("&lt;", "<");
            dest = dest.replaceAll("&gt;", ">");
            
            String resources = GlobEnv.get("news.resources");
            String manageWeb = GlobEnv.get("manage.web");
            String web = GlobEnv.get("gen.server.web");
            dest = dest.replaceAll(manageWeb, "");
            
            // 替换emoj表情URL
            String[] a = dest.split(resources);
            int i = 0;
            StringBuffer contentBuf = new StringBuffer();
            contentBuf.append(a[0]);
            for (String string : a) {
                if(i > 0) {
                    contentBuf.append(web);
                    contentBuf.append(resources);
                    contentBuf.append(string);
                }
                i++;
            }
            res.getDetails().put("content", contentBuf.toString());
            model.put("details", res.getDetails());
            return channel + "/platform/about/media_detail";
        } else {
            model.put("url", "platform/about/media/index");
            model.put("errMsg", "不存在此媒体报道");
            model.put("titleName", "媒体报道");
            return channel + "/platform/help_error";
        }
        
    }
	
	/**
	 * 荣誉资质
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/honor/index")
	public String honor(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/about/honor";
	}
	
	/**
	 * 合作伙伴
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/partner/index")
	public String partner(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/about/partner";
	}
	
	/**
	 * 联系我们
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/about/contact_us/index")
	public String contactUs(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/about/contact_us";
	}
	
	/** 关于我们模块结束 **/
	
	
	/** 帮助中心模块开始 **/
	
	/**
	 * 常见问题
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/help/common_problem/index")
	public String commonProblem(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response) {
		return channel + "/platform/help/common_problem";
	}
	
	/** 帮助中心模块结束**/
	
	/**
     * 
     * 港湾资讯列表（公司动态||新闻资讯||湾粉活动）
     * @param channel
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gw_info/index")
    public String gwInfo(@PathVariable String channel,Map<String, Object> model, ReqMsg_News_QueryNewsPage req,
                         HttpServletRequest request, HttpServletResponse response) {
        req.setReceiverType(this.recieverType(channel, request, model));
        //改版港湾资讯 分类别查询
        req.setNumPerPage(20);
        if(!StringUtil.isNotEmpty(req.getType())) {
            req.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
        }else {
            req.setType(request.getParameter("type"));
        }
        ResMsg_News_QueryNewsPage res = (ResMsg_News_QueryNewsPage) communicateBusiness.handleMsg(req);
        model.put("dataGrid", res.getDataGrid());
        req.setTotolRows(res.getCount());
        model.put("count", req.getTotalPages());
        model.put("req", req);
        if(StringUtil.isNotBlank(request.getParameter("page")) && "page".equals(request.getParameter("page"))) {
            return channel+"/platform/info/gw_info_page";
        }
        return channel+"/platform/info/gw_info";
    }


    private String recieverType(String channel, HttpServletRequest request, Map<String, Object> model) {
        CookieManager manager = new CookieManager(request);
        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        if(Constants.CHANNEL_GEN.equals(channel)) {
            return Constants.NEWS_RECEIVER_TYPE_BGW;
        } else {
            if(Constants.CHANNEL_MICRO.equals(channel) && Constants.USER_AGENT_QIANBAO.equals(request.getParameter("qianbao"))) {
                model.put(Constants.USER_AGENT_QIANBAO, Constants.USER_AGENT_QIANBAO);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    try {
                        if(Constants.AGENT_ID_QD.equals(Integer.valueOf(viewAgentFlagCookie))) {
                            // 七店
                            return Constants.NEWS_RECEIVER_TYPE_BGWQD;
                        }else if(Constants.AGENT_VIEW_ID_QHD_JT.equals(viewAgentFlagCookie)) {
                            return Constants.NEWS_RECEIVER_TYPE_BGWQHDJT;
                        }else if(Constants.AGENT_VIEW_ID_QHD_XW.equals(viewAgentFlagCookie)) {
                            return Constants.NEWS_RECEIVER_TYPE_BGWQHDXW;
                        } else if(Constants.AGENT_VIEW_ID_QHD_TV.equals(viewAgentFlagCookie)) {
                            return Constants.NEWS_RECEIVER_TYPE_BGWQHDTV;
                        }else if(Constants.AGENT_VIEW_ID_QHD_SJC.equals(viewAgentFlagCookie)) {
                            return Constants.NEWS_RECEIVER_TYPE_BGWQHDSJC;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Constants.NEWS_RECEIVER_TYPE_BGW;
                    }
                }
            }
            if((Constants.CHANNEL_MICRO.equals(channel) && Constants.USER_AGENT_QIANBAO.equals(request.getParameter("qianbao")))
                    || Constants.CHANNEL_GEN_178.equals(channel)) {
                model.put(Constants.USER_AGENT_QIANBAO, Constants.USER_AGENT_QIANBAO);
                if(Constants.AGENT_ID_QD.equals(viewAgentFlagCookie)) {
                    return Constants.NEWS_RECEIVER_TYPE_BGW178;
                } else if(Constants.AGENT_VIEW_ID_KQ.equals(viewAgentFlagCookie)) {
                    return Constants.NEWS_RECEIVER_TYPE_BGWKQ;

                } else if(Constants.AGENT_VIEW_ID_RUIAN.equals(viewAgentFlagCookie)) {
                    return Constants.NEWS_RECEIVER_TYPE_BGWRUIAN;

                } else if(Constants.AGENT_VIEW_ID_HN.equals(viewAgentFlagCookie)) {
                    return Constants.NEWS_RECEIVER_TYPE_BGWHN;
                } else if(Constants.AGENT_VIEW_ID_QHD_ST.equals(viewAgentFlagCookie)) {
                    return Constants.NEWS_RECEIVER_TYPE_BGWQHDST;
                }
                 else {
                    return Constants.NEWS_RECEIVER_TYPE_BGW178;
                }
            } else {
                return Constants.NEWS_RECEIVER_TYPE_BGW;
            }
        }
    }
    /**
     * 港湾（公司动态||新闻资讯||湾粉活动）内容
     * @param channel
     * @param model
     * @param req
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gw_info/detail")
    public String gwInfoDetail(@PathVariable String channel,Map<String, Object> model, ReqMsg_News_Details req,
                         HttpServletRequest request, HttpServletResponse response) {
        req.setReceiverType(this.recieverType(channel, request, model));
        ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
        String content = (String) res.getDetails().get("content");
        String dest = content.replaceAll("&lt;", "<");
        dest = dest.replaceAll("&gt;", ">");
        
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
        dest = dest.replaceAll(manageWeb, "");
        
        // 替换emoj表情URL
        String[] a = dest.split(resources);
        int i = 0;
        StringBuffer contentBuf = new StringBuffer();
        contentBuf.append(a[0]);
        for (String string : a) {
            if(i > 0) {
                contentBuf.append(web);
                contentBuf.append(resources);
                contentBuf.append(string);
            }
            i++;
        }
        res.getDetails().put("content", contentBuf.toString());
        model.put("details", res.getDetails());
        model.put("type", req.getType());
        return channel+"/platform/info/gw_info_detail";
    }

    /**
     * APP港湾资讯、平台公告详情页
     * @param channel
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gw_info/detailApp")
    public String gwInfoDetailApp(@PathVariable String channel,Map<String, Object> model,
                         HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        Integer id = Integer.parseInt(request.getParameter("id"));
        String userIdStr = request.getParameter("userId");
        Integer userId = StringUtil.isNotBlank(userIdStr) ? Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr)) : null;
        String receiverType = Constants.NEWS_RECEIVER_TYPE_BGW;

        String content = "";
        HashMap<String, Object> details = new HashMap<>();
        if(Constants.POSITION_NOTICE.equals(type)) {
            //  公告
            ReqMsg_News_ReadMessage readReq = new ReqMsg_News_ReadMessage();
            readReq.setUserId(userId);
            readReq.setType(Constants.POSITION_NOTICE);
            readReq.setId(id);
            readReq.setReceiverType(receiverType);
            communicateBusiness.handleMsg(readReq);
            ReqMsg_News_NoticeDetails req = new ReqMsg_News_NoticeDetails();
            req.setId(id);
            ResMsg_News_NoticeDetails res = (ResMsg_News_NoticeDetails) communicateBusiness.handleMsg(req);
            content = (String) res.getDetails().get("content");
            details = res.getDetails();
        } else {
            ReqMsg_News_Details req = new ReqMsg_News_Details();
            req.setType(type);
            req.setId(id);
            req.setReceiverType(receiverType);
            req.setUserId(userId);
            ResMsg_News_Details res = (ResMsg_News_Details) communicateBusiness.handleMsg(req);
            content = (String) res.getDetails().get("content");
            details = res.getDetails();
        }

        String dest = content.replaceAll("&lt;", "<");
        dest = dest.replaceAll("&gt;", ">");

        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
        dest = dest.replaceAll(manageWeb, "");
        
        // 替换emoj表情URL
        String[] a = dest.split(resources);
        int i = 0;
        StringBuffer contentBuf = new StringBuffer();
        contentBuf.append(a[0]);
        for (String string : a) {
            if(i > 0) {
                contentBuf.append(web);
                contentBuf.append(resources);
                contentBuf.append(string);
            }
            i++;
        }
        details.put("content", contentBuf.toString());
        model.put("details", details);
        model.put("type", type);
        return channel+"/platform/info/gw_info_detail_app";
    }
    
    @RequestMapping("/newsDetail")
    public String newsDetail(@PathVariable String channel, ReqMsg_News_QueryBySource req,
                             HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> model) {
        if("gen178".equals(channel)) {
        } else if("gen2.0".equals(channel)) {
            req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
            ResMsg_News_QueryBySource res = (ResMsg_News_QueryBySource) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                if(null != res.getNews()) {
                    String content = (String) res.getNews().get("content");
                    String dest = content.replaceAll("&lt;", "<");
                    dest = dest.replaceAll("&gt;", ">");
                    String resources = GlobEnv.get("news.resources");
                    String manageWeb = GlobEnv.get("manage.web");
                    String web = GlobEnv.get("gen.server.web");
                    dest = dest.replaceAll(manageWeb, "");
                    
                    // 替换emoj表情URL
                    String[] a = dest.split(resources);
                    int i = 0;
                    StringBuffer contentBuf = new StringBuffer();
                    contentBuf.append(a[0]);
                    for (String string : a) {
                        if(i > 0) {
                            contentBuf.append(web);
                            contentBuf.append(resources);
                            contentBuf.append(string);
                        }
                        i++;
                    }
                    res.getNews().put("content", contentBuf.toString());
                    model.put("details", res.getNews());
                    return channel + "/platform/about/media_detail";
                } else {
                    return "redirect:about/media/index";
                }
            } else {
                return "redirect:about/media/index";
            }
        }
        return "redirect:about/media/index";
    }

    // 查询平台公告列表页
    @RequestMapping("/noticeInfo/index")
    public String noticeInfo(@PathVariable String channel,Map<String, Object> model, ReqMsg_News_QueryNoticeInfo req,
                         HttpServletRequest request, HttpServletResponse response) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);

        req.setReceiverType(this.recieverType(channel, request, model));
        req.setNumPerPage(20);
        ResMsg_News_QueryNoticeInfo res = (ResMsg_News_QueryNoticeInfo) communicateBusiness.handleMsg(req);
        model.put("dataGrid",res.getDataGrid());
        req.setTotolRows(res.getCount());
        model.put("count", req.getTotalPages());
        model.put("req", req);
        if(StringUtil.isNotBlank(request.getParameter("page")) && "page".equals(request.getParameter("page"))) {
            return channel+"/platform/info/notice_info_page";
        }
        return channel+"/platform/info/notice_info";
    }

    //查询公告详情页
    @RequestMapping("/notice_info/detail")
    public String noticeInfoDetail(@PathVariable String channel,Map<String, Object> model, ReqMsg_News_NoticeDetails req,
                               HttpServletRequest request, HttpServletResponse response) {

        if (Constants.CHANNEL_MICRO.equals(channel)) {
            ReqMsg_News_ReadMessage readReq = new ReqMsg_News_ReadMessage();
            CookieManager manager = new CookieManager(request);
            String userID = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            readReq.setUserId(StringUtils.isEmpty(userID) ? null : Integer.parseInt(userID));
            readReq.setType(Constants.POSITION_NOTICE);
            readReq.setId(req.getId());
            readReq.setReceiverType(this.recieverType(channel, request, model));
            communicateBusiness.handleMsg(readReq);
        }
        //首页公告详情页的入口标记
        String entrance = request.getParameter("entrance");
        model.put("entrance", entrance);
        ResMsg_News_NoticeDetails res = (ResMsg_News_NoticeDetails) communicateBusiness.handleMsg(req);
        String content = (String) res.getDetails().get("content");
        String dest = content.replaceAll("&lt;", "<");
        dest = dest.replaceAll("&gt;", ">");

        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
        dest = dest.replaceAll(manageWeb, "");

        // 替换emoj表情URL
        String[] a = dest.split(resources);
        int i = 0;
        StringBuffer contentBuf = new StringBuffer();
        contentBuf.append(a[0]);
        for (String string : a) {
            if(i > 0) {
                contentBuf.append(web);
                contentBuf.append(resources);
                contentBuf.append(string);
            }
            i++;
        }
        res.getDetails().put("content", contentBuf.toString());
        model.put("details", res.getDetails());
        return channel+"/platform/info/notice_info_detail";
    }

    //查询公告详情页-APP专用
    @Deprecated
    @RequestMapping("/notice_info/detailApp")
    public String noticeInfoDetailApp(@PathVariable String channel,Map<String, Object> model, ReqMsg_News_NoticeDetails req,
                                   HttpServletRequest request, HttpServletResponse response) {
        ResMsg_News_NoticeDetails res = (ResMsg_News_NoticeDetails) communicateBusiness.handleMsg(req);
        String content = (String) res.getDetails().get("content");
        String dest = content.replaceAll("&lt;", "<");
        dest = dest.replaceAll("&gt;", ">");

        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
        dest = dest.replaceAll(manageWeb, "");

        // 替换emoj表情URL
        String[] a = dest.split(resources);
        int i = 0;
        StringBuffer contentBuf = new StringBuffer();
        contentBuf.append(a[0]);
        for (String string : a) {
            if(i > 0) {
                contentBuf.append(web);
                contentBuf.append(resources);
                contentBuf.append(string);
            }
            i++;
        }
        res.getDetails().put("content", contentBuf.toString());
        model.put("details", res.getDetails());
        return channel+"/platform/info/notice_info_detail_app";
    }

    /**
     * 活动中心
     */
    @RequestMapping("/activityCenter/activityCenter_index")
    public String activityInfo(@PathVariable String channel,Map<String, Object> model, ReqMsg_AppActive_QueryActivePage req,
                         HttpServletRequest request, HttpServletResponse response) {

        req.setShowTerminal(Constants.APP_ACTIVE_SHOW_TERMINAL_BGW_H5);
        req.setNumPerPage(20);

        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        model.put("userId", StringUtils.isEmpty(userId) ? null : userId);

        //首页活动中心的入口标记
        String activityEntrance = request.getParameter("activityEntrance");
        model.put("activityEntrance", activityEntrance);

        ResMsg_AppActive_QueryActivePage res = (ResMsg_AppActive_QueryActivePage) communicateBusiness.handleMsg(req);
        model.put("dataGrid", res.getDataGrid());
        req.setTotolRows(res.getCount());
        model.put("count", req.getTotalPages());
        model.put("req", req);
        if(StringUtil.isNotBlank(request.getParameter("page")) && "page".equals(request.getParameter("page"))) {
            return channel+"/platform/info/activityCenter_index_info";
        }
        return channel+"/platform/info/activityCenter_index";
    }

    @ResponseBody
    @RequestMapping("/readMessage")
    public Map<String, Object> readMsg(@PathVariable String channel, ReqMsg_News_ReadMessage req, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (Constants.CHANNEL_MICRO.equals(channel)) {
            CookieManager manager = new CookieManager(request);
            String userID = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            req.setUserId(StringUtils.isEmpty(userID) ? null : Integer.parseInt(userID));
            // 登录的用户进入活动详情页，bs_user_message_read插入或更新阅读记录
            req.setUserId(StringUtils.isEmpty(userID) ? null : Integer.parseInt(userID));
            req.setType(Constants.POSITION_ACTIVITY);
            req.setId(req.getId());
            req.setReceiverType(this.recieverType(channel, request, result));
            ResMsg_News_ReadMessage res = (ResMsg_News_ReadMessage) communicateBusiness.handleMsg(req);
        }
        return result;
    }

    /**
     * 新增的所有平台数据的查询
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = URLConstant.PLATFORM_DATA)
    public Map<String, Object> platformData(@PathVariable Integer type) {
        Map<String, Object> result = new HashMap<>();
        ReqMsg_Invest_PlatformData req = new ReqMsg_Invest_PlatformData();
        req.setType(type);
        ResMsg_Invest_PlatformData res = (ResMsg_Invest_PlatformData) communicateBusiness.handleMsg(req);
        result.put("amount", res.getAmount());
        result.put("times", res.getTimes());
        return result;
    }

    public static void main(String[] args) {
        String content = "&lt;span style=\"font-size:24px;\"&gt;&lt;img alt=\"微笑\" src=\"/manage/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/smile.gif\" /&gt;&lt;img alt=\"微笑\" src=\"/manage/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/smile.gif\" /&gt;&lt;img src=\"http://localhost:8084/site/resources/upload/news/pic/1456363990269.png\" alt=\"\" /&gt;飒飒飒飒撒大大&lt;/span&gt;";
        String[] a = content.split("/manage/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/");
        int i = 0;
        StringBuffer contentBuf = new StringBuffer();
        contentBuf.append(a[0]);
        for (String string : a) {
            if(i > 0) {
                contentBuf.append("http://localhost:8080/site/resources/dwz/external/dwz-ria/xheditor/xheditor_emot/default/"+string);
            }
            i++;
        }
        System.out.println(contentBuf);
    }
}
