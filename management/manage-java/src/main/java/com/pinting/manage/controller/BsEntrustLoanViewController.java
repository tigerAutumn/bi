package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_BsEntrustLoanView_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsEntrustLoanView_GetList;
import com.pinting.business.service.manage.BsEntrustLoanViewService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;

@Controller
@RequestMapping("/statistics/entrustLoanView")
public class BsEntrustLoanViewController {

	@Autowired 
	BsEntrustLoanViewService bsEntrustLoanViewService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,ReqMsg_BsEntrustLoanView_GetList req){
		ResMsg_BsEntrustLoanView_GetList res = new ResMsg_BsEntrustLoanView_GetList();
		if(StringUtil.isNotBlank(request.getParameter("toPropertySymbol"))){
			req = new ReqMsg_BsEntrustLoanView_GetList();
			req.setPropertySymbol(request.getParameter("toPropertySymbol"));
		}
		if(StringUtil.isBlank(req.getPropertySymbol())){
			req.setPropertySymbol("ZAN");
		}
		bsEntrustLoanViewService.getListByTimePropertySymbol(req, res);
		model.put("count", res.getTotalRows());
		model.put("list", res.getValueList());
		model.put("VIPview", res.getVIPview());
		model.put("req", req);
		return "statistics/entrustLoanView/index";
	}
	
}
