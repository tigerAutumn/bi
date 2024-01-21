package com.pinting.site.common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_AutoRedPacket_RedPacketSend;
import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.hessian.site.message.ResMsg_AutoRedPacket_RedPacketSend;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.utils.IpUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * 注册时，（用户注册、邀请满人） 红包自动发放拦截器
 * @Project: site-java
 * @Title: RedPacketAutoGrantInterceptor.java
 * @author dingpf
 * @date 2016-3-3 下午5:12:19
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class RedPacketAutoGrantInterceptor extends HandlerInterceptorAdapter {
	private static Logger log = LoggerFactory.getLogger(RedPacketAutoGrantInterceptor.class);
	
	@Autowired
	private CommunicateBusiness interceptorService;

	@SuppressWarnings("unchecked")
	@Override  
    public void postHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {  
		Map<String, Object> model = (Map<String, Object>)request.getAttribute("resultMap");
		if(ConstantUtil.RESCODE_SUCCESS.equals(model.get("bsCode"))) {
			//注册成功,解析自动红包规则
			//注册红包
			ReqMsg_AutoRedPacket_RedPacketSend req = new ReqMsg_AutoRedPacket_RedPacketSend();
			req.setUserId(Integer.valueOf(model.get("userId").toString()));
			req.setTriggerType(Constants.AUTO_RED_PACKET_TIGGER_TYPE_NEW_USER);
			ResMsg_AutoRedPacket_RedPacketSend resRegister = (ResMsg_AutoRedPacket_RedPacketSend)interceptorService.handleMsg(req);
			log.info("编号："+req.getUserId()+"的用户获取到的注册红包："+resRegister.getRedPacketInfoIds());
			//推荐红包
			req.setTriggerType(Constants.AUTO_RED_PACKET_TIGGER_TYPE_INVITE_FULL);
			ResMsg_AutoRedPacket_RedPacketSend resRecommend = (ResMsg_AutoRedPacket_RedPacketSend)interceptorService.handleMsg(req);
			log.info("编号："+req.getUserId()+"的推荐用户获取到的推荐红包："+resRecommend.getRedPacketInfoIds());
			
			
			
			/**
			 * 注册成功获得专享额度
			 * 1、在固定时间区间内新用户注册成功给用户发放额度一万
			 * 2、如果注册成功的用户填写了邀请码，那么给邀请人增加两万额度
			 */
			//新用户
			ReqMsg_UserProductLimit_UserProductLimitAdd reqUserProductLimitAddRegister = new ReqMsg_UserProductLimit_UserProductLimitAdd();
			reqUserProductLimitAddRegister.setUserId(Integer.valueOf(model.get("userId").toString()));
			reqUserProductLimitAddRegister.setEvent(Constants.EVENT_REGISTER);
			ResMsg_UserProductLimit_UserProductLimitAdd resUserProductLimitAddRegister = (ResMsg_UserProductLimit_UserProductLimitAdd)interceptorService.handleMsg(reqUserProductLimitAddRegister);
			//邀请用户
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resUserProductLimitAddRegister.getResCode())) {
				if (model.get("recommendId") != null && !"".equals(model.get("recommendId"))&& Integer.valueOf(model.get("recommendId").toString()) != null) {
					ReqMsg_UserProductLimit_UserProductLimitAdd reqUserProductLimitAddRecommend = new ReqMsg_UserProductLimit_UserProductLimitAdd();
					reqUserProductLimitAddRecommend.setUserId(Integer.valueOf(model.get("recommendId").toString()));
					reqUserProductLimitAddRecommend.setEvent(Constants.EVENT_RECOMMEND);
					ResMsg_UserProductLimit_UserProductLimitAdd resUserProductLimitAddRecommend = (ResMsg_UserProductLimit_UserProductLimitAdd)interceptorService.handleMsg(reqUserProductLimitAddRecommend);
				}

			}

		}
    }  
}
