package com.pinting.gateway.qidian.in.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.gateway.qidian.in.model.BaseReqModel;
import com.pinting.gateway.qidian.in.model.FranchiseeRegistReqModel;
import com.pinting.gateway.qidian.in.model.FranchiseeRegistResModel;
import com.pinting.gateway.qidian.in.model.LoginReqModel;
import com.pinting.gateway.qidian.in.model.LoginResModel;
import com.pinting.gateway.qidian.in.service.QiDianService;
import com.pinting.gateway.qidian.in.util.QiDianInConstant;
import com.pinting.gateway.qidian.in.util.QiDianInMsgUtil;



@Controller
public class QiDianController {

	private Logger log = LoggerFactory
			.getLogger(QiDianController.class);
	@Autowired
	private		QiDianService 	qiDianService;

	@RequestMapping("/qidian/business")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		BaseReqModel reqModel = (BaseReqModel) request.getAttribute("reqModel");
		// 待返回报文密文
		String resp = null;
		switch (reqModel.getTransCode()) {
		
		case QiDianInConstant.LOGIN:// 七店登录 处理
			LoginResModel loginResModel = new LoginResModel();
			loginResModel = qiDianService.login((LoginReqModel) reqModel);
			resp = QiDianInMsgUtil.packageMsg(loginResModel);
			break;
		
		case QiDianInConstant.FRANCHISEE_REGIST: // 店主注册
			FranchiseeRegistResModel franchiseeRegistResModel = new FranchiseeRegistResModel();
			franchiseeRegistResModel = qiDianService.franchiseeRegist((FranchiseeRegistReqModel) reqModel);
			resp = QiDianInMsgUtil.packageMsg4AmountIsNull(franchiseeRegistResModel);
			break;	

		default:
			break;
		}
		// 返回响应报文（密文）
		return resp;
	}
	
	@RequestMapping("/qidian/getString")
	public @ResponseBody
	String getString(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String encryptData = request.getParameter("DATA");
		String returnCode = QiDianInMsgUtil.getString(encryptData);
		return returnCode;
	}
	
	

}
