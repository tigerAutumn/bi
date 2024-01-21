package com.pinting.business.dao;

import com.pinting.business.model.BsUserKeepView;
import com.pinting.business.model.BsUserKeepViewExample;
import com.pinting.business.model.vo.BsUserKeepViewVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserKeepViewMapper {
    int countByExample(BsUserKeepViewExample example);

    int deleteByExample(BsUserKeepViewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserKeepView record);

    int insertSelective(BsUserKeepView record);

    List<BsUserKeepView> selectByExample(BsUserKeepViewExample example);

    BsUserKeepView selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserKeepView record, @Param("example") BsUserKeepViewExample example);

    int updateByExample(@Param("record") BsUserKeepView record, @Param("example") BsUserKeepViewExample example);

    int updateByPrimaryKeySelective(BsUserKeepView record);

    int updateByPrimaryKey(BsUserKeepView record);
    
    /**
     * 用户留存率查询
     * 
     * 查询7个时间点  注册用户数
     * @param yesterday 系统时间-1天
     * @param agentId 渠道ID   1、all_user  2、非渠道 agent_id is null   3、渠道
     * @return
     */
	List<BsUserKeepViewVO> selectUserRegisterList(@Param("yesterday") String yesterday, @Param("agentId") String agentId);
	
	/**
	 * 活跃用户登录次数
	 * @param yesterday 系统时间-1天
	 * @param agentId 渠道ID   1、all_user  2、非渠道 agent_id is null   3、渠道
	 * @return
	 */
	List<BsUserKeepViewVO> selectActiveUserList(@Param("yesterday") String yesterday, @Param("agentId") String agentId);
	
	List<BsUserKeepViewVO> selectUserKeepViewList(BsUserKeepViewVO record);
	
	int selectUserKeepViewCount(BsUserKeepViewVO record);
	
	/**
	 * 根据注册时间和渠道查询对象
	 * @param registDate
	 * @param agentId
	 * @return
	 */
	BsUserKeepView selectByRegistDateAgent(@Param("registDate") String registDate, @Param("agentId") Integer agentId);

}