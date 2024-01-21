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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_SysConfigQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_SysConfigUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_spercialJnl;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_SysConfigQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_SysConfigUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_spercialJnl;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 特殊交易流水
 * @Project: manage-java
 * @Title: specialJnlController.java
 * @author Linkin
 * @date 2015-2-1 下午4:07:43
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class SpecialJnlController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;

	@RequestMapping("/sys/specialJnl/index")
	public String sysSpecialJnlIndex(ReqMsg_MSystem_spercialJnl reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_MSystem_spercialJnl resMsg = (ResMsg_MSystem_spercialJnl) manageService
				.handleMsg(reqMsg);
		ArrayList<HashMap<String, Object>> spercialList = resMsg.getMSpercialList();
		model.put("spercialList", spercialList);
		// 分页信息
		model.put("pageNum", resMsg.getPageNum());
		model.put("numPerPage", resMsg.getNumPerPage());
		model.put("totalRows", resMsg.getTotalRows());

		return "/system/special_index";

	}
	


}
