package com.pinting.site.common.interceptor;





import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * URL拦截器
 * 
 * @author linkin
 *
 */
public class URLInterceptor extends HandlerInterceptorAdapter {
	
	
	private Logger log = LoggerFactory.getLogger(URLInterceptor.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_SITE);
	@Autowired
	private CommunicateBusiness interceptorService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CookieManager cookie = new CookieManager(request);
  		String _account = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		/* 当Cookie被清除时，则直接跳转到登录页面 */
//		String burl = request.getRequestURL()+"?"+request.getQueryString();
  		StringBuffer burl = new StringBuffer(request.getServletPath());
  		if(StringUtil.isNotEmpty(request.getQueryString())) {
  			burl.append("?"+URLEncoder.encode(request.getQueryString(), "UTF-8"));
  		}
  		
  		String qianbao = request.getParameter("qianbao");
		/*String burl = request.getHeader("Referer");*/
		if (StringUtil.isEmpty(_account)) {
			if(request.getServletPath().contains("/gen2.0/") || request.getServletPath().contains("/pc/"))
			{
				//重定向url不为空时，追加url
				String rUrl = GlobEnv.get("gen.server.web")+"/gen2.0/user/login/index.htm";
				if(StringUtil.isNotEmpty(burl.toString())) {
					rUrl += "?burl="+burl;
				}
				if(StringUtil.isNotEmpty(burl.toString())) {
					//request.getSession().setAttribute("redirectUrl", burl.toString());
					cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
			        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
				}
				response.sendRedirect(rUrl);
			}else if (request.getServletPath().contains("/gen178/") || request.getServletPath().contains("/pc_a/")) {
				//重定向url不为空时，追加url
				String rUrl = GlobEnv.get("gen.server.web")+"/gen178/user/login/index.htm";
				if(StringUtil.isNotEmpty(burl.toString())) {
					rUrl += "?burl="+burl;
				}
				if(StringUtil.isNotEmpty(burl.toString())) {
					//request.getSession().setAttribute("redirectUrl", burl.toString());
					cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
			        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
				}
				response.sendRedirect(rUrl);
			}else{
			    // 下架摇一摇，跳转至落地页
			    if(request.getServletPath().contains("/shake/guide") || 
		            request.getServletPath().contains("/shake/shake") ||
		            request.getServletPath().contains("/shake/shakeSuccess")) {
			        response.sendRedirect(GlobEnv.getWebURL("/micro2.0/landing/landingPage318?flag=no_shake"));
                } else {
                    // 重定向url不为空时，追加url
                    String rUrl = "/micro2.0/user/login/index";
                    if(StringUtil.isNotBlank(qianbao)) {
                        rUrl += "?qianbao=qianbao";
                    }
                    if(StringUtil.isNotEmpty(burl.toString())) {
                        if(StringUtil.isNotBlank(qianbao)) {
                            rUrl += "&burl="+burl;
                        } else {
                            rUrl += "?burl="+burl;
                        }
                    }
                    if(StringUtil.isNotEmpty(burl.toString()) && !"/micro2.0/profile/index".equals(burl.toString())) {
                        //request.getSession().setAttribute("redirectUrl", burl.toString());
                        cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
    			        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
                    }
                    response.sendRedirect(GlobEnv.getWebURL(rUrl));
                }
			}
			return false;
		}else	
		{
			CookieManager manager = new CookieManager(request);
			try{
				String userId = manager.getValue(CookieEnums._SITE.getName(), 
						CookieEnums._SITE_USER_ID.getName(), true);
				ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
				req.setUserId(Integer.parseInt(userId));
				ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery) interceptorService.handleMsg(req);
				
				//活跃用户记录
				ReqMsg_ActiveUserRecord_AddRecord ActiveUserReq = new ReqMsg_ActiveUserRecord_AddRecord();
				ActiveUserReq.setUserId(Integer.parseInt(userId));
				
				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
					if(request.getServletPath().contains("/gen2.0/") || request.getServletPath().contains("/pc/"))
					{
						//重定向url不为空时，追加url
						String rUrl = GlobEnv.get("gen.server.web")+"/gen2.0/user/login/index.htm";
						if(StringUtil.isNotEmpty(burl.toString())) {
							rUrl += "?burl="+burl;
						}
						if(StringUtil.isNotEmpty(burl.toString())) {
							//request.getSession().setAttribute("redirectUrl", burl.toString());
							cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
					        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
						}
						response.sendRedirect(rUrl);
					}else if (request.getServletPath().contains("/gen178/") || request.getServletPath().contains("/pc_a/")) {
						//重定向url不为空时，追加url
						String rUrl = GlobEnv.get("gen.server.web")+"/gen178/user/login/index.htm";
						if(StringUtil.isNotEmpty(burl.toString())) {
							rUrl += "?burl="+burl;
						}
						if(StringUtil.isNotEmpty(burl.toString())) {
							//request.getSession().setAttribute("redirectUrl", burl.toString());
							cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
							cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
						}
						response.sendRedirect(rUrl);
					}else{
					    // 下架摇一摇，跳转至落地页
		                if(request.getServletPath().contains("/shake/guide") || 
		                    request.getServletPath().contains("/shake/shake") ||
		                    request.getServletPath().contains("/shake/shakeSuccess")) {
		                    response.sendRedirect(GlobEnv.getWebURL("/micro2.0/landing/landingPage318?flag=no_shake"));
		                } else {
		                    // 重定向url不为空时，追加url
	                        String rUrl = "/micro2.0/user/login/index";
	                        if(StringUtil.isNotEmpty(burl.toString())) {
	                            rUrl += "?burl="+burl;
	                        }
	                        if(StringUtil.isNotBlank(qianbao)){
	                            rUrl += "?qianbao=qianbao";
	                        }
	                        if(StringUtil.isNotEmpty(burl.toString())) {
	                            //request.getSession().setAttribute("redirectUrl", burl.toString());
	                            cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
	        			        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
	                        }
	                        response.sendRedirect(GlobEnv.getWebURL(rUrl));
		                }
					}
					
/*					if(request.getServletPath().contains("/gen2.0/"))
					{
						response.sendRedirect(GlobEnv.get("gen.server.web")+"/gen2.0/user/login/index.htm?"+"burl="+burl);
					
					}else{
						response.sendRedirect(GlobEnv.getWebURL("/micro2.0/user/login/index?"+"burl="+burl));
					}
*/					return false;
				}else{
					ActiveUserReq.setSrcUrl(burl.toString());
					if(request.getServletPath().contains("/gen2.0/") || request.getServletPath().contains("/gen178/")
							|| request.getServletPath().contains("/pc/") || request.getServletPath().contains("/pc_a/")){
						ActiveUserReq.setTerminalType("PC");
					}else if(request.getServletPath().contains("/micro2.0/") || request.getServletPath().contains("/h5/")){
						ActiveUserReq.setTerminalType("H5");
					}
					ResMsg_ActiveUserRecord_AddRecord ActiveUserRes =  (ResMsg_ActiveUserRecord_AddRecord)interceptorService.handleMsg(ActiveUserReq);
				}
			}catch(Exception e){
				e.printStackTrace();
				if(request.getServletPath().contains("/gen2.0/") || request.getServletPath().contains("/pc/"))
				{
					//重定向url不为空时，追加url
					String rUrl = GlobEnv.get("gen.server.web")+"/gen2.0/user/login/index.htm";
					if(StringUtil.isNotEmpty(burl.toString())) {
						rUrl += "?burl="+burl;
					}
					if(StringUtil.isNotEmpty(burl.toString())) {
						//request.getSession().setAttribute("redirectUrl", burl.toString());
						cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
				        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
					}
					response.sendRedirect(rUrl);
				}else if (request.getServletPath().contains("/gen178/") || request.getServletPath().contains("/pc_a/")) {
					//重定向url不为空时，追加url
					String rUrl = GlobEnv.get("gen.server.web")+"/gen178/user/login/index.htm";
					if(StringUtil.isNotEmpty(burl.toString())) {
						rUrl += "?burl="+burl;
					}
					if(StringUtil.isNotEmpty(burl.toString())) {
						//request.getSession().setAttribute("redirectUrl", burl.toString());
						cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
				        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
					}
					response.sendRedirect(rUrl);
				}else{
				    // 下架摇一摇，跳转至落地页
                    if(request.getServletPath().contains("/shake/guide") || 
                        request.getServletPath().contains("/shake/shake") ||
                        request.getServletPath().contains("/shake/shakeSuccess")) {
                        response.sendRedirect(GlobEnv.getWebURL("/micro2.0/landing/landingPage318?flag=no_shake"));
                    } else {
                        //重定向url不为空时，追加url
                        String rUrl = "/micro2.0/user/login/index";
                        if(StringUtil.isNotEmpty(burl.toString())) {
                            //request.getSession().setAttribute("redirectUrl", burl.toString());
                        	cookie.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl.toString(), true);
        			        cookie.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
                        }
                        response.sendRedirect(GlobEnv.getWebURL(rUrl));
                    }
				}
				return false;
			}
			if(request.getServletPath().contains("/gen2.0/") || request.getServletPath().contains("/gen178/") 
					|| request.getServletPath().contains("/pc/") || request.getServletPath().contains("/pc_a/"))
			{
				manager.save(response, CookieEnums._SITE.getName(), null, "/",  -1, true);
			}else{
			    manager.save(response, CookieEnums._SITE.getName(), null, "/",  5*365*24*3600, true);
			    // 下架摇一摇，跳转至落地页
                if(request.getServletPath().contains("/shake/guide") || 
                    request.getServletPath().contains("/shake/shake") ||
                    request.getServletPath().contains("/shake/shakeSuccess")) {
                    response.sendRedirect(GlobEnv.getWebURL("/micro2.0/landing/landingPage318?flag=no_shake"));
                }
			}
		}
		
		//token 注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    //request.getSession().setAttribute("token", UUID.randomUUID().toString());//分布式时需替换
					Util.createToken(request, response);
				}
                boolean needRemoveSession = annotation.remove();
                /*if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    request.getSession().removeAttribute("token");
                }*/
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
		
	}
	
	@Deprecated
	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession().getAttribute("token");
	    if (serverToken == null) {
	        return true;
	    }
	    String clinetToken = request.getParameter("token");
	    if (clinetToken == null) {
	        return true;
	    }
	    if (!serverToken.equals(clinetToken)) {
	        return true;
	    }
	    return false;
	}
}