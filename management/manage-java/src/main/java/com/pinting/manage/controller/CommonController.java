/**
 * 
 */
package com.pinting.manage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.model.BsAgent;
import com.pinting.business.service.manage.AgentService;

/**
 * 通用列表控制器
 *
 * @Project: manage-java
 * @author yanwl
 * @Title: CommonController.java
 * @date 2016-4-6 上午11:40:07
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class CommonController {
	@Autowired
	private AgentService agentService;
	
	/**
	 * 所有用户渠道
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/agent/agent_list")
	public String agentList(Map<String, Object> model) {
		List<BsAgent> agents = agentService.findAllAgentList();
		model.put("agents", agents);
		return  "/common/agent_list";
	}
	
	/**
	 * 所有展示终端
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/terminal/terminal_list")
	public String terminalList() {
		return  "/common/terminal_list";
	}

	/**
	 * 订单信息展示终端
	 * @return
	 */
	@RequestMapping("/terminal/pay_orders_terminal_list")
	public String payOrdersTerminalList() {
		return  "/common/pay_orders_terminal_list";
	}
}
