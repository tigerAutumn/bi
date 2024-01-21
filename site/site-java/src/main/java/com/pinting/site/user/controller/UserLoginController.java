package com.pinting.site.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pinting.business.hessian.site.message.ReqMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ReqMsg_Product_FindProductUserTypeAuthById;
import com.pinting.business.hessian.site.message.ReqMsg_User_BindUserCallBack;
import com.pinting.business.hessian.site.message.ReqMsg_User_CheckMobile;
import com.pinting.business.hessian.site.message.ReqMsg_User_CsaiRegister;
import com.pinting.business.hessian.site.message.ReqMsg_User_FindUserByMobile;
import com.pinting.business.hessian.site.message.ReqMsg_User_Login;
import com.pinting.business.hessian.site.message.ReqMsg_User_PushP2p;
import com.pinting.business.hessian.site.message.ResMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ResMsg_Product_FindProductUserTypeAuthById;
import com.pinting.business.hessian.site.message.ResMsg_User_BindUserCallBack;
import com.pinting.business.hessian.site.message.ResMsg_User_CheckMobile;
import com.pinting.business.hessian.site.message.ResMsg_User_CsaiRegister;
import com.pinting.business.hessian.site.message.ResMsg_User_FindUserByMobile;
import com.pinting.business.hessian.site.message.ResMsg_User_Login;
import com.pinting.business.hessian.site.message.ResMsg_User_PushP2p;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.csai.apiconfig.CsaiConfig;
import com.pinting.csai.util.DesUtil;
import com.pinting.csai.util.UrlParmHelper;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.PreURLInterceptor;
import com.pinting.site.common.session.SessionService;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.BannerUtil;
import com.pinting.util.ClientMacAddrUtil;
import com.pinting.util.Constants;
import com.pinting.util.MobileCheckUtil;

@Controller
public class UserLoginController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	// 本地sqlmap测试
    @Autowired
    private CommunicateBusiness siteService;
    @Autowired
	private WeChatUtil weChatUtil;
	@Autowired
	private SessionService sessionService;

    String                      url = "";

    /**
     * 登录界面
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/user/login/index")
    public String loginInit(@PathVariable String channel, HttpServletRequest request,
                            HttpServletResponse response, Map<String, Object> model) {
        if (request.getParameter("nick") != null) {
            String nick = request.getParameter("nick");
            model.put("NICK", nick);
        }
        
        String burl = request.getParameter("burl");
        if(StringUtils.isNotBlank(burl)){
        	//request.getSession().setAttribute("redirectUrl", burl);
        	CookieManager cookieManager = new CookieManager(request);
            cookieManager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), burl, true);
            cookieManager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", 5*3600*24, true);
        }

		List<HashMap<String, Object>> bannerList = BannerUtil.queryBannerList(siteService, channel, Constants.SHOW_PAGE_LOGIN);
		if(!org.springframework.util.CollectionUtils.isEmpty(bannerList)) {
			model.put("banner", bannerList.get(0));
		}

        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的跳转参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
            
            // 分享
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
            return "qianbao178/user/login_index";
        }

        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        return channel + "/user/login_index";
    }
    
    /**
     * 微信小程序登录界面
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/user/login/index")
    public String weChatLoginInit(HttpServletRequest request,
                            HttpServletResponse response, Map<String, Object> model) {
        if (request.getParameter("nick") != null) {
            String nick = request.getParameter("nick");
            model.put("NICK", nick);
        }
        return "weixin/user/login_index";
    }
    
    /**
     * 钱报登录入口
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/user/login_index_qianbao")
    public String loginIndex(@PathVariable String channel, HttpServletRequest request,
                            HttpServletResponse response, Map<String, Object> model) {
        url = request.getParameter("burl");
        if (request.getParameter("nick") != null) {
            String nick = request.getParameter("nick");
            model.put("NICK", nick);
        }
        model.put("burl", url);
        model.put("qianbao", "qianbao");
        String link = GlobEnv.getWebURL("/micro2.0/index")+"?qianbao=qianbao";
        CookieManager manager = new CookieManager(request);
        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
        	link += "&agentViewFlag="+viewAgentFlagCookie;
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        return "qianbao178/user/login_index";
    }

    /**
     * 用户登录
     * 
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/login")
    public @ResponseBody
    HashMap<String, Object> loginSubmit(@PathVariable String channel,ReqMsg_User_Login reqLogin, 
    		HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ResMsg_User_Login resLogin = (ResMsg_User_Login) siteService.handleMsg(reqLogin);
            
            // 钱报的跳转参数
            String qianbao = request.getParameter("qianbao");
            if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
//                if(StringUtil.isNotBlank(resLogin.getAgentId()) && Constants.USER_AGENT_QIANBAO.equals(resLogin.getAgentId())) {
                dataMap.put("qianbao", Constants.USER_AGENT_QIANBAO);
//                }
            } else {
                resLogin.setAgentId("");
            }
            
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resLogin.getResCode())) {
            	ReqMsg_ActiveUserRecord_AddRecord ActiveUserReq = new ReqMsg_ActiveUserRecord_AddRecord();
        		ActiveUserReq.setUserId(resLogin.getUserId());
        		ActiveUserReq.setSrcUrl("/"+channel+"/user/login");
        		try {
        			if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                		userCookieManagePC(request, response, dataMap, resLogin);
                		ActiveUserReq.setTerminalType("PC");
                		ResMsg_ActiveUserRecord_AddRecord ActiveUserRes =  (ResMsg_ActiveUserRecord_AddRecord) siteService.handleMsg(ActiveUserReq);
                    }else{
                		ActiveUserReq.setUserId(resLogin.getUserId());
                		ActiveUserReq.setTerminalType("H5");
                		ResMsg_ActiveUserRecord_AddRecord ActiveUserRes =  (ResMsg_ActiveUserRecord_AddRecord) siteService.handleMsg(ActiveUserReq);
                    	userCookieManage(request, response, dataMap, resLogin);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
                //String rUrl = (String)request.getSession().getAttribute("redirectUrl");
                
                CookieManager cookieManager = new CookieManager(request);
                String rUrl = cookieManager.getValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), true);
                
                if(StringUtil.isNotEmpty(rUrl)) {
                	dataMap.put("burl", rUrl);
                	//request.getSession().removeAttribute("redirectUrl");
                	cookieManager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), "", true);
                    cookieManager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
                }
                //返回其它 非公共信息 字段
                dataMap.put(ConsMsgUtil.DATA, resLogin.getExtendMap());
            } else {
                //公共信息字段返回
                errorRes(dataMap, resLogin);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            errorRes(dataMap, e);
        }

        return dataMap;
    }
    
    /**
     * 微信小程序用户登录
     * 
     * @param reqLogin
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("weixin/user/login")
    public @ResponseBody
    HashMap<String, Object> weChatLoginSubmit(ReqMsg_User_Login reqLogin, 
    		HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ResMsg_User_Login resLogin = (ResMsg_User_Login) siteService.handleMsg(reqLogin);
            
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resLogin.getResCode())) {
            	ReqMsg_ActiveUserRecord_AddRecord ActiveUserReq = new ReqMsg_ActiveUserRecord_AddRecord();
        		ActiveUserReq.setUserId(resLogin.getUserId());
        		ActiveUserReq.setSrcUrl("/weixin/login");
        		try {
                	ActiveUserReq.setTerminalType("WXAPP");
                	ResMsg_ActiveUserRecord_AddRecord ActiveUserRes =  (ResMsg_ActiveUserRecord_AddRecord) siteService.handleMsg(ActiveUserReq);
                	userCookieManage(request, response, dataMap, resLogin);
				} catch (Exception e) {
					e.printStackTrace();
				}
                //返回其它 非公共信息 字段
        		dataMap.put("userId", resLogin.getUserId());
                dataMap.put(ConsMsgUtil.DATA, resLogin.getExtendMap());
            } else {
                //公共信息字段返回
                errorRes(dataMap, resLogin);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            errorRes(dataMap, e);
        }

        return dataMap;
    }

    /**
     * 存PCcookie
     * @param request
     * @param response
     * @param dataMap
     * @param resLogin
     */
    private void userCookieManagePC(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> dataMap, ResMsg_User_Login resLogin) {
        CookieManager manager = new CookieManager(request);

        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(),
            String.valueOf(resLogin.getUserId()), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_NAME.getName(),
            resLogin.getNick(), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_TYPE.getName(),
            resLogin.getUserType(), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(),
            resLogin.getMobile(), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_AGENT_ID.getName(), resLogin.getAgentId(), true);
		// 实际的用户渠道ID
		if(resLogin.getRealAgentId() != null) {
			manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), String.valueOf(resLogin.getRealAgentId()), true);
		}
        //判断是否是钱报、七店相关的agentid，是则存cookie
		if(null != resLogin.getRealAgentId()){
			List<Integer> agentIdList = PreURLInterceptor.agentIdList;
			if(CollectionUtils.isNotEmpty(agentIdList)){
				for (Integer integer : agentIdList) {
					if(resLogin.getRealAgentId() == integer){
						manager.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(),integer.toString() , true);
						manager.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
					}
				}
			}
		}
        manager.save(response, CookieEnums._SITE.getName(), null, "/", -1, true);
        successRes(dataMap);
    }
    
    /**
     * 存微网cookie
     * @param request
     * @param response
     * @param dataMap
     * @param resLogin
     */
    private void userCookieManage(HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> dataMap, ResMsg_User_Login resLogin) {
		CookieManager manager = new CookieManager(request);
		
		manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(),
		String.valueOf(resLogin.getUserId()), true);
		manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_NAME.getName(),
		resLogin.getNick(), true);
		manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_TYPE.getName(),
		resLogin.getUserType(), true);
		manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(),
		resLogin.getMobile(), true);
		manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_AGENT_ID.getName(), resLogin.getAgentId(), true);
		// 实际的用户渠道ID
		if(resLogin.getRealAgentId() != null) {
			manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), String.valueOf(resLogin.getRealAgentId()), true);
		}
		//判断是否是钱报、七店相关的agentid，是则存cookie
		if(null != resLogin.getRealAgentId()){
			List<Integer> agentIdList = PreURLInterceptor.agentIdList;
			if(CollectionUtils.isNotEmpty(agentIdList)){
				for (Integer integer : agentIdList) {
					if(resLogin.getRealAgentId() == integer){
						manager.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(),integer.toString() , true);
						manager.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
					}
				}
			}
		}
		manager.save(response, CookieEnums._SITE.getName(), null, "/", -1, true);
		successRes(dataMap);

	}
    
    /**
     * 是否处于登录状态
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/user/login/isInLogin")
    public Map<String, Object> isInLogin(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId))
            result.put("isInLogin", false);
        else
            result.put("isInLogin", true);
        return result;
    }
    
    /**
     * 检查手机是否已注册
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/user/login/mobileRegisted")
    public Map<String, Object> mobileRegisted(@PathVariable String channel, ReqMsg_User_CheckMobile req, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String mobile = req.getMobile();
        
        boolean isMobile = MobileCheckUtil.isMobile(mobile);
        if(isMobile) {
            ResMsg_User_CheckMobile res = (ResMsg_User_CheckMobile)siteService.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                result.put("isRegistered", res.getIsRegister());
                result.put("mobile", mobile);
                successRes(result);
            } else {
                errorRes(result);
            }
        } else {
            errorRes(result);
            result.put("resMsg", "请输入正确的手机号");
        }
        return result;
    }

    @RequestMapping("/user/test")
    public ModelAndView loginTest(String param, HttpServletRequest request,
                                  HttpServletResponse response) {
        request.setAttribute("msg", "你好junit");
        return new ModelAndView("/index.jsp");

        // return "forward:/order/add"
        // return "redirect:/index.jsp"
        // return "path:/index.jsp"
    }
    
    /**
     * 希财登录入口
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("/user/login_index_csai")
    public String loginIndexCsai(HttpServletRequest request,
                            HttpServletResponse response, Map<String, Object> model) throws IOException {
    	//检查客户端IP地址
    	String ipAddr = ClientMacAddrUtil.getIpAddr(request);
    	logger.info("希财自动登录接口-请求来源IP地址：【"+ipAddr+"】");
    	//String ipAddr = "210.73.220.214";
/*    	if (!Constants.CSAI_AUTO_LOGIN_IP_ADDR1.equals(ipAddr) && !Constants.CSAI_AUTO_LOGIN_IP_ADDR2.equals(ipAddr)&& !Constants.CSAI_AUTO_LOGIN_IP_ADDR3.equals(ipAddr)) {
			return null;
		}*/
    	
    	//进行加密参数解密
    	
    	String sign =  request.getParameter("sign");
    	String result = "";
    	try
    	{
    		result = DesUtil.decrypt(sign, CsaiConfig.client_secret.substring(0,8));
    	}
    	catch(Exception ex)
    	{
    		response.getWriter().write("解密失败"+ex.getMessage());
    	}
    	
    	//result ="phone=15868157902&name=test&pid=15&t=123456789&display=pc";  
    	Map<String,String> keyvalue = UrlParmHelper.Split(result);
    	String phone = keyvalue.get("phone");  //用户手机号码
    	String name = keyvalue.get("name");  //用户昵称       对应nike
    	String pid = keyvalue.get("pid");  //产品ID
    	String t = keyvalue.get("t");
    	String display = keyvalue.get("display");  //客户端类型   pc
    	display = "pc";
    	logger.info("解密参数：【phone="+phone+"name="+name+"pid="+pid+"t="+t+"display="+display+"】" );
    	//校验参数是否正确
    	if (phone == null || pid == null || display == null) {
			return null;
		}
    	if (!Constants.CSAI_USER_DISPLAY_PC.equals(display)) {
			return null;
		}
    	//根据手机查询用户是否存在
    	ReqMsg_User_FindUserByMobile reqMsg_User_FindUserByMobile = new ReqMsg_User_FindUserByMobile();
    	reqMsg_User_FindUserByMobile.setMobile(phone);
    	ResMsg_User_FindUserByMobile resMsg_User_FindUserByMobile = (ResMsg_User_FindUserByMobile) siteService.handleMsg(reqMsg_User_FindUserByMobile);
    	
    	if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_User_FindUserByMobile.getResCode())) {
    		return null;
    	}
    	String genUrl = GlobEnv.get("gen.server.web");
    	if(resMsg_User_FindUserByMobile.getId() != null){ //检查用户是否存在
    		//检查用户是否是希财用户，如果是希财用户则进行登录操作
    		if (Constants.USER_TYPE_NORMAL.equals(resMsg_User_FindUserByMobile.getUserType()) && resMsg_User_FindUserByMobile.getAgentId()!=null && Constants.AGENT_CSAI_ID == resMsg_User_FindUserByMobile.getAgentId()) {
    			
    			//调用绑定回调接口
    	    	ReqMsg_User_BindUserCallBack bindUserCallBack = new ReqMsg_User_BindUserCallBack();
    	    	bindUserCallBack.setPhone(resMsg_User_FindUserByMobile.getMobile());
    	    	bindUserCallBack.setName(resMsg_User_FindUserByMobile.getNick());
    	    	bindUserCallBack.setResult(1);
    	    	bindUserCallBack.setDisplay(Constants.CSAI_USER_DISPLAY_PC);
    	    	ResMsg_User_BindUserCallBack resBindUserCallBack = (ResMsg_User_BindUserCallBack) siteService.handleMsg(bindUserCallBack);
    			
    			HashMap<String, Object> dataMap = new HashMap<String, Object>();
    			
    			ResMsg_User_Login resLogin = new ResMsg_User_Login();
    			resLogin.setUserId(resMsg_User_FindUserByMobile.getId());
    			resLogin.setNick(resMsg_User_FindUserByMobile.getNick());
    			resLogin.setUserType(resMsg_User_FindUserByMobile.getUserType());
    			resLogin.setMobile(resMsg_User_FindUserByMobile.getMobile());
    			resLogin.setAgentId(String.valueOf(resMsg_User_FindUserByMobile.getAgentId()));
    			userCookieManagePC(request, response, dataMap, resLogin);
    			
    			//检查pid是否合法，不合法跳转产品列表页面
    			ReqMsg_Product_FindProductUserTypeAuthById  reqMsg_Product_FindProductUserTypeAuthById  = new ReqMsg_Product_FindProductUserTypeAuthById();
    			reqMsg_Product_FindProductUserTypeAuthById.setId(Integer.parseInt(pid));
    			ResMsg_Product_FindProductUserTypeAuthById resMsg_Product_FindProductUserTypeAuthById = (ResMsg_Product_FindProductUserTypeAuthById) siteService.handleMsg(reqMsg_Product_FindProductUserTypeAuthById);
    			if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_Product_FindProductUserTypeAuthById.getResCode()) || resMsg_Product_FindProductUserTypeAuthById.getId() == null) {
    				return "redirect:"+genUrl+"/gen2.0/regular/list";
    	    	}
    			if (Constants.USER_TYPE_NORMAL.equals(resMsg_Product_FindProductUserTypeAuthById.getUserType())) {
    				return "redirect:"+genUrl+"/gen2.0/regular/index?id="+pid;
				}else {
					return "redirect:"+genUrl+"/gen2.0/regular/list";
				}
    			
			}else {
				return null ;
			}
    	}else {
    		//检查用户不存在，进行注册操作
    		ReqMsg_User_CsaiRegister reqMsg_User_CsaiRegister = new ReqMsg_User_CsaiRegister();
    		reqMsg_User_CsaiRegister.setMobile(phone);
    		reqMsg_User_CsaiRegister.setAgentId(Constants.AGENT_CSAI_ID);
    		ResMsg_User_CsaiRegister resMsg_User_CsaiRegister = (ResMsg_User_CsaiRegister) siteService.handleMsg(reqMsg_User_CsaiRegister);
        	if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_User_FindUserByMobile.getResCode())) {
        		//调用绑定回调接口
    	    	ReqMsg_User_BindUserCallBack bindUserCallBack = new ReqMsg_User_BindUserCallBack();
    	    	bindUserCallBack.setPhone(resMsg_User_CsaiRegister.getMobile());
    	    	bindUserCallBack.setName(resMsg_User_CsaiRegister.getNick());
    	    	bindUserCallBack.setResult(1);
    	    	bindUserCallBack.setDisplay(Constants.CSAI_USER_DISPLAY_PC);
    	    	ResMsg_User_BindUserCallBack resBindUserCallBack = (ResMsg_User_BindUserCallBack) siteService.handleMsg(bindUserCallBack);
        		
        		HashMap<String, Object> dataMap = new HashMap<String, Object>();
        		ResMsg_User_Login resLogin = new ResMsg_User_Login();
        		
    			resLogin.setUserId(resMsg_User_CsaiRegister.getUserId());
    			resLogin.setNick(resMsg_User_CsaiRegister.getNick());
    			resLogin.setUserType(resMsg_User_CsaiRegister.getUserType());
    			resLogin.setMobile(resMsg_User_CsaiRegister.getMobile());
    			resLogin.setAgentId(String.valueOf(resMsg_User_CsaiRegister.getAgentId()));
    			userCookieManagePC(request, response, dataMap, resLogin);
    			//检查pid是否合法，不合法跳转产品列表页面
    			ReqMsg_Product_FindProductUserTypeAuthById  reqMsg_Product_FindProductUserTypeAuthById  = new ReqMsg_Product_FindProductUserTypeAuthById();
    			reqMsg_Product_FindProductUserTypeAuthById.setId(Integer.parseInt(pid));
    			ResMsg_Product_FindProductUserTypeAuthById resMsg_Product_FindProductUserTypeAuthById = (ResMsg_Product_FindProductUserTypeAuthById) siteService.handleMsg(reqMsg_Product_FindProductUserTypeAuthById);
    			if (!ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_Product_FindProductUserTypeAuthById.getResCode()) || resMsg_Product_FindProductUserTypeAuthById.getId() == null) {
    				return "redirect:"+genUrl+"/gen2.0/regular/list";
    	    	}
    			if (Constants.USER_TYPE_NORMAL.equals(resMsg_Product_FindProductUserTypeAuthById.getUserType())) {
    				return "redirect:"+genUrl+"/gen2.0/regular/index?id="+pid;
				}else {
					return "redirect:"+genUrl+"/gen2.0/regular/list";
				}
        	}else {
        		return null;
			}
    		
		}
        
    }
    
    /**
     * 希财推送产品ID
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("/user/push_p2p")
    public String pushP2p(HttpServletRequest request,
                            HttpServletResponse response, Map<String, Object> model) throws IOException {
    	String pid =  request.getParameter("pid");
    	//检查客户端IP地址
/*    	String ipAddr = ClientMacAddrUtil.getIpAddr(request);
    	logger.info("希财推送产品ID接口-请求来源IP地址：【"+ipAddr+"】");
    	if (!Constants.CSAI_AUTO_LOGIN_IP_ADDR3.equals(ipAddr)) {
			return "非法请求！";
		}*/
    	//检查用户不存在，进行注册操作
		ReqMsg_User_PushP2p reqMsg_User_PushP2p = new ReqMsg_User_PushP2p();
		reqMsg_User_PushP2p.setPid(pid);
		ResMsg_User_PushP2p resMsg_User_PushP2p = (ResMsg_User_PushP2p) siteService.handleMsg(reqMsg_User_PushP2p);
    	return null ;
    }

}
