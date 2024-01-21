package com.pinting.manage.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_AgentPerformance;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_AgentQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_AgentUserQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_AgentPerformance;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_AgentQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_AgentUserQuery;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;

@Controller
public class AgentController {
	@Autowired
	@Qualifier("dispatchService")
	public HessianService manageService;
	
	@RequestMapping("/statistics/agent/index")
	public String agentInit(ReqMsg_Statistics_AgentQuery req,HashMap<String,Object> model){
			ResMsg_Statistics_AgentQuery res = (ResMsg_Statistics_AgentQuery) manageService.handleMsg(req);
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", res.getNumPerPage());
			model.put("agentList", res.getAgentList());
			model.put("totalRows", res.getTotalRows());
		return "/statistics/agentUser/index";
	}
	
	@RequestMapping("/statistics/agent/agentUser")
	public String agentUser(ReqMsg_Statistics_AgentUserQuery req,HashMap<String,Object> model){
		String userName = req.getUserName();
		String mobile = req.getMobile();
		if(!StringUtil.isEmpty(mobile)){
			req.setMobile(mobile.trim());
		}
		if(!StringUtil.isEmpty(userName)){
			req.setUserName(userName.trim());
		}
		
		ResMsg_Statistics_AgentUserQuery res = (ResMsg_Statistics_AgentUserQuery) manageService.handleMsg(req);
		model.put("agentUserList", res.getAgentUserList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);
		
		return "/statistics/agentUser/agent_user";
	}
	
	/**
	 * 渠道用户统计 过滤掉钱报渠道
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/agent/conciseIndex")
	public String agentConciseInit(ReqMsg_Statistics_AgentQuery req,HashMap<String,Object> model){
		if (req.getQueryDefaultPageFlag() != null && req.getQueryDefaultPageFlag().equals("DEFAULTPAGE")) {
			ResMsg_Statistics_AgentQuery res = (ResMsg_Statistics_AgentQuery) manageService.handleMsg(req);
			model.put("agentList", res.getAgentList());
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", res.getNumPerPage());
			model.put("totalRows", res.getTotalRows());
		} else {
			ResMsg_Statistics_AgentQuery res = (ResMsg_Statistics_AgentQuery) manageService.handleMsg(req);
			model.put("agentList", res.getAgentList());
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", 200); // 渠道用户统计,分页默认显示是200条
			model.put("totalRows", res.getTotalRows());
		}
		
		return "/statistics/agentUser/conciseIndex";
	}
	
	/**
	 * 渠道业绩统计
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/statistics/agentUser/performance")
	public String agentPerformanceInit(ReqMsg_Statistics_AgentPerformance req, HttpServletRequest request, HashMap<String, Object> model) {
		// 年化交易额 升序、降序排序
		if (request.getParameter("orderDirection") != null
				&& request.getParameter("orderField") != null && request.getParameter("orderDirection").equals("asc")) {
			model.put("proceedsBalance", "asc");
		} else if(request.getParameter("orderDirection") != null
				&& request.getParameter("orderField") != null && request.getParameter("orderDirection").equals("desc")){
			model.put("proceedsBalance", "desc");
		}
		if (req.getQueryDefaultPageFlag() != null && req.getQueryDefaultPageFlag().equals("DEFAULTPAGE")) {
			ResMsg_Statistics_AgentPerformance res = (ResMsg_Statistics_AgentPerformance) manageService.handleMsg(req);
			model.put("agentPerformanceList", res.getValueList());
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", res.getNumPerPage());
			model.put("totalRows", res.getTotalRows());
			model.put("agents", res.getAgentList());
			model.put("proceedsBalanceTotal", res.getProceedsBalanceTotal());
		} else {
			ResMsg_Statistics_AgentPerformance res = (ResMsg_Statistics_AgentPerformance) manageService.handleMsg(req);
			model.put("agentPerformanceList", res.getValueList());
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", 200);
			model.put("totalRows", res.getTotalRows());
			model.put("agents", res.getAgentList());
			model.put("proceedsBalanceTotal", res.getProceedsBalanceTotal());
		}
		model.put("req", req);
		return "/statistics/agentUser/performance";
	}
	
	
}
