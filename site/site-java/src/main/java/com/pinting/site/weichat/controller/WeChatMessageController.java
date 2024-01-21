package com.pinting.site.weichat.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pinting.business.hessian.site.message.ReqMsg_WechatMsg_sendMesg;
import com.pinting.site.communicate.CommunicateBusiness;
@Controller
public class WeChatMessageController {
	private Logger log = LoggerFactory.getLogger(WeChatMessageController.class);
	@Autowired
	private CommunicateBusiness siteService;
	
	
	@RequestMapping(value = "/weChat/templateSendFirstPass", method = { RequestMethod.GET })
	public void templateSendFirstPass(HttpServletRequest request,ReqMsg_WechatMsg_sendMesg req,
			HttpServletResponse response) {
		try {
			/*String openId = "oJ12xv8Ys_akxGU71sDyuJCfrHgs";朱媛*/
			String userId = request.getParameter("userId");
			req.setOpenId(userId);
			siteService.handleMsg(req);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
