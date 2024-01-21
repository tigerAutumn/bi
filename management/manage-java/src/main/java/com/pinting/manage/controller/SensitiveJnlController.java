package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_sensitiveJnl;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_sensitiveJnl;
import com.pinting.core.hessian.service.HessianService;

/**
 * 用户敏感操作
 * @Project: manage-java
 * @Title: sensitiveJnlController.java
 * @author Linkin
 * @date 2015-2-2 下午3:38:49
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class SensitiveJnlController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;

	@RequestMapping("/sys/sensitiveJnl/index")
	public String sysSensitiveJnlIndex(ReqMsg_MSystem_sensitiveJnl reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_MSystem_sensitiveJnl resMsg = (ResMsg_MSystem_sensitiveJnl) manageService
				.handleMsg(reqMsg);
		ArrayList<HashMap<String, Object>> sensitiveList = resMsg.getBsSensitiveList();
		model.put("sensitiveList", sensitiveList);
		// 分页信息
		model.put("pageNum", resMsg.getPageNum());
		model.put("numPerPage", resMsg.getNumPerPage());
		model.put("totalRows", resMsg.getTotalRows());
		model.put("userName", resMsg.getUserName());
		model.put("ip", resMsg.getIp());

		return "/system/sensitive_index";

	}
	


}
