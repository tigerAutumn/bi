package com.pinting.site.common.interceptor;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.hessian.site.message.ReqMsg_AutoRedPacket_RedPacketSend;
import com.pinting.business.hessian.site.message.ResMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.hessian.site.message.ResMsg_AutoRedPacket_RedPacketSend;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

public class PayAdsInterceptor extends HandlerInterceptorAdapter {
	private static Logger log = LoggerFactory.getLogger(PayAdsInterceptor.class);
	
	@Autowired
	private CommunicateBusiness interceptorService;

	@SuppressWarnings("unchecked")
	@Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
		Map<String, Object> model = (Map<String, Object>)request.getAttribute("resultMap");
		
		//注册提交
        if (model != null && "payAds".equals(model.get("payAds"))) {
			CookieManager manager = new CookieManager(request);
			String sourceString= manager.getValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_RESOURCE.getName(), true);
			String mediumString= manager.getValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_MEDIUM.getName(), true);
			String termString= manager.getValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_TERM.getName(), true);
			String contentString= manager.getValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_CONTENT.getName(), true);
			String campaignString= manager.getValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_CAMPAIGN.getName(), true);
    		//注册提交成功判断
    		if(sourceString !=null && !"".equals(sourceString) &&mediumString !=null && !"".equals(mediumString) && campaignString !=null && !"".equals(campaignString) && ConstantUtil.RESCODE_SUCCESS.equals(model.get("bsCode").toString())) {

    			ReqMsg_AdEffect_AdEffectSaveInfo req= new ReqMsg_AdEffect_AdEffectSaveInfo();
    			req.setUrl(request.getHeader("referer"));
    			req.setMonitorType("REGISTER");
    			req.setVisitTime(new Date());
    			req.setRegMobile((String)model.get("mobile"));
    			req.setUtmSource(sourceString);
    			req.setUtmMedium(mediumString);
    			req.setUtmTerm(termString);
    			req.setUtmContent(contentString);
    			req.setUtmCampaign(campaignString);
    			req.setCreateTime(new Date());
    			ResMsg_AdEffect_AdEffectSaveInfo res = (ResMsg_AdEffect_AdEffectSaveInfo)interceptorService.handleMsg(req);
    			
    		}
		}else {
			//落地页面进来
			String utm_source = request.getParameter("utm_source");
            String utm_medium = request.getParameter("utm_medium");
            String utm_term = request.getParameter("utm_term");
            String utm_content = request.getParameter("utm_content");
            String utm_campaign = request.getParameter("utm_campaign");
           
            if (utm_source != null && !"".equals(utm_source) && utm_medium != null && !"".equals(utm_medium) && utm_campaign != null && !"".equals(utm_campaign)) {
            	log.info("付费广告页面参数：【utm_source="+utm_source+",utm_medium"+utm_medium+",utm_term"+utm_term+",utm_content"+utm_content+",utm_campaign"+utm_campaign);
            	CookieManager manager = new CookieManager(request);
        		manager.setValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_RESOURCE.getName(),
        				utm_source, true);
        		manager.setValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_MEDIUM.getName(),
        				utm_medium, true);
        		manager.setValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_TERM.getName(),
        				utm_term, true);
        		manager.setValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_CONTENT.getName(),
        				utm_content, true);
        		manager.setValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_CAMPAIGN.getName(),
        				utm_campaign, true);
        		manager.save(response, CookieEnums._ADS.getName(), null, "/",  -1, true);
        		
        		String sourceString= manager.getValue(CookieEnums._ADS.getName(), CookieEnums._ADS_EFFECT_UTM_RESOURCE.getName(), true);
            	
            	ReqMsg_AdEffect_AdEffectSaveInfo req= new ReqMsg_AdEffect_AdEffectSaveInfo();
    			req.setUrl(request.getRequestURL().toString());
    			req.setMonitorType("BROWSER");
    			req.setVisitTime(new Date());
    			req.setRegMobile(null);
    			req.setUtmSource(utm_source);
    			req.setUtmMedium(utm_medium);
    			req.setUtmTerm(utm_term);
    			req.setUtmContent(utm_content);
    			req.setUtmCampaign(utm_campaign);
    			req.setCreateTime(new Date());
    			ResMsg_AdEffect_AdEffectSaveInfo res = (ResMsg_AdEffect_AdEffectSaveInfo)interceptorService.handleMsg(req);
            }
		}

    }  
}
