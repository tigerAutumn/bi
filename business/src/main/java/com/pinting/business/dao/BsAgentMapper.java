package com.pinting.business.dao;

import java.util.List;

import com.pinting.business.model.vo.WxAgentVO;
import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.vo.AgentStatInfoVO;
import com.pinting.business.model.vo.SpreadChannelVO;

public interface BsAgentMapper {
    int countByExample(BsAgentExample example);

    int deleteByExample(BsAgentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAgent record);

    int insertSelective(BsAgent record);

    List<BsAgent> selectByExample(BsAgentExample example);

    BsAgent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAgent record, @Param("example") BsAgentExample example);

    int updateByExample(@Param("record") BsAgent record, @Param("example") BsAgentExample example);

    int updateByPrimaryKeySelective(BsAgent record);

    int updateByPrimaryKey(BsAgent record);
    /**
     * 渠道统计列表查询
     * @param record
     * @return
     */
    List<AgentStatInfoVO> selectAgentStatList(BsAgent record);
    
    /**
     * 浏览量+1
     * @param id
     * @return
     */
    int increaseViewTimesById(Integer id);
    
    List<BsAgent> nameGroupByList(BsAgent record);
    
    /**
     * 渠道业绩统计
     * @param agentVO
     * @return
     */
    List<AgentStatInfoVO> selectPerformanceList(AgentStatInfoVO agentVO);
    
    /**
     * 渠道部门列表
     */
    List<BsAgent> selectAgentDept(BsAgent record);
    
    /**
     * 查询所有渠道
     * @return
     */
    List<BsAgent> selectAllAgentList();
    
    /**
     * 查询所有渠道(分页)
     * @param start
     * @param numPerPage
     * @param status 
     * @param agentName 
     * @return
     */
	List<SpreadChannelVO> querySpreadChannelList(@Param("start") Integer start,
			@Param("numPerPage")Integer numPerPage,@Param("agentName") String agentName,@Param("status") String status);

	/**
	 * 查询所有渠道count
	 * @param agentName
	 * @param status
	 * @return
	 */
	Integer getTotalCount(@Param("agentName") String agentName,@Param("status") String status);

    /**
     * 公众号渠道管理列表
     * @param agentName
     * @param start
     * @param numPerPage
     * @return
     */
    List<WxAgentVO> selectWxAgentList(@Param("agentName") String agentName, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 公众号渠道管理统计
     * @param agentName
     * @return
     */
    int selectWxAgentCount(@Param("agentName") String agentName);

    /**
     * 根据渠道编号，分页查询该渠道关注的人数列表
     * @param wxAgentId
     * @param start
     * @param numPerPage
     * @return
     */
    List<WxAgentVO> selectWxInfoListByAgentId(@Param("wxAgentId") Integer wxAgentId,
                                              @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

}