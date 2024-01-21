package com.pinting.business.service.manage;

import java.util.Date;
import java.util.List;

import org.aspectj.weaver.loadtime.Agent;

import com.pinting.business.model.BsAgent;
import com.pinting.business.model.vo.AgentStatInfoVO;
import com.pinting.business.model.vo.AgentUserInfoVO;

public interface AgentService {
	
	/**
	 * 渠道信息统计
	 * @param start
	 * @param pageNum
	 * @return 成功返回list，否则返回null
	 */
	public List<AgentStatInfoVO> findAgentStatList(Integer pageNum, Integer numPerPage);
	
	/**
	 * 渠道总数
	 * @return
	 */
	public int countAgentStatList();
	
	/**
	 * 渠道用户信息查询
	 * @param agentId
	 * @param start
	 * @param pageNum
	 * @return 成功返回list，否则返回null
	 */
	public List<AgentUserInfoVO> findAgentUserList(Integer agentId,  String userName, String mobile, String investFlag, String sregistTime, String eregistTime, Integer pageNum, Integer numPerPage);
	
	/**
	 * 某渠道的用户总数
	 * @param agentId
	 * @return
	 */
	public int countAgentUserList(Integer agentId, String userName, String mobile, String investFlag, String sregistTime, String eregistTime);
	/**
	 * 分组查询查询渠道List
	 * @param object
	 * @return
	 */
	public List<BsAgent> agentNameGroupByList(BsAgent object);

	/**
	 * 查询渠道List
	 * @return 成功返回List,否则返回空List
	 */
	public List<BsAgent> findAgentsList();
	
	/**
	 * 渠道业绩统计
	 * @param beginTime 开始时间
	 * @param overTime 结束时间
	 * @dept 部门
	 * @param pageNum 分页
	 * @param numPerPage
	 * @param orderDirection 排序
	 * @param orderField
	 * @return
	 */
	public List<AgentStatInfoVO> findPerformanceList(Date beginTime, Date overTime, String dept, 
			int pageNum, int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 渠道业绩统计
	 * @param beginTime 开始时间
	 * @param overTime 结束时间
	 * @dept 部门
	 * @terminal 终端
	 * @param pageNum 分页
	 * @param numPerPage
	 * @param orderDirection 排序
	 * @param orderField
	 * @return
	 */
	public List<AgentStatInfoVO> findPerformanceList(Date beginTime, Date overTime, String dept, String terminal,
			int pageNum, int numPerPage, String orderDirection, String orderField);
		
	/**
	 * 查询渠道部门列表
	 * @return
	 */
	public List<BsAgent> findAgentDeptList(BsAgent object);
	
	/**
	 * 查询所有渠道
	 * @return
	 */
	public List<BsAgent> findAllAgentList();
	
	/**
	 * 查询所有渠道的名字
	 * @return
	 */
	public String findAllAgentName(List<Integer> agentIds);
	
	/**
	 * 查询所有渠道总数
	 * @return
	 */
	public int findAgentsTotalRows();
	
}
