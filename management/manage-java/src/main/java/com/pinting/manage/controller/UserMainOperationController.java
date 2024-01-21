package com.pinting.manage.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_MUserMainOperation_UserMainOperationListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserMainOperation_UserMainOperationListQuery;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;

/**
 * 用户操作IP
 * @Project: manage-java
 * @Title: UserMainOperationController.java
 * @author yanwl
 * @date 2016-3-11 下午14:11:58
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
@RequestMapping("/userMainOperation")
public class UserMainOperationController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;

	/**
	 * 用户操作IP首页
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public String userMainOperation(ReqMsg_MUserMainOperation_UserMainOperationListQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		if(req.getPageNum() <= 0 || req.getNumPerPage() <= 0) {
			req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
			req.setNumPerPage(100);
		}
		ResMsg_MUserMainOperation_UserMainOperationListQuery res = (ResMsg_MUserMainOperation_UserMainOperationListQuery)manageService.handleMsg(req);
		model.put("req", req);
		model.put("totalRows", res.getTotalRows());
		model.put("userMainOperationList", res.getUserMainOperationList());
		return  "/userMainOperation/user_main_operation_index";
	}
}
