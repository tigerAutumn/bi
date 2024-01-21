package com.pinting.gateway.mobile.in.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ResMsg_ActiveUserRecord_AddRecord;
import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.AppIndexBusinessService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


public class ActiveUserRecordInterceptor extends HandlerInterceptorAdapter {

	private Logger log = LoggerFactory
			.getLogger(ActiveUserRecordInterceptor.class);
	
	@Autowired
	private AppIndexBusinessService appIndexBusinessService;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			//log.info("===========活跃用户记录开始===========");
			String request_json_str_ = request.getParameter("request_json_str_");
			StringBuffer burl = new StringBuffer(request.getPathInfo());
			String log_request_str = (StringUtils.isNotBlank(request_json_str_) && request_json_str_.length() > 512) ? request_json_str_.substring(0, 512) : request_json_str_;
			log.info("APP请求-"+burl+":"+ log_request_str);
			if (request_json_str_ != null) {
				JsonConfig config = new JsonConfig();
				config.registerJsonValueProcessor(Date.class,
						new JsonValueProcessorImpl());
				JSONObject jsonObject = JSONObject.fromObject(request_json_str_, config);
				
				Object appVersion = jsonObject.get("appVersion");
				if (appVersion == null || StringUtil.isEmpty((String)appVersion)) {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					return false;
				}
				
				
				String userId = "";
				try {
					userId = jsonObject.getString("userId");
				} catch (Exception e) {
				}
				if(StringUtils.isNotBlank(userId)){
					ReqMsg_ActiveUserRecord_AddRecord req = new ReqMsg_ActiveUserRecord_AddRecord();
					req.setUserId(Integer.valueOf(jsonObject.getString("userId")));
					req.setSrcUrl(burl.toString());
					//log.info("===========添加活跃用户记录，用户id："+req.getUserId()+"===========");
					ResMsg_ActiveUserRecord_AddRecord res = appIndexBusinessService.addRecord(req);
				}
			}else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("===========添加活跃用户记录出错===========");
		}
		
		return true;
	}

}
