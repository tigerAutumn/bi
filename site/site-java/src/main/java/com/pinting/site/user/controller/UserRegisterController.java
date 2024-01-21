/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.user.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_GetUserRegistRedPakt;
import com.pinting.business.hessian.site.message.ReqMsg_User_BindUserCallBack;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoValidation;
import com.pinting.business.hessian.site.message.ReqMsg_User_Regist;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_GetUserRegistRedPakt;
import com.pinting.business.hessian.site.message.ResMsg_User_BindUserCallBack;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoValidation;
import com.pinting.business.hessian.site.message.ResMsg_User_Login;
import com.pinting.business.hessian.site.message.ResMsg_User_Regist;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.PreURLInterceptor;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author HuanXiong
 * @version $Id: UserRegisterController.java, v 0.1 2015-11-12 上午11:48:35 HuanXiong Exp $
 */
@Controller
public class UserRegisterController extends BaseController {

    @Autowired
    private CommunicateBusiness siteService;
    @Autowired
    private WeChatUtil weChatUtil;
    
    private final Logger logger = LoggerFactory.getLogger(UserRegisterController.class);
    private final String CHANNEL_MICRO = "micro2.0";
    private final String CHANNEL_GEN = "gen2.0";
    private final String CHANNEL_GEN_178 = "gen178";
    private final String QD = "qd";


    /**
     * 注册界面--原来进入到注册界面的入口
     * 现在这个入口留给地退人员使用，
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/register_first_index")
    public String registerFirstIndex(@PathVariable String channel, HttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> model) {
        // 移出旧cookie
        CookieManager manager = new CookieManager(request);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(),
                null, true);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_NAME.getName(),
                null, true);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_TYPE.getName(),
                null, true);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_MOBILE_NUM.getName(),
                null, true);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_MOBILE_CODE.getName(),
                null, true);
        manager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
        manager.cleanAll(response, CookieEnums._SITE.getName(),  true);

        // 钱报的参数
        registerFirst(channel, request, model);
        return channel + "/user/register_first_index";
    }
    
    
    /**
     * 注册界面--这个入口是新的注册页面入口  原来注册页面入口留给地推用户使用
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/register_first_new_index")
    public String registerFirstNewIndex(@PathVariable String channel, HttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> model) {
        // 移出旧bookie
        removeCodeCookieManage(request, response, new HashMap<String, Object>(),
            CookieEnums._SITE_MOBILE_CODE.getName());

        registerFirst(channel, request, model);

        List<HashMap<String, Object>> bannerList = BannerUtil.queryBannerList(siteService, channel, Constants.SHOW_PAGE_REGISTER);
        if(!org.springframework.util.CollectionUtils.isEmpty(bannerList)) {
            model.put("banner", bannerList.get(0));
        }
        return channel + "/user/register_first_new_index";
    }


    /**
     * 微信小程序注册界面
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/user/register_first_new_index")
    public String weChatRegisterFirstNewIndex(HttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> model) {
        // 移出旧bookie
        removeCodeCookieManage(request, response, new HashMap<String, Object>(),
            CookieEnums._SITE_MOBILE_CODE.getName());
        return "weixin/user/register_first_new_index";
    }
    
    private void registerFirst(String channel, HttpServletRequest request, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if(StringUtil.isNotBlank(viewAgentFlagCookie)){
                link += "&agentViewFlag="+viewAgentFlagCookie;
            }

            if(Constants.AGENT_VIEW_ID_QD.equals(request.getParameter("agentViewFlag"))) {
                model.put("flagForQD", Constants.AGENT_VIEW_ID_QD);
            }
        }

        if(CHANNEL_MICRO.equals(channel)) {
            String recommendId = request.getParameter("recommendId");
            model.put("recommendId", recommendId);
            if (StringUtil.isNotBlank(recommendId) && recommendId.length() >= 2) {
                String qd = recommendId.substring(0, 2);
                if (QD.equals(qd)) {
                    link = GlobEnv.getWebURL("/micro2.0/index");
                    model.put(Constants.USER_AGENT_QIANBAO, Constants.USER_AGENT_QIANBAO);
                    link += "?qianbao=qianbao&agentViewFlag=" + Constants.AGENT_VIEW_ID_QD;
                    request.setAttribute("agentViewFlag", Constants.AGENT_VIEW_ID_QD);
                    model.put("flagForQD", Constants.AGENT_VIEW_ID_QD);
                }
            }
            // 分享
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
        } else if(CHANNEL_GEN.equals(channel)) {
            CookieManager cookieManager = new CookieManager(request);
            String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_RECOMMEND_ID.getName(), true);
            if (recommendId != null && !"".equals(recommendId)) {
                if(!recommendId.matches("[0-9]+")){//不是数字就设置null
                    model.put("recommendId", recommendId);
                }else{
                    model.put("recommendId", Util.generateInvitationCode(Integer.parseInt(recommendId)));
                }
            }
        }
    }

    /**
     * 验证
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/register_first_validate/{flag}")
    public @ResponseBody
    HashMap<String, Object> registerFirstValidate(ReqMsg_User_InfoValidation reqValidate,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  @PathVariable String flag) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        ReqMsg_User_InfoValidation req = new ReqMsg_User_InfoValidation();
        try {
            Field f1 = req.getClass().getDeclaredField(flag);
            Field f2 = reqValidate.getClass().getDeclaredField(flag);
            f1.setAccessible(true);
            f2.setAccessible(true);
            f1.set(req, f2.get(reqValidate));
        } catch (Exception e) {
        	e.printStackTrace();
            dataMap.put("resCode", "999");
            dataMap.put("resMsg", "请检查链接地址！");
            return dataMap;
        }
        if(Util.isRepeatMobileSubmit(request, response)) {
            dataMap.put("resCode", ConstantUtil.BSRESCODE_FAIL);
            dataMap.put("resMsg", "重复提交的请求");
            return dataMap;
        }
        try {
            ResMsg_User_InfoValidation resp = (ResMsg_User_InfoValidation) siteService.handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
                dataMap.put("resCode", "000");
                dataMap.put("resMsg", resp.getResMsg());
            } else {
                dataMap.put("resCode", resp.getResCode());
                dataMap.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(dataMap);
        }

        dataMap.put("mobileToken", Util.createMobileToken(request, response));
        dataMap.put("mobile", reqValidate.getMobile());
        return dataMap;
    }

    /**
     * 注册界面2
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/register_second_index")
    public String registerSecondIndex(@PathVariable String channel, HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model) {
        model.put("verCode", request.getParameter("verCode"));
        model.put("mobile", request.getParameter("mobile"));
        model.put("agentId", request.getParameter("agentId"));
        model.put("agentPage", request.getParameter("agentPage"));
        model.put("flagForQD", request.getParameter("flagForQD"));
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        CookieManager cookieManager = new CookieManager(request);
        String recommendId = request.getParameter("recommendId");
        if(StringUtil.isBlank(recommendId)){
        	recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_RECOMMEND_ID.getName(), true);
        	if(!recommendId.matches("[0-9]+")){//不是数字就设置null
                if (StringUtil.isNotBlank(recommendId) && recommendId.length() >= 2) {
                    String qd = recommendId.substring(0, 2);
                    if (!QD.equals(qd)) {
                        model.put("recommendId", recommendId);
                    }
                } else {
                    model.put("recommendId", recommendId);
                }
        	}else{
        		model.put("recommendId", Util.generateInvitationCode(Integer.parseInt(recommendId)));
        	}
        }else if (StringUtil.isNotBlank(recommendId)) {
        	model.put("recommendId", recommendId);
        }
        
        return channel + "/user/register_second_index";
    }
    
    /**
     * 微信小程序注册界面2
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/user/register_second_index")
    public String weChatRegisterSecondIndex(HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model) {
        model.put("verCode", request.getParameter("verCode"));
        model.put("mobile", request.getParameter("mobile"));
        model.put("agentId", request.getParameter("agentId"));
        model.put("agentPage", request.getParameter("agentPage"));
        return "weixin/user/register_second_index";
    }

    /**
     * 注册完毕
     * 
     * @param reqRegist
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/user/register_submit")
    public @ResponseBody
    HashMap<String, Object> registerSubmit(ReqMsg_User_Regist reqRegist,
        @PathVariable String channel,HttpServletRequest request, HttpServletResponse response) {
        // 获取注册用户来源（H5、PC）
        if("micro2.0".equals(channel)) {
            reqRegist.setRegTerminal(Constants.USER_REG_TERMINAL_H5);
        } else if("gen178".equals(channel) || "gen2.0".equals(channel)){
            reqRegist.setRegTerminal(Constants.USER_REG_TERMINAL_PC);
        }
        
    	if("gen178".equals(channel)) {
            reqRegist.setQianbao("qianbao");
        }
    	
    	
    	
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("agentId", reqRegist.getAgentId());
        
        CookieManager cookieManager = new CookieManager(request);
        String viewAgentId = cookieManager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
    	if(!"".equals(viewAgentId)){
    		reqRegist.setViewAgentId(viewAgentId);
    	}
        
        //判断是否存在希财Cookie
        String agentName = cookieManager.getValue(CookieEnums._CSAI.getName(),
                CookieEnums._CSAI_AGENT_CSAI.getName(), true);
        if (agentName != null && !"".equals(agentName) && Constants.USER_AGENT_CAAI.equals(agentName)) {
            if (reqRegist.getAgentId() == null || "".equals(reqRegist.getAgentId())) {
    			reqRegist.setAgentId(Constants.AGENT_CSAI_ID);
    		}
		}
        
        boolean isRecommed = false;
        try {
            if (reqRegist.getRecommendId() == null || "".equals(reqRegist.getRecommendId())) {
               
                String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_RECOMMEND_ID.getName(), true);
                if (recommendId != null && !"".equals(recommendId)) {
                    reqRegist.setRecommendId(recommendId);
                    isRecommed = true;
                } else {
                    if(StringUtil.isNotBlank(reqRegist.getViewAgentId()) && reqRegist.getViewAgentId().equals(Constants.AGENT_VIEW_ID_QD)) {
                        // 七店：注册为默认一个邀请码（该邀请码读取配置：7_DIAN_DEFAULT_SALES_ID）的币港湾用户
                        String flagForQD = request.getParameter("flagForQD");
                        if(Constants.AGENT_VIEW_ID_QD.equals(flagForQD)) {
                            reqRegist.setInviteCode(Constants.SEVEN_DIAN_DEFAULT_SALES_ID_KEY);
                            reqRegist.setQianbao(null);
                        }
                    }
                }
            } else {
            	//判断是否是销售人员推荐（根据邀请码判断）
            	if(!reqRegist.getRecommendId().matches("[0-9]+")){//不是数字就设置null
                    if (StringUtil.isNotBlank(reqRegist.getRecommendId()) && reqRegist.getRecommendId().length() >= 2) {
                        String qd = reqRegist.getRecommendId().substring(0, 2);
                        if (!QD.equals(qd)) {
                            reqRegist.setAgentId(31); // 销售人员推荐渠道ID
                            reqRegist.setQianbao(null);
                        } else {
                            reqRegist.setAgentId(Constants.AGENT_ID_QD); // 七店销售人员推荐渠道ID
                            reqRegist.setQianbao(null);
                        }
                    } else {
                        reqRegist.setAgentId(31); // 销售人员推荐渠道ID
                        reqRegist.setQianbao(null);
                    }
            		reqRegist.setInviteCode(reqRegist.getRecommendId());//把邀请码存下来
            		reqRegist.setRecommendId(null);
            	}
            	if(null != reqRegist.getRecommendId() && reqRegist.getRecommendId().length()!= 11){
            		reqRegist.setRecommendId(Util.getUserIdByInvitationCode(reqRegist.getRecommendId()).toString());
            		isRecommed = true;
            	}else if (null != reqRegist.getRecommendId() && reqRegist.getRecommendId().length()== 11) {
            		reqRegist.setManagerInviteCode(reqRegist.getRecommendId());
            		reqRegist.setRecommendId(null);
            		reqRegist.setAgentId(Constants.MANAGER_AGENT_ID); // 客户经理人推荐渠道ID
				}
            }

            if (channel.contains("gen")) {
                reqRegist.setFlag(-2);// 判断是否是pc端注册,如果是电脑注册则验证码验证过后就失效
            }
           
            if (!isRecommed && reqRegist.getAgentId() != null) {
            	cookieManager.setValue(CookieEnums._SITE.getName(),
                         CookieEnums._SITE_RECOMMEND_ID.getName(),
                         null, true);
				reqRegist.setRecommendId(null);
			}
            
            //有推荐人id时，其他渠道统一置空
            if(isRecommed){
            	reqRegist.setQianbao(null);
            	reqRegist.setAgentId(null);
            	reqRegist.setInviteCode(null);
            }
            
            ResMsg_User_Regist resRegmobile = (ResMsg_User_Regist) siteService.handleMsg(reqRegist);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resRegmobile.getResCode())) {
                dataMap.put("bsCode", "000");
                dataMap.put("mobile", resRegmobile.getMobile());
                dataMap.put("mobileCode", resRegmobile.getMobileCode());
                dataMap.put("userId", resRegmobile.getUserId());
                dataMap.put("nick", resRegmobile.getNick());
                dataMap.put("userType", resRegmobile.getUserType());
                dataMap.put("recommendId", reqRegist.getRecommendId());
                // 存入cookie
                ResMsg_User_Login resLogin = new ResMsg_User_Login();
                resLogin.setMobile(resRegmobile.getMobile());
                resLogin.setNick(resRegmobile.getNick());
                resLogin.setUserType(resRegmobile.getUserType());
                resLogin.setUserId(resRegmobile.getUserId());
                if(resRegmobile.getAgentId() != null) {
                    resLogin.setRealAgentId(resRegmobile.getAgentId());
                }

                //清除希财Cokie
                cookieManager.setValue(CookieEnums._CSAI.getName(),
                        CookieEnums._CSAI_AGENT_CSAI.getName(), null, true);
                cookieManager.save(response, CookieEnums._CSAI.getName(), null, "/",
                        30 * 24 * 3600, true);
                
              //调用绑定回调接口
                /*  if (Constants.AGENT_CSAI_ID == reqRegist.getAgentId()) {
        	    	ReqMsg_User_BindUserCallBack bindUserCallBack = new ReqMsg_User_BindUserCallBack();
        	    	bindUserCallBack.setPhone(resRegmobile.getMobile());
        	    	bindUserCallBack.setName(resRegmobile.getNick());
        	    	bindUserCallBack.setResult(1);
        	    	bindUserCallBack.setDisplay(Constants.CSAI_USER_DISPLAY_PC);
        	    	ResMsg_User_BindUserCallBack resBindUserCallBack = (ResMsg_User_BindUserCallBack) siteService.handleMsg(bindUserCallBack);
				}*/
        		

                //同程
                if(null != reqRegist.getAgentId() && reqRegist.getAgentId() == Constants.AGENT_TONGCHENG_ID) {
                	String tc_token = request.getParameter("tc_token");
                    String tc_type = request.getParameter("tc_type");
                    String tc_Sign = request.getParameter("tc_Sign");
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("token", tc_token);
                    map.put("type", tc_type);
                    map.put("Sign", tc_Sign);
                    JSONObject result = HttpUtil.postHttpJson(Constants.TONGCHENG_URL, map);
                    logger.info("=========【同程回调接口返回的结果】data："+result.toString()+"=========");
                }
                
                // 钱报
                if("gen178".equals(channel) || (resRegmobile.getQianbaoFlag()!=null && resRegmobile.getQianbaoFlag() == Constants.AGENT_QIANBAO_ID)) {
                	resLogin.setAgentId("qianbao");
                	dataMap.put("qianbao", "qianbao");
                }else{
                	resLogin.setAgentId("");
                }
                
                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
            		userCookieManagePC(request, response, dataMap, resLogin);
                }else{
                	userCookieManage(request, response, dataMap, resLogin);
                }
            } else {
                dataMap.put("bsCode", resRegmobile.getResCode());
                dataMap.put("bsMsg", resRegmobile.getResMsg());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	dataMap.put("bsCode", "999");
            dataMap.put("bsMsg", "港湾航道堵塞，稍后再试吧");
        }
        dataMap.put("payAds", "payAds");
        request.setAttribute("resultMap", dataMap);
        return dataMap;
    }

    /**
     * 微信小程序注册完毕
     * 
     * @param reqRegist
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("weixin/user/register_submit")
    public @ResponseBody
    HashMap<String, Object> weChatRegisterSubmit(ReqMsg_User_Regist reqRegist,
        HttpServletRequest request, HttpServletResponse response) {
        // 获取注册用户来源（微信小程序）
        reqRegist.setRegTerminal(Constants.USER_REG_TERMINAL_WXAPP);
        
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ResMsg_User_Regist resRegmobile = (ResMsg_User_Regist) siteService.handleMsg(reqRegist);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resRegmobile.getResCode())) {
                dataMap.put("bsCode", "000");
                dataMap.put("mobile", resRegmobile.getMobile());
                dataMap.put("mobileCode", resRegmobile.getMobileCode());
                dataMap.put("userId", resRegmobile.getUserId());
                dataMap.put("nick", resRegmobile.getNick());
                dataMap.put("userType", resRegmobile.getUserType());
                // 存入cookie
                ResMsg_User_Login resLogin = new ResMsg_User_Login();
	            resLogin.setMobile(resRegmobile.getMobile());
	            resLogin.setNick(resRegmobile.getNick());
	            resLogin.setUserType(resRegmobile.getUserType());
	            resLogin.setUserId(resRegmobile.getUserId());
	            if(resRegmobile.getAgentId() != null) {
	                resLogin.setRealAgentId(resRegmobile.getAgentId());
	            }
	            resLogin.setAgentId("");
	            userCookieManage(request, response, dataMap, resLogin);
            } else {
                dataMap.put("bsCode", resRegmobile.getResCode());
                dataMap.put("bsMsg", resRegmobile.getResMsg());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	dataMap.put("bsCode", "999");
            dataMap.put("bsMsg", "港湾航道堵塞，稍后再试吧");
        }
        request.setAttribute("resultMap", dataMap);
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
		//判断是否是钱报相关的agentid，是则存cookie
		if(null != resLogin.getRealAgentId()) {
			List<Integer> agentIdList = PreURLInterceptor.agentIdList;
			if(!CollectionUtils.isEmpty(agentIdList)){
				for (Integer integer : agentIdList) {
					if(resLogin.getRealAgentId() == integer){
                        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), String.valueOf(resLogin.getRealAgentId()), true);
						manager.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(),integer.toString() , true);
						manager.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
					}
				}
			}
		}
		manager.save(response, CookieEnums._SITE.getName(), null, "/",  -1, true);
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
        //判断是否是钱报相关的agentid，是则存cookie
        if(null != resLogin.getRealAgentId()){
        	List<Integer> agentIdList = PreURLInterceptor.agentIdList;
        	if(!CollectionUtils.isEmpty(agentIdList)){
        		for (Integer integer : agentIdList) {
        			if(resLogin.getRealAgentId().equals(integer)){
                        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), String.valueOf(resLogin.getRealAgentId()), true);
        				manager.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(),integer.toString() , true);
        				manager.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
        			}
        		}
        	}
        }
        manager.save(response, CookieEnums._SITE.getName(), null, "/",  5*365*24*3600, true);
        successRes(dataMap);

    }

    /**
     * 删除cookie
     * 
     * @param request
     * @param response
     * @param dataMap
     * @param cookieName
     */
    private void removeCodeCookieManage(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> dataMap, String cookieName) {
        CookieManager manager = new CookieManager(request);

        manager.setValue(CookieEnums._SITE.getName(), cookieName, "", true);
        manager.save(response, CookieEnums._SITE.getName(), null, "/", 600, true);
        successRes(dataMap);
    }
    
    
    /**
     * 渠道用户进入到注册成功提示页面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/agent/agent_register_succ")
    public String agentRegisterSucc(@PathVariable String channel, HttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> model) {
        //查询注册获得红包
    	CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_RedPacketInfo_GetUserRegistRedPakt req = new ReqMsg_RedPacketInfo_GetUserRegistRedPakt();
		req.setUserId(Integer.valueOf(userId));
		ResMsg_RedPacketInfo_GetUserRegistRedPakt res = (ResMsg_RedPacketInfo_GetUserRegistRedPakt) siteService.handleMsg(req);
    	List<Map<String, Object>> redPakts = res.getUserRegistRedPakts();
		if(CollectionUtils.isEmpty(redPakts)){
			return channel + "/agent/agent_register_succ";
		}else{
			Double amount = 0d;
			for (Map<String, Object> map : redPakts) {
				amount = MoneyUtil.add(String.valueOf(amount), String.valueOf(map.get("subtract"))).doubleValue();
			}
			double a = amount;
            int integer = (int)a;
            if(a == integer) {
                model.put("amount", integer);
            } else {
                model.put("amount", a);
            }
			return channel + "/agent/agent_register_succ_redpakt";
		}
    	
    }
    
    /**
     * 地推进入到注册成功提示页面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/channel/channel_register_succ")
    public String channelRegisterSucc(@PathVariable String channel, HttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> model) {
    	//查询注册获得红包
    	CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_RedPacketInfo_GetUserRegistRedPakt req = new ReqMsg_RedPacketInfo_GetUserRegistRedPakt();
		req.setUserId(Integer.valueOf(userId));
		ResMsg_RedPacketInfo_GetUserRegistRedPakt res = (ResMsg_RedPacketInfo_GetUserRegistRedPakt) siteService.handleMsg(req);
    	List<Map<String, Object>> redPakts = res.getUserRegistRedPakts();
		if(CollectionUtils.isEmpty(redPakts)){
			return channel + "/user/pack_welcome";
		}else{
			Double amount = 0d;
			for (Map<String, Object> map : redPakts) {
				amount = MoneyUtil.add(String.valueOf(amount), String.valueOf(map.get("subtract"))).doubleValue();
			}
            double a = amount;
            int integer = (int)a;
            if(a == integer) {
                model.put("amount", integer);
            } else {
                model.put("amount", a);
            }
			return channel + "/channel/channel_register_succ_redpakt";
		}
    	
    	
    }
    
    
    /**
     * 推荐送奖金页面分享出来的注册入口
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/user/register_index_share")
    public String registerIndexShare(@PathVariable String channel, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String recommend = new String();
        // 没有登录
        if(StringUtil.isBlank(userId)) {
          //当点击用户分享链接过来时会有此参数 
            String user = request.getParameter("user");
            //UUID长度至少36
            if(StringUtil.isNotBlank(user) && user.length() >= 36) {
                user = user.substring(0, user.length()-36);
                // 用户ID不为空，存入cookie
                if(StringUtil.isNotBlank(user)) {
                    String newRecommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(user));
                    CookieManager cookieManager = new CookieManager(request);
                    String oldRecommendId = cookieManager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(), true);
                    //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
                    if(StringUtil.isBlank(oldRecommendId) || !user.equals(oldRecommendId)) { 
                        cookieManager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_RECOMMEND_ID.getName(), user, true);
                        cookieManager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365*24*3600, true);
                        recommend = newRecommend;
                    } else {
                       	if(!oldRecommendId.matches("[0-9]+")){//不是数字就设置null
                       		recommend = oldRecommendId;
                       	}else{
                       		recommend = com.pinting.util.Util.generateInvitationCode(Integer.parseInt(oldRecommendId));
                       	}
                    }
                }
            }
            
            model.put("recommendId", recommend);
            return channel + "/user/register_index_share";
        } else {
            // 已经登录，转入首页
            return "redirect:/"+channel+"/index";
        }
    }

    /**
     * 微信点亮存管图标瓜分百万红包-注册入口
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/user/register_index_lightUp2017")
    public String registerIndexLightUpDepLogo2017(@PathVariable String channel, HttpServletRequest request,
                                                  HttpServletResponse response, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        // 没有登录
        if(StringUtil.isBlank(userId)) {
            return channel + "/user/register_index_lightUp2017";
        } else {
            // 已经登录，转入首页
            return "redirect:/"+channel+"/index";
        }
    }

}
