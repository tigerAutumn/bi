package com.pinting.site.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_System_Status;
import com.pinting.business.hessian.site.message.ResMsg_System_Status;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
public class ErrorManageController {
	@Autowired
	private CommunicateBusiness regularService;
	@RequestMapping("/404")
	public String notFound(HttpServletRequest request, HttpServletResponse response){
		return "/gen/errors/404";
		
	}
	@RequestMapping("/500")
	public String sysError(HttpServletRequest request, HttpServletResponse response){
		return "/gen/errors/500";
	}
	@RequestMapping("/protect")
	public String sysProtect(HttpServletRequest request, HttpServletResponse response,ReqMsg_System_Status req){
		ResMsg_System_Status resMsg = (ResMsg_System_Status)regularService.handleMsg(req);
		Constants.sysValue=resMsg.getSysValue();
		Constants.tranValue = resMsg.getTranValue();
		return "/gen2.0/errors/protect";
	}
	
}
