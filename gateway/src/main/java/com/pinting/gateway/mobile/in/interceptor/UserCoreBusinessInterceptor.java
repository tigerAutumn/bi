package com.pinting.gateway.mobile.in.interceptor;

import java.util.Date;

import com.pinting.business.hessian.site.message.ReqMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ReqMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.hessian.site.message.ResMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ResMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.UserMainOperationService;
import com.pinting.gateway.mobile.in.util.IpUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:      cyb
 * Date:        2016/11/3
 * Description: 币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额）
 */
public class UserCoreBusinessInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(UserCoreBusinessInterceptor.class);

    @Autowired
    private UserMainOperationService userMainOperationService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            logger.info("APP币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额）" );
            String ip = IpUtil.getClientIp(request);
            String srcAgent = request.getHeader("user-agent");
            StringBuffer url = new StringBuffer(request.getRequestURL());
            String referer = request.getHeader("Referer");
            if(StringUtil.isNotEmpty(request.getQueryString())) {
                url.append("?"+request.getQueryString());
            }
            //获取userId或者mobile
            String userId = "";
            String mobile = "";
			String request_json_str_ = request.getParameter("request_json_str_");
			if (request_json_str_ != null) {
				JsonConfig config = new JsonConfig();
				config.registerJsonValueProcessor(Date.class,
						new JsonValueProcessorImpl());
				JSONObject jsonObject = JSONObject.fromObject(request_json_str_, config);
				
				try {
					userId = jsonObject.getString("userId");
				} catch (Exception e) {
				}
				
				try {
					mobile = jsonObject.getString("mobile");
				} catch (Exception e) {
				}
			}
            
            ReqMsg_UserMainOperation_UserMainOperationAdd req = new ReqMsg_UserMainOperation_UserMainOperationAdd();
            req.setUserId(StringUtil.isBlank(userId) ? null : Integer.parseInt(userId));
            req.setMobile(StringUtil.isBlank(mobile) ? null : mobile);
            req.setReferer(referer);
            req.setSrcAgent(srcAgent);
            req.setSrcIp(ip);
            req.setUrl(url.toString());
            req.setVersion("1.0.0");
            logger.info("APP币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额），请求参数：{}", JSONObject.fromObject(req));
            ResMsg_UserMainOperation_UserMainOperationAdd res = userMainOperationService.saveUserMainOperation(req);
            logger.info("APP币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额），响应参数：{}", JSONObject.fromObject(res));
        } catch (Exception e) {
            logger.info("APP币港湾用户核心业务拦截（登录、修改密码、绑卡、充值、提现、购买、奖励金转余额）异常" );
            e.printStackTrace();
        }
    }

}
