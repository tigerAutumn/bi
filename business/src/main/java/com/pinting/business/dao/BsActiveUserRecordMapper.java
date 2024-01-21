package com.pinting.business.dao;

import com.pinting.business.model.BsActiveUserRecord;
import com.pinting.business.model.BsActiveUserRecordExample;
import com.pinting.business.model.vo.BsActiveUserRecordVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsActiveUserRecordMapper {
    int countByExample(BsActiveUserRecordExample example);

    int deleteByExample(BsActiveUserRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActiveUserRecord record);

    int insertSelective(BsActiveUserRecord record);

    List<BsActiveUserRecord> selectByExample(BsActiveUserRecordExample example);

    BsActiveUserRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActiveUserRecord record, @Param("example") BsActiveUserRecordExample example);

    int updateByExample(@Param("record") BsActiveUserRecord record, @Param("example") BsActiveUserRecordExample example);

    int updateByPrimaryKeySelective(BsActiveUserRecord record);

    int updateByPrimaryKey(BsActiveUserRecord record);
    
    
    /**
     * 根据用户id，终端，登录时间查询记录
     * @param record
     * @return
     */
    List<BsActiveUserRecord> selectByRecord(BsActiveUserRecord record);
    
    /**
     * 管理台列表查询
     * @param agents
     * @param agentsFlag --0,有普通用户且有其他渠道；1--仅有普通用户
     * @param startDate
     * @param endDate
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsActiveUserRecordVO> selectList(@Param("agentIds")List<Object> agentIds,
    		@Param("agentsFlag")String agentsFlag,
    		@Param("startDate")String startDate,
    		@Param("endDate")String endDate,
    		@Param("start") Integer start,
            @Param("numPerPage") Integer numPerPage);
    
    /**
     * 总条数查询
     * @param agents
     * @param agentsFlag --0,有普通用户且有其他渠道；1--仅有普通用户
     * @param startDate
     * @param endDate
     * @return
     */
   int countList(@Param("agentIds")List<Object> agentIds,
		@Param("agentsFlag")String agentsFlag,
   		@Param("startDate")String startDate,
   		@Param("endDate")String endDate);
    
}