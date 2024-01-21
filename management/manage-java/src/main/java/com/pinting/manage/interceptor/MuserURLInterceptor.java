package com.pinting.manage.interceptor;

import com.pinting.business.model.BsUser;
import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MUserOpRecordService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MuserURLInterceptor extends HandlerInterceptorAdapter {
	
private Logger log = LoggerFactory.getLogger(MuserURLInterceptor.class);
	
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private MUserOpRecordService mUserOpRecordService;
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String userId = request.getParameter("userId");
		if(StringUtil.isNotBlank(userId)){
        	/*获取用户信息*/
        	BsUser user = bsUserService.findUserByUserId(Integer.parseInt(userId));
        	if(user != null){
        		/*管理用户操作登记表新增数据*/
        		MUserOpRecord record = new MUserOpRecord();
        		CookieManager cookie = new CookieManager(request);
    			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
    					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
    			if(StringUtils.isBlank(mUserId)){
    				log.warn("未获取到登录者信息");
    			}else{
    				record.setOpUserId(Integer.parseInt(mUserId));
					if(request.getServletPath().contains("/bank/statusModifyCard")){
						record.setFunctionName("银行卡解绑");
						record.setNote(null);
						record.setFunctionUrl("/bank/statusModifyCard");
						record.setOpContent("用户编号："+userId+ "，注册手机号："+user.getMobile());
					} else {
						record.setFunctionName("订单跟踪");
						record.setNote("单列单行获取");
						record.setFunctionUrl("/account/order/indexNormal/getUserInfo");
						record.setOpContent(user.getUserName()== null?"手机号："+user.getMobile()+"，姓名：":"手机号："+user.getMobile()+"，姓名："+user.getUserName());
					}

            		String ip = getIp(request);
            		record.setIp(ip);

            		mUserOpRecordService.addMUserOpRecord(record);
    			}
        	}
        }else{
        	String mobile = request.getParameter("userMobile");
        	String name = request.getParameter("userName");
        	if(StringUtil.isNotBlank(mobile) || StringUtil.isNotBlank(name)){
            	//管理用户操作登记表新增数据
        		MUserOpRecord record = new MUserOpRecord();
        		CookieManager cookie = new CookieManager(request);
    			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
    					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
    			if(StringUtils.isBlank(mUserId)){
    				
    			}else{
    				record.setOpUserId(Integer.parseInt(mUserId));
    				record.setFunctionName("订单跟踪");
    				record.setNote("按条件查询");
    				if(StringUtil.isNotBlank(mobile)){
            			record.setOpContent("手机号："+mobile);
            		}
        			if(StringUtil.isNotBlank(name)){
            			record.setOpContent(record.getOpContent()!=null?record.getOpContent()+"，姓名："+name:"姓名："+name);
            		}
            		
            		String ip = getIp(request);
            		record.setIp(ip);
            		record.setFunctionUrl("/account/order/indexNormal");
            		mUserOpRecordService.addMUserOpRecord(record);
    			}
            }
        }
		return true;
	}
	
	private String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");  
        if (log.isInfoEnabled()) {  
            log.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);  
        }  
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
                if (log.isInfoEnabled()) {  
                    log.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);  
                }  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
	}

}
