package com.pinting.manage.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_BsActiveUserRecord_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActiveUserRecord_GetList;
import com.pinting.core.hessian.service.HessianService;


@Controller
@RequestMapping("/statistics/activeUserRecor")
public class BsActiveUserRecordController {

	@Autowired
    @Qualifier("dispatchService")
    private HessianService dispatchService;
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,ReqMsg_BsActiveUserRecord_GetList req){
		
		if(StringUtils.isBlank(req.getAgents())){
			model.put("agentSize", 0);
		}else{
			String agent[] = req.getAgents().split(",");
			if(agent[0].equals("-1") || agent[0].equals("")){
				model.put("agentSize", agent.length-1);
			}else{
				model.put("agentSize", agent.length);
			}
		}
		
		model.put("agents", req.getAgents());
		ResMsg_BsActiveUserRecord_GetList res = (ResMsg_BsActiveUserRecord_GetList) dispatchService.handleMsg(req);
		model.put("count", res.getTotalRows());
		model.put("list", res.getValueList());
		model.put("req", req);
		return "statistics/activeUserRecor/index";
	}
}
