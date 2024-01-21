package com.pinting.site.activity.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.hessian.site.message.ReqMsg_BannerConfig_GetBanner;
import com.pinting.business.hessian.site.message.ReqMsg_BannerConfig_GetBannerCount;
import com.pinting.business.hessian.site.message.ResMsg_Activity_SalaryIncreasePlan;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_GetBanner;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_GetBannerCount;
import com.pinting.core.util.DateUtil;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.WeChatShareUtil;

@Controller
public class SalaryIncreaseActivityController {

	private final static String CHANNEL_APP = "app";
    private final static String CHANNEL_H5 = "micro2.0";
    
    @Autowired
    private WeChatUtil weChatUtil;
    @Autowired
    private CommunicateBusiness communicateBusiness;
	
	private Logger log = LoggerFactory.getLogger(SalaryIncreaseActivityController.class);
	
	/**
	 * 加薪计划活动主页
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/{channel}/activity/salary_increase_plan")
	public String salaryIncreasePlan(@PathVariable String channel, HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
		if(CHANNEL_H5.equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/activity/salary_increase_plan");
            String shareTitle = "工资不够花，币港湾给你加薪啦！";
            String shareContent = "每月15日，来币港湾加薪，名额有限！机会就在你手上！";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        } else if(CHANNEL_APP.equals(channel)) {
            String client = request.getParameter("client");
            model.put("client", client);
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer date = cal.get(Calendar.DAY_OF_MONTH);
		log.info("========date:"+date+"==============");
		log.info("========constants.date:"+Constants.SALARY_INCREASE_DATE_15+"==============");
		ReqMsg_BannerConfig_GetBannerCount banerReq = new ReqMsg_BannerConfig_GetBannerCount();
		banerReq.setUrl(Constants.BANNER_URL_SALARY_INCREASE);
		ResMsg_BannerConfig_GetBannerCount bannerRes = (ResMsg_BannerConfig_GetBannerCount) communicateBusiness.handleMsg(banerReq);
		if (bannerRes.getCount() != 3) {
			log.info("==============加薪计划活动不存在，请联系运营人员==============");
			// url 链接不存在
			model.put("error_reason_info", "币港湾加薪计划活动正在维护，");
			model.put("description_info", "请您敬请期待加薪计划归来！");
			if (CHANNEL_APP.equals(channel)) {
				return "micro2.0/regular/url_not_find";
			}
			return channel+"/regular/url_not_find";
		}
		//加薪计划活动日为每月15号
		if (date < Constants.SALARY_INCREASE_DATE_15) {
			model.put("isStart", "not_start");//活动未开始
		} else if (date > Constants.SALARY_INCREASE_DATE_15) {
			model.put("isStart", "end");//活动结束
		} else {
			model.put("isStart", "start");//活动开始
		}
		
		return channel + "/activity/2018/SalaryIncreasePlan/salary_increase_plan";
	}
	
	/**
     * 异步查询加薪计划活动
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/salaryIncreasePlan/getNewData", method = RequestMethod.GET)
    public Map<String, Object> getNewData(@PathVariable String channel, HttpServletRequest request) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer date = cal.get(Calendar.DAY_OF_MONTH);
		Map<String, Object> model = new HashMap<>();
		if (date < Constants.SALARY_INCREASE_DATE_15) {
			model.put("moreThan10000List", null);//单笔年化出借额10000-50000 排行榜
			model.put("moreThan10000Quota", null);//单笔年化出借额10000-50000 剩余名额
			model.put("moreThan50000List", null);//单笔年化出借额50001-100000 排行榜
			model.put("moreThan50000Quota", null);//单笔年化出借额50001-100000 剩余名额
			model.put("moreThan100000List", null);//单笔年化出借额100001-500000 排行榜
			model.put("moreThan100000Quota", null);//单笔年化出借额100001-500000 剩余名额
			model.put("moreThan500000List", null);//单笔年化出借额500001以上  排行榜
			model.put("moreThan500000Quota", null);//单笔年化出借额500001以上 剩余名额
		} else {
			ReqMsg_Activity_SalaryIncreasePlan planReq = new ReqMsg_Activity_SalaryIncreasePlan();
			ResMsg_Activity_SalaryIncreasePlan planRes = (ResMsg_Activity_SalaryIncreasePlan) communicateBusiness.handleMsg(planReq);
			model.put("moreThan10000List", planRes.getMoreThan10000List());//单笔年化出借额10000-50000 排行榜
			model.put("moreThan10000Quota", planRes.getMoreThan10000Quota());//单笔年化出借额10000-50000 剩余名额
			model.put("moreThan50000List", planRes.getMoreThan50000List());//单笔年化出借额50001-100000 排行榜
			model.put("moreThan50000Quota", planRes.getMoreThan50000Quota());//单笔年化出借额50001-100000 剩余名额
			model.put("moreThan100000List", planRes.getMoreThan100000List());//单笔年化出借额100001-500000 排行榜
			model.put("moreThan100000Quota", planRes.getMoreThan100000Quota());//单笔年化出借额100001-500000 剩余名额
			model.put("moreThan500000List", planRes.getMoreThan500000List());//单笔年化出借额500001以上  排行榜
			model.put("moreThan500000Quota", planRes.getMoreThan500000Quota());//单笔年化出借额500001以上 剩余名额
		}
    	return model;
    }
}
