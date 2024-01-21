package com.pinting.site.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_User_AgentQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_FindPassword;
import com.pinting.business.hessian.site.message.ReqMsg_User_Login;
import com.pinting.business.hessian.site.message.ReqMsg_User_Regist;
import com.pinting.business.hessian.site.message.ResMsg_User_AgentQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_FindPassword;
import com.pinting.business.hessian.site.message.ResMsg_User_Login;
import com.pinting.business.hessian.site.message.ResMsg_User_Regist;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.Util;

@Controller
@Scope("prototype")
public class UserRegistController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
    private WeChatUtil weChatUtil;
	
	private final Logger logger = LoggerFactory.getLogger(UserRegistController.class);

	/**
	 * 忘记密码界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/forgetPassword")
	public String forgetPasswordInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response) {
		// 测试hessian是否连通
		// System.err.println(">>>>>>>>hessian测试>>>>>>>>>>>>>"+basicService.hello()+"<<<<<<<hessian测试<<<<<<<<<");
		// return channel + "/user/user_login";
		return channel + "/user/forget_password";
	}

	/**
	 * 忘记密码修改密码界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/changePassword")
	public String changePasswordInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> data) {
		data.put("mobile", request.getParameter("mobile"));
		return channel + "/user/change_password";
	}

	/**
	 * 注册界面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/regist/index")
	public String userLoginInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response) {
		removeCodeCookieManage(request, response,
				new HashMap<String, Object>(),
				CookieEnums._SITE_MOBILE_CODE.getName());
		// 测试hessian是否连通
		// System.err.println(">>>>>>>>hessian测试>>>>>>>>>>>>>"+basicService.hello()+"<<<<<<<hessian测试<<<<<<<<<");
		// return channel + "/user/user_login";
		return channel + "/user/Mobile_regist";
	}

	/**
	 * 注册界面2
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/regist/passwordRegist")
	public String passwordRegistInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> data) {
		// removeCodeCookieManage(request, response, new HashMap<String,
		// Object>(), CookieEnums._SITE_MOBILE_CODE.getName());
		// 测试hessian是否连通
		// System.err.println(">>>>>>>>hessian测试>>>>>>>>>>>>>"+basicService.hello()+"<<<<<<<hessian测试<<<<<<<<<");
		// return channel + "/user/user_login";

		data.put("mobile", request.getParameter("mobile"));
		data.put("agentId", request.getParameter("agentId"));

		return channel + "/user/password_regist";
	}

	/**
	 * 注册界面3
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/regist/userinfoRegist")
	public String userinfoRegistInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> data) {
		// removeCodeCookieManage(request, response, new HashMap<String,
		// Object>(), CookieEnums._SITE_MOBILE_CODE.getName());
		// 测试hessian是否连通
		// System.err.println(">>>>>>>>hessian测试>>>>>>>>>>>>>"+basicService.hello()+"<<<<<<<hessian测试<<<<<<<<<");
		// return channel + "/user/user_login";
		data.put("mobileCode", request.getParameter("mobileCode"));
		data.put("mobile", request.getParameter("mobile"));
		data.put("userId", request.getParameter("userId"));
		data.put("nick", request.getParameter("nick"));
		return channel + "/user/userinfo_regist";
	}

	/**
	 * 用户注册协议
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/regist/agreementRegist")
	public String agreementRegistInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> data) {
		data.put("agentId", request.getParameter("agentId"));
		return channel + "/user/agreementRegist";
	}

	/**
	 * 用户注册
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	@RequestMapping("{channel}/user/regist")
	public @ResponseBody
	HashMap<String, Object> userRegistSubmit(ReqMsg_User_Regist reqRegist,
			@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("agentId", reqRegist.getAgentId());
		try {
			if (reqRegist.getRecommendId() == null
					|| "".equals(reqRegist.getRecommendId())) {
				CookieManager cookieManager = new CookieManager(request);
				String recommendId = cookieManager.getValue(
						CookieEnums._SITE.getName(),
						CookieEnums._SITE_RECOMMEND_ID.getName(), true);
				if (recommendId != null && !"".equals(recommendId)) {
					reqRegist.setRecommendId(recommendId);
				}
			} else {
				reqRegist.setRecommendId(Util
						.getUserIdByInvitationCode(reqRegist.getRecommendId()
								).toString());
			}

			if (channel.contains("gen")) {
				reqRegist.setFlag(-2);// 判断是否是pc端注册,如果是电脑注册则验证码验证过后就失效

			}

			ResMsg_User_Regist resRegmobile = (ResMsg_User_Regist) siteService
					.handleMsg(reqRegist);
			if (ConstantUtil.BSRESCODE_SUCCESS
					.equals(resRegmobile.getResCode())) {
				dataMap.put("bsCode", "000");
				dataMap.put("mobile", resRegmobile.getMobile());
				dataMap.put("mobileCode", resRegmobile.getMobileCode());
				dataMap.put("userId", resRegmobile.getUserId());
				dataMap.put("nick", resRegmobile.getNick());
			} else {
				dataMap.put("bsCode", resRegmobile.getResCode());
				dataMap.put("bsMsg", resRegmobile.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("bsCode", "999");
            dataMap.put("bsMsg", "港湾航道堵塞，稍后再试吧");
		}

		return dataMap;
	}

	/**
	 * 修改新密码
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/user/passwordSubimt")
	public @ResponseBody
	HashMap<String, Object> userChangePasswordSubmit(
			ReqMsg_User_FindPassword reqRegist, HttpServletRequest request,
			HttpServletResponse response, @PathVariable String channel) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ResMsg_User_FindPassword resRegmobile = (ResMsg_User_FindPassword) siteService
					.handleMsg(reqRegist);
			if (ConstantUtil.BSRESCODE_SUCCESS
					.equals(resRegmobile.getResCode())) {
			    ReqMsg_User_Login reqLogin = new ReqMsg_User_Login();
			    reqLogin.setNick(reqRegist.getMobile());
			    reqLogin.setMobile(reqRegist.getMobile());
			    reqLogin.setPassword(StringUtil.trimStr(reqRegist.getPassword()));
			    ResMsg_User_Login resLogin = (ResMsg_User_Login) siteService.handleMsg(reqLogin);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resLogin.getResCode())) {
					// 钱报的跳转参数
					String qianbao = request.getParameter("qianbao");
					if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
						if(StringUtil.isNotBlank(resLogin.getAgentId()) && Constants.USER_AGENT_QIANBAO.equals(resLogin.getAgentId())) {
							dataMap.put("qianbao", Constants.USER_AGENT_QIANBAO);
						}
					} else {
						resLogin.setAgentId("");
					}
					if("gen2.0".equals(channel) || "gen178".equals(channel)) {
						userCookieManagePC(request, response, dataMap, resLogin);
					}else{
						userCookieManage(request, response, dataMap, resLogin);
					}
					dataMap.put("nick", resRegmobile.getNick());
				}
				dataMap.put("bsCode", "000");
			} else {
				dataMap.put("bsCode", resRegmobile.getResCode());
				dataMap.put("bsMsg", resRegmobile.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("bsCode", "999");
            dataMap.put("bsMsg", "港湾航道堵塞，稍后再试吧");
		}

		return dataMap;
	}
	
	/**
	 * 微信小程序修改新密码
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("weixin/user/passwordSubimt")
	public @ResponseBody
	HashMap<String, Object> weChatuserChangePasswordSubmit(
			ReqMsg_User_FindPassword reqRegist, HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ResMsg_User_FindPassword resRegmobile = (ResMsg_User_FindPassword) siteService
					.handleMsg(reqRegist);
			if (ConstantUtil.BSRESCODE_SUCCESS
					.equals(resRegmobile.getResCode())) {
			    ReqMsg_User_Login reqLogin = new ReqMsg_User_Login();
			    reqLogin.setNick(reqRegist.getMobile());
			    reqLogin.setMobile(reqRegist.getMobile());
			    reqLogin.setPassword(StringUtil.trimStr(reqRegist.getPassword()));
			    ResMsg_User_Login resLogin = (ResMsg_User_Login) siteService.handleMsg(reqLogin);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resLogin.getResCode())) {
					userCookieManage(request, response, dataMap, resLogin);
					dataMap.put("nick", resRegmobile.getNick());
				}
				dataMap.put("bsCode", "000");
			} else {
				dataMap.put("bsCode", resRegmobile.getResCode());
				dataMap.put("bsMsg", resRegmobile.getResMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("bsCode", "999");
            dataMap.put("bsMsg", "港湾航道堵塞，稍后再试吧");
		}

		return dataMap;
	}

	// 删除验证码Cookie
	private void removeCodeCookieManage(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> dataMap,
			String cookieName) {
		CookieManager manager = new CookieManager(request);

		manager.setValue(CookieEnums._SITE.getName(), cookieName, "", true);
		manager.save(response, CookieEnums._SITE.getName(), null, "/", 600,
				true);
		successRes(dataMap);
	}
	
	/**
	 * 渠道注册首页
	 * @param channel
	 * @param agentId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/a/{agentId}")
	public String agentRegisterInit(@PathVariable String agentId,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> data) {
	    String link = GlobEnv.getWebURL("micro2.0/index");
	    // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        data.put("weichat", sign);
        
		//根据渠道编号查询渠道信息
		ReqMsg_User_AgentQuery reqMsg = new ReqMsg_User_AgentQuery();
		try {
			reqMsg.setAgentId(Integer.parseInt(agentId));
			ResMsg_User_AgentQuery resp = (ResMsg_User_AgentQuery) siteService.handleMsg(reqMsg);
			
			if ("9100029".equals(resp.getResCode())){
			    return "/micro2.0/agent/agent_not_exisit";
			}
			if(reqMsg.getAgentId().equals(Constants.AGENT_TEILU_ID) 
                    || reqMsg.getAgentId().equals(Constants.AGENT_TONGCHENG_ID)
                    || Constants.AGENT_DEPT_BEIJING.equals(resp.getDept())) {
                // 如果是北京|同城|铁路渠道跳入其他入口
                return "redirect:/micro2.0/activity/528";
            } else if(reqMsg.getAgentId()==Constants.AGENT_QIANBAO_ID || 
			        reqMsg.getAgentId()==Constants.AGENT_CHANNEL_ID || reqMsg.getAgentId()==Constants.AGENT_MONKEY_ID ||  
			        reqMsg.getAgentId()==Constants.AGENT_CSAI_ID || reqMsg.getAgentId()==Constants.AGENT_XIAOSHOURENYUAN_ID ||
			        reqMsg.getAgentId()==Constants.AGENT_318SHAKE_ID || reqMsg.getAgentId()==Constants.LANDING_PAGE_AGENT_ID ||
			        reqMsg.getAgentId()==Constants.MANAGER_AGENT_ID || reqMsg.getAgentId()==Constants.AGENT_XIAO_MI_STORE_ID ||
					reqMsg.getAgentId()==Constants.AGENT_BAIDU_STORE_ID|| reqMsg.getAgentId()== Constants.AGENT_HEFEILUNTAN_ID ||
					reqMsg.getAgentId()==Constants.AGENT_ANMO_ID || reqMsg.getAgentId()== Constants.AGENT_KQ_ID || 
					reqMsg.getAgentId()== Constants.AGENT_HAINING_ID || reqMsg.getAgentId()== Constants.AGENT_RUIAN_ID) {
                return "/micro2.0/agent/agent_not_exisit";
			}
			data.put("agent", resp);
			//同城
			if(reqMsg.getAgentId()==Constants.AGENT_TONGCHENG_ID) {
				String tc_token = request.getParameter("token");
				String tc_type = request.getParameter("type");
				String tc_Sign = request.getParameter("Sign");
				logger.info("=========【同程落地页请求参数】token："+tc_token+"，type："+tc_type+"，Sign："+tc_Sign+"=========");
				if(StringUtil.isNotBlank(tc_token)&&StringUtil.isNotBlank(tc_type)&&StringUtil.isNotBlank(tc_Sign)) {
					String md5 = MD5Util.encryptMD5(tc_token+tc_type+Constants.TONGCHENG_EKY);
					if(tc_Sign.equals(md5)) {
						data.put("tc_token", tc_token);
						data.put("tc_type", tc_type);
						data.put("tc_Sign", tc_Sign);
						return "/micro2.0/agent/agent_tongcheng";
					}
					else {
						return "/micro2.0/agent/agent_not_exisit";
					}
				}
				else {
					return "/micro2.0/agent/agent_not_exisit";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/micro2.0/agent/agent_error";
		}
		return  "/micro2.0/agent/agent_index";
	}
	
	/**
	 * 注册成功页
	 * @param channel
	 * @param mobile
	 * @param request
	 * @param response
	 * @param data
	 * @return
	 */
	@RequestMapping("{channel}/user/regist/succ")
	public String registerSucc(@PathVariable String channel,String mobile,String agentId,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> data) {
	    
	    String link = GlobEnv.getWebURL("micro2.0/index");
	    // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        data.put("weichat", sign);
        
		data.put("mobile", mobile);
		data.put("agentId", agentId);
		return channel + "/user/regist_succ";
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
		manager.save(response, CookieEnums._SITE.getName(), null, "/",  -1, true);
		successRes(dataMap);
		
	}
	
	/**
	 *  存微网cookie
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
        manager.save(response, CookieEnums._SITE.getName(), null, "/",  5*365*24*3600, true);
        successRes(dataMap);

    }

	/**
	 * 静态页注册跳转判断
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping("{channel}/user/regist/registInit")
	public Map<String, Object> registInit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		result.put("userId", userId);
		return result;
	}
}
