package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsAgentMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.AgentStatInfoVO;
import com.pinting.business.model.vo.AgentUserInfoVO;
import com.pinting.business.service.manage.AgentService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {
	@Autowired
	private BsAgentMapper bsAgentMapper;
	@Autowired
	private BsUserMapper bsUserMapper;

	@Override
	public List<AgentStatInfoVO> findAgentStatList(Integer pageNum, Integer numPerPage) {
		BsAgent agent = new BsAgent();
		agent.setPageNum(pageNum);
		agent.setNumPerPage(numPerPage);
		List<AgentStatInfoVO> list = bsAgentMapper.selectAgentStatList(agent);
		return !CollectionUtils.isEmpty(list) ? list : null;
	}

	@Override
	public List<AgentUserInfoVO> findAgentUserList(Integer agentId, String userName, String mobile, String investFlag, String sregistTime, String eregistTime,Integer pageNum, Integer numPerPage) {
		BsUser user = new BsUser();
		user.setAgentId(agentId);
		user.setMobile(mobile);
		user.setUserName(userName);
		user.setPageNum(pageNum);
		user.setNumPerPage(numPerPage);
		List<AgentUserInfoVO> list = bsUserMapper.selectAgentUserList(user, investFlag, sregistTime, eregistTime);
		return !CollectionUtils.isEmpty(list) ? list : null;
	}

	@Override
	public int countAgentStatList() {
		BsAgentExample example = new BsAgentExample();
		return bsAgentMapper.countByExample(example);
	}

	@Override
	public int countAgentUserList(Integer agentId, String userName, String mobile, String investFlag, String sregistTime, String eregistTime) {
		BsUser record = new BsUser();
		record.setAgentId(agentId);
		record.setMobile(mobile);
		record.setUserName(userName);
		return bsUserMapper.countAgentUserList(record, investFlag, sregistTime, eregistTime);
	}

	@Override
	public List<BsAgent> findAgentsList() {
		return bsAgentMapper.selectByExample(null);
	}

	@Override
	public List<BsAgent> agentNameGroupByList(BsAgent object) {
		return bsAgentMapper.nameGroupByList(object);
	}

	@Override
	public List<AgentStatInfoVO> findPerformanceList(Date beginTime, Date overTime, String dept,  
			int pageNum, int numPerPage, String orderDirection, String orderField) {
		AgentStatInfoVO agentVO = new AgentStatInfoVO();
		if (beginTime != null && !"".equals(beginTime)) {
			agentVO.setBeginTime(beginTime);
		}
		if (overTime != null && !"".equals(overTime)) {
			agentVO.setOverTime(DateUtil.addDays(overTime, 1));
		}
		if (dept != null && !"".equals(dept)) {
			agentVO.setDept(dept);
		}
		if (orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			agentVO.setOrderDirection(orderDirection);
			agentVO.setOrderField(orderField);
		}
		agentVO.setPageNum(pageNum);
		agentVO.setNumPerPage(numPerPage);
		return bsAgentMapper.selectPerformanceList(agentVO);
	}

	@Override
	public List<BsAgent> findAgentDeptList(BsAgent object) {
		return bsAgentMapper.selectAgentDept(object);
	}

	@Override
	public List<BsAgent> findAllAgentList() {
		return bsAgentMapper.selectAllAgentList();
	}

	@Override
	public String findAllAgentName(List<Integer> agentIds) {
		StringBuffer agentNames = new StringBuffer("");
		BsAgentExample example = new BsAgentExample();
		example.createCriteria().andIdIn(agentIds);
		List<BsAgent> agentList = bsAgentMapper.selectByExample(example);
		for (BsAgent bsAgent : agentList) {
			agentNames.append(bsAgent.getAgentName()+",");
		}

		if (StringUtils.isNotBlank(agentNames.toString())) {
			return agentNames.toString().substring(0, agentNames.toString().length()-1);
		}
		return "";
	}

	@Override
	public int findAgentsTotalRows() {
		return bsAgentMapper.countByExample(null);
	}
	
	@Override
	public List<AgentStatInfoVO> findPerformanceList(Date beginTime, Date overTime, String dept, String terminal,
			int pageNum, int numPerPage, String orderDirection, String orderField) {
		AgentStatInfoVO agentVO = new AgentStatInfoVO();
		if (beginTime != null && !"".equals(beginTime)) {
			agentVO.setBeginTime(beginTime);
		}
		if (overTime != null && !"".equals(overTime)) {
			agentVO.setOverTime(DateUtil.addDays(overTime, 1));
		}
		if (dept != null && !"".equals(dept)) {
			agentVO.setDept(dept);
		}
		if (StringUtil.isNotEmpty(terminal)) {
			agentVO.setTerminal(terminal);
		}
		if (orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			agentVO.setOrderDirection(orderDirection);
			agentVO.setOrderField(orderField);
		}
		agentVO.setPageNum(pageNum);
		agentVO.setNumPerPage(numPerPage);
		return bsAgentMapper.selectPerformanceList(agentVO);
	}
	
}
